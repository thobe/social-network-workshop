package org.neo4j.examples.social.impl;

import static org.neo4j.examples.social.SocialNetworkRelationshipTypes.FRIENDS;
import static org.neo4j.examples.social.SocialNetworkRelationshipTypes.INTERESTED_IN;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.neo4j.examples.social.domain.FriendPath;
import org.neo4j.examples.social.domain.Person;
import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.helpers.Predicate;
import org.neo4j.kernel.Traversal;
import org.neo4j.kernel.Uniqueness;

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
        for ( Relationship interest : underlyingNode.getRelationships( INTERESTED_IN ) )
        {
            interests.add( (String) interest.getEndNode().getProperty( Neo4jSocialNetwork.INTEREST ) );
        }
        return Collections.unmodifiableCollection( interests );
    }

    public Collection<Person> getFriends()
    {
        List<Person> result = new ArrayList<Person>();
        for ( Relationship firendship : underlyingNode.getRelationships( FRIENDS ) )
        {
            result.add( new PersonImpl( socnet, firendship.getOtherNode( underlyingNode ) ) );
        }
        return result;
    }

    private static final PathFinder<Path> shortestPath = GraphAlgoFactory.shortestPath(
            Traversal.expanderForTypes( FRIENDS ), 4 );
    public FriendPath getPath( Person other )
    {
        // There cannot be a path if they are of different implementations
        if ( !( other instanceof PersonImpl ) ) return null;

        Path path = shortestPath.findSinglePath( underlyingNode,
                ( (PersonImpl) other ).underlyingNode );
        return path == null ? null : new FriendPathImpl( socnet, path );
    }

    private static final TraversalDescription friendsSuggestion = Traversal.description().relationships(
            FRIENDS ).breadthFirst().uniqueness( Uniqueness.NODE_GLOBAL ).prune(
            Traversal.pruneAfterDepth( 2 ) ).filter(
            new Predicate<Path>()
            {
                public boolean accept( Path item )
                {
                    return item.length() == 2;
                }
            } );
    public Collection<Person> suggestFriends()
    {
        List<Person> result = new ArrayList<Person>();
        for ( Node node : friendsSuggestion.traverse( underlyingNode ).nodes() )
        {
            result.add( new PersonImpl( socnet, node ) );
        }
        return result;
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
