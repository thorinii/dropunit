package me.lachlanap.dropunit.world

/**
 * The workhorse of the game.
 */
class Block(val blueprint: Blueprint, controller: BlockController) {
  def step(dt: Double, centre: Vector2, orientation: Orientation): (Block, List[Transform]) = {
    val (nextController, actions) = controller.step(dt, centre, orientation)
    (new Block(blueprint, nextController), actions)
  }
}


trait BlockController {
  def step(dt: Double, centre: Vector2, orientation: Orientation): (BlockController, List[Transform])
}

case object NilController extends BlockController {
  override def step(dt: Double, centre: Vector2, orientation: Orientation) = (this, Nil)
}