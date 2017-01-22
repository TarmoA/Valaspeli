import processing.core.PVector
import processing.core.PApplet
import java.awt.Rectangle
import scala.util.Random


class Harpoon(p: PApplet, spawn:PVector, right: Boolean) extends Actor {
  p.sketchFile("Harpoon.png")
  p.sketchFile("HarpoonR.png")
  var location = spawn
  var img = if(right) p.loadImage("HarpoonR.png") else p.loadImage("Harpoon.png")
  val speed = if(right) new PVector(-10,0) else new PVector(0,4f)
  if(!right)img.resize(35, 150) else img.resize(150, 35)
  var rand = new Random
  var cosV = rand.nextFloat()*10
  var hit = false 
  private var first = true

  def getX = location.x
  def getY = location.y
  
  def getBounds = new Rectangle(getX.toInt,getY.toInt,img.width,img.height)
  
  
  def update = {
    location = location.copy().add(speed)
    cosV += rand.nextFloat()*10
  }
  
  
  def draw() = {
    p.image(img,location.x,location.y)
  }
  
   override def checkCollision(obj: Actor) = {
    if(obj.getBounds.intersects(this.getBounds) && flag){
      flag = false
      hitAction(obj)
      obj.hitAction(this)
      true
    }else{
      flag = true
      false
    }
  }
  
  override def hitAction(obj: Actor) = {
    if(first){
        Audio.play("Audio/harpoon.wav",false)
    	Whale.health -= 33 
    	first = false
    }
  }
  
  
  
  
  
  
}