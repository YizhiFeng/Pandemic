package pandemic_initialization;

import pandemic_game.Deck;

public interface DeckInitializer {
  Deck initializePlayerDeck();

  Deck initializeEpidemicCards(Deck playerDeck, int numOfEpidemicCard);

  Deck initializeInfectionDeck();

  Deck initializePlayerDiscard();

  Deck initializeInfectionDiscard();
}
