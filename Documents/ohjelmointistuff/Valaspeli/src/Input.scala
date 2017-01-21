
import java.awt.Event._

import processing.event.MouseEvent._
import processing.core.PApplet
import processing.core.PVector
import processing.event.KeyEvent

class Input(m: ScalaProcessingExample){
  var seek = false
  var loc = new PVector(0,0)
  def keyPressed (e: KeyEvent)= {
     if (e.getKey == 'd'){
      m.state = STATE.GAME
     }
  }
  
   def mousePressed(location: PVector) = {
     loc = loc
    if(Menu.inBounds(location.x.toInt, location.y.toInt)){
      m.state = STATE.GAME
    }
   seek = true
    
  }
   
   def removeMouse = {
     
    
   seek = false
    
  }
   
   
  
  def update(mousex: Int, mousey: Int) = {
    
    if(seek) Whale.moveTo(new PVector(mousex, mousey))
     
    
  }
}