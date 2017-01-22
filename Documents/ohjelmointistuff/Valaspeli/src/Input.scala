
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
     if (e.getKey == 'd'){
      m.state = STATE.GAME
     }else if(e.getKey == 'e'){
       if(Whale.dir == 1) 
         m.radar.useRadar(Whale.position.x + 30, Whale.position.y - 30)
       else
         m.radar.useRadar(Whale.position.x - 30, Whale.position.y - 30)
     }else if(e.getKey == 't'){
       m.powerups.foreach(_.alpha = 255)
     }
     if (e.getKey == 'a') {
       m.squirtHandler.squirt

     }
      
     if (e.getKey == 'd'){
      m.state = STATE.GAME
     }
     if (e.getKey == 'a') m.squirtHandler.squirt
     if (e.getKey == 'd') m.pelicanSpawner.spawn(new PVector(-100,m.random(0,450)), new PVector(m.random(5,7),m.random(1,2)), new PVector(0,m.random(-0.05f,-0.01f)))
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
