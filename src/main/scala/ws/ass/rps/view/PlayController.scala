package ws.ass.rps.view

import scalafx.animation.{Animation, ParallelTransition, RotateTransition}
import scalafx.application.Platform
import scalafx.scene.control.Label
import scalafx.scene.image.{Image, ImageView}
import scalafx.util.Duration
import ws.ass.rps.model.PlayModel

class PlayController(
                      private val playerMarks: Label,
                      private val computerMarks: Label,
                      private val playerChoice: ImageView,
                      private val computerChoice: ImageView,
                      private val resultText: Label,
                      private val usernameLabel: Label
                    ) {

  // Common Choices for all levels
  object Choices {
    val Rock = "Rock"
    val Paper = "Paper"
    val Scissors = "Scissors"
    val Spock = "Spock"
    val Lizard = "Lizard"
  }

  import Choices._

  private val paperImagePath: String = "ws/ass/rps/image/paper_image.png"
  private val scissorsImagePath: String = "ws/ass/rps/image/scissors_image.png"
  private val spockImagePath: String = "ws/ass/rps/image/spock_image.png"
  private val lizardImagePath: String = "ws/ass/rps/image/lizard_image.png"
  private val rockImagePath: String = "ws/ass/rps/image/rock_image.png"

  private val choiceToImagePathMap: Map[String, String] = Map(
    Rock -> rockImagePath,
    Paper -> paperImagePath,
    Scissors -> scissorsImagePath,
    Spock -> spockImagePath,
    Lizard -> lizardImagePath
  )

  // Constants for animation durations
  // Adjust the duration as needed (in milliseconds)
  private val animationDuration: Duration = Duration(500)
  private var isAnimationPlaying = false

  // Set default image and result text when initializing the view
  playerChoice.setImage(new Image(rockImagePath))
  computerChoice.setImage(new Image(rockImagePath))
  resultText.text = "Choose 1"
  playerMarks.text = "0"
  computerMarks.text = "0"

  // Button event handlers
  def onRockButtonClicked(): Unit = playChoice(Choices.Rock)

  def onPaperButtonClicked(): Unit = playChoice(Choices.Paper)

  def onScissorsButtonClicked(): Unit = playChoice(Choices.Scissors)

  def setUsername(username: String): Unit = {
    usernameLabel.setText(username)
  }

  protected def playChoice(userChoice: String): Unit = {
    if (!isAnimationPlaying) {
      isAnimationPlaying = true
      playRound(userChoice)
    }
  }

  private def playRound(userChoice: String): Unit = {
    var isRoundFinished = false
    // Play the animations for player's and computer's choices
    val userAnimation = createPlayerAnimation(userChoice)
    val computerAnimation = createComputerAnimation()

    // After the animations, show the result and update the score
    val parallelTransition = createParallelTransition(userAnimation, computerAnimation)
    parallelTransition.onFinished = { _ =>
      // Continue with the game logic after the animations end
      val computerChoice = PlayModel.generateComputerChoice()
      changeComputerChoiceImage(computerChoice)

      // Determine the winner
      val winner = PlayModel.determineWinner(userChoice, computerChoice)

      // Display the result and update the score
      Platform.runLater(() => {
        val roundFinished = PlayModel.setResultAndScore(winner, isRoundFinished) // Update the PlayModel's data

        // Update the UI elements with the data from PlayModel
        resultText.text = winner
        playerMarks.text = s" ${PlayModel.getUserWins}"
        computerMarks.text = s" ${PlayModel.getComputerWins}"

        // Reset the flag to allow new animations
        isAnimationPlaying = false
        // Set isRoundFinished to true after the animations are finished
        isRoundFinished = roundFinished
      })
    }

    // Set playerChoice and computerChoice to be visible before starting the animations
    playerChoice.visible = true
    computerChoice.visible = true

    // Start the animations
    parallelTransition.play()
  }

  // Method to create the animation for player's choice
  private def createPlayerAnimation(choice: String): Animation = {
    createRotationAnimation(playerChoice, choice, 45)
  }

  // Method to create the animation for computer's choice
  private def createComputerAnimation(): Animation = {
    createRotationAnimation(computerChoice, Choices.Rock, -45)
  }

  private def createRotationAnimation(imageView: ImageView, choice: String, angle: Double): Animation = {
    val rotateTransition = new RotateTransition(animationDuration, imageView)
    // Rotate down by 45 degrees (negative angle for rotating up to the left)
    rotateTransition.byAngle = angle
    // Perform the animation 3 times down and 3 times up
    rotateTransition.cycleCount = 6
    // Rotate back up to the original position
    rotateTransition.autoReverse = true

    rotateTransition.onFinished = { _ =>
      // Update the image after the animation completes based on the user's choice
      changeUserChoiceImage(imageView, choice)
    }

    rotateTransition
  }

  // Method to change the image of user's choice
  private def changeUserChoiceImage(imageView: ImageView, choice: String): Unit = {
    choiceToImagePathMap.get(choice).foreach(imagePath => {
      imageView.setImage(new Image(imagePath))
    })
  }

  // Method to change the computer's choice image
  private def changeComputerChoiceImage(choice: String): Unit = {
    choiceToImagePathMap.get(choice).foreach(imagePath => {
      computerChoice.setImage(new Image(imagePath))
    })
  }

  // Method to create a parallel transition from multiple animations
  private def createParallelTransition(animations: Animation*): ParallelTransition = {
    val parallelTransition = new ParallelTransition()
    parallelTransition.children = animations.toList
    parallelTransition
  }

  def restartGame(): Unit = {
    PlayModel.resetGame()
    playerMarks.text = "0"
    computerMarks.text = "0"
    playerChoice.setImage(new Image(rockImagePath))
    computerChoice.setImage(new Image(rockImagePath))
    resultText.text = "Choose 1"
  }
}
