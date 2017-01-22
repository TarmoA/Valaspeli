import scala.util.Random
import scala.collection.mutable.Buffer


class NotHarpoonSpawner(P: ScalaProcessingExample) {
    
  var barrels = Buffer[Barrel]()
  var drowners = Buffer[Drowner]()
  val Rand = new Random
  var time = P.millis()
  
  def decide = {
    if (Rand.nextInt(2) == 1) true else false
  }
  
  def spawnDrowner = {
    val uusi = new Drowner(P, 20 + Rand.nextInt(P.width - 40), decide)
    drowners += uusi
    uusi
  }
  
  def spawnBarrel = {
    val uusi = new Barrel(P, 20 + Rand.nextInt(P.width - 40), decide)
    barrels += uusi
    uusi 
  }
  
  def tick = {
    if (P.millis() > time + 5 * 1000) {
      if (decide) {
        this.spawnDrowner
      } else {
        this.spawnBarrel
      }
      time = P.millis()
    }
    
    this.barrels.foreach(_.move)
    this.drowners.foreach(_.move)
  }
  
}