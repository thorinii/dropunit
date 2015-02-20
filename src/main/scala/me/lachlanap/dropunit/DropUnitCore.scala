package me.lachlanap.dropunit

import com.badlogic.gdx.{ApplicationListener, Gdx}
import me.lachlanap.dropunit.ui.{InputHandler, Renderer, Textures, UIConfig}
import me.lachlanap.dropunit.world._

/**
 * Runs the game.
 */
class DropUnitCore(worldConfig: WorldConfig, uiConfig: UIConfig, blueprintLoader: BlueprintLoader) extends ApplicationListener {
  var world: World = null
  var textures: Textures = null
  var inputHandler: InputHandler = null
  var renderer: Renderer = null

  override def create(): Unit = {
    world = World.build(worldConfig, blueprintLoader)
    textures = Textures.load()
    inputHandler = new InputHandler(world, uiConfig)
    renderer = new Renderer(worldConfig, uiConfig, inputHandler)

    Gdx.input.setInputProcessor(inputHandler)
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

      inputHandler.setWorld(world)
    }
  }
}
