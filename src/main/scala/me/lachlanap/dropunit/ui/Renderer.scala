package me.lachlanap.dropunit.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.{Texture, OrthographicCamera, GL20}
import me.lachlanap.dropunit.world._

/**
 * Renders the world with OpenGL.
 */
class Renderer {
  val camera = new OrthographicCamera()
  val batch = new SpriteBatch()

  val blockTexture = new Texture("gamedata/textures/block-wooden-frame.png")

  val columnWidth = blockTexture.getWidth.toFloat
  val blockHeight = blockTexture.getHeight.toFloat

  camera.setToOrtho(false, 800, 480)

  def render(world: World) = {
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
