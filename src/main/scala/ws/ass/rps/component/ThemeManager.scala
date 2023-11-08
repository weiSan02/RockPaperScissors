package ws.ass.rps.component

import javafx.scene.Scene
import javafx.scene.layout.BackgroundImage
import javafx.scene.layout.Background
import javafx.scene.layout.Region
import scalafx.application.Platform
import scalafx.scene.image.Image

object ThemeManager {
  private var currentTheme: String = "MorningTheme.css"

  def switchTheme(scene: Scene, themePath: String, backgroundImagePath: String): Unit = {
    if (currentTheme != themePath) {
      currentTheme = themePath
      Platform.runLater(() => {
        scene.getStylesheets.clear()
        scene.getStylesheets.add(getClass.getResource(themePath).toExternalForm)
        println("Active Theme: " + themePath)

        // Change both the theme stylesheet and background image
        changeBackgroundImage(scene, backgroundImagePath)
      })
    }
  }

  // Method to change only the background image
  private def changeBackgroundImage(scene: Scene, backgroundImagePath: String): Unit = {
    Platform.runLater(() => {
      // Create the new background image
      val backgroundImage = new BackgroundImage(
        new Image(backgroundImagePath),
        null,
        null,
        null,
        null
      )
      val background = new Background(backgroundImage)

      // Set the new background for the root of the scene
      scene.getRoot.asInstanceOf[Region].setBackground(background)
    })
  }
}
