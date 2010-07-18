package org.neo4j.examples.social.domain;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.neo4j.examples.social.TheMatrix;
import org.neo4j.examples.social.graphdb.TestNeo4jSocialNetwork;

public class TestStepFour extends TestNeo4jSocialNetwork
{
    @Test
    public void canLookupPeople()
    {
        assertNotNull( "Could not look up Thomas Anderson", socnet().lookupPerson(
                TheMatrix.THOMAS_ANDERSON ) );
        assertNotNull( "Could not look up Morpheus", socnet().lookupPerson( TheMatrix.MORPHEUS ) );
        assertNotNull( "Could not look up Trinity", socnet().lookupPerson( TheMatrix.TRINITY ) );
        assertNotNull( "Could not look up Agent Smith", socnet().lookupPerson(
                TheMatrix.AGENT_SMITH ) );
    }

    @Test
    public void canGetFriends() throws Exception
    {
        Person neo = socnet().lookupPerson( TheMatrix.THOMAS_ANDERSON );
        assertNotNull( "Could not lookup Thomas Anderson", neo );
        Set<String> expectedFriends = new HashSet<String>( Arrays.asList( TheMatrix.MORPHEUS,
                TheMatrix.TRINITY ) );
        for ( Person friend : neo.getFriends() )
        {
            if ( !expectedFriends.remove( friend.getName() ) )
            {
                System.out.println( "Unexpected frind " + friend );
            }
        }
        assertTrue( "Thomas Anderson did not have the expected frind(s) " + expectedFriends,
                expectedFriends.isEmpty() );
    }
}
