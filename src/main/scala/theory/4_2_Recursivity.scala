package theory

import scala.annotation.tailrec

/**
 *  Here's a little set of instructions to get you in the "recursive" mood in case you are not used to it.
 */
object Recursivity extends App {
  /**
   * The factorial is function that recieves an Int "n" and returns the result of multiplying (n * n-1 * n-2 * ... * 1). See the pattern?
   * In other words, you could say the factorial is the result of multiplying "n" by the result of the factorial of "n-1", right? Ok. Let's code this now!
   */
  def factorial_v1(n: Int): Int = n * factorial_v1(n-1)

  /**
   *  Easy enough right? Well actually no, because this function will run forever! There is no case where the function stops calling itself!
   *  So we need to tell this function when to stop. This is what is known as the "base case" of the recursive function.
   *  In this case, when would the recursivity end? Well, according to the factorial definition, it ends when n == 1, right?
   *  So let's add this logic to our code now:
   */
  def factorial_v2(n: Int): Int =
    if (n > 1) n * factorial_v2(n-1)
    else if (n == 1) 1  //here we do not call the factorial function anymore. We return the result of factorial(1) instead, which would be 1.
    else ???

  /**
   * Alright so we are done now right? Nope! We need to take care of the edge cases now. There is a factorial detail I have omitted until now which is:
   * factorial(0) == 1 !! Our recursive case nor our base case cover this edge case. So we need to add extra logic for it:
   */
  def factorial_v3(n: Int): Int =
    if (n > 1) n * factorial_v3(n-1)
    else if (n == 1 || n == 0) 1
    else ???


  /**
   *  Ok, the recursive part is finished now. But you might have noticed that this function is not defined for all inputs. This is a hidden partial function.
   *  What can we do about this? Well the most common thing to do would be to make and Option out of the output to turn it into a complete and pure function.
   */
  def factorial(n: Int): Option[Int] =
    if (n > 1) Some(n * factorial(n-1).get)
    else if (n == 1 || n == 0) Some(1)
    else None

  /**
   * Or... we could turn it into an actual partial function like this:
   */
  def factorialP: PartialFunction[Int,Int] = new PartialFunction[Int, Int] {
    def isDefinedAt(n: Int): Boolean = n >= 0
    def apply(n: Int): Int =
      if (n > 1) n * factorialP.apply(n-1)
      else if (n == 1 || n == 0) 1
      else throw new Exception("Unreachable code!")
  }

  val n = -10
  /**
   * And now you can call it with a callback function in case it's not defined like this:
   */
  println(
    if (factorialP.isDefinedAt(n)) factorialP.apply(n)
    else s"factorial is not defined for $n"
  )
  val n2 = 10
  println(
    if (factorialP.isDefinedAt(n2)) factorialP.apply(n2)
    else s"factorial is not defined for $n2"
  )

  /**
   * but what would happen if we tried to execute the factorial of a high enough number? Like 10000?
   */
  //factorial(10000)

  /**
   * Oops! We get a stack overflow error. This is because each time we call the function recursively, its parameters get added to the stack, which is limited.
   * With enough recursive calls we can easily overflow it. Is all hope lost? No! There is a way to program recursion in scala which does not make use of the
   * stack: tail recursion. A function is considered tail recursive when the recursive call is the VERY LAST thing called inside the function logic. For that
   * we can make use of an extra parameter that will accumulate the results of previous recursive calls like so:
   */
  def factorialTR(n: Int): Option[Int] = {
    @tailrec
    def loop(n: Int, acc: Int): Option[Int] =
      if (n > 1) loop(n-1,acc * n)
      else if (n == 1 || n == 0) Some(acc)
      else None
    loop(n,1)
  }

  /**
   * And now the scala compiler will turn this into a while loop, making it stack-safe (although, in this particular case, the correct result will not show because
   * such a big factorial does not fit inside an Int)
   */
  println(factorialTR(10000))
}
