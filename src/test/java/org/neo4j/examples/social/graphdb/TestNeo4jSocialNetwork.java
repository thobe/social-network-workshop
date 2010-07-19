package org.neo4j.examples.social.graphdb;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.neo4j.examples.social.TestUtils;
import org.neo4j.examples.social.domain.DataLoader;
import org.neo4j.examples.social.domain.SocialNetwork;
import org.neo4j.examples.social.impl.Neo4jDataLoader;
import org.neo4j.examples.social.impl.Neo4jSocialNetwork;

@Ignore
public class TestNeo4jSocialNetwork
{
    private static final String STORE_DIR = "target/socnet";
    private static Neo4jSocialNetwork socialNet;

    protected SocialNetwork socnet()
    {
        return socialNet;
    }

    @BeforeClass
    public static void setupMatrixTestGraph() throws Exception
    {
        TestUtils.deleteDir( STORE_DIR );
        DataLoader loader = new Neo4jDataLoader( STORE_DIR );
        try
        {
            loader.load( TestUtils.resourceFile( "/matrix.data" ) );
        }
        finally
        {
            loader.done();
        }
        socialNet = new Neo4jSocialNetwork( STORE_DIR );
    }

    @AfterClass
    public static void shutdownSocialNetwork()
    {
        socialNet.shutdown();
        socialNet = null;
    }
}
