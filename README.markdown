# algorithms-and-data-structures

[![Build Status](https://travis-ci.org/CarloMicieli/algorithms-and-data-structures.png?branch=master)](https://travis-ci.org/CarloMicieli/algorithms-and-data-structures)
[![Coverage Status](https://coveralls.io/repos/CarloMicieli/algorithms-and-data-structures/badge.svg?branch=master&service=github)](https://coveralls.io/github/CarloMicieli/algorithms-and-data-structures?branch=master)

Data structures and algorithms implementations with the Scala programming language.

To run the tests with `sbt`:

    $ sbt test

To import the project in Intellij Idea:

    $ sbt gen-idea

## Sorting Algorithms

| Name              |   stable  |  in place |           |           |  
| ----------------- | --------- | --------- | --------- | --------- |
| BubbleSort [1]    |    no     |   yes     |   Θ(n²)   |    O(n²)  |
| InsertionSort     |    yes    |   yes     |   Θ(n²)   |    O(n²)  |
| SelectionSort     |    no     |   yes     |   Θ(n²)   |    O(n²)  |
| ShellSort [2]     |    no     |   yes     |   Θ(n<sup>3/2</sup>) |    O(n²)  |



| Name              |        16 |       256 |     4.096 |    32.768 |    65.566 |   262.144 |   524.288 | 1.048.576 |
| ----------------- | --------- | --------- | --------- | --------- | --------- | --------- | --------- | --------- |
| BubbleSort [1]    |      2 ms |     18 ms |    166 ms |     8 sec |    44 sec |    10 min |    47 min |    --     |
| InsertionSort     |      0 ms |      6 ms |    154 ms |     5 sec |    22 sec |           |           |    --     |
| SelectionSort     |      0 ms |     17 ms |    295 ms |    10 sec |           |           |           |    --     |
| ShellSort [2]     |      0 ms |      6 ms |     22 ms |     42 ms |     72 ms |    295 ms |    659 ms |     1 sec |


[1]: implementation with _"early exit"_
[2]: implementation with the Knuth's interval sequence

Machine used for the benchmark
* Intel® Core™ i5-3570K CPU @ 3.40GHz × 4
* 16 Gb RAM
* SSD hard disk
* Ubuntu 14.04.3
* Java SE 1.8u60 / Scala 2.11.7

## Data structures

### `Maybe`

The `Maybe` type encapsulates an optional value. A value of type `Maybe[A]` either contains a value of type a 
(represented as `Just[A]`), or it is empty (represented as `None`).

```scala
trait Maybe[+A] {
  def get: A
  def getOrElse(default: => A): A
  def orElse(that: => Maybe[A]): Maybe[A]
  def isDefined: Boolean
  def isEmpty: Boolean
  def foreach(f: A => Unit): Unit
  def map(f: A => A): Maybe[A]
  def flatMap(f: A => Maybe[A]): Maybe[A]
  def filter(p: A => Boolean): Maybe[A]
  def toGood[B](bad: => B): Or[A, B]
}
```

### `Or`

__full disclaimer:__ API borrowed from the type with the same name available in scalactic.

```scala
trait Or[+A, +B] {
  def isBad: Boolean
  def isGood: Boolean
  def get: A
  def map[C](f: A => C): Or[C, B]
  def flatMap[C, D >: B](f: A => Or[C, D]): Or[C, D]
  def orElse[D](v: => D): Or[A, D]
  def toMaybe[A]: Maybe[A]
}
```

### `Tree`

It represents a binary search tree.
A binary tree is:
* either an empty node;
* or a node contains 3 parts, a value, two children which are also trees.

```scala
trait Tree[+K, +V] {
  def get: (K, V)
  def depth: Int
  def max: Maybe[K]
  def min: Maybe[K]
  def size: Int
  def isEmpty: Boolean
  def lookup(key: K): Maybe[(K, V)]
  def upsert(key: K, value: V)(f: V => V): Tree[K, V]
  def insert(key: K, value: V): Tree[K, V]
  def delete(key: K): (Maybe[V], Tree[K, V])
  def toList: List[(K, V)]
}
```


References
----------

* __Robert Sedgewick, Kevin Wayne__, `Algorithms, 4th Edition`, 2011, Addison-Wesley Professional
* __Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein__, `Introduction to Algorithms, 3rd Edition`, 2009, Mit Press
* __Paul Chiusano and Rúnar Bjarnason__, `Functional Programming in Scala`, 2014, Manning Publications
