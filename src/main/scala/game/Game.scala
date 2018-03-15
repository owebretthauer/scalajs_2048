package game

import scala.util.Random

case class Board(state: List[List[Int]])

class Game {

  var board: Board = _
  var turns: Int = 0
  var points: Int = 0

  def turn(direction: Direction): Unit = {

    val newBoard: Board = direction match {
      case Left =>
        Board(for (s <- board.state) yield transform(s)(0))
      case Right =>
        Board(for (s <- board.state) yield transform(s)(1))
      case Up =>
        val r2c = for (s <- rows2cols(board.state)) yield transform(s)(0)
        Board(rows2cols(r2c))
      case Down =>
        val r2c = for (s <- rows2cols(board.state)) yield transform(s)(1)
        Board(rows2cols(r2c))
    }

    if (!this.board.equals(newBoard)) {
      this.board = newBoard
      this.turns += 1
      prepareNextTurn()
    }

  }

  def prepareNextTurn(): Unit = {
    addRandomNumber()
  }

  private def addRandomNumber(): Unit = {
    val spots = for {
      (row, i) <- board.state.zipWithIndex
      (_, j) <- row.zipWithIndex
      value: Int = board.state(i)(j)
      if value == 0
    } yield (i, j)

    if (spots.nonEmpty) {

      val number = if (Random.nextFloat() > 0.20f) 2 else 4

      val (i,j) = Random.shuffle(spots).head
      val state = this.board.state
      val row: List[Int] = board.state(i).updated(j, number)
      this.board = Board(state.updated(i, row))
    }

  }

  private def transform(xs: List[Int])(implicit direction: Int): List[Int] = {
    val filtered = xs.filter(_ != 0)
    direction match {
      case 0 => sum(List(), filtered).padTo(xs.size, 0)
      case 1 => sum(List(), filtered.reverse).padTo(xs.size, 0).reverse
    }
  }

  private def sum(accu: List[Int], xs: List[Int]): List[Int] = {
    xs match {
      case Nil => accu
      case a :: Nil => sum(accu :+ a, Nil)
      case a :: b :: cs if a == b => {
        points =  points + a + b
        sum(accu :+ (a + b), cs)
      }
      case a :: b :: cs => sum (accu :+ a, b +: cs)
    }
  }

  private def rows2cols[T](rows: List[List[T]]): List[List[T]] = {
    rows.transpose
  }

}