package exercises.TypeClasses

/**
 *  Consider the 'Option' trait, it's companion object and the 'Some' and 'None' case class/object.
 *
 *  Important: consider making Option covariant on its parametric type. This will allow scala to identify Option[Nothing] as a subclass of Option[A].
 *  If you skip this you will get a compiler error when implementing some of the next exercises. You can try this experiment if you wish.
 */

/**
 *  - Implement the get method:
 *    It returns the value inside the option if it exists, otherwise it throws an exception.
 *
 *    def get: A
 */

/**
 *  - Implement the getOrElse method:
 *     It returns the value inside the option if exists, otherwise it runs the function given as a parameter to the getOrElse method
 *
 *  def getOrElse[B >: A](f: => B)
 */

/**
 *  - Define the unit function for Option:
 *    It defines an Option wrapper for a given A type
 *
 *  def unit[A](a: A): Option[A]
 */

/**
 *  - Define the map and flatMap functions for Option. Can you implement map in terms of flatMap and unit?
 */

/**
 *  - We have a solid enough Option implementation, play around with it a little. Here are some example exercises:
 *
 *  a) Build an Option that contains a List of integers (make some of them have more ciphers than others e.g: 3,53,262,3332)
 *  b) Obtain an Option that contains a list with the string values of those integers.
 *  c) Obtain an Option that contains a list with the number of ciphers that each number had.
 *  d) Obtain an Option that contains the previous list if any of the numbers on the list has at least 5 ciphers and None otherwise.
 *  e) Make a boolean val that will hold true if the final Option contained a value and False otherwise.
 *  f) Make a one-line program that prints the Option content if it has any, or prints nothing otherwise.
 *  g) What happens if we make the original option in 'a' a None? Can this somehow break our execution process? If it did, how could we avoid it?
 *  (Tip: do not use the get function)
 */

sealed trait Option[+A] {
  def get: A = ???
  def getOrElse[B >: A](f: => B): B = ???
  def map[B](f: A => B): Option[B] = ???
  def flatMap[B](f: A => Option[B]): Option[B] = ???
}
object Option {
  def unit[A](a: A): Option[A] = ???
}
final case class Some[A](value: A) extends Option[A]
object None extends Option[Nothing]
