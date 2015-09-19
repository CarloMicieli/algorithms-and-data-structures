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
import io.github.carlomicieli.util.{Just, Good, None}

class StackSpec extends AbstractTestSpec with StackFixture {
  "an empty stack" should "have size 0" in {
    val emptyStack = Stack.empty[Int]
    emptyStack.size shouldBe 0
    emptyStack.isEmpty shouldBe true
    emptyStack.nonEmpty shouldBe false
  }

  "pushing elements to a stack" should "change its size" in {
    val stack = newStack.push(42)
    stack.isEmpty shouldBe false
    stack.nonEmpty shouldBe true
    stack.size shouldBe 1
  }

  "adding elements to a stack" should "happen in LIFO fashion" in {
    val stack = newStack.push(1).push(2).push(3)
    stack.size shouldBe 3
    val Good((el, stack2)) = stack.pop
    el shouldBe 3
    stack2.size shouldBe 2
  }

  "pop an element from an empty stack" should "return a Bad value" in {
    val res = newStack.pop
    res.isBad shouldBe true
  }

  "top" should "return the top element without affecting the stack" in {
    val stack = newStack(List(4, 3, 2, 1))
    stack.top shouldBe Just(1)
    stack.size shouldBe 4
  }

  "top" should "return a None when the stack is empty" in {
    newStack.top shouldBe None
  }
}

trait StackFixture {
  def newStack: Stack[Int] = Stack.empty[Int]

  def newStack(items: List[Int]): Stack[Int] = {
    @annotation.tailrec
    def go(items: List[Int], stack: Stack[Int]): Stack[Int] = {
      items match {
        case Nil => stack
        case x +: xs => go(xs, stack push x)
      }
    }
    go(items, Stack.empty[Int])
  }
}