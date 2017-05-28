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

import io.github.carlomicieli.test.AbstractPropSpec
import org.scalacheck.Prop.forAll

class EqProperties extends AbstractPropSpec with EqLaws {

  property("Int respect the Eq type class laws") {
    check(forAll { (x: Int, y: Int, z: Int) =>
      checkAllLaws(x, y, z)(_ % 2)
    })
  }

  property("Char respect the Eq type class laws") {
    check(forAll { (x: Char, y: Char, z: Char) =>
      checkAllLaws(x, y, z)(ch => (ch.toInt + 1).toChar)
    })
  }

  property("String respect the Eq type class laws") {
    check(forAll { (x: String, y: String, z: String) =>
      checkAllLaws(x, y, z)(_ * 2)
    })
  }

  property("Float respect the Eq type class laws") {
    check(forAll { (x: Float, y: Float, z: Float) =>
      checkAllLaws(x, y, z)(_ * 2)
    })
  }

  property("Double respect the Eq type class laws") {
    check(forAll { (x: Double, y: Double, z: Double) =>
      checkAllLaws(x, y, z)(_ * 2)
    })
  }

  property("Boolean respect the Eq type class laws") {
    check(forAll { (x: Boolean, y: Boolean, z: Boolean) =>
      checkAllLaws(x, y, z)(identity)
    })
  }

  def checkAllLaws[A: Eq](x: A, y: A, z: A)(f: A => A): Boolean = {
    reflexivityLaw(x)
    symmetryLaw(x, y)
    transitivityLaw(x, y, z)
    substitutionLaw(x, x)(f)
  }
}
