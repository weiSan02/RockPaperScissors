package ws.ass.rps.view

import scalafx.application.Platform
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Button, ButtonType, Label}
import scalafxml.core.macros.sfxml
import ws.ass.rps.MainApp
import ws.ass.rps.component.DialogManager

@sfxml
class ResultPageController(
                            private val resultText: Label,
                            private val playButton: Button,
                            private val exitButton: Button,
                            private val playController: PlayController
                          ) {

  // Event handler for restartGame button
  def restartGame(): Unit = {
    MainApp.showWelcomePage()
  }

  // Event handler for endGame button
  def endGame(): Unit = {
    DialogManager.showAlert(AlertType.Confirmation, "Exit Application", "Confirm Exit", "Are you sure you want to exit?") match {
      case Some(ButtonType.OK) => Platform.exit()
      case _ => // Do nothing if the user clicks Cancel or closes the alert
    }
  }

  // Method to set the result text based on the winner
  def setResultText(winner: String): Unit = {
    val result =
      if (winner == "You Win") "Congratulations!\n You Win!"
      else "Game Over!\n Computer Wins!"

    resultText.text = result
  }
}
