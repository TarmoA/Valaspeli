
import java.awt.Event._

import processing.event.MouseEvent._
import processing.core.PApplet
import processing.core.PVector
import processing.event.KeyEvent
import scala.math._

class Input(m: WhaleGame){
  var seek = false
  var loc = new PVector(0,0)
  
  def keyPressed (e: KeyEvent)= {
     if (e.getKey == 'd' || e.getKey == 'D'){
      m.state = STATE.GAME
     }else if(e.getKey == 'e' || e.getKey == 'E'){
       if(Whale.dir == 1) 
         m.radar.useRadar(Whale.position.x + 30, Whale.position.y - 30)
       else
         m.radar.useRadar(Whale.position.x - 30, Whale.position.y - 30)
     }else if(e.getKey == 't'){
       m.powerups.foreach(_.alpha = 255)
     }
     if (e.getKey == 'a' || e.getKey == 'A') m.squirtHandler.squirt
      
     if (e.getKey == 'd' || e.getKey == 'D'){
      m.state = STATE.GAME
     }
      
  }
  
   def mousePressed(location: PVector) = {
     loc = loc
    if(Menu.inBounds(location.x.toInt, location.y.toInt) && m.state == STATE.MENU){
      m.state = STATE.GAME
    }else if(Menu.inBounds(location.x.toInt, location.y.toInt) && m.state == STATE.DEATH){
      m.resetGame
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