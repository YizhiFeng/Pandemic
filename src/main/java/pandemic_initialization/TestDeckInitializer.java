package pandemic_initialization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import pandemic_game.Card;
import pandemic_game.Constants.CardType;
import pandemic_game.Constants.CubeColor;
import pandemic_game.Deck;

public class TestDeckInitializer implements DeckInitializer {

  private static int TOTAL_CITY_CARDS = 48;
  private static int TOTAL_EVENT_CARDS = 5;

  @Override
  public Deck initializePlayerDeck() {
    List<Card> playerCards = new ArrayList<Card>();
    Card atlantaCityCard = new Card(CardType.City, "Atlanta", CubeColor.BLUE, 10);
    playerCards.add(atlantaCityCard);
    Card chicagoCityCard = new Card(CardType.City, "Chicago", CubeColor.BLUE, 12);
    playerCards.add(chicagoCityCard);
    Card montrealCityCard = new Card(CardType.City, "Montreal", CubeColor.BLUE, 13);
    playerCards.add(montrealCityCard);
    Card newYorkCityCard = new Card(CardType.City, "New York", CubeColor.BLUE, 11);
    playerCards.add(newYorkCityCard);
    Card washingtonCityCard = new Card(CardType.City, "Washington", CubeColor.BLUE, 14);
    playerCards.add(washingtonCityCard);
    Card sanFCityCard = new Card(CardType.City, "San Francisco", CubeColor.BLUE, 15);
    playerCards.add(sanFCityCard);
    Card londonCityCard = new Card(CardType.City, "London", CubeColor.BLUE, 16);
    playerCards.add(londonCityCard);
    Card miamiCityCard = new Card(CardType.City, "Miami", CubeColor.YELLOW, 17);
    playerCards.add(miamiCityCard);
    Card mexicoCityCityCard = new Card(CardType.City, "Mexico City", CubeColor.YELLOW, 18);
    playerCards.add(mexicoCityCityCard);
    Card losACityCard = new Card(CardType.City, "Los Angeles", CubeColor.YELLOW, 19);
    playerCards.add(losACityCard);
    Card bogotaCityCard = new Card(CardType.City, "Bogota", CubeColor.YELLOW, 20);
    playerCards.add(bogotaCityCard);
    Card limaCityCard = new Card(CardType.City, "Lima", CubeColor.YELLOW, 21);
    playerCards.add(limaCityCard);
    Card santiagoCityCard = new Card(CardType.City, "Santiago", CubeColor.YELLOW, 22);
    playerCards.add(santiagoCityCard);
    Card saopauloCityCard = new Card(CardType.City, "Sao Paulo", CubeColor.YELLOW, 23);
    playerCards.add(saopauloCityCard);
    Card madridCityCard = new Card(CardType.City, "Madrid", CubeColor.BLUE, 24);
    playerCards.add(madridCityCard);
    for (int i = 0; i < TOTAL_CITY_CARDS - 15; i++) {
      Card card = new Card(CardType.City, i + "", CubeColor.COLORLESS, 25 + i);
      playerCards.add(card);
    }
    for (int i = 0; i < TOTAL_EVENT_CARDS; i++) {
      playerCards.add(new Card(CardType.Event, "Event" + i));
    }

    return new Deck(playerCards);
  }

  @Override
  public Deck initializeInfectionDeck() {
    List<Card> infectionCards = new ArrayList<Card>();

    Card atlantaInfectionCard = new Card(CardType.Infection, "Atlanta", CubeColor.BLUE);
    infectionCards.add(atlantaInfectionCard);
    Card chicagoInfectionCard = new Card(CardType.Infection, "Chicago", CubeColor.BLUE);
    infectionCards.add(chicagoInfectionCard);
    Card montrealInfectionCard = new Card(CardType.Infection, "Montreal", CubeColor.BLUE);
    infectionCards.add(montrealInfectionCard);
    Card newYorkInfectionCard = new Card(CardType.Infection, "New York", CubeColor.BLUE);
    infectionCards.add(newYorkInfectionCard);
    Card washingtonInfectionCard = new Card(CardType.Infection, "Washington", CubeColor.BLUE);
    infectionCards.add(washingtonInfectionCard);
    Card sanFInfectionCard = new Card(CardType.Infection, "San Francisco", CubeColor.BLUE);
    infectionCards.add(sanFInfectionCard);
    Card londonInfectionCard = new Card(CardType.Infection, "London", CubeColor.YELLOW);
    infectionCards.add(londonInfectionCard);
    Card madridInfectionCard = new Card(CardType.Infection, "Madrid", CubeColor.BLUE);
    infectionCards.add(madridInfectionCard);
    Card miamiInfectionCard = new Card(CardType.Infection, "Miami", CubeColor.YELLOW);
    infectionCards.add(miamiInfectionCard);
    Card mexicoCityInfectionCard = new Card(CardType.Infection, "Mexico City", CubeColor.YELLOW);
    infectionCards.add(mexicoCityInfectionCard);
    Card losAInfectionCard = new Card(CardType.Infection, "Los Angeles", CubeColor.YELLOW);
    infectionCards.add(losAInfectionCard);
    Card bogotaInfectionCard = new Card(CardType.Infection, "Bogota", CubeColor.YELLOW);
    infectionCards.add(bogotaInfectionCard);
    Card limaInfectionCard = new Card(CardType.Infection, "Lima", CubeColor.YELLOW);
    infectionCards.add(limaInfectionCard);
    Card santiagoInfectionCard = new Card(CardType.Infection, "Santiago", CubeColor.YELLOW);
    infectionCards.add(santiagoInfectionCard);
    Card saopauloInfectionCard = new Card(CardType.Infection, "Sao Paulo", CubeColor.YELLOW);
    infectionCards.add(saopauloInfectionCard);
    for (int i = 0; i < TOTAL_CITY_CARDS - 15; i++) {
      infectionCards.add(new Card(CardType.Infection, i + "", CubeColor.COLORLESS));
    }
    return new Deck(infectionCards);
  }

  @Override
  public Deck initializePlayerDiscard() {
    return new Deck(new ArrayList<Card>());
  }

  @Override
  public Deck initializeInfectionDiscard() {
    return new Deck(new ArrayList<Card>());
  }

  public Deck initializeEpidemicCards(Deck playerDeck, int numOfEpidemicCard) {
    List<Card> cards = playerDeck.getCards();
    int factor = cards.size() / numOfEpidemicCard;
    List<Card> completeCards = new ArrayList<Card>();

    for (int i = 0; i < numOfEpidemicCard - 1; i++) {
      List<Card> pile = cards.subList(i * factor, (i + 1) * factor);
      List<Card> newPile = this.populateSinglePile(pile);
      completeCards.addAll(newPile);
    }
    List<Card> lastPile = cards.subList((numOfEpidemicCard - 1) * factor, cards.size());
    List<Card> newPile = this.populateSinglePile(lastPile);
    completeCards.addAll(newPile);
    return new Deck(completeCards);
  }

  private List<Card> populateSinglePile(List<Card> source) {
    List<Card> copyOfPile = new ArrayList<>();
    copyOfPile = this.copyCards(source, copyOfPile);
    copyOfPile = this.addEpidemicCardToSinglePile(copyOfPile);
    return copyOfPile;
  }

  private List<Card> copyCards(List<Card> source, List<Card> destination) {
    for (int i = 0; i < source.size(); i++) {
      destination.add(source.get(i));
    }
    return destination;
  }

  private List<Card> addEpidemicCardToSinglePile(List<Card> cards) {
    cards.add(new Card(CardType.Epidemic));
    Collections.shuffle(cards, new Random());
    return cards;
  }
}