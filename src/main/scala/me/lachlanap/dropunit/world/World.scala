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

  private def structure() = new Block(Blueprint("Frame", Specs(strength = Strength(2))))
  private def power() = new Block(Blueprint("Generator", Specs(power = PowerGeneration(1))))
  private def resource() = new Block(Blueprint("Miner", Specs()))
  private def shield() = new Block(Blueprint("EM Shield", Specs(power = PowerConsumption(3), attackStrength = Strength(4))))
}

/**
 * The World. All state in the game.
 */
class World(val config: WorldConfiguration,
            val leftPlayer: Area, val rightPlayer: Area) {
  val columnWidth: Int = 2
}


