package org.neo4j.examples.social;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;

import org.junit.Before;
import org.junit.Test;
import org.neo4j.examples.social.domain.DataLoader;
import org.neo4j.examples.social.domain.FriendPath;
import org.neo4j.examples.social.domain.Person;
import org.neo4j.examples.social.domain.SocialNetwork;
import org.neo4j.examples.social.graphdb.Neo4jDataLoader;
import org.neo4j.examples.social.graphdb.Neo4jSocialNetwork;

public class TestDataLoader
{
    private static final String DB_PATH = "target/flintstones";

    @Before
    public void clearDb()
    {
        TestUtils.deleteDir( DB_PATH );
    }

    @Test
    public void canLoadTheFlintstones() throws Exception
    {
        DataLoader loader = new Neo4jDataLoader( DB_PATH );
        try
        {
            loader.load( new File( getClass().getResource( "/flintstones.data" ).toURI() ) );
        }
        finally
        {
            loader.done();
        }

        SocialNetwork flintstones = new Neo4jSocialNetwork( DB_PATH );
        try
        {
            Person fred = flintstones.lookup( "Fred Flintstone" );
            assertNotNull( "could not get Fred", fred );
            Person betty = flintstones.lookup( "Betty Rubble" );
            assertNotNull( "could not get Betty", betty );
            FriendPath path = fred.getPath( betty );
            assertNotNull( "could not find out how Fred and Betty know each other", path );
            assertEquals( 3, path.length() );
            System.out.println( path );
        }
        finally
        {
            flintstones.shutdown();
        }
    }
}
