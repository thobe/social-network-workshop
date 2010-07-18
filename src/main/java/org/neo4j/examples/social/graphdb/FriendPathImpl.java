package org.neo4j.examples.social.graphdb;

import java.util.Iterator;

import org.neo4j.examples.social.domain.FriendPath;
import org.neo4j.examples.social.domain.Person;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.helpers.collection.IteratorWrapper;

class FriendPathImpl implements FriendPath
{
    private final Neo4jSocialNetwork socnet;
    private final Path path;

    FriendPathImpl( Neo4jSocialNetwork socnet, Path path )
    {
        this.socnet = socnet;
        this.path = path;
        if ( path.length() < 1 )
        {
            throw new IllegalArgumentException();
        }
    }

    public int length()
    {
        return path.length() + 1;
    }

    public Iterator<Person> iterator()
    {
        return new IteratorWrapper<Person, Node>( path.nodes().iterator() )
        {
            @Override
            protected Person underlyingObjectToObject( Node person )
            {
                return new PersonImpl( socnet, person );
            }
        };
    }

    @Override
    public String toString()
    {
        Iterator<Node> persons = path.nodes().iterator();
        StringBuilder result = new StringBuilder( PersonImpl.getName( persons.next() ) );
        result.append( " is friends with " );
        result.append( PersonImpl.getName( path.endNode() ) );
        Node friend = persons.next();
        String sep = " through ";
        while ( persons.hasNext() )
        {
            result.append( sep );
            sep = " and ";
            result.append( PersonImpl.getName( friend ) );
            friend = persons.next();
        }
        return result.toString();
    }
}
