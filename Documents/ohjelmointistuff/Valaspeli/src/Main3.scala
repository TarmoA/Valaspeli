/*
import processing.core._
import processing.core.PConstants._
import scala.util.Random
import scala.math._
//import scala.swing.event.MousePressed
import scala.math._

class ScalaProcessingExample extends PApplet {
  sketchFile("Characters/Whale.png")
  sketchFile("Map/SeaBed.png")
  var running = true
  var yoff = 0.0f; // 2nd dimension of perlin noise
  var state = STATE.GAME
  var img = loadImage("Map/SeaBed.png")

  override def setup() = {
    frameRate(120)

  }

  override def settings() {
    size(1140, 680);
  }

  override def draw() {
    tick
    drawBackground
    image(img, 0,480)
    pushMatrix()

    translate(Whale.position.x, Whale.position.y)
    var angle = 0f
    if (Whale.dir() == 1) {
      angle = -PVector.angleBetween(Whale.sub(Whale.position, Whale.target), new PVector(0, 1))
      println(angle.toDegrees)
    } else {
      angle = PVector.angleBetween(Whale.sub(Whale.position, Whale.target), new PVector(0, 1))
    }
    rotate(angle + 90.toRadians)
    if (Whale.dir() == 1) {
      image(Whale.img, 0, 0, Whale.img.width / 3, Whale.img.height / 3)
    } else {
      pushMatrix()
      scale(1.0f, -1.0f)
      image(Whale.img, 0, 0, Whale.img.width / 3, -Whale.img.height / 3)
      popMatrix()
    }

    popMatrix()

  }

  def drawBackground = {

    background(135, 206, 250);

    fill(142, 229, 238);
    // We are going to draw a polygon out of the wave points
    beginShape();
    noStroke()
    stroke(92, 172, 238)
    var xoff = 0f; // Option #1: 2D Noise
    // float xoff = yoff; // Option #2: 1D Noise

    // Iterate over horizontal pixels
    for (x <- 0 to width by 10) {
      // Calculate a y value according to noise, map to 
      var y = PApplet.map(noise(xoff, yoff), 0, 1, 200, 255) // Option #1: 2D Noise
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
  }

  def tick() = {

    Whale.tick(1)
    Input.update(mouseX, mouseY)
  }
  var thread = new Thread {
    override def run() = {
      var counter = 0
      var last = System.currentTimeMillis()
      while (running) {
        var now = System.currentTimeMillis()
        var delta = (now - last) / 10f
        last = now
        if (state == STATE.GAME) {
          Whale.tick(delta)
          if (counter > 90000000) {
            println(delta)
            counter = 0
          }
          counter += 1
        }

        //        Input.update(mouseX, mouseY)

      }

    }

  }
  //  thread.start

  override def mousePressed {
    Input.mousePressed(new PVector(mouseX, mouseY))
  }

  override def mouseReleased {
    Input.removeMouse
  }

}

object ScalaProcessingExample {
  def main(args: Array[String]) {
    PApplet.main(Array[String]("ScalaProcessingExample"))
  }
}*/