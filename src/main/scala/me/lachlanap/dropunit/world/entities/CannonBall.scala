package me.lachlanap.dropunit.world.entities

import me.lachlanap.dropunit.world._

object CannonBall {
  val Id = "cannon-ball"
  val Speed = 11.0
  val Damage = 1

  def build(start: Vector2, angle: Double, owner: Orientation)(id: Id) = {
    new CannonBall(id, owner, start, Vector2.fromAngle(angle) * Speed)
  }
}

class CannonBall(id: Id, owner: Orientation, pos: Vector2, vel: Vector2 = Vector2.Zero) extends Entity(CannonBall.Id, id, pos, vel) {
  def step(dt: Double, gravity: Double, collisionView: CollisionView): (Entity, List[Transform]) = {
    val (pos, vel) = integrate(dt, gravity, this.pos, this.vel)
    val next = new CannonBall(id, owner, pos, vel)

    val intersectingBlock = collisionView.intersectingBlock(pos)
    intersectingBlock.map {
                            case (area, _, _) if area.orientation == owner =>
                              (next, Nil)
                            case (_, _, block) =>
                              (next, List(DestroyEntity(this), DamageBlock(block, CannonBall.Damage)))
                          } getOrElse {
      (next, Nil)
    }
  }
}
