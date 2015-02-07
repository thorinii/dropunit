package me.lachlanap.dropunit.world

import scala.collection.Set

case class WorldConfiguration(columns: Int,
                              separation: Int,
                              maxHeight: Int)

object World {
  def build(config: WorldConfiguration) = {
    new World(
      config,
      buildPlayer(config, FacingRight),
      buildPlayer(config, FacingLeft))
  }

  private def buildPlayer(config: WorldConfiguration, orientation: Orientation) = {
    Area(orientation,
         Array.fill(config.columns) { Column(List.empty) },
         Set.empty)
  }
}

/**
 * The World. All state in the game.
 */
class World(val config: WorldConfiguration,
            val leftPlayer: Area, val rightPlayer: Area) {
  val columnWidth: Int = 2
}


