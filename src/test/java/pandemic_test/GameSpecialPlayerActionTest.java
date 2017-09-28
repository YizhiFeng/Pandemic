package pandemic_test;

import java.util.HashMap;
import java.util.Map;

import org.easymock.EasyMock;
import org.junit.Test;

import pandemic_exception.InvalidActionException;
import pandemic_game.Card;
import pandemic_game.City;
import pandemic_game.Constants.CubeColor;
import pandemic_game.Constants.Role;
import pandemic_game.Deck;
import pandemic_game.Game;
import pandemic_game.GameLogic;
import pandemic_game.Player;

public class GameSpecialPlayerActionTest {

  @Test
  public void testMovePlayerToCityWithPlayers() {
    Player mockedPlayer1 = EasyMock.createMock(Player.class);
    City mockedCity = EasyMock.createMock(City.class);
    City mockedDest = EasyMock.createMock(City.class);
    EasyMock.expect(mockedPlayer1.getCurrentCity()).andReturn(mockedCity);
    mockedPlayer1.setCurrentCity(mockedDest);
    EasyMock.expectLastCall();
    EasyMock.expect(mockedPlayer1.getRole()).andReturn(Role.Scientist);
    Game mockedGame = EasyMock.createMock(Game.class);
    EasyMock.expect(mockedGame.getPlayerAtIndex(1)).andStubReturn(mockedPlayer1);
    EasyMock.expect(mockedGame.hasPlayers(mockedDest)).andReturn(true);
    mockedGame.decrementPlayerActionsLeft();
    EasyMock.expectLastCall();
    EasyMock.replay(mockedPlayer1, mockedCity, mockedDest, mockedGame);

    GameLogic logic = new GameLogic(mockedGame);
    logic.movePlayerByDispatcher(1, mockedDest);

    EasyMock.verify(mockedPlayer1, mockedCity, mockedDest, mockedGame);
  }

  @Test(expected = InvalidActionException.class)
  public void testMovePlayerToCityWithoutPlayers() {
    Player mockedPlayer = EasyMock.createMock(Player.class);
    City mockedCity = EasyMock.createMock(City.class);
    City mockedDest = EasyMock.createMock(City.class);
    EasyMock.expect(mockedPlayer.getCurrentCity()).andStubReturn(mockedDest);

    Game mockedGame = EasyMock.createMock(Game.class);
    EasyMock.expect(mockedGame.getPlayerAtIndex(1)).andStubReturn(mockedPlayer);
    EasyMock.expect(mockedGame.hasPlayers(mockedCity)).andReturn(false);
    EasyMock.replay(mockedPlayer, mockedCity, mockedGame, mockedDest);

    GameLogic logic = new GameLogic(mockedGame);
    logic.movePlayerByDispatcher(1, mockedCity);

    EasyMock.verify(mockedPlayer, mockedCity, mockedGame, mockedDest);
  }

  @Test(expected = InvalidActionException.class)
  public void testMovePlayerToCurrentCity() {
    Player mockedPlayer = EasyMock.createMock(Player.class);
    City mockedCity = EasyMock.createMock(City.class);
    EasyMock.expect(mockedPlayer.getCurrentCity()).andStubReturn(mockedCity);

    Game mockedGame = EasyMock.createMock(Game.class);
    EasyMock.expect(mockedGame.getPlayerAtIndex(1)).andStubReturn(mockedPlayer);
    EasyMock.replay(mockedPlayer, mockedCity, mockedGame);

    GameLogic logic = new GameLogic(mockedGame);
    logic.movePlayerByDispatcher(1, mockedCity);

    EasyMock.verify(mockedPlayer, mockedCity, mockedGame);
  }

  @Test
  public void testTreatCurrentCityByMedicDiseaseUncured() {
    Player mockedPlayer = EasyMock.createMock(Player.class);
    mockedPlayer.removeAllCubes(CubeColor.BLUE);
    EasyMock.expectLastCall();
    Game mockedGame = EasyMock.createMock(Game.class);
    EasyMock.expect(mockedGame.isDiseaseCured(CubeColor.BLUE)).andReturn(false);
    mockedGame.decrementPlayerActionsLeft();
    EasyMock.expectLastCall();
    City mockedCity = EasyMock.createMock(City.class);
    EasyMock.expect(mockedPlayer.getCurrentCity()).andReturn(mockedCity);
    EasyMock.expect(mockedGame.getCubesLeftByColor(CubeColor.BLUE)).andReturn(1);
    EasyMock.expect(mockedCity.getCubeCount(CubeColor.BLUE)).andReturn(1);
    mockedGame.setDiseaseCubesLeft(CubeColor.BLUE, 2);
    EasyMock.replay(mockedPlayer, mockedGame, mockedCity);

    GameLogic logic = new GameLogic(mockedGame);
    logic.treatCityByMedic(mockedPlayer, CubeColor.BLUE);

    EasyMock.verify(mockedPlayer, mockedGame, mockedCity);
  }

  @Test
  public void testTreatCurrentCityByMedicDiseaseCured() {
    Player mockedPlayer = EasyMock.createMock(Player.class);
    mockedPlayer.removeAllCubes(CubeColor.BLACK);
    EasyMock.expectLastCall();
    Game mockedGame = EasyMock.createMock(Game.class);
    EasyMock.expect(mockedGame.isDiseaseCured(CubeColor.BLACK)).andReturn(true);
    City mockedCity = EasyMock.createMock(City.class);
    EasyMock.expect(mockedPlayer.getCurrentCity()).andReturn(mockedCity);
    EasyMock.expect(mockedCity.getCubeCount(CubeColor.BLACK)).andReturn(2);
    EasyMock.expect(mockedGame.getCubesLeftByColor(CubeColor.BLACK)).andReturn(2);
    mockedGame.setDiseaseCubesLeft(CubeColor.BLACK, 4);
    EasyMock.replay(mockedPlayer, mockedGame, mockedCity);

    GameLogic logic = new GameLogic(mockedGame);
    logic.treatCityByMedic(mockedPlayer, CubeColor.BLACK);

    EasyMock.verify(mockedPlayer, mockedGame, mockedCity);
  }

  @Test
  public void testCuredCubeAutoRemoveByDriving() {
    Player mockedPlayer = EasyMock.createMock(Player.class);
    EasyMock.expect(mockedPlayer.getRole()).andReturn(Role.Medic);
    City mockedCity = EasyMock.createMock(City.class);
    EasyMock.expect(mockedPlayer.getCurrentCity()).andStubReturn(mockedCity);
    mockedPlayer.driveOrFerry(mockedCity);
    EasyMock.expectLastCall();
    EasyMock.expect(mockedCity.getCubeCount(CubeColor.RED)).andReturn(0);
    EasyMock.expect(mockedCity.getCubeCount(CubeColor.YELLOW)).andReturn(0);
    EasyMock.expect(mockedCity.getCubeCount(CubeColor.BLUE)).andReturn(0);

    Game mockedGame = EasyMock.createMock(Game.class);
    EasyMock.expect(mockedGame.getCurrentPlayer()).andStubReturn(mockedPlayer);
    mockedGame.decrementPlayerActionsLeft();
    EasyMock.expectLastCall();
    Map<CubeColor, Boolean> curedDisease = new HashMap<>();
    curedDisease.put(CubeColor.BLACK, true);
    curedDisease.put(CubeColor.BLUE, true);
    curedDisease.put(CubeColor.RED, true);
    curedDisease.put(CubeColor.YELLOW, true);
    EasyMock.expect(mockedGame.getCuredDiseases()).andStubReturn(curedDisease);
    EasyMock.expect(mockedGame.isDiseaseCured(CubeColor.BLACK)).andStubReturn(false);
    EasyMock.expect(mockedGame.isDiseaseCured(CubeColor.BLUE)).andStubReturn(true);
    EasyMock.expect(mockedGame.isDiseaseCured(CubeColor.RED)).andStubReturn(true);
    EasyMock.expect(mockedGame.isDiseaseCured(CubeColor.YELLOW)).andStubReturn(true);
    EasyMock.replay(mockedPlayer, mockedCity, mockedGame);

    GameLogic logic = new GameLogic(mockedGame);
    logic.movePlayerByDriveOrFerry(mockedCity);

    EasyMock.verify(mockedPlayer, mockedCity, mockedGame);
  }

  @Test
  public void testCuredCubeAutoRemoveByDirectFlight() {
    Player mockedPlayer = EasyMock.createMock(Player.class);
    EasyMock.expect(mockedPlayer.getRole()).andReturn(Role.Medic);
    mockedPlayer.removeAllCubes(CubeColor.RED);
    EasyMock.expectLastCall();
    mockedPlayer.removeAllCubes(CubeColor.YELLOW);
    EasyMock.expectLastCall();
    City mockedCity = EasyMock.createMock(City.class);
    EasyMock.expect(mockedPlayer.getCurrentCity()).andStubReturn(mockedCity);
    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);
    mockedPlayer.directFlight(mockedCity, mockedPlayerDiscardDeck);
    EasyMock.expectLastCall();
    EasyMock.expect(mockedCity.getCubeCount(CubeColor.RED)).andReturn(2);
    EasyMock.expect(mockedCity.getCubeCount(CubeColor.YELLOW)).andReturn(3);
    EasyMock.expect(mockedCity.getCubeCount(CubeColor.RED)).andReturn(2);
    EasyMock.expect(mockedCity.getCubeCount(CubeColor.YELLOW)).andReturn(3);

    Game mockedGame = EasyMock.createMock(Game.class);
    EasyMock.expect(mockedGame.getPlayerDiscardDeck()).andReturn(mockedPlayerDiscardDeck);
    EasyMock.expect(mockedGame.getCurrentPlayer()).andStubReturn(mockedPlayer);
    mockedGame.decrementPlayerActionsLeft();
    EasyMock.expectLastCall();
    Map<CubeColor, Boolean> curedDisease = new HashMap<>();
    curedDisease.put(CubeColor.RED, true);
    curedDisease.put(CubeColor.YELLOW, true);
    EasyMock.expect(mockedGame.getCuredDiseases()).andStubReturn(curedDisease);
    EasyMock.expect(mockedGame.isDiseaseCured(CubeColor.RED)).andStubReturn(true);
    EasyMock.expect(mockedGame.isDiseaseCured(CubeColor.YELLOW)).andStubReturn(true);
    EasyMock.expect(mockedGame.getCubesLeftByColor(CubeColor.RED)).andReturn(1);
    EasyMock.expect(mockedGame.getCubesLeftByColor(CubeColor.YELLOW)).andReturn(1);
    mockedGame.setDiseaseCubesLeft(CubeColor.RED, 3);
    mockedGame.setDiseaseCubesLeft(CubeColor.YELLOW, 4);
    EasyMock.replay(mockedPlayer, mockedCity, mockedGame, mockedPlayerDiscardDeck);

    GameLogic logic = new GameLogic(mockedGame);
    logic.movePlayerByDirectFlight(mockedCity);

    EasyMock.verify(mockedPlayer, mockedCity, mockedGame, mockedPlayerDiscardDeck);
  }

  @Test
  public void testCuredCubeAutoRemoveByCharterFlight() {
    Player mockedPlayer = EasyMock.createMock(Player.class);
    EasyMock.expect(mockedPlayer.getRole()).andReturn(Role.Medic);
    mockedPlayer.removeAllCubes(CubeColor.BLACK);
    EasyMock.expectLastCall();
    mockedPlayer.removeAllCubes(CubeColor.BLUE);
    EasyMock.expectLastCall();
    City mockedCity = EasyMock.createMock(City.class);
    EasyMock.expect(mockedPlayer.getCurrentCity()).andStubReturn(mockedCity);
    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);
    mockedPlayer.charterFlight(mockedCity, mockedPlayerDiscardDeck);
    EasyMock.expectLastCall();
    EasyMock.expect(mockedCity.getCubeCount(CubeColor.BLACK)).andReturn(2);
    EasyMock.expect(mockedCity.getCubeCount(CubeColor.BLACK)).andReturn(2);
    EasyMock.expect(mockedCity.getCubeCount(CubeColor.BLUE)).andReturn(3);
    EasyMock.expect(mockedCity.getCubeCount(CubeColor.BLUE)).andReturn(3);

    Game mockedGame = EasyMock.createMock(Game.class);
    EasyMock.expect(mockedGame.getPlayerDiscardDeck()).andReturn(mockedPlayerDiscardDeck);
    EasyMock.expect(mockedGame.getCurrentPlayer()).andStubReturn(mockedPlayer);
    mockedGame.decrementPlayerActionsLeft();
    EasyMock.expectLastCall();
    Map<CubeColor, Boolean> curedDisease = new HashMap<>();
    curedDisease.put(CubeColor.BLACK, true);
    curedDisease.put(CubeColor.BLUE, true);
    EasyMock.expect(mockedGame.getCuredDiseases()).andStubReturn(curedDisease);
    EasyMock.expect(mockedGame.isDiseaseCured(CubeColor.BLACK)).andStubReturn(true);
    EasyMock.expect(mockedGame.isDiseaseCured(CubeColor.BLUE)).andStubReturn(true);
    EasyMock.expect(mockedGame.getCubesLeftByColor(CubeColor.BLACK)).andReturn(1);
    mockedGame.setDiseaseCubesLeft(CubeColor.BLACK, 3);
    EasyMock.expectLastCall();
    EasyMock.expect(mockedGame.getCubesLeftByColor(CubeColor.BLUE)).andReturn(1);
    mockedGame.setDiseaseCubesLeft(CubeColor.BLUE, 4);
    EasyMock.expectLastCall();

    EasyMock.replay(mockedPlayer, mockedCity, mockedGame, mockedPlayerDiscardDeck);

    GameLogic logic = new GameLogic(mockedGame);
    logic.movePlayerByCharterFlight(mockedCity);

    EasyMock.verify(mockedPlayer, mockedCity, mockedPlayerDiscardDeck);
  }

  @Test
  public void testCuredCubeAutoRemoveByShuttleFlight() {
    Player mockedPlayer = EasyMock.createMock(Player.class);
    EasyMock.expect(mockedPlayer.getRole()).andReturn(Role.Medic);
    mockedPlayer.removeAllCubes(CubeColor.BLACK);
    EasyMock.expectLastCall();
    mockedPlayer.removeAllCubes(CubeColor.BLUE);
    EasyMock.expectLastCall();
    City mockedCity = EasyMock.createMock(City.class);
    EasyMock.expect(mockedPlayer.getCurrentCity()).andStubReturn(mockedCity);
    mockedPlayer.shuttleFlight(mockedCity);
    EasyMock.expectLastCall();
    EasyMock.expect(mockedCity.getCubeCount(CubeColor.BLACK)).andReturn(2);
    EasyMock.expect(mockedCity.getCubeCount(CubeColor.BLUE)).andReturn(3);
    EasyMock.expect(mockedCity.getCubeCount(CubeColor.BLACK)).andReturn(2);
    EasyMock.expect(mockedCity.getCubeCount(CubeColor.BLUE)).andReturn(3);
    

    Game mockedGame = EasyMock.createMock(Game.class);
    EasyMock.expect(mockedGame.getCurrentPlayer()).andStubReturn(mockedPlayer);
    mockedGame.decrementPlayerActionsLeft();
    EasyMock.expectLastCall();
    Map<CubeColor, Boolean> curedDisease = new HashMap<>();
    curedDisease.put(CubeColor.BLACK, true);
    curedDisease.put(CubeColor.BLUE, true);
    EasyMock.expect(mockedGame.getCuredDiseases()).andStubReturn(curedDisease);
    EasyMock.expect(mockedGame.isDiseaseCured(CubeColor.BLACK)).andStubReturn(true);
    EasyMock.expect(mockedGame.isDiseaseCured(CubeColor.BLUE)).andStubReturn(true);
    EasyMock.expect(mockedGame.getCubesLeftByColor(CubeColor.BLACK)).andReturn(1);
    mockedGame.setDiseaseCubesLeft(CubeColor.BLACK, 3);
    EasyMock.expect(mockedGame.getCubesLeftByColor(CubeColor.BLUE)).andReturn(1);
    mockedGame.setDiseaseCubesLeft(CubeColor.BLUE, 4);
    EasyMock.replay(mockedPlayer, mockedCity, mockedGame);

    GameLogic logic = new GameLogic(mockedGame);
    logic.movePlayerByShuttleFlight(mockedCity);

    EasyMock.verify(mockedPlayer, mockedCity);
  }

  @Test
  public void testCuredCubeAutoRemoveByDispatcherAction() {
    Player mockedPlayer = EasyMock.createNiceMock(Player.class);
    EasyMock.expect(mockedPlayer.getRole()).andReturn(Role.Medic);
    mockedPlayer.removeAllCubes(CubeColor.BLACK);
    EasyMock.expectLastCall();
    mockedPlayer.removeAllCubes(CubeColor.BLUE);
    EasyMock.expectLastCall();
    City mockedCity = EasyMock.createMock(City.class);
    City mockedDest = EasyMock.createMock(City.class);
    EasyMock.expect(mockedPlayer.getCurrentCity()).andReturn(mockedCity);
    EasyMock.expect(mockedPlayer.getCurrentCity()).andStubReturn(mockedDest);
    mockedPlayer.setCurrentCity(mockedDest);
    EasyMock.expectLastCall();
    EasyMock.expect(mockedDest.getCubeCount(CubeColor.BLACK)).andReturn(2);
    EasyMock.expect(mockedDest.getCubeCount(CubeColor.BLUE)).andReturn(3);
    EasyMock.expect(mockedDest.getCubeCount(CubeColor.BLACK)).andReturn(2);
    EasyMock.expect(mockedDest.getCubeCount(CubeColor.BLUE)).andReturn(3);

    Game mockedGame = EasyMock.createNiceMock(Game.class);
    EasyMock.expect(mockedGame.getPlayerAtIndex(1)).andStubReturn(mockedPlayer);
    mockedGame.decrementPlayerActionsLeft();
    EasyMock.expectLastCall();
    Map<CubeColor, Boolean> curedDisease = new HashMap<>();
    curedDisease.put(CubeColor.BLACK, true);
    curedDisease.put(CubeColor.BLUE, true);
    EasyMock.expect(mockedGame.hasPlayers(mockedDest)).andReturn(true);
    EasyMock.expect(mockedGame.getCuredDiseases()).andStubReturn(curedDisease);
    EasyMock.expect(mockedGame.isDiseaseCured(CubeColor.BLACK)).andReturn(true);
    EasyMock.expect(mockedGame.isDiseaseCured(CubeColor.BLUE)).andReturn(true);

    EasyMock.replay(mockedPlayer, mockedCity, mockedDest, mockedGame);

    GameLogic logic = new GameLogic(mockedGame);
    logic.movePlayerByDispatcher(1, mockedDest);

    EasyMock.verify(mockedPlayer, mockedCity, mockedDest, mockedGame);
  }

  @Test
  public void testPassSpecificCardByResearcher() {
    Card mockedCard = EasyMock.createMock(Card.class);
    Player mockedPlayer = EasyMock.createNiceMock(Player.class);
    Player mockedResearcher = EasyMock.createNiceMock(Player.class);
    mockedResearcher.passSpecificCardToPlayer(mockedPlayer, mockedCard);
    EasyMock.expectLastCall();
    Game mockedGame = EasyMock.createMock(Game.class);
    mockedGame.decrementPlayerActionsLeft();
    EasyMock.expectLastCall();
    EasyMock.replay(mockedCard, mockedGame, mockedPlayer, mockedResearcher);

    GameLogic logic = new GameLogic(mockedGame);
    logic.passSpecificCardByResearcher(mockedResearcher, mockedPlayer, mockedCard);

    EasyMock.verify(mockedCard, mockedGame, mockedPlayer, mockedResearcher);
  }

}
