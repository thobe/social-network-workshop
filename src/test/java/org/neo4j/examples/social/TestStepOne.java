package org.neo4j.examples.social;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.neo4j.graphdb.Node;

public class TestStepOne extends StepTest
{
    @Test
    public void canGetFriendsOfFriends()
    {
        Node mrAnderson = mrAnderson();
        assertNotNull( "The node retuned by TheMatrix.createSocialNetwork() was null, "
                       + "the method is probably not implemented.", mrAnderson );
        assertIsNamed( TheMatrix.THOMAS_ANDERSON, mrAnderson );
        testFoaf( mrAnderson );
    }
}
