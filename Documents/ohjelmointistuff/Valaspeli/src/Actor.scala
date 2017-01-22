import java.awt.Rectangle


trait Actor {
  var flag = true
  var health = 100
  var damage = 100
  var scorePlus = 1000
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
    if(obj.isInstanceOf[Squirt])
      Whale.score += 500
    if(obj.isInstanceOf[Barrel])
    	Whale.score += this.scorePlus
    if(obj.isInstanceOf[Harpoon])
      health = health - obj.damage
  }
  
  def isDestroyed: Boolean = health <= 0
  
  def getScore = this.scorePlus
}