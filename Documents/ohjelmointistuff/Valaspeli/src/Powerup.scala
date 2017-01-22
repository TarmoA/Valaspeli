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
  var img = if(Random.nextBoolean) starfish else urchin
  
  def getBounds: Rectangle = new Rectangle(x.toInt + width/2, y.toInt + height/2, width, height)
  
  override def hitAction(obj: Actor): Unit = {
    if(alpha > 1)
      if(Random.nextBoolean) obj.health += 33 else{
        Whale.score += 750
    		println(Whale.score)
      }
  }
  
  override def checkCollision(obj: Actor) = {
    if(obj.getBounds.intersects(this.getBounds) && flag && alpha > 1){
      flag = false
      hitAction(obj)
      obj.hitAction(this)
      true
    }else{
      false
    }
  }
  
  def draw = p.image(img, x, y, width, height)

   
  def update {
    
    if (!this.checkCollision(Whale) && flag){
      
    	p.noStroke()
    	p.tint(255, alpha)
    	this.draw
    	p.tint(255,255)
    	alpha -= 1
    }
  }
}