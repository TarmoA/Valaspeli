import processing.core.PApplet
import java.awt.Rectangle


import processing.core._
import scala.math._
import java.awt.Rectangle
import java.awt.geom.Rectangle2D
import java.awt.geom.RectangularShape


class Barrel(P: PApplet, xDestination: Float, fromLeft: Boolean) extends Actor {
  P.sketchFile("Barrel.png")
  private val image = P.loadImage("Barrel.png")
  image.resize(35,35)
  private var xCoord = if (fromLeft) 0.0f else 1140
  private var yCoord = 260.0f
  var cosVal = 0.0f
  
  damage = 0
  
  
  def getX = xCoord
  def getY = yCoord
  
  def draw = P.image(image ,getX, getY)
  
  def move()= {
    if (!this.checkCollision(Whale) && flag) {
      yCoord = cos(cosVal).toFloat * 15.0f + 270f
      cosVal = cosVal + 0.01f * 5f

      if (xCoord > xDestination - 1 && xCoord < xDestination + 1) {
        xCoord = xDestination
      } else if (fromLeft) {
        xCoord += 0.5f
      } else {
        xCoord -= 0.5f
      }
      this.draw
    }
  }
  
  def getBounds(): Rectangle = new Rectangle(xCoord.toInt, yCoord.toInt, this.image.width, this.image.height)
  

}
  
