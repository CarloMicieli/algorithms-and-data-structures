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

import io.github.carlomicieli.test.AbstractPropSpec
import org.scalacheck.Prop.forAll

class NumProperties extends AbstractPropSpec with AdditionLaws {
  property("Int: was made instance of Num type class") {
    check(forAll { (x: Int, y: Int) =>
      val num = Num[Int]

      num.zero === 0
      num.one === 1
      num.add(x, y) === x + y
      num.sub(x, y) === x - y
      num.mul(x, y) === x * y
      num.abs(x) >= 0
      num.mul(num.abs(x), num.signum(x)) === x
    })
  }

  property("Long: was made instance of Num type class") {
    check(forAll { (x: Long, y: Long) =>
      val num = Num[Long]

      num.zero === 0L
      num.one === 1L
      num.add(x, y) === x + y
      num.sub(x, y) === x - y
      num.mul(x, y) === x * y
      num.abs(x) >= 0
      num.mul(num.abs(x), num.signum(x)) === x
    })
  }

  property("Float: was made instance of Num type class") {
    check(forAll { (x: Float, y: Float) =>
      val num = Num[Float]

      num.zero === 0.0f
      num.one === 1.0f
      num.add(x, y) === x + y
      num.sub(x, y) === x - y
      num.mul(x, y) === x * y
      num.abs(x) >= 0
      num.mul(num.abs(x), num.signum(x)) === x
    })
  }

  property("Double: was made instance of Num type class") {
    check(forAll { (x: Double, y: Double) =>
      val num = Num[Double]

      num.zero === 0.0
      num.one === 1.0
      num.add(x, y) === x + y
      num.sub(x, y) === x - y
      num.mul(x, y) === x * y
      num.abs(x) >= 0
      num.mul(num.abs(x), num.signum(x)) === x
    })
  }

  property("sum is lawful according to addition laws") {
    check(forAll { (x: Int, y: Int, z: Int) =>
      commutativityLaw[Int](x, y)
      associativityLaw[Int](x, y, z)
      identityElement[Int](x)
    })
  }
}
