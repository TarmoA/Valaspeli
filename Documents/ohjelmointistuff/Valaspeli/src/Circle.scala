import processing.core._

class Circle(p: PApplet, x: Float, y: Float){
  
  var radius: Int = _
  
  def update() {
    radius += 3
    p.stroke(255, 255, 255, 400 - radius)
    p.strokeWeight(3)
    p.noFill
    p.ellipse(x, y, radius, radius)
  }
  
}