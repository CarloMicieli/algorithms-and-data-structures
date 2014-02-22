algorithms-and-data-structures
==============================

Data structures and algorithms implementations with the Scala programming language.

To run the tests with `sbt`:

    sbt test

To import the project in Intellij Idea:

    sbt gen-idea


Data structures
---------------

* `Union-Find`;

| Algorithm     | Initialize  | Union     | Connected |
| _____________ | ----------- | --------- | --------- |
| quick find    | O(n)        | O(n)      | O(1)      |
| quick union   | O(n)        | O(n)      | O(n)      |
| weighted      | O(n)        | O(lgn)    | O(lgn)    |


References
----------

__Robert Sedgewick, Kevin Wayne__, `Algorithms, 4th Edition`, 2011, Addison-Wesley Professional