# algorithms-and-data-structures

[![Build Status](https://travis-ci.org/CarloMicieli/algorithms-and-data-structures.png?branch=master)](https://travis-ci.org/CarloMicieli/algorithms-and-data-structures)
[![Coverage Status](https://coveralls.io/repos/CarloMicieli/algorithms-and-data-structures/badge.svg?branch=master&service=github)](https://coveralls.io/github/CarloMicieli/algorithms-and-data-structures?branch=master)

Data structures and algorithms implementations with the Scala programming language.

    $ git clone https://github.com/CarloMicieli/algorithms-and-data-structures.git

To run the test suite:

    $ sbt test

To generate the `Scaladoc` documentation:

    $ sbt doc

Documentation will be available under `./target/scala-2.11/api/`.


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

The `Maybe` type encapsulates an optional value. A value of type `Maybe[A]` either contains a value of type `A` 
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

Represents a value that is one of two possible types, with one type being `Good[A]` and the other `Bad[B]`.

__full disclaimer:__ API borrowed from the type with the same name available in 
[Scalactic](http://doc.scalatest.org/2.2.4/org/scalactic/Or.html).

```scala
trait Or[+G, +B] {
  def isBad: Boolean
  def isGood: Boolean
  def get: G
  def getOrElse(default: => G): G
  def exists(p: G => Boolean): Boolean
  def foreach(f: G => Unit): Unit
  def map[G1](f: G => G1): Or[G1, B]
  def flatMap[G1](f: G => Or[G1, B]): Or[G1, B]
  def orElse[D](v: => D): Or[A, D]
  def zip[G1, B1 >: B](that: Or[G1, B1]): Or[(G, G1), List[B1]]
  def toMaybe[A]: Maybe[A]
}
```

### `List`

A list is either empty, or a constructed list with a `head` and a `tail`.

```scala
trait List[+A] {
  def head: A                                   // O(1)
  def headOption: Maybe[A]                      // O(1)
  def tail: List[A]                             // O(1)
  def isEmpty: Boolean                          // O(1)
  def nonEmpty: Boolean                         // O(1)
  def +:(x: A): List[A]                         // O(1)
  def foreach(f: A => Unit): Unit               // O(n)
  def filter(p: A => Boolean): List[A]          // O(n)
  def withFilter(p: A => Boolean): WithFilter   // O(n)
  def filterNot(p: A => Boolean): List[A]       // O(n)
  def length: Int                               // O(n)
  def take(m: Int): List[A]                     // O(m)
  def takeWhile(p: (A) ⇒ Boolean): List[A]      // O(n)
  def drop(m: Int): List[A]                     // O(m)
  def dropWhile(p: A => Boolean): List[A]       // O(n)
  def reverse: List[A]                          // O(n)
  def map[B](f: A => B): List[B]                // O(n)
  def flatMap[B](f: A => List[B]): List[B]      // O(n)
  def ++(that: List[A]): List[A]                // O(n)
  def intersperse(x: A): List[A]                // O(n)
  def foldRight[B](z: B)(f: (A, B) => B): B     // O(n)
  def foldRight[B](continue: (A, => B) => B, z: B)(f: (A, B) => B): B
  def foldLeft[B](z: B)(f: (B, A) => B): B      // O(n)
  def flatten[B](implicit ev: A => List[B]): List[B] // O(n)
  def splitAt(m: Int): (List[A], List[A])       // O(m)
  def span(p: A => Boolean): (List[A], List[A]) // O(n)
}
```

### `Stack`

It represents a LIFO data structure, the last element added to the stack will be the first one to be removed.

```scala
trait Stack[+A] {
  def top: Maybe[A]
  def push(el: A): Stack[A]
  def pop: (A, Stack[A]) Or EmptyStackException
  def isEmpty: Boolean
  def size: Int
}
```

### `Queue`

```scala
trait Queue[+A] {
  def enqueue(el: A): Queue[A]
  def dequeue: (A, Queue[A]) Or EmptyQueueException
  def peek: Maybe[A]
  def isEmpty: Boolean
  def size: Int
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
  def contains(key: K): Boolean
  def lookup(key: K): Maybe[V]
  def upsert(key: K, value: V)(f: V => V): Tree[K, V]
  def insert(key: K, value: V): Tree[K, V]
  def delete(key: K): (Maybe[V], Tree[K, V])
  def map[V1](f: V => V1): Tree[K, V1] 
  def toList: List[(K, V)]
}
```

References
----------

* __Robert Sedgewick, Kevin Wayne__, `Algorithms, 4th Edition`, 2011, Addison-Wesley Professional
* __Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest and Clifford Stein__, `Introduction to Algorithms, 3rd Edition`, 2009, Mit Press
* __Paul Chiusano and Rúnar Bjarnason__, `Functional Programming in Scala`, 2014, Manning Publications
* __Larry LIU Xinyu__, `Elementary Algorithms`, 2014
* __Chris Okasaki__, `Purely Functional Data Structures`, 1999, Cambridge University Press