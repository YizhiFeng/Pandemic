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

import pandemic_exception.InvalidActionException;
import pandemic_exception.TooManyCardsInSameColorException;
import pandemic_game.Card;
import pandemic_game.City;
import pandemic_game.Constants.CubeColor;
import pandemic_game.Constants.Role;
import pandemic_game.Deck;
import pandemic_game.Player;

public class PlayerTest {

  @Test
  public void testGetRole() {
    City atlanta = EasyMock.createMock(City.class);

    EasyMock.replay(atlanta);
    Player player = new Player(atlanta);

    player.setRole(Role.Scientist);
    assertEquals(Role.Scientist, player.getRole());
    EasyMock.verify(atlanta);
  }
  
  @Test
  public void testGetNumberOfCardsInEachColor() {
    City atlanta = EasyMock.createMock(City.class);

    EasyMock.replay(atlanta);
    Player player = new Player(atlanta);
    
    assertEquals(0, player.getNumberOfCardsInEachColor(CubeColor.BLACK));
    assertEquals(0, player.getNumberOfCardsInEachColor(CubeColor.BLUE));
    assertEquals(0, player.getNumberOfCardsInEachColor(CubeColor.YELLOW));
    assertEquals(0, player.getNumberOfCardsInEachColor(CubeColor.RED));
  }
  
  @Test
  public void testGetNumberOfCardsInEachColorWithTwoRedCards() {
    City atlanta = EasyMock.createMock(City.class);
    Card red1 = EasyMock.createMock(Card.class);
    EasyMock.expect(red1.getColor()).andStubReturn(CubeColor.RED);
    Card red2 = EasyMock.createMock(Card.class);
    EasyMock.expect(red2.getColor()).andStubReturn(CubeColor.RED);

    EasyMock.replay(atlanta, red1, red2);
    Player player = new Player(atlanta);
    
    assertEquals(0, player.getNumberOfCardsInEachColor(CubeColor.BLACK));
    assertEquals(0, player.getNumberOfCardsInEachColor(CubeColor.BLUE));
    assertEquals(0, player.getNumberOfCardsInEachColor(CubeColor.YELLOW));
    assertEquals(0, player.getNumberOfCardsInEachColor(CubeColor.RED));
    
    EasyMock.verify(atlanta, red1, red2);
  }

  @Test
  public void testDriveOrFerryNormalCase() {
    City mockedAtlanta = EasyMock.createNiceMock(City.class);
    City mockedChicago = EasyMock.createNiceMock(City.class);
    ArrayList<City> adjacent = new ArrayList<>();
    adjacent.add(mockedChicago);
    EasyMock.expect(mockedAtlanta.getAdjacentCities()).andReturn(adjacent);

    EasyMock.replay(mockedAtlanta, mockedChicago);
    Player player = new Player(mockedAtlanta);

    player.driveOrFerry(mockedChicago);
    assertEquals(mockedChicago, player.getCurrentCity());
    EasyMock.verify(mockedAtlanta, mockedChicago);
  }

  @Test(expected = InvalidActionException.class)
  public void testDriveOrFerryToCurrentCity() {
    City mockedAtlanta = EasyMock.createNiceMock(City.class);
    ArrayList<City> adjacent = new ArrayList<>();
    EasyMock.expect(mockedAtlanta.getAdjacentCities()).andReturn(adjacent);

    EasyMock.replay(mockedAtlanta);
    Player player = new Player(mockedAtlanta);
    player.driveOrFerry(mockedAtlanta);
    EasyMock.verify(mockedAtlanta);
  }

  @Test(expected = InvalidActionException.class)
  public void testDriveOrFerryToNonadjacentCity() {
    City mockedAtlanta = EasyMock.createNiceMock(City.class);
    City mockedBeijing = EasyMock.createNiceMock(City.class);
    ArrayList<City> adjacent = new ArrayList<>();
    EasyMock.expect(mockedAtlanta.getAdjacentCities()).andReturn(adjacent);

    EasyMock.replay(mockedAtlanta, mockedBeijing);
    Player player = new Player(mockedAtlanta);
    player.driveOrFerry(mockedBeijing);
    EasyMock.verify(mockedAtlanta, mockedBeijing);
  }

  @Test
  public void testDirectFlightNormalCase() {
    Card mockedBeijingCard = EasyMock.createNiceMock(Card.class);
    EasyMock.expect(mockedBeijingCard.getName()).andStubReturn("Beijing");
    EasyMock.expect(mockedBeijingCard.getColor()).andStubReturn(CubeColor.COLORLESS);
    City mockedAtlanta = EasyMock.createNiceMock(City.class);
    City mockedBeijing = EasyMock.createNiceMock(City.class);
    ArrayList<City> adjacent = new ArrayList<>();
    EasyMock.expect(mockedAtlanta.getAdjacentCities()).andReturn(adjacent);
    EasyMock.expect(mockedBeijing.getName()).andStubReturn("Beijing");
    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);
    mockedPlayerDiscardDeck.addCard(mockedBeijingCard);
    EasyMock.expectLastCall();

    EasyMock.replay(mockedAtlanta, mockedBeijing, mockedBeijingCard, mockedPlayerDiscardDeck);
    Player player = new Player(mockedAtlanta);
    player.addCard(mockedBeijingCard);
    player.directFlight(mockedBeijing, mockedPlayerDiscardDeck);

    assertEquals(mockedBeijing, player.getCurrentCity());
    assertEquals(0, player.getCards().size());
    EasyMock.verify(mockedAtlanta, mockedBeijing, mockedBeijingCard, mockedPlayerDiscardDeck);
  }

  @Test(expected = InvalidActionException.class)
  public void testDirectFligthAndDontHaveCard() {
    City mockedAtlanta = EasyMock.createNiceMock(City.class);
    ArrayList<City> adjacent = new ArrayList<>();
    EasyMock.expect(mockedAtlanta.getAdjacentCities()).andReturn(adjacent);
    Card mockedMoscowCard = EasyMock.createNiceMock(Card.class);
    EasyMock.expect(mockedMoscowCard.getName()).andStubReturn("Moscow");
    EasyMock.expect(mockedMoscowCard.getColor()).andStubReturn(CubeColor.COLORLESS);
    City mockedBeijing = EasyMock.createNiceMock(City.class);
    EasyMock.expect(mockedBeijing.getName()).andStubReturn("Beijing");
    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);

    EasyMock.replay(mockedAtlanta, mockedBeijing, mockedMoscowCard, mockedPlayerDiscardDeck);
    Player player = new Player(mockedAtlanta);

    player.addCard(mockedMoscowCard);
    player.directFlight(mockedBeijing, mockedPlayerDiscardDeck);
    EasyMock.verify(mockedAtlanta, mockedBeijing, mockedMoscowCard, mockedPlayerDiscardDeck);
  }

  @Test
  public void testDirectFlightToAdjacentCityAndNotDiscardCard() {
    Card chicagoCard = EasyMock.createNiceMock(Card.class);
    EasyMock.expect(chicagoCard.getName()).andStubReturn("Chicago");
    EasyMock.expect(chicagoCard.getColor()).andStubReturn(CubeColor.COLORLESS);
    City mockedAtlanta = EasyMock.createNiceMock(City.class);
    City mockedChicago = EasyMock.createNiceMock(City.class);
    ArrayList<City> adjacent = new ArrayList<>();
    adjacent.add(mockedChicago);
    EasyMock.expect(mockedAtlanta.getAdjacentCities()).andReturn(adjacent);
    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);

    EasyMock.replay(chicagoCard, mockedAtlanta, mockedChicago, mockedPlayerDiscardDeck);
    Player player = new Player(mockedAtlanta);
    player.addCard(chicagoCard);
    player.directFlight(mockedChicago, mockedPlayerDiscardDeck);
    assertTrue(player.getCards().containsKey("Chicago"));
    EasyMock.verify(chicagoCard, mockedAtlanta, mockedChicago, mockedPlayerDiscardDeck);
  }

  @Test
  public void testCharterFlightNormalCase() {
    Card atlantaCard = EasyMock.createMock(Card.class);
    EasyMock.expect(atlantaCard.getName()).andStubReturn("Atlanta");
    EasyMock.expect(atlantaCard.getColor()).andStubReturn(CubeColor.COLORLESS);
    City mockedAtlanta = EasyMock.createNiceMock(City.class);
    City mockedTokyo = EasyMock.createNiceMock(City.class);
    EasyMock.expect(mockedAtlanta.getName()).andStubReturn("Atlanta");
    EasyMock.expect(mockedTokyo.getName()).andReturn("Tokyo");
    EasyMock.expect(mockedAtlanta.getAdjacentCities()).andReturn(new ArrayList<City>());
    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);
    mockedPlayerDiscardDeck.addCard(atlantaCard);
    EasyMock.expectLastCall();

    EasyMock.replay(atlantaCard, mockedAtlanta, mockedTokyo, mockedPlayerDiscardDeck);

    Player player = new Player(mockedAtlanta);
    player.addCard(atlantaCard);
    player.charterFlight(mockedTokyo, mockedPlayerDiscardDeck);

    assertEquals(0, player.getCards().size());
    EasyMock.verify(atlantaCard, mockedAtlanta, mockedTokyo, mockedPlayerDiscardDeck);
  }

  @Test(expected = InvalidActionException.class)
  public void testCharterFlightToCurrentCity() {
    City mockedAtlanta = EasyMock.createNiceMock(City.class);
    EasyMock.expect(mockedAtlanta.getName()).andStubReturn("Atlanta");
    Card atlantaCard = EasyMock.createMock(Card.class);
    EasyMock.expect(atlantaCard.getName()).andReturn("Atlanta");
    EasyMock.expect(atlantaCard.getColor()).andReturn(CubeColor.COLORLESS);
    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);

    EasyMock.replay(mockedAtlanta, atlantaCard, mockedPlayerDiscardDeck);
    Player player = new Player(mockedAtlanta);

    player.addCard(atlantaCard);
    player.charterFlight(mockedAtlanta, mockedPlayerDiscardDeck);
    EasyMock.verify(mockedAtlanta, atlantaCard, mockedPlayerDiscardDeck);
  }

  @Test(expected = InvalidActionException.class)
  public void testCharterFlightInvalidCase() {
    City mockedAtlanta = EasyMock.createNiceMock(City.class);
    City mockedChicago = EasyMock.createNiceMock(City.class);
    EasyMock.expect(mockedAtlanta.getName()).andStubReturn("Atlanta");
    EasyMock.expect(mockedChicago.getName()).andStubReturn("Chicago");
    EasyMock.expect(mockedAtlanta.getAdjacentCities()).andReturn(new ArrayList<City>());
    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);

    Card atlantaCard = EasyMock.createNiceMock(Card.class);
    EasyMock.expect(atlantaCard.getName()).andStubReturn("mock");
    EasyMock.expect(atlantaCard.getColor()).andStubReturn(CubeColor.COLORLESS);

    EasyMock.replay(atlantaCard, mockedAtlanta, mockedChicago, mockedPlayerDiscardDeck);
    Player player = new Player(mockedAtlanta);

    player.addCard(atlantaCard);
    player.charterFlight(mockedChicago, mockedPlayerDiscardDeck);
    EasyMock.verify(atlantaCard, mockedAtlanta, mockedChicago, mockedPlayerDiscardDeck);
  }

  @Test
  public void testCharterFlightToAdjacentCity() {
    Card atlantaCard = EasyMock.createNiceMock(Card.class);
    EasyMock.expect(atlantaCard.getName()).andStubReturn("Atlanta");
    EasyMock.expect(atlantaCard.getColor()).andStubReturn(CubeColor.COLORLESS);
    City mockedAtlanta = EasyMock.createNiceMock(City.class);
    City mockedChicago = EasyMock.createNiceMock(City.class);

    ArrayList<City> adjacentCities = new ArrayList<>();
    adjacentCities.add(mockedChicago);
    EasyMock.expect(mockedAtlanta.getAdjacentCities()).andReturn(adjacentCities);
    EasyMock.expect(mockedAtlanta.getName()).andStubReturn("Atlanta");
    EasyMock.expect(mockedChicago.getName()).andStubReturn("Chicago");
    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);

    EasyMock.replay(atlantaCard, mockedAtlanta, mockedChicago, mockedPlayerDiscardDeck);

    Player player = new Player(mockedAtlanta);
    player.addCard(atlantaCard);
    player.charterFlight(mockedChicago, mockedPlayerDiscardDeck);
    assertEquals(1, player.getCards().size());
    EasyMock.verify(atlantaCard, mockedAtlanta, mockedChicago, mockedPlayerDiscardDeck);
  }

  @Test
  public void testShuttleFlightWhenCurrentCityHasResearchStation() {
    City mockedAtlanta = EasyMock.createNiceMock(City.class);
    City mockedBeijing = EasyMock.createNiceMock(City.class);
    EasyMock.expect(mockedAtlanta.hasResearchStation()).andStubReturn(true);
    EasyMock.expect(mockedBeijing.hasResearchStation()).andStubReturn(true);

    EasyMock.replay(mockedAtlanta, mockedBeijing);
    Player player = new Player(mockedAtlanta);
    player.shuttleFlight(mockedBeijing);
    assertEquals(mockedBeijing, player.getCurrentCity());
    EasyMock.verify(mockedAtlanta, mockedBeijing);
  }

  @Test(expected = InvalidActionException.class)
  public void testShuttleFlightWhenCurrentCityHasNoResearchStation() {
    City mockedAtlanta = EasyMock.createNiceMock(City.class);
    City mockedBeijing = EasyMock.createNiceMock(City.class);
    EasyMock.expect(mockedAtlanta.hasResearchStation()).andStubReturn(false);
    EasyMock.expect(mockedBeijing.hasResearchStation()).andStubReturn(true);

    EasyMock.replay(mockedAtlanta, mockedBeijing);
    Player player = new Player(mockedAtlanta);
    player.shuttleFlight(mockedBeijing);
    EasyMock.verify(mockedAtlanta, mockedBeijing);
  }

  @Test(expected = InvalidActionException.class)
  public void testShuttleFlightToCurrentCity() {
    City mockedCity = EasyMock.createMock(City.class);

    EasyMock.replay(mockedCity);

    Player player = new Player(mockedCity);
    player.shuttleFlight(mockedCity);

    EasyMock.verify(mockedCity);
  }

  @Test(expected = InvalidActionException.class)
  public void testMoveToCityWithResearchStationWhenDestinationHasNoResearchStation() {
    City mockedAtlanta = EasyMock.createNiceMock(City.class);
    City mockedBeijing = EasyMock.createNiceMock(City.class);
    EasyMock.expect(mockedAtlanta.hasResearchStation()).andStubReturn(true);
    EasyMock.expect(mockedBeijing.hasResearchStation()).andStubReturn(false);

    EasyMock.replay(mockedAtlanta, mockedBeijing);
    Player player = new Player(mockedAtlanta);
    player.shuttleFlight(mockedBeijing);
    EasyMock.verify(mockedAtlanta, mockedBeijing);
  }

  @Test
  public void testBuildResearchStationAndDiscardCard() {
    Card atlantaCard = EasyMock.createNiceMock(Card.class);
    EasyMock.expect(atlantaCard.getName()).andStubReturn("Atlanta");
    EasyMock.expect(atlantaCard.getColor()).andStubReturn(CubeColor.BLUE);
    City mockedAtlanta = EasyMock.createNiceMock(City.class);
    EasyMock.expect(mockedAtlanta.hasResearchStation()).andReturn(false);
    EasyMock.expect(mockedAtlanta.getName()).andStubReturn("Atlanta");
    EasyMock.expect(mockedAtlanta.hasResearchStation()).andReturn(true);
    mockedAtlanta.addResearchStation();
    EasyMock.expectLastCall();
    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);
    mockedPlayerDiscardDeck.addCard(atlantaCard);
    EasyMock.expectLastCall();

    EasyMock.replay(atlantaCard, mockedAtlanta, mockedPlayerDiscardDeck);

    Player player = new Player(mockedAtlanta);
    player.setRole(Role.Scientist);
    player.addCard(atlantaCard);
    int previoudCardCount = player.getNumberOfCardsInEachColor(CubeColor.BLUE);
    player.buildResearchStation(mockedPlayerDiscardDeck);

    assertEquals(previoudCardCount - 1, player.getNumberOfCardsInEachColor(CubeColor.BLUE));
    assertEquals(0, player.getHandsSize());
    assertFalse(player.getCards().containsKey("Atlanta"));
    assertTrue(mockedAtlanta.hasResearchStation());
    EasyMock.verify(atlantaCard, mockedAtlanta, mockedPlayerDiscardDeck);
  }

  @Test(expected = InvalidActionException.class)
  public void testBuildResearchStationWithoutCurrentCityCard() {
    City mockedAtlanta = EasyMock.createNiceMock(City.class);
    EasyMock.expect(mockedAtlanta.hasResearchStation()).andReturn(false);
    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);

    EasyMock.replay(mockedAtlanta, mockedPlayerDiscardDeck);
    Player player = new Player(mockedAtlanta);
    player.setRole(Role.Scientist);
    player.buildResearchStation(mockedPlayerDiscardDeck);
    EasyMock.verify(mockedAtlanta, mockedPlayerDiscardDeck);
  }

  @Test(expected = InvalidActionException.class)
  public void testBuildResearchStationOnCurrentCityWithResearchStation() {
    City mockedAtlanta = EasyMock.createNiceMock(City.class);
    EasyMock.expect(mockedAtlanta.hasResearchStation()).andReturn(true);
    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);

    EasyMock.replay(mockedAtlanta, mockedPlayerDiscardDeck);
    Player player = new Player(mockedAtlanta);
    player.setCurrentCity(mockedAtlanta);
    player.buildResearchStation(mockedPlayerDiscardDeck);
    EasyMock.verify(mockedAtlanta, mockedPlayerDiscardDeck);
  }

  @Test
  public void testTreatCurrentCityWithOneCubePresent() {
    City mockedAtlanta = EasyMock.createNiceMock(City.class);
    mockedAtlanta.removeCube(CubeColor.BLUE);
    EasyMock.expectLastCall();

    EasyMock.replay(mockedAtlanta);
    Player player = new Player(mockedAtlanta);
    player.setRole(Role.Scientist);
    player.treatCurrentCity(CubeColor.BLUE);

    assertEquals(0, mockedAtlanta.getCubeCount(CubeColor.BLUE));
    EasyMock.verify(mockedAtlanta);
  }
  
  @Test
  public void testTreatCurrentCityWithTwoCubesPresent() {
    City mockedAtlanta = EasyMock.createMock(City.class);
    EasyMock.expect(mockedAtlanta.getCubeCount(CubeColor.BLUE)).andReturn(1);
    mockedAtlanta.removeCube(CubeColor.BLUE);
    EasyMock.expectLastCall();

    EasyMock.replay(mockedAtlanta);
    Player player = new Player(mockedAtlanta);
    player.setRole(Role.Scientist);
    player.treatCurrentCity(CubeColor.BLUE);

    assertEquals(1, mockedAtlanta.getCubeCount(CubeColor.BLUE));
    EasyMock.verify(mockedAtlanta);
  }

  @Test
  public void testPassMatchedCardToPlayerAtCurrentCity() {
    City mockedAtlanta = EasyMock.createNiceMock(City.class);
    EasyMock.expect(mockedAtlanta.getName()).andStubReturn("Atlanta");
    Card atlantaCard = EasyMock.createNiceMock(Card.class);
    EasyMock.expect(atlantaCard.getName()).andStubReturn("Atlanta");
    EasyMock.expect(atlantaCard.getColor()).andStubReturn(CubeColor.COLORLESS);

    EasyMock.replay(mockedAtlanta, atlantaCard);
    Player player1 = new Player(mockedAtlanta);
    Player player2 = new Player(mockedAtlanta);

    player1.addCard(atlantaCard);
    player1.passCard(player2);

    assertFalse(player1.getCards().containsKey("Atlanta"));
    assertTrue(player2.getCards().containsKey("Atlanta"));
    EasyMock.verify(mockedAtlanta, atlantaCard);
  }

  @Test
  public void testPassMatchedCardFromPlayerAtCurrentCity() {
    City mockedAtlanta = EasyMock.createNiceMock(City.class);
    EasyMock.expect(mockedAtlanta.getName()).andStubReturn("Atlanta");
    Card atlantaCard = EasyMock.createNiceMock(Card.class);
    EasyMock.expect(atlantaCard.getName()).andStubReturn("Atlanta");
    EasyMock.expect(atlantaCard.getColor()).andStubReturn(CubeColor.COLORLESS);

    EasyMock.replay(mockedAtlanta, atlantaCard);
    Player player1 = new Player(mockedAtlanta);
    Player player2 = new Player(mockedAtlanta);

    player2.addCard(atlantaCard);
    player1.passCard(player2);

    assertTrue(player1.getCards().containsKey("Atlanta"));
    assertFalse(player2.getCards().containsKey("Atlanta"));
    EasyMock.verify(mockedAtlanta, atlantaCard);
  }

  @Test(expected = InvalidActionException.class)
  public void testPassMatchedCardToPlayerAtAnotherCity() {
    City mockedAtlanta = EasyMock.createNiceMock(City.class);
    EasyMock.expect(mockedAtlanta.getName()).andStubReturn("Atlanta");
    EasyMock.expect(mockedAtlanta.getName()).andStubReturn("Beijing");
    Card atlantaCard = EasyMock.createNiceMock(Card.class);
    EasyMock.expect(atlantaCard.getName()).andStubReturn("Atlanta");
    EasyMock.expect(atlantaCard.getColor()).andStubReturn(CubeColor.COLORLESS);
    City mockedBeijing = EasyMock.createNiceMock(City.class);

    EasyMock.replay(mockedAtlanta, mockedBeijing, atlantaCard);
    Player player1 = new Player(mockedAtlanta);
    Player player2 = new Player(mockedBeijing);

    player1.addCard(atlantaCard);
    player1.passCard(player2);
    EasyMock.verify(mockedAtlanta, mockedBeijing, atlantaCard);
  }

  @Test(expected = InvalidActionException.class)
  public void testPassUnMatchedCardToPlayerAtCurrentCity() {
    City mockedAtlanta = EasyMock.createNiceMock(City.class);
    EasyMock.expect(mockedAtlanta.getName()).andStubReturn("Atlanta");
    Card beijingCard = EasyMock.createNiceMock(Card.class);
    EasyMock.expect(beijingCard.getName()).andStubReturn("Beijing");

    EasyMock.replay(beijingCard, mockedAtlanta);
    Player player1 = new Player(mockedAtlanta);
    Player player2 = new Player(mockedAtlanta);
    player1.passCard(player2);
    EasyMock.verify(mockedAtlanta, beijingCard);
  }

  @Test
  public void testHasEnoughCardsToDiscoverCureWithThreeCards() {
    Card mockedCard = EasyMock.createMock(Card.class);
    EasyMock.expect(mockedCard.getName()).andReturn("City1");
    EasyMock.expect(mockedCard.getName()).andReturn("City2");
    EasyMock.expect(mockedCard.getName()).andReturn("City3");
    EasyMock.expect(mockedCard.getColor()).andStubReturn(CubeColor.COLORLESS);
    City mockedCity = EasyMock.createMock(City.class);

    EasyMock.replay(mockedCard, mockedCity);

    Player player = new Player(mockedCity);
    player.addCard(mockedCard);
    player.addCard(mockedCard);
    player.addCard(mockedCard);
    assertFalse(player.hasEnoughCardsToDiscoverCure());

    EasyMock.verify(mockedCard, mockedCity);
  }

  @Test
  public void testHasEnoughCardsToDiscoverCureWithFourCardsAndNotScientist() {
    Card mockedCard = EasyMock.createMock(Card.class);
    EasyMock.expect(mockedCard.getName()).andReturn("City1");
    EasyMock.expect(mockedCard.getName()).andReturn("City2");
    EasyMock.expect(mockedCard.getName()).andReturn("City3");
    EasyMock.expect(mockedCard.getName()).andReturn("City4");
    EasyMock.expect(mockedCard.getColor()).andStubReturn(CubeColor.COLORLESS);
    City mockedCity = EasyMock.createMock(City.class);

    EasyMock.replay(mockedCard, mockedCity);

    Player player = new Player(mockedCity);
    player.setRole(Role.Medic);
    player.addCard(mockedCard);
    player.addCard(mockedCard);
    player.addCard(mockedCard);
    player.addCard(mockedCard);
    assertFalse(player.hasEnoughCardsToDiscoverCure());

    EasyMock.verify(mockedCard, mockedCity);
  }

  @Test
  public void testHasEnoughCardsToDiscoverCureWithFourValidCardsAndScientist() {
    Card mockedCard = EasyMock.createMock(Card.class);
    EasyMock.expect(mockedCard.getName()).andReturn("City1");
    EasyMock.expect(mockedCard.getName()).andReturn("City2");
    EasyMock.expect(mockedCard.getName()).andReturn("City3");
    EasyMock.expect(mockedCard.getName()).andReturn("City4");
    EasyMock.expect(mockedCard.getColor()).andStubReturn(CubeColor.BLUE);
    City mockedCity = EasyMock.createMock(City.class);

    EasyMock.replay(mockedCard, mockedCity);

    Player player = new Player(mockedCity);
    player.setRole(Role.Scientist);
    player.addCard(mockedCard);
    player.addCard(mockedCard);
    player.addCard(mockedCard);
    player.addCard(mockedCard);
    assertTrue(player.hasEnoughCardsToDiscoverCure());

    EasyMock.verify(mockedCard, mockedCity);
  }

  @Test(expected = TooManyCardsInSameColorException.class)
  public void testHasEnoughCardsToDiscoverCureWithFiveValidCardsAnScientist() {
    Card mockedCard = EasyMock.createMock(Card.class);
    EasyMock.expect(mockedCard.getName()).andReturn("City1");
    EasyMock.expect(mockedCard.getName()).andReturn("City2");
    EasyMock.expect(mockedCard.getName()).andReturn("City3");
    EasyMock.expect(mockedCard.getName()).andReturn("City4");
    EasyMock.expect(mockedCard.getName()).andReturn("City5");
    EasyMock.expect(mockedCard.getColor()).andStubReturn(CubeColor.YELLOW);
    City mockedCity = EasyMock.createMock(City.class);

    EasyMock.replay(mockedCard, mockedCity);

    Player player = new Player(mockedCity);
    player.setRole(Role.Scientist);
    player.addCard(mockedCard);
    player.addCard(mockedCard);
    player.addCard(mockedCard);
    player.addCard(mockedCard);
    player.addCard(mockedCard);
    player.hasEnoughCardsToDiscoverCure();

    EasyMock.verify(mockedCard, mockedCity);
  }

  @Test(expected = TooManyCardsInSameColorException.class)
  public void testHasEnoughCardsToDiscoverCureWithSixValidCardsAnMedic() {
    Card mockedCard = EasyMock.createMock(Card.class);
    EasyMock.expect(mockedCard.getName()).andReturn("City1");
    EasyMock.expect(mockedCard.getName()).andReturn("City2");
    EasyMock.expect(mockedCard.getName()).andReturn("City3");
    EasyMock.expect(mockedCard.getName()).andReturn("City4");
    EasyMock.expect(mockedCard.getName()).andReturn("City5");
    EasyMock.expect(mockedCard.getName()).andReturn("City6");
    EasyMock.expect(mockedCard.getColor()).andStubReturn(CubeColor.BLACK);
    City mockedCity = EasyMock.createMock(City.class);

    EasyMock.replay(mockedCard, mockedCity);

    Player player = new Player(mockedCity);
    player.setRole(Role.Medic);
    player.addCard(mockedCard);
    player.addCard(mockedCard);
    player.addCard(mockedCard);
    player.addCard(mockedCard);
    player.addCard(mockedCard);
    player.addCard(mockedCard);
    player.hasEnoughCardsToDiscoverCure();

    EasyMock.verify(mockedCard, mockedCity);
  }

  @Test
  public void testHasEnoughCardsToDiscoverCureFails() {
    Card mockedCard1 = EasyMock.createMock(Card.class);
    EasyMock.expect(mockedCard1.getName()).andStubReturn("1");
    EasyMock.expect(mockedCard1.getColor()).andStubReturn(CubeColor.BLACK);
    Card mockedCard2 = EasyMock.createMock(Card.class);
    EasyMock.expect(mockedCard2.getName()).andStubReturn("2");
    EasyMock.expect(mockedCard2.getColor()).andStubReturn(CubeColor.BLUE);
    Card mockedCard3 = EasyMock.createMock(Card.class);
    EasyMock.expect(mockedCard3.getName()).andStubReturn("3");
    EasyMock.expect(mockedCard3.getColor()).andStubReturn(CubeColor.YELLOW);
    Card mockedCard4 = EasyMock.createMock(Card.class);
    EasyMock.expect(mockedCard4.getName()).andStubReturn("4");
    EasyMock.expect(mockedCard4.getColor()).andStubReturn(CubeColor.RED);
    Card mockedCard5 = EasyMock.createMock(Card.class);
    EasyMock.expect(mockedCard5.getName()).andStubReturn("5");
    EasyMock.expect(mockedCard5.getColor()).andStubReturn(CubeColor.RED);
    City mockedCity = EasyMock.createMock(City.class);
    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);
    EasyMock.replay(mockedCity, mockedPlayerDiscardDeck, mockedCard1, mockedCard2, mockedCard3,
        mockedCard4, mockedCard5);

    Player player = new Player(mockedCity);
    player.setRole(Role.Scientist);
    player.addCard(mockedCard1);
    player.addCard(mockedCard2);
    player.addCard(mockedCard3);
    player.addCard(mockedCard4);
    player.addCard(mockedCard5);
    assertFalse(player.hasEnoughCardsToDiscoverCure());

    EasyMock.verify(mockedCity, mockedPlayerDiscardDeck, mockedCard1, mockedCard2, mockedCard3,
        mockedCard4, mockedCard5);
  }

  @Test
  public void testHasEnoughCardsToDiscoverCureWithFiveValidCardsAnMedic() {
    Card mockedCard = EasyMock.createMock(Card.class);
    EasyMock.expect(mockedCard.getName()).andReturn("City1");
    EasyMock.expect(mockedCard.getName()).andReturn("City2");
    EasyMock.expect(mockedCard.getName()).andReturn("City3");
    EasyMock.expect(mockedCard.getName()).andReturn("City4");
    EasyMock.expect(mockedCard.getName()).andReturn("City5");
    EasyMock.expect(mockedCard.getColor()).andStubReturn(CubeColor.BLACK);
    City mockedCity = EasyMock.createMock(City.class);

    EasyMock.replay(mockedCard, mockedCity);

    Player player = new Player(mockedCity);
    player.setRole(Role.Medic);
    player.addCard(mockedCard);
    player.addCard(mockedCard);
    player.addCard(mockedCard);
    player.addCard(mockedCard);
    player.addCard(mockedCard);
    assertTrue(player.hasEnoughCardsToDiscoverCure());

    EasyMock.verify(mockedCard, mockedCity);
  }

  @Test
  public void testDiscoverCureByNonScientistPlayers() {
    Card mockedCard = EasyMock.createMock(Card.class);
    for (int i = 0; i < 2; i++) {
      EasyMock.expect(mockedCard.getName()).andReturn("City1");
      EasyMock.expect(mockedCard.getName()).andReturn("City2");
      EasyMock.expect(mockedCard.getName()).andReturn("City3");
      EasyMock.expect(mockedCard.getName()).andReturn("City4");
      EasyMock.expect(mockedCard.getName()).andReturn("City5");
    }
    EasyMock.expect(mockedCard.getColor()).andStubReturn(CubeColor.YELLOW);
    City mockedCity = EasyMock.createMock(City.class);
    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);
    mockedPlayerDiscardDeck.addCard(mockedCard);
    EasyMock.expectLastCall().asStub();

    EasyMock.replay(mockedCard, mockedCity, mockedPlayerDiscardDeck);

    Player player = new Player(mockedCity);
    for (int i = 0; i < 5; i++) {
      player.addCard(mockedCard);
    }
    Map<CubeColor, Boolean> map = new HashMap<>();
    map.put(CubeColor.YELLOW, false);
    assertEquals(CubeColor.YELLOW, player.discoverCure(mockedPlayerDiscardDeck, map));
    assertTrue(player.getCardList().isEmpty());

    EasyMock.verify(mockedCard, mockedCity, mockedPlayerDiscardDeck);
  }

  @Test
  public void testDiscoverCureByScientist() {
    Card mockedCard1 = EasyMock.createMock(Card.class);
    Card mockedCard2 = EasyMock.createMock(Card.class);
    Card mockedCard3 = EasyMock.createMock(Card.class);
    Card mockedCard4 = EasyMock.createMock(Card.class);
    Card mockedCard5 = EasyMock.createMock(Card.class);

    EasyMock.expect(mockedCard1.getName()).andStubReturn("City1");
    EasyMock.expect(mockedCard2.getName()).andStubReturn("City2");
    EasyMock.expect(mockedCard3.getName()).andStubReturn("City3");
    EasyMock.expect(mockedCard4.getName()).andStubReturn("City4");
    EasyMock.expect(mockedCard5.getName()).andStubReturn("City5");
    EasyMock.expect(mockedCard1.getColor()).andStubReturn(CubeColor.RED);
    EasyMock.expect(mockedCard2.getColor()).andStubReturn(CubeColor.RED);
    EasyMock.expect(mockedCard3.getColor()).andStubReturn(CubeColor.RED);
    EasyMock.expect(mockedCard4.getColor()).andStubReturn(CubeColor.RED);
    EasyMock.expect(mockedCard5.getColor()).andStubReturn(CubeColor.YELLOW);

    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);
    mockedPlayerDiscardDeck.addCard(mockedCard1);
    EasyMock.expectLastCall();
    mockedPlayerDiscardDeck.addCard(mockedCard2);
    EasyMock.expectLastCall();
    mockedPlayerDiscardDeck.addCard(mockedCard3);
    EasyMock.expectLastCall();
    mockedPlayerDiscardDeck.addCard(mockedCard4);
    EasyMock.expectLastCall();

    City mockedCity = EasyMock.createMock(City.class);
    EasyMock.replay(mockedCard1, mockedCard2, mockedCard3, mockedCard4, mockedCard5, mockedCity,
        mockedPlayerDiscardDeck);

    Player player = new Player(mockedCity);
    player.setRole(Role.Scientist);

    player.addCard(mockedCard1);
    player.addCard(mockedCard2);
    player.addCard(mockedCard3);
    player.addCard(mockedCard4);
    player.addCard(mockedCard5);
    Map<CubeColor, Boolean> map = new HashMap<>();
    map.put(CubeColor.RED, false);
    assertEquals(CubeColor.RED, player.discoverCure(mockedPlayerDiscardDeck, map));
    assertEquals(1, player.getCardList().size());

    EasyMock.verify(mockedCard1, mockedCard2, mockedCard3, mockedCard4, mockedCard5, mockedCity,
        mockedPlayerDiscardDeck);
  }

  @Test(expected = InvalidActionException.class)
  public void testDiscoverCureWhenDiseaseAlreadyCured() {
    Card mockedCard1 = EasyMock.createMock(Card.class);
    Card mockedCard2 = EasyMock.createMock(Card.class);
    Card mockedCard3 = EasyMock.createMock(Card.class);
    Card mockedCard4 = EasyMock.createMock(Card.class);
    Card mockedCard5 = EasyMock.createMock(Card.class);

    EasyMock.expect(mockedCard1.getName()).andStubReturn("City1");
    EasyMock.expect(mockedCard2.getName()).andStubReturn("City2");
    EasyMock.expect(mockedCard3.getName()).andStubReturn("City3");
    EasyMock.expect(mockedCard4.getName()).andStubReturn("City4");
    EasyMock.expect(mockedCard5.getName()).andStubReturn("City5");
    EasyMock.expect(mockedCard1.getColor()).andStubReturn(CubeColor.RED);
    EasyMock.expect(mockedCard2.getColor()).andStubReturn(CubeColor.RED);
    EasyMock.expect(mockedCard3.getColor()).andStubReturn(CubeColor.RED);
    EasyMock.expect(mockedCard4.getColor()).andStubReturn(CubeColor.RED);
    EasyMock.expect(mockedCard5.getColor()).andStubReturn(CubeColor.YELLOW);

    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);
    mockedPlayerDiscardDeck.addCard(mockedCard1);
    EasyMock.expectLastCall();
    mockedPlayerDiscardDeck.addCard(mockedCard2);
    EasyMock.expectLastCall();
    mockedPlayerDiscardDeck.addCard(mockedCard3);
    EasyMock.expectLastCall();
    mockedPlayerDiscardDeck.addCard(mockedCard4);
    EasyMock.expectLastCall();

    City mockedCity = EasyMock.createMock(City.class);
    
    EasyMock.replay(mockedCard1, mockedCard2, mockedCard3, mockedCard4, mockedCard5, mockedCity,
        mockedPlayerDiscardDeck);

    Player player = new Player(mockedCity);
    player.setRole(Role.Scientist);

    player.addCard(mockedCard1);
    player.addCard(mockedCard2);
    player.addCard(mockedCard3);
    player.addCard(mockedCard4);
    player.addCard(mockedCard5);
    Map<CubeColor, Boolean> map = new HashMap<>();
    map.put(CubeColor.RED, true);
    player.discoverCure(mockedPlayerDiscardDeck, map);

    EasyMock.verify(mockedCard1, mockedCard2, mockedCard3, mockedCard4, mockedCard5, mockedCity,
        mockedPlayerDiscardDeck);
  }
  
  @Test
  public void testDiscoverCureWithSelectedCards() {
    Card mockedCard = EasyMock.createMock(Card.class);
    for (int i = 0; i < 2; i++) {
      EasyMock.expect(mockedCard.getName()).andReturn("City1");
      EasyMock.expect(mockedCard.getName()).andReturn("City2");
      EasyMock.expect(mockedCard.getName()).andReturn("City3");
      EasyMock.expect(mockedCard.getName()).andReturn("City4");
    }
    EasyMock.expect(mockedCard.getColor()).andStubReturn(CubeColor.BLACK);
    City mockedCity = EasyMock.createMock(City.class);
    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);
    mockedPlayerDiscardDeck.addCard(mockedCard);
    EasyMock.expectLastCall().asStub();

    EasyMock.replay(mockedCard, mockedCity, mockedPlayerDiscardDeck);

    Player player = new Player(mockedCity);
    for (int i = 0; i < 4; i++) {
      player.addCard(mockedCard);
    }
    List<Card> selectedCards = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      selectedCards.add(mockedCard);
    }
    Map<CubeColor, Boolean> map = new HashMap<>();
    map.put(CubeColor.BLACK, false);
    assertEquals(CubeColor.BLACK, player.discoverCure(selectedCards, mockedPlayerDiscardDeck, map));
    assertTrue(player.getCardList().isEmpty());

    EasyMock.verify(mockedCard, mockedCity, mockedPlayerDiscardDeck);
  }
  
  @Test(expected = InvalidActionException.class)
  public void testDiscoverCureWithSelectedCardsOnCuredDisease() {
    Card mockedCard = EasyMock.createMock(Card.class);
    for (int i = 0; i < 2; i++) {
      EasyMock.expect(mockedCard.getName()).andReturn("City1");
      EasyMock.expect(mockedCard.getName()).andReturn("City2");
      EasyMock.expect(mockedCard.getName()).andReturn("City3");
      EasyMock.expect(mockedCard.getName()).andReturn("City4");
    }
    EasyMock.expect(mockedCard.getColor()).andStubReturn(CubeColor.BLACK);
    City mockedCity = EasyMock.createMock(City.class);
    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);
    mockedPlayerDiscardDeck.addCard(mockedCard);
    EasyMock.expectLastCall().asStub();

    EasyMock.replay(mockedCard, mockedCity, mockedPlayerDiscardDeck);

    Player player = new Player(mockedCity);
    for (int i = 0; i < 4; i++) {
      player.addCard(mockedCard);
    }
    List<Card> selectedCards = new ArrayList<>();
    for (int i = 0; i < 4; i++) {
      selectedCards.add(mockedCard);
    }
    Map<CubeColor, Boolean> map = new HashMap<>();
    map.put(CubeColor.BLACK, true);
    player.discoverCure(selectedCards, mockedPlayerDiscardDeck, map);

    EasyMock.verify(mockedCard, mockedCity, mockedPlayerDiscardDeck);
  }

  @Test
  public void testBuildResearchStationAsOperationExpertWithACityCard() {
    City mockedAtlanta = EasyMock.createNiceMock(City.class);
    EasyMock.expect(mockedAtlanta.getName()).andStubReturn("Atlanta");
    EasyMock.expect(mockedAtlanta.hasResearchStation()).andReturn(false);
    EasyMock.expect(mockedAtlanta.hasResearchStation()).andReturn(true);
    mockedAtlanta.addResearchStation();
    EasyMock.expectLastCall();
    Card atlantaCard = EasyMock.createNiceMock(Card.class);
    EasyMock.expect(atlantaCard.getName()).andStubReturn("Atlanta");
    EasyMock.expect(atlantaCard.getColor()).andStubReturn(CubeColor.BLUE);
    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);
    EasyMock.replay(mockedAtlanta, atlantaCard, mockedPlayerDiscardDeck);

    Player player = new Player(mockedAtlanta);
    player.setRole(Role.OperationExpert);
    player.addCard(atlantaCard);
    player.buildResearchStation(mockedPlayerDiscardDeck);

    assertTrue(mockedAtlanta.hasResearchStation());
    assertEquals(1, player.getHandsSize());
    EasyMock.verify(mockedAtlanta, atlantaCard, mockedPlayerDiscardDeck);
  }

  @Test
  public void testBuildResearchStationAsOperationExpertWithoutACityCard() {
    City mockedAtlanta = EasyMock.createNiceMock(City.class);
    EasyMock.expect(mockedAtlanta.getName()).andStubReturn("Atlanta");
    EasyMock.expect(mockedAtlanta.hasResearchStation()).andReturn(false);
    EasyMock.expect(mockedAtlanta.hasResearchStation()).andReturn(true);
    Card beijingCard = EasyMock.createNiceMock(Card.class);
    EasyMock.expect(beijingCard.getName()).andStubReturn("Beijing");
    EasyMock.expect(beijingCard.getColor()).andStubReturn(CubeColor.COLORLESS);
    Deck mockedPlayerDiscardDeck = EasyMock.createMock(Deck.class);
    EasyMock.replay(mockedAtlanta, beijingCard, mockedPlayerDiscardDeck);

    Player player = new Player(mockedAtlanta);
    player.setRole(Role.OperationExpert);
    player.addCard(beijingCard);
    player.buildResearchStation(mockedPlayerDiscardDeck);

    assertTrue(mockedAtlanta.hasResearchStation());
    assertEquals(1, player.getHandsSize());
    EasyMock.verify(mockedAtlanta, beijingCard, mockedPlayerDiscardDeck);
  }

  @Test
  public void testPassACardToAnotherPlayerAsResearcher() {
    City mockedAtlanta = EasyMock.createNiceMock(City.class);
    EasyMock.expect(mockedAtlanta.getName()).andStubReturn("Atlanta");
    Card beijingCard = EasyMock.createNiceMock(Card.class);
    EasyMock.expect(beijingCard.getName()).andStubReturn("Beijing");
    EasyMock.expect(beijingCard.getColor()).andStubReturn(CubeColor.COLORLESS);

    EasyMock.replay(beijingCard, mockedAtlanta);
    Player player1 = new Player(mockedAtlanta);
    Player player2 = new Player(mockedAtlanta);

    player1.addCard(beijingCard);
    player1.passSpecificCardToPlayer(player2, beijingCard);
    assertEquals(0, player1.getHandsSize());
    assertEquals(1, player2.getHandsSize());
    EasyMock.verify(mockedAtlanta, beijingCard);
  }

  @Test(expected = InvalidActionException.class)
  public void testPassMatchedCardToPlayerNotSameCityAsResearcher() {
    City mockedAtlanta = EasyMock.createNiceMock(City.class);
    EasyMock.expect(mockedAtlanta.getName()).andStubReturn("Atlanta");
    EasyMock.expect(mockedAtlanta.getName()).andStubReturn("Beijing");
    Card beijingCard = EasyMock.createNiceMock(Card.class);
    EasyMock.expect(beijingCard.getName()).andStubReturn("Beijing");
    EasyMock.expect(beijingCard.getColor()).andStubReturn(CubeColor.COLORLESS);
    City mockedBeijing = EasyMock.createNiceMock(City.class);

    EasyMock.replay(mockedAtlanta, mockedBeijing, beijingCard);
    Player player1 = new Player(mockedAtlanta);
    Player player2 = new Player(mockedBeijing);

    player1.addCard(beijingCard);
    player1.passSpecificCardToPlayer(player2, beijingCard);
    EasyMock.verify(mockedAtlanta, mockedBeijing, beijingCard);
  }

  @Test
  public void testSetCityAndRemoveAllCubesAsMedic() {
    City mockedAtlanta = EasyMock.createNiceMock(City.class);
    EasyMock.expect(mockedAtlanta.getName()).andStubReturn("Atlanta");
    City mockedChicago = EasyMock.createNiceMock(City.class);
    EasyMock.expect(mockedChicago.getName()).andStubReturn("Chicago");
    EasyMock.expect(mockedChicago.getCubeCount(CubeColor.BLUE)).andReturn(2);

    EasyMock.replay(mockedAtlanta, mockedChicago);
    Player player = new Player(mockedAtlanta);
    player.setRole(Role.Medic);
    player.setCurrentCity(mockedChicago);
    // assume the blue disease already has a cure
    player.removeAllCubes(CubeColor.BLUE);

    assertEquals(player.getCurrentCity(), mockedChicago);
    assertEquals(0, mockedChicago.getCubeCount(CubeColor.BLUE));
    EasyMock.verify(mockedAtlanta, mockedChicago);
  }

  @Test
  public void testSetCityAndRemoveAllCubesAsNonMedic() {
    City mockedAtlanta = EasyMock.createNiceMock(City.class);
    EasyMock.expect(mockedAtlanta.getName()).andStubReturn("Atlanta");
    City mockedChicago = EasyMock.createNiceMock(City.class);
    EasyMock.expect(mockedChicago.getName()).andStubReturn("Chicago");
    EasyMock.expect(mockedChicago.getCubeCount(CubeColor.BLUE)).andReturn(2);

    EasyMock.replay(mockedAtlanta, mockedChicago);
    Player player = new Player(mockedAtlanta);
    player.setRole(Role.Scientist);
    player.setCurrentCity(mockedChicago);
    // assume the blue disease already has a cure
    player.removeAllCubes(CubeColor.BLUE);

    assertEquals(player.getCurrentCity(), mockedChicago);
    assertEquals(2, mockedChicago.getCubeCount(CubeColor.BLUE));
    EasyMock.verify(mockedAtlanta, mockedChicago);
  }

  @Test
  public void testRemoveAllCubesCalledCorrectNumberOfTimes() {
    City mockedCity = EasyMock.createMock(City.class);
    EasyMock.expect(mockedCity.getCubeCount(CubeColor.BLACK)).andReturn(3);
    mockedCity.removeCube(CubeColor.BLACK);
    EasyMock.expectLastCall();
    mockedCity.removeCube(CubeColor.BLACK);
    EasyMock.expectLastCall();
    mockedCity.removeCube(CubeColor.BLACK);
    EasyMock.expectLastCall();

    EasyMock.replay(mockedCity);

    Player player = new Player(mockedCity);
    player.setRole(Role.Medic);
    player.removeAllCubes(CubeColor.BLACK);

    EasyMock.verify(mockedCity);
  }

  @Test
  public void testGetTopPopulationInHands() {
    Card mockedCard = EasyMock.createMock(Card.class);
    EasyMock.expect(mockedCard.getName()).andReturn("mockedCard1");
    EasyMock.expect(mockedCard.getName()).andReturn("mockedCard10");
    EasyMock.expect(mockedCard.getPopulation()).andReturn(1);
    EasyMock.expect(mockedCard.getPopulation()).andReturn(10);
    EasyMock.expect(mockedCard.getColor()).andStubReturn(CubeColor.COLORLESS);
    City mockedCity = EasyMock.createMock(City.class);

    EasyMock.replay(mockedCard, mockedCity);

    Player player = new Player(mockedCity);
    player.addCard(mockedCard);
    player.addCard(mockedCard);
    assertEquals(10, player.getTopPopulationInHands());

    EasyMock.verify(mockedCard, mockedCity);
  }

  @Test
  public void testGetCardList() {
    Card mockedCard = EasyMock.createMock(Card.class);
    EasyMock.expect(mockedCard.getName()).andReturn("mockedCard1");
    EasyMock.expect(mockedCard.getName()).andReturn("mockedCard2");
    EasyMock.expect(mockedCard.getColor()).andStubReturn(CubeColor.COLORLESS);
    City mockedCity = EasyMock.createMock(City.class);

    EasyMock.replay(mockedCard, mockedCity);

    Player player = new Player(mockedCity);
    player.addCard(mockedCard);
    player.addCard(mockedCard);
    assertEquals(mockedCard, player.getCardList().get(0));
    assertEquals(mockedCard, player.getCardList().get(1));

    EasyMock.verify(mockedCard, mockedCity);
  }
}
