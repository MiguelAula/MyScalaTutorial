package theory

object MoreSequenceMethods extends App {
  val seq = Seq(1,2,3)

  //lift: turn a Seq[A] into an Int => Option[A]
  val liftedSeq = seq.lift
  println(seq(2))
  //println(list(3))  //IndexOutOfBoundsException
  println(liftedSeq(2))
  println(liftedSeq(3))
}

object Fold {

}

object Scan {

}
