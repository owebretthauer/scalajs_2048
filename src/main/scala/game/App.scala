package game

import org.scalajs.dom.Event
import org.scalajs.dom.raw.KeyboardEvent
import slinky.core._
import slinky.core.annotations.react
import slinky.web.html._
import slinky.web.html.onKeyDown

import scala.scalajs.js
import scala.scalajs.js.annotation.{JSImport, ScalaJSDefined}

@JSImport("resources/App.css", JSImport.Default)
@js.native
object AppCSS extends js.Object

@react class App extends StatelessComponent {
  type Props = Unit
  private val css = AppCSS
  def render() = {
    div(className := "root")(
      div(className := "app")(
        GameBoard()
      )
    )
  }
}

@react class GameBoard extends Component {
  case class Props()
  case class State(game: Game)
  override def initialState: State = {
    val game = new Game()
    val board = Board(List(
      List(0,0,0,0),
      List(0,0,0,0),
      List(0,0,0,0),
      List(0,0,0,0)))
    game.board = board
    game.prepareNextTurn()
    State(game)
  }

  def render() = {

    div(
      div(className := "points")("Points: ", state.game.points),
      div(Table(state.game.board.state)),
      div(className := "control")(
        input(`type` := "text", onKeyDown := (event => keyDown(event))),
        div("keyboard (left, right, top, down)")
      )
    )
  }

 def keyDown(event: Event): Unit = {
   val keyboardEvent = event.asInstanceOf[KeyboardEvent]
   val game = state.game

   keyboardEvent.keyCode match {
      case 40 => game.turn(Down)
      case 39 => game.turn(Right)
      case 38 => game.turn(Up)
      case 37 => game.turn(Left)
      case _ => println(keyboardEvent.keyCode)
   }
   this.setState(State.apply(game))
  }

}

@react class Table extends StatelessComponent {
  case class Props(rows: List[List[Int]])
  def render() = {
    table(
      tbody(
        props.rows.map( row =>
          Row(row)
      ))
    )
  }
}

@react class Row extends StatelessComponent {
  case class Props(cells: List[Int])
  def render() = {
    tr(
      props.cells.map( cell =>
        td(className := s"tile-${cell}") {
          if (cell > 0) cell.toString else ""
        }

      )
    )
  }
}

