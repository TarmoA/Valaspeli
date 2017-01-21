
import processing.core._
import scala.math._
import java.awt.Rectangle
import java.awt.geom.Rectangle2D
import java.awt.geom.RectangularShape

class Trash(P: PApplet, depth: Float, fromLeft: Boolean) extends Actor {
  P.sketchFile("Harpoon.png")
  private val image = P.loadImage("Harpoon.png")
  image.resize(100,35)
  private var xCoord = if (fromLeft) 0.0f else 1140
  private var yCoord = depth
  var cosVal = 0.0f
  
  damage = 33
  
  
  def getX = xCoord
  def getY = yCoord
  
  def draw = P.image(image ,getX, getY)
  
  def move()= {
    if (!this.checkCollision(Whale) && flag) {
      yCoord = cos(cosVal).toFloat * 15.0f + depth.toFloat
      cosVal = cosVal + 0.006f * 5f

      if (fromLeft) {
        xCoord += 0.5f
      } else {
        xCoord -= 0.5f
      }
      this.draw
    }
  }
  
  def getBounds(): Rectangle = new Rectangle(xCoord.toInt, yCoord.toInt, this.image.width, this.image.height)
  

}