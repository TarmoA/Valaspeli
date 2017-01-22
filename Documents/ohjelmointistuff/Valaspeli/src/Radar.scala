import processing.core._
import scala.collection.mutable.Buffer

class Radar(p: PApplet, var x: Float, var y: Float){
  
  var circles = Buffer[Circle]()
  var counter: Int = _
//  var isOn = false
  
  def useRadar(newX: Float, newY: Float) {
    circles.clear()
    counter = 0
    x = newX
    y = newY
    circles += new Circle(p, x, y)
  }
  
  def update() {
    if(counter <= 100) counter += 1 
    if(counter % 20 == 0) circles += new Circle(p, x, y)
    circles.foreach(_.update)
    circles = circles.filter(_.radius < 400)
  }    
}