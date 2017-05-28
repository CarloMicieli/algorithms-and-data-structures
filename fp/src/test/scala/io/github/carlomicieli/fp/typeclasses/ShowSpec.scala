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

package io.github.carlomicieli.fp.typeclasses

import io.github.carlomicieli.fp.dst.List
import io.github.carlomicieli.fp.dst.{ Maybe, None, Just }
import io.github.carlomicieli.test.AbstractSpec
import Show.ops._

class ShowSpec extends AbstractSpec {

  describe("Show") {
    describe("List instance") {
      it("should produce a string from a List of chars") {
        val cs: List[Char] = List('h', 'e', 'l', 'l', 'o')
        cs.show shouldBe "\"hello\""
      }

      it("should produce a string from a List of strings") {
        val ss: List[String] = List("hello", "world")
        ss.show shouldBe "[\"hello\", \"world\"]"
      }

      it("should produce a string from a List of integers") {
        val xs: List[Int] = List(1, 2, 3, 4, 5)
        xs.show shouldBe "[1, 2, 3, 4, 5]"
      }
    }

    describe("Maybe instance") {
      it("should produce a string from a None value") {
        Show[Maybe[Int]].show(None) shouldBe "None"
      }

      it("should produce a string from a Just value") {
        Show[Maybe[Int]].show(Just(42)) shouldBe "Just(42)"
      }
    }
  }
}
