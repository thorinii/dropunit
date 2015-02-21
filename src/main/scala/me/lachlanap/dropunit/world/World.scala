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
      buildPlayer(config, blueprints, FacingLeft),
      Nil)
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
            val leftPlayer: Area, val rightPlayer: Area,
            val entities: List[Entity]) {

  def reloadBlueprints(loader: BlueprintLoader) = {
    new World(config, loader.loadAll(), leftPlayer, rightPlayer, entities)
  }

  def step(dt: Double) = {
    val (left, leftActions) = leftPlayer.step(dt, config.columnWidth, config.separation / 2)
    val (right, rightActions) = rightPlayer.step(dt, config.columnWidth, config.separation / 2)

    val actions = leftActions ++ rightActions
    if (actions.nonEmpty)
      println(s"$actions to execute")

    // TODO: entity processing

    val stepped = new World(config, blueprints, left, right, entities)

    actions.foldLeft(stepped) { (w, a) => a.applyTo(w) }
  }
}


