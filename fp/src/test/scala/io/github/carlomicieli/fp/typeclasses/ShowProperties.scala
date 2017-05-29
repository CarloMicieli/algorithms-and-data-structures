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
import Show.ops._
import org.scalacheck.Prop.forAll

class ShowProperties extends AbstractPropSpec {
  property("Boolean is made instance of the Show type class") {
    check(forAll { (b: Boolean) =>
      b.show === b.toString
    })
  }

  property("Bytes are made instance of the Show type class") {
    check(forAll { (n: Byte) =>
      n.show === n.toString
    })
  }

  property("Longs are made instance of the Show type class") {
    check(forAll { (n: Long) =>
      n.show === n.toString
    })
  }

  property("Ints are made instance of the Show type class") {
    check(forAll { (n: Int) =>
      n.show === n.toString
    })
  }

  property("Floats are made instance of the Show type class") {
    check(forAll { (n: Float) =>
      n.show === n.toString
    })
  }

  property("Doubles are made instance of the Show type class") {
    check(forAll { (n: Double) =>
      n.show === n.toString
    })
  }

  property("Strings are made instance of the Show type class") {
    check(forAll { (s: String) =>
      s.show === s""""$s""""
    })
  }

  property("Chars are made instance of the Show type class") {
    check(forAll { (c: Char) =>
      c.show === s"'$c'"
    })
  }
}
