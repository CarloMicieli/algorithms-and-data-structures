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

package io.github.carlomicieli.fp.dst

import io.github.carlomicieli.test.AbstractTestSpec

class BalancedQueueSpec extends AbstractTestSpec with BalancedQueueFixture {
  "An empty balanced queue" should "have size 0" in {
    val empty = BalancedQueue.empty[Int]
    empty.size shouldBe 0
    empty.isEmpty shouldBe true
    empty.nonEmpty shouldBe false
  }

  "Enqueue an element" should "change its size" in {
    val q = BalancedQueue.empty[Int].enqueue(1).enqueue(2)
    q.size shouldBe 2
    q.isEmpty shouldBe false
  }

  "peek an element" should "leave the queue unchanged" in {
    queue.peek shouldBe Just(1)
    emptyQueue.peek shouldBe None
  }

  "dequeuing an element" should "remove the element from the queue" in {
    val Good((x, q)) = queue.dequeue
    x shouldBe 1
    q.size shouldBe queue.size - 1
  }

  "dequeue on empty queues" should "return a Bad value" in {
    val res = emptyQueue.dequeue
    res.isBad shouldBe true
  }
}

trait BalancedQueueFixture {
  def emptyQueue: Queue[Int] = BalancedQueue.empty[Int]
  def queue: Queue[Int] = emptyQueue.enqueue(1).enqueue(2).enqueue(3)
}