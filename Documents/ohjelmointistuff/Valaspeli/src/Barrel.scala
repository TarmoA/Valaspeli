
import processing.core._
import scala.math._

class Barrel(P: PApplet, xDestination: Float, fromLeft: Boolean) {
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
  
  /*topleft(x,y), topright(x,y), bottomright(x,y) and bottomleft(x,y)*/
  def getHitbox: Array[Int] = {
    Array(this.xCoord, this.yCoord, this.xCoord + 35, this.yCoord, this.xCoord + 35, this.yCoord + 35, 
        this.xCoord, this.yCoord + 35).map(_.toInt)
  }
  
  /*Call this for (a,b) and (b,a) to check both ways*/
  def intersection (a: Array[Int], b: Array[Int]): Boolean = {
    if (a(0) < b(0) && b(0) < a(2) && a(1) > b(1) && b(1) > a(5)) {
      true
    } else false
  }
  
  
}