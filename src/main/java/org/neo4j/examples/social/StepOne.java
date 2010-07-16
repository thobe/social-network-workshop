package org.neo4j.examples.social;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;

public class StepOne extends TutorialStep
{
    public StepOne( String storeDir )
    {
        super( storeDir );
    }

    public Node initialize()
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
