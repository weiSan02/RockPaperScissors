package ws.ass.rps

import javafx.{scene => jfxs}
import scalafx.Includes._
import scalafx.application.{JFXApp, Platform}
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.Scene
import scalafx.scene.control.{Alert, ButtonType, Label}
import scalafx.scene.control.Alert.AlertType
import scalafx.scene.image.ImageView
import scalafxml.core.{FXMLLoader, NoDependencyResolver}
import ws.ass.rps.view._
import scalafx.stage.WindowEvent

import scala.util.{Failure, Success, Try}
import ws.ass.rps.component.DialogManager

object MainApp extends JFXApp {

  // Load the RootLayout.fxml as the main layout
  private val rootResource = getClass.getResource("view/RootLayout.fxml")
  private val loader = new FXMLLoader(rootResource, NoDependencyResolver)
  loader.load()
  private val rootLayout = loader.getRoot[jfxs.layout.BorderPane]


  stage = new PrimaryStage {
    title = "Rock Paper Scissors"
    resizable = false
    scene = new Scene {
      root = rootLayout
    }

    // Event handler for window's close request (X button)
    onCloseRequest = (e: WindowEvent) => {
      e.consume() // Consume the event to prevent immediate window close
      DialogManager.showAlert(AlertType.Confirmation, "Exit Application", "Confirm Exit", "Are you sure ?") match {
        case Some(ButtonType.OK) => Platform.exit()
        case _ => // Do nothing if the user clicks Cancel or closes the alert
      }
    }
  }

  val playController = new PlayController(
    playerMarks = new Label(),
    computerMarks = new Label(),
    playerChoice = new ImageView(), computerChoice = new ImageView(),
    resultText = new Label(),
    usernameLabel = new Label()
  )

  def showAboutPage(): Unit = {
    val resource = getClass.getResource("view/AboutPage.fxml")
    loadFXML(resource) { loader =>
      loader.load()
      val aboutPage = loader.getRoot[jfxs.layout.AnchorPane]

      rootLayout.setCenter(aboutPage)
    }
  }

  def showBeginnerRule(): Unit = {
    val resource = getClass.getResource("view/BeginnerRule.fxml")
    loadFXML(resource) { loader =>
      loader.load()
      val beginnerRule = loader.getRoot[jfxs.layout.AnchorPane]

      rootLayout.setCenter(beginnerRule)
    }
  }

  def showIntermediateRule(): Unit = {
    val resource = getClass.getResource("view/IntermediateRule.fxml")
    loadFXML(resource) { loader =>
      loader.load()
      val intermediateRule = loader.getRoot[jfxs.layout.AnchorPane]
      rootLayout.setCenter(intermediateRule)
    }
  }

  // Method to switch to the ResultPage.fxml and set the ResultPageController reference
  private var resultPageController: Option[ResultPageController#Controller] = None

  def showResultPage(winner: String): Unit = {
    val resource = getClass.getResource("view/ResultPage.fxml")
    loadFXML(resource) { loader =>
      loader.load()
      val roots = loader.getRoot[jfxs.layout.AnchorPane]

      // Set the reference to the ResultPageController
      resultPageController = Option(loader.getController[ResultPageController#Controller]())

      // Set the result text on the ResultPageController
      resultPageController.foreach(controller => controller.setResultText(winner))

      rootLayout.setCenter(roots)
    }
  }

  // actions for display intermediate level window
  private var intermediateLvlController: Option[IntermediatePlayController#Controller] = None

  def showIntermediateLvl(interUsername: String): Unit = {
    val resource = getClass.getResource("view/IntermediatePlay.fxml")
    loadFXML(resource) { loader =>
      loader.load()
      val roots = loader.getRoot[jfxs.layout.AnchorPane]()

      // Set the reference to the IntermediateLvlController
      intermediateLvlController = Option(loader.getController[IntermediatePlayController#Controller]())

      // Set the username in the IntermediateLvlController
      intermediateLvlController.foreach(controller => controller.setUsername(interUsername))

      rootLayout.setCenter(roots)
    }
  }

  private var beginnerLvlController: Option[BeginnerPlayController#Controller] = None

  def showBeginnerPlay(beginnerUsername: String): Unit = {
    val resource = getClass.getResource("view/BeginnerPlay.fxml")
    loadFXML(resource) { loader =>
      loader.load()
      val roots = loader.getRoot[jfxs.layout.AnchorPane]

      // Set the reference to the BeginnerLvlController
      beginnerLvlController = Option(loader.getController[BeginnerPlayController#Controller]())

      // Set the username in the BeginnerLvlController
      beginnerLvlController.foreach(controller => controller.setUsername(beginnerUsername))

      rootLayout.setCenter(roots)

    }
  }

  private var interUsernameController: Option[InterUsernameController#Controller] = None

  def showInterUsername(): Unit = {
    val resource = getClass.getResource("view/IntermediateUsername.fxml")
    loadFXML(resource) { loader =>
      loader.load()
      val roots = loader.getRoot[jfxs.layout.AnchorPane]

      // Set the reference to the ResultPageController
      interUsernameController = Option(loader.getController[InterUsernameController#Controller]())

      rootLayout.setCenter(roots)
    }
  }

  private var beginnerUsernameController: Option[BeginnerUsernameController#Controller] = None

  def showBeginnerUsername(): Unit = {
    val resource = getClass.getResource("view/BeginnerUsername.fxml")
    loadFXML(resource) { loader =>
      loader.load()
      val roots = loader.getRoot[jfxs.layout.AnchorPane]

      // Set the reference to the ResultPageController
      beginnerUsernameController = Option(loader.getController[BeginnerUsernameController#Controller]())

      rootLayout.setCenter(roots)
    }
  }

  // Method to switch to the WelcomePage.fxml
  def showWelcomePage(): Unit = {
    val resource = getClass.getResource("view/WelcomePage.fxml")
    loadFXML(resource) { loader =>
      loader.load()
      val welcomePage = loader.getRoot[jfxs.layout.AnchorPane]

      // Set the WelcomePage.fxml as the center of the RootLayout
      rootLayout.setCenter(welcomePage)

      // Restart the game whenever the welcome page is shown
      playController.restartGame()

    }
  }

  showWelcomePage()

  // Helper method for loading FXML with error handling
  private def loadFXML[T](resource: java.net.URL)(f: FXMLLoader => T): Unit = {
    Try {
      val loader = new FXMLLoader(resource, NoDependencyResolver)
      f(loader)
    } match {
      case Success(_) =>
      // Loading succeeded
      case Failure(exception) =>
        // Loading failed, show an alert with the error message
        val alert = new Alert(AlertType.Error) {
          title = "Error"
          headerText = "Failed to load FXML"
          contentText = s"An error occurred while loading the FXML: ${exception.getMessage}"
        }
        alert.showAndWait()
    }
  }
}
