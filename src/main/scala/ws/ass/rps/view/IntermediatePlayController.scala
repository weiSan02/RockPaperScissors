package ws.ass.rps.view

import scalafx.scene.control.Label
import scalafx.scene.image.ImageView
import scalafxml.core.macros.sfxml

@sfxml
class IntermediatePlayController(
                                  private val playerChoice: ImageView,
                                  private val computerChoice: ImageView,
                                  private val interUsername: Label,
                                  private val resultText: Label,
                                  private val playerMarks: Label,
                                  private val computerMarks: Label
                                ) extends PlayController(
  playerMarks,
  computerMarks,
  playerChoice,
  computerChoice,
  resultText,
  interUsername
) {

  override def onRockButtonClicked(): Unit = playChoice(Choices.Rock)

  override def onPaperButtonClicked(): Unit = playChoice(Choices.Paper)

  override def onScissorsButtonClicked(): Unit = playChoice(Choices.Scissors)

  def onSpockButtonClicked(): Unit = playChoice(Choices.Spock)

  def onLizardButtonClicked(): Unit = playChoice(Choices.Lizard)

  override def setUsername(username: String): Unit = {
    interUsername.text = username
  }
}
