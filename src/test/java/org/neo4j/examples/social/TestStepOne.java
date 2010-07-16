package org.neo4j.examples.social;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.neo4j.graphdb.Node;

public class TestStepOne extends StepTest
{
    @Test
    public void canGetFriendsOfFriends()
    {
        testFoaf( root );
    }

    private static final String STORE_DIR = "target/stepone";
    private static StepOne step;
    private static Node root;

    @BeforeClass
    public static void init()
    {
        TestUtils.deleteDir( STORE_DIR );
        step = new StepOne( STORE_DIR );
        root = step.initialize();
    }

    @AfterClass
    public static void shutdown()
    {
        root = null;
        step.shutdown();
        step = null;
    }

    @Override
    StepOne step()
    {
        return step;
    }
}
