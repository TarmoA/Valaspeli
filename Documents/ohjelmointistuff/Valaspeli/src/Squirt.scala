import scala.collection.mutable.Buffer
import processing.core._
import scala.math._
import java.awt.Rectangle

class Squirt(p: PApplet, whalePos: PVector, height: PVector, width: PVector) {
  
  //def straightDir = direction.copy.rotate((Pi/2).toFloat)
  
  var lifeTime = 45
  def isDead = lifeTime <= 0
  
  var particles = Buffer[Particle]()
  
  var posDelta = new PVector(90,20)

    
  def run = {
    if (!isDead) {
      update
      display
    }
  }
  
  def mkParticle(amount: Int) = {
    for (int <- 1 to amount) {
      val particle = new Particle(p,new PVector(whalePos.x + posDelta.x,whalePos.y + posDelta.y),new PVector(p.random(-3,3),p.random(-20,-25)), new PVector(0,p.random(1,2)))
      particles += particle
    }
  }
  
  def update = { 
  mkParticle(5)
  particles.foreach(_.run)
  particles = particles.filter(!_.isDead)
  lifeTime -= 1
  }
  //var calcPos = whalePos.copy 
 /* val coords = Array[Float](whalePos.x, whalePos.y,
         calcPos.add(height).x, calcPos.y,
         calcPos.add(width).x,calcPos.y,
         calcPos.sub(height).x,calcPos.y
         )*/
         
         
  //val rect = (whalePos.x, whalePos.y,width.x, height.y)
  def bounds = new Rectangle((whalePos.x + posDelta.x).toInt, (whalePos.y + posDelta.y).toInt, width.x.toInt, height.y.toInt)
  def display = {
    /*p.quad(whalePos.x,   whalePos.y, 
        whalePos.x+10, whalePos.y,
        whalePos.x+15, whalePos.y-100,
        whalePos.x -5, whalePos.y-100
  )*/
  
    
  //p.rect(whalePos.x + posDelta.x, whalePos.y + posDelta.y,width.x, height.y)
  //p.quad(coords(0),coords(1),coords(2),coords(3),coords(4),coords(5),coords(6),coords(7))
  }
  


  
  
}

class Particle(p:PApplet, position: PVector, velocity: PVector, acceleration: PVector) {
  
  val colorMap = Map[String,Array[Int]]("blue" -> Array[Int](102,102,255), "lightBlue" -> Array[Int](210,210,255))
  val startingPos = position.copy
  var lifeTime = 30
  def isDead = {
    lifeTime <= 0 || position.y >= startingPos.y + 40
  }
  def run = {
    update
    display
  }
  
  def update() = {
    position.add(velocity)
    velocity.add(acceleration)
    lifeTime -= 1
  }
  
  def display = {
    var color = ""
    if(p.random(0,1)<=0.5)color = "blue" else color = "lightBlue"
    p.stroke(colorMap(color)(0), colorMap(color)(1), colorMap(color)(2))
    p.fill(colorMap(color)(0), colorMap(color)(1), colorMap(color)(2))
    p.ellipse(position.x, position.y, 6, 6)
  }
  
  
}