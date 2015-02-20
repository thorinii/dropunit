package me.lachlanap.dropunit

import com.badlogic.gdx.{ApplicationListener, Gdx}
import me.lachlanap.dropunit.ui.{Renderer, Textures, UIConfig}
import me.lachlanap.dropunit.world._

/**
 * Runs the game.
 */
class DropUnitCore(worldConfig: WorldConfig, uiConfig: UIConfig, blueprintLoader: BlueprintLoader) extends ApplicationListener {
  var world: World = null
  var textures: Textures = null
  var renderer: Renderer = null

  override def create(): Unit = {
    world = World.build(worldConfig, blueprintLoader)
    textures = Textures.load()
    renderer = new Renderer(worldConfig, uiConfig)
  }

  override def dispose(): Unit = {}

  override def resize(width: Int, height: Int): Unit = {}

  override def pause(): Unit = {}

  override def resume(): Unit = {}

  val TickFrequency = 1.0 / 60
  var timeToTick = 0.0

  override def render(): Unit = {
    renderer.render(world, textures)

    timeToTick -= Gdx.graphics.getDeltaTime
    if (timeToTick <= 0) {
      timeToTick = TickFrequency
      world = world.step(TickFrequency)
    }
  }
}
