package org.neo4j.examples.social.domain;

public interface SocialNetwork
{
    Person lookupPerson( String name );

    Person createPerson( String name );

    void shutdown();
}
