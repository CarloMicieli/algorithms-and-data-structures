/*
 *                       __                  __
 *      ______________ _/ /___ _      ____ _/ /___ _____
 *     / ___/ ___/ __ `/ / __ `/_____/ __ `/ / __ `/ __ \
 *    (__  ) /__/ /_/ / / /_/ /_____/ /_/ / / /_/ / /_/ /
 *   /____/\___/\__,_/_/\__,_/      \__,_/_/\__, /\____/
 *                                         /____/
 *  Copyright (c) 2015 the original author or authors.
 *  See the LICENCE.txt file distributed with this work for additional
 *  information regarding copyright ownership.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package io.github.carlomicieli.dst.mutable

import java.util.NoSuchElementException

import io.github.carlomicieli.test.AbstractTestSpec
import io.github.carlomicieli.util.{None, Just}

class BsTreeSpec extends AbstractTestSpec with BsTreesFixture {
  "An empty BS tree" should "have size 0" in {
    val empty = BsTree.empty[Int, Int]
    empty.isEmpty shouldBe true
    empty.size shouldBe 0
  }

  "Adding an element to a BS tree" should "increase its size" in {
    val t = newTree
    t.insert(1, "one")
    t.insert(2, "two")
    t.insert(3, "three")
    t.size shouldBe 3
  }

  "it" should "search for elements in a BS tree" in {
    val t = tree
    t.search(18) shouldBe Just("(18)")
    t.search(99) shouldBe None

    val empty = newTree
    empty.search(99) shouldBe None
  }

  "min" should "throw an exception if the BS tree is empty" in {
    val r = intercept[NoSuchElementException] {
      emptyTree.min
    }
  }

  "min" should "return the minimum key in the BS tree" in {
    tree.min shouldBe 2
  }

  "max" should "throw an exception if the BS tree is empty" in {
    val r = intercept[NoSuchElementException] {
      emptyTree.max
    }
  }

  "max" should "return the maximum key in the BS tree" in {
    tree.max shouldBe 20
  }

  "successor" should "find the smallest key greater than the one provided" in {
    tree.successor(15) shouldBe 17
  }

  "successor" should "throw an exception if no successor is found" in {
    val thrown = intercept[NoSuchElementException] {
      tree.successor(20)
    }
    thrown.getMessage shouldBe "Successor not found for '20'"
  }

  "predecessor" should "find with the greatest key smaller than the one provided" in {
    tree.predecessor(15) shouldBe 13
  }

  "predecessor" should "throw an exception if no predecessor is found" in {
    val thrown = intercept[NoSuchElementException] {
      tree.predecessor(2)
    }
    thrown.getMessage shouldBe "Predecessor not found for '2'"
  }

  "inorderTreeWalk" should "traverse the tree elements in order" in {
    var str = ""
    tree.inorderWalk(kv => str = str + s"${kv.key} > ")
    str shouldBe "2 > 3 > 4 > 6 > 7 > 9 > 13 > 15 > 17 > 18 > 20 > "
  }
}

trait BsTreesFixture {
  val emptyTree: Tree[Int, String] = BsTree.empty[Int, String]
  def newTree: Tree[Int, String] = BsTree.empty[Int, String]
  def tree: Tree[Int, String] = {
    val t = newTree
    t.insert(15, "(15)")
    t.insert(6 , "( 6)")
    t.insert(18, "(18)")
    t.insert(3 , "( 3)")
    t.insert(7 , "( 7)")
    t.insert(17, "(17)")
    t.insert(20, "(20)")
    t.insert(2 , "( 2)")
    t.insert(4 , "( 4)")
    t.insert(13, "(13)")
    t.insert(9 , "( 9)")
    t
  }
}