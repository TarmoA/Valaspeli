import java.awt.Rectangle


trait Actor {
  var flag = true
  var health = 100
  var damage = 100
  def getBounds: Rectangle
  def checkCollision(obj: Actor) = {
    if(obj.getBounds.intersects(this.getBounds) && flag){
      flag = false
      hitAction(obj)
      obj.hitAction(this)
      true
    }else{
      false
    }
  }
  
  def hitAction(obj: Actor) = {
    if(obj.isInstanceOf[Trash])
      health = health - obj.damage
  }
  
  def isDestroyed: Boolean = health <= 0
}