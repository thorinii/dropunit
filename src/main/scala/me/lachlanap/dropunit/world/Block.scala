package me.lachlanap.dropunit.world

/**
 * The workhorse of the game.
 */
class Block(val blueprint: Blueprint,
            val id: Id,
            val health: Double,
            controller: BlockController) {
  def step(dt: Double, centre: Vector2, orientation: Orientation): (Block, List[Transform]) = {
    val (nextController, actions) = controller.step(dt, centre, orientation)
    (new Block(blueprint, id, health, nextController), actions)
  }

  def damage(amount: Double) = new Block(blueprint, id, health - 1, controller)
}


trait BlockController {
  def step(dt: Double, centre: Vector2, orientation: Orientation): (BlockController, List[Transform])
}

case object NilController extends BlockController {
  override def step(dt: Double, centre: Vector2, orientation: Orientation) = (this, Nil)
}