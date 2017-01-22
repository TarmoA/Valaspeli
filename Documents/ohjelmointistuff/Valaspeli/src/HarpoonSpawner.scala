import scala.collection.mutable._
import processing.core.PVector
import processing.core.PApplet
import java.util.Random

class HarpoonSpawner(p: PApplet) {
  var harpoons = Buffer[Harpoon]()
  var harpoonsRight = Buffer[Harpoon]()
  spawnHarpoonsRight
  spawnHarpoons(400)
  var up = false
  var right = true
  var x = 1200.0
  var y = -100.0
  var random = new Random

  private def spawnHarpoonsRight() = {

    harpoonsRight = Buffer[Harpoon]()

    for (y <- 230 to 680 by 100) {

      harpoonsRight += new Harpoon(p, new PVector(1200, y), true)
    }

  }

  private def spawnHarpoons(amount: Int) = {
    harpoons = Buffer[Harpoon]()
    for (x <- 50 to 1200 by amount) {
//      if (random.nextInt(10) > 4) {
      harpoons += new Harpoon(p, new PVector(x, -120), false)
//      }
    }

  }
  def tick(delta: Float) = {
    y += 2
    if (y >= 800) {
      if (random.nextInt(10) > 2) {
        spawnHarpoons(amount = random.nextInt(300) + 200)
      }
      y = 0
    }
    harpoons.foreach { x =>
      x.update
      Whale.checkCollision(x)
    }

  }
}