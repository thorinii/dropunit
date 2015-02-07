package me.lachlanap.dropunit.world

/**
 * The workhorse of the game.
 */
class Block(val blueprint: UnitBlueprint) {

}



case class UnitBlueprint(name: String, specs: UnitSpecs)

case class UnitSpecs(strength: UnitStrength = UnitStrength(1),
                     power: UnitPower = UnitPowerNone,
                      attackStrength: UnitStrength = UnitStrength(1))

case class UnitStrength(strength: Int)

sealed trait UnitPower
case class UnitPowerGeneration(amount: Int) extends UnitPower
case class UnitPowerConsumption(amount: Int) extends UnitPower
case object UnitPowerNone extends UnitPower