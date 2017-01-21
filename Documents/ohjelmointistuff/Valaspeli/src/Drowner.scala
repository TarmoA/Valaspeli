
import processing.core._
import scala.math._
import java.awt.Rectangle
import java.awt.geom.Rectangle2D

class Drowner(P: PApplet, xDestination: Float, fromLeft: Boolean) {
  P.sketchFile("Barrel.png")
  private val image = P.loadImage("Barrel.png")
  image.resize(35,35)
  private var xCoord = if (fromLeft) 0.0f else 1140
  private var yCoord = 260.0f
  private var isSaved = false
  
  var cosVal = 0.0f
  
  def getX = xCoord
  def getY = yCoord
  
  def draw = P.image(image ,getX, getY)
  
  def move()= {
    if (!interLapsWithWhale) {
      yCoord = cos(cosVal).toFloat * 15.0f + 260f
      cosVal = cosVal + 0.01f * 5f

      if (xCoord > xDestination - 1 && xCoord < xDestination + 1) {
        xCoord = xDestination
      } else if (fromLeft) {
        xCoord += 0.5f
      } else {
        xCoord -= 0.5f
      }
      draw
    } else {
      yCoord -= 10
      draw
    }
  }
  
  def bounds(): Rectangle2D = new Rectangle(xCoord.toInt, yCoord.toInt, 35, 35)
  
  def interLapsWithWhale = {
    if (Whale.bounds.intersects(bounds)) {
      isSaved = true
    }
    isSaved
  }
  
  
}