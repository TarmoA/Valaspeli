
import java.awt.Event._
import java.awt.event.KeyEvent
import processing.event.MouseEvent._
import processing.core.PApplet
import processing.core.PVector

object Input{
  var seek = false
  var loc = new PVector(0,0)
  def keyPressed (e: KeyEvent)= {
     if (e.getKeyCode() == KeyEvent.VK_D) seek = true
  }
  
   def mousePressed(location: PVector) = {
     loc = loc
    
   seek = true
    
  }
   
   def removeMouse = {
     
    
   seek = false
    
  }
   
   
  
  def update(mousex: Int, mousey: Int) = {
    
    if(seek) Whale.moveTo(new PVector(mousex, mousey))
     
    
  }
}