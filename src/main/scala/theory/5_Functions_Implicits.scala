package theory

import theory.caseClasses.{Cat, Dog}

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
  As we seen in the types section, you can parametrize on the type of a function like this:
   */
  def getHead[A](list: List[A]): A = list.head

  def main(args: Array[String]): Unit = {
    println(countChars("I have 15 chars"))
    println(f("I have 15 chars"))
    println(f2("I have 15 chars"))
  }
}

/**
 *  In this section we will learn about function composition while trying to create a more complex string formatter function using simple
 *  formatting functions.
 */
object ComposingFunctions {
  val alphanumericSet: Set[Char] = (('a' to 'z') ++ ('A' to 'Z') ++ ('0' to '9')).toSet + ' '
  def isAlphanumeric(c: Char): Boolean = alphanumericSet.contains(c)
  def filterAlphanumeric(s:String): String = s.filter(isAlphanumeric)
  def toUpper(str: String): String = str.toUpperCase
  def fixSpaces(str: String): String = str.trim.replaceAll("\\s+"," ")

  def formatString = filterAlphanumeric _ andThen toUpper andThen fixSpaces
  def formatString2 = filterAlphanumeric _ compose toUpper compose fixSpaces

  /*
  Ordering using andThen: f(x) andThen g(x) == g(f(x))
  Ordering using compose: f(x) compose g(x) == f(g(x))
   */

  def main(args: Array[String]): Unit = {
    println(formatString("  /·(=%=! this =)()   string HaS    ??¿ BeeN FiXeD!!!!!!!    "))
    println(formatString2("  /·(=%=! this =)()   string HaS    ??¿ BeeN FiXeD!!!!!!!    "))
  }
}

/**
 *  Partial functions are functions that only work properly for a subset of their inputs.
 */
object PartialFunctions {
  /** Partial functions with 1 argument */
  def divide100(denom: Int): Int = 100 / denom

  val divide100p = new PartialFunction[Int, Int] {
    override def isDefinedAt(x: Int): Boolean = x != 0

    override def apply(v: Int): Int = 100 / v
  }

  /** Partial functions with multiple arguments */
  def divide(num: Int, denom: Int): Int = num / denom

  val divideP = new PartialFunction[(Int,Int), Int] {
    override def isDefinedAt(x: (Int, Int)): Boolean = x._2 != 0

    override def apply(v: (Int, Int)): Int = v._1 / v._2
  }

  val divideOption: ((Int, Int)) => Option[Int] = divideP.lift

  /** We could build a partial function so that it is defined for all numbers with the orElse method */
  val divideBy0 = new PartialFunction[(Int,Int), String] {
    override def isDefinedAt(x: (Int, Int)): Boolean = x._2 == 0

    override def apply(v: (Int, Int)): String = "Infinity"
  }

  val divideComplete = divideP orElse divideBy0

  def main(args: Array[String]): Unit = {
    println(s"divide (2/3): ${divide(2,3)}")
    //println(s"divide: ${divide(2,0)}")  //this throws runtime exception!
    println(s"divideP (2/0): ${divideP.applyOrElse((2,0), (params: (Int,Int)) => s"undefined for parameters ${params._1}/${params._2}")}")
    println(s"divideO (2/0): ${divideOption(2,0)}")

    println(s"divide (2/0): ${divideComplete(2,0)}")
  }
}

/**
 *  Option and Either are good solutions to transform a partial function into a pure one.
 *  Option is used if you do NOT need any additional information when you have no result. If you do, you can use Either to propagate information
 *  about the wrong result.
 */
object UsingOptionEither {
  def divideO(num: Int, denom: Int): Option[Int] =
    if (denom != 0) Some(num / denom)
    else None

  def divideE(num: Int, denom: Int): Either[Throwable,Int] =
    if (denom != 0) Right(num / denom)
    else Left(new Throwable("Denominator cannot be zero!"))

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
  We can import the divide functions from the theory.UsingOptionEither object to call them here directly
   */
  import UsingOptionEither.{divideE, divideO}

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

  /*printableDivisionE
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
 *  Notice how the previous printers are used only for Int and String ... would it be possible to generalize it for any given type? The answer is YES!
 *  Let's see how we could address this with the help of a new scala feature: implicits.
 *
 *  Scala has a way of automatically passing parameters to a function. If you want a parameter to be passed like this you must use the "implicit" keyword.
 *  Scala will then look for the appropriate implicit value to pass, which means you need to have that implicit value defined somewhere! Let's see how this works...
 */
object Implicits {
  import UsingOptionEither.{divideE, divideO}

  implicit val optionPrinter = (res: Option[Int]) => res match {
    case Some(value) => value.toString
    case None => "There is no result for the given input"
  }
  implicit val eitherPrinter = (res: Either[Throwable,Int]) => res match {
    case Right(value) => value.toString
    case Left(exception) => exception.getMessage
  }

  def print[A](it: A)(implicit printer: A => String): Unit = println(printer(it))


  /*
  But what happens if we try to print a type with no defined implicit? The scala compiler will throw an error, also IntellIJ warns us beforehand.
  NOTE: the "anyPrinter" implicit below must be commented to be able to see the warning

  If you don't have an implicit value available and you don't want to define it, you can also pass it explicitly!
  print(new Dog("Lucky","brown"))(
    (g: Dog) => s"One ${c.color} dog named ${c.name}"
  )
   */
  //print(new Dog("Lucky","brown"))

  /*
  This is usually enough, since at the moment of writing the code we should always know the types we are handling and if we come accross a type that is not defined
  for our print function, it is a sign that we should go to the file that contains the implicits and add it there.
   */
  implicit val catPrinter: Cat => String = (c: Cat) => s"One ${c.color} cat named ${c.name}"


  /*
    Nevertheless if, for some reason, we expect our print function to handle unexpected item types without giving out compilation error, we must define a default
    implicit for it:
   */
  implicit val anyPrinter = (_: Any) => "Unknown item"

  /**
  BEWARE: the implicit values MUST be defined_
   1) In the correct ORDER
   2) BEFORE the place where they are used.
  Otherwise, the scala compiler won't do his job properly:
   1) The order is important that it is set from more specific to less specific because scala assigns the first implicit that fits, which means that you would always assign
      the generic one to all cases.
   2) Also the placing before the usage is important because otherwise it won't compile!

  You can play around with the implicit declarations to test this behaviour.

  NOTE: The compiler looks for implicit members locally defined in the current or enclosing scopes, inherited, or imported.
   */
  def main(args: Array[String]): Unit = {
    /*
    Now, as long as we have a predefined printer for the type, we can use the print function:
     */
    print(divideE(1, 0))
    print(divideO(6, 2))

    print(Cat("Lucy","black"))

    print(new Dog("Lucky","brown"))
  }
}

/**
 * If you need access an implicit value you can use the "implicitly" keyword.
 * It is commonly used to check if an implicit value of type T is available in some context and return it if such is the case.
 */
object StringImpl {
  implicit val a: String = "test" // define an implicit value of type String
}

object Implicitly {
  import StringImpl._
  import theory.Implicits.catPrinter

  def main(args: Array[String]): Unit = {
    println(implicitly[String])
    val x = implicitly[Cat => String] // search for an implicit value of type Any => String
    println(x)
  }
}

/**
 *  The previous implicit example for a printer is ok. But what if somewhere else in our code we need an implicit T => String ? This would cause conflicts
 *  with our already defined implicits for the printer. This is when typeClasses come in handy:
 */
object TypeClasses extends App {
  sealed trait Printer[A] {
    def howToPrint(a: A): String
  }
  trait GenericPrinter {
    implicit def genericPrinter[A]: Printer[A] = new Printer[A] {
      override def howToPrint(x: A): String = x.toString
    }
  }
  object Printer extends GenericPrinter {
    /* Implicits */
    implicit val catPrinter: Printer[Cat] = new Printer[Cat] {
      override def howToPrint(c: Cat): String = s"One ${c.color} cat named ${c.name}"
    }
    implicit val dogPrinter: Printer[Dog] = new Printer[Dog] {
      override def howToPrint(d: Dog): String = s"One ${d.color} dog named ${d.name}"
    }
    /* Methods */
    def print[A](a: A)(implicit p: Printer[A]): Unit = println(p.howToPrint(a))
  }

  Printer.print(Cat("Lucas","grey"))
  Printer.print(new Dog("Tom","brown"))

  case class Person(name: String) //there is no concrete printer defined for this!
  Printer.print(Person("Peter"))
}

