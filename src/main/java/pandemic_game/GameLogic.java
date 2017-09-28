package pandemic_game;

import static pandemic_game.Constants.Role.Dispatcher;
import static pandemic_game.Constants.Role.Medic;
import static pandemic_game.Constants.Role.OperationExpert;
import static pandemic_game.Constants.Role.Researcher;
import static pandemic_game.Constants.Role.Scientist;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;

import pandemic_exception.GameOverException;
import pandemic_exception.InvalidActionException;
import pandemic_exception.NotEnoughResearchStationsException;
import pandemic_game.Constants.CardType;
import pandemic_game.Constants.CubeColor;
import pandemic_game.Constants.Role;
import pandemic_initialization.CityInitializer;
import pandemic_initialization.DeckInitializer;

public class GameLogic {

  private static int TOTAL_CUBES_EACH_COLOR = 24;
  private static Role[] PLAYER_ROLES = { Dispatcher, Medic, OperationExpert, Researcher,
      Scientist };
  private static int INITIAL_HANDS_REFERENCE_NUM = 6;
  private static ResourceBundle messages = ResourceBundle.getBundle("MessageBundle");

  private Game game;
  private Set<City> visitedCities = new HashSet<>();

  public GameLogic(Game game) {
    this.game = game;
  }

  public void initialize(DeckInitializer deckInit, CityInitializer cityInit) {
    initializeCityMap(cityInit);
    initializeDiseaseCubes();
    initializePlayerDeck(deckInit);
    initializePlayerDiscardDeck(deckInit);
    initializeInfectionDeck(deckInit);
    initializeInfectionDiscardDeck(deckInit);
    initializePlayers();
    initializePlayersToSkipNextInfectorPhase();
    initializeCureMarkers();
    infectNineCities();
    initializeEpidemicCard(deckInit);
    game.setCurrentPlayerIndex(indexOfPlayerHasTopPopulation());
    game.setPlayerActionsLeft(4);
    game.getCityMap().get("Atlanta").addResearchStation();
    game.setTotalResearchStationLeft(5);
  }

  public void initializeEpidemicCard(DeckInitializer deckInit) {
    game.setPlayerDeck(
        deckInit.initializeEpidemicCards(game.getPlayerDeck(), game.getNumberOfEpidemicCard()));
  }

  public void initializeCityMap(CityInitializer cityInit) {
    game.setCityMap(cityInit.initializeCityMap());
  }

  public void initializeDiseaseCubes() {
    Map<CubeColor, Integer> cubes = new HashMap<>();
    cubes.put(CubeColor.RED, TOTAL_CUBES_EACH_COLOR);
    cubes.put(CubeColor.YELLOW, TOTAL_CUBES_EACH_COLOR);
    cubes.put(CubeColor.BLUE, TOTAL_CUBES_EACH_COLOR);
    cubes.put(CubeColor.BLACK, TOTAL_CUBES_EACH_COLOR);
    game.setCubes(cubes);
  }

  public void initializePlayerDeck(DeckInitializer deckInit) {
    game.setPlayerDeck(deckInit.initializePlayerDeck());
  }

  public void initializePlayerDiscardDeck(DeckInitializer deckInit) {
    game.setPlayerDiscardDeck(deckInit.initializePlayerDiscard());
  }

  public void initializeInfectionDeck(DeckInitializer deckInit) {
    game.setInfectionDeck(deckInit.initializeInfectionDeck());
  }

  public void initializeInfectionDiscardDeck(DeckInitializer deckInit) {
    game.setInfectionDiscardDeck(deckInit.initializeInfectionDiscard());
  }

  public void initializePlayers() {
    List<Player> players = new ArrayList<Player>();
    List<Role> roles = new ArrayList<>(Arrays.asList(PLAYER_ROLES));
    Collections.shuffle(roles);
    for (int i = 0; i < game.getTotalPlayers(); i++) {
      Player newPlayer = new Player(game.getCityMap().get("Atlanta"));
      newPlayer.setRole(roles.get(i));
      initializePlayerHands(newPlayer);
      players.add(newPlayer);
    }
    game.setPlayers(players);
  }

  public void initializePlayerHands(Player player) {
    int numberOfInitalHands = INITIAL_HANDS_REFERENCE_NUM - game.getTotalPlayers();
    for (int i = 0; i < numberOfInitalHands; i++) {
      player.addCard(game.getPlayerDeck().drawCard());
    }
  }

  public void initializePlayersToSkipNextInfectorPhase() {
    Map<Player, Boolean> playersToSkipNextInfectorPhase = new HashMap<>();
    for (Player player : game.getPlayers()) {
      playersToSkipNextInfectorPhase.put(player, false);
    }
    game.setPlayersToSkipNextInfectorPhase(playersToSkipNextInfectorPhase);
  }

  public void initializeCureMarkers() {
    Map<CubeColor, Boolean> curedDiseases = new HashMap<>();
    curedDiseases.put(CubeColor.RED, false);
    curedDiseases.put(CubeColor.YELLOW, false);
    curedDiseases.put(CubeColor.BLUE, false);
    curedDiseases.put(CubeColor.BLACK, false);
    game.setCuredDiseases(curedDiseases);
  }

  public void infectNineCities() {
    infectThreeCities(3);
    infectThreeCities(2);
    infectThreeCities(1);
  }

  public void infectThreeCities(int numCubesEachCity) {
    for (int i = 0; i < 3; i++) {
      Card infectionCard = game.getInfectionDeck().drawCard();
      CubeColor diseaseColor = infectionCard.getColor();
      City infectedCity = game.getCityMap().get(infectionCard.getName());
      for (int j = 0; j < numCubesEachCity; j++) {
        infectedCity.addCube(diseaseColor);
        game.decrementDiseaseCube(diseaseColor);
      }
      game.getInfectionDiscardDeck().addCard(infectionCard);
    }
  }

  public int indexOfPlayerHasTopPopulation() {
    int topPopulation = 0;
    int indexOfPlayerHasTopPopulation = 0;
    for (int i = 0; i < game.getPlayers().size(); i++) {
      int currentTopPopulation = game.getPlayerAtIndex(i).getTopPopulationInHands();
      if (currentTopPopulation > topPopulation) {
        topPopulation = currentTopPopulation;
        indexOfPlayerHasTopPopulation = i;
      }
    }
    return indexOfPlayerHasTopPopulation;
  }

  public void incrementOutbreaks() {
    game.incrementOutbreaks();
    if (game.getOutbreaks() >= 8) {
      throw new GameOverException(messages.getString("gameOverEightOutbreaksException"));
    }
  }

  public boolean hasEnoughCardsToCureDisease() {
    return game.getCurrentPlayer().hasEnoughCardsToDiscoverCure();
  }

  public void cureDisease() {
    if (game.getCurrentPlayer().getCurrentCity().hasResearchStation()) {
      game.cureDisease(game.getCurrentPlayer().discoverCure(game.getPlayerDiscardDeck(),
          game.getCuredDiseases()));
      game.decrementPlayerActionsLeft();
    } else {
      String message = messages.getString("cityNoResearchStationException");
      throw new InvalidActionException(message);
    }
  }

  public void cureDisease(List<Card> cardsToCureDisease) {
    if (game.getCurrentPlayer().getCurrentCity().hasResearchStation()) {
      game.cureDisease(game.getCurrentPlayer().discoverCure(cardsToCureDisease,
          game.getPlayerDiscardDeck(), game.getCuredDiseases()));
      game.decrementPlayerActionsLeft();
    } else {
      String message = messages.getString("cityNoResearchStationException");
      throw new InvalidActionException(message);
    }
  }

  public Set<City> getVisitedCities() {
    return visitedCities;
  }

  public void setVisitedCities(Set<City> visitedCities) {
    this.visitedCities = visitedCities;
  }

  public void movePlayerByDriveOrFerry(City destination) {
    game.getCurrentPlayer().driveOrFerry(destination);
    game.decrementPlayerActionsLeft();
    this.clearCuredDiseaseCubesForFree(game.getCurrentPlayer());
  }

  public void movePlayerByDirectFlight(City destination) {
    game.getCurrentPlayer().directFlight(destination, game.getPlayerDiscardDeck());
    game.decrementPlayerActionsLeft();
    this.clearCuredDiseaseCubesForFree(game.getCurrentPlayer());
  }

  public void movePlayerByCharterFlight(City destination) {
    game.getCurrentPlayer().charterFlight(destination, game.getPlayerDiscardDeck());
    game.decrementPlayerActionsLeft();
    this.clearCuredDiseaseCubesForFree(game.getCurrentPlayer());
  }

  public void movePlayerByShuttleFlight(City destination) {
    game.getCurrentPlayer().shuttleFlight(destination);
    game.decrementPlayerActionsLeft();
    this.clearCuredDiseaseCubesForFree(game.getCurrentPlayer());
  }

  private void clearCuredDiseaseCubesForFree(Player player) {
    if (player.getRole() == Role.Medic) {
      for (Entry<CubeColor, Boolean> color : game.getCuredDiseases().entrySet()) {
        if (game.isDiseaseCured(color.getKey())
            && (player.getCurrentCity().getCubeCount(color.getKey()) > 0)) {
          treatCityByMedic(player, color.getKey());
        }
      }
    }
  }

  public void treatCityByMedic(Player player, CubeColor color) {
    int numCubes = player.getCurrentCity().getCubeCount(color);
    player.removeAllCubes(color);
    game.setDiseaseCubesLeft(color, game.getCubesLeftByColor(color) + numCubes);
    if (!game.isDiseaseCured(color)) {
      game.decrementPlayerActionsLeft();
    }
  }

  public void movePlayerByDispatcher(int playerIndex, City destination) {
    if (destinationEqualsCurrentCity(playerIndex, destination)) {
      String message = messages.getString("movePlayerByDispatcherCurrentCityException");
      throw new InvalidActionException(message);
    } else if (!game.hasPlayers(destination)) {
      String message = messages.getString("movePlayerByDispatcherNoPlayerException");
      throw new InvalidActionException(message);
    }
    game.getPlayerAtIndex(playerIndex).setCurrentCity(destination);
    game.decrementPlayerActionsLeft();
    this.clearCuredDiseaseCubesForFree(game.getPlayerAtIndex(playerIndex));
  }

  private boolean destinationEqualsCurrentCity(int playerIndex, City destination) {
    return game.getPlayerAtIndex(playerIndex).getCurrentCity().equals(destination);
  }

  public void buildResearchStation() {
    if (game.getTotalResearchStationLeft() == 0) {
      throw new NotEnoughResearchStationsException(messages.getString("notEnoughResearchStations"));
    }
    game.getCurrentPlayer().buildResearchStation(game.getPlayerDiscardDeck());
    game.decrementPlayerActionsLeft();
    game.decrementResearchStationLeft();
  }

  public void moveResearchStation(City cityToRemoveResearchStation) {
    City currentCity = game.getCurrentPlayer().getCurrentCity();
    if (validResearchStationMove(cityToRemoveResearchStation, currentCity)) {
      cityToRemoveResearchStation.removeResearchStation();
      currentCity.addResearchStation();
      game.decrementPlayerActionsLeft();
    } else {
      String message = messages.getString("gameMoveResearchStationFirstException");
      throw new InvalidActionException(cityToRemoveResearchStation.getName() + message);
    }
  }

  private boolean validResearchStationMove(City removeCity, City currentCity) {
    return removeCity.hasResearchStation() && !currentCity.hasResearchStation();
  }

  public void removeOneDiseaseCube(CubeColor color) {
    game.getCurrentPlayer().treatCurrentCity(color);
    game.decrementPlayerActionsLeft();
  }

  public void playInfectorPhase() {
    if (game.willPlayerSkipNextInfectorPhase(game.getCurrentPlayer())) {
      game.updatePlayerSkipNextInfectorPhase(game.getCurrentPlayer(), false);
    } else {
      int currentInfectionRate = game.getInfectionRate();
      for (int i = 0; i < currentInfectionRate; i++) {
        drawInfectionCard();
      }
    }
  }

  public void drawInfectionCard() {
    Card infectionCard = game.getInfectionDeck().drawCard();
    if (!isEradicated(infectionCard.getColor())) {
      City infectedCity = game.getCity(infectionCard.getName());
      visitedCities = new HashSet<>();
      addDiseaseCube(infectedCity, infectionCard.getColor());
      game.getInfectionDiscardDeck().addCard(infectionCard);
    }
  }

  public void addDiseaseCube(City city, CubeColor diseaseColor) {
    int cubeLeft = game.getCubesLeftByColor(diseaseColor);
    if (cubeLeft == 0) {
      throw new GameOverException(messages.getString("gameOverNoCubesException"));
    } else {
      addDiseaseCubesToCity(city, diseaseColor);
    }
  }

  private void addDiseaseCubesToCity(City city, CubeColor diseaseColor) {
    if (!game.hasMedicAt(city) || !game.isDiseaseCured(diseaseColor)) {
      int cubeCount = city.getCubeCount(diseaseColor);
      if (cubeCount == 3) {
        this.incrementOutbreaks();
        outbreakChain(city, diseaseColor);
      } else {
        game.decrementDiseaseCube(diseaseColor);
        city.addCube(diseaseColor);
      }
    }
  }

  private void outbreakChain(City city, CubeColor diseaseColor) {
    List<City> adjacentCities = city.getAdjacentCities();
    getVisitedCities().add(city);
    for (City acity : adjacentCities) {
      if (!getVisitedCities().contains(acity)) {
        getVisitedCities().add(acity);
        addDiseaseCube(acity, diseaseColor);
      }
    }
  }

  public void epidemicCardDrawn(Random random) {
    game.increaseInfectionRate();
    Card infectionCard = game.getInfectionDeck().drawLastCard();
    CubeColor infectedColor = infectionCard.getColor();
    City infectedCity = game.getCity(infectionCard.getName());
    if (!needToAddCubes(infectedColor, infectedCity)) {
      return;
    }
    boolean willCauseOutbreak = infectedCity.getCubeCount(infectedColor) > 0;
    while (infectedCity.getCubeCount(infectedColor) < 3) {
      infectedCity.addCube(infectedColor);
      game.decrementDiseaseCube(infectedColor);
    }
    if (willCauseOutbreak) {
      visitedCities = new HashSet<>();
      addDiseaseCube(infectedCity, infectedColor);
    }
    game.getInfectionDiscardDeck().addCard(infectionCard);
    game.getInfectionDiscardDeck().shuffle(random);
    game.getInfectionDeck().placeCardsOnTop(game.getInfectionDiscardDeck());
  }

  private boolean needToAddCubes(CubeColor infectedColor, City infectedCity) {
    if (this.isEradicated(infectedColor)) {
      return false;
    }
    if (game.hasMedicAt(infectedCity) && game.isDiseaseCured(infectedColor)) {
      return false;
    }
    return true;
  }

  public void playAirlift(Player playerWithCard, Card eventCard, Player playerToBeMoved,
      City destination) {
    playerToBeMoved.setCurrentCity(destination);
    game.getPlayerDiscardDeck().addCard(playerWithCard.removeCard(eventCard));
  }

  public void playOneQuietNight(Player playerWithCard, Card eventCard,
      Player playerToSkipNextInfectorPhase) {
    game.updatePlayerSkipNextInfectorPhase(playerToSkipNextInfectorPhase, true);
    game.getPlayerDiscardDeck().addCard(playerWithCard.removeCard(eventCard));
  }

  public void playResilientPopulation(Player playerWithCard, Card eventCard,
      Card infectionCardToBeRemoved) {
    game.getInfectionDiscardDeck().removeCard(infectionCardToBeRemoved);
    game.getPlayerDiscardDeck().addCard(playerWithCard.removeCard(eventCard));
  }

  public void playGovernmentGrantAndBuild(Player playerWithCard, Card eventCard,
      City cityToAddResearchStation) {
    if (cityToAddResearchStation.hasResearchStation()) {
      String message = messages.getString("playerBuildResearchStationException");
      throw new InvalidActionException(message);
    }
    if (game.getTotalResearchStationLeft() == 0) {
      throw new NotEnoughResearchStationsException(messages.getString("notEnoughResearchStations"));
    }
    cityToAddResearchStation.addResearchStation();
    game.decrementResearchStationLeft();
    game.getPlayerDiscardDeck().addCard(playerWithCard.removeCard(eventCard));
  }

  public void playGovernmentGrantAndMove(Player playerWithCard, Card eventCard,
      City cityToRemoveResearchStation, City cityToAddResearchStation) {
    if (this.validResearchStationMove(cityToRemoveResearchStation, cityToAddResearchStation)) {
      cityToRemoveResearchStation.removeResearchStation();
      cityToAddResearchStation.addResearchStation();
      game.getPlayerDiscardDeck().addCard(playerWithCard.removeCard(eventCard));
    } else {
      String first = messages.getString("governmentGrantExceptionFirstHalf");
      String second = messages.getString("governmentGrantExceptionSecondHalf");
      throw new InvalidActionException(
          cityToRemoveResearchStation.getName() + first + cityToAddResearchStation + second);
    }
  }

  public void playForecast(Player playerWithCard, Card eventCard, List<Card> rearrangedCard) {
    for (int i = rearrangedCard.size() - 1; i >= 0; i--) {
      game.getInfectionDeck().addCard(rearrangedCard.get(i));
    }
    game.getPlayerDiscardDeck().addCard(playerWithCard.removeCard(eventCard));
  }

  public Card drawFromPlayerDeck() {
    try {
      Card cardDrawn = game.getPlayerDeck().drawCard();
      game.getCurrentPlayer().addCard(cardDrawn);
      game.getPlayerDiscardDeck().addCard(cardDrawn);
      return cardDrawn;
    } catch (NoSuchElementException e) {
      throw new GameOverException(messages.getString("gameOverNoPlayerCardsException"));
    }
  }

  public boolean isEradicated(CubeColor color) {
    return game.getCubesLeftByColor(color) == TOTAL_CUBES_EACH_COLOR && game.isDiseaseCured(color);
  }

  public void passCard(int playerIndex) {
    game.getCurrentPlayer().passCard(game.getPlayerAtIndex(playerIndex));
  }

  public boolean checkForWin() {
    for (Boolean isCured : game.getCuredDiseases().values()) {
      if (!isCured) {
        return false;
      }
    }
    return true;
  }

  public Game getGame() {
    return game;
  }

  public void passSpecificCardByResearcher(Player researcher, Player playerToPass,
      Card cardToPass) {
    researcher.passSpecificCardToPlayer(playerToPass, cardToPass);
    game.decrementPlayerActionsLeft();
  }
}