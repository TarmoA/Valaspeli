import java.awt.Rectangle


trait Actor {
  var flag = true
  var health = 100
  var damage = 100
  var scorePlus = 1000
  def getBounds: Rectangle
  def checkCollision(obj: Actor) = {
    if(obj.getBounds.intersects(this.getBounds) && flag){
      println("lit")
      flag = false
      hitAction(obj)
      obj.hitAction(this)
      true
    } else {
//      flag = true
      false
    }
  }
  
  def hitAction(obj:Actor) = {
    health = health - obj.damage
    Whale.score += this.scorePlus
  }
  
  def isDestroyed: Boolean = health <= 0
  
  def getScore = this.scorePlus
}