package exercises

/**
 *  Here's a little set of exercises to get you in the "recursive" mood in case you are not used to it.
 */
object Recursivity {
  /**
   * The factorial is function that recieves an Int "n" and returns the result of multiplying (n * n-1 * n-2 * ... * 1).
   * The factorial of 0 is 1.
   *
   * Hint:
   *  1) recursive case
   *  2) base case + edge cases
   *  3) if necessary, make it complete
   */
  def factorial(n: Int): Int = ???

  /**
   * Sum all the elements from a List[Int].
   *
   * e.g. List(1,2,2) => 5
   *
   * Hint: you can use a match expression to reason about a list
   */
  def sumAll(list: List[Int]): Int = ???


  /**
   * Transform all the elements from a List[Int] into strings containing the same number of 'a's as the integer value.
   *
   * e.g. List(1,4,2) => List('a','aaaa','aa')
   */
  def mapToAs(list: List[Int]): List[String] = ???



  /**
   *  And now for the 10/10...
   */
  def map[A,B](list: List[A])(f: A => B): List[B] = ???

  def flatten[A](list: List[List[A]]): List[A] = ???

  def flatMap[A,B](list: List[A])(f: A => List[B]): List[B] = ???
}
