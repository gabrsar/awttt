package model

import model.helpers.Fabricator
import org.specs2.mutable._


class GameEngineSpec extends Specification {

  "GameEngine" should {

    sequential // These steps simulate a gameplay

    testStartState()
    testExecuteMovement()
    testVictoryConditions()
    
  }

  def testStartState() = {

    val game = Fabricator.game()

    "start with all positions clear" in {
      var totalEmpty = true
      game.board.foreach(x => x.foreach(xy => {
        if (xy != GameEngine.empty) totalEmpty = false
      }))
      totalEmpty must beTrue
    }

    "start a game with Player1" in {
      game.currentPlayer must beEqualTo(GameEngine.player1)
    }
  }
  def testExecuteMovement() = {



    val game = Fabricator.game()

    "refuse to play with invalid coordinates" in {
      game.executeMovement(-1, -1) must beFalse
      game.executeMovement(-1, 3) must beFalse
      game.executeMovement(3, -1) must beFalse
      game.executeMovement(3, 3) must beFalse
    }

    "don't swap player if is an invalid move" in {
      game.executeMovement(-1, -1) must beFalse
      game.currentPlayer must beEqualTo(GameEngine.player1)
    }

    "pass to next player if is a valid move" in {
      game.executeMovement(0, 0) must beTrue
      game.currentPlayer must beEqualTo(GameEngine.player2)
      game.executeMovement(0, 1) must beTrue
      game.currentPlayer must beEqualTo(GameEngine.player1)
    }

    "do not accept a overlap move" in {
      game.executeMovement(0, 0) must beFalse
    }


  }
  def testVictoryConditions() = {

    def testBeforeWinner() = {
      // X X _ <--- Neither X
      // O O _      and O can
      // _ _ _      Win yet
      val game = Fabricator.game()
      game.executeMovement(0, 0)
      game.executeMovement(1, 0)
      game.executeMovement(0, 1)
      game.executeMovement(1, 1)

      "have no winners in less than 5 plays" in {
        game.checkForVictory must beEqualTo(GameEngine.continue)
      }
    }

    def testWithoutWinner() = {
      // X O X <--- No Winner
      // X O X
      // O X O
      val game = Fabricator.game()
      game.executeMovement(0, 0)
      game.executeMovement(0, 1)
      game.executeMovement(0, 2)
      game.executeMovement(1, 0)
      game.executeMovement(1, 1)
      game.executeMovement(1, 2)
      game.executeMovement(2, 1)
      game.executeMovement(2, 0)
      game.executeMovement(2, 2)

      "end in a draw" in {
        game.checkForVictory must beEqualTo(GameEngine.draw)
      }
    }

    def testPlayer1Winner() = {
      // X X X <--- X Win
      // O O _
      // _ _ _
      val game = Fabricator.game()
      game.executeMovement(0, 0)
      game.executeMovement(1, 0)
      game.executeMovement(0, 1)
      game.executeMovement(1, 1)
      game.executeMovement(0, 2)

      "end with Player1 wining" in {
        game.checkForVictory must beEqualTo(GameEngine.player1)
      }
    }

    def testPlayer2Winner() = {
      // O O O <--- O Win
      // X X _
      // X _ _
      val game = Fabricator.game()
      game.executeMovement(1, 0)
      game.executeMovement(0, 0)
      game.executeMovement(1, 1)
      game.executeMovement(0, 1)
      game.executeMovement(2, 1)
      game.executeMovement(0, 2)

      "end with Player2 wining" in {
        game.checkForVictory must beEqualTo(GameEngine.player2)
      }
    }

    testBeforeWinner()
    testWithoutWinner()
    testPlayer1Winner()
    testPlayer2Winner()
  }


}
