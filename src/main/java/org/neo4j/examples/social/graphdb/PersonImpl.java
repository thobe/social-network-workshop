package org.neo4j.examples.social.graphdb;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.neo4j.examples.social.domain.FriendPath;
import org.neo4j.examples.social.domain.Person;
import org.neo4j.graphdb.Node;

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
        // TODO: implement this in Step four
    }

    public void addInterest( String interest )
    {
        // TODO: implement this in Step four
    }

    public List<Person> getFriends()
    {
        // TODO: implement this in Step four
        return Collections.emptyList();
    }

    public List<String> getInterests()
    {
        // TODO: implement this in Step four
        return Collections.emptyList();
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
}
