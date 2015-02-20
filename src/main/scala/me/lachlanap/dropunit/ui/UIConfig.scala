package me.lachlanap.dropunit.ui

case class UIConfig(width: Int, height: Int,
                    pixelsPerMetre: Int) {
  def toPixels(metres: Double) = metres * pixelsPerMetre

  def toMetres(pixels: Double) = pixels / pixelsPerMetre
}
