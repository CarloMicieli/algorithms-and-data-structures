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

import java.util.NoSuchElementException

import io.github.carlomicieli.test.AbstractSpec

class BsTreeSpec extends AbstractSpec with BsTreesFixture {
  describe("A mutable Binary Search tree") {
    describe("fromList") {
      it("should create an empty tree from the empty list") {
        val empty = BsTree.fromList(List.empty[(Int, Int)])
        empty.size shouldBe 0
        empty.isEmpty shouldBe true
      }

      it("should create a tree with the list elements") {
        val xs = List((1, 1), (2, 2), (3, 3))
        val tree = BsTree.fromList(xs)
        tree.size shouldBe xs.length
      }
    }

    describe("size") {
      it("should be 0 for the empty tree") {
        val empty = BsTree.empty[Int, Int]
        empty.size shouldBe 0
      }

      it("should be the number of nodes in the tree") {
        tree.size shouldBe 11
      }
    }

    describe("isEmpty") {
      it("should be 'true' for the empty tree") {
        emptyTree.isEmpty shouldBe true
      }

      it("should be 'false' for the empty tree") {
        tree.isEmpty shouldBe false
      }
    }

    describe("insert") {
      it("should change the tree size") {
        val t1 = emptyTree
        val t2 = tree
        t1.insert(42, "answer")
        t1.size shouldBe 1

        t2.insert(42, "answer")
        t2.size shouldBe 12
      }

      it("should insert the new element") {
        val t = tree
        t.insert(42, "answer")
        t.search(42) shouldBe Some("answer")
      }
    }

    describe("search") {
      it("should search for elements in the tree") {
        val t = tree
        t.search(18) shouldBe Some("(18)")
        t.search(99) shouldBe None
      }

      it("should always return None for the empty tree") {
        emptyTree.search(99) shouldBe None
      }
    }

    describe("min") {
      it("should throw an exception for the empty tree") {
        the[NoSuchElementException] thrownBy {
          emptyTree.min
        } should have message "min: tree is empty"
      }

      it("should return the min key from a non empty tree") {
        tree.min shouldBe 2
      }
    }

    describe("max") {
      it("should throw an exception for the empty tree") {
        the[NoSuchElementException] thrownBy {
          emptyTree.max
        } should have message "max: tree is empty"
      }

      it("should return the max key from a non empty tree") {
        tree.max shouldBe 20
      }
    }

    describe("successor") {
      it("should find the smallest key greater than the one provided") {
        tree.successor(15) shouldBe 17
      }

      it("should throw an exception if no successor is found") {
        the[NoSuchElementException] thrownBy {
          tree.successor(20)
        } should have message "Successor not found for '20'"
      }

      it("should throw an exception for the empty tree") {
        the[NoSuchElementException] thrownBy {
          emptyTree.successor(20)
        } should have message "Successor not found for '20'"
      }
    }

    describe("predecessor") {
      it("should find the smallest key greater than the one provided") {
        tree.predecessor(15) shouldBe 13
      }

      it("should throw an exception if no predecessor is found") {
        the[NoSuchElementException] thrownBy {
          tree.predecessor(2)
        } should have message "Predecessor not found for '2'"
      }

      it("should throw an exception for the empty tree") {
        the[NoSuchElementException] thrownBy {
          emptyTree.predecessor(20)
        } should have message "Predecessor not found for '20'"
      }
    }

    describe("inorderTreeWalk") {
      it("should traverse the tree elements in order") {
        var str = ""
        tree.inorderWalk(kv => str = str + s"${kv.key} > ")
        str shouldBe "2 > 3 > 4 > 6 > 7 > 9 > 13 > 15 > 17 > 18 > 20 > "
      }
    }

    describe("delete") {
      it("should return a None deleting an element from the empty tree") {
        emptyTree.delete(99) shouldBe None
      }

      it("should return a None if the key is not found") {
        val t = tree
        t.delete(42) shouldBe None
      }

      it("should remove the element with the given key") {
        val t = tree
        t.delete(6) shouldBe Some("( 6)")
        t.search(6) shouldBe None
      }
    }
  }
}

trait BsTreesFixture {
  def emptyTree: Tree[Int, String] = BsTree.empty[Int, String]
  def newTree: Tree[Int, String] = BsTree.empty[Int, String]
  def tree: Tree[Int, String] = {
    val t = newTree
    t.insert(15, "(15)")
    t.insert(6, "( 6)")
    t.insert(18, "(18)")
    t.insert(3, "( 3)")
    t.insert(7, "( 7)")
    t.insert(17, "(17)")
    t.insert(20, "(20)")
    t.insert(2, "( 2)")
    t.insert(4, "( 4)")
    t.insert(13, "(13)")
    t.insert(9, "( 9)")
    t
  }
}
