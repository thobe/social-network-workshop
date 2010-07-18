package org.neo4j.examples.social;

import java.io.File;

import org.junit.Ignore;
import org.neo4j.examples.social.graphdb.Neo4jSocialNetwork;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.index.IndexService;

@Ignore
public abstract class TestUtils
{
    public static void deleteDir( String path )
    {
        recursiveDelete( new File( path ) );
    }

    public static void recursiveDelete( File file )
    {
        if ( file.isDirectory() )
        {
            for ( File child : file.listFiles() )
            {
                recursiveDelete( child );
            }
        }
        file.delete();
    }

    protected final Neo4jSocialNetwork socialNetworkOf( TheMatrix theMatrix )
    {
        return createSocialNetwork( theMatrix.graphDb, theMatrix.indexes );
    }

    protected abstract Neo4jSocialNetwork createSocialNetwork( GraphDatabaseService graphDb,
            IndexService indexes );
}
