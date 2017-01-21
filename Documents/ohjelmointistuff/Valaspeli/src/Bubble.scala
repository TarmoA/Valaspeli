import java.util.Random

class Bubble {
  var random = new Random
  var x = random.nextInt(1200)
  var y = random.nextInt(200) + 600.0
  var vel = random.nextInt(200) / 100.0
  var size = random.nextInt(10) + 20
  def tick(delta: Float) {
    if (y >= 220) {
      y -= vel
    } else{
      y = random.nextInt(200) + 600.0
    }

  }
}