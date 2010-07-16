=====================================
 Social Network Workshop using Neo4j
=====================================

The purpose of this tutorial is to learn how to use Neo4j. The
tutorial starts off with the basic Neo4j API for creating a social
graph and performing some simple traversals of that graph. The next
part that's introduced is the concept of indexes for finding nodes in
the graph that traversals can start from. We then move on to building
a domain API for our social network, before introducing graph
algorithms for mining more complex aspects of the social
graph. Finally we use the social graph model to make recommendations
for the people in the social network.

This is going to be a hands-on tutorial, where You are expected to
write code. The source code provided with this tutorial is a code
skeleton with blanks that you are expected to fill in.



Prerequirements
===============

The first thing you are going to need is a JDK (Java Development Kit),
you can download this from the `SUN Java website`_.

The second thing you need is to `download the source code`_ for this
tutorial.

While you could compile the sources manually with just javac, the
process becomes a lot easier using a build tool such as maven_ or
ant_. We recommend using maven_ since it takes care of dependency
management automatically, and works well for standard java
projects. The rest of this guideline is going to assume that you are
using maven_, but if you already feel more at home with ant_, we have
included an ant build file for you. The instructions will talk about
maven but using ant should be pretty straight forward.

.. _`SUN Java website`: http://java.sun.com/javase/downloads/index.jsp
.. _`download the source code`: http://github.com/thobe/
.. _maven: http://maven.apache.org/download.html
.. _ant: http://ant.apache.org/bindownload.cgi


Step One - a basic social network
=================================

The purpose of this assignment is to get a first experience with Neo4j
and its core component, the `Neo4j Graph Database Kernel`_. After this
assignment you will be able to build a graph using the core Neo4j
Graph Database API and perform some simple traversals over that graph.

For interesting resources for this task, please refer to the `Javadoc
API documentation`_ for the Neo4j Graph Database Kernel. The
documentation about the `Neo4j Traversal Framework`_ on the `Neo4j
wiki`_ also contains some useul information for this task.

.. _`Neo4j Graph Database Kernel`: http://components.neo4j.org/neo4j-kernel/
.. _`Javadoc API documentation`:
   http://components.neo4j.org/neo4j-kernel/apidocs/index.html?org/neo4j/graphdb/package-summary.html
.. _`Neo4j wiki`: http://wiki.neo4j.org/
.. _`Neo4j Traversal Framework`:
   http://wiki.neo4j.org/content/Traversal_Framework

Tasks
-----
* Create a graph of the social connections in The Matrix
* Write a traversal for getting the friends of a person, and the
  friends of those persons

.. Charactors from The Matrix include:
   * Thomas Anderson a.k.a. Neo
   * Morpheus
   * Trinity
   * Agent Smith
   * The Oracle
   * Cypher a.k.a. Mr. Reagan
   * Tank (born free)
   * Dozer (born free)
   * Apoc
   * Mouse
   * Switch
   * Agent Brown
   * Agent Jones
   * Spoon Boy
   * Woman in Red (programmed by Mouse)
   * The Architect
   * Kali (Dozer's wife)
   * Persephone (Merovingian wife)
   * The Keymaker
   * Merovingian (holds the keymaker)
   * Lock (Niobes bf)
   * Niobe (Morpheus ex)
   * Link (married to Zee)
   * Ghost (Niobe's right hand)
   * Seraph (guardian of the oracle)
   * Zee (Tank & Dozer's sister)
   * The Trainman (employee of Merovingian)


Step Two - Introducing indexing to the social network
=====================================================

Tasks
-----
* Add indexing to the Matrix graph to allow for lookup of the people
  in the social network.



Step Three - Introducing a domain API
=====================================

Tasks
-----
* Implement the domain interfaces by delegating to Neo4j objects.
* Use the Neo4j Graph Algorithms to implement searching for paths in
  between two people.



Step Four - Recommendations
===========================

Tasks
-----
* Implement a simple recommendation algorithm for suggesting new
  friends to a person in the social network. The recommendations
  should be people in the persons extended social network that are not
  direct friends with the person.
