/*
  WELCOME TO MIKI'S SCALA TUTORIAL!

  First of all let's take a look at a brief introduction to the basic principles of functional programming:

  - Functional programming is a paradigm, or style, that values immutability, first-class functions, referential transparency, and pure functions.

  Wow! A lot of new concepts right? Let's do a quick review of each one:


  1) Immutability:

    This principle refers to the data used in programs. It means the data content is not modified, which means that you (mostly) work with static
    values instead of variables inside your programs. This is most relevant when working with data structures such as Arrays. Usually when you
    use .pop() on an array, the array itself gets modified as a result. However, if you use an immutable array you would get an other array with
    one less element.

    You might ask yourself: how is that efficient? Would this not mean we are constantly duplicating elements? Is that not counter productive?

    Well, it can be implemented in an efficient way. For example if you have a list stored in an immutable value and you want to remove the first element,
    you could just return a new pointer that points to the second element of the list. So you still maintain the first list immutable and get a second
    immutable list for free! So you are not necessarily duplicating your existing data.


  2) First-class functions:

  In functional programming, our functions are first-class, which means we can use them like any other value. We can create arrays of functions, pass
  them as arguments to other functions, and store them in variables.


  3) Referential transparency:

    An expression is called referentially transparent if it can be replaced with its corresponding value (and vice-versa) without changing the program's
    behavior. This requires that the expression be PURE, that is to say the expression value must be the same for the same inputs and its evaluation must
    have NO SIDE EFFECTS.


  4) Pure functions:

    I like to think of functions as factory machines — they take an input, or arguments, and then output something, the return value. Pure functions
    don’t have ‘side effects’ or actions that don’t relate to the output of the function. Some potential side effects would be printing a value or
    logging it out, or manipulating variables outside the function.



  To close it up let's take a look at a bit of history:

  Functional programming evolved from lambda calculus, a mathematical system built around function abstraction and generalization. As a result, a
  lot of functional programming languages look very mathematical. That being said, the functional programming principles can also be applied in non
  functional programming language (e.g. JavaScript)



  Alright! That is all you need to know about functional programming for this tutorial. Let's dive right into Scala!

 */

/**
 *  Objects are basically singleton classes which are automatically instantiated.
 *  They can be run if we define a "main" method for them
 */
object Object {
  def main(args: Array[String]): Unit = {
    println("Hello! I'm a runnable object!")
  }
}

/**
 * An object can also be run if you make it inherit from the "App" object.
 */
object MainObject extends App {
  println("Hello! I'm another runnable object!")
}

/**
 * mutability: val / var / def
 */
object Mutability {

  var a = 1
  val b = 1
  def c = 1

  def main(args: Array[String]): Unit = {
    a = 2
    /** the following 2 lines do not compile since "val" and "def" ar non-mutable */
    //b = 2
    //c = 2
  }

  /*  DID YOU KNOW THAT...
  Immutability helps a great deal with referential transparency, one of the basis of functional programming, because it allows us to pass a value
  around different contexts knowing it will NEVER be changed and thus preserving the program behaviour for any given input.
   */
}

/**
 * evaluation and lazyness: val / lazy val / def
 */
object Evaluation {

  val a = println("evaluated right here")
  lazy val b = println("will be evaluated when used for the first time")
  def c = println("will be evaluated every time it is used")

  def doNothing(params: Unit*): Unit = ()

  def main(args: Array[String]): Unit = {
    /**
    If you run the empty main you will only see the "a" value being printed, since it is the only one being
    evaluated as soon as it's defined when the "Evaluation" object is instantiated.

    If you uncomment the doNothing functions you will see how "b" is only evaluated once and "c" is evaluated twice, as expected.
     */

    //doNothing(a,b,c)
    //doNothing(a,b,c)
  }
}

/**
 *  If / else expressions are functions that execute all the code inside their blocks and automatically return the last statement
 */
object IfExpressions {
  /* you make an import at any given point in your code, although the convention is to perform all imports at the beginning of the file */
  import scala.util.Random

  var int: Int = Random.nextInt() //get a random int
  val str1: String = if (int > 0) {
    "I can do anything before the last statement but, unless it's a side effect, it will be completely ignored"
    "positive int"
  } else if (int < 0) {
    "negative int"
  } else {
    "zero"
  }

  int = Random.nextInt()  //get another random int
  /* if-else statements can be one-liners: */
  val str2: String = if (int > 0) "positive number" else if (int < 0) "negative number" else "zero"

  def main(args: Array[String]): Unit = {
    println(str1)
    println(str2)
  }
}

/**
 *  This is a first insight on pattern matching. This is definitely not the best use case for it though. We will see better cases later on.
 */
object BasicPatternMatching {
  import scala.util.Random
  var int: Int = Random.nextInt()

  val str: String = int match {
    case posNum if posNum > 0 => "positive number"
    case negNum if negNum < 0 => "negative number"
    case 0 => "zero"
  }

  def main(args: Array[String]): Unit = {
    println(str)
  }

  /*
    DID YOU KNOW...

    In the last case of the pattern matching you could also use:
      case z => "zero"
    or
      case _ => "zero"

    these two alternatives would act as a "default" case for the pattern-matching


    Here the _ character refers to an anonymous case parameter, and it is used to define a parameter that we don't intend to use further in the code.

    The _ character has a few more use cases in scala, we will see them later in this tutorial...
     */
}

