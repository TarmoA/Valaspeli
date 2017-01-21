
import processing.core._
import scala.math._

class Drowner(P: PApplet, xDestination: Float, fromLeft: Boolean) {
  P.sketchFile("Barrel.png")
  private val image = P.loadImage("Barrel.png")
  image.resize(35,35)
  private var xCoord = if (fromLeft) 0.0f else 1140
  private var yCoord = 260.0f
  var cosVal = 0.0f
  
  def getX = xCoord
  def getY = yCoord
  
  def draw = P.image(image ,getX, getY)
  
  def move()= {
    yCoord = cos(cosVal).toFloat * 15.0f + 270f
    cosVal = cosVal + 0.01f * 5f
    
    
    if (xCoord > xDestination - 1 && xCoord < xDestination + 1) {
      xCoord = xDestination
    } else if (fromLeft){
      xCoord += 0.5f
    } else {
      xCoord -= 0.5f
    }
  }
  
  def getHitbox = {
    P.quad(this.xCoord, this.yCoord, this.xCoord + 35, this.yCoord, this.xCoord + 35, this.yCoord + 35, this.xCoord,
        this.yCoord + 35)
  }
  
  def getSaved = {
    
  }
  
  
}