import scala.collection.mutable.Buffer


object Bubbles {
  var bubbles = Buffer[Bubble]()
  
  def tick(delta: Float) = {
    bubbles.foreach { x => x.tick(delta) }
  }
  
  for(x <- 0 until 20){
    bubbles += new Bubble
  }
}