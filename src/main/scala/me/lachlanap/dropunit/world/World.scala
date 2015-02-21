package me.lachlanap.dropunit.world

case class WorldConfig(columns: Int, columnWidth: Int,
                       separation: Int,
                       maxHeight: Int)

object World {
  def build(config: WorldConfig, blueprintLoader: BlueprintLoader) = {
    val blueprints = blueprintLoader.loadAll()

    new World(
      config,
      blueprints,
      buildPlayer(config, blueprints, FacingRight),
      buildPlayer(config, blueprints, FacingLeft))
  }

  private def buildPlayer(config: WorldConfig, set: BlueprintSet, orientation: Orientation) = {
    Area(orientation,
         Array(Column(List(block(set, "wooden-miner"), block(set, "wooden-generator"))),
               Column(List(block(set, "wooden-miner"), block(set, "wooden-generator"))),
               Column(List(block(set, "wooden-frame"), block(set, "wooden-generator"), block(set, "wooden-generator"), block(set, "wooden-generator"))),
               Column(List(block(set, "wooden-frame"), block(set, "wooden-frame"), block(set, "wooden-frame"), block(set, "wooden-frame"))),
               Column(List(block(set, "wooden-frame"), block(set, "wooden-shield-em"), block(set, "wooden-shield-em"), block(set, "wooden-cannon")))).reverse,
         Nil)
  }

  private def block(set: BlueprintSet, id: String) = set.byName(id).build()
}

/**
 * The World. All state in the game.
 */
class World(val config: WorldConfig,
            val blueprints: BlueprintSet,
            val leftPlayer: Area, val rightPlayer: Area) {

  def reloadBlueprints(loader: BlueprintLoader) = {
    new World(config, loader.loadAll(), leftPlayer, rightPlayer)
  }

  def step(dt: Double) = {
    val (left, leftActions) = leftPlayer.step(dt, config.columnWidth, config.separation / 2)
    val (right, rightActions) = rightPlayer.step(dt, config.columnWidth, config.separation / 2)

    val actions = leftActions ++ rightActions
    if (!actions.isEmpty)
      println(s"$actions to execute")

    new World(config, blueprints, left, right)
  }

  // TODO: entity spawning; entity processing
}


