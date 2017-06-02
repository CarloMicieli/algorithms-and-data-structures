/*
 *                     __                  __
 *    ______________ _/ /___ _      ____ _/ /___ _____
 *   / ___/ ___/ __ `/ / __ `/_____/ __ `/ / __ `/ __ \
 *  (__  ) /__/ /_/ / / /_/ /_____/ /_/ / / /_/ / /_/ /
 * /____/\___/\__,_/_/\__,_/      \__,_/_/\__, /\____/
 *                                       /____/
 * Copyright (c) 2017 the original author or authors.
 * See the LICENCE.txt file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.carlomicieli.oop.dst

import org.scalacheck.Prop.forAll
import io.github.carlomicieli.test.AbstractPropSpec
import io.github.carlomicieli.oop.dst.arbitraryDoublyLinkedList._

class DoublyLinkedListProperties extends AbstractPropSpec {
  property("append: increase list length by 1") {
    check(forAll { (x: Int, list: LinkedList[Int]) =>
      val originalLength = list.length
      list.append(x)
      list.length === (originalLength + 1)
    })
  }

  property("append: the appended element is new list last element") {
    check(forAll { (x: Int, list: LinkedList[Int]) =>
      list.append(x)
      list.last === x
    })
  }

  property("prepend: increase list length by 1") {
    check(forAll { (x: Int, list: LinkedList[Int]) =>
      val originalLength = list.length
      list.prepend(x)
      list.length === (originalLength + 1)
    })
  }

  property("prepend: the prepended element is new list head") {
    check(forAll { (x: Int, list: LinkedList[Int]) =>
      list.prepend(x)
      list.head === x
    })
  }

  property("find: checks whether the list contain a given key") {
    check(forAll { (elements: Short, x: Int) =>
      val l = DoublyLinkedList.empty[Int]
      for (_ <- 1 to elements)
        l += 1
      l += x
      for (_ <- 1 to elements)
        l += 1

      l.contains(x) === true
    })
  }

  property("isEmpty === length = 0") {
    check(forAll { (list: LinkedList[Int]) =>
      list.isEmpty === (list.length == 0)
    })
  }

  property("foldLeft and foldRight produce the same results when the operation is associative") {
    check(forAll { (list: LinkedList[Int]) =>
      list.foldLeft(0)(_ + _) === list.foldRight(0)(_ + _)
    })
  }
}
