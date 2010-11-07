package org.neo4j.examples.social.impl;

import static org.neo4j.examples.social.SocialNetworkRelationshipTypes.FRIENDS;
import static org.neo4j.examples.social.SocialNetworkRelationshipTypes.INTERESTED_IN;

import org.neo4j.examples.social.domain.DataLoader;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.index.Index;
import org.neo4j.kernel.EmbeddedGraphDatabase;

public final class Neo4jDataLoader extends DataLoader<Transaction>
{
    private final GraphDatabaseService graphDb;
    private final Index<Node> persons, interests;

    public Neo4jDataLoader( String storeDir )
    {
        this.graphDb = new EmbeddedGraphDatabase( storeDir );
        this.persons = graphDb.index().forNodes( Neo4jSocialNetwork.PERSON_INDEX );
        this.interests = graphDb.index().forNodes( Neo4jSocialNetwork.INTERESTS_INDEX );
    }

    private Node person( String name )
    {
        Node person = persons.get( Neo4jSocialNetwork.PERSON_NAME, name ).getSingle();
        if ( person == null )
        {
            person = graphDb.createNode();
            person.setProperty( PersonImpl.PERSON_NAME, name );
            persons.add( person, Neo4jSocialNetwork.PERSON_NAME, name );
        }
        return person;
    }

    private Node interest( String name )
    {
        Node interest = interests.get( Neo4jSocialNetwork.INTEREST, name ).getSingle();
        if ( interest == null )
        {
            interest = graphDb.createNode();
            interest.setProperty( Neo4jSocialNetwork.INTEREST, name );
            interests.add( interest, Neo4jSocialNetwork.INTEREST, name );
        }
        return interest;
    }

    @Override
    protected void friends( String one, String other )
    {
        createRelationship( person( one ), person( other ), FRIENDS );
    }

    private Relationship createRelationship( Node one, Node other, RelationshipType type )
    {
        return one.createRelationshipTo( other, type );
    }

    @Override
    protected void interest( String person, String interest )
    {
        createRelationship( person( person ), interest( interest ), INTERESTED_IN );
    }

    @Override
    protected Transaction txBegin()
    {
        return graphDb.beginTx();
    }

    @Override
    protected void txSuccessful( Transaction tx )
    {
        tx.success();
    }

    @Override
    protected void txCompleted( Transaction tx )
    {
        tx.finish();
    }

    @Override
    public void done()
    {
        graphDb.shutdown();
    }
}
