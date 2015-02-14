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

  lazy val camera = new OrthographicCamera()
  lazy val batch = new SpriteBatch()

  lazy val blockTexture = new Texture("gamedata/textures/block-wooden-frame.png")

  override def create(): Unit = {
    world = World.build(config, blueprintLoader)

    camera.setToOrtho(false, 800, 480)
  }

  override def dispose(): Unit = {}

  override def resize(width: Int, height: Int): Unit = {}

  override def pause(): Unit = {}

  override def resume(): Unit = {}

  override def render(): Unit = {
    Gdx.gl.glClearColor(.4f, .7f, 1f, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    camera.update()

    batch.setProjectionMatrix(camera.combined)
    batch.begin()
    renderPlayer(world, world.leftPlayer, batch)
    renderPlayer(world, world.rightPlayer, batch)
    batch.end()
  }

  def renderPlayer(world: World, area: Area, batch: SpriteBatch) = {
    val columnWidth = blockTexture.getWidth
    val blockHeight = blockTexture.getHeight
    val isLeft = area.orientation == FacingRight
    var x = 0

    for (column: Column <- area.columns) {
      var y = 0

      for(block: Block <- column.stack) {
        val px = if(isLeft) x * columnWidth else 800 - x * columnWidth - columnWidth

        batch.draw(blockTexture, px, y * blockHeight)

        y += 1
      }

      x += 1
    }
  }
}
