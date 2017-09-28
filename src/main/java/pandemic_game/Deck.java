package pandemic_game;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.ResourceBundle;

public class Deck {

  private static ResourceBundle messages = ResourceBundle.getBundle("MessageBundle");
  
  private List<Card> cards;

  public Deck(List<Card> cards) {
    this.cards = cards;
  }

  public int size() {
    return cards.size();
  }

  public void shuffle(Random num) {
    Collections.shuffle(cards, num);
  }

  public Card drawCard() {
    return drawCardAtPosition(0);
  }

  public Card drawLastCard() {
    return drawCardAtPosition(cards.size() - 1);
  }

  private Card drawCardAtPosition(int pos) {
    try {
      return cards.remove(pos);
    } catch (IndexOutOfBoundsException e) {
      throw new NoSuchElementException(messages.getString("deckNoCardException"));
    }
  }

  public void placeCardsOnTop(Deck deck) {
    Card temp;
    int size = size();

    for (int i = 0; i < size; i++) {
      temp = drawLastCard();
      deck.cards.add(temp);
    }

    List<Card> swap = cards;
    cards = deck.cards;
    deck.cards = swap;
  }

  public void addCard(Card card) {
    cards.add(0, card);
  }

  public void addCardAtLast(Card card) {
    cards.add(card);
  }

  public List<Card> getCards() {
    return this.cards;
  }

  public boolean removeCard(Card cardToRemove) {
    return cards.remove(cardToRemove);
  }
}
