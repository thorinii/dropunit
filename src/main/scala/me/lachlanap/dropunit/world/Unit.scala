package me.lachlanap.dropunit.world

/**
 * The workhorse of the game.
 */
trait Unit {
  val blueprint: UnitBlueprint
}



case class UnitBlueprint(name: String, specs: UnitSpecs)

case class UnitSpecs(strength: UnitStrength,
                     powerGeneration: UnitPower)

case class UnitStrength(strength: Int)

sealed trait UnitPower
case class UnitPowerGeneration(amount: Int) extends UnitPower
case class UnitPowerConsumption(amount: Int) extends UnitPower