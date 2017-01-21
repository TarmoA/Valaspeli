import scala.collection.mutable.Buffer
import processing.core._
import scala.math._

class Squirt(p: PApplet, position: PVector, height: PVector, width: PVector) {
  
  //def straightDir = direction.copy.rotate((Pi/2).toFloat)
  
  var lifeTime = 30
  def isDead = lifeTime <= 0
  
  var particles = Buffer[Particle]()

    
  def run = {
    if (!isDead) {
      update
      display
    }
  }
  
  def mkParticle(amount: Int) = {
    for (int <- 1 to amount) {
      val particle = new Particle(p,new PVector(position.x - 10,position.y - 60),new PVector(p.random(-2,2),p.random(-10,-15)), new PVector(0,p.random(1,2)))
      particles += particle
    }
  }
  
  def update = { 
  mkParticle(3)
  particles.foreach(_.run)
  particles = particles.filter(!_.isDead)
  lifeTime -= 1
  }
  var calcPos = position.copy 
 /* val coords = Array[Float](position.x, position.y,
         calcPos.add(height).x, calcPos.y,
         calcPos.add(width).x,calcPos.y,
         calcPos.sub(height).x,calcPos.y
         )*/
         
         
  val rect = (position.x, position.y,width.x, height.y)
  
  def display = {
    /*p.quad(position.x,   position.y, 
        position.x+10, position.y,
        position.x+15, position.y-100,
        position.x -5, position.y-100
  )*/
  
  p.rect(position.x, position.y,width.x, height.y)
  //p.quad(coords(0),coords(1),coords(2),coords(3),coords(4),coords(5),coords(6),coords(7))
  }
  


  
  
}

class Particle(p:PApplet, position: PVector, velocity: PVector, acceleration: PVector) {
  
  val colorMap = Map[String,Array[Int]]("blue" -> Array[Int](102,102,255), "lightBlue" -> Array[Int](210,210,255))
  
  var lifeTime = 30
  def isDead = lifeTime <= 0
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
    p.ellipse(position.x, position.y, 5, 5)
  }
  
  
}