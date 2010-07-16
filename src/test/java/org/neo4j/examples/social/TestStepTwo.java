package org.neo4j.examples.social;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestStepTwo extends StepTest
{
    @Test
    public void canGetFriendsOfFriends()
    {
        testFoaf( step.lookupPerson( TutorialStep.THOMAS_ANDERSON ) );
    }

    private static final String STORE_DIR = "target/steptwo";
    private static StepTwo step;

    @BeforeClass
    public static void init()
    {
        TestUtils.deleteDir( STORE_DIR );
        step = new StepTwo( STORE_DIR );
        step.initialize();
    }

    @AfterClass
    public static void shutdown()
    {
        step.shutdown();
        step = null;
    }

    @Override
    StepTwo step()
    {
        return step;
    }
}
