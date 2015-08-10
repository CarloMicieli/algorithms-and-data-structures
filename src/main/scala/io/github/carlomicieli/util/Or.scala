package io.github.carlomicieli.util

sealed trait Or[A, B] {
  def isBad: Boolean
  def isGood: Boolean

  def get: A

  def map[C](f: A => C): Or[C, B] = this match {
    case Bad(_)  => this.asInstanceOf[Or[C, B]]
    case Good(v) => Good(f(v))
  }

  def flatMap[C](f: A => Or[C, B]): Or[C, B] = this match {
    case Bad(_)  => this.asInstanceOf[Or[C, B]]
    case Good(v) => f(v)
  }

  def orElse[C](v: => C): Or[C, B] =
    if (isGood)
      this.asInstanceOf[Or[C, B]]
    else
      Good(v)
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