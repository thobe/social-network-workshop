package org.neo4j.examples.social.domain;

public interface FriendPath extends Iterable<Person>
{
    public int length();
}
