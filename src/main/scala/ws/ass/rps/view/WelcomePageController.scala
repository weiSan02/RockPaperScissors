package ws.ass.rps.view

import scalafx.event.ActionEvent
import scalafx.scene.control.{Button, Slider}
import scalafx.scene.layout.AnchorPane
import scalafxml.core.macros.sfxml
import ws.ass.rps.MainApp
import ws.ass.rps.component.{MusicManager, ThemeManager}

@sfxml
class WelcomePageController(
                             private val beginnerButton: Button,
                             private val intermediateButton: Button,
                             private val morningButton: Button,
                             private val nightButton: Button,
                             private val volumeSlider: Slider,
                             private val anchorPane: AnchorPane
                           ) {

  // Play Music as Page start
  MusicManager.play()

  def onBeginnerButtonClicked(): Unit = {
    MainApp.showBeginnerUsername()
  }

  def onIntermediateButtonClicked(): Unit = {
    MainApp.showInterUsername()
  }

  def switchToMorningTheme(event: ActionEvent): Unit = {
    ThemeManager.switchTheme(anchorPane.getScene, "/ws/ass/rps/css/MorningTheme.css", "/ws/ass/rps/image/morning_image.png")
  }

  def switchToNightTheme(event: ActionEvent): Unit = {
    ThemeManager.switchTheme(anchorPane.getScene, "/ws/ass/rps/css/NightTheme.css", "/ws/ass/rps/image/night_image.png")
  }

  def switchToDefaultTheme(event: ActionEvent): Unit = {
    ThemeManager.switchTheme(anchorPane.getScene, "/ws/ass/rps/css/Default.css", "/ws/ass/rps/image/default_image.jpg")
  }


  // Add a listener to the volumeSlider to control the MediaPlayer's volume
  volumeSlider.valueProperty().addListener((_, _, newValue) => {
    val volume = newValue.doubleValue() / 100.0 // Convert to a value between 0 and 1
    MusicManager.setVolume(volume)
  })
}
