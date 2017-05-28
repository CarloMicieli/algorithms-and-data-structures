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

class LinkedListStackSpec extends AbstractTestSpec with ExampleStacks {

  "An empty stack" should "have size equals to 0" in {
    val empty = Stack.empty[Int]
    empty.size shouldBe 0
    empty.isEmpty shouldBe true
    empty.nonEmpty shouldBe false
  }

  "Push elements to a stack" should "increase its size" in {
    val stack = emptyStack
    stack.push(1)

    stack.size shouldBe 1
    stack.isEmpty shouldBe false
  }

  "Pop elements out of a stack" should "happen in LIFO fashion" in {
    val stack = emptyStack
    stack.push(1)
    stack.push(2)

    val out = stack.pop
    out shouldBe 2
  }

  "peek()" should "return the first element, without changing the stack" in {
    val first = stack.top
    first shouldBe Some(1)
  }
}

trait ExampleStacks {
  def emptyStack = LinkedListStack.empty[Int]

  def stack = {
    val st = LinkedListStack.empty[Int]
    st.push(3)
    st.push(2)
    st.push(1)
    st
  }
}
