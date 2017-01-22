import processing.core._
import scala.math._
import java.awt.Rectangle
import java.awt.geom.Rectangle2D
import processing.core.PApplet


class Drowner(P: WhaleGame, xDestination: Float, fromLeft: Boolean) extends Actor {
  P.sketchFile("Characters/Raincoat.png")
  private val image = P.loadImage("Characters/Raincoat.png")
  image.resize(50,50)
  private var xCoord = if (fromLeft) 0.0f else 1140
  private var yCoord = 260.0f
  private var isSaved = false
  private var shouldGoUP = false
  damage = 0
  
  var cosVal = 0.0f
  
  def getX = xCoord
  def getY = yCoord
  
  def draw = {
    P.image(image ,getX, getY)
  }
  
  
  override def hitAction(obj: Actor) = {
    Whale.score += 100
    shouldGoUP = true
  }
  
  def move()= {
    P.squirtHandler.squirts.foreach(this.checkCollision(_))
    
    if(!shouldGoUP) {
        yCoord = cos(cosVal).toFloat * 15.0f + 260f
        cosVal = cosVal + 0.01f * 5f
    }
        if (xCoord > xDestination - 1 && xCoord < xDestination + 1) {
          xCoord = xDestination
        } else if (fromLeft) {
          xCoord += 0.5f
        } else {
          xCoord -= 0.5f
      }
      draw
      
    
//      yCoord -= 10
      //isSaved = true
      
         
    if(shouldGoUP){
      yCoord -= 10
      
    }
  }
  
  def getBounds(): Rectangle = new Rectangle(xCoord.toInt, yCoord.toInt, this.image.width + 40, this.image.height + 40)
  
}