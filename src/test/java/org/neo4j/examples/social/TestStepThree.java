package org.neo4j.examples.social;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.neo4j.graphdb.Node;

public class TestStepThree extends StepTest
{
    /**
     * Make the test pass by updating these methods:
     * <ul>
     * <li>{@link TheMatrix#createPersonNode(String)}</li>
     * <li>{@link TheMatrix#createInterestNode(String)} (optional)</li>
     * </ul>
     * and implementing this method:
     * <ul>
     * <li>{@link TheMatrix#lookupPerson(String)}</li>
     * </ul>
     */
    @Test
    public void canLookupMrAnderson()
    {
        Node neo = theMatrix().lookupPerson( TheMatrix.THOMAS_ANDERSON );
        assertNotNull( "Could not look up Thomas Anderson, "
                       + "is TheMatrix.lookupPerson() implemented?", neo );
        assertEquals( "The Thomas Andersom we retrieved was not the one we expected", mrAnderson(),
                neo );
        testFoaf( neo );
    }

    /**
     * Make the test pass by implementing this method:
     * <ul>
     * <li>{@link TheMatrix#createPersonNode(String)}</li>
     * </ul>
     * and updating this method:
     * <ul>
     * <li>{@link TheMatrix#lookupPerson(String)}</li>
     * </ul>
     */
    @Test
    public void canLookupAgentSmith()
    {
        Node agentSmith = theMatrix().lookupPerson( TheMatrix.AGENT_SMITH );
        assertNotNull( "Could not look up Agent Smith", agentSmith );
        assertIsNamed( TheMatrix.AGENT_SMITH, agentSmith );
    }
}
