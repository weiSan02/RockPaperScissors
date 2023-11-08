package ws.ass.rps.component

import javafx.scene.media.{Media, MediaPlayer}

object MusicManager {
  private val musicPath = getClass.getResource("/ws/ass/rps/bgm/Fluffing-a-Duck.mp3").toString
  private val music = new Media(musicPath)
  private val mediaPlayer = new MediaPlayer(music)

  // Set the initial volume to 50% (0.5 in MediaPlayer)
  mediaPlayer.setVolume(0.5)

  def play(): Unit = {
    mediaPlayer.play()
    mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE) // Set the cycle count to indefinitely
  }

  def setVolume(volume: Double): Unit = {
    if (mediaPlayer != null) {
      mediaPlayer.setVolume(volume)
    }
  }
}
