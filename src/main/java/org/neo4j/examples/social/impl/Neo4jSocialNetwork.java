package org.neo4j.examples.social.impl;

import static org.neo4j.graphdb.DynamicRelationshipType.withName;

import org.neo4j.examples.social.domain.Person;
import org.neo4j.examples.social.domain.SocialNetwork;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.kernel.EmbeddedGraphDatabase;

/**
 * Implementation of the Social Network domain API for Steps four to six of the
 * tutorial.
 */
public class Neo4jSocialNetwork implements SocialNetwork
{
    static final String INTEREST = "interest";
    static final String PERSON_NAME = PersonImpl.PERSON_NAME;
    static final String PERSON_INDEX = "person";
    static final String INTERESTS_INDEX = INTEREST;
    private static final RelationshipType PERSONS_REFERENCE = withName( "PERSONS_REFERENCE" );
    private static final RelationshipType INTEREST_REFERENCE = withName( "INTEREST_REFERENCE" );
    private static final RelationshipType ISA = withName( "IS_A" );
    private final GraphDatabaseService graphDb;
    private final Index<Node> persons;
    private final Index<Node> interests;
    private final Node personRoot;
    private final Node interestRoot;

    /**
     * Creates an implementation of the Social Network service based on Neo4j.
     *
     * @param storeDir the location to store the Neo4j data.
     */
    public Neo4jSocialNetwork( String storeDir )
    {
        this.graphDb = new EmbeddedGraphDatabase( storeDir );
        this.persons = this.graphDb.index().forNodes( PERSON_INDEX );
        this.interests = this.graphDb.index().forNodes( INTERESTS_INDEX );
        this.personRoot = subreference( graphDb.getReferenceNode(), PERSONS_REFERENCE );
        this.interestRoot = subreference( graphDb.getReferenceNode(), INTEREST_REFERENCE );
    }

    private static Node subreference( Node reference, RelationshipType type )
    {
        GraphDatabaseService graphDb = reference.getGraphDatabase();
        Relationship ref = reference.getSingleRelationship( type, Direction.OUTGOING );
        if ( ref == null )
        {
            Transaction tx = graphDb.beginTx();
            try
            {
                Relationship newRef = reference.createRelationshipTo( graphDb.createNode(), type );
                // Check for concurrent creation
                for ( Relationship oldRef : reference.getRelationships( type, Direction.OUTGOING ) )
                {
                    if ( oldRef.equals( newRef ) ) continue;
                    ref = oldRef;
                }
                if ( ref == null )
                {
                    ref = newRef;
                    tx.success();
                }
            }
            finally
            {
                tx.finish();
            }
        }
        return ref.getEndNode();
    }

    public void shutdown()
    {
        graphDb.shutdown();
    }

    public Person lookupPerson( String name )
    {
        Node person = persons.get( PERSON_NAME, name ).getSingle();
        return person == null ? null : new PersonImpl( this, person );
    }

    public Person createPerson( String name )
    {
        Transaction tx = graphDb.beginTx();
        final Node person;
        try
        {
            person = graphDb.createNode();
            person.createRelationshipTo( personRoot, ISA );
            if ( lookupPerson( name ) != null )
            {
                throw new IllegalArgumentException( "The person \"" + name + "\" already exists." );
            }
            person.setProperty( PERSON_NAME, name );
            persons.add( person, PERSON_NAME, name );
            tx.success();
        }
        finally
        {
            tx.finish();
        }
        return new PersonImpl( this, person );
    }

    Transaction beginTx()
    {
        return graphDb.beginTx();
    }

    Node interestNode( String interest )
    {
        Node interestNode = interests.get( INTEREST, interest ).getSingle();
        if ( interestNode == null )
        {
            interestNode = graphDb.createNode();
            Relationship isa = interestNode.createRelationshipTo( interestRoot, ISA );
            Node concurrentNode = interests.get( INTEREST, interest ).getSingle();
            if ( concurrentNode != null )
            {
                isa.delete();
                interestNode.delete();
                interestNode = concurrentNode;
            }
            else
            {
                interests.add( interestNode, INTEREST, interest );
                interestNode.setProperty( INTEREST, interest );
            }
        }
        return interestNode;
    }
}
