import scala.collection.mutable.Buffer
import processing.core._
import scala.math._
import java.awt.Rectangle
import java.awt.geom.Ellipse2D

class Squirt(p: PApplet, whalePos: PVector, height: PVector, width: PVector, direction: PVector, squirtOffsetAngle: Float) extends Actor {
  
  //def straightDir = direction.copy.rotate((Pi/2).toFloat)
  
  var lifeTime = 45
  def isDead = lifeTime <= 0
  
  var particles = Buffer[Particle]()
  
  var posDelta = new PVector(90,20)

    
  def run = {
    if (!isDead) {
      update
    }
  }
  
  
  def getVel = {
    direction.copy.rotate(p.random(-0.08f,0.08f)).mult(p.random(10f,15f))
  }
  def getOffSet = {
    val offSet = new PVector(-60,0)
    if (Whale.dir == 1){
      new PVector(whalePos.x,whalePos.y).add(offSet.copy.rotate(squirtOffsetAngle)).sub(direction.copy.mult(10))
    }else new PVector(whalePos.x,whalePos.y).add(offSet.copy.rotate(squirtOffsetAngle + Pi.toFloat)).sub(direction.copy.mult(10))
  }
  
  def getSquirt0Pos = {
  getOffSet
  }

  def mkParticle(amount: Int) = {
    for (int <- 1 to amount) {
      val particle = new Particle(p,getSquirt0Pos,getVel, new PVector(0,p.random(0.1f,1f)))
      particles += particle
    }
  }
  
  def update = { 
  mkParticle(5)
  particles.foreach(_.run)
  particles = particles.filter(!_.isDead)
  lifeTime -= 1
  }
 
  def getBounds = new Rectangle((getOffSet.x).toInt, (getOffSet.y).toInt, 50, 150)
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



class SquirtHandler(p:PApplet) {
      
    val squirtHeight = new PVector(0,-80)
    val squirtWidth = new PVector(-20,0)
    var squirts = Buffer[Squirt]() 
    def getSquirts = this.squirts
    def timeNow = System.currentTimeMillis() / 1000
    var lastSquirt = 0L
    val timeBetweenSquirts = 1
    val squirtDir = new PVector(0,-1)
    var squirtAngle = 0f
    //var squirtOffset = new PVector(90,0)
    var squirtOffsetAngle = 0f
    def squirt = {
      if (timeNow >= lastSquirt + timeBetweenSquirts) {
        val squirt = new Squirt(p,Whale.position, squirtHeight.copy, squirtWidth.copy, squirtDir.copy.rotate(squirtAngle), squirtOffsetAngle)
        squirts += squirt
        lastSquirt = timeNow
      }
    }
    
    
    def update(sqAngle: Float, lkAngle: Float) = {
      squirts.foreach(_.run)
      squirts = squirts.filter(!_.isDead)
      squirtAngle = sqAngle
      squirtOffsetAngle = lkAngle
      

  }



  }