package pandemic_test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;

import pandemic_game.Card;
import pandemic_game.Constants.CubeColor;
import pandemic_game.Deck;
import pandemic_game.Game;
import pandemic_game.GameLogic;
import pandemic_game.Player;
import pandemic_initialization.TestCityInitializer;
import pandemic_initialization.TestDeckInitializer;

public class GameInitializationTest {

  @Test
  public void testGetGame() {
    Game mockedGame = EasyMock.createMock(Game.class);
    EasyMock.replay(mockedGame);

    GameLogic logic = new GameLogic(mockedGame);
    assertEquals(mockedGame, logic.getGame());
  }

  @Test
  public void testInfectThreeCitiesEachThreeCubes() {
    Deck mockedDeck = EasyMock.createMock(Deck.class);
    Card mockedCard = EasyMock.createMock(Card.class);
    EasyMock.expect(mockedDeck.drawCard()).andStubReturn(mockedCard);
    EasyMock.expect(mockedCard.getColor()).andStubReturn(CubeColor.BLUE);
    EasyMock.expect(mockedCard.getName()).andReturn("Atlanta");
    EasyMock.expect(mockedCard.getName()).andReturn("Chicago");
    EasyMock.expect(mockedCard.getName()).andReturn("Montreal");

    EasyMock.replay(mockedDeck, mockedCard);

    Game game = new Game(2, 4);
    GameLogic logic = new GameLogic(game);
    logic.initializeCityMap(new TestCityInitializer());
    logic.initializeInfectionDiscardDeck(new TestDeckInitializer());
    game.setInfectionDeck(mockedDeck);
    logic.initializeDiseaseCubes();
    logic.infectThreeCities(3);
    assertEquals(3, game.getCity("Atlanta").getCubeCount(CubeColor.BLUE));
    assertEquals(3, game.getCity("Chicago").getCubeCount(CubeColor.BLUE));
    assertEquals(3, game.getCity("Montreal").getCubeCount(CubeColor.BLUE));
    assertEquals(15, game.getCubesLeftByColor(CubeColor.BLUE));
    
    EasyMock.verify(mockedDeck, mockedCard);
  }

  @Test
  public void testInfectThreeCitiesEachTwoCubes() {
    Deck mockedDeck = EasyMock.createMock(Deck.class);
    Card mockedCard = EasyMock.createMock(Card.class);
    EasyMock.expect(mockedDeck.drawCard()).andStubReturn(mockedCard);
    EasyMock.expect(mockedCard.getColor()).andStubReturn(CubeColor.BLUE);
    EasyMock.expect(mockedCard.getName()).andReturn("Atlanta");
    EasyMock.expect(mockedCard.getName()).andReturn("Chicago");
    EasyMock.expect(mockedCard.getName()).andReturn("Montreal");

    EasyMock.replay(mockedDeck, mockedCard);

    Game game = new Game(2, 4);
    GameLogic logic = new GameLogic(game);
    logic.initializeCityMap(new TestCityInitializer());
    logic.initializeInfectionDiscardDeck(new TestDeckInitializer());
    game.setInfectionDeck(mockedDeck);
    logic.initializeDiseaseCubes();
    logic.infectThreeCities(2);
    assertEquals(2, game.getCity("Atlanta").getCubeCount(CubeColor.BLUE));
    assertEquals(2, game.getCity("Chicago").getCubeCount(CubeColor.BLUE));
    assertEquals(2, game.getCity("Montreal").getCubeCount(CubeColor.BLUE));
    assertEquals(18, game.getCubesLeftByColor(CubeColor.BLUE));
    
    EasyMock.verify(mockedDeck, mockedCard);
  }

  @Test
  public void testInfectThreeCitiesEachOneCube() {
    Deck mockedDeck = EasyMock.createMock(Deck.class);
    Card mockedCard = EasyMock.createMock(Card.class);
    EasyMock.expect(mockedDeck.drawCard()).andStubReturn(mockedCard);
    EasyMock.expect(mockedCard.getColor()).andStubReturn(CubeColor.BLUE);
    EasyMock.expect(mockedCard.getName()).andReturn("Atlanta");
    EasyMock.expect(mockedCard.getName()).andReturn("Chicago");
    EasyMock.expect(mockedCard.getName()).andReturn("Montreal");

    EasyMock.replay(mockedDeck, mockedCard);

    Game game = new Game(2, 4);
    GameLogic logic = new GameLogic(game);
    logic.initializeCityMap(new TestCityInitializer());
    logic.initializeInfectionDiscardDeck(new TestDeckInitializer());
    game.setInfectionDeck(mockedDeck);
    logic.initializeDiseaseCubes();
    logic.infectThreeCities(1);
    assertEquals(1, game.getCity("Atlanta").getCubeCount(CubeColor.BLUE));
    assertEquals(1, game.getCity("Chicago").getCubeCount(CubeColor.BLUE));
    assertEquals(1, game.getCity("Montreal").getCubeCount(CubeColor.BLUE));
    assertEquals(21, game.getCubesLeftByColor(CubeColor.BLUE));

    EasyMock.verify(mockedDeck, mockedCard);
  }

  @Test
  public void testIndexOfPlayerHasTopPopulation() {
    Player mockedPlayer = EasyMock.createMock(Player.class);
    EasyMock.expect(mockedPlayer.getTopPopulationInHands()).andReturn(0);
    EasyMock.expect(mockedPlayer.getTopPopulationInHands()).andReturn(1);

    EasyMock.replay(mockedPlayer);

    Game game = new Game(2, 4);
    List<Player> players = new ArrayList<>();
    players.add(mockedPlayer);
    players.add(mockedPlayer);
    game.setPlayers(players);
    GameLogic logic = new GameLogic(game);
    assertEquals(1, logic.indexOfPlayerHasTopPopulation());

    EasyMock.verify(mockedPlayer);
  }
}
