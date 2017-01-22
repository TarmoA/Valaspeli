
import java.awt.Event._

import processing.event.MouseEvent._
import processing.core.PApplet
import processing.core.PVector
import processing.event.KeyEvent
import scala.math._

class Input(m: ScalaProcessingExample){
  var seek = false
  var loc = new PVector(0,0)
  
  def keyPressed (e: KeyEvent)= {
     if (e.getKey == 'e'){
      m.state = STATE.GAME
     }else if(e.getKey == 'e'){
       m.radar.useRadar(Whale.position.x + 100, Whale.position.y + 50)
       m.radar.isOn = true
     }
     if (e.getKey == 'a') m.squirtHandler.squirt
      
     if (e.getKey == 'd'){
      m.state = STATE.GAME
     }
     if (e.getKey == 'a') m.squirtHandler.squirt
      
  }
  
   def mousePressed(location: PVector) = {
     loc = loc
    if(Menu.inBounds(location.x.toInt, location.y.toInt)){
      m.state = STATE.GAME
    }
   seek = true
    
  }
   
   def removeMouse = {
     
    
//   seek = false
    
  }
   
   
  
  def update(mousex: Int, mousey: Int) = {
    
    if(seek) Whale.moveTo(new PVector(mousex, max(mousey, 250)))
     
    
  }
}