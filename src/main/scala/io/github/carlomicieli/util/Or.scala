package io.github.carlomicieli.util

sealed trait Or[+A, +B] {
  def isBad: Boolean
  def isGood: Boolean

  def get: A

  def map[C](f: A => C): Or[C, B] = this match {
    case Bad(_)  => this.asInstanceOf[Or[C, B]]
    case Good(v) => Good(f(v))
  }

  def flatMap[C, D >: B](f: A => Or[C, D]): Or[C, D] = this match {
    case Bad(_)  => this.asInstanceOf[Or[C, D]]
    case Good(v) => f(v)
  }

  def orElse[D](v: => D): Or[A, D] =
    if (isGood)
      this.asInstanceOf[Or[A, D]]
    else
      Bad(v)
}

case class Good[A, B](value: A) extends Or[A, B] {
  def isBad = false
  def isGood = true
  def get = value
}

case class Bad[A, B](value: B)  extends Or[A, B] {
  def isBad = true
  def isGood = false
  def get: A = throw new NoSuchElementException("Or.get: is empty")
}