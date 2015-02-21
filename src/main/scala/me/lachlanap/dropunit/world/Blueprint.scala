package me.lachlanap.dropunit.world


case class Blueprint(id: String, name: String, specs: Specs, controllerFactory: () => BlockController) {
  def build() = new Block(this, controllerFactory())
}

case class Specs(strength: Strength = Strength(1),
                 power: Power = PowerNone,
                 attackStrength: Strength = Strength(1))


case class Strength(strength: Int)


sealed trait Power

case class PowerGeneration(amount: Int) extends Power

case class PowerConsumption(amount: Int) extends Power

case object PowerNone extends Power