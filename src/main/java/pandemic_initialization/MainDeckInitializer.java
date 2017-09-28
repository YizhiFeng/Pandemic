package pandemic_initialization;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import pandemic_exception.InitializationException;
import pandemic_game.Card;
import pandemic_game.Constants.CardType;
import pandemic_game.Deck;

public class MainDeckInitializer implements DeckInitializer {

  private static ResourceBundle messages = ResourceBundle.getBundle("MessageBundle");
  
  private CardBuilder builder = new CardBuilder();

  @Override
  public Deck initializePlayerDeck() {
    List<Card> cards = createCityCards();
    cards.addAll(createEventCards());
    Deck deck = new Deck(cards);
    deck.shuffle(new Random());
    return deck;
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

  private List<Card> createCityCards() {
    List<Card> black = generateColoredCityCards("black");
    List<Card> blue = generateColoredCityCards("blue");
    List<Card> red = generateColoredCityCards("red");
    List<Card> yellow = generateColoredCityCards("yellow");
    black.addAll(blue);
    black.addAll(red);
    black.addAll(yellow);
    return black;
  }

  private List<Card> generateColoredCityCards(String color) {
    String path = "cards/city_cards_" + color + ".txt";
    InputStreamReader inputReader = openInputReader(path);
    BufferedReader reader = new BufferedReader(inputReader);
    return builder.loadPandemicCards(reader);
  }

  private List<Card> createEventCards() {
    String path = "cards/event_cards.txt";
    InputStreamReader inputReader = openInputReader(path);
    BufferedReader reader = new BufferedReader(inputReader);
    return builder.loadPandemicCards(reader);
  }

  @Override
  public Deck initializeInfectionDeck() {
    List<Card> cards = createInfectionCards();
    Deck deck = new Deck(cards);
    deck.shuffle(new Random());
    return deck;
  }

  private List<Card> createInfectionCards() {
    List<Card> black = generateColoredInfectionCards("black");
    List<Card> blue = generateColoredInfectionCards("blue");
    List<Card> red = generateColoredInfectionCards("red");
    List<Card> yellow = generateColoredInfectionCards("yellow");
    black.addAll(blue);
    black.addAll(red);
    black.addAll(yellow);
    return black;
  }

  private List<Card> generateColoredInfectionCards(String color) {
    String path = "cards/infection_cards_" + color + ".txt";
    InputStreamReader inputReader = openInputReader(path);
    BufferedReader reader = new BufferedReader(inputReader);
    return builder.loadPandemicCards(reader);
  }

  private InputStreamReader openInputReader(String path) {
    try {
      return new InputStreamReader(getClass().getClassLoader().getResourceAsStream(path), "UTF-8");
    } catch (UnsupportedEncodingException e) {
      throw new InitializationException(messages.getString("unableToLoadCardsException"));
    }
  }

  @Override
  public Deck initializePlayerDiscard() {
    return new Deck(new ArrayList<Card>());
  }

  @Override
  public Deck initializeInfectionDiscard() {
    return new Deck(new ArrayList<Card>());
  }

}
