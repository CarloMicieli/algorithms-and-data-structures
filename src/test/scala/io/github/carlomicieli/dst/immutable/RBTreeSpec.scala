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
package io.github.carlomicieli.dst.immutable

import io.github.carlomicieli.util.{Just, None}
import io.github.carlomicieli.test.AbstractTestSpec

class RBTreeSpec extends AbstractTestSpec with RBTreesFixture {
  "An empty RB tree" should "have size equals to 0" in {
    val empty = RBTree.empty[Int, String]
    empty.isEmpty shouldBe true
    empty.size shouldBe 0
  }

  "inserting elements in a RBTree" should "increase the tree size" in {
    val t = emptyTree.insert(1, "one").insert(2, "two").insert(3, "three")
    t.isEmpty shouldBe false
    t.size shouldBe 3
  }

  "toList" should "convert a RBTree to a list" in {
    emptyTree.toList shouldBe List()
    bsTree.toList shouldBe List((21, "b"), (42, "a"), (66, "f"), (99, "c"))
  }

  "lookup" should "find elements in a RB tree" in {
    emptyTree.lookup(99) shouldBe None
    bsTree.lookup(42) shouldBe Just((42, "a"))
  }

  "min" should "find the min element in a RB tree" in {
    emptyTree.min shouldBe None
    bsTree.min shouldBe Just(21)
  }

  "max" should "find the max element in a RB tree" in {
    emptyTree.max shouldBe None
    bsTree.max shouldBe Just(99)
  }

  "contains" should "checks whether the RB tree contains a key" in {
    emptyTree.contains(99) shouldBe false
    bsTree.contains(99) shouldBe true
    bsTree.contains(9999) shouldBe false
  }

  "depth" should "return the maximum RB tree depth" in {
    emptyTree.depth shouldBe 0
    bsTree.depth shouldBe 3
  }

  "fold" should "apply a function to all RB tree values" in {
    numTree.fold(_ + _) shouldBe 15
  }
}

trait RBTreesFixture {
  def emptyTree: Tree[Int, String] = RBTree.empty[Int, String]
  def bsTree: Tree[Int, String] = RBTree.fromList(List((42, "a"), (21, "b"), (99, "c"), (66, "f")))
  def numTree: Tree[Int, Int] = RBTree.fromList(List((1, 1), (2, 2), (3, 3), (4, 4), (5, 5)))
}