package me.lachlanap.dropunit.ui

import com.badlogic.gdx.{Gdx, ApplicationListener}
import com.badlogic.gdx.graphics.{Texture, GL20, OrthographicCamera}
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import me.lachlanap.dropunit.world._

/**
 * Runs the game.
 */
class DropUnitCore(config: WorldConfiguration, blueprintLoader: BlueprintLoader) extends ApplicationListener {
  var world: World = null
  var renderer: Renderer = null

  override def create(): Unit = {
    world = World.build(config, blueprintLoader)
    renderer = new Renderer
  }

  override def dispose(): Unit = {}

  override def resize(width: Int, height: Int): Unit = {}

  override def pause(): Unit = {}

  override def resume(): Unit = {}

  override def render(): Unit = {
    renderer.render(world)
  }
}
