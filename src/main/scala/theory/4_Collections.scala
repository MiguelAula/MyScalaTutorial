package theory

import scala.annotation.tailrec

/**
 *  Collections in scala are divided in 3 types: sequences, sets and maps
 *
 *  Sequence: it's the most commonly used collection, it allows repeated values
 *  Set: you can't insert the same value twice in a set
 *  Map: it's a key-value set, the keys must be unique
 */
object Collections {
  //https://docs.scala-lang.org/overviews/collections/overview.html
  val seq: Seq[Int] = Seq(1,2,3,3)
  val set: Set[Int] = Set(1,2,3,3)
  val map: Map[Int,String] = Map(1 -> "one",2 -> "two", 3 -> "three", 3 -> "three again")

  def main(args: Array[String]): Unit = {
    println(s"seq: $seq")
    println(s"set: $set")
    println(s"map: $map")

    /* After executing: notice how duplicates are ignored in "Set" and how "Map" overrides the value if you try to duplicate a key */
  }
}

/**
 * Here we showcase the most common sequence implementations in the scala std library.
 * Note that, with the exception of Array, all the structures used below are immutable BUT most can be made mutable if desired.
 */
object SequenceImplementations {
  val list: List[Int] = List(1,2,3,4,5)       //fastest for simple use cases where random access is not needed.
  val array: Array[Int] = Array(1,2,3,4,5)    //fastest for random access. Beware! An array is MUTABLE by implementation.
  val vector: Vector[Int] = Vector(1,2,3,4,5) //fastest for big and complex parallel algorithms.
  val range: Range = 1 to 5                   //commonly used to perform iterations

  val string: String = "string is actually implemented as an indexed sequence!"

  /** Since Queue is not part of the global scala package, so we have to import it. Also notice we can choose to import it from the mutable or the immutable package.
   *  As always, unless we have a specific reason to do the opposite, we will import the immutable version for FP reasons. */
  import scala.collection.immutable.Queue

  val queue: Queue[Int] = Queue(1,2,3,4,5)

  def main(args: Array[String]): Unit = {
    println(list)
    println(array)
    println(array.mkString("Array(", ", ", ")"))  //to print an array in a user friendly manner we need the mkString function
    println(vector)
    println(range)
    println(queue)
  }
}

/**
 * Here we showcase the most basic sequence methods
 */
object BasicSequenceMethods extends App {
  val nums: Seq[Int] = Seq(1,2,3,4,5)

  /* map
      It allows you to transform a Seq[A] into an Seq[B] by applying a given function A => B to each of its elements
   */
  def map[A,B](seq: Seq[A])(f: A => B): Seq[B] = seq match {
    case head :: tail => f(head) +: map(tail)(f)  /* this is equivalent to: map(tail)(f).+:(f(head)) */
    case Nil => Nil
  }

  val seqPlus1 = nums.map(n => n + 1)
  val seqPlus1_2 = nums.map(_ + 1)

  println(s"seqPlus1: $seqPlus1")
  println(s"seqPlus1_2: $seqPlus1_2")

  /* flatten
      It turns a sequence of sequence into a single sequence.
   */
  def flatten[A](seq: Seq[Seq[A]]): Seq[A] = seq match {
    case subSeq :: tail => subSeq match {
      case head :: subTail => head +: flatten(subTail +: tail)
      case Nil => flatten(tail)
    }
    case Nil => Nil
  }

  val seqOfSeqs = Seq(Seq(1,2),Seq(3,4),Seq(5))
  println(s"flattened seqOfSeqs: ${seqOfSeqs.flatten}")

  val seqWithSeqs = Seq(1,2,Seq(3,4),Seq(5))
  //def flattenSeqWithSeqs[A]()

  /*flatMap
    It allows you to turn a Seq[A] into a Seq[B] providing a function that turns every element of the Seq[A] into a whole Seq[B]
   */
  def flatMap[A,B](list: Seq[A])(f: A => Seq[B]): Seq[B] = flatten(map(list)(f))

  val seqToChar = nums.flatMap(n => Seq(n,n))
  println(s"seqToChar: $seqToChar")

  //head / tail
  val firstVal = nums.head
  val restOfVals = nums.tail
  println(s"firstVal: $firstVal")
  println(s"restOfVals: $restOfVals")

  //reduce
  val sub = nums.reduce((v1,v2) => v1 - v2)
  val sub2 = nums.reduce(_ - _)
  val sum = nums.reduce(_ + _)
  println(s"sub: $sub")
  println(s"sub2: $sub2")
  println(s"sum: $sum")

  //filter
  val lessThan3 = nums.filter(_ < 3)
  println(s"filtered: $lessThan3")

  //sortWith
  val sortFromBigger = nums.sortWith(_ > _)
  println(s"sortFromBigger: $sortFromBigger")

  //take
  val takeTwo = nums.take(2)
  println(s"takeTwo: $takeTwo")

  //drop
  val dropTwo = nums.drop(2)
  println(s"dropTwo: $dropTwo")

  println(s"originalList: $nums") //original list remains unchanged
}

/**
 *  Streams (or LazyLists since Scala 2.13) are sequences capable of containing infinite amounts of data virtually. This is possible because of their
 *  lazy nature, which means they don't evaluate the next item (or even if there IS a next item) of the list until it is required.
 */
object Streams {
  val lazyList: LazyList[Int] = LazyList.continually(1)

  def main(args: Array[String]): Unit = {
    println(lazyList.take(20))
    println(lazyList.take(20).toList)
  }
}
