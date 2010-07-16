package org.neo4j.examples.social.graphdb;

import org.neo4j.graphdb.RelationshipType;

enum SocialTypes implements RelationshipType
{
    FRIEND,
    INTEREST
}
