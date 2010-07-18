package org.neo4j.examples.social.domain;

import static org.junit.Assert.assertFalse;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.neo4j.examples.social.TheMatrix;
import org.neo4j.examples.social.graphdb.TestNeo4jSocialNetwork;

public class TestStepSix extends TestNeo4jSocialNetwork
{
    @Test
    public void suggestionsDoNotContainPreviousFriends()
    {
        Person neo = socnet().lookupPerson( TheMatrix.THOMAS_ANDERSON );
        Set<Person> suggestions = new HashSet<Person>( neo.suggestFriends() );
        assertFalse( "No friends suggested", suggestions.isEmpty() );
        assertFalse( "Suggestion included previous friends",
                suggestions.removeAll( neo.getFriends() ) );
    }
}
