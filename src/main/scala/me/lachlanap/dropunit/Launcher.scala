package me.lachlanap.dropunit

import me.lachlanap.dropunit.resources.DiskBlueprintLoader
import me.lachlanap.dropunit.ui.UIConfig
import me.lachlanap.dropunit.world._

object Launcher {
  def main(args: Array[String]): Unit = {
    import com.badlogic.gdx.backends.lwjgl._

    val worldConfig = WorldConfig(
      columns = 5,
      columnWidth = 2,
      separation = 8,
      maxHeight = 10
    )

    val uiConfig = UIConfig(
      width = 1000,
      height = 600,
      pixelsPerMetre = 30
    )

    val appcfg = new LwjglApplicationConfiguration
    appcfg.title = "Drop Unit"
    appcfg.width = uiConfig.width
    appcfg.height = uiConfig.height
    appcfg.forceExit = false
    appcfg.backgroundFPS = -1
    appcfg.resizable = false
    appcfg.vSyncEnabled = true
    new LwjglApplication(new DropUnitCore(worldConfig, uiConfig, new DiskBlueprintLoader), appcfg)
  }
}