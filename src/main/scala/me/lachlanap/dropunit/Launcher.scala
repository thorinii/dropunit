package me.lachlanap.dropunit

import me.lachlanap.dropunit.world._
import scala.reflect.ClassTag

object Launcher {
  def main(args: Array[String]): Unit = {
    val config = WorldConfiguration(
      columns = 5,
      separation = 10,
      maxHeight = 10
    )

    val world = World.build(config, new BlueprintLoader {
      import java.nio.file.{Files, Paths}
      import scala.collection.JavaConversions._
      import com.typesafe.config.ConfigFactory

      val gamedata = Paths.get(".", "gamedata")
      val blueprintsDir = gamedata.resolve("blueprints")

      override def loadAll(): BlueprintSet = {
        val blueprintIds = Files.newDirectoryStream(blueprintsDir)
                                .map(blueprintsDir.relativize(_).toString)
                                .filter(_.endsWith(".blpt"))
                                .map(file => file.substring(0, file.length - 5))
                                .toList

        val blueprints = blueprintIds.map(load _)
        new BlueprintSet(blueprints.toSet)
      }

      override def load(name: String): Blueprint = {
        val file = blueprintsDir.resolve(name + ".blpt").toFile

        val config = ConfigFactory.parseFile(file)

        val strength = Strength(config.getInt("strength"))
        val power = config.getInt("power") match {
          case p if p > 0 => PowerGeneration(p)
          case p if p < 0 => PowerConsumption(p)
          case _ => PowerNone
        }
        val attackStrength = Strength(config.getInt("attack-strength"))

        val specs = Specs(strength, power, attackStrength)
        Blueprint(id = name, name = config.getString("name"), specs)
      }
    })


    render(world)
  }

  def render(world: World) = {
    val worldWidth = world.config.columns * world.columnWidth * 2 + world.config.separation

    val renderGrid = new Array2D[Char](worldWidth, world.config.maxHeight)

    renderPlayer(world, world.leftPlayer, 0, 1, renderGrid)
    renderPlayer(world, world.rightPlayer, worldWidth - 1, -1, renderGrid)

    printGrid(renderGrid)
  }

  def renderPlayer(world: World, area: Area, start: Int, direction: Int, renderGrid: Array2D[Char]) = {
    var index = start
    for (column: Column <- area.columns) {
      var y = 0
      for(block: Block <- column.stack) {
        val state = blockTile(area.orientation, block)

        if (area.orientation == FacingRight) {
          renderGrid(index, y + 1) = state.charAt(0)
          renderGrid(index + 1, y + 1) = state.charAt(0 + 1)
        } else {
          renderGrid(index, y + 1) = state.charAt(0 + 1)
          renderGrid(index - 1, y + 1) = state.charAt(0)
        }
        if (area.orientation == FacingRight) {
          renderGrid(index, y) = state.charAt(2)
          renderGrid(index + 1, y) = state.charAt(2 + 1)
        } else {
          renderGrid(index, y) = state.charAt(2 + 1)
          renderGrid(index - 1, y) = state.charAt(2)
        }

        y += world.columnWidth
      }

      index += direction * world.columnWidth
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

  def blockTile(orientation: Orientation, block: Block): String = block.blueprint.specs match {
    case Specs(_, PowerGeneration(p), _) if p >= 1 =>
      "!!" +
      "!!"
    case Specs(_, _, Strength(s)) if s >= 4 => orientation match {
      case FacingLeft =>
        "(-" +
        "(-"
      case FacingRight =>
        "-)" +
        "-)"
    }
    case Specs(Strength(s), _, _) if s >= 2 =>
      "^^" +
      "##"
    case _ =>
      """/\""" +
      """\/"""
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