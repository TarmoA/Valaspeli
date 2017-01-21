import processing.core._
import processing.core.PConstants._
import scala.util.Random
import scala.math._
//import scala.swing.event.MousePressed
import scala.math._
import processing.event.KeyEvent
import scala.collection.mutable.Buffer
import java.awt.Rectangle

class ScalaProcessingExample extends PApplet {
  sketchFile("Characters/Whale.png")
  sketchFile("Map/SeaBed.png")
  sketchFile("Other/Play.png")
  sketchFile("Other/life.png")
  var running = true
  val input = new Input(this)
  var yoff = 0.0f; // 2nd dimension of perlin noise
  var state = STATE.MENU
  var radar = new Radar(this, Whale.position.x, Whale.position.y)
  var img = loadImage("Map/SeaBed.png")
  var menu = loadImage("Other/Play.png")
  var powerups = Buffer[Powerup]()
  
  
  var life = loadImage("Other/life.png")
  life.resize(25, 25)
  
  val b = new Barrel(this, 400, true)
  val d = new Drowner(this, 200, false)
  val t = new Trash(this, 450, false)
  
  
  
  override def setup() = {
    frameRate(120)
    addPowerups(5)
  }

  override def settings() {
    size(1140, 680);
  }

  override def draw() {
    drawBackground
    image(img, 0, 480)
    if (state == STATE.GAME) {
      tick
      
      
      pushMatrix()

      translate(Whale.position.x, Whale.position.y)

      var angle = 0f
      if (Whale.dir() == 1) {
        
        angle = -PVector.angleBetween(Whale.sub(Whale.position, Whale.target), new PVector(0, 1))
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
      fill(0,255,0)
      this.rect(Whale.position.x, Whale.position.y, Whale.img.width / 3, Whale.img.height / 3)
    } else if(state == STATE.MENU){
      image(menu, Menu.x, Menu.y) 
    }
    
    b.move()
    d.move()
    t.move()
    
    this.textSize(28)
    this.fill(255, 0, 0)
    this.text("Lives", 10, 28)
    var index = 1
    while( Whale.health - index * 33 > 0) {
      this.image(life, 55 + index * 25, 7)
      index += 1
    }
    
  }

  def drawBackground = {

    background(135, 206, 250);
    strokeWeight(1)
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
  
  def addPowerups(i: Int) = {
	  for(a <- 0 until i)
		  powerups += new Powerup(this, a*225 + Random.nextInt(225), 570 + Random.nextInt(20))
  }  

  def tick() = {
    if(radar.circles.size > 0){
    	powerups.foreach(_.checkCollision(radar.circles(0)))
      radar.update()
    }
    powerups.foreach(_.display) 
    Whale.tick(1)
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