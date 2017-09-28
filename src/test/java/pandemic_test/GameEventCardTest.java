package pandemic_test;

import java.util.ArrayList;
import java.util.List;

import org.easymock.EasyMock;
import org.junit.Test;

import pandemic_exception.InvalidActionException;
import pandemic_exception.NotEnoughResearchStationsException;
import pandemic_game.Card;
import pandemic_game.City;
import pandemic_game.Deck;
import pandemic_game.Game;
import pandemic_game.GameLogic;
import pandemic_game.Player;

public class GameEventCardTest {

  @Test
  public void testPlayEventCardAirLift() {
    Player mockedPlayer = EasyMock.createMock(Player.class);
    City mockedCity = EasyMock.createMock(City.class);
    mockedPlayer.setCurrentCity(mockedCity);
    EasyMock.expectLastCall();
    Card mockedEventCard = EasyMock.createMock(Card.class);
    EasyMock.expect(mockedPlayer.removeCard(mockedEventCard)).andReturn(mockedEventCard);
    Game mockedGame = EasyMock.createMock(Game.class);
    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);
    EasyMock.expect(mockedGame.getPlayerDiscardDeck()).andReturn(mockedPlayerDiscardDeck);
    mockedPlayerDiscardDeck.addCard(mockedEventCard);
    EasyMock.expectLastCall();
    EasyMock.replay(mockedPlayer, mockedCity, mockedEventCard, mockedGame, mockedPlayerDiscardDeck);

    GameLogic logic = new GameLogic(mockedGame);
    logic.playAirlift(mockedPlayer, mockedEventCard, mockedPlayer, mockedCity);

    EasyMock.verify(mockedPlayer, mockedCity, mockedEventCard, mockedGame, mockedPlayerDiscardDeck);
  }

  @Test
  public void testPlayEventCardOneQuietNight() {
    Player mockedPlayer = EasyMock.createMock(Player.class);
    Player nextPlayer = EasyMock.createMock(Player.class);
    Card mockedEventCard = EasyMock.createMock(Card.class);
    EasyMock.expect(mockedPlayer.removeCard(mockedEventCard)).andReturn(mockedEventCard);

    Game mockedGame = EasyMock.createMock(Game.class);
    mockedGame.updatePlayerSkipNextInfectorPhase(nextPlayer, true);
    EasyMock.expectLastCall();
    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);
    EasyMock.expect(mockedGame.getPlayerDiscardDeck()).andReturn(mockedPlayerDiscardDeck);
    mockedPlayerDiscardDeck.addCard(mockedEventCard);
    EasyMock.expectLastCall();
    EasyMock.replay(mockedPlayer, mockedEventCard, mockedGame, nextPlayer, mockedPlayerDiscardDeck);

    GameLogic logic = new GameLogic(mockedGame);
    logic.playOneQuietNight(mockedPlayer, mockedEventCard, nextPlayer);

    EasyMock.verify(mockedPlayer, mockedEventCard, mockedGame, nextPlayer, mockedPlayerDiscardDeck);
  }

  @Test
  public void testPlayEventCardResilientPopulation() {
    Card mockedInfectionCard = EasyMock.createMock(Card.class);
    Deck mockedInfectionDiscardDeck = EasyMock.createMock(Deck.class);

    EasyMock.expect(mockedInfectionDiscardDeck.removeCard(mockedInfectionCard)).andReturn(true);
    Player mockedPlayer = EasyMock.createMock(Player.class);
    Card mockedEventCard = EasyMock.createMock(Card.class);
    EasyMock.expect(mockedPlayer.removeCard(mockedEventCard)).andReturn(mockedEventCard);

    Game mockedGame = EasyMock.createMock(Game.class);
    EasyMock.expect(mockedGame.getInfectionDiscardDeck()).andReturn(mockedInfectionDiscardDeck);
    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);
    EasyMock.expect(mockedGame.getPlayerDiscardDeck()).andReturn(mockedPlayerDiscardDeck);
    mockedPlayerDiscardDeck.addCard(mockedEventCard);
    EasyMock.expectLastCall();
    EasyMock.replay(mockedInfectionCard, mockedInfectionDiscardDeck, mockedPlayer, mockedEventCard,
        mockedGame, mockedPlayerDiscardDeck);

    GameLogic logic = new GameLogic(mockedGame);
    logic.playResilientPopulation(mockedPlayer, mockedEventCard, mockedInfectionCard);

    EasyMock.verify(mockedInfectionCard, mockedInfectionDiscardDeck, mockedPlayer, mockedEventCard,
        mockedGame, mockedPlayerDiscardDeck);
  }

  @Test
  public void testPlayEventCardGovernmentGrant() {
    City mockedCity = EasyMock.createMock(City.class);
    EasyMock.expect(mockedCity.hasResearchStation()).andReturn(false);
    mockedCity.addResearchStation();
    EasyMock.expectLastCall();
    Player mockedPlayer = EasyMock.createMock(Player.class);
    Card mockedEventCard = EasyMock.createMock(Card.class);
    EasyMock.expect(mockedPlayer.removeCard(mockedEventCard)).andReturn(mockedEventCard);
    Game mockedGame = EasyMock.createMock(Game.class);
    mockedGame.decrementResearchStationLeft();
    EasyMock.expectLastCall();
    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);
    EasyMock.expect(mockedGame.getPlayerDiscardDeck()).andReturn(mockedPlayerDiscardDeck);
    mockedPlayerDiscardDeck.addCard(mockedEventCard);
    EasyMock.expectLastCall();
    EasyMock.expect(mockedGame.getTotalResearchStationLeft()).andReturn(1);
    EasyMock.replay(mockedCity, mockedPlayer, mockedEventCard, mockedGame, mockedPlayerDiscardDeck);

    GameLogic logic = new GameLogic(mockedGame);
    logic.playGovernmentGrantAndBuild(mockedPlayer, mockedEventCard, mockedCity);

    EasyMock.verify(mockedCity, mockedPlayer, mockedEventCard, mockedGame, mockedPlayerDiscardDeck);
  }

  @Test(expected = InvalidActionException.class)
  public void testPlayEventCardGovernmentGrantWhenCityHasStation() {
    City mockedCity = EasyMock.createMock(City.class);
    EasyMock.expect(mockedCity.hasResearchStation()).andReturn(true);
    EasyMock.expect(mockedCity.getName()).andReturn("City");
    Player mockedPlayer = EasyMock.createMock(Player.class);
    Card mockedEventCard = EasyMock.createMock(Card.class);
    Game mockedGame = EasyMock.createMock(Game.class);
    EasyMock.replay(mockedCity, mockedPlayer, mockedEventCard, mockedGame);

    GameLogic logic = new GameLogic(mockedGame);
    logic.playGovernmentGrantAndBuild(mockedPlayer, mockedEventCard, mockedCity);

    EasyMock.verify(mockedCity, mockedPlayer, mockedEventCard, mockedGame);
  }

  @Test(expected = NotEnoughResearchStationsException.class)
  public void testPlayEventCardGovernmentGrantNotEnoughResearchStations() {
    City mockedCity = EasyMock.createMock(City.class);
    EasyMock.expect(mockedCity.hasResearchStation()).andReturn(false);
    EasyMock.expect(mockedCity.getName()).andReturn("City");
    Player mockedPlayer = EasyMock.createMock(Player.class);
    Card mockedEventCard = EasyMock.createMock(Card.class);
    Game mockedGame = EasyMock.createMock(Game.class);
    EasyMock.expect(mockedGame.getTotalResearchStationLeft()).andReturn(0);
    EasyMock.replay(mockedCity, mockedPlayer, mockedEventCard, mockedGame);

    GameLogic logic = new GameLogic(mockedGame);
    logic.playGovernmentGrantAndBuild(mockedPlayer, mockedEventCard, mockedCity);

    EasyMock.verify(mockedCity, mockedPlayer, mockedEventCard, mockedGame);
  }

  @Test
  public void testPlayEventCardGovernmentGrantAndMove() {
    City mockedCityFrom = EasyMock.createMock(City.class);
    EasyMock.expect(mockedCityFrom.hasResearchStation()).andReturn(true);
    City mockedCityTo = EasyMock.createMock(City.class);
    EasyMock.expect(mockedCityTo.hasResearchStation()).andReturn(false);
    mockedCityFrom.removeResearchStation();
    EasyMock.expectLastCall();
    mockedCityTo.addResearchStation();
    EasyMock.expectLastCall();
    Player mockedPlayer = EasyMock.createMock(Player.class);
    Card mockedEventCard = EasyMock.createMock(Card.class);
    EasyMock.expect(mockedPlayer.removeCard(mockedEventCard)).andReturn(mockedEventCard);
    Game mockedGame = EasyMock.createMock(Game.class);
    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);
    EasyMock.expect(mockedGame.getPlayerDiscardDeck()).andReturn(mockedPlayerDiscardDeck);
    mockedPlayerDiscardDeck.addCard(mockedEventCard);
    EasyMock.expectLastCall();
    EasyMock.replay(mockedCityFrom, mockedCityTo, mockedPlayer, mockedEventCard, mockedGame,
        mockedPlayerDiscardDeck);

    GameLogic logic = new GameLogic(mockedGame);
    logic.playGovernmentGrantAndMove(mockedPlayer, mockedEventCard, mockedCityFrom, mockedCityTo);

    EasyMock.verify(mockedCityFrom, mockedCityTo, mockedPlayer, mockedEventCard, mockedGame,
        mockedPlayerDiscardDeck);
  }

  @Test(expected = InvalidActionException.class)
  public void testPlayEventCardGovernmentGrantAndMoveInvalid() {
    City mockedCityFrom = EasyMock.createMock(City.class);
    EasyMock.expect(mockedCityFrom.getName()).andReturn("CityFrom");
    City mockedCityTo = EasyMock.createMock(City.class);
    EasyMock.expect(mockedCityTo.getName()).andReturn("CityTo");
    EasyMock.expect(mockedCityFrom.hasResearchStation()).andReturn(false);
    Player mockedPlayer = EasyMock.createMock(Player.class);
    Card mockedEventCard = EasyMock.createMock(Card.class);
    EasyMock.expect(mockedPlayer.removeCard(mockedEventCard)).andReturn(mockedEventCard);
    Game mockedGame = EasyMock.createMock(Game.class);
    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);
    EasyMock.expect(mockedGame.getPlayerDiscardDeck()).andReturn(mockedPlayerDiscardDeck);
    mockedPlayerDiscardDeck.addCard(mockedEventCard);
    EasyMock.expectLastCall();
    EasyMock.replay(mockedCityFrom, mockedCityTo, mockedPlayer, mockedEventCard, mockedGame,
        mockedPlayerDiscardDeck);

    GameLogic logic = new GameLogic(mockedGame);
    logic.playGovernmentGrantAndMove(mockedPlayer, mockedEventCard, mockedCityFrom, mockedCityTo);

    EasyMock.verify(mockedCityFrom, mockedCityTo, mockedPlayer, mockedEventCard, mockedGame,
        mockedPlayerDiscardDeck);
  }

  @Test
  public void testPlayEventCardForecast() {
    Card mockedInfectionCard = EasyMock.createMock(Card.class);
    List<Card> cards = new ArrayList<>();
    cards.add(mockedInfectionCard);
    cards.add(mockedInfectionCard);
    Card mockedEventCard = EasyMock.createMock(Card.class);
    Player mockedPlayer = EasyMock.createMock(Player.class);
    EasyMock.expect(mockedPlayer.removeCard(mockedEventCard)).andReturn(mockedEventCard);

    Deck mockedInfectionDeck = EasyMock.createMock(Deck.class);
    mockedInfectionDeck.addCard(mockedInfectionCard);
    EasyMock.expectLastCall();
    mockedInfectionDeck.addCard(mockedInfectionCard);
    EasyMock.expectLastCall();
    Game mockedGame = EasyMock.createMock(Game.class);
    EasyMock.expect(mockedGame.getInfectionDeck()).andStubReturn(mockedInfectionDeck);
    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);
    EasyMock.expect(mockedGame.getPlayerDiscardDeck()).andReturn(mockedPlayerDiscardDeck);
    mockedPlayerDiscardDeck.addCard(mockedEventCard);
    EasyMock.expectLastCall();
    EasyMock.replay(mockedInfectionCard, mockedInfectionDeck, mockedGame, mockedEventCard,
        mockedPlayer, mockedPlayerDiscardDeck);

    GameLogic logic = new GameLogic(mockedGame);
    logic.playForecast(mockedPlayer, mockedEventCard, cards);

    EasyMock.verify(mockedInfectionCard, mockedInfectionDeck, mockedGame, mockedEventCard,
        mockedPlayer, mockedPlayerDiscardDeck);
  }

}
