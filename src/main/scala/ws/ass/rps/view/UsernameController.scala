package ws.ass.rps.view

import javafx.event.ActionEvent

trait UsernameController {
  def clearField(event: ActionEvent): Unit
  def submitProcess(event: ActionEvent): Unit
  def validateUsername(): Boolean
  def getUsername(): String
}