package ws.ass.rps.view

import javafx.event.ActionEvent
import scalafx.application.Platform
import scalafx.scene.control.{Menu, MenuItem}
import scalafxml.core.macros.sfxml
import ws.ass.rps.MainApp

@sfxml
class RootLayoutController(
                            private val homeButton: MenuItem,
                            private val aboutButton: MenuItem,
                            private val beginnerRuleButton: MenuItem,
                            private val interRuleButton: MenuItem
                          ) {
  // Event handler for the "Home" button
  def homePage(event: ActionEvent): Unit = {
    MainApp.showWelcomePage()
  }

  // Event handler for the "About" button
  def aboutPage(event: ActionEvent): Unit = {
    MainApp.showAboutPage()
  }

  // Event handler for the "Beginner Rule" button
  def beginnerRule(event: ActionEvent): Unit = {
    MainApp.showBeginnerRule()
  }

  // Event handler for the "Intermediate Rule" button
  def interRule(event: ActionEvent): Unit = {
    MainApp.showIntermediateRule()
  }
}
