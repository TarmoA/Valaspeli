import processing.core._
import processing.core.PConstants._
import scala.util.Random
import scala.math._
import processing.event.KeyEvent
import scala.collection.mutable.Buffer
import java.awt.Rectangle

class WhaleGame extends PApplet {
  sketchFile("Characters/Whale.png")
  sketchFile("Map/SeaBed.png")
  sketchFile("Other/Play.png")
  sketchFile("Other/life.png")
  sketchFile("Characters/Pelican.png")
  sketchFile("Other/Sky2.png")
  sketchFile("Death.png")
  val pelicanImg = loadImage("Characters/Pelican.png")
  var sky = loadImage("Other/Sky2.png")
  var gameover = loadImage("Death.png")
  val pelicanSpawner = new PelicanHandler(this, pelicanImg)
  var running = true
  var currentAngle = 0f
  val input = new Input(this)
  var yoff = 0.0f; // 2nd dimension of perlin noise
  var state = STATE.MENU
  var radar = new Radar(this, Whale.position.x, Whale.position.y)
  var img = loadImage("Map/SeaBed.png")
  var menu = loadImage("Other/Play.png")
  var powerups = Buffer[Powerup]()
  
  Audio.setVolume(1)
  var bubble = loadImage("Other/Bubble.png")
  var last = new PVector(0, 0)
  var life = loadImage("Other/life.png")
  life.resize(25, 25)

//  val b = new Barrel(this, 400, true)
//  val k = new Drowner(this, 200, false)
//  val t = new Trash(this, 450, true)
  val NotHarpoonSpawner = new NotHarpoonSpawner(this)
  var harpoonSpawner = new HarpoonSpawner(this)
  var lastBubbleSound = 0L
  var timePerBubbleSound = 4

  def timeNow = System.currentTimeMillis() / 1000

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
      
      this.textSize(28)
      this.fill(255, 0, 0)
      this.text("Lives", 10, 28)
      var index = 1
      while (Whale.health - index * 33 > 0) {
        this.image(life, 55 + index * 25, 7)
        index += 1
      }
      
      this.text("Score " + Whale.score.toString(), this.width - 200, 28)
      
      
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
      var squirtAngle = 0f
      var lookAngle = 0f
      var distance = abs(angle - d)
      if (distance >= Whale.max_turn) { //||(angle > d - 1.toRadians && angle < d + 1.toRadians)) {
        currentAngle += Whale.max_turn
      } else {
        currentAngle = d
      }
      
      rotate(angle + 90.toRadians)
      val loc = -60
      if (Whale.dir() == 1) {
        image(Whale.img, loc, loc, Whale.img.width / 3, Whale.img.height / 3)
        
        //rect(-60, -70, Whale.img.width / 3, Whale.img.height / 3)
//        Whale.bounds = new Rectangle(-60, -70, Whale.img.width / 3, Whale.img.height / 3)
        
        squirtAngle = angle + 90.toRadians
      } else {
        pushMatrix()
        scale(1.0f, -1.0f)
        
        image(Whale.img, loc, loc, Whale.img.width / 3, Whale.img.height / 3)
        squirtAngle = angle - 90.toRadians
        popMatrix()
       }
      
      lookAngle = angle + Pi.toFloat
      popMatrix()
      Bubbles.bubbles.foreach { x => image(bubble, x.x, x.y.toInt, x.size, x.size) }
      squirtHandler.update(squirtAngle, lookAngle)
      pelicanSpawner.update
      harpoonSpawner.harpoons.foreach(_.draw())
      harpoonSpawner.harpoonsRight.foreach(_.draw())
      if (timeNow >= lastBubbleSound + timePerBubbleSound) {
        Audio.play("Audio/bubble.wav",false)
        lastBubbleSound = timeNow
      }
      
      if(Whale.isDestroyed){
        state = STATE.DEATH
      }
      
    } else if (state == STATE.MENU) {

      image(menu, Menu.x, Menu.y)
    } else if (state == STATE.DEATH){
      image(gameover, Menu.x, Menu.y)
    }
      

  }
  val squirtHandler = new SquirtHandler(this)
//This is an example from processing tutorials: https://processing.org/examples/noisewave.html
  def drawBackground = {
	  background(135, 206, 250);
	  image(sky, 0,0)
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
  
  def collision(powerup: Powerup) = {
    if(sqrt(pow(powerup.y - radar.circles(0).y, 2) + pow(powerup.x - radar.circles(0).x, 2)).toFloat < radar.circles(0).radius / 2)
      powerup.alpha = 255
  }
  
  def resetGame {
    powerups.clear()
    this.NotHarpoonSpawner.barrels.clear()
    this.NotHarpoonSpawner.drowners.clear()
    this.harpoonSpawner.harpoons.clear()
    this.pelicanSpawner.pelicans.clear()
    Whale.health = 100
    Whale.score = 0
    addPowerups(5)
    state = STATE.GAME
  }

  def tick() = {
    if(radar.circles.size > 0){
    	powerups.foreach(collision)
      radar.update()
    }
    powerups.foreach(_.update) 
    harpoonSpawner.tick(1f)
    Bubbles.bubbles.foreach { x => x.tick(1f) }
    Whale.tick(1)
    this.pelicanSpawner.pelicans.foreach(Whale.checkCollision(_))
//    this.squirtHandler.getSquirts.foreach(_.getBounds.intersection(r))
    //rect(Whale.position.x.toInt - 60, Whale.position.y.toInt - 70, Whale.img.width / 3, Whale.img.height / 2)
    input.update(mouseX, mouseY)
  }
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


object WhaleGame {
  def main(args: Array[String]) {
    PApplet.main(Array[String]("WhaleGame"))
  }
}
