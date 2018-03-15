package game

import org.scalatest.Matchers._
import org.scalatest.{FunSuite, _}

class MockGame extends Game {
  override def prepareNextTurn(): Unit = ()
}

class GameTest extends FunSuite with BeforeAndAfterEach {

  var game: Game = _

  override protected def beforeEach(): Unit = this.game = new MockGame()

  test("turn left") {

    // given
    val initialState = Board(List(
      List(2,2,2,0),
      List(0,0,0,0),
      List(2,4,2,2),
      List(0,0,0,2)))

    game.board = initialState

    // when
    game.turn(Left)
    val actual = game.board

    // then
    val expected =  Board(List(
      List(4,2,0,0),
      List(0,0,0,0),
      List(2,4,4,0),
      List(2,0,0,0)))
    actual shouldEqual(expected)

  }

  test("turn right") {

    // given
    val initialState = Board(List(
      List(2,2,2,0),
      List(0,0,0,0),
      List(2,4,2,2),
      List(0,0,0,2)))

    game.board = initialState

    // when
    game.turn(Right)
    val actual = game.board

    // then
    val expected =  Board(List(
      List(0,0,2,4),
      List(0,0,0,0),
      List(0,2,4,4),
      List(0,0,0,2)))
    actual shouldEqual(expected)

  }

  test("turn down") {

    // given
    val initialState = Board(List(
      List(2,2,2,0),
      List(0,0,0,0),
      List(2,4,2,2),
      List(0,0,0,2)))

    game.board = initialState

    // when
    game.turn(Down)
    val actual = game.board

    // then
    val expected =  Board(List(
      List(0,0,0,0),
      List(0,0,0,0),
      List(0,2,0,0),
      List(4,4,4,4)))
    actual shouldEqual(expected)

  }

  test("turn up") {

    // given
    val initialState = Board(List(
      List(2,2,2,0),
      List(0,0,0,0),
      List(2,4,2,2),
      List(0,0,0,2)))

    game.board = initialState

    // when
    game.turn(Up)
    val actual = game.board

    // then
    val expected =  Board(List(
      List(4,2,4,4),
      List(0,4,0,0),
      List(0,0,0,0),
      List(0,0,0,0)))
    actual shouldEqual(expected)

  }

  test("count only when board change") {

    // given
    val initialState = Board(List(
      List(4, 2, 0, 0),
      List(0, 0, 0, 0),
      List(2, 1, 2, 4),
      List(2, 0, 0, 0)))

    game.board = initialState

    // when
    game.turn(Left)
    game.prepareNextTurn()
    game.turn(Left)
    game.prepareNextTurn()
    game.turn(Left)
    val actual = game.board

    // then
    val expected = Board(List(
      List(4, 2, 0, 0),
      List(0, 0, 0, 0),
      List(2, 1, 2, 4),
      List(2, 0, 0, 0)))
    actual shouldEqual (expected)

    game.turns.shouldEqual(0)

  }

  test("count points") {

    // given
    val initialState = Board(List(
      List(4, 2, 2, 0),
      List(0, 0, 0, 0),
      List(2, 2, 2, 4),
      List(2, 0, 0, 0)))

    game.board = initialState

    // when
    game.turn(Left)
    game.turn(Left)

    game.points.shouldEqual(16)

  }

}
