package org.neo4j.examples.social.graphdb;

import org.neo4j.examples.social.domain.Person;
import org.neo4j.examples.social.domain.SocialNetwork;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.index.IndexService;
import org.neo4j.index.lucene.LuceneIndexService;
import org.neo4j.kernel.EmbeddedGraphDatabase;

/**
 * Implementation of the Social Network domain API for Steps four to six of the
 * tutorial.
 */
public class Neo4jSocialNetwork implements SocialNetwork
{
    static final String INTEREST = "interest";
    static final String NAME = PersonImpl.PERSON_NAME;
    static final String PERSON = "person";
    private final GraphDatabaseService graphDb;
    private final IndexService indexes;

    /**
     * Creates an implementation of the Social Network service based on Neo4j.
     *
     * @param storeDir the location to store the Neo4j data.
     */
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
    }

    public void shutdown()
    {
        indexes.shutdown();
        graphDb.shutdown();
    }

    public Person lookupPerson( String name )
    {
        // TODO: implement this in Step four
        return null;
    }

    public Person createPerson( String name )
    {
        // TODO: implement this in Step four
        return null;
    }
}
