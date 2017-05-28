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

package io.github.carlomicieli.fp.samples.strings

/** "The Caesar cipher" sample from
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
    'y' -> 2.0f, 'z' -> 0.1f
  )

  val table = frequencyTable.toList.sorted.map { case (_, v) => v }

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

  def encode(n: Int, cs: List[Char]): List[Char] = {
    cs.map(shift(n, _: Char))
  }

  def encode(n: Int, cs: String): String = {
    encode(n, cs.toList).mkString
  }

  def percent(a: Int, b: Int): Float = {
    round((a.toFloat / b.toFloat) * 100.0f, 2)
  }

  private def round(n: Float, scale: Int): Float = {
    BigDecimal(n.toDouble)
      .setScale(scale, BigDecimal.RoundingMode.HALF_UP)
      .toFloat
  }

  private val round1 = (n: Float) => round(n, 1)

  private def count(c: Char, cs: List[Char]): Int = cs.count { _ == c }
  private def lowers(xs: List[Char]): Int = xs.count(isLower)

  def freqs(cs: List[Char]): List[(Char, Float)] = {
    val letters = ('a' to 'z').toList
    val n = lowers(cs)
    letters.map(x => x -> percent(count(x, cs), n))
  }

  // A standard method for comparing a list of observed frequencies os with a list
  // of expected frequencies es is the chi-square statistic.
  def chiSquare(os: List[Float], es: List[Float]): Float = {
    val chiFun = (o: Float, e: Float) => round1(math.pow(o - e, 2).toFloat)
    (os zip es).map(chiFun.tupled).sum
  }

  def rotate[A](n: Int, xs: List[A]): List[A] = xs.drop(n) ::: xs.take(n)

  // A function that returns the list of all positions
  // at which a value occurs in a list, by pairing each
  // element with its position
  def positions[A](a: A, xs: List[A]): List[Int] = {
    xs.zipWithIndex.collect { case (`a`, k) => k }
  }

  def solutions(cs: List[Char]): List[(Float, Int)] = {
    val tablePrime = freqs(cs).map { case (_, fr) => fr }
    val f = (n: Int) => chiSquare(rotate(n, tablePrime), table)
    (0 to 25).toList.map(f).zipWithIndex.sorted.take(1)
  }

  def crack(msg: String): String = {
    val cs: List[Char] = msg.toList
    val (_, factor) :: _ = solutions(cs)
    encode(-factor, msg)
  }
}
