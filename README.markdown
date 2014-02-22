# algorithms-and-data-structures

[![Build Status](https://travis-ci.org/CarloMicieli/algorithms-and-data-structures.png?branch=master)](https://travis-ci.org/CarloMicieli/algorithms-and-data-structures)

Data structures and algorithms implementations with the Scala programming language.

To run the tests with `sbt`:

    $ sbt test

To import the project in Intellij Idea:

    $ sbt gen-idea


## Data structures

### Union/Find
Used for dynamic connectivity problems.

| Algorithm     | Initialize  | Union     | Connected |
| ------------- | ----------- | --------- | --------- |
| quick find    | O(n)        | O(n)      | O(1)      |
| quick union   | O(n)        | O(n)      | O(n)      |
| weighted      | O(n)        | O(lgn)    | O(lgn)    |

References
----------

* __Robert Sedgewick, Kevin Wayne__, `Algorithms, 4th Edition`, 2011, Addison-Wesley Professional
* __Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein__, `Introduction to Algorithms, 3rd Edition`, 2009, Mit Press
* __Paul Chiusano and Rúnar Bjarnason__, `Functional Programming in Scala (MEAP)`, 2014, Manning Publications
