object DefiningFunctions {
  //named func
  def countChars(str: String): Int = str.length
  //anonymous func
  def f: String => Int = (str: String) => str.length
  //function as value
  val f2: String => Int = (str: String) => str.length

  /*
  Functions can have any number of parameters of any types.
  Also if you want the code to compile but you don't have an implementation for a function or a value yet, you can use ???
   */
  def functionXL(a: Int,b: String,c: Unit,d: (Double,Float),e: Int => String): Unit = ???

  /*
  You can parame
   */

  def main(args: Array[String]): Unit = {
    println(countChars("I have 15 chars"))
    println(f("I have 15 chars"))
    println(f2("I have 15 chars"))
  }
}

object ComposingFunctions {
  /* Here we aim to create a string formatter */
  val alphanumeric = (('a' to 'z') ++ ('A' to 'Z') ++ ('0' to '9')).toSet + ' '
  def isAlphanumeric(c: Char): Boolean = alphanumeric.contains(c)
  def onlyAlphanumeric(s:String): String = s.filter(isAlphanumeric)
  def toUpper(str: String): String = str.toUpperCase
  def fixSpaces(str: String): String = str.trim.replaceAll("\\s+"," ")

  def formatString = onlyAlphanumeric _ andThen toUpper andThen fixSpaces
  def formatString2 = onlyAlphanumeric _ compose toUpper compose fixSpaces

  /*
  Ordering using andThen: f(x) andThen g(x) = g(f(x))
  Ordering using compose: f(x) compose g(x) = f(g(x))
   */

  def main(args: Array[String]): Unit = {
    println(formatString("  /·(=%=! this =)()   string HaS    ??¿ BeeN FiXeD!!!!!!!    "))
  }
}

/**
 *  Partial functions are functions that only work properly for a subset of their inputs.
 */
object PartialFunctions {
  def divide100(denom: Int): Int = 100 / denom

  val divideP = new PartialFunction[(Int,Int), Int] {
    def apply(num: Int, denom: Int) = num / denom
    def isDefinedAt(params) = x != 0
  }

  def divide(num: Int, denom: Int): Int = num / denom

  val divideP = new PartialFunction[(Int,Int), Int] {
    def apply(num: Int, denom: Int) = num / denom
    def isDefinedAt(params) = x != 0
  }

  def main(args: Array[String]): Unit = {
    println(divide(2,3))
    println(divide(2,0))
  }
}

/**
 *  Option and Either are good solutions to transform a partial function into a pure one.
 *  Option is used if you do NOT need any additional information when you have no result. If you do, you can use Either to propagate information
 *  about the wrong result.
 */
object UsingOptionEither {
  def divideO(num: Int, denom: Int): Option[Int] = if (denom != 0) Some(num / denom) else None

  def divideE(num: Int, denom: Int): Either[Throwable,Int] = if (denom != 0) Right(num / denom) else Left(new Throwable("Denominator cannot be zero!"))

  def main(args: Array[String]): Unit = {
    println(divideO(2,3))
    println(divideO(2,0))

    println(divideE(2,3))
    println(divideE(2,0))
  }
}

/**
 *  We can use pattern-matching to reason about the result of a function that returns an Option or an Either
 */
object PatternMatching {
  /*
  We can import the divide functions from the UsingOptionEither object to use them here...
   */
  import UsingOptionEither.divideO
  import UsingOptionEither.divideE

  /*
  Let's reason about the Option / Either results defining a printer for our division functions...

  Notice how we can have two methods with the same name as long as their signature is different.
  Type inference should take care of which one is called.
   */
  def printer(res: Option[Int]): String = res match {
    case Some(value) => value.toString
    case None => "There is no result for the given input"
  }
  def printer(res: Either[Throwable,Int]): String = res match {
    case Left(exception) => exception.getMessage
    case Right(value) => value.toString
  }

  /*
    Here we can't use composition to create a printableDivision.
    That is because "andThen"/"compose" only work for functions with 1 single argument!
   */
  //def printableDivisionO = divideO _ andThen printer

  /*
  We have to compose it the old fashioned way... Notice how the type inferrer knows which printer to use in each case here...
   */
  def printableDivisionO(num: Int, denom: Int): String = printer(divideO(num,denom))
  def printableDivisionE(num: Int, denom: Int): String = printer(divideE(num,denom))

  def main(args: Array[String]): Unit = {
    println(printableDivisionO(2,3))
    println(printableDivisionO(2,0))

    println(printableDivisionE(2,3))
    println(printableDivisionE(2,0))
  }
}

/**
 *
 */
object Abstraction {

}

object Implicits {

}

object MoreSequenceMethods {
  //lift
  val wtf = List(1,2).lift
  val first = wtf(0)
  val second = wtf(1)
  println(s"first: $first")
  println(s"second: $second")
}

