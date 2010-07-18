package org.neo4j.examples.social;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.neo4j.graphdb.Node;

abstract class StepTest
{
    private static final String STORE_DIR = "target/thematrix";
    private static TheMatrix theMatrix;
    private static Node mrAnderson;

    @BeforeClass
    public static void init()
    {
        TestUtils.deleteDir( STORE_DIR );
        theMatrix = new TheMatrix( STORE_DIR );
        mrAnderson = theMatrix.createSocialNetwork();
    }

    @AfterClass
    public static void shutdown()
    {
        mrAnderson = null;
        theMatrix.shutdown();
        theMatrix = null;
    }

    TheMatrix theMatrix()
    {
        return theMatrix;
    }

    Node mrAnderson()
    {
        return mrAnderson;
    }

    static void testFoaf( Node neo )
    {
        int emergencyBreak = 100;
        HashSet<String> friends = new HashSet<String>( Arrays.asList( TheMatrix.MORPHEUS,
                TheMatrix.TRINITY, TheMatrix.AGENT_SMITH ) );
        friends.addAll( Arrays.asList( TheMatrix.NEBUCHADNEZZAR_CREW ) );
        @SuppressWarnings( "unchecked" ) Set<String> expectedFriends = (Set<String>) friends.clone();
        Iterable<Node> friendsOfFriends = theMatrix.getFriendsOfFriends( neo );
        assertNotNull( "Could not get friends of friends! "
                       + "TheMatrix.getFriendsOfFriends() returned null, "
                       + "is it not implemented?", friendsOfFriends );
        for ( Node friend : friendsOfFriends )
        {
            String name = (String) friend.getProperty( TheMatrix.PERSON_NAME );
            if ( !expectedFriends.remove( name ) )
            {
                if ( friends.contains( name ) )
                {
                    System.out.println( name + " was found as a friend more than once." );
                }
                else if ( TheMatrix.THOMAS_ANDERSON.equals( name ) )
                {
                    System.out.println( "Thomas Anderson is his own (transitive) friend." );
                }
                else
                {
                    System.out.println( "More friends have joined the party: " + name );
                }
            }
            if ( --emergencyBreak == 0 ) fail( "Iteration over friends does not seem to stop" );
        }
        assertTrue( "Some of the expected friends were not found: " + expectedFriends,
                expectedFriends.isEmpty() );
    }

    static void assertNotEqual( String message, Object unexpected, Object actual )
    {
        if ( actual == unexpected )
        {
            throw new AssertionError( message );
        }
        else if ( unexpected != null && actual != null )
        {
            if ( unexpected.equals( actual ) ) throw new AssertionError( message );
        }
    }

    static void assertIsNamed( String name, Node person )
    {
        assertEquals( name, person.getProperty( TheMatrix.PERSON_NAME ) );
    }
}
