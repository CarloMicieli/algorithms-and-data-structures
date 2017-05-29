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

import io.github.carlomicieli.test.AbstractTestSpec

class ListQueueSpec extends AbstractTestSpec with SampleQueues {
  "An empty queue" should "have size equal to 0" in {
    val empty = Queue.empty[Int]
    empty.isEmpty shouldBe true
    empty.size shouldBe 0
  }

  "Adding an element to a queue" should "happen in FIFO fashion" in {
    val queue = emptyQueue
    queue.enqueue(1)
    queue.enqueue(2)
    queue.size should be(2)
    queue.isEmpty shouldBe false
  }

  "dequeue operation" should "remove elements from queue" in {
    val q = queue
    val e1 = q.dequeue()
    e1 shouldBe 1

    val e2 = q.dequeue()
    e2 shouldBe 2
  }

  "dequeue operation" should "throw an exception when the queue is empty" in {
    val _ = intercept[EmptyQueueException] {
      emptyQueue.dequeue()
    }
  }

  "peek operation" should "return the first element, without modifying the queue" in {
    val q = queue
    q.peek.get shouldBe 1
  }
}

trait SampleQueues {
  def emptyQueue: Queue[Int] = Queue.empty[Int]

  def queue: Queue[Int] = {
    val queue = emptyQueue
    queue.enqueue(1)
    queue.enqueue(2)
    queue
  }
}