

import java.util.Timer

import scala.math.min
import processing.core.PImage
import processing.core.PApplet
import processing.core.PVector
import java.awt.geom.Rectangle2D
import java.awt.Rectangle

object Whale extends PApplet with Actor {
  sketchFile("Characters/Whale.png")
  var x = 100
  var y = 600
  var img = loadImage("Characters/Whale.png")
  //var isDestroyed = false
  //var health = 100
  var timer = new Timer
  var lives = 3
  var target_offset: PVector = _
  var clipped_speed: Float = _
  var distance: Float = _
  var position = new PVector(x, y)
  var max_speed = 10.0f
  var velocity = new PVector(0, 0)
  var target = new PVector(100, 100)
  var desired_velocity: PVector = _
  var score = 0
  var old_pos = position
  var bounds = new Rectangle(position.x.toInt, position.y.toInt, img.width, img.height)
  
  def arrive(target: PVector, delta: Float) = {
    def getDesired_velocity = target_offset.mult(clipped_speed / distance)
    //println("pos: " + position)
    target_offset = position.sub(target)
    //println("off:" + target_offset)
    if(target_offset !=0){
    distance = target_offset.mag()
    }
    var slow_distance = 150.0f
    var ramped_speed = max_speed * (distance / 150.0f)
    clipped_speed = min(ramped_speed, max_speed)
    desired_velocity = target_offset.mult(clipped_speed / distance)
    //println("des: " + desired_velocity)
    var steering = velocity.sub(desired_velocity)
    //println("Steering: " + steering)
    steering
  }
  var counter = 0
  /*def tick(delta: Float) = {
    position = old_pos
    //println("pos tick: " + position)
    position = position.add(arrive(target, delta))
    //println("pos: jälkeen" + position)
//    if (position.x < 0) {
//      position.x = 0
//    }
//    if (position.y < 0) {
//      position.y = 0
//    }
//    if (position.x > 1140) {
//      position.x = 1140
//    }
//    if (position.y > 640) {
//      position.y = 640
//    }
    //    if(counter > 90000000){
    //    //println(position)
    counter = 0
    //    }
    counter += 1
  }*/
  
  
   def tick(delta: Float) = {

    if (!isDestroyed) {
      
      position = position.add(arrive(target, delta))
    }
    if (position.x < 0) {
      position.x = 0
    }
    if (position.y < 0) {
      position.y = 0
    }
    if (position.x > 1080) {
      position.x = 1080
    }
    if (position.y > 640) {
      position.y = 640
    }

  }
  
  def getNormal = {
    sub(target, position).rotate(math.Pi.toFloat / 2f)
  }
  
  
  def dir() = {
    var dis = sub(target, position)
    if (dis.x > 0) {
      0
    } else {
      1
    }
  }
  
  //def bounds(): Rectangle2D = new Rectangle(Whale.position.x.toInt, Whale.position.y.toInt, Whale.img.width,Whale.img.height )

  def sub(v: PVector, v2: PVector) = {
    new PVector(v2.x - v.x, v2.y - v.y)
  }
  
  def getBounds = bounds
  
  def moveTo(location: PVector) = {
//        position = location
//    var old_pos = position
//    target = location
//    println(position + ", " + location + ", " + target)
//    var offset = position.sub(location).normalize()
//        println(offset)
//    target = offset.add(offset.mult(2f))
//    println("Target: " + target)
//    println("Old pos:" + old_pos)
//    position = old_pos
    target = location

  }

  override def toString = "Choo choo, lives: " + lives + ", score: " + score
}