package me.lachlanap.dropunit.world.controllers

import me.lachlanap.dropunit.world._

object CannonController {
  val Cooldown = 2

  def apply() = new CannonController(Cooldown, 30)
}

case class CannonController(timeTillNextFire: Double, angle: Double) extends BlockController {

  override def step(dt: Double, centre: Vector2, orientation: Orientation): (BlockController, List[Transform]) = {
    val nextFire = timeTillNextFire - dt
    val absAngle = if(orientation == FacingRight) angle else 180 - angle

    if (nextFire <= 0) {
      println("FIRING")
      (CannonController(CannonController.Cooldown, angle), List(
        SpawnEntity(Entities.cannonBall(centre, absAngle, orientation))
      ))
    } else {
      (CannonController(nextFire, angle), Nil)
    }
  }
}
