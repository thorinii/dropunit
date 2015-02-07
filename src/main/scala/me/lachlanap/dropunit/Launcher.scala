package me.lachlanap.dropunit

import me.lachlanap.dropunit.world._

import scala.reflect.ClassTag

object Launcher {
  def main(args: Array[String]): Unit = {
    val config = WorldConfiguration(
      columns = 4,
      separation = 10,
      maxHeight = 10
    )

    val world = World.build(config)


    render(world)
  }

  def render(world: World) = {
    val worldWidth = world.config.columns * world.columnWidth * 2 + world.config.separation

    val renderGrid = new Array2D[Char](worldWidth, world.config.maxHeight)

    renderPlayer(world.leftPlayer, 0, 1, renderGrid)
    renderPlayer(world.rightPlayer, worldWidth - 1, -1, renderGrid)

    printGrid(renderGrid)
  }

  def renderPlayer(area: Area, start: Int, direction: Int, renderGrid: Array2D[Char]) = {
    var index = start
    for (column <- area.columns) {
      var y = 0
      for(block <- column.stack) {
        val state = block.blueprint.specs match {
          case UnitSpecs(_, UnitPowerGeneration(p), _) if p >= 1 => '!'
          case UnitSpecs(_, _, UnitStrength(s)) if s >= 4 => area.orientation match {
            case FacingLeft => '('
            case FacingRight => ')'
          }
          case UnitSpecs(UnitStrength(s), _, _) if s >= 2 => '#'
          case _ => 'O'
        }

        renderGrid(index, y) = state
        renderGrid(index + direction, y) = state
        renderGrid(index, y+1) = state
        renderGrid(index + direction, y+1) = state

        y += 2
      }

      index += direction * 2
    }
  }

  def printGrid(renderGrid: Array2D[Char]) = {
    for (y <- (0 to renderGrid.height - 1).reverse) {
      val builder = new StringBuilder
      builder.append('|')
      for (x <- 0 to renderGrid.width - 1) {
        var value = renderGrid(x, y)
        if(value == 0)
          value = ' '
        builder.append(value)
      }
      builder.append('|')

      println(builder)
    }
  }
}

class Array2D[T](val width: Int, val height: Int)(implicit tag: ClassTag[T]) {
  private val array: Array[T] = new Array[T](width*height)

  def apply(x: Int, y: Int): T = array(index(x,y))

  def update(x: Int, y: Int, value: T) = {
    array(index(x,y)) = value
  }

  private def index(x: Int, y: Int) = y * width + x
}