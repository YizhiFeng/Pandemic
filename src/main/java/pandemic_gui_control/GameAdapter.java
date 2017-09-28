package pandemic_gui_control;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.ResourceBundle;

import pandemic_exception.InvalidActionException;
import pandemic_exception.NotEnoughResearchStationsException;
import pandemic_game.Card;
import pandemic_game.City;
import pandemic_game.Constants.CardType;
import pandemic_game.Constants.CubeColor;
import pandemic_game.Constants.Role;
import pandemic_game.Deck;
import pandemic_game.Game;
import pandemic_game.GameLogic;
import pandemic_game.Player;

public class GameAdapter implements GameState {

  private static ResourceBundle messages = ResourceBundle.getBundle("MessageBundle");

  private GameLogic logic;
  private Game game;
  private List<String> researchStations;

  public GameAdapter(GameLogic logic) {
    this.logic = logic;
    game = this.logic.getGame();
    researchStations = new ArrayList<>();
    researchStations.add("Atlanta");
  }

  @Override
  public int getInfectionRate() {
    return game.getInfectionRateIndex() + 1;
  }

  @Override
  public int getOutbreakNumber() {
    return game.getOutbreaks();
  }

  @Override
  public int getNumberOfPlayers() {
    return game.getPlayers().size();
  }

  @Override
  public Player getPlayer(int index) {
    return game.getPlayerAtIndex(index);
  }

  @Override
  public Role getCurrentPlayerRole() {
    return game.getCurrentPlayer().getRole();
  }

  @Override
  public Card[] getCurrentPlayerCards() {
    List<Card> cardList = game.getCurrentPlayer().getCardList();
    Card[] cards = new Card[cardList.size()];
    cardList.toArray(cards);
    return cards;
  }

  @Override
  public boolean isOver() {
    return game.isGameOver();
  }

  @Override
  public List<City> getAdjacentCitiesOfCurrentPlayer() {
    return game.getCurrentPlayer().getCurrentCity().getAdjacentCities();
  }

  @Override
  public List<String> getCitiesWithResearchStations() {
    return researchStations;
  }

  @Override
  public boolean isCityName(String cityName) {
    return game.getCity(cityName) != null;
  }

  @Override
  public void movePlayerByDriveOrFerry(String selectedCity) {
    if (selectedCity.isEmpty()) {
      throw new InvalidActionException(messages.getString("gameAdapterSelectCityException"));
    }
    logic.movePlayerByDriveOrFerry(game.getCity(selectedCity));
  }

  @Override
  public void movePlayerByShuttle(String selectedCity) {
    if (selectedCity.isEmpty()) {
      throw new InvalidActionException(messages.getString("gameAdapterSelectCityException"));
    }
    logic.movePlayerByShuttleFlight(game.getCity(selectedCity));
  }

  @Override
  public void movePlayerByCharterFlight(String selectedCity) {
    if (selectedCity.isEmpty()) {
      throw new InvalidActionException(messages.getString("gameAdapterSelectCityException"));
    }
    logic.movePlayerByCharterFlight(game.getCity(selectedCity));
  }

  @Override
  public void movePlayerByDirectFlight(String selectedCity) {
    if (selectedCity.isEmpty()) {
      throw new InvalidActionException(messages.getString("gameAdapterSelectCityException"));
    }
    logic.movePlayerByDirectFlight(game.getCity(selectedCity));
  }

  @Override
  public void buildResearchStation() {
    if (game.getTotalResearchStationLeft() > 0) {
      logic.buildResearchStation();
      researchStations.add(game.getCurrentPlayer().getCurrentCity().getName());
    } else {
      String message = messages.getString("removeStationException");
      throw new NotEnoughResearchStationsException(message);
    }
  }

  @Override
  public void buildResearchStation(String city) {
    logic.moveResearchStation(game.getCity(city));
    researchStations.remove(city);
    researchStations.add(game.getCurrentPlayer().getCurrentCity().getName());
  }

  @Override
  public List<City> getDiseasedCities() {
    Map<String, City> cityMap = game.getCityMap();
    Collection<City> cities = cityMap.values();
    Iterator<City> iter = cities.iterator();
    City tempCity = null;
    List<City> cityList = new ArrayList<City>();
    while (iter.hasNext()) {
      tempCity = iter.next();
      if (hasCube(tempCity)) {
        cityList.add(tempCity);
      }
    }
    return cityList;
  }

  private boolean hasCube(City city) {
    return (city.getCubeCount(CubeColor.RED) + city.getCubeCount(CubeColor.BLUE)
        + city.getCubeCount(CubeColor.BLACK) + city.getCubeCount(CubeColor.YELLOW)) > 0;
  }

  @Override
  public void treatDisease(CubeColor color) {
    if (game.getCurrentPlayer().getRole().equals(Role.Medic)) {
      logic.treatCityByMedic(getCurrentPlayer(), color);
    } else {
      logic.removeOneDiseaseCube(color);
    }
  }

  @Override
  public String[] getCuredDiseases() {
    List<String> curedColors = findCuredDiseases();
    String[] colors = new String[curedColors.size()];
    curedColors.toArray(colors);
    return colors;
  }

  private List<String> findCuredDiseases() {
    CubeColor[] cubes = { CubeColor.YELLOW, CubeColor.RED, CubeColor.BLACK, CubeColor.BLUE };
    List<String> curedColors = new ArrayList<>();
    for (CubeColor color : cubes) {
      if (game.isDiseaseCured(color)) {
        curedColors.add(color.toString());
      }
    }
    return curedColors;
  }

  @Override
  public String[] getEradicatedDiseases() {
    List<String> curedColors = findEradicatedDiseases();
    String[] colors = new String[curedColors.size()];
    curedColors.toArray(colors);
    return colors;
  }

  private List<String> findEradicatedDiseases() {
    CubeColor[] cubes = { CubeColor.YELLOW, CubeColor.RED, CubeColor.BLACK, CubeColor.BLUE };
    List<String> curedColors = new ArrayList<>();
    for (CubeColor color : cubes) {
      if (logic.isEradicated(color)) {
        curedColors.add(color.toString());
      }
    }
    return curedColors;
  }

  @Override
  public CubeColor[] cubeColorsOnCurrCity() {
    return cubeColorsOnCity(game.getCurrentPlayer().getCurrentCity());
  }

  @Override
  public CubeColor[] cubeColorsOnCity(City city) {
    List<CubeColor> colors = new ArrayList<CubeColor>();
    if (city.getCubeCount(CubeColor.BLUE) > 0) {
      colors.add(CubeColor.BLUE);
    }
    if (city.getCubeCount(CubeColor.RED) > 0) {
      colors.add(CubeColor.RED);
    }
    if (city.getCubeCount(CubeColor.BLACK) > 0) {
      colors.add(CubeColor.BLACK);
    }
    if (city.getCubeCount(CubeColor.YELLOW) > 0) {
      colors.add(CubeColor.YELLOW);
    }
    CubeColor[] colorArray = new CubeColor[colors.size()];
    colors.toArray(colorArray);
    return colorArray;
  }

  @Override
  public int getPlayerActionsLeft() {
    return game.getPlayerActionsLeft();
  }

  @Override
  public void changeTurn() {
    game.changeTurn();
  }

  @Override
  public Player getCurrentPlayer() {
    return game.getCurrentPlayer();
  }

  @Override
  public Card drawFromPlayerDeck() {
    return logic.drawFromPlayerDeck();
  }

  @Override
  public void epidemicCardDrawn() {
    logic.epidemicCardDrawn(new Random());
  }

  @Override
  public void doInfectionPhase() {
    logic.playInfectorPhase();
  }

  @Override
  public void discard(Player playerWithCard, Card card) {
    for (Player player : game.getPlayers()) {
      if (player.equals(playerWithCard)) {
        player.removeCard(card);
        return;
      }
    }
  }

  @Override
  public void passCard(Player player) {
    List<Player> players = game.getPlayers();
    for (int i = 0; i < players.size(); i++) {
      if (players.get(i).equals(player)) {
        logic.passCard(i);
      }
    }
  }

  @Override
  public Card[] getResearcherCards() {
    Player player = getResearcher();
    List<Card> cardsList = player.getCardList();
    Card[] cards = new Card[cardsList.size()];
    cardsList.toArray(cards);
    return cards;
  }

  private Player getResearcher() {
    List<Player> players = game.getPlayers();
    for (Player player : players) {
      if (player.getRole().equals(Role.Researcher)) {
        return player;
      }
    }
    String message = messages.getString("noResearcherException");
    throw new InvalidActionException(message);
  }

  @Override
  public void researcherPassCard(String string, Card card) {
    for (Player player : game.getPlayers()) {
      if (player.getRole().toString().equals(string)) {
        logic.passSpecificCardByResearcher(getResearcher(), player, card);
        return;
      }
    }
  }

  @Override
  public void dispatcherMovePlayers(String string, String selectedCity) {
    int playerIndex = 0;
    for (Player player : game.getPlayers()) {
      if (player.getRole().toString().equals(string)) {
        logic.movePlayerByDispatcher(playerIndex, game.getCity(selectedCity));
      }
      playerIndex++;
    }
  }

  @Override
  public Card[] getPlayerEventCards(int chosenPlayer) {
    List<Card> cardsList = getPlayer(chosenPlayer).getCardList();
    List<Card> eventCardList = findEventCards(cardsList);
    if (eventCardList.size() == 0) {
      throw new InvalidActionException(messages.getString("playHasNoEventCards"));
    }
    Card[] cards = new Card[eventCardList.size()];
    eventCardList.toArray(cards);
    return cards;
  }

  private List<Card> findEventCards(List<Card> cardList) {
    List<Card> eventCardList = new ArrayList<>();
    for (Card card : cardList) {
      if (card.getType() == CardType.Event) {
        eventCardList.add(card);
      }
    }
    return eventCardList;
  }

  @Override
  public void airliftPlayer(Player cardOwner, String cityName, Player movePlayer, Card eventCard) {
    logic.playAirlift(cardOwner, eventCard, movePlayer, game.getCity(cityName));
  }

  @Override
  public void oneQuietNightPlayer(Player cardOwner, Card card) {
    List<Player> players = game.getPlayers();
    int nextPlayerIndex = 0;
    for (int i = 0; i < players.size(); i++) {
      if (players.get(i) == this.getCurrentPlayer()) {
        nextPlayerIndex = (i + 1) % players.size();
      }
    }
    logic.playOneQuietNight(cardOwner, card, players.get(nextPlayerIndex));
  }

  @Override
  public void governmentGrantCity(Player cardOwner, Card card, String cityName) {
    logic.playGovernmentGrantAndBuild(cardOwner, card, game.getCity(cityName));
    researchStations.add(cityName);
  }

  @Override
  public void governmentGrantRemoveStation(Player cardOwner, Card card, String cityName,
      String stationRemove) {
    logic.playGovernmentGrantAndMove(cardOwner, card, game.getCity(stationRemove),
        game.getCity(cityName));
    researchStations.remove(stationRemove);
    researchStations.add(cityName);
  }

  @Override
  public Card[] getInfectionDiscardCards() {
    Deck infectionDiscardDeck = game.getInfectionDiscardDeck();
    List<Card> infectionCards = infectionDiscardDeck.getCards();
    if (infectionCards.size() == 0) {
      throw new InvalidActionException(messages.getString("infectionDiscardDeckEmpty"));
    }
    Card[] cards = new Card[infectionCards.size()];
    infectionCards.toArray(cards);
    return cards;
  }

  @Override
  public void resilientPopulationCard(Player cardOwner, Card eventCard, Card infectionCard) {
    logic.playResilientPopulation(cardOwner, eventCard, infectionCard);
  }

  @Override
  public boolean hasEnoughCardsToCureDisease() {
    return logic.hasEnoughCardsToCureDisease();
  }

  @Override
  public void cureDisease() {
    logic.cureDisease();
  }

  @Override
  public void cureDisease(Card[] cardsToCureDisease) {
    List<Card> cureCards = new ArrayList<>();
    for (Card card : cardsToCureDisease) {
      cureCards.add(card);
    }
    logic.cureDisease(cureCards);
  }

  @Override
  public Card[] getTopSixInfectionCards() {
    Deck infectionDeck = game.getInfectionDeck();
    List<Card> infectionCards = new ArrayList<>();
    for (int i = 0; i < 6; i++) {
      infectionCards.add(infectionDeck.drawCard());
    }
    Card[] topCards = new Card[infectionCards.size()];
    infectionCards.toArray(topCards);
    return topCards;
  }

  @Override
  public void placeForecastCards(Player cardOwner, Card card, Card[] forecast) {
    List<Card> forecastedCards = new ArrayList<>();
    for (int i = 0; i < forecast.length; i++) {
      forecastedCards.add(forecast[i]);
    }
    logic.playForecast(cardOwner, card, forecastedCards);
  }

  @Override
  public CubeColor findColorToCure() {
    Map<CubeColor, Integer> colorCount = new HashMap<>();
    colorCount.put(CubeColor.RED, 0);
    colorCount.put(CubeColor.BLUE, 0);
    colorCount.put(CubeColor.BLACK, 0);
    colorCount.put(CubeColor.YELLOW, 0);
    List<Card> playerHand = game.getCurrentPlayer().getCardList();
    CubeColor cardColor = null;
    for (Card card : playerHand) {
      cardColor = card.getColor();
      colorCount.put(cardColor, colorCount.get(cardColor) + 1);
      if (colorCount.get(cardColor) == 3) {
        return cardColor;
      }
    }
    throw new InvalidActionException(messages.getString("noColorToTreat"));
  }

  @Override
  public int getNumCardsToCure() {
    if (getCurrentPlayerRole() == Role.Scientist) {
      return 4;
    }
    return 5;
  }

  @Override
  public boolean checkTurnOver() {
    return game.checkTurnOver();
  }
}
