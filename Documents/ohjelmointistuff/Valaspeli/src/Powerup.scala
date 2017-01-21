import processing.core._
import java.awt.Rectangle

class Powerup(p: PApplet, val x: Float, val y: Float) extends Actor {
  
  var alpha = 0
  val width = 20
  val height = 20
  
  def getBounds: Rectangle = new Rectangle(x.toInt, y.toInt, width, height)
  
  override def hitAction(obj: Actor): Unit = alpha = 255
   
  def display {
    p.noStroke()
    p.fill(255, 0, 0, alpha)
    p.rect(x, y, width, height)
    alpha -= 1
  }
}