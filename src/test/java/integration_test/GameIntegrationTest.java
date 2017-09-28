package integration_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import pandemic_game.Constants.CubeColor;
import pandemic_game.Game;
import pandemic_game.GameLogic;
import pandemic_game.Player;
import pandemic_initialization.TestCityInitializer;
import pandemic_initialization.TestDeckInitializer;

public class GameIntegrationTest {

  @Test
  public void testSetUpTwoPlayers() {
    Game game = new Game(2, 4);
    GameLogic logic = new GameLogic(game);
    logic.initializeCityMap(new TestCityInitializer());
    logic.initializePlayerDeck(new TestDeckInitializer());
    logic.initializePlayers();
    for (int i = 0; i < 2; i++) {
      assertEquals("Atlanta", game.getPlayers().get(i).getCurrentCity().getName());
    }
  }

  @Test
  public void testGetRolesOnPlayer() {
    Game game = new Game(2, 4);

    GameLogic logic = new GameLogic(game);
    logic.initializeCityMap(new TestCityInitializer());
    logic.initializePlayerDeck(new TestDeckInitializer());
    logic.initializePlayers();
    List<Player> players = game.getPlayers();
    for (int i = 0; i < 2; i++) {
      assertTrue(players.get(i).getRole() != null);
    }
  }

  @Test
  public void testInitializePlayersToSkipNextInfectorPhase() {
    Game game = new Game(2, 4);
    GameLogic logic = new GameLogic(game);
    logic.initializeCityMap(new TestCityInitializer());
    logic.initializePlayerDeck(new TestDeckInitializer());
    logic.initializePlayers();
    logic.initializePlayersToSkipNextInfectorPhase();
    for (Player player : game.getPlayers()) {
      assertFalse(game.willPlayerSkipNextInfectorPhase(player));
    }
  }

  @Test
  public void testInitializeCurrentPlayer() {
    Game game = new Game(2, 4);
    GameLogic logic = new GameLogic(game);
    logic.initialize(new TestDeckInitializer(), new TestCityInitializer());
    assertEquals(game.getPlayers().get(1), game.getCurrentPlayer());
  }

  @Test
  public void testInitializeDiseaseCubes() {
    Game game = new Game(2, 4);
    GameLogic logic = new GameLogic(game);
    logic.initializeDiseaseCubes();
    assertEquals(24, game.getCubesLeftByColor(CubeColor.BLUE));
    assertEquals(24, game.getCubesLeftByColor(CubeColor.BLACK));
    assertEquals(24, game.getCubesLeftByColor(CubeColor.RED));
    assertEquals(24, game.getCubesLeftByColor(CubeColor.YELLOW));
  }

  @Test
  public void testInitializeOutbreaks() {
    Game game = new Game(2, 4);
    GameLogic logic = new GameLogic(game);
    logic.initialize(new TestDeckInitializer(), new TestCityInitializer());
    assertEquals(0, game.getOutbreaks());
  }

  @Test
  public void testInitializeInfectionRate() {
    Game game = new Game(2, 4);
    GameLogic logic = new GameLogic(game);
    logic.initialize(new TestDeckInitializer(), new TestCityInitializer());
    assertEquals(2, game.getInfectionRate());
  }

  @Test
  public void testInitializeCureMarker() {
    Game game = new Game(2, 4);
    GameLogic logic = new GameLogic(game);
    logic.initializeCureMarkers();

    for (int i = 0; i < 4; i++) {
      assertFalse(game.isDiseaseCured(CubeColor.BLUE));
      assertFalse(game.isDiseaseCured(CubeColor.BLACK));
      assertFalse(game.isDiseaseCured(CubeColor.RED));
      assertFalse(game.isDiseaseCured(CubeColor.YELLOW));
    }
  }

  @Test
  public void testInitializePlayerActionsLeft() {
    Game game = new Game(2, 4);
    GameLogic logic = new GameLogic(game);
    logic.initialize(new TestDeckInitializer(), new TestCityInitializer());

    assertEquals(4, game.getPlayerActionsLeft());
  }

  @Test
  public void testInitializeTotalResearchStationLeft() {
    Game game = new Game(2, 4);
    GameLogic logic = new GameLogic(game);
    logic.initialize(new TestDeckInitializer(), new TestCityInitializer());

    assertTrue(game.getCity("Atlanta").hasResearchStation());
    assertEquals(5, game.getTotalResearchStationLeft());
  }

  @Test
  public void testInitializePlayerDeckWithNoEpidemicCard() {
    Game game = new Game(2, 4);
    GameLogic logic = new GameLogic(game);
    logic.initializePlayerDeck(new TestDeckInitializer());
    assertEquals(53, game.getPlayerDeck().size());
  }

  @Test
  public void testInitializePlayerDeckWithEpidemicCard() {
    Game game = new Game(2, 4);
    GameLogic logic = new GameLogic(game);
    logic.initialize(new TestDeckInitializer(), new TestCityInitializer());
    assertEquals(49, game.getPlayerDeck().size());
  }

  @Test
  public void testInitializePlayerDiscardDeck() {
    Game game = new Game(2, 4);
    GameLogic logic = new GameLogic(game);
    logic.initializePlayerDiscardDeck(new TestDeckInitializer());

    assertEquals(0, game.getPlayerDiscardDeck().size());
  }

  @Test
  public void testFormInitialHandsWithTwoPlayers() {
    Game game = new Game(2, 4);
    GameLogic logic = new GameLogic(game);
    logic.initializeCityMap(new TestCityInitializer());
    logic.initializePlayerDeck(new TestDeckInitializer());
    logic.initializePlayers();

    for (int i = 0; i < 2; i++) {
      assertEquals(4, game.getPlayers().get(i).getHandsSize());
    }
  }

  @Test
  public void testFormInitialHandsWithFourPlayers() {
    Game game = new Game(4, 4);
    GameLogic logic = new GameLogic(game);
    logic.initializeCityMap(new TestCityInitializer());
    logic.initializePlayerDeck(new TestDeckInitializer());
    logic.initializePlayers();

    for (int i = 0; i < 4; i++) {
      assertEquals(2, game.getPlayers().get(i).getHandsSize());
    }
  }

  @Test
  public void testInitializeInfectionDeck() {
    Game game = new Game(2, 4);
    GameLogic logic = new GameLogic(game);
    logic.initializeInfectionDeck(new TestDeckInitializer());
    assertEquals(48, game.getInfectionDeck().size());
  }

  @Test
  public void testInitializeInfectionDiscardDeck() {
    Game game = new Game(2, 4);
    GameLogic logic = new GameLogic(game);
    logic.initializeInfectionDiscardDeck(new TestDeckInitializer());
    assertEquals(0, game.getInfectionDiscardDeck().size());
  }

  @Test
  public void testInfectNineCitiesCheckDeck() {
    Game game = new Game(2, 4);
    GameLogic logic = new GameLogic(game);
    logic.initializeCityMap(new TestCityInitializer());
    logic.initializeInfectionDeck(new TestDeckInitializer());
    logic.initializeInfectionDiscardDeck(new TestDeckInitializer());
    logic.initializeDiseaseCubes();
    logic.infectNineCities();

    assertEquals(39, game.getInfectionDeck().size());
    assertEquals(9, game.getInfectionDiscardDeck().size());
  }

  @Test
  public void testInitialization() {
    Game game = new Game(2, 4);
    GameLogic logic = new GameLogic(game);
    logic.initialize(new TestDeckInitializer(), new TestCityInitializer());

    assertFalse(game.getCityMap().isEmpty());
    assertTrue(game.getPlayerDeck().size() != 0);
    assertTrue(game.getPlayerDiscardDeck().size() == 0);
    assertTrue(game.getInfectionDeck().size() != 0);
    assertEquals(9, game.getInfectionDiscardDeck().size());
    assertEquals(2, game.getPlayers().size());
    Player p1 = game.getCurrentPlayer();
    assertFalse(game.willPlayerSkipNextInfectorPhase(p1));
    assertFalse(game.getCuredDiseases().isEmpty());
    assertEquals(0, game.getOutbreaks());
    assertEquals(0, game.getInfectionRateIndex());
  }
}
