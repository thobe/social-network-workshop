package org.neo4j.examples.social.graphdb;

import org.neo4j.examples.social.domain.Person;
import org.neo4j.examples.social.domain.SocialNetwork;
import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Transaction;
import org.neo4j.index.IndexService;
import org.neo4j.index.lucene.LuceneIndexService;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.kernel.Traversal;
import org.neo4j.kernel.impl.core.LockReleaser;
import org.neo4j.kernel.impl.transaction.LockManager;
import org.neo4j.kernel.impl.transaction.LockType;

public class Neo4jSocialNetwork implements SocialNetwork
{
    static final String INTEREST = "interest";
    static final String NAME = PersonImpl.NAME;
    static final String PERSON = "person";
    private static final int MAX_DEPTH = 0;
    private final GraphDatabaseService graphDb;
    private final IndexService indexes;
    private final PathFinder<Path> shortestPath;

    public Neo4jSocialNetwork( String storeDir )
    {
        this( new EmbeddedGraphDatabase( storeDir ) );
    }

    private Neo4jSocialNetwork( EmbeddedGraphDatabase graphDb )
    {
        // Silly constructor used because Java does not allow statements before
        // invoking another constructor.
        this( graphDb, new LuceneIndexService( graphDb ) );
    }

    Neo4jSocialNetwork( GraphDatabaseService graphDb, IndexService indexes )
    {
        this.graphDb = graphDb;
        this.indexes = indexes;
        this.shortestPath = GraphAlgoFactory.shortestPath( Traversal.expanderForTypes(
                SocialTypes.FRIEND, Direction.BOTH ), MAX_DEPTH );
    }

    public void shutdown()
    {
        indexes.shutdown();
        graphDb.shutdown();
    }

    public Person lookup( String name )
    {
        Node person = indexes.getSingleNode( PERSON, name );
        if ( person == null )
        {
            return null;
        }
        else
        {
            return new PersonImpl( this, person );
        }
    }

    public Person create( String name )
    {
        class PersonEntry extends IndexEntry
        {
            boolean created = false;

            PersonEntry( @SuppressWarnings( "hiding" ) String name )
            {
                super( PERSON, name );
            }

            @Override
            Node create()
            {
                created = true;
                Node person = graphDb.createNode();
                person.setProperty( NAME, value );
                return person;
            }
        }

        Transaction tx = graphDb.beginTx();
        try
        {
            PersonEntry person = new PersonEntry( name );
            Node result = getOrCreate( person );
            tx.success();
            if ( !person.created )
                throw new IllegalStateException( "Person \"" + name + "\" already exists." );
            return new PersonImpl( this, result );
        }
        finally
        {
            tx.finish();
        }
    }

    Node interest( String interest )
    {
        return getOrCreate( new IndexEntry( INTEREST, interest )
        {
            @Override
            Node create()
            {
                Node node = graphDb.createNode();
                node.setProperty( key, value );
                return node;
            }
        } );
    }

    Path findPath( Node source, Node target )
    {
        return shortestPath.findSinglePath( source, target );
    }

    private Node getOrCreate( IndexEntry entry )
    {
        Node result = indexes.getSingleNode( entry.key, entry.value );
        if ( result == null )
        {
            LockManager lm = ( (EmbeddedGraphDatabase) graphDb ).getConfig().getLockManager();
            LockReleaser lr = ( (EmbeddedGraphDatabase) graphDb ).getConfig().getLockReleaser();
            lm.getWriteLock( entry );
            result = indexes.getSingleNode( entry.key, entry.value );
            if ( result != null )
            {
                lm.releaseWriteLock( entry );
            }
            else
            {
                lr.addLockToTransaction( entry, LockType.WRITE );
                result = entry.create();
                indexes.index( result, entry.key, entry.value );
            }
        }
        return null;
    }

    private static abstract class IndexEntry
    {
        final String key;
        final String value;

        IndexEntry( String key, String value )
        {
            this.key = key;
            this.value = value;
        }

        abstract Node create();

        @Override
        public int hashCode()
        {
            return value.hashCode();
        }

        @Override
        public boolean equals( Object obj )
        {
            if ( obj instanceof IndexEntry )
            {
                IndexEntry entry = (IndexEntry) obj;
                return key.equals( entry.key ) && value.equals( entry.value );
            }
            else
            {
                return false;
            }
        }
    }
}
