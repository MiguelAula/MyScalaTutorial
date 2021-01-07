package theory

object ForComprehensions {
  /**
   * Imagine we have a matrix that stores the amount of cm³ of water that hold certain landmarks in Girona.
   */
  val matrix: List[List[Double]] =
    List(
      List(0.5,3,5.2),
      List(1.2,6.2,0.1),
      List(9.2,2.6,3.9)
    )

  /**
   * It just rained 3 cm³ in all of Girona, which means we need a mean to update our matrix. Let's define a function that does this:
   */
  def updateMatrix(matrix: List[List[Double]])(op: Double => Double): List[List[Double]] = matrix.map(_.map(op(_)))

  val mat2: List[List[Double]] = updateMatrix(matrix)(_ + 3)

  /**
   * We need to be able to pour all of the water into any slot of the matrix to get it ready for collection
   */
  def pourTo(matrix: List[List[Double]], slot: (Int,Int)): List[List[Double]] = {
    val acc = matrix.foldLeft(0.0)((n,l) => l.sum + n)
    matrix.zipWithIndex.map { case (row, i) =>
      row.zipWithIndex.map { case (_, j) =>
        if (slot != (i, j)) 0
        else acc
      }
    }
  }

  /**
   * We need to be able to pour all of the water into any slot of the matrix to get it ready for collection
   */
  def pourToV2(matrix: List[List[Double]], slot: (Int,Int)): List[List[Double]] = {
    val mutMatrix = matrix.map(l => l.toArray).toArray
    matrix.zipWithIndex.foreach { case (row, i) =>
      row.zipWithIndex.foreach { case (_, j) =>
        if (slot != (i, j)) {
          matrix.updated(i,row.updated(j,0))
        }
        else matrix.updated(i,row.updated(j,0))
      }
    }
    mutMatrix.map(arr => arr.toList).toList
  }

  def main(args: Array[String]): Unit = {
    /*val a = for {
      i <- 1 to 5
      j <- 1 to 2
    } yield (i,j)
    println(a)*/
    println(
      pourTo(matrix,(0,1))
    )
  }
}

