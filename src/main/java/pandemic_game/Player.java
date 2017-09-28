package pandemic_game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import pandemic_exception.InvalidActionException;
import pandemic_exception.TooManyCardsInSameColorException;
import pandemic_game.Constants.CubeColor;
import pandemic_game.Constants.Role;

public class Player {

  private static ResourceBundle messages = ResourceBundle.getBundle("MessageBundle");

  private City currentCity;
  private Map<String, Card> cards = new HashMap<>();
  private Map<CubeColor, Integer> numberOfCardsInEachColor = new HashMap<>();
  private Role role;

  public Player(City city) {
    this.currentCity = city;
    this.numberOfCardsInEachColor.put(CubeColor.BLACK, 0);
    this.numberOfCardsInEachColor.put(CubeColor.BLUE, 0);
    this.numberOfCardsInEachColor.put(CubeColor.RED, 0);
    this.numberOfCardsInEachColor.put(CubeColor.YELLOW, 0);
  }

  public void driveOrFerry(City destination) {
    if (this.currentCity.getAdjacentCities().contains(destination)) {
      this.currentCity = destination;
    } else {
      throw new InvalidActionException(messages.getString("playerDriveOrFerryExceptionFirstHalf")
          + destination.getName() + messages.getString("playerDriveOrFerryExceptionSecondHalf"));
    }
  }

  public void directFlight(City destination, Deck playerDiscardDeck) {
    if (this.currentCity.getAdjacentCities().contains(destination)) {
      this.currentCity = destination;
    } else if (this.cards.containsKey(destination.getName())) {
      this.currentCity = destination;
      playerDiscardDeck.addCard(this.removeCard(this.cards.get(destination.getName())));
    } else {
      throw new InvalidActionException(messages.getString("playerDirectFlightExceptionFirstHalf")
          + destination.getName() + messages.getString("playerDirectFlightExceptionSecondHalf"));
    }
  }

  public void charterFlight(City destination, Deck playerDiscardDeck) {
    if (this.currentCity.getName().equals(destination.getName())) {
      throw new InvalidActionException(messages.getString("playerCharterFlightSameCityException"));
    } else if (this.currentCity.getAdjacentCities().contains(destination)) {
      this.currentCity = destination;
    } else if (this.cards.containsKey(this.currentCity.getName())) {
      playerDiscardDeck.addCard(this.removeCard(this.cards.get(this.currentCity.getName())));
      this.currentCity = destination;
    } else {
      throw new InvalidActionException(messages.getString("playerDirectFlightExceptionFirstHalf")
          + destination.getName() + messages.getString("playerDirectFlightExceptionSecondHalf"));
    }
  }

  public void shuttleFlight(City destination) {
    if (this.currentCity.equals(destination)) {
      throw new InvalidActionException(messages.getString("playerShuttleFlightSameCityException"));
    } else if (!this.currentCity.hasResearchStation()) {
      throw new InvalidActionException(
          messages.getString("playerShuttleFlightCurrentNoResearchStationException"));
    } else if (!destination.hasResearchStation()) {
      throw new InvalidActionException(
          messages.getString("playerShuttleFlightTargetNoResearchStationException"));
    } else {
      this.currentCity = destination;
    }
  }

  public City getCurrentCity() {
    return this.currentCity;
  }

  public void buildResearchStation(Deck playerDiscardDeck) {
    if (this.getCurrentCity().hasResearchStation()) {
      throw new InvalidActionException(messages.getString("playerBuildResearchStationException"));
    } else if (this.role.equals(Role.OperationExpert)) {
      this.currentCity.addResearchStation();
    } else if (this.cards.containsKey(this.currentCity.getName())) {
      this.currentCity.addResearchStation();
      playerDiscardDeck.addCard(this.removeCard(this.cards.get(this.currentCity.getName())));
    } else {
      throw new InvalidActionException(
          messages.getString("playerBuildResearchStationNoCardException"));
    }
  }

  public void treatCurrentCity(CubeColor color) {
    this.currentCity.removeCube(color);
  }

  public void passCard(Player player) {
    if (!player.getCurrentCity().equals(this.getCurrentCity())) {
      throw new InvalidActionException(messages.getString("playerPassCardNotSameCityException"));
    } else if (this.getCards().containsKey(this.getCurrentCity().getName())) {
      Card passedCard = this.removeCard(this.cards.get(this.getCurrentCity().getName()));
      player.addCard(passedCard);
    } else if (player.getCards().containsKey(this.getCurrentCity().getName())) {
      Card passedCard = player.removeCard(player.cards.get(this.getCurrentCity().getName()));
      this.addCard(passedCard);
    } else {
      throw new InvalidActionException(
          messages.getString("playerPassCardBothNoMatchingCardException"));
    }
  }

  public void addCard(Card card) {
    this.cards.put(card.getName(), card);
    CubeColor color = card.getColor();
    if (color != CubeColor.COLORLESS) {
      int currentCount = this.numberOfCardsInEachColor.get(color);
      this.numberOfCardsInEachColor.put(color, ++currentCount);
    }
  }

  public Map<String, Card> getCards() {
    return this.cards;
  }

  public Card removeCard(Card card) {
    this.cards.remove(card.getName());
    CubeColor color = card.getColor();
    if (color != CubeColor.COLORLESS) {
      int currentCount = this.numberOfCardsInEachColor.get(color);
      this.numberOfCardsInEachColor.put(color, --currentCount);
    }
    return card;
  }

  public int getNumberOfCardsInEachColor(CubeColor color) {
    return this.numberOfCardsInEachColor.get(color);
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Role getRole() {
    return this.role;
  }

  public int getHandsSize() {
    return this.cards.size();
  }

  public void setCurrentCity(City newCurrentCity) {
    this.currentCity = newCurrentCity;
  }

  public boolean hasEnoughCardsToDiscoverCure() {
    int handSize = this.cards.size();
    if (handSize < 4) {
      return false;
    } else if ((handSize == 4) && (this.role != Role.Scientist)) {
      return false;
    } else {
      for (Integer numOfCardInColor : this.numberOfCardsInEachColor.values()) {
        if (numOfCardInColor >= 4) {
          if (this.role == Role.Scientist) {
            if (numOfCardInColor == 4) {
              return true;
            }
            throw new TooManyCardsInSameColorException(
                this.role + messages.getString("playerDiscoverCureMoreThanFourCards"));
          } else {
            if (numOfCardInColor == 5) {
              return true;
            }
            throw new TooManyCardsInSameColorException(
                this.role + messages.getString("playerDiscoverCureMoreThanFiveCards"));
          }
        }
      }
    }
    return false;
  }

  public CubeColor discoverCure(Deck playerDiscardDeck, Map<CubeColor, Boolean> curedDisease) {
    CubeColor colorOfDiseaseToCure = CubeColor.COLORLESS;
    int minimumNumOfCards = this.role == Role.Scientist ? 4 : 5;
    for (Entry<CubeColor, Integer> color : this.numberOfCardsInEachColor.entrySet()) {
      if (color.getValue() == minimumNumOfCards) {
        colorOfDiseaseToCure = color.getKey();
        break;
      }
    }
    if (curedDisease.get(colorOfDiseaseToCure)) {
      String message = messages.getString("playerDiscoverCureOnCuredDisease");
      throw new InvalidActionException(message);
    } else {
      List<Card> cardsToRemove = new ArrayList<>();
      for (Card card : this.cards.values()) {
        if (card.getColor().equals(colorOfDiseaseToCure)) {
          cardsToRemove.add(card);
        }
      }
      return this.discoverCure(cardsToRemove, playerDiscardDeck, curedDisease);
    }
  }

  public CubeColor discoverCure(List<Card> cardsToDiscoverCure, Deck playerDiscardDeck,
      Map<CubeColor, Boolean> curedDisease) {
    if (curedDisease.get(cardsToDiscoverCure.get(0).getColor())) {
      String message = messages.getString("playerDiscoverCureOnCuredDisease");
      throw new InvalidActionException(message);
    } else {
      for (Card card : cardsToDiscoverCure) {
        playerDiscardDeck.addCard(this.removeCard(card));
      }
      return cardsToDiscoverCure.get(0).getColor();
    }
  }

  public void passSpecificCardToPlayer(Player player, Card card) {
    if (!player.currentCity.equals(this.currentCity)) {
      throw new InvalidActionException(messages.getString("playerPassCardNotSameCityException"));
    }
    Card passedCard = this.removeCard(this.cards.get(card.getName()));
    player.addCard(passedCard);
  }

  public void removeAllCubes(CubeColor color) {
    if (this.role.equals(Role.Medic)) {
      int numOfCubes = this.currentCity.getCubeCount(color);
      for (int i = 0; i < numOfCubes; i++) {
        this.currentCity.removeCube(color);
      }
    }
  }

  public int getTopPopulationInHands() {
    int topPopulation = 0;
    for (Card card : this.getCards().values()) {
      int currentPopulation = card.getPopulation();
      if (currentPopulation > topPopulation) {
        topPopulation = currentPopulation;
      }
    }
    return topPopulation;
  }

  public List<Card> getCardList() {
    List<Card> cardList = this.cards.entrySet().stream().map(x -> x.getValue())
        .collect(Collectors.toList());
    return cardList;
  }

}
