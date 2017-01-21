import processing.core._
import java.awt.Rectangle
import scala.util.Random

class Powerup(p: PApplet, val x: Float, val y: Float) extends Actor {
  p.sketchFile("Other/Starfish.png")
  p.sketchFile("Other/Urchin.png")
  var starfish = p.loadImage("Other/Starfish.png")
  var urchin = p.loadImage("Other/Urchin.png")

  var alpha = 0
  val width = 40
  val height = 40
  var img  = if(Random.nextBoolean) starfish else urchin 
  
  def getBounds: Rectangle = new Rectangle(x.toInt, y.toInt, width, height)
  
  override def hitAction(obj: Actor): Unit = {
    if(obj.isInstanceOf[Circle])
    	alpha = 255
    else if(obj == Whale)
      println("valas")
      
  }
   
  def display {
    p.noStroke()
    p.tint(255, alpha)
    p.image(img, x, y, width, height)
    p.tint(255,255)
    alpha -= 1
  }
}