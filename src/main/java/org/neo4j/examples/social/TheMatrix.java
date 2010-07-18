package org.neo4j.examples.social;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Transaction;
import org.neo4j.index.IndexService;
import org.neo4j.index.lucene.LuceneIndexService;
import org.neo4j.kernel.EmbeddedGraphDatabase;

/**
 * Steps One through Three of the tutorial. The social network of the movie
 * "The Matrix".
 *
 * Test cases: {@link TestStepOne}, {@link TestStepTwo}, {@link TestStepThree},
 */
public class TheMatrix
{
    /** The name of Thomas Anderson */
    public static final String THOMAS_ANDERSON = "Thomas Anderson";
    /** The name of Morpheus */
    public static final String MORPHEUS = "Morpheus";
    /** The name of Trinity */
    public static final String TRINITY = "Trinity";
    /** The names of the (rest of the) Nebuchadnezzar crew */
    public static final String[] NEBUCHADNEZZAR_CREW = { "Cypher", "Apoc", "Tank", "Dozer", "Mouse" };
    /** The name of Agent Smith */
    public static final String AGENT_SMITH = "Agent Smith";
    /** The Property key to use for names of persons */
    public static final String PERSON_NAME = "name";
    /** The identifier for the interest in freedom */
    public static final String FREEDOM = "freedom";

    // Internal state of The Matrix
    // - this is all it takes to keep humanity subdued...
    final GraphDatabaseService graphDb;
    final IndexService indexes;

    /**
     * This constructor takes care of creating the Neo4j
     * {@link GraphDatabaseService}.
     *
     * @param storeDir The directory where the Neo4j data is stored.
     */
    public TheMatrix( String storeDir )
    {
        this.graphDb = new EmbeddedGraphDatabase( storeDir );
        this.indexes = new LuceneIndexService( graphDb );
    }

    /**
     * Life cycle management for the Neo4j {@link GraphDatabaseService}.
     */
    public void shutdown()
    {
        this.indexes.shutdown();
        this.graphDb.shutdown();
    }

    /**
     * Create a representation of the social network of the movie "The Matrix".
     * Use the RelationshipTypes from {@link SocialNetworkRelationshipTypes}.
     *
     * @return Thomas "Neo" Anderson
     */
    public Node createSocialNetwork()
    {
        Node thomasAnderson = null; // <- this should be assigned in step one
        Transaction tx = graphDb.beginTx();
        // Modifications to the Neo4j graph needs to be within transactions.
        // graphDb.beginTx() starts a new transaction.
        // tx.finish() completes the transaction by either commit or rollback.
        // In order for the transaction to be committed by tx.finish() it needs
        // to be marked as successful by invoking tx.success(), if tx.success()
        // is never invoked on the transaction it will be rolled back.
        // A transaction can also be explicitly marked to be rolled back by
        // invoking the tx.failure() method, this overrides previous calls to
        // tx.success() and makes subsequent calls to tx.success() no-ops.
        try
        {
            // Step One: Create the social graph of friends
            Node morpheus, trinity, cypher, apoc, tank, dozer, mouse, agentSmith;
            {
                /* TODO: Create the social network of "The Matrix",
                 * containing at least Thomas "Neo" Anderson, Morpheus,
                 * Trinity, the crew of the Nebuchadnezzar (Cypher, Apoc,
                 * Tank, Dozer and Mouse) and Agent Smith.
                 */
                // NOTE: also implement getFriendsOfFriends() for this step
            }

            // Step Two: create the interests of the people in the graph
            Node interestInFreedom, interestInThomasAnderson;
            {
                /* TODO: Define the interests of the people of "The Matrix".
                 * Thomas Anderson should have some common interest with both
                 * Morpheus and Trinity, he should also have a common interest
                 * with Agent Smith (they are not completely different after
                 * all...).
                 * Trinity should be interested in "Thomas Anderson" (an
                 * interest she shares with Persephone)...
                 * Cypher should share an interest with Agent Smith that he
                 * doesn't share with anyone else aboard the Nebuchadnezzar.
                 */
            }

            // Step Three: add indexing to persons and interests
            {
                /* TODO: index the people and interests of "The Matrix".
                 * Persons and interests should be indexed in separate indexes.
                 * All the persons and interests defined above should be
                 * present in the respective indexes. There should be a few
                 * people aboard the Nebuchadnezzar that are interested in
                 * "Freedom".
                 */
                // NOTE: also implement lookupPerson and lookupInterest
            }

            tx.success(); // <- this should always be last in the try-block
        }
        finally
        {
            tx.finish();
        }
        return thomasAnderson;
    }

    /**
     * Use the traversal features of the Neo4j Graph Database to find the
     * friends of the supplied person.
     *
     * @param person The person to return the friends of.
     * @return The friends of the supplied person, and their friends
     */
    public Iterable<Node> getFriendsOfFriends( Node person )
    {
        // TODO: recursively get the friends of the supplied person node (and
        // friends of those friends, and so on...)
        return null;
    }

    /**
     * Use the index service to retrieve a person. Return null if no person
     * exists with the given name.
     *
     * @param name the name of the person to retrieve
     * @return the Node representing the person with the given name
     */
    public Node lookupPerson( String name )
    {
        // TODO: implement this in step three
        return null;
    }

    /**
     * Use the index service to retrieve an interest node. Return null if no
     * interest exists with the given identifier.
     *
     * @param interest the identifier of the interest node to retrieve
     * @return the Node representing the interest with the given identifier
     */
    public Node lookupInterest( String interest )
    {
        // TODO: implement this in step three
        return null;
    }
}
