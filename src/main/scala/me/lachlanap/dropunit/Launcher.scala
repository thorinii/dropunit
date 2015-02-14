package me.lachlanap.dropunit

import me.lachlanap.dropunit.resources.DiskBlueprintLoader
import me.lachlanap.dropunit.ui.DropUnitCore
import me.lachlanap.dropunit.world._
import scala.reflect.ClassTag

object Launcher {
  def main(args: Array[String]): Unit = {
    import com.badlogic.gdx.backends.lwjgl._

    val config = WorldConfiguration(
      columns = 5,
      separation = 10,
      maxHeight = 10
    )

    val appcfg = new LwjglApplicationConfiguration
    appcfg.title = "Drop Unit"
    appcfg.width = 800
    appcfg.height = 480
    appcfg.forceExit = false
    appcfg.backgroundFPS = -1
    appcfg.resizable = false
    appcfg.vSyncEnabled = true
    new LwjglApplication(new DropUnitCore(config, new DiskBlueprintLoader), appcfg)
  }
}