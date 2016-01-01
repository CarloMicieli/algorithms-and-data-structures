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
package io.github.carlomicieli.fp.typeclasses

import org.scalacheck.Prop.forAll
import io.github.carlomicieli.test.AbstractPropSpec

class OrdProperties extends AbstractPropSpec with OrdLaws {

  property("Show[Int] instance is lawful") {
    check(forAll { (x: Int, y: Int, z: Int) =>
      checkAllLaws(x, y, z)
    })
  }

  property("Show[Boolean] instance is lawful") {
    check(forAll { (x: Boolean, y: Boolean, z: Boolean) =>
      checkAllLaws(x, y, z)
    })
  }

  property("Show[String] instance is lawful") {
    check(forAll { (x: String, y: String, z: String) =>
      checkAllLaws(x, y, z)
    })
  }

  def checkAllLaws[A: Ord](x: A, y: A, z: A): Boolean = {
    antisymmetryLaw(x, y)
    transitivityLaw(x, y, z)
    totalityLaw(x, y)
  }
}