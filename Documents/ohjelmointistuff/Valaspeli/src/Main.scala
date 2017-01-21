
import processing.core._
import processing.core.PConstants._
import scala.math._
//import scala.swing.event.MousePressed

class ScalaProcessingExample extends PApplet {

 var yoff = 0.0f;
 val k = new Barrel(this, 400, true)
 val d = new Drowner(this, 200, false)
 
 override def setup() {
   //frameRate(15)
 }

  override def settings() {
   size(1140, 680);
  }
  
  def tick() = {
    Whale.tick(1)
    Input.update(mouseX, mouseY)
  }
  
  override def mousePressed {
    Input.mousePressed(new PVector(mouseX, mouseY))
  }

  override def mouseReleased {
    Input.removeMouse
  }
  
  
override def draw() {
  background(0);
  
  fill(194, 223, 255);
  // We are going to draw a polygon out of the wave points
  beginShape(); 
  
  var xoff = 0f;       // Option #1: 2D Noise
  // float xoff = yoff; // Option #2: 1D Noise
  
  // Iterate over horizontal pixels
  for (x <- 0 to width by 15) {
    // Calculate a y value according to noise, map to 
    var y = PApplet.map(noise(xoff, yoff), 0, 1, 250,300) // Option #1: 2D Noise
    // float y = map(noise(xoff), 0, 1, 200,300);    // Option #2: 1D Noise
    
    // Set the vertex
    vertex(x, y); 
    // Increment x dimension for noise
    xoff += 0.05f
  }
  // increment y dimension for noise
  yoff += 0.005f
  vertex(width, height);
  vertex(1, height);
  endShape(PConstants.CLOSE);
  
  
  image(Whale.img, Whale.position.x, Whale.position.y, Whale.img.width / 3, Whale.img.height / 3)
  this.tick()
  k.move()
  d.move()
  }
  
}



object ScalaProcessingExample {
  def main(args: Array[String]) {
    PApplet.main(Array[String]("ScalaProcessingExample"))
  }
}