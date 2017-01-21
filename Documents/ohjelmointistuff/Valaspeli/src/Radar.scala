import processing.core._
import scala.collection.mutable.Buffer

class Radar(p: PApplet, var x: Float, var y: Float){
  
  var circles = Buffer[Circle]()
  var counter = 0
  var isOn = false
  
  def useRadar(newX: Float, newY: Float) {
    circles.clear()
    x = newX
    y = newY
    isOn = true
    circles += new Circle(p, x, y)
  }
  
  def update() {
    if(counter == 200) counter = 0 else counter += 1
    if(circles.size < 5 && counter % 10 == 0) circles += new Circle(p, x, y)
    circles.foreach(_.update)
    circles.filter(_.radius < 500)
  }
}