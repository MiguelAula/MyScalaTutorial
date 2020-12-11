package theory

/**
 *  Collections in scala are divided in 3 types: sequences, sets and maps
 */
object Collections {
  val seq: Seq[Int] = Seq(1,2,3,3)
  val set: Set[Int] = Set(1,2,3,3)
  val map: Map[Int,String] = Map(1 -> "one",2 -> "two", 3 -> "three", 3 -> "three")

  def main(args: Array[String]): Unit = {
    println(s"seq: $seq")
    println(s"set: $set")
    println(s"map: $map")

    /* After executing: notice how duplicates are ignored in "Set" and "Map" */
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

  import scala.collection.immutable.Queue
  /** Queue is not part of the global scala package, so we have to make an import.
   *  Also notice we can choose to import it from the mutable or the immutable package */
  val queue: Queue[Int] = Queue(1,2,3,4,5)

  def main(args: Array[String]): Unit = {
    println(list)
    println(array.mkString("Array(", ", ", ")"))
    println(vector)
    println(range)
    println(queue)
  }
}

/**
 * Here we showcase the most basic sequence methods
 */
object BasicSequenceMethods extends App {
  def map[A,B](list: List[A])(f: A => B): List[B] = ???
  def flatten[A](list: List[List[A]]): List[A] = ???
  def flatMap[A,B](list: List[A])(f: A => List[B]): List[B] = flatten(map(list)(f))

  val nums: Seq[Int] = Seq(1,2,3,4,5)

  /* map
      It allows you to transform a Seq[A] into an Seq[B] by applying a given function to each of its elements
   */
  val seqPlus1 = nums.map(n => n + 1)
  val seqPlus1_2 = nums.map(_ + 1)

  println(s"seqPlus1: $seqPlus1")
  println(s"seqPlus1_2: $seqPlus1_2")

  /* flatten
      It turns a sequence of sequence into a single sequence.
   */
  val seqOfSeqs = Seq(Seq(1,2),Seq(3,4),Seq(5))
  println(s"flattened seqOfSeqs: ${seqOfSeqs.flatten}")

  val seqWithSeqs = Seq(1,2,Seq(3,4),Seq(5))
  //def flattenSeqWithSeqs[A]()

  /*flatMap

   */
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

object Streams {
  val lazyList: LazyList[Int] = LazyList.continually(1)

  def main(args: Array[String]): Unit = {
    println(lazyList.take(20))
    println(lazyList.take(20).toList)
  }
}
