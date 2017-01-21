
import processing.core._
import scala.math._

class Trash(P: PApplet, depth: Float, fromLeft: Boolean) {
  P.sketchFile("Barrel.png")
  private val image = P.loadImage("Barrel.png")
  image.resize(35,35)
  private var xCoord = if (fromLeft) 0.0f else 1140
  private var yCoord = depth
  var cosVal = 0.0f
  
  def getX = xCoord
  def getY = yCoord
  
  def draw = {
    if (xCoord > -10 && xCoord < P.width + 10) {
      P.image(image ,getX, getY)  
    }
    
  }
  
  def move()= {
    yCoord = cos(cosVal).toFloat * 15.0f + depth
    cosVal = cosVal + 0.01f * 5f
    
    if (xCoord > -10 && xCoord < P.width + 10) {
      
      if (fromLeft){
        xCoord += 0.8f
      } else {
        xCoord -= 0.8f
      }
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