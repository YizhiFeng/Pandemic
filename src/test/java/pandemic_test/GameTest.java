package pandemic_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.easymock.EasyMock;
import org.junit.Test;

import pandemic_game.City;
import pandemic_game.Constants.CubeColor;
import pandemic_game.Constants.Role;
import pandemic_game.Deck;
import pandemic_game.Game;
import pandemic_game.Player;

public class GameTest {

  @Test
  public void testGetTotalPlayersWithTwoPlayers() {
    Game game = new Game(2, 4);
    game.setTotalPlayers(2);

    assertEquals(2, game.getTotalPlayers());
  }

  @Test
  public void testGetTotalPlayersWithFourPlayers() {
    Game game = new Game(4, 4);
    game.setTotalPlayers(4);

    assertEquals(4, game.getTotalPlayers());
  }

  @Test
  public void testGetNumOfEpidemicCard() {
    Game game = new Game(2, 4);
    assertEquals(4, game.getNumberOfEpidemicCard());
  }

  @Test
  public void testGetAndSetCurrentPlayerIndex() {
    Game game = new Game(4, 4);
    game.setCurrentPlayerIndex(2);
    assertEquals(2, game.getCurrentPlayerIndex());
  }

  @Test
  public void testGetAndSetCurrentPlayerIndexWith2Players() {
    Game game = new Game(2, 4);
    game.setCurrentPlayerIndex(1);
    assertEquals(1, game.getCurrentPlayerIndex());
  }

  @Test
  public void testGetNextPlayer() {
    Player mockedPlayer1 = EasyMock.createMock(Player.class);
    Player mockedPlayer2 = EasyMock.createMock(Player.class);

    EasyMock.replay(mockedPlayer1, mockedPlayer2);

    List<Player> players = new ArrayList<>();
    players.add(mockedPlayer1);
    players.add(mockedPlayer2);
    Game game = new Game(2, 4);
    game.setPlayers(players);
    assertEquals(mockedPlayer2, game.getNextPlayer());

    EasyMock.verify(mockedPlayer1, mockedPlayer2);
  }

  @Test
  public void testGetNextPlayerWhoseIndexIsZero() {
    Player mockedPlayer1 = EasyMock.createMock(Player.class);
    Player mockedPlayer2 = EasyMock.createMock(Player.class);

    EasyMock.replay(mockedPlayer1, mockedPlayer2);

    List<Player> players = new ArrayList<>();
    players.add(mockedPlayer1);
    players.add(mockedPlayer2);
    Game game = new Game(2, 4);
    game.setPlayers(players);
    game.setCurrentPlayerIndex(1);
    assertEquals(mockedPlayer1, game.getNextPlayer());

    EasyMock.verify(mockedPlayer1, mockedPlayer2);
  }

  @Test
  public void testGetCurrentPlayer() {
    Player mockedPlayer1 = EasyMock.createMock(Player.class);
    Player mockedPlayer2 = EasyMock.createMock(Player.class);

    EasyMock.replay(mockedPlayer1, mockedPlayer2);

    List<Player> players = new ArrayList<>();
    players.add(mockedPlayer1);
    players.add(mockedPlayer2);
    Game game = new Game(2, 4);
    game.setPlayers(players);
    game.setCurrentPlayerIndex(0);

    assertEquals(mockedPlayer1, game.getCurrentPlayer());
  }

  @Test
  public void testGetPlayerAtIndex() {
    Player mockedPlayer1 = EasyMock.createMock(Player.class);
    Player mockedPlayer2 = EasyMock.createMock(Player.class);

    EasyMock.replay(mockedPlayer1, mockedPlayer2);

    List<Player> players = new ArrayList<>();
    players.add(mockedPlayer1);
    players.add(mockedPlayer2);
    Game game = new Game(2, 4);
    game.setPlayers(players);

    assertEquals(mockedPlayer1, game.getPlayerAtIndex(0));
  }

  @Test
  public void testGetCityMap() {
    City mockedCity = EasyMock.createMock(City.class);
    Map<String, City> cityMap = new HashMap<>();
    cityMap.put("Atlanta", mockedCity);
    cityMap.put("Chicago", mockedCity);
    Game game = new Game(2, 4);
    game.setCityMap(cityMap);

    assertTrue(game.getCityMap().containsKey("Atlanta"));
    assertTrue(game.getCityMap().containsKey("Chicago"));
  }

  @Test
  public void testGetCityByName() {
    City mockedCity = EasyMock.createMock(City.class);
    Map<String, City> cityMap = new HashMap<>();
    cityMap.put("Atlanta", mockedCity);
    Game game = new Game(2, 4);
    game.setCityMap(cityMap);

    assertEquals(mockedCity, game.getCity("Atlanta"));
  }

  @Test
  public void testGetAndSetGameOver() {
    Game game = new Game(2, 4);
    assertFalse(game.isGameOver());

    game.setGameOver(true);
    assertTrue(game.isGameOver());
  }

  @Test
  public void testGetPlayers() {
    Player mockedPlayer1 = EasyMock.createMock(Player.class);
    Player mockedPlayer2 = EasyMock.createMock(Player.class);

    EasyMock.replay(mockedPlayer1, mockedPlayer2);

    List<Player> players = new ArrayList<>();
    players.add(mockedPlayer1);
    players.add(mockedPlayer2);
    Game game = new Game(2, 4);
    game.setPlayers(players);
    assertTrue(game.getPlayers().contains(mockedPlayer1));
    assertTrue(game.getPlayers().contains(mockedPlayer2));

    EasyMock.verify(mockedPlayer1, mockedPlayer2);
  }

  @Test
  public void testGetAndSetOutbreaks() {
    Game game = new Game(2, 4);
    assertEquals(0, game.getOutbreaks());

    game.setOutbreaks(3);
    assertEquals(3, game.getOutbreaks());
  }

  @Test
  public void testIncrementOutbreaks() {
    Game game = new Game(2, 4);
    assertEquals(0, game.getOutbreaks());

    game.incrementOutbreaks();
    assertEquals(1, game.getOutbreaks());
  }

  @Test
  public void testGetAndSetInfectionRateIndex() {
    Game game = new Game(2, 4);
    assertEquals(0, game.getInfectionRateIndex());

    game.setInfectionRateIndex(2);
    assertEquals(2, game.getInfectionRateIndex());
  }

  @Test
  public void testGetAndIncrementInfectionRate() {
    Game game = new Game(2, 4);
    assertEquals(2, game.getInfectionRate());
    game.increaseInfectionRate();
    assertEquals(2, game.getInfectionRate());
    game.increaseInfectionRate();
    assertEquals(3, game.getInfectionRate());
  }

  @Test
  public void testGetAndSetPlayerDeck() {
    Deck playerDeck = EasyMock.createMock(Deck.class);
    EasyMock.replay(playerDeck);
    Game game = new Game(2, 4);

    game.setPlayerDeck(playerDeck);
    assertEquals(playerDeck, game.getPlayerDeck());
    EasyMock.verify(playerDeck);
  }

  @Test
  public void testGetAndSetPlayerDiscardDeck() {
    Deck playerDiscardDeck = EasyMock.createMock(Deck.class);
    EasyMock.replay(playerDiscardDeck);
    Game game = new Game(2, 4);

    game.setPlayerDiscardDeck(playerDiscardDeck);
    assertEquals(playerDiscardDeck, game.getPlayerDiscardDeck());
  }

  @Test
  public void testGetAndSetInfectionDeck() {
    Deck infectiondDeck = EasyMock.createMock(Deck.class);
    EasyMock.replay(infectiondDeck);
    Game game = new Game(2, 4);

    game.setInfectionDeck(infectiondDeck);
    assertEquals(infectiondDeck, game.getInfectionDeck());
  }

  @Test
  public void testGetAndSetInfectionDiscardDeck() {
    Deck infectiondDiscardDeck = EasyMock.createMock(Deck.class);
    EasyMock.replay(infectiondDiscardDeck);
    Game game = new Game(2, 4);

    game.setInfectionDiscardDeck(infectiondDiscardDeck);
    assertEquals(infectiondDiscardDeck, game.getInfectionDiscardDeck());
  }

  @Test
  public void testGetAndSetPlayerActionsLeft() {
    Game game = new Game(2, 4);

    game.setPlayerActionsLeft(2);
    assertEquals(2, game.getPlayerActionsLeft());
  }

  @Test
  public void testGetAndSetPlayerActionsLeftWith3() {
    Game game = new Game(2, 4);

    game.setPlayerActionsLeft(3);
    assertEquals(3, game.getPlayerActionsLeft());
  }

  @Test
  public void testDecrementPlayerActionsLeft() {
    Game game = new Game(2, 4);
    game.setPlayerActionsLeft(3);
    game.decrementPlayerActionsLeft();
    assertEquals(2, game.getPlayerActionsLeft());
  }

  @Test
  public void testGetAndSetTotalResearchStationLeft() {
    Game game = new Game(2, 4);

    game.setTotalResearchStationLeft(3);
    assertEquals(3, game.getTotalResearchStationLeft());
  }

  @Test
  public void testGetAndSetTotalResearchStationLeftWith5() {
    Game game = new Game(2, 4);

    game.setTotalResearchStationLeft(5);
    assertEquals(5, game.getTotalResearchStationLeft());
  }

  @Test
  public void testDecrementResearchStationsLeft() {
    Game game = new Game(2, 4);
    game.setTotalResearchStationLeft(4);
    game.decrementResearchStationLeft();
    assertEquals(3, game.getTotalResearchStationLeft());
  }

  @Test
  public void testGetAndSetDiseaseCubesByColor() {
    Map<CubeColor, Integer> cubes = new HashMap<>();
    cubes.put(CubeColor.BLACK, 3);
    cubes.put(CubeColor.BLUE, 2);
    cubes.put(CubeColor.YELLOW, 20);
    cubes.put(CubeColor.RED, 13);

    Game game = new Game(2, 4);
    game.setCubes(cubes);
    assertEquals(3, game.getCubesLeftByColor(CubeColor.BLACK));
    assertEquals(2, game.getCubesLeftByColor(CubeColor.BLUE));
    assertEquals(20, game.getCubesLeftByColor(CubeColor.YELLOW));
    assertEquals(13, game.getCubesLeftByColor(CubeColor.RED));
  }

  @Test
  public void testSetDiseaseCubesLeft() {
    Map<CubeColor, Integer> cubes = new HashMap<>();

    Game game = new Game(2, 4);
    game.setCubes(cubes);
    game.setDiseaseCubesLeft(CubeColor.BLACK, 1);
    game.setDiseaseCubesLeft(CubeColor.BLUE, 0);
    game.setDiseaseCubesLeft(CubeColor.YELLOW, 10);
    game.setDiseaseCubesLeft(CubeColor.RED, 15);
    assertEquals(1, game.getCubesLeftByColor(CubeColor.BLACK));
    assertEquals(0, game.getCubesLeftByColor(CubeColor.BLUE));
    assertEquals(10, game.getCubesLeftByColor(CubeColor.YELLOW));
    assertEquals(15, game.getCubesLeftByColor(CubeColor.RED));
  }

  @Test
  public void testDecrementDiseaseCubeBlack() {
    Game game = new Game(2, 4);
    Map<CubeColor, Integer> cubes = new HashMap<>();
    cubes.put(CubeColor.BLACK, 5);
    game.setCubes(cubes);

    game.decrementDiseaseCube(CubeColor.BLACK);
    assertEquals(4, game.getCubesLeftByColor(CubeColor.BLACK));
  }

  @Test
  public void testDecrementDiseaseCubeBlue() {
    Game game = new Game(2, 4);
    Map<CubeColor, Integer> cubes = new HashMap<>();
    cubes.put(CubeColor.BLUE, 2);
    game.setCubes(cubes);

    game.decrementDiseaseCube(CubeColor.BLUE);
    assertEquals(1, game.getCubesLeftByColor(CubeColor.BLUE));
  }

  @Test
  public void testGetAndSetCuredDiseases() {
    Game game = new Game(2, 4);

    Map<CubeColor, Boolean> curedDiseases = new HashMap<>();
    game.setCuredDiseases(curedDiseases);
    assertEquals(curedDiseases, game.getCuredDiseases());
  }

  @Test
  public void testGetCuredDiseasesByColor() {
    Game game = new Game(2, 4);
    Map<CubeColor, Boolean> curedDiseases = new HashMap<>();
    curedDiseases.put(CubeColor.BLACK, true);
    game.setCuredDiseases(curedDiseases);

    assertTrue(game.isDiseaseCured(CubeColor.BLACK));
  }

  @Test
  public void testGetCuredDiseasesByColorFalse() {
    Game game = new Game(2, 4);
    Map<CubeColor, Boolean> curedDiseases = new HashMap<>();
    curedDiseases.put(CubeColor.BLUE, false);
    game.setCuredDiseases(curedDiseases);

    assertFalse(game.isDiseaseCured(CubeColor.BLUE));
  }

  @Test
  public void testCureDiesease() {
    Game game = new Game(2, 4);
    Map<CubeColor, Boolean> curedDiseases = new HashMap<>();
    curedDiseases.put(CubeColor.BLUE, false);
    game.setCuredDiseases(curedDiseases);

    game.cureDisease(CubeColor.BLUE);
    assertTrue(game.isDiseaseCured(CubeColor.BLUE));
  }

  @Test
  public void testGetNumberOfEpidemicCard() {
    Game game = new Game(2, 4);

    assertEquals(4, game.getNumberOfEpidemicCard());
  }

  @Test
  public void testGetNumberOfEpidemicCardWith5() {
    Game game = new Game(2, 5);

    assertEquals(5, game.getNumberOfEpidemicCard());
  }

  @Test
  public void testSetPlayerToSkipAndGet() {
    Player mockedPlayer = EasyMock.createMock(Player.class);
    Player mockedPlayer2 = EasyMock.createMock(Player.class);
    EasyMock.replay(mockedPlayer, mockedPlayer2);

    Game game = new Game(2, 4);
    Map<Player, Boolean> playersToSkipNextInfectorPhase = new HashMap<>();
    playersToSkipNextInfectorPhase.put(mockedPlayer, true);
    playersToSkipNextInfectorPhase.put(mockedPlayer2, false);

    game.setPlayersToSkipNextInfectorPhase(playersToSkipNextInfectorPhase);

    assertTrue(game.willPlayerSkipNextInfectorPhase(mockedPlayer));
    assertFalse(game.willPlayerSkipNextInfectorPhase(mockedPlayer2));
    EasyMock.verify(mockedPlayer, mockedPlayer2);
  }

  @Test
  public void testUpdatePlayerSkillNectInfectorPhase() {
    Player mockedPlayer = EasyMock.createMock(Player.class);
    Player mockedPlayer2 = EasyMock.createMock(Player.class);
    EasyMock.replay(mockedPlayer, mockedPlayer2);

    Game game = new Game(2, 4);
    Map<Player, Boolean> playersToSkipNextInfectorPhase = new HashMap<>();
    playersToSkipNextInfectorPhase.put(mockedPlayer, true);
    playersToSkipNextInfectorPhase.put(mockedPlayer2, false);
    game.setPlayersToSkipNextInfectorPhase(playersToSkipNextInfectorPhase);

    game.updatePlayerSkipNextInfectorPhase(mockedPlayer, false);
    game.updatePlayerSkipNextInfectorPhase(mockedPlayer2, true);

    assertFalse(game.willPlayerSkipNextInfectorPhase(mockedPlayer));
    assertTrue(game.willPlayerSkipNextInfectorPhase(mockedPlayer2));
    EasyMock.verify(mockedPlayer, mockedPlayer2);

  }

  @Test
  public void testChangeTurn() {
    Player mockedPlayer1 = EasyMock.createMock(Player.class);
    Player mockedPlayer2 = EasyMock.createMock(Player.class);

    EasyMock.replay(mockedPlayer1, mockedPlayer2);

    List<Player> players = new ArrayList<>();
    players.add(mockedPlayer1);
    players.add(mockedPlayer2);
    Game game = new Game(2, 4);
    game.setPlayers(players);
    game.setCurrentPlayerIndex(0);
    game.setPlayerActionsLeft(0);

    game.changeTurn();
    assertEquals(mockedPlayer2, game.getCurrentPlayer());
    assertEquals(1, game.getCurrentPlayerIndex());
    assertEquals(4, game.getPlayerActionsLeft());
  }

  @Test
  public void testChangeTurnOnLastIndex() {
    Player mockedPlayer1 = EasyMock.createMock(Player.class);
    Player mockedPlayer2 = EasyMock.createMock(Player.class);

    EasyMock.replay(mockedPlayer1, mockedPlayer2);

    List<Player> players = new ArrayList<>();
    players.add(mockedPlayer1);
    players.add(mockedPlayer2);
    Game game = new Game(2, 4);
    game.setPlayers(players);
    game.setCurrentPlayerIndex(1);
    game.setPlayerActionsLeft(0);

    game.changeTurn();
    assertEquals(mockedPlayer1, game.getCurrentPlayer());
    assertEquals(0, game.getCurrentPlayerIndex());
    assertEquals(4, game.getPlayerActionsLeft());
  }

  @Test
  public void testChangeTurnAfterThreeActionsDone() {
    Player mockedPlayer1 = EasyMock.createMock(Player.class);
    Player mockedPlayer2 = EasyMock.createMock(Player.class);

    EasyMock.replay(mockedPlayer1, mockedPlayer2);

    List<Player> players = new ArrayList<>();
    players.add(mockedPlayer1);
    players.add(mockedPlayer2);
    Game game = new Game(2, 4);
    game.setPlayers(players);
    game.setCurrentPlayerIndex(1);
    game.setPlayerActionsLeft(1);

    game.changeTurn();
    assertEquals(mockedPlayer1, game.getCurrentPlayer());
    assertEquals(0, game.getCurrentPlayerIndex());
    assertEquals(4, game.getPlayerActionsLeft());
  }

  @Test
  public void testHasPlayerTrueCase() {
    City mockedCity1 = EasyMock.createMock(City.class);
    City mockedCity2 = EasyMock.createMock(City.class);
    Player mockedPlayer1 = EasyMock.createMock(Player.class);
    EasyMock.expect(mockedPlayer1.getCurrentCity()).andReturn(mockedCity1);
    Player mockedPlayer2 = EasyMock.createMock(Player.class);
    EasyMock.expect(mockedPlayer2.getCurrentCity()).andReturn(mockedCity2);

    EasyMock.replay(mockedPlayer1, mockedPlayer2, mockedCity1, mockedCity2);

    List<Player> players = new ArrayList<>();
    players.add(mockedPlayer1);
    players.add(mockedPlayer2);
    Game game = new Game(2, 4);
    game.setPlayers(players);

    assertTrue(game.hasPlayers(mockedCity2));
    EasyMock.verify(mockedPlayer1, mockedPlayer2, mockedCity1, mockedCity2);
  }

  @Test
  public void testHasPlayerFalseCase() {
    City mockedCity1 = EasyMock.createMock(City.class);
    City mockedCity2 = EasyMock.createMock(City.class);
    City mockedCity3 = EasyMock.createMock(City.class);
    Player mockedPlayer1 = EasyMock.createMock(Player.class);
    EasyMock.expect(mockedPlayer1.getCurrentCity()).andReturn(mockedCity1);
    Player mockedPlayer2 = EasyMock.createMock(Player.class);
    EasyMock.expect(mockedPlayer2.getCurrentCity()).andReturn(mockedCity2);

    EasyMock.replay(mockedPlayer1, mockedPlayer2, mockedCity1, mockedCity2, mockedCity3);

    List<Player> players = new ArrayList<>();
    players.add(mockedPlayer1);
    players.add(mockedPlayer2);
    Game game = new Game(2, 4);
    game.setPlayers(players);

    assertFalse(game.hasPlayers(mockedCity3));
    EasyMock.verify(mockedPlayer1, mockedPlayer2, mockedCity1, mockedCity2, mockedCity3);
  }

  @Test
  public void testHasMedicAtCityTrue() {
    City mockedCity1 = EasyMock.createMock(City.class);
    City mockedCity2 = EasyMock.createMock(City.class);

    Player mockedPlayer1 = EasyMock.createMock(Player.class);
    EasyMock.expect(mockedPlayer1.getCurrentCity()).andReturn(mockedCity1);
    EasyMock.expect(mockedPlayer1.getRole()).andStubReturn(Role.Medic);
    Player mockedPlayer2 = EasyMock.createMock(Player.class);
    EasyMock.expect(mockedPlayer2.getCurrentCity()).andStubReturn(mockedCity2);
    EasyMock.expect(mockedPlayer2.getRole()).andStubReturn(Role.Dispatcher);
    EasyMock.replay(mockedCity1, mockedCity2, mockedPlayer1, mockedPlayer2);

    List<Player> players = new ArrayList<>();
    players.add(mockedPlayer1);
    players.add(mockedPlayer2);

    Game game = new Game(2, 4);
    game.setPlayers(players);
    assertTrue(game.hasMedicAt(mockedCity1));
    EasyMock.verify(mockedCity1, mockedCity2, mockedPlayer1, mockedPlayer2);
  }

  @Test
  public void testHasMedicAtCityFalse() {
    City mockedCity = EasyMock.createMock(City.class);
    Player mockedPlayer = EasyMock.createMock(Player.class);
    EasyMock.expect(mockedPlayer.getRole()).andReturn(Role.Dispatcher);
    EasyMock.replay(mockedCity, mockedPlayer);

    List<Player> players = new ArrayList<>();
    players.add(mockedPlayer);

    Game game = new Game(2, 4);
    game.setPlayers(players);
    assertFalse(game.hasMedicAt(mockedCity));
    EasyMock.verify(mockedCity, mockedPlayer);
  }

  @Test
  public void testIncreaseInfectionRateAtHighestRate() {
    Game game = new Game(2, 4);
    game.setInfectionRateIndex(5);
    game.increaseInfectionRate();
    assertEquals(5, game.getInfectionRateIndex());
  }
  
  @Test
  public void testCheckTurnOverWithNoActionLeft() {
    Game game = new Game(2, 4);
    game.setPlayerActionsLeft(0);
    
    assertTrue(game.checkTurnOver());
  }
  
  @Test
  public void testCheckTurnOverWithOneActionLeft() {
    Game game = new Game(2, 4);
    game.setPlayerActionsLeft(1);
    
    assertFalse(game.checkTurnOver());
  }
}