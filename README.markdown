# algorithms-and-data-structures

[![Build Status](https://travis-ci.org/CarloMicieli/algorithms-and-data-structures.png?branch=master)](https://travis-ci.org/CarloMicieli/algorithms-and-data-structures)

Data structures and algorithms implementations with the Scala programming language.

To run the tests with `sbt`:

    $ sbt test

To import the project in Intellij Idea:

    $ sbt gen-idea

## Algorithms

| Algorithm     |        16 |       256 |     4.096 |    32.768 |    65.566 |   262.144 |   524.288 | 1.048.576 |
| ------------- | --------- | --------- | --------- | --------- | --------- | --------- | --------- | --------- |
| BubbleSort    |       2ms |      18ms |     166ms |     8 sec |    36 sec |    10 min |     --    |    --     |


Machine used for the benchmark
* Intel® Core™ i5-3570K CPU @ 3.40GHz × 4
* 16 Gb RAM
* SSD hard disk
* Ubuntu 14.04.1
* Java SE 1.8u11 / Scala 2.11.2

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
