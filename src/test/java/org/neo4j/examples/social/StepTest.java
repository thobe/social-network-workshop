package org.neo4j.examples.social;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;

abstract class StepTest
{
    abstract TutorialStep step();

    void testFoaf( Node neo )
    {
        Set<String> found = new HashSet<String>();
        for ( Path foaf : step().getFriendsOfFriends( neo ) )
        {
            String name = (String) foaf.endNode().getProperty( StepOne.NAME );
            if ( !found.add( name ) )
            {
                System.err.println( name + " returned twice" );
            }
        }
    }
}
