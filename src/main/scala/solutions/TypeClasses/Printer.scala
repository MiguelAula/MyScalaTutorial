package solutions.TypeClasses

trait Printer[A] {
  def run(a: A): String
  def map[B](f: A => B): Printer[B] = this.flatMap(a => Printer.unit(a))
  def flatMap[B](f: A => Printer[B]): Printer[B] = ???
}
object Printer {
  def unit[A](a: A): Printer[A] = new Printer[A] {
    override def run(a: A): String = a.toString
  }
}
