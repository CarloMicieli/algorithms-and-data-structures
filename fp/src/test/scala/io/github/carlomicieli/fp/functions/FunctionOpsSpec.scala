/*
 *                       __                  __
 *      ______________ _/ /___ _      ____ _/ /___ _____
 *     / ___/ ___/ __ `/ / __ `/_____/ __ `/ / __ `/ __ \
 *    (__  ) /__/ /_/ / / /_/ /_____/ /_/ / / /_/ / /_/ /
 *   /____/\___/\__,_/_/\__,_/      \__,_/_/\__, /\____/
 *                                         /____/
 *  Copyright (c) 2016 the original author or authors.
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
package io.github.carlomicieli.fp.functions

import io.github.carlomicieli.test.AbstractSpec

class FunctionOpsSpec extends AbstractSpec {
  describe("const") {
    it("should always return the same value") {
      val f = FunctionOps.const(42)
      f() shouldBe 42
      f() shouldBe 42
    }
  }

  describe("id") {
    it("should return the argument") {
      FunctionOps.id(4) shouldBe 4
      FunctionOps.id('a') shouldBe 'a'
    }
  }

  describe("flip") {
    it("should switch the function arguments") {
      val f = (a: Int, b: Int) => a - b
      FunctionOps.flip(f)(20, 10) shouldBe -10
    }
  }

  describe("util") {
    it("should apply the function f until condition holds") {
      FunctionOps.until[Int](_ > 10)(_ + 1)(1) shouldBe 11
      FunctionOps.until[Int](_ < 10)(_ + 1)(1) shouldBe 1
    }
  }

  describe("error") {
    it("should throw an exception") {
      the [Exception] thrownBy {
        FunctionOps.error("error message")
      } should have message "*** Exception: error message"
    }
  }
}
