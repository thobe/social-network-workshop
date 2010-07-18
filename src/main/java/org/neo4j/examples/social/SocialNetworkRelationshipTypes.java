package org.neo4j.examples.social;

import org.neo4j.graphdb.RelationshipType;

public enum SocialNetworkRelationshipTypes implements RelationshipType
{
    FRIENDS,
    INTERESTED_IN
}
