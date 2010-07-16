package org.neo4j.examples.social.graphdb;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.neo4j.examples.social.domain.FriendPath;
import org.neo4j.examples.social.domain.Person;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.traversal.TraversalDescription;
import org.neo4j.graphdb.traversal.Uniqueness;
import org.neo4j.kernel.Traversal;

class PersonImpl implements Person
{
    private static final TraversalDescription FRIENDS = Traversal.description().uniqueness(
            Uniqueness.NONE ).relationships( SocialTypes.FRIEND );
    private static final TraversalDescription INTERESTS = Traversal.description().uniqueness(
            Uniqueness.NONE ).relationships( SocialTypes.INTEREST ).prune(
            Traversal.pruneAfterDepth( 1 ) );
    static final String NAME = "name";

    private final Node underlyingNode;
    private final Neo4jSocialNetwork social;

    PersonImpl( Neo4jSocialNetwork social, Node underlyingNode )
    {
        this.social = social;
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

    public void addFriend( Person friend )
    {
        Transaction tx = underlyingNode.getGraphDatabase().beginTx();
        try
        {
            underlyingNode.createRelationshipTo( ( (PersonImpl) friend ).underlyingNode,
                    SocialTypes.FRIEND );
            tx.success();
        }
        finally
        {
            tx.finish();
        }
    }

    public void addInterest( String interest )
    {
        Transaction tx = underlyingNode.getGraphDatabase().beginTx();
        try
        {
            underlyingNode.createRelationshipTo( social.interest( interest ), SocialTypes.INTEREST );
            tx.success();
        }
        finally
        {
            tx.finish();
        }
    }

    public List<Person> getFriends()
    {
        List<Person> friends = new LinkedList<Person>();
        for ( Node friend : FRIENDS.prune( Traversal.pruneAfterDepth( 1 ) ).traverse(
                underlyingNode ).nodes() )
        {
            friends.add( new PersonImpl( social, friend ) );
        }
        return Collections.unmodifiableList( friends );
    }

    public List<String> getInterests()
    {
        List<String> interests = new LinkedList<String>();
        for ( Node interest : INTERESTS.traverse( underlyingNode ).nodes() )
        {
            interests.add( (String) interest.getProperty( Neo4jSocialNetwork.INTEREST ) );
        }
        return Collections.unmodifiableList( interests );
    }

    public FriendPath getPath( Person other )
    {
        Path path = social.findPath( this.underlyingNode, ( (PersonImpl) other ).underlyingNode );
        if ( path == null )
        {
            return null;
        }
        else
        {
            return new FriendPathImpl( social, path );
        }
    }

    public Collection<Person> recommendFriends()
    {
        throw new UnsupportedOperationException( "recommendation not implemented" );
    }

    @Override
    public String toString()
    {
        return toString( underlyingNode );
    }

    static String toString( Node person )
    {
        return (String) person.getProperty( PersonImpl.NAME );
    }
}
