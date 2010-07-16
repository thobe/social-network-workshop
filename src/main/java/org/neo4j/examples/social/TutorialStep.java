package org.neo4j.examples.social;

import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.kernel.EmbeddedGraphDatabase;

abstract class TutorialStep
{
    /**
     * Property key for storing the name of a person on a {@link Node}
     *
     * @see Node#setProperty(String, Object)
     */
    public static final String NAME = "name";
    static final String THOMAS_ANDERSON = "Thomas Anderson";

    protected final EmbeddedGraphDatabase graphDb;

    public TutorialStep( String storeDir )
    {
        this.graphDb = new EmbeddedGraphDatabase( storeDir );
    }

    public void shutdown()
    {
        graphDb.shutdown();
    }

    public abstract Iterable<Path> getFriendsOfFriends( Node person );
}
