package org.neo4j.examples.social;

import static org.junit.Assert.assertTrue;
import static org.neo4j.examples.social.SocialNetworkRelationshipTypes.INTERESTED_IN;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Relationship;

public class TestStepTwo extends StepTest
{
    @Test
    public void mrAndersonHasCommonInterestsWithMorpheusTrinityAndAgentSmith()
    {
        Set<String> personsWithCommonInterests = new HashSet<String>();
        Node neo = mrAnderson();
        for ( Relationship interest : neo.getRelationships( INTERESTED_IN, Direction.OUTGOING ) )
        {
            for ( Relationship interested : interest.getEndNode().getRelationships( INTERESTED_IN,
                    Direction.INCOMING ) )
            {
                personsWithCommonInterests.add( (String) interested.getStartNode().getProperty(
                        TheMatrix.PERSON_NAME ) );
            }
        }
        assertTrue( "Thomas Anderson did not have any interests",
                personsWithCommonInterests.contains( TheMatrix.THOMAS_ANDERSON ) );
        assertTrue( "Morpheus did not share any interests with Neo",
                personsWithCommonInterests.contains( TheMatrix.MORPHEUS ) );
        assertTrue( "Trinity did not share any interests with Neo",
                personsWithCommonInterests.contains( TheMatrix.TRINITY ) );
        assertTrue( "Agent Smith did not share any interests with Neo",
                personsWithCommonInterests.contains( TheMatrix.AGENT_SMITH ) );
    }
}
