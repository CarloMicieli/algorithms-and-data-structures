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

import io.github.carlomicieli.test.AbstractPropSpec
import org.scalacheck.Prop.forAll

class MembershipProperties extends AbstractPropSpec with MembershipFixture {

  property("contains") {
    check(forAll { (x: Int) =>
      val fun = (x: Int) => x > 0
      val posNumbers = Membership { fun }

      posNumbers.contains(x) === fun(x)
    })
  }

  property("union") {
    check(forAll { (x: Int) =>
      val posOrOdd = posNumbers union evenNumber
      posOrOdd.contains(x) === (posNumbers.contains(x) || evenNumber.contains(x))
    })
  }

  property("intersection") {
    check(forAll { (x: Int) =>
      val posAndOdd = posNumbers intersection evenNumber
      posAndOdd.contains(x) === (posNumbers.contains(x) && evenNumber.contains(x))
    })
  }

  property("complement") {
    check(forAll { (x: Int) =>
      val posNotOdd = posNumbers complement evenNumber
      posNotOdd.contains(x) === (posNumbers.contains(x) && !evenNumber.contains(x))
    })
  }

  property("negate") {
    check(forAll { (x: Int) =>
      val negateOdd = oddNumber.negate
      negateOdd.contains(x) === evenNumber.contains(x)
    })
  }
}

trait MembershipFixture {
  def posNumbers = Membership { (x: Int) => x > 0 }
  def evenNumber = Membership { (x: Int) => x % 2 == 0 }
  def oddNumber = Membership { (x: Int) => x % 2 != 0 }
}