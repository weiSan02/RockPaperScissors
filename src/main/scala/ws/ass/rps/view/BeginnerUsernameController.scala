package ws.ass.rps.view

import javafx.event.ActionEvent
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.Button
import scalafx.scene.control.TextField
import scalafxml.core.macros.sfxml
import ws.ass.rps.MainApp
import ws.ass.rps.component.DialogManager

@sfxml
class BeginnerUsernameController(
                                  private val submitButton: Button,
                                  private val usernameField: TextField
                                ) extends UsernameController {

  // Event handler for the "Clear" button
  override def clearField(event: ActionEvent): Unit = {
    usernameField.setText("")
  }

  // Event handler for the "Submit" button
  override def submitProcess(event: ActionEvent): Unit = {
    if (validateUsername()) {
      val username = getUsername()
      MainApp.showBeginnerPlay(username)
    } else {
      // Show a warning dialog for invalid username format
      DialogManager.showAlert(AlertType.Warning, "Invalid Input", "Invalid Username",
        "Username must have at least 1 uppercase letter, 1 lowercase letter, 1 number, and 1 special character (!@#$).")
    }
  }

  // Method to validate the username format using regular expressions
  override def validateUsername(): Boolean = {
    val username = usernameField.getText()

    // Regular expressions for required patterns
    val uppercasePattern = "(?=.*[A-Z])"
    val lowercasePattern = "(?=.*[a-z])"
    val numberPattern = "(?=.*[0-9])"
    val specialCharPattern = "(?=.*[!@#\\$])"

    // Check if the username matches all required patterns
    val isValid = username.matches(s".*$uppercasePattern.*$lowercasePattern.*$numberPattern.*$specialCharPattern.*")
    isValid
  }

  // Method to get the username from the text field
  override def getUsername(): String = {
    usernameField.getText()
  }
}
