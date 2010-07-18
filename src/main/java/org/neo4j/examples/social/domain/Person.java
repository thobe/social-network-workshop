package org.neo4j.examples.social.domain;

import java.util.Collection;

public interface Person
{
    String getName();

    Collection<Person> getFriends();

    void addFriend( Person friend );

    Collection<String> getInterests();

    void addInterest( String interest );

    FriendPath getPath( Person other );

    Collection<Person> suggestFriends();
}
