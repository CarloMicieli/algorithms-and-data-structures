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

import io.github.carlomicieli.test.AbstractTestSpec
import io.github.carlomicieli.util.{None, Just}

class BinarySearchTreeSpec extends AbstractTestSpec {
  import TreesFixture._

  "An empty BS tree" should "have size 0" in {
    val emptyTree = Tree.empty[Int, String]
    emptyTree.size shouldBe 0
    emptyTree.isEmpty shouldBe true
  }

  "Adding an element to a BS tree" should "increase its size" in {
    val t3 = emptyTree.insert(42, "answer")

    t3.isEmpty shouldBe false
    t3.size shouldBe 1
  }

  "It" should "find an element in a BS tree" in {
    bsTree.lookup(99) shouldBe Just((99, "c"))
    bsTree.lookup(-1) shouldBe None
  }

  "It" should "calculate a tree depth" in {
    emptyTree.depth shouldBe 0
    bsTree.depth shouldBe 3
  }

  "It" should "find the min element in the tree" in {
    emptyTree.min shouldBe None
    bsTree.min shouldBe Just(21)
  }

  "It" should "find the max element in the tree" in {
    emptyTree.max shouldBe None
    bsTree.max shouldBe Just(99)
  }

  "It" should "convert a tree to a list" in {
    bsTree.toList shouldBe List((21, "b"), (42, "a"), (66, "f"), (99, "c"))
  }

  "It" should "update a value for a key already in the tree" in {
    val t3 = bsTree.upsert(45, "d")(_ * 2)
    t3.lookup(45) shouldBe Just((45, "d"))

    val t4 = bsTree.upsert(21, "b")(_ * 2)
    t4.lookup(21) shouldBe Just((21, "bb"))
  }
  
  "deleting a element from an empty tree" should "produce an empty result" in {
    val (removed, newTree) = emptyTree.delete(4)
    removed shouldBe None
    newTree shouldBe Tree.empty[Int, String]
  }

  "delete an element from a tree" should "return the remove value" in {
    val (removed, newTree) = bsTree.delete(42)
    removed shouldBe Just("a")
    newTree.toString shouldBe "((- [21->b] -) [66->f] (- [99->c] -))"
  }

  "it" should "produce string representations for trees" in {
    emptyTree.toString shouldBe "-"
    bsTree.toString shouldBe "((- [21->b] (- [42->a] -)) [66->f] (- [99->c] -))"
  }
}

object TreesFixture {
  def emptyTree: Tree[Int, String] = Tree.empty[Int, String]
  def bsTree: Tree[Int, String] = Tree((42, "a"), (21, "b"), (99, "c"), (66, "f"))
}