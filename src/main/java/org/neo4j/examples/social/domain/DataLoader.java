package org.neo4j.examples.social.domain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public abstract class DataLoader<TX>
{
    public final void load( File datafile ) throws IOException
    {
        if ( datafile.isDirectory() )
        {
            for ( File file : datafile.listFiles() )
            {
                load( file );
            }
        }
        else if ( datafile.isFile() )
        {
            load( new BufferedReader( new FileReader( datafile ) ) );
        }
    }

    private void load( BufferedReader reader ) throws IOException
    {
        TX tx = txBegin();
        try
        {
            String line;
            while ( ( line = reader.readLine() ) != null )
            {
                String[] parts = line.trim().split( ";" );
                if ( parts.length != 0 )
                {
                    String command = parts[0].trim().toUpperCase();
                    try
                    {
                        if ( !"".equals( command ) )
                        {
                            Command.valueOf( command ).dispatch( this, parts );
                        }
                    }
                    catch ( IllegalArgumentException ex )
                    {
                        log( "unknown command: " + parts[0] );
                    }
                }
            }
            txSuccessful( tx );
        }
        finally
        {
            txCompleted( tx );
        }
    }

    protected final void log( String string )
    {
        System.err.println( string );
    }

    protected abstract void friends( String one, String other );

    protected abstract void interest( String person, String interest );

    private enum Command
    {
        FRIENDS( 2 )
        {
            @Override
            void apply( DataLoader loader, String[] args )
            {
                loader.friends( args[0], args[1] );
            }
        },
        INTEREST( 2 )
        {
            @Override
            void apply( DataLoader loader, String[] args )
            {
                loader.interest( args[0], args[1] );
            }
        },
        PROPERTY( 3 )
        {
            @Override
            void apply( DataLoader loader, String[] args )
            {
            }
        };
        private final int argCount;

        private Command( int argCount )
        {
            this.argCount = argCount;
        }

        void dispatch( DataLoader loader, String... parts )
        {
            if ( parts.length != argCount + 1 )
            {
                loader.log( "Illegal command format: " + Arrays.toString( parts ) );
                return;
            }
            String[] args = new String[argCount];
            for ( int i = 1; i <= argCount; i++ )
            {
                args[i - 1] = parts[i].trim();
            }
            apply( loader, args );
        }

        abstract void apply( DataLoader loader, String[] args );
    }

    protected TX txBegin()
    {
        return null;
    }

    protected void txSuccessful( TX tx )
    {
    }

    protected void txCompleted( TX tx )
    {
    }

    public void done()
    {
    }
}
