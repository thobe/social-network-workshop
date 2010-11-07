package org.neo4j.examples.social.domain;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.neo4j.examples.social.TheMatrix;
import org.neo4j.examples.social.graphdb.TestNeo4jSocialNetwork;

public class TestStepFive extends TestNeo4jSocialNetwork
{
    /**
     * Make the test pass by implementing this method:
     * <ul>
     * <li>{@link Person#getPath(Person)}</li>
     * </ul>
     */
    @Test
    public void pathBetweenNeoAndSmithIsFourPersonsLong()
    {
        FriendPath path = socnet().lookupPerson( TheMatrix.THOMAS_ANDERSON ).getPath(
                socnet().lookupPerson( TheMatrix.AGENT_SMITH ) );
        assertNotNull( "No path found between Thomas Anderson and Agent Smith", path );
        assertEquals( 4, path.length() );
    }
}
