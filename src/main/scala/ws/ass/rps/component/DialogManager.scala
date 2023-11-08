package ws.ass.rps.component

import scalafx.scene.control.Alert.AlertType
import scalafx.scene.control.{Alert, ButtonType}

object DialogManager {
  def showAlert(alertType: AlertType, alertTitle: String, alertHeader: String, alertText: String): Option[ButtonType] = {
    val alert = new Alert(alertType) {
      title = alertTitle
      headerText = alertHeader
      contentText = alertText
    }

    val result = alert.showAndWait()
    result
  }
}
