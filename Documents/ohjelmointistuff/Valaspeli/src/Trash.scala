
import processing.core._
import scala.math._
import java.awt.Rectangle
import java.awt.geom.Rectangle2D
import java.awt.geom.RectangularShape

class Trash(P: PApplet, depth: Float, fromLeft: Boolean) extends Actor {
  P.sketchFile("Harpoon.png")
  private val image = P.loadImage("Harpoon.png")
  image.resize(100,35)
  image.resize(50,5)
  var xCoord = if (fromLeft) 0.0f else 1140
  private var yCoord = depth
  var cosVal = 0.0f

  damage = 33
  
  
  def getX = xCoord
  def getY = yCoord
  
  //def draw = P.image(image ,getX, getY)
  
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
  

  
  def draw = {
    if (xCoord > -10 && xCoord < P.width + 10) {
      P.image(image ,getX, getY)  
    }
    
  }
  
//  def move()= {
//    yCoord = cos(cosVal).toFloat * 15.0f + depth
//    cosVal = cosVal + 0.01f * 5f
//    
//    if (xCoord > -10 && xCoord < P.width + 10) {
//      
//      if (fromLeft){
//        xCoord += 0.8f
//      } else {
//        xCoord -= 0.8f
//      }
//    }
//  }
  
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