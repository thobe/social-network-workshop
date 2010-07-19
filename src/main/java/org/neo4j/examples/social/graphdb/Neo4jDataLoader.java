package org.neo4j.examples.social.graphdb;

import static org.neo4j.examples.social.SocialNetworkRelationshipTypes.FRIENDS;
import static org.neo4j.examples.social.SocialNetworkRelationshipTypes.INTERESTED_IN;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.neo4j.examples.social.domain.DataLoader;
import org.neo4j.index.lucene.LuceneIndexBatchInserterImpl;
import org.neo4j.kernel.impl.batchinsert.BatchInserterImpl;

public final class Neo4jDataLoader extends DataLoader
{
    private final BatchInserterImpl inserter;
    private final Map<String, Long> persons = new HashMap<String, Long>(),
            interests = new HashMap<String, Long>();
    private final LuceneIndexBatchInserterImpl indexer;

    public Neo4jDataLoader( String storeDir )
    {
        this.inserter = new BatchInserterImpl( storeDir );
        this.indexer = new LuceneIndexBatchInserterImpl( inserter );
    }

    private long person( String name )
    {
        Long id = persons.get( name );
        if ( id == null )
        {
            id = inserter.createNode( Collections.<String, Object>singletonMap(
                    PersonImpl.PERSON_NAME, name ) );
            persons.put( name, id );
            indexer.index( id, Neo4jSocialNetwork.PERSON_INDEX, name );
        }
        return id;
    }

    private long interest( String name )
    {
        Long id = interests.get( name );
        if ( id == null )
        {
            id = inserter.createNode( Collections.<String, Object>singletonMap(
                    Neo4jSocialNetwork.INTEREST, name ) );
            interests.put( name, id );
            indexer.index( id, Neo4jSocialNetwork.INTEREST, name );
        }
        return id;
    }

    @Override
    protected void friends( String one, String other )
    {
        inserter.createRelationship( person( one ), person( other ), FRIENDS, null );
    }

    @Override
    protected void interest( String person, String interest )
    {
        inserter.createRelationship( person( person ), interest( interest ), INTERESTED_IN,
                null );
    }

    @Override
    public void done()
    {
        indexer.shutdown();
        inserter.shutdown();
    }
}
