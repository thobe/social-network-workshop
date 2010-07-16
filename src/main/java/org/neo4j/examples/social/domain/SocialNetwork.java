package org.neo4j.examples.social.domain;

public interface SocialNetwork
{
    Person lookup( String name );

    Person create( String name );

    void shutdown();
}
