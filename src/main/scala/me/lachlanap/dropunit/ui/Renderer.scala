package me.lachlanap.dropunit.ui

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.{GL20, OrthographicCamera}
import me.lachlanap.dropunit.world._

/**
 * Renders the world with OpenGL.
 */
class Renderer(worldConfig: WorldConfig, config: UIConfig, inputHandler: InputHandler) {
  val camera = new OrthographicCamera()
  val batch = new SpriteBatch()

  val columnWidth = config.toPixels(worldConfig.columnWidth)
  val blockHeight = config.toPixels(worldConfig.columnWidth)
  val centreX = config.width / 2.0

  camera.setToOrtho(false, config.width, config.height)

  def render(world: World, textures: Textures) = {
    Gdx.gl.glClearColor(.4f, .7f, 1f, 1)
    Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)

    camera.update()

    batch.setProjectionMatrix(camera.combined)
    batch.begin()
    renderPlayer(world, textures, world.leftPlayer, batch)
    renderPlayer(world, textures, world.rightPlayer, batch)
    renderEntities(world, textures, batch)
    batch.end()
  }

  def renderPlayer(world: World, textures: Textures, area: Area, batch: SpriteBatch) = {
    val isLeft = area.orientation == FacingRight
    var x = 0

    val halfSeparation = config.toPixels(worldConfig.separation / 2)

    for (column <- area.columns) {
      var y = 0

      for (block <- column.stack) {
        val px = if (isLeft) centreX - halfSeparation - x * columnWidth - columnWidth
                 else centreX + halfSeparation + x * columnWidth

        batch.draw(textures.block(block, area.orientation),
                   px.toFloat, (y * blockHeight).toFloat)

        y += 1
      }

      x += 1
    }
  }

  def renderEntities(world: World, textures: Textures, batch: SpriteBatch) = {
    for (entity <- world.entities) {
      val texture = textures.entity(entity)
      batch.draw(texture,
                 config.toPixels(entity.pos.x).toFloat - texture.getWidth / 2 + centreX.toFloat,
                 config.toPixels(entity.pos.y).toFloat - texture.getHeight / 2)
    }
  }
}
