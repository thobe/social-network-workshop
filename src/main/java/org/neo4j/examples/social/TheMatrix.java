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
    public static final String[] NEBUCHADNEZZAR_CREW = { "Cypher", "Apoc", "Tank", "Dozer",
            "Mouse", "Switch" };
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
            thomasAnderson = createPersonNode( THOMAS_ANDERSON );
            Node morpheus = createPersonNode( MORPHEUS );
            Node trinity = createPersonNode( TRINITY );
            Node agentSmith = createPersonNode( AGENT_SMITH );
            Node agentBrown = createPersonNode( "Agent Brown" );
            Node agentJones = createPersonNode( "Agent Jones" );
            Node apoc = createPersonNode( "Apoc" );
            Node cypher = createPersonNode( "Cypher" );
            Node dozer = createPersonNode( "Dozer" );
            Node ghost = createPersonNode( "Ghost" );
            Node kali = createPersonNode( "Kali" );
            Node ladyInRed = createPersonNode( "Lady in Red" );
            Node link = createPersonNode( "Link" );
            Node lock = createPersonNode( "Lock" );
            Node merovingian = createPersonNode( "Merovingian" );
            Node mouse = createPersonNode( "Mouse" );
            Node niobe = createPersonNode( "Niobe" );
            Node persephone = createPersonNode( "Persephone" );
            Node seraph = createPersonNode( "Seraph" );
            Node spoonBoy = createPersonNode( "Spoon Boy" );
            Node _switch = createPersonNode( "Switch" );
            Node tank = createPersonNode( "Tank" );
            Node theArchitect = createPersonNode( "The Architect" );
            Node theKeymaker = createPersonNode( "The Keymaker" );
            Node theOracle = createPersonNode( "The Oracle" );
            Node theTrainman = createPersonNode( "The Trainman" );
            Node zee = createPersonNode( "Zee" );
            // Connect the people as friends
            makeFriends( thomasAnderson, morpheus );
            makeFriends( thomasAnderson, trinity );
            makeFriends( trinity, morpheus );
            makeFriends( morpheus, tank );
            makeFriends( morpheus, dozer );
            makeFriends( morpheus, apoc );
            makeFriends( morpheus, cypher );
            makeFriends( morpheus, mouse );
            makeFriends( morpheus, _switch );
            makeFriends( morpheus, seraph );
            makeFriends( morpheus, niobe );
            makeFriends( cypher, apoc );
            makeFriends( cypher, tank );
            makeFriends( cypher, dozer );
            makeFriends( cypher, mouse );
            makeFriends( cypher, _switch );
            makeFriends( cypher, agentSmith );
            makeFriends( apoc, tank );
            makeFriends( apoc, dozer );
            makeFriends( apoc, mouse );
            makeFriends( apoc, _switch );
            makeFriends( tank, dozer );
            makeFriends( tank, mouse );
            makeFriends( tank, _switch );
            makeFriends( tank, zee );
            makeFriends( dozer, mouse );
            makeFriends( dozer, _switch );
            makeFriends( dozer, zee );
            makeFriends( dozer, kali );
            makeFriends( mouse, _switch );
            makeFriends( mouse, ladyInRed );
            makeFriends( zee, link );
            makeFriends( niobe, lock );
            makeFriends( niobe, ghost );
            makeFriends( agentSmith, agentBrown );
            makeFriends( agentSmith, agentJones );
            makeFriends( agentBrown, agentJones );
            makeFriends( theOracle, theArchitect );
            makeFriends( theOracle, seraph );
            makeFriends( theOracle, spoonBoy );
            makeFriends( merovingian, persephone );
            makeFriends( merovingian, theKeymaker );
            makeFriends( merovingian, theTrainman );

            // Step Two: create the interests of the people in the graph
            Node annihilationOfHumans = createInterestNode( "Annihilation of humans" );
            Node war = createInterestNode( "War" );
            Node zion = createInterestNode( "Zion" );
            Node power = createInterestNode( "Power" );
            Node keys = createInterestNode( "Keys" );
            Node whiteClothes = createInterestNode( "White clothes" );
            Node betrayal = createInterestNode( "Betrayal" );
            Node ignorance = createInterestNode( "Ignorance" );
            Node understanding = createInterestNode( "Understanding" );
            Node protection = createInterestNode( "Protection" );
            Node bendingSpoons = createInterestNode( "Bending spoons" );
            Node destruction = createInterestNode( "Destruction" );
            Node freedom = createInterestNode( "Freedom" );
            Node hacking = createInterestNode( "Hacking" );
            Node control = createInterestNode( "Control" );
            Node love = createInterestNode( "Love" );
            Node theTruth = createInterestNode( "The Truth" );
            Node illuminati = createInterestNode( "Illuminati" );
            Node creation = createInterestNode( "Creation" );
            Node theOne = createInterestNode( "The One" );
            Node sex = createInterestNode( "Sex" );
            Node theFuture = createInterestNode( "The future" );
            // Associate the interests with people
            addInterest( thomasAnderson, theTruth );
            addInterest( thomasAnderson, theFuture );
            addInterest( thomasAnderson, hacking );
            addInterest( thomasAnderson, understanding );
            addInterest( trinity, hacking );
            addInterest( trinity, thomasAnderson );
            addInterest( morpheus, theTruth );
            addInterest( morpheus, theOne );
            addInterest( morpheus, zion );
            addInterest( morpheus, keys );
            addInterest( cypher, ignorance );
            addInterest( cypher, freedom );
            addInterest( cypher, betrayal );
            addInterest( apoc, freedom );
            addInterest( tank, freedom );
            addInterest( tank, zion );
            addInterest( dozer, freedom );
            addInterest( dozer, zion );
            addInterest( mouse, sex );
            addInterest( mouse, freedom );
            addInterest( _switch, freedom );
            addInterest( _switch, whiteClothes );
            addInterest( ladyInRed, sex );
            addInterest( ladyInRed, illuminati );
            addInterest( kali, love );
            addInterest( kali, zion );
            addInterest( zee, zion );
            addInterest( zee, love );
            addInterest( niobe, zion );
            addInterest( niobe, love );
            addInterest( niobe, war );
            addInterest( lock, zion );
            addInterest( lock, war );
            addInterest( link, zion );
            addInterest( ghost, zion );
            addInterest( agentSmith, power );
            addInterest( agentSmith, control );
            addInterest( agentSmith, understanding );
            addInterest( agentSmith, betrayal );
            addInterest( agentSmith, annihilationOfHumans );
            addInterest( agentBrown, control );
            addInterest( agentJones, control );
            addInterest( persephone, thomasAnderson );
            addInterest( persephone, understanding );
            addInterest( spoonBoy, bendingSpoons );
            addInterest( theOracle, theFuture );
            addInterest( theOracle, creation );
            addInterest( theOracle, theOne );
            addInterest( seraph, protection );
            addInterest( theArchitect, theFuture );
            addInterest( theArchitect, creation );
            addInterest( theArchitect, destruction );
            addInterest( merovingian, destruction );
            addInterest( merovingian, power );
            addInterest( theKeymaker, keys );
            addInterest( theTrainman, control );

            tx.success(); // <- this should always be last in the try-block
        }
        finally
        {
            tx.finish();
        }
        return thomasAnderson;
    }

    private Node createPersonNode( String name )
    {
        // TODO: implement in Step 1, update in Step 3
        return null;
    }

    private void makeFriends( Node person1, Node person2 )
    {
        // TODO: implement in Step 1, update in Step 3
    }

    private Node createInterestNode( String interest )
    {
        // TODO: implement in Step 2, update in Step 3
        return null;
    }

    private void addInterest( Node person, Node interest )
    {
        // TODO: implement in Step 2, update in Step 3
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
