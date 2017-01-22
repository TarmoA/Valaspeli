import processing.core._
import processing.core.PConstants._
import scala.util.Random
import scala.math._
//import scala.swing.event.MousePressed
import scala.math._
import processing.event.KeyEvent
import java.awt.Rectangle

class ScalaProcessingExample extends PApplet {
  sketchFile("Characters/Whale.png")
  sketchFile("Map/SeaBed.png")
  sketchFile("Other/Play.png")
  sketchFile("Other/life.png")
  var running = true
  var currentAngle = 0f
  val input = new Input(this)
  var yoff = 0.0f; // 2nd dimension of perlin noise
  var state = STATE.MENU
  var radar = new Radar(this, Whale.position.x, Whale.position.y)
  var img = loadImage("Map/SeaBed.png")
  var menu = loadImage("Other/Play.png")
  var bubble = loadImage("Other/Bubble.png")
  var last = new PVector(0, 0)
  var life = loadImage("Other/life.png")
  life.resize(25, 25)

  val b = new Barrel(this, 400, true)
  val k = new Drowner(this, 200, false)
  val t = new Trash(this, 450, true)
  val NotHarpoonSpawner = new NotHarpoonSpawner(this)


  override def setup() = {
    frameRate(120)

  }

  override def settings() {
    size(1140, 680);
  }

  override def draw() {
    drawBackground
    image(img, 0, 480)
    if (state == STATE.GAME) {
      tick
      
      this.textSize(28)
      this.fill(255, 0, 0)
      this.text("Lives", 10, 28)
      var index = 1
      while (Whale.health - index * 33 > 0) {
        this.image(life, 55 + index * 25, 7)
        index += 1
      }
      
      this.text("Score " + Whale.score.toString(), this.width - 150, 28)
      
      
      b.move()
      k.move()
      t.move()
      NotHarpoonSpawner.tick
      
      pushMatrix()

      translate(Whale.position.x, Whale.position.y)

      var angle = 0f
      var delt = 0f

      //      var v = Whale.normalize(Whale.velocity).mult(8)
      var v = new PVector(0, 0)
      var d = PVector.angleBetween(v, new PVector(1, 0))
      line(Whale.position.x, Whale.position.y, Whale.position.x + v.x, Whale.position.y + v.y)

      if (Whale.dir() == 1) {
        angle = -PVector.angleBetween(Whale.sub(Whale.position, new PVector(mouseX, mouseY)), new PVector(0, 1))
      } else {
        angle = PVector.angleBetween(Whale.sub(Whale.position, new PVector(mouseX, mouseY)), new PVector(0, 1))
      }

      var sign = {
        if (angle < 0) {
          -1
        } else {
          1
        }
      }
//      println("d: " + d.toDegrees)
      var squirtAngle = 0f
      var lookAngle = 0f
      var distance = abs(angle - d)
      if (distance >= Whale.max_turn) { //||(angle > d - 1.toRadians && angle < d + 1.toRadians)) {
        currentAngle += Whale.max_turn
      } else {
        currentAngle = d
      }
      //      println("max: " + Whale.max_turn + "angle: " + angle)
      //      angle = abs(min(abs(Whale.max_turn), abs(angle)))// + 90.toRadians
      //      println("Väli: " + angle)
      //      angle = angle*sign
      //      println("Hei: " + angle)

      rotate(angle + 90.toRadians)
      val loc = -60
      if (Whale.dir() == 1) {
        image(Whale.img, loc, loc, Whale.img.width / 3, Whale.img.height / 3)
        
        //rect(-60, -70, Whale.img.width / 3, Whale.img.height / 3)
        Whale.bounds = new Rectangle(-60, -70, Whale.img.width / 3, Whale.img.height / 3)
        
        squirtAngle = angle + 90.toRadians
      } else {
        pushMatrix()
        scale(1.0f, -1.0f)
        
        Whale.bounds = new Rectangle(-60, -70, Whale.img.width / 3, Whale.img.height / 2)
        //rect(-60, -70, Whale.img.width / 3, Whale.img.height / 3)
        
        image(Whale.img, loc, loc, Whale.img.width / 3, Whale.img.height / 3)
        squirtAngle = angle - 90.toRadians
        popMatrix()
      }
      lookAngle = angle + Pi.toFloat
      popMatrix()
      Bubbles.bubbles.foreach { x => image(bubble, x.x, x.y.toInt, x.size, x.size) }
      squirtHandler.update(squirtAngle, lookAngle)
    } else if (state == STATE.MENU) {

      image(menu, Menu.x, Menu.y)
    }

  }
  val squirtHandler = new SquirtHandler(this)

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

  def setState(s: STATE.Value) = state = s

  def tick() = {
    if(radar.isOn) radar.update()
    Bubbles.bubbles.foreach { x => x.tick(1f) }
    Whale.tick(1)
//    this.squirtHandler.getSquirts.foreach(_.getBounds.intersection(r))
    //rect(Whale.position.x.toInt - 60, Whale.position.y.toInt - 70, Whale.img.width / 3, Whale.img.height / 2)
    
    input.update(mouseX, mouseY)
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

  override def keyPressed(e: KeyEvent){
    input.keyPressed(e)
  }

  override def mousePressed {
    input.mousePressed(new PVector(mouseX, mouseY))
  }

  override def mouseReleased {
    input.removeMouse
  }

}


object ScalaProcessingExample {
  def main(args: Array[String]) {
    PApplet.main(Array[String]("ScalaProcessingExample"))
  }
}
