package ws.ass.rps.model

import ws.ass.rps.MainApp

import scala.util.Random

object PlayModel {

  // Common Choices for all levels
  object Choices {
    val Rock = "Rock"
    val Paper = "Paper"
    val Scissors = "Scissors"
    val Spock = "Spock"
    val Lizard = "Lizard"
  }

  import Choices._

  // Properties to hold the game state
  private var userWins = 0
  private var computerWins = 0

  // Constants for the game
  private val winsToWinGame = 3 // Number of wins needed to win the game
  private val choices = List(Rock, Paper, Scissors)

  def getUserWins: Int = userWins // Public getter method for userWins

  def getComputerWins: Int = computerWins // Public getter method for computerWins

  private var isRoundFinished = false

  def generateComputerChoice(): String = {
    choices(Random.nextInt(choices.length))
  }


  def setResultAndScore(winner: String, isRoundFinished: Boolean): Boolean = {
    if (!isRoundFinished) {
      if (winner == "You Win") {
        userWins += 1
      } else if (winner == "Computer Wins") {
        computerWins += 1
      }

      // Check if either user or computer has won the game
      if (userWins == winsToWinGame || computerWins == winsToWinGame) {
        val winner = if (userWins == winsToWinGame) "You Win" else "Computer Wins"
        MainApp.showResultPage(winner) // Switch to the ResultPage.fxml with the winner information
      }
      true
    }else{
      false
    }
  }

  def determineWinner(userChoice: String, computerChoice: String): String = {
    if (userChoice == computerChoice) {
      "It's a Tie"
    } else if (
      (userChoice == Rock && (computerChoice == Scissors || computerChoice == Lizard)) ||
        (userChoice == Paper && (computerChoice == Rock || computerChoice == Spock)) ||
        (userChoice == Scissors && (computerChoice == Paper || computerChoice == Lizard)) ||
        (userChoice == Spock && (computerChoice == Rock || computerChoice == Scissors)) ||
        (userChoice == Lizard && (computerChoice == Paper || computerChoice == Spock))
    ) {
      "You Win"
    } else {
      "Computer Wins"
    }
  }

  def resetGame(): Unit = {
    userWins = 0
    computerWins = 0
    isRoundFinished = false // Reset the isRoundFinished flag
  }

}
