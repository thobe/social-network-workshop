package org.neo4j.examples.social.graphdb;

import static org.neo4j.examples.social.SocialNetworkRelationshipTypes.FRIENDS;
import static org.neo4j.examples.social.SocialNetworkRelationshipTypes.INTERESTED_IN;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.neo4j.examples.social.domain.FriendPath;
import org.neo4j.examples.social.domain.Person;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;

class PersonImpl implements Person
{
    static final String PERSON_NAME = "name";
    private final Node underlyingNode;
    private final Neo4jSocialNetwork socnet;

    PersonImpl( Neo4jSocialNetwork socnet, Node underlyingNode )
    {
        this.socnet = socnet;
        this.underlyingNode = underlyingNode;
    }

    @Override
    public int hashCode()
    {
        return underlyingNode.hashCode();
    }

    @Override
    public boolean equals( Object obj )
    {
        return obj instanceof PersonImpl
               && underlyingNode.equals( ( (PersonImpl) obj ).underlyingNode );
    }

    @Override
    public String toString()
    {
        return "Person[" + getName() + "]";
    }

    public String getName()
    {
        return getName( underlyingNode );
    }

    static String getName( Node person )
    {
        return (String) person.getProperty( PERSON_NAME );
    }

    public void addFriend( Person friend )
    {
        Node friendNode = underlyingNodeOf( friend );
        Transaction tx = socnet.beginTx();
        try
        {
            if ( createSingleRelationship( friendNode, FRIENDS ) )
            {
                tx.success();
            }
        }
        finally
        {
            tx.finish();
        }
    }

    public void addInterest( String interest )
    {
        Transaction tx = socnet.beginTx();
        try
        {
            Node interestNode = socnet.interestNode( interest );
            if ( createSingleRelationship( interestNode, INTERESTED_IN ) )
            {
                tx.success();
            }
        }
        finally
        {
            tx.finish();
        }
    }

    public Collection<String> getInterests()
    {
        List<String> interests = new LinkedList<String>();
        for ( Relationship interest : underlyingNode.getRelationships( FRIENDS ) )
        {
            interests.add( (String) interest.getEndNode().getProperty( Neo4jSocialNetwork.INTEREST ) );
        }
        return Collections.unmodifiableCollection( interests );
    }

    public Collection<Person> getFriends()
    {
        // TODO: implement this in Step four
        return Collections.emptySet();
    }

    public FriendPath getPath( Person other )
    {
        // TODO: implement this in Step five
        return null;
    }

    public Collection<Person> suggestFriends()
    {
        // TODO: implement this in Step six
        return Collections.emptyList();
    }

    private Node underlyingNodeOf( Person friend )
    {
        if ( friend instanceof PersonImpl )
        {
            return ( (PersonImpl) friend ).underlyingNode;
        }
        else
        {
            throw new IllegalArgumentException(
                    friend + " does not belong to the same social network as " + this );
        }
    }

    private boolean createSingleRelationship( Node other, RelationshipType type )
    {
        Relationship newRel = underlyingNode.createRelationshipTo( other, type );
        for ( Relationship rel : underlyingNode.getRelationships( type ) )
        {
            if ( rel == newRel ) continue;
            if ( rel.getOtherNode( underlyingNode ).equals( other ) ) return false;
        }
        return true;
    }
}
