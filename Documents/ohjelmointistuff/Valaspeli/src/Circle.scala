import processing.core._
import java.awt.Rectangle

class Circle(p: PApplet, var x: Float, var y: Float){
  
  var radius: Int = _
 
  def update() {
    radius += 2
    p.stroke(255, 255, 255, 400 - radius)
    p.strokeWeight(3)
    p.noFill
    p.ellipse(x, y, radius, radius)
  }
  
}