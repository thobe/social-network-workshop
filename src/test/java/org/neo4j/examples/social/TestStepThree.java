package org.neo4j.examples.social;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.neo4j.examples.social.SocialNetworkRelationshipTypes.INTERESTED_IN;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

public class TestStepThree extends StepTest
{
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

    @Test
    public void canLookupAgentSmith()
    {
        Node agentSmith = theMatrix().lookupPerson( TheMatrix.AGENT_SMITH );
        assertNotNull( "Could not look up Agent Smith", agentSmith );
        assertIsNamed( TheMatrix.AGENT_SMITH, agentSmith );
    }

    @Test
    public void someoneIsInterestedInThomasAnderson()
    {
        Node interestInThomasAnderson = theMatrix().lookupInterest( TheMatrix.THOMAS_ANDERSON );
        assertNotNull( "Noone has an interest in Thomas Anderson", interestInThomasAnderson );
        assertTrue( "Noone has an interest in Thomas Anderson",
                interestInThomasAnderson.hasRelationship( INTERESTED_IN, Direction.INCOMING ) );
    }

    @Test
    public void theNebuchadnezzarCrewIsInterestedInFreedom()
    {
        Node interestInFreedom = theMatrix().lookupInterest( TheMatrix.FREEDOM );
        assertNotNull( "Noone is interested in Freedom", interestInFreedom );
        Set<String> crew = new HashSet<String>( Arrays.asList( TheMatrix.NEBUCHADNEZZAR_CREW ) );
        for ( Relationship interest : interestInFreedom.getRelationships( INTERESTED_IN,
                Direction.INCOMING ) )
        {
            crew.remove( interest.getStartNode().getProperty( TheMatrix.PERSON_NAME ) );
        }
        assertTrue( "Parts of the Nebuchadnezzar crew were not interested in freedom",
                crew.isEmpty() );
    }

    @Test
    public void personsAndInterestsHaveDifferentIndexes()
    {
        assertNotEqual( "Interests and persons should be indexed in different indexes",
                theMatrix().lookupInterest( TheMatrix.FREEDOM ), theMatrix().lookupPerson(
                        TheMatrix.FREEDOM ) );
    }
}
