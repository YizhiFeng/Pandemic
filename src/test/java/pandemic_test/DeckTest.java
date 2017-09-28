package pandemic_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

import org.easymock.EasyMock;
import org.junit.Test;

import pandemic_game.Card;
import pandemic_game.Deck;

public class DeckTest {

  private List<Card> createCardList(int numOfCards) {
    ArrayList<Card> cards = new ArrayList<>();
    for (int i = 0; i < numOfCards; i++) {
      cards.add(EasyMock.mock(Card.class));
    }
    return cards;
  }

  @Test
  public void testShuffleDeck() {
    List<Card> cards = createCardList(3);
    List<Card> checkCards = createCardList(3);
    Collections.copy(checkCards, cards);
    Deck deck = new Deck(cards);
    Deck checkDeck = new Deck(checkCards);
    deck.shuffle(new Random(5));
    assertFalse(deck.drawCard().equals(checkDeck.drawCard()));
  }

  @Test
  public void testDrawTopCardOneCard() {
    List<Card> cards = createCardList(1);
    Deck deck = new Deck(cards);

    assertEquals(cards.get(0), deck.drawCard());
  }

  @Test
  public void testDrawTopCardTwoCards() {
    List<Card> cards = createCardList(2);
    Deck deck = new Deck(cards);

    // Expectation is that the top of the deck is position 0
    // and that cards are removed as they are drawn
    assertEquals(cards.get(0), deck.drawCard());
    assertEquals(cards.get(0), deck.drawCard());
  }

  @Test(expected = NoSuchElementException.class)
  public void testDrawCardNoCards() {
    List<Card> cards = createCardList(0);
    Deck deck = new Deck(cards);

    deck.drawCard();
  }

  @Test
  public void testSizeNoCards() {
    List<Card> cards = createCardList(0);
    Deck deck = new Deck(cards);

    assertEquals(0, deck.size());
  }

  @Test
  public void testSizeOneCard() {
    List<Card> cards = createCardList(1);
    Deck deck = new Deck(cards);

    assertEquals(1, deck.size());
  }

  @Test(expected = NoSuchElementException.class)
  public void testDrawLastCardNoCards() {
    List<Card> cards = createCardList(0);
    Deck deck = new Deck(cards);

    deck.drawLastCard();
    assertEquals(0, deck.size());
  }

  @Test
  public void testDrawLastCardOneCard() {
    List<Card> cards = createCardList(1);
    Deck deck = new Deck(cards);

    assertEquals(cards.get(0), deck.drawLastCard());
    assertEquals(0, deck.size());
  }

  @Test
  public void testDrawLastCardTwoCards() {
    List<Card> cards = createCardList(3);
    Deck deck = new Deck(cards);

    assertEquals(cards.get(2), deck.drawLastCard());
    assertEquals(2, deck.size());
  }

  @Test
  public void testPlaceCardsOnTopNonemptyDecks() {
    List<Card> cards1 = createCardList(2);
    List<Card> cards2 = createCardList(2);
    List<Card> cards2Copy = createCardList(2);
    Collections.copy(cards2Copy, cards2);

    Deck deck1 = new Deck(cards1);
    Deck deck2 = new Deck(cards2);

    deck1.placeCardsOnTop(deck2);

    assertEquals(4, deck1.size());
    assertEquals(0, deck2.size());
    assertEquals(cards2Copy.get(0), deck1.drawCard());
  }

  @Test
  public void testPlaceCardsOnTopBothEmpty() {
    List<Card> cards1 = createCardList(0);
    List<Card> cards2 = createCardList(0);

    Deck deck1 = new Deck(cards1);
    Deck deck2 = new Deck(cards2);

    deck1.placeCardsOnTop(deck2);

    assertEquals(0, deck1.size());
    assertEquals(0, deck2.size());
  }

  @Test
  public void testPlaceCardsOnTopOneEmpty() {
    List<Card> cards1 = createCardList(0);
    List<Card> cards2 = createCardList(1);

    Deck deck1 = new Deck(cards1);
    Deck deck2 = new Deck(cards2);

    // Deck1 is empty case
    deck1.placeCardsOnTop(deck2);

    assertEquals(1, deck1.size());
    assertEquals(0, deck2.size());

    // Deck2 is empty case
    deck1.placeCardsOnTop(deck2);

    assertEquals(1, deck1.size());
    assertEquals(0, deck2.size());
  }

  @Test
  public void testAddCardSingleCard() {
    Deck deck = new Deck(createCardList(0));
    Card card = EasyMock.mock(Card.class);
    deck.addCard(card);
    assertEquals(card, deck.drawCard());
  }

  @Test
  public void testAddCardAtLast() {
    Deck deck = new Deck(createCardList(3));
    Card card = EasyMock.mock(Card.class);
    deck.addCardAtLast(card);
    assertEquals(card, deck.drawLastCard());
  }

  @Test
  public void testGetCardList() {
    Deck deck = new Deck(createCardList(3));
    assertEquals(3, deck.getCards().size());
  }

  @Test
  public void testRemoveCard() {
    Card mockedCard1 = EasyMock.createMock(Card.class);
    Card mockedCard2 = EasyMock.createMock(Card.class);
    EasyMock.replay(mockedCard1, mockedCard2);
    List<Card> cards = new ArrayList<>();
    cards.add(mockedCard1);
    cards.add(mockedCard2);
    Deck deck = new Deck(cards);

    assertEquals(2, deck.getCards().size());
    assertTrue(deck.removeCard(mockedCard1));
    assertFalse(deck.getCards().contains(mockedCard1));
    EasyMock.verify(mockedCard1, mockedCard2);

  }

  @Test
  public void testRemoveNonExistedCard() {
    Card mockedCard1 = EasyMock.createMock(Card.class);
    Card mockedCard2 = EasyMock.createMock(Card.class);
    Card mockedCard3 = EasyMock.createMock(Card.class);
    EasyMock.replay(mockedCard1, mockedCard2, mockedCard3);

    List<Card> cards = new ArrayList<>();
    cards.add(mockedCard1);
    cards.add(mockedCard2);
    Deck deck = new Deck(cards);

    assertEquals(2, deck.getCards().size());
    assertFalse(deck.removeCard(mockedCard3));
    EasyMock.verify(mockedCard1, mockedCard2, mockedCard3);

  }
}
