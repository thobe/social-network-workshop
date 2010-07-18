=====================================
 Social Network Workshop using Neo4j 
=====================================

The  purpose of  this tutorial  is  to learn  how to  use Neo4j.   The
tutorial starts  off with  the basic Neo4j  API for creating  a social
graph and performing  some simple traversals of that  graph.  The next
part that's introduced is the  concept of indexes for finding nodes in
the graph that traversals can start  from. We then move on to building
a  domain  API  for  our  social  network,  before  introducing  graph
algorithms   for   mining  more   complex   aspects   of  the   social
graph. Finally we  use the social graph model  to make recommendations
for the people in the social network.

This is  going to be  a hands-on tutorial,  where You are  expected to
write  code. The source  code provided  with this  tutorial is  a code
skeleton with blanks that you are expected to fill in.


Pre requirements
================

The first thing you are going to need is a JDK (Java Development Kit),
you can download this from the `SUN Java website`_.

The second thing  you need is to `download the  source code`_ for this
tutorial.

While  you could  compile the  sources manually  with just  javac, the
process becomes  a lot  easier using  a build tool  such as  maven_ or
ant_.  We  recommend using  maven_ since it  takes care  of dependency
management   automatically,   and  works   well   for  standard   java
projects. The rest  of this guideline is going to  assume that you are
using maven_, but if you already  feel more at home with ant_, we have
included an ant  build file for you. The  instructions will talk about
maven but using ant should be pretty straight forward.

.. _`SUN Java website`: http://java.sun.com/javase/downloads/index.jsp
.. _`download the source code`: http://github.com/thobe/
.. _maven: http://maven.apache.org/download.html
.. _ant: http://ant.apache.org/bindownload.cgi


Step One - a basic social network
=================================

The purpose of this assignment is to get a first experience with Neo4j
and its core component, the `Neo4j Graph Database Kernel`_. After this
assignment you  will be  able to  build a graph  using the  core Neo4j
Graph Database API and perform some simple traversals over that graph.

For  resources  for  this  task,  please refer  to  the  `Javadoc  API
documentation`_ for the Neo4j Graph Database Kernel. The documentation
about  the `Neo4j  Traversal  Framework`_ on  the  `Neo4j wiki`_  also
contains  some useful  information for  this task.   The rest  of this
section will give you an introduction to the core concepts of Neo4j.

The  first interface  you will  make  acquaintance with  is the  Neo4j
GraphDatabaseService_. This  is the  main interface through  which you
access the elements of the  Graph. The standard implementation of this
interface  embeds_ a Neo4j  Graph database  in your  application. From
this  instance you  can retrieve  Nodes_ from  which you  can retrieve
Relationships_ to  other Nodes_.  Both Nodes_  and Relationships_ have
methods  for  `manipulating  properties`_.   Properties on  nodes  and
relationships are  key/value pairs, the  keys can be any  strings, and
the  values can be  any `primitive  value`_. Primitive  values include
numbers (integer and floating point), Strings, boolean values (true or
false), bytes and arrays of any of the former value types.

The `Node  type`_, in addition  to the methods for  getting_, setting_
and removing_ properties,  contain methods for `creating relationships
to other  nodes`_ and retrieving  them based on types_,  direction_ or
`both qualifiers`_ (including getting `all relationships`_). Similarly
the `Relationship type`_  has methods for getting the  start_ and end_
nodes of the relationship.

All Relationships_ in Neo4j have a  type_ and a direction. The type of
the relationship has nothing to do  with data types (even if it can be
used  for  `determining  data  types`_).   Instead the  types  of  the
relationships are more like a label that is used to navigate the graph
more  efficiently. The direction  of relationships  in Neo4j  does not
mean that relationships can only be retrieved from one of the nodes of
the relationship.   Traversing a relationship is equally  fast in both
directions. The semantics of the  direction is up to your application,
if the direction of a  specific relationship is insignificant, i.e. if
you  want  an  undirected  relationship,  you can  simply  ignore  the
direction of  it, the Neo4j API  is able to treat  any relationship as
undirected.

In this task you are going  to use the features of the above mentioned
API to build  a simple social network. The social  network that we are
going to model  for this example is a small  outtake of the characters
of the  movie `The  Matrix`_.  Then use  the `traversal  features`_ of
Neo4j to compute information about that social graph.

.. _`Neo4j Graph Database Kernel`: http://components.neo4j.org/neo4j-kernel/
.. _`Javadoc API documentation`:
   http://api.neo4j.org/current/index.html?org/neo4j/graphdb/package-summary.html
.. _`Neo4j wiki`: http://wiki.neo4j.org/
.. _`Neo4j Traversal Framework`:
   http://wiki.neo4j.org/content/Traversal_Framework
.. _GraphDatabaseService:
   http://api.neo4j.org/current/org/neo4j/graphdb/GraphDatabaseService.html
.. _embeds: http://api.neo4j.org/current/org/neo4j/kernel/EmbeddedGraphDatabase.html
.. _Nodes: http://api.neo4j.org/current/org/neo4j/graphdb/Node.html
.. _`Node type`: http://api.neo4j.org/current/org/neo4j/graphdb/Node.html
.. _Relationships:
   http://api.neo4j.org/current/org/neo4j/graphdb/Relationship.html
.. _`Relationship type`:
   http://api.neo4j.org/current/org/neo4j/graphdb/Relationship.html
.. _`manipulating properties`:
   http://api.neo4j.org/current/org/neo4j/graphdb/PropertyContainer.html
.. _`primitive value`:
   http://api.neo4j.org/current/org/neo4j/graphdb/PropertyContainer.html
.. _getting:
   http://api.neo4j.org/current/org/neo4j/graphdb/PropertyContainer.html#getProperty(java.lang.String)
.. _setting:
   http://api.neo4j.org/current/org/neo4j/graphdb/PropertyContainer.html#setProperty(java.lang.String,%20java.lang.Object)
.. _removing:
   http://api.neo4j.org/current/org/neo4j/graphdb/PropertyContainer.html#removeProperty(java.lang.String)
.. _`creating relationships to other nodes`:
   http://api.neo4j.org/current/org/neo4j/graphdb/Node.html#createRelationshipTo(org.neo4j.graphdb.Node,%20org.neo4j.graphdb.RelationshipType)
.. _types:
   http://api.neo4j.org/current/org/neo4j/graphdb/Node.html#getRelationships(org.neo4j.graphdb.RelationshipType...)
.. _direction:
   http://api.neo4j.org/current/org/neo4j/graphdb/Node.html#getRelationships(org.neo4j.graphdb.Direction)
.. _`both qualifiers`:
   http://api.neo4j.org/current/org/neo4j/graphdb/Node.html#getRelationships(org.neo4j.graphdb.RelationshipType,%20org.neo4j.graphdb.Direction)
.. _`all relationships`:
   http://api.neo4j.org/current/org/neo4j/graphdb/Node.html#getRelationships()
.. _start: http://api.neo4j.org/current/org/neo4j/graphdb/Relationship.html#getStartNode()
.. _end: http://api.neo4j.org/current/org/neo4j/graphdb/Relationship.html#getEndNode()
.. _type: http://api.neo4j.org/current/org/neo4j/graphdb/RelationshipType.html
.. _`determining data types`: http://wiki.neo4j.org/content/ToDo
.. _`traversal features`: http://api.neo4j.org/current/org/neo4j/graphdb/Node.html#traverse(org.neo4j.graphdb.Traverser.Order,%20org.neo4j.graphdb.StopEvaluator,%20org.neo4j.graphdb.ReturnableEvaluator,%20java.lang.Object...)
.. _`The Matrix`: http://www.imdb.com/title/tt0133093/

Tasks
-----
* Create a graph of the social connections in The Matrix.
* Write  a traversal  for getting  the friends  of a  person,  and the
  friends of those persons.

Here is an image as an example  of what the social graph of The Matrix
could look like, feel free to add other aspects to the graph, but keep
in mind that the test cases that are provided for this exercise assume
that this graph is used.

.. _TheMatrixGraph:

.. image:: http://github.com/thobe/social-network-workshop/raw/master/doc/matrix.png
  :width: 800px
  :target: http://github.com/thobe/social-network-workshop/raw/master/doc/matrix.png

Getting the friends of Thomas Anderson in this graph would yield:

* Morpheus
* Trinity

Getting  the friends  of Thomas  Anderson's friends  recursively would
yield:

* On depth 1: Morpheus
* On depth 1: Trinity
* On depth 2: Seraph
* On depth 2: Niobe
* On depth 2: Cypher
* On depth 2: Tank
* On depth 2: Dozer
* On depth 2: Apoc
* On depth 2: Switch
* On depth 2: Mouse
* On depth 3: Ghost
* On depth 3: Lock
* ...


Step Two - Adding more Relationship types
=========================================

Different relationship types in are used for creating relationships to
nodes that represent different kinds of entities.

In our  social network our users want  to be able to  find new friends
based on shared interests. To do this we need to store the information
about each persons interests in the graph. In order to be able to find
persons with  common interests, we  represent interests as  nodes, and
the  fact  that a  specific  person has  a  particular  interest by  a
relationship  of type  "INTERESTED_IN"  from the  person  node to  the
interest  node.   This  design  allows  each person  to  have  several
interests.

If generalized to other domains,  the concept of interests in a social
network  is like  tagging.  Each  person can  have  multiple interests
(tags) and each interest can be shared by multiple persons, and we can
use the  interest nodes (or tag  nodes) to find persons  that have the
same interest.  In  fact tagging would be implemented  in the same way
when implemented for other domains as well when using Neo4j.

Tasks
-----
* Implement  the concept of  interests in  the social  network. Ensure
  that persons that share the same interests have relationships to the
  same interest  nodes. For testing,  use the interests data  from the
  `graph above`_.

.. _`graph above`: TheMatrixGraph_


Step Three - Introducing indexing to the social network
=======================================================

In  order to  traverse a  graph you  need a  starting  point. Starting
points are acquired using indexes_ in Neo4j.

More information  about how to use  indexing in Neo4j  is available in
the `API documentation` and the `Indexing wiki page`. The rest of this
section will give you an introduction to working with Neo4j indexing.

Indexing in Neo4j is done  explicitly and programatically. It is up to
you as a developer to index nodes when they are created, and to update
the indexes  when the  nodes change. This  might look like  a weakness
compared to  other database managment  systems, but it gives  you more
power and flexibility in what to index and how to index it. It is also
worth  noting that unlike  Relational databases,  where all  access is
done through  indexes, with  Neo4j indexes are  only used  for getting
start nodes  from which  a traversal can  be started.   Traversing the
graph does not use indexes, which is  why it is faster than joins in a
relational database.

A common  approach to indexing  is to index  some property of  a node.
This   is   very  similar   to   how   indexes   work  in   relational
databases. While this is simple and easy to manage, it is not strictly
necessary  .   Since  indexing  is  done  programmatically  in  Neo4j,
indexing  can be  done using  any value.   It could  be  computed from
several of the  properties on the node, it could  be properties from a
relationships the node,  it could be aggregated from  other nodes that
are related to the node, it could even be an arbitrary value.

The main  interface through which  indexing is managed is  accessed is
the IndexService_. The recommended implementation of this interface is
the one that is `based on lucene`_. For creating index entries use the
`index  method`_. Updating  an index  entry is  done by  `removing the
current index entry`_, then creating  a new.  There are two methods in
IndexService_ for  accessing indexed nodes. One method  is for getting
`all indexed nodes`_ that matches the index query, the other method is
a convenience method for when  the node is `uniquely indexed`_. A node
being uniquely indexed  means that there is at most  one node with the
given index entry.

An entry in  an index is (as  seen in the `index method`_)  made up of
not only a Node, but also a key  and a value. For the key it is common
to use  the key of  the property being  indexed, but with  some slight
modification.   Neo4j does not  have any  types for  nodes, but  it is
likely  that your  application  uses nodes  to  represent entities  of
various types. It is also quite common for entities of different types
to have  properties with the  same key, and  that is where  the slight
modification  of the key  for indexing  comes into  play. It  is often
important  for the application  to know  the type  of the  entity it's
looking up, so for properties  that are shared among multiple kinds of
nodes, the key  used in the index should be specific  to that type. If
your application  for example  represents both persons  and companies,
both of these entities might have  a name property that you would like
to be  able to use for  lookup. Then you  could let the index  key for
indexing  the name  of  a person  be  "person name"  and  the key  for
indexing the  name of a company be  "company name". Or -  if those are
the only  indexes for these  entity types -  the keys for  the indexes
could simply be "person" and "company" respectively.

In  this task  you will  use the  indexing features  for Neo4j  to add
lookup  capabilities   for  persons   and  interests  in   the  social
network. The goal is to be able  to look up persons by their name, and
to be  able to look up  the identifier nodes based  on its identifying
text representation.

.. _indexes: http://components.neo4j.org/neo4j-index
.. _`API documentation`:
   http://api.neo4j.org/current/index.html?org/neo4j/index/package-summary.html
.. _`Indexing wiki page`:
   http://wiki.neo4j.org/content/Indexing_with_IndexService
.. _IndexService: http://api.neo4j.org/current/org/neo4j/index/IndexService.html
.. _`based on lucene`:
   http://api.neo4j.org/current/org/neo4j/index/lucene/LuceneIndexService.html
.. _`index method`:
   http://api.neo4j.org/current/org/neo4j/index/IndexService.html#index(org.neo4j.graphdb.Node,%20java.lang.String,%20java.lang.Object)
.. _`removing the current index entry`:
   http://api.neo4j.org/current/org/neo4j/index/IndexService.html#removeIndex(org.neo4j.graphdb.Node,%20java.lang.String,%20java.lang.Object)
.. _`all indexed nodes`:
   http://api.neo4j.org/current/org/neo4j/index/IndexService.html#getNodes(java.lang.String,%20java.lang.Object)
.. _`uniquely indexed`:
   http://api.neo4j.org/current/org/neo4j/index/IndexService.html#getSingleNode(java.lang.String,%20java.lang.Object)


Tasks
-----
* Add indexing to  the Matrix graph to allow for  lookup of the people
  in the social network based on their name.
* Add indexing to the Social Network for looking up interest nodes.


Step Four - Introducing a domain API
====================================

It   is  time   to  start   turning   this  example   into  a   proper
application. Regardless  of how  nice the Neo4j  API is to  work with,
managing an application where all entities are of one single data type
is  a pain.  Instead  we want  to be  able to  work with  objects that
represents the entities of our domain: Persons and Interests.

The recommended way  to implement a domain using  Neo4j is by defining
the domain as a set  of interfaces, and then create implementations of
those interfaces that delegate their  state to Neo4j.  The way this is
done is  by letting  the implementing class  only have one  field, the
Node or  Relationship (depending  on what kind  of entity it  is) that
represent it in the graph. Then for each attribute accessor (Java Bean
setter or getter), the value is  retrieved and stored as a property of
the  underlying node/relationship. Associations  to other  objects are
stored  as,  and  retrieved  through  relationships  with  appropriate
RelationshipTypes.  Since  Neo4j is fully transactional  the effect of
implementing  domain objects  by  delegating state  to  Neo4j is  that
working  with  the  domain  objects  is  like  working  with  Software
Transactional Memory.

For retrieving  and creating instances of  the domain objects  it is a
good idea to define a  repository interface as well. The repository is
responsible  for   looking  up  nodes  by  index   and  returning  the
appropriate domain  objects, and for creating new  domain objects with
underlying  nodes. In  this  application the  repository interface  is
going  to  be SocialNetwork,  and  the  domain  object is  the  Person
interface.

Your  task is  now  to implement  the  domain for  the social  network
application by delegating state to Neo4j. You should be able to access
the same  graph that you have  used in the previous  steps through the
new domain  API. In  fact the test  cases for  this step also  use the
social graph of The Matrix as sample data.

Tasks
-----
* Implement the domain interfaces by delegating to Neo4j objects.


Step Five - Graph Algorithms
============================

Graph Databases excel  at deep queries and traversals,  and apart from
the  core traversal  API  Neo4j  comes with  a  package that  contains
implementations  of a few  graph algorithms  for (among  other things)
searching in  the graph. In this  task we will use  these features for
implementing  a "how  do I  know this  person" feature  in  our social
network. The "How  do I know this person" will  for two persons search
the social  graph to find the  closest chain of  friends through which
these two persons know each other.

The  `Graph Algorithms  component`_ has  API  documentation `available
online`_.   The Neo4j  graph algorithms  build on  the  `new traversal
features`_ introduced in `Neo4j version 1.1`_. The main interface used
for searching in the graph  is the PathFinder_. `Creating instances of
PathFinder`_ requires that  you provide a RelationshipExpander_, these
can  be  instantiated  using  the  `static methods  on  the  Traversal
class`_.

.. _`Graph Algorithms component`:
   http://components.neo4j.org/neo4j-graph-algo/
.. _`available online`:
   http://components.neo4j.org/neo4j-graph-algo/apidocs/index.html
.. _`new traversal features`: `Neo4j traversal framework`_
.. _`Neo4j version 1.1`:
   http://components.neo4j.org/neo4j-kernel/apidocs/index.html
.. _PathFinder:
   http://components.neo4j.org/neo4j-graph-algo/apidocs/org/neo4j/graphalgo/PathFinder.html
.. _`Creating instances of PathFinder`:
   http://components.neo4j.org/neo4j-graph-algo/apidocs/org/neo4j/graphalgo/GraphAlgoFactory.html#shortestPath(org.neo4j.graphdb.RelationshipExpander,%20int)
.. _RelationshipExpander:
   http://components.neo4j.org/neo4j-kernel/apidocs/org/neo4j/graphdb/RelationshipExpander.html
.. _`static methods on the Traversal class`:
   http://components.neo4j.org/neo4j-kernel/apidocs/org/neo4j/kernel/Traversal.html#expanderForTypes(org.neo4j.graphdb.RelationshipType,%20org.neo4j.graphdb.Direction)


Tasks
-----

* Use the Neo4j  Graph Algorithms to implement searching  for paths in
  between two people.


Step Six - Recommendations
==========================

The final part  of this tutorial is to be able  to suggest new friends
for  the  people  in  the  social  network.   We  will  use  a  simple
recommendation algorithm for this.  The algorithm you are to implement
for making friend suggestions is  simply based on finding persons that
have the same interests and recommending them to one another.

Tasks
-----
* Implement  a  simple  recommendation  algorithm for  suggesting  new
  friends  to a  person  in the  social  network. The  recommendations
  should be people in the persons extended social network that are not
  direct friends with the person.
