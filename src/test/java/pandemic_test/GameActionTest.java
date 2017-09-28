package pandemic_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;

import org.easymock.EasyMock;
import org.junit.Test;

import pandemic_exception.GameOverException;
import pandemic_exception.InvalidActionException;
import pandemic_game.Card;
import pandemic_game.City;
import pandemic_game.Constants.CubeColor;
import pandemic_game.Deck;
import pandemic_game.Game;
import pandemic_game.GameLogic;
import pandemic_game.Player;

public class GameActionTest {

  @Test
  public void testMoveResearchStation() {
    Game mockedGame = EasyMock.createMock(Game.class);
    mockedGame.decrementPlayerActionsLeft();
    EasyMock.expectLastCall();
    Player mockedPlayer = EasyMock.createMock(Player.class);
    EasyMock.expect(mockedGame.getCurrentPlayer()).andReturn(mockedPlayer);
    City mockedCurrentCity = EasyMock.createMock(City.class);
    EasyMock.expect(mockedPlayer.getCurrentCity()).andReturn(mockedCurrentCity);
    City mockedCityToRemoveStation = EasyMock.createMock(City.class);
    EasyMock.expect(mockedCurrentCity.hasResearchStation()).andReturn(false);
    EasyMock.expect(mockedCityToRemoveStation.hasResearchStation()).andReturn(true);
    mockedCurrentCity.addResearchStation();
    EasyMock.expectLastCall();
    mockedCityToRemoveStation.removeResearchStation();
    EasyMock.expectLastCall();

    EasyMock.replay(mockedGame, mockedCurrentCity, mockedCityToRemoveStation, mockedPlayer);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.moveResearchStation(mockedCityToRemoveStation);

    EasyMock.verify(mockedGame, mockedCurrentCity, mockedCityToRemoveStation, mockedPlayer);
  }

  @Test(expected = InvalidActionException.class)
  public void testMoveResearchStationBothFalse() {
    Game mockedGame = EasyMock.createMock(Game.class);
    Player mockedPlayer = EasyMock.createMock(Player.class);
    EasyMock.expect(mockedGame.getCurrentPlayer()).andReturn(mockedPlayer);
    City mockedCurrentCity = EasyMock.createMock(City.class);
    EasyMock.expect(mockedPlayer.getCurrentCity()).andReturn(mockedCurrentCity);
    City mockedCityToRemoveStation = EasyMock.createMock(City.class);
    EasyMock.expect(mockedCurrentCity.hasResearchStation()).andReturn(true);
    EasyMock.expect(mockedCityToRemoveStation.hasResearchStation()).andReturn(false);
    EasyMock.expect(mockedCityToRemoveStation.getName()).andReturn("City");

    EasyMock.replay(mockedGame, mockedCurrentCity, mockedCityToRemoveStation, mockedPlayer);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.moveResearchStation(mockedCityToRemoveStation);

    EasyMock.verify(mockedGame, mockedCurrentCity, mockedCityToRemoveStation, mockedPlayer);
  }

  @Test(expected = InvalidActionException.class)
  public void testMoveResearchStationWhenCityHasNoStation() {
    Game mockedGame = EasyMock.createMock(Game.class);
    Player mockedPlayer = EasyMock.createMock(Player.class);
    EasyMock.expect(mockedGame.getCurrentPlayer()).andReturn(mockedPlayer);
    City mockedCurrentCity = EasyMock.createMock(City.class);
    EasyMock.expect(mockedPlayer.getCurrentCity()).andReturn(mockedCurrentCity);
    City mockedCityToRemoveStation = EasyMock.createMock(City.class);
    EasyMock.expect(mockedCurrentCity.hasResearchStation()).andReturn(false);
    EasyMock.expect(mockedCityToRemoveStation.hasResearchStation()).andReturn(false);
    EasyMock.expect(mockedCityToRemoveStation.getName()).andReturn("City");

    EasyMock.replay(mockedGame, mockedCurrentCity, mockedCityToRemoveStation, mockedPlayer);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.moveResearchStation(mockedCityToRemoveStation);

    EasyMock.verify(mockedGame, mockedCurrentCity, mockedCityToRemoveStation, mockedPlayer);
  }

  @Test(expected = InvalidActionException.class)
  public void testMoveResearchStationWhenCurrentCityHasStation() {
    Game mockedGame = EasyMock.createMock(Game.class);
    Player mockedPlayer = EasyMock.createMock(Player.class);
    EasyMock.expect(mockedGame.getCurrentPlayer()).andReturn(mockedPlayer);
    City mockedCurrentCity = EasyMock.createMock(City.class);
    EasyMock.expect(mockedPlayer.getCurrentCity()).andReturn(mockedCurrentCity);
    City mockedCityToRemoveStation = EasyMock.createMock(City.class);
    EasyMock.expect(mockedCurrentCity.hasResearchStation()).andReturn(true);
    EasyMock.expect(mockedCityToRemoveStation.hasResearchStation()).andReturn(true);
    EasyMock.expect(mockedCityToRemoveStation.getName()).andReturn("City");

    EasyMock.replay(mockedGame, mockedCurrentCity, mockedCityToRemoveStation, mockedPlayer);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.moveResearchStation(mockedCityToRemoveStation);

    EasyMock.verify(mockedGame, mockedCurrentCity, mockedCityToRemoveStation, mockedPlayer);
  }

  @Test
  public void testAddDiseaseCube() {
    City mockedCity = EasyMock.createMock(City.class);
    EasyMock.expect(mockedCity.getCubeCount(CubeColor.RED)).andReturn(0);
    mockedCity.addCube(CubeColor.RED);
    EasyMock.expectLastCall();
    Game mockedGame = EasyMock.createMock(Game.class);
    EasyMock.expect(mockedGame.getCubesLeftByColor(CubeColor.RED)).andReturn(3);
    mockedGame.decrementDiseaseCube(CubeColor.RED);
    EasyMock.expectLastCall();
    EasyMock.expect(mockedGame.hasMedicAt(mockedCity)).andReturn(false);
    EasyMock.replay(mockedGame, mockedCity);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.addDiseaseCube(mockedCity, CubeColor.RED);

    EasyMock.verify(mockedGame, mockedCity);
  }

  @Test
  public void testAddDiseaseCubeFailsOnMedicCity() {
    City mockedCity = EasyMock.createMock(City.class);
    Game mockedGame = EasyMock.createMock(Game.class);
    EasyMock.expect(mockedGame.getCubesLeftByColor(CubeColor.RED)).andReturn(5);
    EasyMock.expect(mockedGame.hasMedicAt(mockedCity)).andReturn(true);
    EasyMock.expect(mockedGame.isDiseaseCured(CubeColor.RED)).andReturn(true);
    EasyMock.replay(mockedGame, mockedCity);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.addDiseaseCube(mockedCity, CubeColor.RED);

    EasyMock.verify(mockedGame, mockedCity);
  }

  @Test
  public void testAddDiseaseCubeCauseOutbreak() {
    Game mockedGame = EasyMock.createMock(Game.class);
    EasyMock.expect(mockedGame.getCubesLeftByColor(CubeColor.BLACK)).andStubReturn(3);
    mockedGame.incrementOutbreaks();
    EasyMock.expectLastCall();
    EasyMock.expect(mockedGame.getOutbreaks()).andReturn(1);
    EasyMock.expect(mockedGame.isGameOver()).andStubReturn(false);
    mockedGame.decrementDiseaseCube(CubeColor.BLACK);
    EasyMock.expectLastCall().asStub();
    City mockedCity = EasyMock.createMock(City.class);
    EasyMock.expect(mockedCity.getCubeCount(CubeColor.BLACK)).andReturn(3);
    City adjacentCity = EasyMock.createMock(City.class);
    EasyMock.expect(adjacentCity.getCubeCount(CubeColor.BLACK)).andReturn(0);
    adjacentCity.addCube(CubeColor.BLACK);
    EasyMock.expectLastCall();
    List<City> adjacent = new ArrayList<>();
    adjacent.add(adjacentCity);
    EasyMock.expect(mockedCity.getAdjacentCities()).andStubReturn(adjacent);
    EasyMock.expect(mockedGame.hasMedicAt(mockedCity)).andReturn(true);
    EasyMock.expect(mockedGame.isDiseaseCured(CubeColor.BLACK)).andReturn(false);
    EasyMock.expect(mockedGame.hasMedicAt(adjacentCity)).andReturn(false);
    EasyMock.replay(mockedGame, mockedCity, adjacentCity);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.addDiseaseCube(mockedCity, CubeColor.BLACK);

    EasyMock.verify(mockedGame, mockedCity, adjacentCity);
  }

  @Test(expected = GameOverException.class)
  public void testAddDiseaseCubeWithoutEnoughCubesToCauseChainEffect() {
    Game mockedGame = EasyMock.createNiceMock(Game.class);
    EasyMock.expect(mockedGame.getCubesLeftByColor(CubeColor.BLACK)).andReturn(1);
    EasyMock.expect(mockedGame.getCubesLeftByColor(CubeColor.BLACK)).andReturn(0);
    mockedGame.setGameOver(true);
    EasyMock.expectLastCall();
    mockedGame.incrementOutbreaks();
    EasyMock.expectLastCall();
    EasyMock.expect(mockedGame.getOutbreaks()).andReturn(1);
    EasyMock.expect(mockedGame.isGameOver()).andStubReturn(false);
    mockedGame.decrementDiseaseCube(CubeColor.BLACK);
    EasyMock.expectLastCall().asStub();
    City mockedCity = EasyMock.createMock(City.class);
    EasyMock.expect(mockedGame.hasMedicAt(mockedCity)).andReturn(false);
    EasyMock.expect(mockedCity.getCubeCount(CubeColor.BLACK)).andReturn(3);
    City adjacentCity1 = EasyMock.createMock(City.class);
    EasyMock.expect(adjacentCity1.getCubeCount(CubeColor.BLACK)).andReturn(0);
    adjacentCity1.addCube(CubeColor.BLACK);
    EasyMock.expectLastCall();
    List<City> adjacent = new ArrayList<>();
    City adjacentCity2 = EasyMock.createMock(City.class);
    adjacent.add(adjacentCity2);
    adjacent.add(adjacentCity1);
    EasyMock.expect(mockedCity.getAdjacentCities()).andStubReturn(adjacent);

    EasyMock.replay(mockedGame, mockedCity, adjacentCity1, adjacentCity2);

    GameLogic gameLogic = new GameLogic(mockedGame);
    Set<City> visitedCities = new HashSet<>();
    visitedCities.add(adjacentCity2);
    gameLogic.setVisitedCities(visitedCities);
    gameLogic.addDiseaseCube(mockedCity, CubeColor.BLACK);

    EasyMock.verify(mockedGame, mockedCity, adjacentCity1, adjacentCity2);
  }

  @Test(expected = GameOverException.class)
  public void testAddDiseaseCubeCauseOutbreakAndGameOver() {
    City mockedCity = EasyMock.createMock(City.class);
    EasyMock.expect(mockedCity.getCubeCount(CubeColor.YELLOW)).andReturn(3);
    Game mockedGame = EasyMock.createNiceMock(Game.class);
    EasyMock.expect(mockedGame.getCubesLeftByColor(CubeColor.YELLOW)).andReturn(10);
    EasyMock.expect(mockedGame.getOutbreaks()).andReturn(8);
    EasyMock.expect(mockedGame.isGameOver()).andReturn(true);
    mockedGame.incrementOutbreaks();
    EasyMock.expectLastCall();
    mockedGame.setGameOver(true);
    EasyMock.expectLastCall();
    EasyMock.expect(mockedGame.hasMedicAt(mockedCity)).andReturn(false);
    EasyMock.expect(mockedGame.isDiseaseCured(CubeColor.RED)).andReturn(true);

    EasyMock.replay(mockedGame, mockedCity);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.addDiseaseCube(mockedCity, CubeColor.YELLOW);

    EasyMock.verify(mockedGame, mockedCity);
  }

  @Test(expected = GameOverException.class)
  public void testAddDiseaseCubeWithNoCubesLeftAndGameOver() {
    Game mockedGame = EasyMock.createNiceMock(Game.class);
    EasyMock.expect(mockedGame.getCubesLeftByColor(CubeColor.RED)).andReturn(0);
    mockedGame.setGameOver(false);
    EasyMock.expectLastCall();
    mockedGame.setGameOver(true);
    EasyMock.expectLastCall();
    City mockedCity = EasyMock.createMock(City.class);

    EasyMock.replay(mockedGame, mockedCity);

    GameLogic gameLogic = new GameLogic(mockedGame);
    mockedGame.setGameOver(false);
    gameLogic.addDiseaseCube(mockedCity, CubeColor.RED);

    EasyMock.verify(mockedGame, mockedCity);
  }

  @Test
  public void testBuildResearchStationAndDecrementResearchStationLeft() {
    Game mockedGame = EasyMock.createMock(Game.class);
    EasyMock.expect(mockedGame.getTotalResearchStationLeft()).andReturn(1);
    mockedGame.decrementPlayerActionsLeft();
    EasyMock.expectLastCall();
    mockedGame.decrementResearchStationLeft();
    EasyMock.expectLastCall();
    Player mockedCurrentPlayer = EasyMock.createMock(Player.class);
    EasyMock.expect(mockedGame.getCurrentPlayer()).andReturn(mockedCurrentPlayer);
    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);
    EasyMock.expect(mockedGame.getPlayerDiscardDeck()).andReturn(mockedPlayerDiscardDeck);
    mockedCurrentPlayer.buildResearchStation(mockedPlayerDiscardDeck);
    EasyMock.expectLastCall();

    EasyMock.replay(mockedGame, mockedCurrentPlayer, mockedPlayerDiscardDeck);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.buildResearchStation();

    EasyMock.verify(mockedGame, mockedCurrentPlayer, mockedPlayerDiscardDeck);
  }

  @Test
  public void testIncrementOutbreaksAndGameContinues() {
    Game mockedGame = EasyMock.createMock(Game.class);
    mockedGame.incrementOutbreaks();
    EasyMock.expect(mockedGame.getOutbreaks()).andReturn(1);

    EasyMock.replay(mockedGame);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.incrementOutbreaks();

    EasyMock.verify(mockedGame);
  }

  @Test(expected = GameOverException.class)
  public void testIncrementOutbreaksAndGameEnds() {
    Game mockedGame = EasyMock.createMock(Game.class);
    mockedGame.incrementOutbreaks();
    EasyMock.expect(mockedGame.getOutbreaks()).andReturn(8);

    EasyMock.replay(mockedGame);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.incrementOutbreaks();

    EasyMock.verify(mockedGame);
  }

  @Test
  public void testHasEnoughCardsToCureDisease() {
    Player mockedPlayer = EasyMock.createMock(Player.class);
    EasyMock.expect(mockedPlayer.hasEnoughCardsToDiscoverCure()).andReturn(true);
    Game mockedGame = EasyMock.createMock(Game.class);
    EasyMock.expect(mockedGame.getCurrentPlayer()).andReturn(mockedPlayer);

    EasyMock.replay(mockedGame, mockedPlayer);

    GameLogic gameLogic = new GameLogic(mockedGame);
    assertTrue(gameLogic.hasEnoughCardsToCureDisease());

    EasyMock.verify(mockedGame, mockedPlayer);
  }

  @Test
  public void testCureDisease() {
    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);
    Game mockedGame = EasyMock.createMock(Game.class);
    Player mockedPlayer = EasyMock.createMock(Player.class);
    EasyMock.expect(mockedGame.getPlayerDiscardDeck()).andReturn(mockedPlayerDiscardDeck);
    EasyMock.expect(mockedGame.getCurrentPlayer()).andStubReturn(mockedPlayer);
    mockedGame.cureDisease(CubeColor.BLUE);
    EasyMock.expectLastCall();
    mockedGame.decrementPlayerActionsLeft();
    EasyMock.expectLastCall();
    City mockedCity = EasyMock.createMock(City.class);
    EasyMock.expect(mockedPlayer.getCurrentCity()).andReturn(mockedCity);
    EasyMock.expect(mockedCity.hasResearchStation()).andReturn(true);
    Map<CubeColor, Boolean> map = new HashMap<>();
    map.put(CubeColor.BLUE, false);
    EasyMock.expect(mockedGame.getCuredDiseases()).andReturn(map);
    EasyMock.expect(mockedPlayer.discoverCure(mockedPlayerDiscardDeck, map))
        .andReturn(CubeColor.BLUE);
    EasyMock.replay(mockedGame, mockedPlayer, mockedPlayerDiscardDeck, mockedCity);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.cureDisease();

    EasyMock.verify(mockedGame, mockedPlayer, mockedPlayerDiscardDeck, mockedCity);
  }

  @Test(expected = InvalidActionException.class)
  public void testCureDiseaseWithoutResearchStation() {
    Player mockedPlayer = EasyMock.createMock(Player.class);
    Game mockedGame = EasyMock.createMock(Game.class);
    EasyMock.expect(mockedGame.getCurrentPlayer()).andStubReturn(mockedPlayer);
    City mockedCity = EasyMock.createMock(City.class);
    EasyMock.expect(mockedPlayer.getCurrentCity()).andReturn(mockedCity);
    EasyMock.expect(mockedCity.hasResearchStation()).andReturn(false);

    EasyMock.replay(mockedGame, mockedPlayer, mockedCity);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.cureDisease();

    EasyMock.verify(mockedGame, mockedPlayer, mockedCity);
  }

  @Test
  public void testCureDiseaseWithSelectedCards() {
    Card mockedCard = EasyMock.createMock(Card.class);
    EasyMock.expect(mockedCard.getColor()).andReturn(CubeColor.YELLOW);
    List<Card> selectedCards = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      selectedCards.add(mockedCard);
    }
    Map<CubeColor, Boolean> map = new HashMap<>();
    map.put(CubeColor.YELLOW, false);
    Player mockedPlayer = EasyMock.createMock(Player.class);
    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);
    EasyMock.expect(mockedPlayer.discoverCure(selectedCards, mockedPlayerDiscardDeck, map))
        .andReturn(CubeColor.YELLOW);
    Game mockedGame = EasyMock.createMock(Game.class);
    EasyMock.expect(mockedGame.getPlayerDiscardDeck()).andReturn(mockedPlayerDiscardDeck);
    EasyMock.expect(mockedGame.getCurrentPlayer()).andStubReturn(mockedPlayer);
    mockedGame.cureDisease(CubeColor.YELLOW);
    EasyMock.expectLastCall();
    mockedGame.decrementPlayerActionsLeft();
    EasyMock.expectLastCall();
    EasyMock.expect(mockedGame.getCuredDiseases()).andReturn(map);
    City mockedCity = EasyMock.createMock(City.class);
    EasyMock.expect(mockedPlayer.getCurrentCity()).andReturn(mockedCity);
    EasyMock.expect(mockedCity.hasResearchStation()).andReturn(true);

    EasyMock.replay(mockedGame, mockedPlayer, mockedPlayerDiscardDeck, mockedCity);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.cureDisease(selectedCards);

    EasyMock.verify(mockedGame, mockedPlayer, mockedPlayerDiscardDeck, mockedCity);
  }

  @Test(expected = InvalidActionException.class)
  public void testCureDiseaseWithSelectedCardsWithoutResearchStation() {
    Player mockedPlayer = EasyMock.createMock(Player.class);
    Game mockedGame = EasyMock.createMock(Game.class);
    EasyMock.expect(mockedGame.getCurrentPlayer()).andStubReturn(mockedPlayer);
    City mockedCity = EasyMock.createMock(City.class);
    EasyMock.expect(mockedPlayer.getCurrentCity()).andReturn(mockedCity);
    EasyMock.expect(mockedCity.hasResearchStation()).andReturn(false);

    EasyMock.replay(mockedGame, mockedPlayer, mockedCity);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.cureDisease(new ArrayList<Card>());

    EasyMock.verify(mockedGame, mockedPlayer, mockedCity);
  }

  @Test
  public void testDrawFromPlayerDeck() {
    Game mockedGame = EasyMock.createMock(Game.class);
    Card mockedCard = EasyMock.createMock(Card.class);
    Player mockedPlayer = EasyMock.createMock(Player.class);
    EasyMock.expect(mockedGame.getCurrentPlayer()).andReturn(mockedPlayer);
    Deck mockedPlayerDeck = EasyMock.createMock(Deck.class);
    EasyMock.expect(mockedGame.getPlayerDeck()).andReturn(mockedPlayerDeck);
    EasyMock.expect(mockedPlayerDeck.drawCard()).andReturn(mockedCard);
    mockedPlayer.addCard(mockedCard);
    EasyMock.expectLastCall();
    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);
    EasyMock.expect(mockedGame.getPlayerDiscardDeck()).andReturn(mockedPlayerDiscardDeck);
    mockedPlayerDiscardDeck.addCard(mockedCard);
    EasyMock.expectLastCall();

    EasyMock.replay(mockedGame, mockedPlayerDeck, mockedCard, mockedPlayer,
        mockedPlayerDiscardDeck);

    GameLogic gameLogic = new GameLogic(mockedGame);
    assertEquals(mockedCard, gameLogic.drawFromPlayerDeck());

    EasyMock.verify(mockedGame, mockedPlayerDeck, mockedCard, mockedPlayer,
        mockedPlayerDiscardDeck);
  }

  @Test(expected = GameOverException.class)
  public void testDrawFromPlayerDeckWhenNoCardLeft() {
    Game mockedGame = EasyMock.createMock(Game.class);
    Deck mockedPlayerDeck = EasyMock.createMock(Deck.class);
    EasyMock.expect(mockedGame.getPlayerDeck()).andReturn(mockedPlayerDeck);
    mockedGame.setGameOver(true);
    EasyMock.expectLastCall();
    EasyMock.expect(mockedPlayerDeck.drawCard()).andThrow(new NoSuchElementException());

    EasyMock.replay(mockedGame, mockedPlayerDeck);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.drawFromPlayerDeck();

    EasyMock.verify(mockedGame, mockedPlayerDeck);
  }

  @Test
  public void testGameContinuesWhenThreeDiseasesAreCured() {
    Map<CubeColor, Boolean> curedDiseases = new HashMap<>();
    curedDiseases.put(CubeColor.RED, true);
    curedDiseases.put(CubeColor.BLACK, true);
    curedDiseases.put(CubeColor.BLUE, true);
    curedDiseases.put(CubeColor.YELLOW, false);

    Game mockedGame = EasyMock.createMock(Game.class);
    EasyMock.expect(mockedGame.getCuredDiseases()).andReturn(curedDiseases);

    EasyMock.replay(mockedGame);

    GameLogic gameLogic = new GameLogic(mockedGame);
    assertFalse(gameLogic.checkForWin());

    EasyMock.verify(mockedGame);
  }

  @Test
  public void testGameWinsWhenAllDiseasesAreCured() {
    Map<CubeColor, Boolean> curedDiseases = new HashMap<>();
    curedDiseases.put(CubeColor.RED, true);
    curedDiseases.put(CubeColor.BLACK, true);
    curedDiseases.put(CubeColor.BLUE, true);
    curedDiseases.put(CubeColor.YELLOW, true);

    Game mockedGame = EasyMock.createMock(Game.class);
    EasyMock.expect(mockedGame.getCuredDiseases()).andReturn(curedDiseases);

    EasyMock.replay(mockedGame);

    GameLogic gameLogic = new GameLogic(mockedGame);
    assertTrue(gameLogic.checkForWin());

    EasyMock.verify(mockedGame);
  }

  @Test
  public void testDrawInfectionCardAndAddCube() {
    Game mockedGame = EasyMock.createMock(Game.class);
    Deck mockedInfectionDeck = EasyMock.createMock(Deck.class);
    Card mockedInfectionCard = EasyMock.createMock(Card.class);
    EasyMock.expect(mockedGame.getInfectionDeck()).andReturn(mockedInfectionDeck);
    EasyMock.expect(mockedInfectionDeck.drawCard()).andReturn(mockedInfectionCard);
    EasyMock.expect(mockedInfectionCard.getName()).andReturn("City");
    EasyMock.expect(mockedInfectionCard.getColor()).andStubReturn(CubeColor.BLACK);
    EasyMock.expect(mockedGame.getCubesLeftByColor(CubeColor.BLACK)).andStubReturn(20);
    City mockedInfectedCity = EasyMock.createMock(City.class);
    EasyMock.expect(mockedGame.getCity("City")).andReturn(mockedInfectedCity);
    EasyMock.expect(mockedInfectedCity.getCubeCount(CubeColor.BLACK)).andReturn(0);
    mockedGame.decrementDiseaseCube(CubeColor.BLACK);
    EasyMock.expectLastCall();
    mockedInfectedCity.addCube(CubeColor.BLACK);
    EasyMock.expectLastCall();
    Deck mockedInfectionDiscardDeck = EasyMock.createMock(Deck.class);
    EasyMock.expect(mockedGame.getInfectionDiscardDeck()).andReturn(mockedInfectionDiscardDeck);
    mockedInfectionDiscardDeck.addCard(mockedInfectionCard);
    EasyMock.expectLastCall();
    EasyMock.expect(mockedGame.hasMedicAt(mockedInfectedCity)).andReturn(true);
    EasyMock.expect(mockedGame.isDiseaseCured(CubeColor.BLACK)).andReturn(false);
    EasyMock.replay(mockedGame, mockedInfectedCity, mockedInfectionCard, mockedInfectionDeck,
        mockedInfectionDiscardDeck);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.drawInfectionCard();

    EasyMock.verify(mockedGame, mockedInfectedCity, mockedInfectionCard, mockedInfectionDeck,
        mockedInfectionDiscardDeck);
  }
  
  @Test
  public void testDrawInfectionCardAndAddOneMoreCube() {
    Game mockedGame = EasyMock.createMock(Game.class);
    Deck mockedInfectionDeck = EasyMock.createMock(Deck.class);
    Card mockedInfectionCard = EasyMock.createMock(Card.class);
    EasyMock.expect(mockedGame.getInfectionDeck()).andReturn(mockedInfectionDeck);
    EasyMock.expect(mockedInfectionDeck.drawCard()).andReturn(mockedInfectionCard);
    EasyMock.expect(mockedInfectionCard.getName()).andReturn("City");
    EasyMock.expect(mockedInfectionCard.getColor()).andStubReturn(CubeColor.BLACK);
    EasyMock.expect(mockedGame.getCubesLeftByColor(CubeColor.BLACK)).andStubReturn(1);
    City mockedInfectedCity = EasyMock.createMock(City.class);
    EasyMock.expect(mockedGame.getCity("City")).andReturn(mockedInfectedCity);
    EasyMock.expect(mockedInfectedCity.getCubeCount(CubeColor.BLACK)).andReturn(1);
    mockedGame.decrementDiseaseCube(CubeColor.BLACK);
    EasyMock.expectLastCall();
    mockedInfectedCity.addCube(CubeColor.BLACK);
    EasyMock.expectLastCall();
    Deck mockedInfectionDiscardDeck = EasyMock.createMock(Deck.class);
    EasyMock.expect(mockedGame.getInfectionDiscardDeck()).andReturn(mockedInfectionDiscardDeck);
    mockedInfectionDiscardDeck.addCard(mockedInfectionCard);
    EasyMock.expectLastCall();
    EasyMock.expect(mockedGame.hasMedicAt(mockedInfectedCity)).andReturn(false);
    EasyMock.replay(mockedGame, mockedInfectedCity, mockedInfectionCard, mockedInfectionDeck,
        mockedInfectionDiscardDeck);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.drawInfectionCard();

    EasyMock.verify(mockedGame, mockedInfectedCity, mockedInfectionCard, mockedInfectionDeck,
        mockedInfectionDiscardDeck);
  }

  @Test
  public void testIsEradicatedWhenBlueHas24AndGotCured() {
    Game mockedGame = EasyMock.createMock(Game.class);
    EasyMock.expect(mockedGame.getCubesLeftByColor(CubeColor.BLUE)).andReturn(24);
    EasyMock.expect(mockedGame.isDiseaseCured(CubeColor.BLUE)).andReturn(true);

    EasyMock.replay(mockedGame);

    GameLogic gameLogic = new GameLogic(mockedGame);
    assertTrue(gameLogic.isEradicated(CubeColor.BLUE));

    EasyMock.verify(mockedGame);
  }

  @Test
  public void testIsEradicatedWhenBlueHas24AndNotCured() {
    Game mockedGame = EasyMock.createMock(Game.class);
    EasyMock.expect(mockedGame.getCubesLeftByColor(CubeColor.BLACK)).andStubReturn(24);
    EasyMock.expect(mockedGame.isDiseaseCured(CubeColor.BLACK)).andReturn(false);

    EasyMock.replay(mockedGame);

    GameLogic gameLogic = new GameLogic(mockedGame);
    assertFalse(gameLogic.isEradicated(CubeColor.BLACK));

    EasyMock.verify(mockedGame);
  }

  @Test
  public void testIsEradicatedWhenBlueHas23() {
    Game mockedGame = EasyMock.createMock(Game.class);
    EasyMock.expect(mockedGame.getCubesLeftByColor(CubeColor.RED)).andStubReturn(23);

    EasyMock.replay(mockedGame);

    GameLogic gameLogic = new GameLogic(mockedGame);
    assertFalse(gameLogic.isEradicated(CubeColor.RED));

    EasyMock.verify(mockedGame);
  }

  @Test
  public void testDrawInfectionCardWhenDiseaseEradicated() {
    Game mockedGame = EasyMock.createMock(Game.class);
    Deck mockedInfectionDeck = EasyMock.createMock(Deck.class);
    Card mockedInfectionCard = EasyMock.createMock(Card.class);
    EasyMock.expect(mockedGame.getInfectionDeck()).andReturn(mockedInfectionDeck);
    EasyMock.expect(mockedInfectionDeck.drawCard()).andReturn(mockedInfectionCard);
    EasyMock.expect(mockedInfectionCard.getColor()).andReturn(CubeColor.RED);
    EasyMock.expect(mockedGame.getCubesLeftByColor(CubeColor.RED)).andStubReturn(24);
    EasyMock.expect(mockedGame.isDiseaseCured(CubeColor.RED)).andReturn(true);

    EasyMock.replay(mockedGame, mockedInfectionCard, mockedInfectionDeck);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.drawInfectionCard();

    EasyMock.verify(mockedGame, mockedInfectionCard, mockedInfectionDeck);
  }

  @Test
  public void testDrawEpidemicCardAction() {
    Game mockedGame = EasyMock.createNiceMock(Game.class);
    mockedGame.increaseInfectionRate();
    EasyMock.expectLastCall();
    Deck mockedInfectionDeck = EasyMock.createMock(Deck.class);
    EasyMock.expect(mockedGame.getInfectionDeck()).andStubReturn(mockedInfectionDeck);
    Card mockedInfectionCard = EasyMock.createMock(Card.class);
    EasyMock.expect(mockedInfectionDeck.drawLastCard()).andReturn(mockedInfectionCard);
    EasyMock.expect(mockedInfectionCard.getColor()).andReturn(CubeColor.BLUE);
    EasyMock.expect(mockedGame.getCubesLeftByColor(CubeColor.BLUE)).andStubReturn(20);
    EasyMock.expect(mockedInfectionCard.getName()).andReturn("City");
    City mockedInfectedCity = EasyMock.createStrictMock(City.class);
    EasyMock.expect(mockedGame.getCity("City")).andReturn(mockedInfectedCity);
    EasyMock.expect(mockedGame.hasMedicAt(mockedInfectedCity)).andReturn(false);

    EasyMock.expect(mockedInfectedCity.getCubeCount(CubeColor.BLUE)).andReturn(0);
    EasyMock.expect(mockedInfectedCity.getCubeCount(CubeColor.BLUE)).andReturn(0);
    mockedInfectedCity.addCube(CubeColor.BLUE);
    EasyMock.expectLastCall();
    mockedGame.decrementDiseaseCube(CubeColor.BLUE);
    EasyMock.expectLastCall();
    EasyMock.expect(mockedInfectedCity.getCubeCount(CubeColor.BLUE)).andReturn(1);
    mockedInfectedCity.addCube(CubeColor.BLUE);
    EasyMock.expectLastCall();
    mockedGame.decrementDiseaseCube(CubeColor.BLUE);
    EasyMock.expectLastCall();
    EasyMock.expect(mockedInfectedCity.getCubeCount(CubeColor.BLUE)).andReturn(2);
    mockedInfectedCity.addCube(CubeColor.BLUE);
    EasyMock.expectLastCall();
    mockedGame.decrementDiseaseCube(CubeColor.BLUE);
    EasyMock.expectLastCall();
    EasyMock.expect(mockedInfectedCity.getCubeCount(CubeColor.BLUE)).andReturn(3);

    Deck mockedInfectionDiscardDeck = EasyMock.createMock(Deck.class);
    EasyMock.expect(mockedGame.getInfectionDiscardDeck()).andStubReturn(mockedInfectionDiscardDeck);
    mockedInfectionDiscardDeck.addCard(mockedInfectionCard);
    EasyMock.expectLastCall();
    Random mockedRandom = EasyMock.createMock(Random.class);
    mockedInfectionDiscardDeck.shuffle(mockedRandom);
    EasyMock.expectLastCall();
    mockedInfectionDeck.placeCardsOnTop(mockedInfectionDiscardDeck);
    EasyMock.expectLastCall();

    EasyMock.replay(mockedGame, mockedInfectedCity, mockedInfectionCard, mockedInfectionDeck,
        mockedInfectionDiscardDeck, mockedRandom);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.epidemicCardDrawn(mockedRandom);

    EasyMock.verify(mockedGame, mockedInfectedCity, mockedInfectionCard, mockedInfectionDeck,
        mockedInfectionDiscardDeck, mockedRandom);
  }

  @Test(expected = GameOverException.class)
  public void testDrawEpidemicCardActionCauseOutbreak() {
    Game mockedGame = EasyMock.createMock(Game.class);
    mockedGame.increaseInfectionRate();
    EasyMock.expectLastCall();
    Deck mockedInfectionDeck = EasyMock.createMock(Deck.class);
    EasyMock.expect(mockedGame.getInfectionDeck()).andStubReturn(mockedInfectionDeck);
    Card mockedInfectionCard = EasyMock.createMock(Card.class);
    EasyMock.expect(mockedInfectionDeck.drawLastCard()).andStubReturn(mockedInfectionCard);
    EasyMock.expect(mockedInfectionCard.getColor()).andReturn(CubeColor.BLUE);
    EasyMock.expect(mockedGame.getCubesLeftByColor(CubeColor.BLUE)).andStubReturn(0);
    EasyMock.expect(mockedInfectionCard.getName()).andReturn("City");
    City mockedInfectedCity = EasyMock.createMock(City.class);
    EasyMock.expect(mockedGame.getCity("City")).andReturn(mockedInfectedCity);
    EasyMock.expect(mockedGame.hasMedicAt(mockedInfectedCity)).andReturn(true);
    EasyMock.expect(mockedGame.isDiseaseCured(CubeColor.BLUE)).andReturn(false);
    EasyMock.expect(mockedInfectedCity.getCubeCount(CubeColor.BLUE)).andStubReturn(3);
    mockedGame.setGameOver(true);
    EasyMock.expectLastCall();

    Deck mockedInfectionDiscardDeck = EasyMock.createMock(Deck.class);
    EasyMock.expect(mockedGame.getInfectionDiscardDeck()).andStubReturn(mockedInfectionDiscardDeck);
    mockedInfectionDiscardDeck.addCard(mockedInfectionCard);
    EasyMock.expectLastCall();
    Random mockedRandom = EasyMock.createMock(Random.class);
    mockedInfectionDiscardDeck.shuffle(mockedRandom);
    EasyMock.expectLastCall();
    mockedInfectionDeck.placeCardsOnTop(mockedInfectionDiscardDeck);
    EasyMock.expectLastCall();

    EasyMock.replay(mockedGame, mockedInfectedCity, mockedInfectionCard, mockedInfectionDeck,
        mockedInfectionDiscardDeck, mockedRandom);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.epidemicCardDrawn(mockedRandom);

    EasyMock.verify(mockedGame, mockedInfectedCity, mockedInfectionCard, mockedInfectionDeck,
        mockedInfectionDiscardDeck, mockedRandom);
  }
  
  @Test
  public void testDrawEpidemicCardActionCauseOutbreakButNoGameOver() {
    Game mockedGame = EasyMock.createMock(Game.class);
    mockedGame.increaseInfectionRate();
    EasyMock.expectLastCall();
    Deck mockedInfectionDeck = EasyMock.createMock(Deck.class);
    EasyMock.expect(mockedGame.getInfectionDeck()).andStubReturn(mockedInfectionDeck);
    Card mockedInfectionCard = EasyMock.createMock(Card.class);
    EasyMock.expect(mockedInfectionDeck.drawLastCard()).andStubReturn(mockedInfectionCard);
    EasyMock.expect(mockedInfectionCard.getColor()).andReturn(CubeColor.BLUE);
    EasyMock.expect(mockedGame.getCubesLeftByColor(CubeColor.BLUE)).andStubReturn(5);
    EasyMock.expect(mockedInfectionCard.getName()).andReturn("City");
    City mockedInfectedCity = EasyMock.createMock(City.class);
    EasyMock.expect(mockedGame.getCity("City")).andReturn(mockedInfectedCity);
    EasyMock.expect(mockedGame.hasMedicAt(mockedInfectedCity)).andStubReturn(false);
    EasyMock.expect(mockedInfectedCity.getCubeCount(CubeColor.BLUE)).andStubReturn(3);
    mockedGame.decrementDiseaseCube(CubeColor.BLUE);
    EasyMock.expectLastCall();
    mockedGame.incrementOutbreaks();
    EasyMock.expectLastCall();
    EasyMock.expect(mockedGame.getOutbreaks()).andReturn(1);
    City mockedAdjacent = EasyMock.createMock(City.class);
    mockedAdjacent.addCube(CubeColor.BLUE);
    EasyMock.expectLastCall();
    EasyMock.expect(mockedGame.hasMedicAt(mockedAdjacent)).andStubReturn(false);
    List<City> adjacents = new ArrayList<>();
    adjacents.add(mockedAdjacent);
    EasyMock.expect(mockedInfectedCity.getAdjacentCities()).andReturn(adjacents);
    EasyMock.expect(mockedAdjacent.getCubeCount(CubeColor.BLUE)).andStubReturn(0);

    Deck mockedInfectionDiscardDeck = EasyMock.createMock(Deck.class);
    EasyMock.expect(mockedGame.getInfectionDiscardDeck()).andStubReturn(mockedInfectionDiscardDeck);
    mockedInfectionDiscardDeck.addCard(mockedInfectionCard);
    EasyMock.expectLastCall();
    Random mockedRandom = EasyMock.createMock(Random.class);
    mockedInfectionDiscardDeck.shuffle(mockedRandom);
    EasyMock.expectLastCall();
    mockedInfectionDeck.placeCardsOnTop(mockedInfectionDiscardDeck);
    EasyMock.expectLastCall();

    EasyMock.replay(mockedGame, mockedInfectedCity, mockedInfectionCard, mockedInfectionDeck,
        mockedInfectionDiscardDeck, mockedRandom,mockedAdjacent);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.epidemicCardDrawn(mockedRandom);

    EasyMock.verify(mockedGame, mockedInfectedCity, mockedInfectionCard, mockedInfectionDeck,
        mockedInfectionDiscardDeck, mockedRandom,mockedAdjacent);
  }


  @Test
  public void testDrawEpidemicCardAndDiseaseGotEradicated() {
    Game mockedGame = EasyMock.createMock(Game.class);
    mockedGame.increaseInfectionRate();
    EasyMock.expectLastCall();
    Deck mockedInfectionDeck = EasyMock.createMock(Deck.class);
    EasyMock.expect(mockedGame.getInfectionDeck()).andStubReturn(mockedInfectionDeck);
    Card mockedInfectionCard = EasyMock.createMock(Card.class);
    EasyMock.expect(mockedInfectionDeck.drawLastCard()).andReturn(mockedInfectionCard);
    EasyMock.expect(mockedInfectionCard.getColor()).andReturn(CubeColor.YELLOW);
    EasyMock.expect(mockedInfectionCard.getName()).andReturn("anything");
    EasyMock.expect(mockedGame.getCubesLeftByColor(CubeColor.YELLOW)).andReturn(24);
    EasyMock.expect(mockedGame.isDiseaseCured(CubeColor.YELLOW)).andReturn(true);
    City mockedCity = EasyMock.createMock(City.class);
    EasyMock.expect(mockedGame.getCity("anything")).andReturn(mockedCity);
    Random mockedRandom = EasyMock.createMock(Random.class);

    EasyMock.replay(mockedGame, mockedInfectionCard, mockedInfectionDeck, mockedRandom, mockedCity);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.epidemicCardDrawn(mockedRandom);

    EasyMock.verify(mockedGame, mockedInfectionCard, mockedInfectionDeck, mockedRandom, mockedCity);
  }

  @Test
  public void testDrawEpidemicCardAndMedicAndDiseaseCured() {
    Game mockedGame = EasyMock.createMock(Game.class);
    mockedGame.increaseInfectionRate();
    EasyMock.expectLastCall();
    Deck mockedInfectionDeck = EasyMock.createMock(Deck.class);
    EasyMock.expect(mockedGame.getInfectionDeck()).andStubReturn(mockedInfectionDeck);
    Card mockedInfectionCard = EasyMock.createMock(Card.class);
    EasyMock.expect(mockedInfectionDeck.drawLastCard()).andReturn(mockedInfectionCard);
    EasyMock.expect(mockedInfectionCard.getColor()).andReturn(CubeColor.YELLOW);
    EasyMock.expect(mockedGame.getCubesLeftByColor(CubeColor.YELLOW)).andReturn(20);
    EasyMock.expect(mockedInfectionCard.getName()).andReturn("City");
    City mockedInfectedCity = EasyMock.createMock(City.class);
    EasyMock.expect(mockedGame.getCity("City")).andReturn(mockedInfectedCity);
    EasyMock.expect(mockedGame.hasMedicAt(mockedInfectedCity)).andReturn(true);
    EasyMock.expect(mockedGame.isDiseaseCured(CubeColor.YELLOW)).andReturn(true);
    Random mockedRandom = EasyMock.createMock(Random.class);

    EasyMock.replay(mockedGame, mockedInfectionCard, mockedInfectionDeck, mockedInfectedCity,
        mockedRandom);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.epidemicCardDrawn(mockedRandom);

    EasyMock.verify(mockedGame, mockedInfectionCard, mockedInfectionDeck, mockedInfectedCity,
        mockedRandom);
  }

  @Test
  public void testPlayInfectorPhaseWithInfectionRateOfTwo() {
    Game mockedGame = EasyMock.createMock(Game.class);
    EasyMock.expect(mockedGame.getInfectionRate()).andReturn(2);
    Player mockedPlayer = EasyMock.createMock(Player.class);
    EasyMock.expect(mockedGame.getCurrentPlayer()).andReturn(mockedPlayer);
    EasyMock.expect(mockedGame.willPlayerSkipNextInfectorPhase(mockedPlayer)).andReturn(false);
    Deck mockedInfectionDeck = EasyMock.createMock(Deck.class);
    Card mockedInfectionCard = EasyMock.createMock(Card.class);
    for (int i = 0; i < 2; i++) {
      EasyMock.expect(mockedGame.getInfectionDeck()).andReturn(mockedInfectionDeck);
      EasyMock.expect(mockedInfectionDeck.drawCard()).andReturn(mockedInfectionCard);
      EasyMock.expect(mockedInfectionCard.getColor()).andReturn(CubeColor.RED);
      EasyMock.expect(mockedGame.getCubesLeftByColor(CubeColor.RED)).andReturn(24);
      EasyMock.expect(mockedGame.isDiseaseCured(CubeColor.RED)).andReturn(true);
    }

    EasyMock.replay(mockedGame, mockedPlayer, mockedInfectionCard, mockedInfectionDeck);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.playInfectorPhase();

    EasyMock.verify(mockedGame, mockedPlayer, mockedInfectionCard, mockedInfectionDeck);
  }

  @Test
  public void testPlayInfectorPhaseInOneQuietNight() {
    Game mockedGame = EasyMock.createMock(Game.class);
    Player mockedPlayer = EasyMock.createMock(Player.class);
    EasyMock.expect(mockedGame.getCurrentPlayer()).andStubReturn(mockedPlayer);
    EasyMock.expect(mockedGame.willPlayerSkipNextInfectorPhase(mockedPlayer)).andReturn(true);
    mockedGame.updatePlayerSkipNextInfectorPhase(mockedPlayer, false);
    EasyMock.expectLastCall();

    EasyMock.replay(mockedGame, mockedPlayer);

    GameLogic gameLogic = new GameLogic(mockedGame);
    gameLogic.playInfectorPhase();

    EasyMock.verify(mockedGame, mockedPlayer);
  }
}
