
package pandemic_gui_control;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import pandemic_exception.GameOverException;
import pandemic_exception.GameVictoryException;
import pandemic_exception.InvalidActionException;
import pandemic_exception.NotEnoughResearchStationsException;
import pandemic_exception.TooManyCardsInSameColorException;
import pandemic_game.Card;
import pandemic_game.City;
import pandemic_game.Constants.CardType;
import pandemic_game.Constants.CubeColor;
import pandemic_game.Constants.Role;
import pandemic_game.Player;

public class GameControl implements GuiListener {

  private static final ResourceBundle messages = ResourceBundle.getBundle("MessageBundle");
  private GameState game;
  private Gui gui;
  private String selectedCity;

  public GameControl(GameState pandemicGame, Gui gui) {
    game = pandemicGame;
    this.gui = gui;
    selectedCity = "";
  }

  public void start() {
    gui.setGuiListener(this);
  }

  @Override
  public void onMapSelected(String itemClicked) {
    if (game.isCityName(itemClicked)) {
      selectedCity = itemClicked;
    } else {
      selectedCity = "";
    }
    drawSelectedCityCircle();
  }

  @Override
  public void onResize() {
    drawAllPlayers();
    drawAllDiseaseCubes();
    drawAllResearchStations();
    drawInfectionMarker();
    drawOutbreakMarker();
    drawSelectedCityCircle();
    drawPlayerRole();
    drawPlayerCards();
    drawCuredDiseases();
    drawEradicatedDiseases();
    drawAllDecks();
  }

  private void drawAllPlayers() {
    for (int i = 0; i < game.getNumberOfPlayers(); i++) {
      drawPlayer(game.getPlayer(i));
    }
  }

  private void drawAllDiseaseCubes() {
    List<City> diseasedCities = game.getDiseasedCities();
    for (City city : diseasedCities) {
      drawDiseaseCubesOnCity(city);
    }
  }

  private void drawAllResearchStations() {
    List<String> cityNames = game.getCitiesWithResearchStations();
    gui.drawResearchStations(cityNames.toArray(new String[0]));
  }

  private void drawInfectionMarker() {
    int infectionRate = game.getInfectionRate();
    gui.drawInfectionRateMarker(infectionRate);
  }

  private void drawOutbreakMarker() {
    int outbreaks = game.getOutbreakNumber();
    gui.drawOutbreakMarker(outbreaks);
  }

  private void drawSelectedCityCircle() {
    gui.drawSelectedCityCircle(selectedCity);
  }

  private void drawPlayerRole() {
    gui.drawPlayerRole(game.getCurrentPlayerRole());
  }

  private void drawPlayerCards() {
    Card[] cards = game.getCurrentPlayerCards();
    String[] cardNames = new String[cards.length];
    for (int i = 0; i < cards.length; i++) {
      cardNames[i] = cards[i].getName();
    }
    gui.drawPlayerCards(cardNames);
  }

  private void drawCuredDiseases() {
    gui.drawCuredDiseases(game.getCuredDiseases());
  }

  private void drawEradicatedDiseases() {
    gui.drawEradicatedDiseases(game.getEradicatedDiseases());
  }

  private void drawAllDecks() {
    gui.drawAllDecks();
  }

  private void drawPlayer(Player player) {
    gui.drawPlayer(player.getRole(), player.getCurrentCity().getName());
  }

  private void drawDiseaseCubesOnCity(City city) {
    CubeColor[] colors = game.cubeColorsOnCity(city);
    int[] cubeCounts = new int[colors.length];
    for (int i = 0; i < colors.length; i++) {
      cubeCounts[i] = city.getCubeCount(colors[i]);
    }
    gui.drawCubesOnCity(cubeCounts, colors, city.getName());
  }

  @Override
  public void onCarAndFerryTravel() {
    try {
      game.movePlayerByDriveOrFerry(selectedCity);
      checkTurnOver();
    } catch (InvalidActionException except) {
      gui.displayError(except);
    }
  }

  @Override
  public void onShuttleTravel() {
    try {
      game.movePlayerByShuttle(selectedCity);
      checkTurnOver();
    } catch (InvalidActionException except) {
      gui.displayError(except);
    }
  }

  @Override
  public void onCharterFlight() {
    try {
      game.movePlayerByCharterFlight(selectedCity);
      checkTurnOver();
      drawPlayerCards();
    } catch (InvalidActionException except) {
      gui.displayError(except);
    }
  }

  @Override
  public void onDirectFlight() {
    try {
      game.movePlayerByDirectFlight(selectedCity);
      checkTurnOver();
      drawPlayerCards();
    } catch (InvalidActionException except) {
      gui.displayError(except);
    }
  }

  @Override
  public void onBuildResearchStation() {
    try {
      game.buildResearchStation();
      drawAllResearchStations();
      checkTurnOver();
      drawPlayerCards();
    } catch (NotEnoughResearchStationsException except) {
      selectResearchStationToRemove();
    } catch (InvalidActionException except) {
      gui.displayError(except);
    }
  }

  private void selectResearchStationToRemove() {
    List<String> citiesWithStations = game.getCitiesWithResearchStations();
    String[] cities = citiesWithStations.toArray(new String[0]);
    int chosen = gui.displayResearchStationOptions(cities);
    if (chosen != -1) {
      game.buildResearchStation(cities[chosen]);
    }
    drawAllResearchStations();
  }

  @Override
  public void onTreatDisease() {
    try {
      treatDisease();
      drawDiseaseCubesOnCity(game.getCurrentPlayer().getCurrentCity());
      drawEradicatedDiseases();
      checkTurnOver();
    } catch (InvalidActionException except) {
      gui.displayError(except);
    }
  }

  private void treatDisease() {
    CubeColor[] colors = game.cubeColorsOnCurrCity();
    if (colors.length == 0) {
      throw new InvalidActionException(messages.getString("noCubeOnThisCity"));
    } else if (colors.length == 1) {
      game.treatDisease(colors[0]);
    } else {
      int chosen = askUserForColor(colors);
      if (chosen == -1) {
        return;
      }
      game.treatDisease(colors[chosen]);
    }
  }

  private int askUserForColor(CubeColor[] colors) {
    String message = messages.getString("selectColorToTreat");
    String title = messages.getString("treatDisease");
    return gui.displayOptions(message, title, colors);
  }

  @Override
  public void onEndTurn() {
    try {
      endTurn();
    } catch (GameOverException except) {
      loseGame();
    }
  }

  private void endTurn() {
    drawFromPlayerDeck();
    game.doInfectionPhase();
    drawAllDiseaseCubes();
    drawInfectionMarker();
    drawOutbreakMarker();
    game.changeTurn();
    drawPlayerCards();
    drawPlayerRole();
  }

  private void drawFromPlayerDeck() {
    Card card = null;
    for (int i = 0; i < 2; i++) {
      card = game.drawFromPlayerDeck();
      if (card.getType() == CardType.Epidemic) {
        epidemicCardDrawn(card);
      } else if (game.getCurrentPlayerCards().length > 7) {
        discardOrPlayCard(game.getCurrentPlayer(), game.getCurrentPlayerCards());
        drawPlayerCards();
      }
    }
  }

  private void loseGame() {
    gui.displayMessage(messages.getString("gameOver"));
    gui.close();
  }

  private void epidemicCardDrawn(Card card) {
    gui.displayMessage(messages.getString("epidemicCardDrawn"));
    game.epidemicCardDrawn();
    game.getCurrentPlayer().removeCard(card);
  }

  @Override
  public void onDiscoverCure() {
    try {
      discoverCure();
      drawPlayerCards();
      drawCuredDiseases();
      drawEradicatedDiseases();
    } catch (InvalidActionException except) {
      except.printStackTrace();
      gui.displayError(except);
    } catch (GameVictoryException except) {
      winGame();
    }
  }

  private void discoverCure() {
    try {
      if (game.hasEnoughCardsToCureDisease()) {
        game.cureDisease();
      }
    } catch (TooManyCardsInSameColorException except) {
      cureDiseaseTooManyCards();
    }
  }

  private void cureDiseaseTooManyCards() {
    Card[] cureCards = getCureCardOptions();
    int numToPick = game.getNumCardsToCure();
    String message = messages.getString("chooseCureCardsMessage");
    Card[] choosenCards = chooseMultipleCards(cureCards, messages.getString("chooseCureCardsTitle"),
        message, message, numToPick);
    if (choosenCards.length != numToPick) {
      return;
    }
    game.cureDisease(choosenCards);
  }

  private Card[] getCureCardOptions() {
    CubeColor cureColor = game.findColorToCure();
    Card[] playerHand = game.getCurrentPlayerCards();
    List<Card> optionCards = new ArrayList<>();
    for (Card card : playerHand) {
      if (card.getColor() == cureColor) {
        optionCards.add(card);
      }
    }
    return optionCards.toArray(new Card[0]);
  }

  private void winGame() {
    gui.displayMessage(messages.getString("won"));
    gui.close();
  }

  private void checkTurnOver() {
    drawPlayer(game.getCurrentPlayer());
    if (game.checkTurnOver()) {
      onEndTurn();
    }
  }

  @Override
  public void onTradeCard() {
    try {
      passCard();
      checkTooManyCards();
      checkTurnOver();
      drawPlayerCards();
    } catch (InvalidActionException except) {
      gui.displayError(except);
    }
  }

  private void passCard() {
    List<Player> players = findOtherPlayers();
    if (players.size() == 1) {
      game.passCard(players.get(0));
    } else {
      String message = messages.getString("selectPlayerToTradeWith");
      int chosen = gui.displayPlayerOptions(message, createRoleLabels(players));
      game.passCard(players.get(chosen));
    }
  }

  private List<Player> findOtherPlayers() {
    int numPlayers = game.getNumberOfPlayers();
    Player otherPlayer = null;
    List<Player> players = new ArrayList<>();
    for (int i = 0; i < numPlayers; i++) {
      otherPlayer = game.getPlayer(i);
      if (!game.getCurrentPlayer().equals(otherPlayer)) {
        players.add(otherPlayer);
      }
    }
    return players;
  }

  private void checkTooManyCards() {
    List<Card> cardsList = Collections.emptyList();
    for (int i = 0; i < game.getNumberOfPlayers(); i++) {
      cardsList = game.getPlayer(i).getCardList();
      if (cardsList.size() > 7) {
        Card[] cards = new Card[cardsList.size()];
        cardsList.toArray(cards);
        discardOrPlayCard(game.getPlayer(i), cards);
      }
    }
  }

  private void discardOrPlayCard(Player player, Card[] cards) {
    String[] labels = getCardNames(cards);
    int chosen = -1;

    String message = messages.getString("selectCardToDiscard");
    String title = messages.getString("discardCard");
    while (chosen == -1) {
      chosen = gui.displayCardOptions(message, title, labels);
    }
    game.discard(player, cards[chosen]);
  }

  private String[] getCardNames(Card[] cards) {
    String[] labels = new String[cards.length];
    for (int i = 0; i < cards.length; i++) {
      labels[i] = cards[i].getName().toString();
    }
    return labels;
  }

  private String[] createRoleLabels(List<Player> list) {
    String[] strings = new String[list.size()];
    for (int i = 0; i < strings.length; i++) {
      strings[i] = list.get(i).getRole().toString();
    }
    return strings;
  }

  @Override
  public void onSeePlayersCards() {
    int numPlayers = game.getNumberOfPlayers();
    String[] playerRoles = new String[numPlayers];
    String[][] playerCards = new String[numPlayers][7];
    Player player = null;
    int cardIndex;

    for (int i = 0; i < numPlayers; i++) {
      player = game.getPlayer(i);
      playerRoles[i] = player.getRole().toString();
      cardIndex = 0;
      for (Card card : player.getCardList()) {
        playerCards[i][cardIndex] = card.getName();
        cardIndex++;
      }
    }
    gui.drawAllPlayersAndCards(playerRoles, playerCards);
  }

  @Override
  public void onResearcherTrade() {
    try {
      tradeWithResearcher();
      checkTooManyCards();
      checkTurnOver();
      drawPlayerCards();
    } catch (InvalidActionException except) {
      gui.displayError(except);
    }
  }

  private void tradeWithResearcher() {
    Card[] cards = game.getResearcherCards();
    String[] players = getNonRolePlayers(Role.Researcher);
    int chosenPlayer = choosePlayerForResearcherTrade(players);
    if (chosenPlayer == -1) {
      return;
    }
    int chosenCard = selectCard(cards);
    if (chosenCard == -1) {
      return;
    }
    if (chosenPlayer < -1) {
      game.researcherPassCard(game.getCurrentPlayer().getRole().toString(), cards[chosenCard]);
    } else {
      game.researcherPassCard(players[chosenPlayer], cards[chosenCard]);
    }
  }

  private String[] getNonRolePlayers(Role role) {
    List<Player> players = new ArrayList<>();
    for (int i = 0; i < game.getNumberOfPlayers(); i++) {
      if (!game.getPlayer(i).getRole().equals(role)) {
        players.add(game.getPlayer(i));
      }
    }
    return createRoleLabels(players);
  }

  private int selectCard(Card[] cards) {
    String message = messages.getString("selectCardToPass");
    String title = messages.getString("passCard");
    return gui.displayCardOptions(message, title, getCardNames(cards));
  }

  private int choosePlayerForResearcherTrade(String[] players) {
    boolean currentPlayerResearcher = game.getCurrentPlayerRole().equals(Role.Researcher);
    String message = messages.getString("cardTradePrompt");
    if (currentPlayerResearcher) {
      int chosenPlayer = gui.displayPlayerOptions(message, players);
      return chosenPlayer;
    }
    return -50;
  }

  @Override
  public void onMoveOtherPlayers() {
    try {
      moveOtherPlayers();
      drawAllPlayers();
      checkTurnOver();
    } catch (Exception except) {
      gui.displayError(except);
    }
  }

  private void moveOtherPlayers() {
    if (game.getCurrentPlayer().getRole() != Role.Dispatcher) {
      String message = messages.getString("onlyDispatcherCanMovePlayersException");
      throw new InvalidActionException(message);
    }
    String[] players = getNonRolePlayers(Role.Dispatcher);
    int chosenPlayer = choosePlayer(players, messages.getString("playerMovePrompt"));
    if (chosenPlayer == -1) {
      return;
    }
    game.dispatcherMovePlayers(players[chosenPlayer], selectedCity);
  }

  private int choosePlayer(String[] players, String message) {
    return gui.displayPlayerOptions(message, players);
  }

  @Override
  public void onPlayEventCard() {
    try {
      playEventCard();
      drawPlayerCards();
    } catch (Exception except) {
      System.out.println("don't remove" + except.toString());
      gui.displayError(except);
    }
  }

  public void playEventCard() {
    String[] players = getPlayerLabels();
    int chosenPlayer = choosePlayer(players, messages.getString("playerEventCardPrompt"));
    if (chosenPlayer == -1) {
      return;
    }
    Card[] cards = game.getPlayerEventCards(chosenPlayer);
    int chosenCard = choseFromCardOptions(cards);
    if (chosenCard == -1) {
      return;
    }
    findEvent(cards[chosenCard], game.getPlayer(chosenPlayer));
  }

  private String[] getPlayerLabels() {
    List<Player> players = new ArrayList<>();
    for (int i = 0; i < game.getNumberOfPlayers(); i++) {
      players.add(game.getPlayer(i));
    }
    return createRoleLabels(players);
  }

  private int choseFromCardOptions(Card[] cardOptions) {
    String message = messages.getString("selectCardToPlay");
    String title = messages.getString("playEventCard");
    return gui.displayCardOptions(message, title, getCardNames(cardOptions));
  }

  private void findEvent(Card card, Player cardOwner) {
    switch (card.getName()) {
      case "Airlift":
        handleAirlift(card, cardOwner);
        drawAllPlayers();
        return;
      case "One Quiet Night":
        game.oneQuietNightPlayer(cardOwner, card);
        return;
      case "Government Grant":
        handleGovernmentGrant(card, cardOwner);
        drawAllResearchStations();
        return;
      case "Resilient Population":
        handleResilientPopulation(cardOwner, card);
        return;
      default:
        handleForecast(cardOwner, card);
        return;
    }
  }

  private void handleAirlift(Card card, Player cardOwner) {
    if (selectedCity.isEmpty()) {
      throw new InvalidActionException(messages.getString("noCitySelected"));
    }
    int movePlayer = choosePlayerFromAll(messages.getString("playerMovePrompt"));
    if (movePlayer == -1) {
      return;
    }
    game.airliftPlayer(cardOwner, selectedCity, game.getPlayer(movePlayer), card);
  }

  private int choosePlayerFromAll(String message) {
    String[] players = getPlayerLabels();
    return choosePlayer(players, message);
  }

  private void handleGovernmentGrant(Card card, Player cardOwner) {
    if (selectedCity.isEmpty()) {
      throw new InvalidActionException(messages.getString("noCitySelected"));
    }
    try {
      game.governmentGrantCity(cardOwner, card, selectedCity);
    } catch (NotEnoughResearchStationsException except) {
      governmentGrantRemoveStation(card, cardOwner);
    }
  }

  private void governmentGrantRemoveStation(Card card, Player cardOwner) {
    List<String> stations = game.getCitiesWithResearchStations();
    String[] stationOptions = new String[stations.size()];
    stations.toArray(stationOptions);
    int chosenStation = gui.displayResearchStationOptions(stationOptions);
    if (chosenStation == -1) {
      return;
    }
    game.governmentGrantRemoveStation(cardOwner, card, selectedCity, stationOptions[chosenStation]);
  }

  private void handleResilientPopulation(Player cardOwner, Card card) {
    Card[] infectionCards = game.getInfectionDiscardCards();
    String message = messages.getString("selectCardToDiscard");
    String title = messages.getString("discardCardTitle");
    int chosenCard = gui.displayCardOptions(message, title, getCardNames(infectionCards));
    if (chosenCard == -1) {
      return;
    }
    game.resilientPopulationCard(cardOwner, card, infectionCards[chosenCard]);
  }

  private void handleForecast(Player cardOwner, Card card) {
    Card[] infectionCards = game.getTopSixInfectionCards();
    Card[] newOrder = chooseMultipleCards(infectionCards, messages.getString("forecastTitle"),
        messages.getString("chooseFirstCard"), messages.getString("chooseNextCard"),
        infectionCards.length);
    if (newOrder.length != infectionCards.length) {
      return;
    }
    game.placeForecastCards(cardOwner, card, newOrder);
  }

  private Card[] chooseMultipleCards(Card[] cardOptions, String title, String initialMessage,
      String secondMessage, int numToPick) {
    List<Card> newOrder = new ArrayList<>();
    String message = initialMessage;
    for (int i = 0; i < numToPick; i++) {
      int chosenCard = gui.displayCardOptions(message, title, getCardNames(cardOptions));
      if (chosenCard == -1) {
        return new Card[0];
      }
      newOrder.add(cardOptions[chosenCard]);
      cardOptions = resetCardOptions(cardOptions, chosenCard);
      message = secondMessage;
    }
    return newOrder.toArray(new Card[0]);
  }

  private Card[] resetCardOptions(Card[] oldCards, int discardCard) {
    Card[] newrCards = new Card[oldCards.length - 1];
    int newIndex = 0;
    for (int i = 0; i < oldCards.length; i++) {
      if (i != discardCard) {
        newrCards[newIndex] = oldCards[i];
        newIndex++;
      }
    }
    return newrCards;

  }
}
