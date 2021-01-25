package theory

object LessonsRemaining {
  //GENERATORS
  //PROPERTY BASED TESTING

  //GENERALIZATION: FUNCTORS, APPLICATIVES, MONOIDS, MONADS

  val suma = (a: Int, b: Int) => a + b
  val resta = (a: Int, b: Int) => a - b

  trait Monoid[A] {
    def op_v2: (A,A) => A
    def op(a: A, b: A): A
  }

  //suma
  val myMonoid: Monoid[Int] = new Monoid[Int] {
    override def op_v2: (Int, Int) => Int = resta

    override def op(a: Int, b: Int): Int = a + b
  }

  myMonoid.op(1,3)
  myMonoid.op_v2(1,3)


}
