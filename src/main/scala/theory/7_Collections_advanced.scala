package theory

import SharedVals._

import scala.annotation.tailrec

/**
 * We can make an object and import it on top of the file to make all vals and methods inside it accessible anywhere in the file.
 */
object SharedVals {
  val seq = Seq(1,2,3,4,5)
}

/**
 * lift: turn a Seq[A] into an Int => Option[A]
 *
 * Can be used to safely access sequence members by index.
 * */
object Lift {
  val liftedSeq: Int => Option[Int] = seq.lift

  def main(args: Array[String]): Unit = {
    println(
      "seq(3): " + seq(3)
    )
    //println(list(10))  //IndexOutOfBoundsException
    println(
      "liftedSeq(3): " + liftedSeq(3)
    )
    println(
      "liftedSeq(10): " + liftedSeq(10)
    )
  }
}

/**
 * fold: operate on the list members from the a chosen direction (right or left), and return the accumulated result
 */
object Fold {
  def main(args: Array[String]): Unit = {
    println(
      "foldLeft: " + seq.foldLeft(0)(_ + _)
    )
    println(
      "reduce: " + seq.reduce(_ + _)
    )
    /*
    Notice the difference between fold and reduce: reduce always returns the same type that the list has, fold doesn't have that restriction.
    This means that fold is a more generic function than reduce, which in turn means that we could implement reduce in terms of foldLeft (you will be asked
    to do this in the exercise section)
     */
    println(
      "foldLeft: " + seq.foldLeft("")(_ + _)
    )

    val toArrayFR = seq.foldRight(Array(): Array[Int])(_ +: _)
    println(
      "toArray(foldRight): " + toArrayFR.mkString("Array(", ", ", ")")
    )

    val toArrayFL = seq.foldLeft(Array(): Array[Int])((arr,x) => x +: arr).reverse
    println(
      toArrayFL.mkString("Array(", ", ", ")")
    )

  }
}

/**
 * The scan function works the same way as fold, but it returns an other sequence with the intermediate results
 */
object Scan {
  def main(args: Array[String]): Unit = {
    println(
      seq.scanLeft(0)(_ + _)
    )
  }
}

object Traverse {
  val seqOpt: Seq[Option[Int]] = Seq(Some(1),Some(2),Some(3),Some(4),Some(5))
  val seqOptNone: Seq[Option[Int]] = None +: seqOpt

  def traverse[A](s: Seq[Option[A]]): Option[Seq[A]] = {
    @tailrec
    def loop(s: Seq[Option[A]], acc: Option[Seq[A]] = Some(List())): Option[Seq[A]] =
      s match {
        case x :: xs =>
          x match {
            case Some(a) => loop(xs,Some(a +: acc.get))
            case None => None
          }
        case Nil => acc.map(_.reverse)
      }
    loop(s)
  }

  def main(args: Array[String]): Unit = {
    println(
      traverse(seqOpt)
    )
    println(
      traverse(seqOptNone)
    )
  }
}

object Sequence {
  def main(args: Array[String]): Unit = {
    println(
      //traverse(seqOpt)
    )
    println(

    )
  }
}
