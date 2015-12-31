# algorithms-and-data-structures

[![Build Status](https://travis-ci.org/CarloMicieli/algorithms-and-data-structures.png?branch=master)](https://travis-ci.org/CarloMicieli/algorithms-and-data-structures)
[![Coverage Status](https://coveralls.io/repos/CarloMicieli/algorithms-and-data-structures/badge.svg?branch=master&service=github)](https://coveralls.io/github/CarloMicieli/algorithms-and-data-structures?branch=master)

Data structures and algorithms implementations with the Scala programming language.

    $ git clone https://github.com/CarloMicieli/algorithms-and-data-structures.git
    
To run the test suite:
    
    $ sbt test
        
To generate the `Scaladoc` documentation:

    $ sbt unidoc

The documentation is generated under the `./target/scala-2.11/unidoc` directory. (`sbt doc` will generate a different doc folder for each subproject).
The project documentation is published [here](http://carlomicieli.github.io/algorithms-and-data-structures/latest/api/)

The project includes the following subprojects:
    
    * `common`: common library;
    * `fp`: purely functional algorithms and data structures;
    * `oop`: object oriented algorithms and data structures;
    * `benchmarks`: algorithms and data structures micro-benchmarks.

Sorting Algorithms
------------------

| Name              |   stable  |  in place |           |           |
| ----------------- | --------- | --------- | --------- | --------- |
| BubbleSort [1]    |    no     |   yes     |   Θ(n²)   |    O(n²)  |
| InsertionSort     |    yes    |   yes     |   Θ(n²)   |    O(n²)  |
| SelectionSort     |    no     |   yes     |   Θ(n²)   |    O(n²)  |
| ShellSort [2]     |    no     |   yes     |   Θ(n<sup>3/2</sup>) |    O(n²)  |

Machine used for the benchmark
* Intel® Core™ i5-3570K CPU @ 3.40GHz × 4
* 16 Gb RAM
* SSD hard disk
* Ubuntu 14.04.3
* Java SE 1.8u66 / Scala 2.11.7

Data Structures
---------------

    * List
    * Binary search tree (normal and Red-Black)
    * Queue
    * Stack

References
----------

* Robert Sedgewick, Kevin Wayne. 2011. __Algorithms, 4th Edition__. Addison-Wesley Professional
* Thomas H. Cormen, Charles E. Leiserson, Ronald L. Rivest, Clifford Stein. 2009. __Introduction to Algorithms__ (3rd Edition). Mit Press
* Paul Chiusano, Rúnar Bjarnason. 2014. __Functional Programming in Scala__. Manning Publications
* Larry LIU Xinyu. 2014. __Elementary Algorithms__.
* Chris Okasaki. 1999. __Purely Functional Data Structures__. Cambridge University Press
* John Hughes. 2007. __QuickCheck testing for fun and profit__. Proceedings of the 9th international conference on Practical Aspects of Declarative Languages (PADL'07)
* Jean Niklas L'orange. __Understanding Clojure's Persistent Vectors__. Blog post ([link](http://hypirion.com/musings/understanding-persistent-vector-pt-1))
* Bill Venners. 2015. Simplifying Scala Collections. ScalaWorld Conference. YouTube video ([link](https://www.youtube.com/watch?v=UBjzbkhvYTU))

Licence
-------

    Copyright 2016 Carlo Micieli
    
    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at
          
         http://www.apache.org/licenses/LICENSE-2.0
                 
    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
