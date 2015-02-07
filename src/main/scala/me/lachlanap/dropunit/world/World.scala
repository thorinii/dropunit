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
         Array(Column(List(resource(), power())),
               Column(List(resource(), power())),
               Column(List(structure(), structure(), power(), power())),
               Column(List(structure(), shield(), shield(), shield()))),
         Set.empty)
  }

  private def structure() = new Block(UnitBlueprint("Frame", UnitSpecs(strength = UnitStrength(2))))
  private def power() = new Block(UnitBlueprint("Generator", UnitSpecs(power=UnitPowerGeneration(1))))
  private def resource() = new Block(UnitBlueprint("Miner", UnitSpecs()))
  private def shield() = new Block(UnitBlueprint("EM Shield", UnitSpecs(power=UnitPowerConsumption(3), attackStrength = UnitStrength(4))))
}

/**
 * The World. All state in the game.
 */
class World(val config: WorldConfiguration,
            val leftPlayer: Area, val rightPlayer: Area) {
  val columnWidth: Int = 2
}


