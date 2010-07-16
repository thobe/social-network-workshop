package org.neo4j.examples.social;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;

public class StepTwo extends TutorialStep
{
    public StepTwo( String storeDir )
    {
        super( storeDir );
    }

    public void initialize()
    {
    }

    public Node lookupPerson( String name )
    {
        return null;
    }

    @Override
    public Iterable<Path> getFriendsOfFriends( Node person )
    {
        // TODO Auto-generated method stub
        return null;
    }
}
