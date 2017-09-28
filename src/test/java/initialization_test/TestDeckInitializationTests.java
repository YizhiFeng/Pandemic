package initialization_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import pandemic_game.Constants.CardType;
import pandemic_game.Deck;
import pandemic_initialization.DeckInitializer;
import pandemic_initialization.TestDeckInitializer;

public class TestDeckInitializationTests {

  @Test
  public void test48CityCards() {
    DeckInitializer deckInit = new TestDeckInitializer();
    Deck deck = deckInit.initializePlayerDeck();
    int count = 0;
    int size = deck.size();
    for (int i = 0; i < size; i++) {
      if (deck.drawCard().getType().equals(CardType.City)) {
        count++;
      }
    }
    assertEquals(48, count);
  }

  @Test
  public void testFourEpidemicCards() {
    DeckInitializer deckInit = new TestDeckInitializer();
    Deck deck = deckInit.initializePlayerDeck();
    deck = deckInit.initializeEpidemicCards(deck, 4);
    int count = 0;
    int size = deck.size();
    for (int i = 0; i < size; i++) {
      if (deck.drawCard().getType().equals(CardType.Epidemic)) {
        count++;
      }
    }
    assertTrue(count == 4);
  }
  
  @Test
  public void testFiveEpidemicCards() {
    DeckInitializer deckInit = new TestDeckInitializer();
    Deck deck = deckInit.initializePlayerDeck();
    deck = deckInit.initializeEpidemicCards(deck, 5);
    int count = 0;
    int size = deck.size();
    for (int i = 0; i < size; i++) {
      if (deck.drawCard().getType().equals(CardType.Epidemic)) {
        count++;
      }
    }
    assertTrue(count == 5);
  }

  @Test
  public void testExactly5EventCards() {
    DeckInitializer deckInit = new TestDeckInitializer();
    Deck deck = deckInit.initializePlayerDeck();
    int count = 0;
    int size = deck.size();
    for (int i = 0; i < size; i++) {
      if (deck.drawCard().getType().equals(CardType.Event)) {
        count++;
      }
    }
    assertEquals(5, count);
  }

  @Test
  public void testEmptyInfectionDiscard() {
    DeckInitializer deckInit = new TestDeckInitializer();
    Deck deck = deckInit.initializeInfectionDiscard();
    assertEquals(0, deck.size());
  }

  @Test
  public void testEmptyPlayerDiscard() {
    DeckInitializer deckInit = new TestDeckInitializer();
    Deck deck = deckInit.initializePlayerDiscard();
    assertEquals(0, deck.size());
  }

  @Test
  public void testExactly48InfectionCards() {
    DeckInitializer deckInit = new TestDeckInitializer();
    Deck deck = deckInit.initializeInfectionDeck();
    assertEquals(48, deck.size());
  }

}
