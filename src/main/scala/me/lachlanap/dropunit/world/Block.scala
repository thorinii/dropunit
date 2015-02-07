package me.lachlanap.dropunit.world

/**
 * The workhorse of the game.
 */
class Block(val blueprint: Blueprint) {

}



case class Blueprint(name: String, specs: Specs)

case class Specs(strength: Strength = Strength(1),
                 power: Power = PowerNone,
                 attackStrength: Strength = Strength(1))

case class Strength(strength: Int)

sealed trait Power
case class PowerGeneration(amount: Int) extends Power
case class PowerConsumption(amount: Int) extends Power
case object PowerNone extends Power