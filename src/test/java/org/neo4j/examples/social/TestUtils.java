package org.neo4j.examples.social;

import java.io.File;

public abstract class TestUtils
{
    private TestUtils()
    {
    }

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

}
