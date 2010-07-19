package org.neo4j.examples.social;

import java.io.File;
import java.net.URISyntaxException;

import org.junit.Ignore;

@Ignore
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

    public static File resourceFile( String name )
    {
        try
        {
            return new File( TestUtils.class.getResource( name ).toURI() );
        }
        catch ( URISyntaxException e )
        {
            throw new RuntimeException( "JDK Failure", e );
        }
    }
}
