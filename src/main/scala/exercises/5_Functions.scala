package exercises


object FunctionExercises extends App {
  /**
   *  0) The msort function below implements a merge-sort on a list given a comparison function lt.
   *  Try to understand its implementation.
   */
  def basic_msort[A](l: List[A])(lt: (A,A) => Boolean): List[A] = {
    val n = l.length / 2
    if (n == 0) l
    else {
      def merge(l1: List[A],l2: List[A]): List[A] = (l1,l2) match {
        case (h1,Nil) => h1
        case (Nil,h2) => h2
        case (h1 :: t1, h2 :: t2) =>
          if (lt(h1,h2)) h1 :: merge(t1,l2)
          else h2 :: merge(t2,l1)
      }
      val (part1, part2) = l.splitAt(n)
      merge(basic_msort(part1)(lt),basic_msort(part2)(lt))
    }
  }

  /**
   * 1) scala.math contains an object called 'Ordering'. This is a parametrized object 'Ordering[A]' that provides an 'lt' function for any parametric [A] type.
   * Inside the 'Ordering' object you will find a value for every simple scala type: Ordering.Int, Ordering.Char, Ordering.String, etc.
   *
   * Rewrite the msort function using the Ordering object from scala.math and its comparison function
   */
  def ord_msort[A](l: List[A])(ord: Ordering[A]): List[A] = {
    val n = l.length / 2
    if (n == 0) l
    else {
      def merge(l1: List[A],l2: List[A]): List[A] = (l1,l2) match {
        case (h1,Nil) => h1
        case (Nil,h2) => h2
        case (h1 :: t1, h2 :: t2) =>
          if (???) h1 :: merge(t1,l2)
          else h2 :: merge(t2,l1)
      }
      val (part1, part2) = l.splitAt(n)
      merge(basic_msort(part1)(???),basic_msort(part2)(???))
    }
  }

  /**
   * 2) Sort these lists using the ord_msort method.
   */
  val l1 = List(3,5,1,4,2)
  val l1sorted = ???
  assert(l1sorted == List(1,2,3,4,5))

  val l2 = List('a','j','z','d')
  val l2sorted = ???
  assert(l2sorted == List('a','d','j','z'))

  val l3 = List("pineapple","pear","apple","banana")
  val l3sorted = ???
  assert(l3sorted == List("pear","apple","banana","pineapple"))

  /**
   * 3) Make the ordering parameter implicit. What changes?
   */
  def msort[A](l: List[A])(ord: Ordering[A]): List[A] = {
    val n = l.length / 2
    if (n == 0) l
    else {
      def merge(l1: List[A],l2: List[A]): List[A] = (l1,l2) match {
        case (h1,Nil) => h1
        case (Nil,h2) => h2
        case (h1 :: t1, h2 :: t2) =>
          if (???) h1 :: merge(t1,l2)
          else h2 :: merge(t2,l1)
      }
      val (part1, part2) = l.splitAt(n)
      merge(basic_msort(part1)(???),basic_msort(part2)(???))
    }
  }

  /**
   * 4) Sort these lists now using the new msort with implicit ordering
   */
  val l4 = List(3,5,1,4,2)
  val l4sorted = ???
  assert(l1sorted == List(1,2,3,4,5))

  val l5 = List('a','j','z','d')
  val l5sorted = ???
  assert(l5sorted == List('a','d','j','z'))

  val l6 = List("pineapple","pear","apple","banana")
  val l6sorted = ???
  assert(l6sorted == List("pear","apple","banana","pineapple"))

  /** ---------------------------------------------------------- */

  /**
   * 5) Make a partial function that multiplies its input by 2. The allowed inputs are all positive numbers (including 0).
   */
  val multPos = new PartialFunction[Int, Int] {
    override def isDefinedAt(x: Int): Boolean = ???

    override def apply(v1: Int): Int = ???
  }

  /**
   * 6) Make a partial function that multiplies its input by 2. The allowed inputs are all negative numbers.
   */
  val multNeg = new PartialFunction[Int, Int] {
    override def isDefinedAt(x: Int): Boolean = ???

    override def apply(v1: Int): Int = ???
  }

  /**
   * 7) Combine partial functions from 5) and 6) into a new function defined for all integers. Test it.
   */
  val mult = ???
}



