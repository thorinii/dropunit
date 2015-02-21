package me.lachlanap.dropunit.ui

import com.badlogic.gdx.graphics.Texture
import me.lachlanap.dropunit.world.{Block, Orientation}

object Textures {
  def load(): Textures = {
    new Textures(id => new Texture(s"gamedata/textures/${id}.png"))
  }
}

/**
 * Holds textures for the game in one immutable place.
 */
class Textures(loadTexture: String => Texture) {
  val woodenFrame = loadTexture("block-wooden-frame")
  val woodenCannon = loadTexture("block-wooden-cannon")

  val cannonBall = loadTexture("entity-cannonball")

  def block(block: Block, direction: Orientation) = block.blueprint.id match {
    case "wooden-cannon" => woodenCannon
    case _ => woodenFrame
  }
}