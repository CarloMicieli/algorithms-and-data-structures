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

package io.github.carlomicieli.fp.typeclasses

import io.github.carlomicieli.test.AbstractSpec
import Enum._

class EnumSpec extends AbstractSpec {
  describe("Enum type") {
    describe("Int as Enum") {
      it("should have a bounds") {
        intEnum.minBound shouldBe Int.MinValue
        intEnum.maxBound shouldBe Int.MaxValue
      }

      it("should convert from enum values") {
        intEnum.fromEnum(42) shouldBe 42
      }

      it("should convert to enum values") {
        intEnum.toEnum(42) shouldBe Some(42)
      }

      it("should return the next element") {
        intEnum.next(42) shouldBe Some(43)
      }

      it("should return the previous element") {
        intEnum.prev(42) shouldBe Some(41)
      }
    }

    describe("Char as Enum") {
      it("should have a bounds") {
        charEnum.minBound shouldBe Char.MinValue
        charEnum.maxBound shouldBe Char.MaxValue
      }

      it("should convert from enum values") {
        charEnum.fromEnum('A') shouldBe 65
      }

      it("should convert to enum values") {
        charEnum.toEnum(65) shouldBe Some('A')
        charEnum.toEnum(-65) shouldBe None
      }

      it("should return the next element") {
        charEnum.next('A') shouldBe Some('B')
      }

      it("should return the previous element") {
        charEnum.prev('B') shouldBe Some('A')
      }

      it("should return the elements range") {
        charEnum.range shouldBe (0 to Char.MaxValue.toInt)
      }
    }
  }
}