package ws.ass.rps.view

import scalafx.scene.control.Label
import scalafx.scene.image.ImageView
import scalafxml.core.macros.sfxml

@sfxml
class BeginnerPlayController(
                              private val playerMarks: Label,
                              private val computerMarks: Label,
                              private val playerChoice: ImageView,
                              private val computerChoice: ImageView,
                              private val resultText: Label,
                              private val usernameLabel: Label
                            ) extends PlayController(
  playerMarks,
  computerMarks,
  playerChoice,
  computerChoice,
  resultText,
  usernameLabel
) {
  override def onRockButtonClicked(): Unit = playChoice(Choices.Rock)

  override def onPaperButtonClicked(): Unit = playChoice(Choices.Paper)

  override def onScissorsButtonClicked(): Unit = playChoice(Choices.Scissors)

  override def setUsername(username: String): Unit = {
    usernameLabel.text = username
  }
}
