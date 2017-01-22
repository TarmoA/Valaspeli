
package generalpackage
import javax.sound.sampled._
import javax.sound.sampled.FloatControl
import java.io.File
import java.math._
import scala.collection.mutable.Buffer

object Audio {
  private var on = true
  private var volume = 0f
  private var volumes = Buffer[FloatControl]()


  def toggle = {
    if (on) on = false
    else on = true
  }

 
  def setVolume(newVol: Float) = volume = newVol

  
  def changeVolume(delta: Float) = {
    if (volume + delta >= 6.0206) {
      volume = 6.0206f
    } else if (volume + delta <= -80f) {
      volume = -80f
    } else {
      volume += delta
    }
  }

 
  def currentVolumeChange = this.volume

 
  def play(path: String, looping: Boolean) = {
    if (on) {
      val file = new File(path)
      val audioIn = AudioSystem.getAudioInputStream(file)
      val clip = AudioSystem.getClip
      clip.open(audioIn)

      var gainControl = (clip.getControl(FloatControl.Type.MASTER_GAIN)).asInstanceOf[FloatControl]

      gainControl.setValue(currentVolumeChange)

      //      gainControl.setValue(0)
      //      volumes += gainControl
      addVolume(gainControl)
      if (looping) {
        clip.loop(9999)
      }
      clip.start()
    }
  }

  
  def addVolume(vol: FloatControl) = {
    volumes += vol
    if (volumes.size >= 10) {
      volumes.drop(1)
    }
  }
  
  def tick(delta: Double) = {
    if (on) {
      volumes.foreach(x => x.setValue(currentVolumeChange))
    } else {
      volumes.foreach(x => x.setValue(-80f))
    }
    //    println(volumes)
    //    println(currentVolumeChange)
  }
}