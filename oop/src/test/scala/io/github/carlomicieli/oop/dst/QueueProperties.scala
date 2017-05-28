/*
 *                     __                  __
 *    ______________ _/ /___ _      ____ _/ /___ _____
 *   / ___/ ___/ __ `/ / __ `/_____/ __ `/ / __ `/ __ \
 *  (__  ) /__/ /_/ / / /_/ /_____/ /_/ / / /_/ / /_/ /
 * /____/\___/\__,_/_/\__,_/      \__,_/_/\__, /\____/
 *                   /____/
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

import org.scalatest.{Matchers, PropSpec}
import org.scalatest.prop.TableDrivenPropertyChecks

class QueueProperties extends PropSpec
with TableDrivenPropertyChecks
with Matchers
with QueueFixtures {

  val emptyQueues =
    Table(
      "queue",
      emptyFixedCapacityQueue,
      emptyLinkedListQueue
    )

  val nonEmptyQueues =
    Table(
      "queue",
      fixedCapQueue,
      linkedListQueue
    )

  property("an empty Queue should have size 0") {
    forAll(emptyQueues) { q =>
      q.size shouldBe 0
    }
  }

  property("a non empty Queue should have size equals to number of elements") {
    forAll(nonEmptyQueues) { q =>
      q.size shouldBe 3
    }
  }

  property("an empty Queue should return 'true' for isEmpty") {
    forAll(emptyQueues) { q =>
      q.isEmpty shouldBe true
    }
  }

  property("a non empty Queue should return 'false' for isEmpty") {
    forAll(nonEmptyQueues) { q =>
      q.isEmpty shouldBe false
    }
  }

  property("an empty Queue should return None as top element") {
    forAll(emptyQueues) { q =>
      q.peek shouldBe None
    }
  }

  property("a non empty Queue should return the top element") {
    forAll(nonEmptyQueues) { q =>
      q.peek shouldBe Some(1)
    }
  }

  property("dequeue from an empty queue should throw an exception") {
    forAll(emptyQueues) { q =>
      the [EmptyQueueException] thrownBy {
        q.dequeue()
      } should have message "Queue is empty"
    }
  }
}

trait QueueFixtures {
  def fixedCapQueue: Queue[Int] = {
    val q = FixedCapacityQueue[Int](16)
    q.enqueue(1)
    q.enqueue(2)
    q.enqueue(3)
    q
  }

  def linkedListQueue: Queue[Int] = {
    val q = LinkedListQueue.empty[Int]
    q.enqueue(1)
    q.enqueue(2)
    q.enqueue(3)
    q
  }

  def emptyFixedCapacityQueue = FixedCapacityQueue.empty[Int]
  def emptyLinkedListQueue = LinkedListQueue.empty[Int]
}
