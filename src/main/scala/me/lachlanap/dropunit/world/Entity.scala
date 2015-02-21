package me.lachlanap.dropunit.world

/**
 * A physically simulated game object.
 */
abstract class Entity(val kind: String, val id: Id,
                      val pos: Vector2, val vel: Vector2 = Vector2.Zero) {
  def step(dt: Double, gravity: Double, collisionView: CollisionView): (Entity, List[Transform])

  def integrate(dt: Double, gravity: Double, pos: Vector2, vel: Vector2) = {
    // TODO: better integration; drag

    val v1 = vel
    val p1 = pos + v1 * (dt * 0.5)
    val v2 = v1 + new Vector2(0, -gravity) * dt
    val p2 = p1 + v2 * (dt * 0.5)

    (p2, v2)
  }

  override def toString() = s"E($kind, $id)"
}