package me.lachlanap.dropunit.ui

import com.badlogic.gdx.InputProcessor
import me.lachlanap.dropunit.world.{Area, World}

/**
 * Listens for mouse and keyboard input and maps them to game actions.
 *
 * Runs on the UI thread.
 */
class InputHandler(world: World, uiConfig: UIConfig) extends InputProcessor {
  private var _world: World = world

  /**
   * The location of the mouse (OpenGL coordinates, pixels)
   */
  var mouseX, mouseY = 0

  /**
   * The location of the mouse (world coordinates, metres)
   */
  var pointerX, pointerY = 0.0

  var selectedColumn: Option[ColumnSelection] = None


  def setWorld(world: World) = _world = world


  override def keyDown(keycode: Int): Boolean = false

  override def keyTyped(character: Char): Boolean = false

  override def keyUp(keycode: Int): Boolean = false

  override def scrolled(amount: Int): Boolean = false


  override def mouseMoved(screenX: Int, screenY: Int): Boolean = {
    updateMouse(screenX, screenY)

    true
  }

  override def touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
    updateMouse(screenX, screenY)

    true
  }

  override def touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean = {
    updateMouse(screenX, screenY)

    true
  }

  override def touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean = {
    updateMouse(screenX, screenY)

    true
  }

  private def updateMouse(screenX: Int, screenY: Int) = {
    mouseX = screenX
    mouseY = uiConfig.height - screenY

    pointerX = uiConfig.toMetres(mouseX - uiConfig.width / 2)
    pointerY = uiConfig.toMetres(mouseY)

    val area = if (pointerX >= 0) world.rightPlayer else world.leftPlayer
    val columnIndex = (pointerX.abs - world.config.separation / 2) / world.config.columnWidth

    selectedColumn = if (columnIndex >= 0 && columnIndex < world.config.columns)
                       Some(ColumnSelection(area, columnIndex.toInt))
                     else
                       None
  }
}


case class ColumnSelection(area: Area, columnIndex: Int)