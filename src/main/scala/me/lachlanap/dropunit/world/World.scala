package me.lachlanap.dropunit.world

import scala.collection.Set

case class WorldConfiguration(columns: Int,
                              separation: Int,
                              maxHeight: Int)

object World {
  def build(config: WorldConfiguration, blueprintLoader: BlueprintLoader) = {
    val blueprints = blueprintLoader.loadAll()

    new World(
      config,
      blueprints,
      buildPlayer(config, blueprints, FacingRight),
      buildPlayer(config, blueprints, FacingLeft))
  }

  private def buildPlayer(config: WorldConfiguration, set: BlueprintSet, orientation: Orientation) = {
    Area(orientation,
         Array(Column(List(block(set, "wooden-miner"), block(set, "wooden-generator"))),
               Column(List(block(set, "wooden-miner"), block(set, "wooden-generator"))),
               Column(List(block(set, "wooden-frame"), block(set, "wooden-generator"), block(set, "wooden-generator"), block(set, "wooden-generator"))),
               Column(List(block(set, "wooden-frame"), block(set, "wooden-frame"), block(set, "wooden-frame"), block(set, "wooden-frame"))),
               Column(List(block(set, "wooden-frame"), block(set, "wooden-shield-em"), block(set, "wooden-shield-em"), block(set, "wooden-shield-em")))),
         Set.empty)
  }

  private def block(set: BlueprintSet, id: String) = new Block(set.byName(id))
}

/**
 * The World. All state in the game.
 */
class World(val config: WorldConfiguration,
            val blueprints: BlueprintSet,
            val leftPlayer: Area, val rightPlayer: Area) {
  val columnWidth: Int = 2

  def reloadBlueprints(loader: BlueprintLoader) = {
    new World(config, loader.loadAll(), leftPlayer, rightPlayer)
  }
}


