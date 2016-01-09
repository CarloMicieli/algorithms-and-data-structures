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
package io.github.carlomicieli.fp.samples.strings

/**
  * "The Caesar cipher" sample from
  * Hutton, Graham. Programming in Haskell. Cambridge University Press, 2007.
  */
object CaesarCipher {

  private val frequencyTable: Map[Char, Float] = Map(
    'a' -> 8.2f, 'b' -> 1.5f, 'c' -> 2.8f,
    'd' -> 4.3f, 'e' -> 12.7f, 'f' -> 2.2f,
    'g' -> 2.0f, 'h' -> 6.1f, 'i' -> 7.0f,
    'j' -> 0.2f, 'k' -> 0.8f, 'l' -> 4.0f,
    'm' -> 2.4f, 'n' -> 6.7f, 'o' -> 7.5f,
    'p' -> 1.9f, 'q' -> 0.1f, 'r' -> 6.0f,
    's' -> 6.3f, 't' -> 9.1f, 'u' -> 2.8f,
    'v' -> 1.0f, 'w' -> 2.4f, 'x' -> 0.2f,
    'y' -> 2.0f, 'z' -> 0.1f)

  def isLower(ch: Char): Boolean = ch >= 'a' && ch <= 'z'

  def char2int(ch: Char): Int = ch.toInt - 'a'.toInt

  def int2char(n: Int): Char = (n + 'a'.toInt).toChar

  def shift(n: Int, c: Char): Char = {
    if (isLower(c))
      int2char(mod(char2int(c) + n, 26))
    else
      c
  }

  private def mod(x: Int, y: Int): Int = {
    val res = x % y
    if (res < 0) res + y else res
  }

  def encode(n: Int, cs: String): String = {
    cs.map(shift(n, _: Char))
  }

  def percent(a: Int, b: Int): Float = (a.toFloat / b.toFloat) * 100.0f

  def freqs(cs: List[Char]): List[(Char, Float)] = {
    val length = cs.length
    val letters = ('a' to 'z').toList

    def notBlank(ch: Char) = ch != ' '

    val freqsMap = cs.filter(notBlank).sorted.groupBy(x => x).map { case (k, v) => k -> v.length }
    def count(ch: Char): (Char, Float) = {
      val freq = freqsMap.getOrElse(ch, 0)
      ch -> percent(freq, length)
    }

    letters.map(count)
  }

  // A standard method for comparing a list of observed frequencies os with a list
  // of expected frequencies es is the chi-square statistic.
  def chiSquare(os: List[Float], es: List[Float]): Float = {
    (os zip es).map { case(o, e) => math.pow(o - e, 2).toFloat }.sum
  }

  def rotate[A](n: Int, xs: List[A]): List[A] = xs.drop(n) ::: xs.take(n)

  // A function that returns the list of all positions
  // at which a value occurs in a list, by pairing each
  // element with its position
  def positions[A](a: A, xs: List[A]): List[Int] = {
    xs.zipWithIndex.collect { case (`a`, k) => k }
  }

  def crack(msg: String): String = {
    val cs: List[Char] = msg.toList
    val table = freqs(cs).map { case (_, v) => v}

    def chiTabRow(n: Int): Float = chiSquare(rotate(n, table), frequencyTable.values.toList)

    val chiTable: List[Float] = (0 to 25).map(chiTabRow).toList
    val factor = positions(chiTable.min, chiTable).head

    encode(-factor, msg)
  }
}
