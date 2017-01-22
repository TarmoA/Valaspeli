import scala.collection.mutable.Buffer
import processing.core._
import scala.math._
import java.awt.Rectangle

class Pelican(p: ScalaProcessingExample,position: PVector, velocity: PVector, acceleration: PVector, img: PImage) extends Actor{
  
 
  def run = {
    update
    display
  }
  var acceleratingUp = false
  def update = {
    position.add(velocity)
    velocity.add(acceleration)
    p.squirtHandler.squirts.foreach(this.checkCollision(_))
    Whale.checkCollision(this)
    if (!flag) velocity.add(new PVector(0,-0.1f))
    
  }
  
  def getBounds = {
    new Rectangle(position.x.toInt,position.y.toInt, img.width/2, img.height/2)
  }
  
  def display = {
    p.pushMatrix
    p.rotate(-1*PVector.angleBetween(velocity,new PVector(1,0)))
    p.image(img,position.x, position.y,img.width/2, img.height/2)
    p.popMatrix
  }
}

class PelicanHandler(p: ScalaProcessingExample, img: PImage) {
  val pelicans = Buffer[Pelican]()
  

  var lastSpawned = timeNow
  val timePerSpawn = 3
  def timeNow = System.currentTimeMillis() / 1000
  def update = {
    pelicans.foreach(_.run)
    if (timeNow >= lastSpawned + timePerSpawn){
      spawn(new PVector(-100,p.random(0,150)), new PVector(p.random(5,6),p.random(1,2)), new PVector(0,-0.02f))
      lastSpawned = System.currentTimeMillis / 1000
    }
  }
  
  def spawn(position: PVector, velocity: PVector, acceleration: PVector) = {
    pelicans += new Pelican(p,position,velocity, acceleration, img)
  }
}