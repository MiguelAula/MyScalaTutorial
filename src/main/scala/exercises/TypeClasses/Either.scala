package exercises.TypeClasses

/**
 *  Define the 'Either' trait.
 *
 *  Define the unit, map and flatMap functions for this trait.
 */
sealed trait Either[+E,+A] {
  def map[B](f: A => B): Either[E,B] = ???
  def flatMap[EE >: E,B](f: A => Either[EE,B]): Either[EE,B] = ???
}
object Either {
  def unit[A](a: A): Either[Nothing,A] = ???
}
final case class Right[+V](value: V) extends Either[Nothing,V]
final case class Left[+E](err: E) extends Either[E,Nothing]
