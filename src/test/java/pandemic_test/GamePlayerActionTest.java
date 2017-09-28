package pandemic_test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.easymock.EasyMock;
import org.junit.Test;

import pandemic_exception.NotEnoughResearchStationsException;
import pandemic_game.City;
import pandemic_game.Constants.CubeColor;
import pandemic_game.Constants.Role;
import pandemic_game.Deck;
import pandemic_game.Game;
import pandemic_game.GameLogic;
import pandemic_game.Player;

public class GamePlayerActionTest {

  @Test
  public void testMovePlayerByDriveOrFerry() {
    Game mockedGame = EasyMock.createMock(Game.class);
    Player mockedPlayer = EasyMock.createMock(Player.class);
    EasyMock.expect(mockedGame.getCurrentPlayer()).andStubReturn(mockedPlayer);
    EasyMock.expect(mockedPlayer.getRole()).andReturn(Role.OperationExpert);
    City mockedCity = EasyMock.createMock(City.class);
    mockedPlayer.driveOrFerry(mockedCity);
    EasyMock.expectLastCall();
    mockedGame.decrementPlayerActionsLeft();
    EasyMock.expectLastCall();

    EasyMock.replay(mockedGame, mockedPlayer, mockedCity);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.movePlayerByDriveOrFerry(mockedCity);
    EasyMock.verify(mockedGame, mockedPlayer, mockedCity);
  }

  @Test
  public void testMovePlayerByDirectFlight() {
    Game mockedGame = EasyMock.createMock(Game.class);
    Player mockedPlayer = EasyMock.createMock(Player.class);
    EasyMock.expect(mockedGame.getCurrentPlayer()).andStubReturn(mockedPlayer);
    EasyMock.expect(mockedPlayer.getRole()).andReturn(Role.OperationExpert);
    City mockedCity = EasyMock.createMock(City.class);
    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);
    EasyMock.expect(mockedGame.getPlayerDiscardDeck()).andReturn(mockedPlayerDiscardDeck);
    mockedPlayer.directFlight(mockedCity, mockedPlayerDiscardDeck);
    EasyMock.expectLastCall();
    mockedGame.decrementPlayerActionsLeft();
    EasyMock.expectLastCall();

    EasyMock.replay(mockedGame, mockedPlayer, mockedCity, mockedPlayerDiscardDeck);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.movePlayerByDirectFlight(mockedCity);
    EasyMock.verify(mockedGame, mockedPlayer, mockedCity, mockedPlayerDiscardDeck);
  }

  @Test
  public void testMovePlayerByCharterFlight() {
    Game mockedGame = EasyMock.createMock(Game.class);
    Player mockedPlayer = EasyMock.createMock(Player.class);
    EasyMock.expect(mockedGame.getCurrentPlayer()).andStubReturn(mockedPlayer);
    EasyMock.expect(mockedPlayer.getRole()).andReturn(Role.OperationExpert);
    City mockedCity = EasyMock.createMock(City.class);
    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);
    EasyMock.expect(mockedGame.getPlayerDiscardDeck()).andReturn(mockedPlayerDiscardDeck);
    mockedPlayer.charterFlight(mockedCity, mockedPlayerDiscardDeck);
    EasyMock.expectLastCall();
    mockedGame.decrementPlayerActionsLeft();
    EasyMock.expectLastCall();

    EasyMock.replay(mockedGame, mockedPlayer, mockedCity, mockedPlayerDiscardDeck);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.movePlayerByCharterFlight(mockedCity);
    EasyMock.verify(mockedGame, mockedPlayer, mockedCity, mockedPlayerDiscardDeck);
  }

  @Test
  public void testMovePlayerByShuttleFlight() {
    Game mockedGame = EasyMock.createMock(Game.class);
    Player mockedPlayer = EasyMock.createMock(Player.class);
    EasyMock.expect(mockedGame.getCurrentPlayer()).andStubReturn(mockedPlayer);
    EasyMock.expect(mockedPlayer.getRole()).andReturn(Role.OperationExpert);
    City mockedCity = EasyMock.createMock(City.class);
    mockedPlayer.shuttleFlight(mockedCity);
    EasyMock.expectLastCall();
    mockedGame.decrementPlayerActionsLeft();
    EasyMock.expectLastCall();

    EasyMock.replay(mockedGame, mockedPlayer, mockedCity);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.movePlayerByShuttleFlight(mockedCity);
    EasyMock.verify(mockedGame, mockedPlayer, mockedCity);
  }

  @Test
  public void testBuildResearchStationWithEnoughStations() {
    Game mockedGame = EasyMock.createMock(Game.class);
    EasyMock.expect(mockedGame.getTotalResearchStationLeft()).andReturn(1);
    mockedGame.decrementPlayerActionsLeft();
    EasyMock.expectLastCall();
    mockedGame.decrementResearchStationLeft();
    EasyMock.expectLastCall();
    Player mockedPlayer = EasyMock.createMock(Player.class);
    EasyMock.expect(mockedGame.getCurrentPlayer()).andReturn(mockedPlayer);
    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);
    EasyMock.expect(mockedGame.getPlayerDiscardDeck()).andReturn(mockedPlayerDiscardDeck);
    mockedPlayer.buildResearchStation(mockedPlayerDiscardDeck);
    EasyMock.expectLastCall();

    EasyMock.replay(mockedGame, mockedPlayer, mockedPlayerDiscardDeck);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.buildResearchStation();

    EasyMock.verify(mockedGame, mockedPlayer, mockedPlayerDiscardDeck);
  }

  @Test(expected = NotEnoughResearchStationsException.class)
  public void testBuildResearchStationWithNoStationLeft() {
    Game mockedGame = EasyMock.createMock(Game.class);
    EasyMock.expect(mockedGame.getTotalResearchStationLeft()).andReturn(0);

    EasyMock.replay(mockedGame);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.buildResearchStation();

    EasyMock.verify(mockedGame);
  }

  @Test
  public void testRemoveOneDiseaseCube() {
    Game mockedGame = EasyMock.createMock(Game.class);
    mockedGame.decrementPlayerActionsLeft();
    EasyMock.expectLastCall();
    Player mockedPlayer = EasyMock.createMock(Player.class);
    EasyMock.expect(mockedGame.getCurrentPlayer()).andReturn(mockedPlayer);
    mockedPlayer.treatCurrentCity(CubeColor.BLUE);
    EasyMock.expectLastCall();

    EasyMock.replay(mockedGame, mockedPlayer);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.removeOneDiseaseCube(CubeColor.BLUE);

    EasyMock.verify(mockedGame, mockedPlayer);
  }

  @Test
  public void testPassCard() {
    Player mockedPlayer1 = EasyMock.createMock(Player.class);
    Player mockedPlayer2 = EasyMock.createMock(Player.class);
    mockedPlayer1.passCard(mockedPlayer2);
    EasyMock.expectLastCall();
    Game mockedGame = EasyMock.createMock(Game.class);
    EasyMock.expect(mockedGame.getCurrentPlayer()).andReturn(mockedPlayer1);
    EasyMock.expect(mockedGame.getPlayerAtIndex(1)).andReturn(mockedPlayer2);

    EasyMock.replay(mockedPlayer1, mockedPlayer2, mockedGame);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.passCard(1);

    EasyMock.verify(mockedPlayer1, mockedPlayer2, mockedGame);
  }
}
