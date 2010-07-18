package org.neo4j.examples.social.graphdb;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.neo4j.examples.social.TestUtils;
import org.neo4j.examples.social.TheMatrix;
import org.neo4j.examples.social.domain.SocialNetwork;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.index.IndexService;

@Ignore
public class TestNeo4jSocialNetwork extends TestUtils
{
    private static final String STORE_DIR = "target/thematrix";
    private static Neo4jSocialNetwork socialNet;

    protected SocialNetwork socnet()
    {
        return socialNet;
    }

    @BeforeClass
    public static void setupMatrixTestGraph()
    {
        deleteDir( STORE_DIR );
        socialNet = new TestNeo4jSocialNetwork().setupTheMatrix( STORE_DIR );
    }

    @AfterClass
    public static void shutdownSocialNetwork()
    {
        socialNet.shutdown();
        socialNet = null;
    }

    private Neo4jSocialNetwork setupTheMatrix( String storeDir )
    {
        return socialNetworkOf( new TheMatrix( storeDir ) );
    }

    @Override
    protected final Neo4jSocialNetwork createSocialNetwork( GraphDatabaseService graphDb,
            IndexService indexes )
    {
        return new Neo4jSocialNetwork( graphDb, indexes );
    }
}
