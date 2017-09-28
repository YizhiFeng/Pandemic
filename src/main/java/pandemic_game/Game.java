package pandemic_game;

import java.util.List;
import java.util.Map;

import pandemic_game.Constants.CubeColor;
import pandemic_game.Constants.Role;

public class Game {

  private static int[] INFECTION_RATE_TRACKER = { 2, 2, 3, 3, 4, 4 };

  private int totalPlayers;
  private List<Player> players;
  private Deck playerDeck;
  private Deck playerDiscardDeck;
  private Deck infectionDeck;
  private Deck infectionDiscardDeck;
  private int outbreaks;
  private int infectionRateIndex;
  private Map<CubeColor, Integer> cubes;
  private Map<CubeColor, Boolean> curedDiseases;
  private int currentPlayerIndex;
  private int playerActionsLeft;
  private int researchStationLeft;
  private Map<String, City> cityMap;
  private Map<Player, Boolean> playersToSkipNextInfectorPhase;
  private int numOfEpidemicCard;
  private boolean isGameOver = false;

  public Game(int totalPlayers, int numOfEpidemicCard) {
    this.totalPlayers = totalPlayers;
    this.numOfEpidemicCard = numOfEpidemicCard;
  }

  public int getNumberOfEpidemicCard() {
    return this.numOfEpidemicCard;
  }

  public void setGameOver(boolean isGameOver) {
    this.isGameOver = isGameOver;
  }

  public boolean isGameOver() {
    return this.isGameOver;
  }

  public List<Player> getPlayers() {
    return this.players;
  }

  public void setPlayers(List<Player> players) {
    this.players = players;
  }

  public Player getCurrentPlayer() {
    return this.players.get(this.currentPlayerIndex);
  }

  public Player getPlayerAtIndex(int playerIndex) {
    return this.getPlayers().get(playerIndex);
  }

  public int getOutbreaks() {
    return this.outbreaks;
  }

  public void incrementOutbreaks() {
    this.outbreaks++;
  }

  public void setInfectionRateIndex(int infectionRateIndex) {
    this.infectionRateIndex = infectionRateIndex;
  }

  public int getInfectionRate() {
    return INFECTION_RATE_TRACKER[this.infectionRateIndex];
  }

  public void increaseInfectionRate() {
    if (this.infectionRateIndex < 5) {
      this.infectionRateIndex++;
    }
  }

  public void decrementDiseaseCube(CubeColor diseaseColor) {
    int cubeLeft = this.cubes.get(diseaseColor);
    this.cubes.put(diseaseColor, --cubeLeft);
  }

  public Deck getPlayerDeck() {
    return this.playerDeck;
  }

  public void setPlayerDeck(Deck playerCards) {
    this.playerDeck = playerCards;
  }

  public Deck getPlayerDiscardDeck() {
    return this.playerDiscardDeck;
  }

  public Deck getInfectionDeck() {
    return this.infectionDeck;
  }

  public void setInfectionDeck(Deck infectionDeck) {
    this.infectionDeck = infectionDeck;
  }

  public Deck getInfectionDiscardDeck() {
    return this.infectionDiscardDeck;
  }

  public void setInfectionDiscardDeck(Deck infectionDiscardDeck) {
    this.infectionDiscardDeck = infectionDiscardDeck;
  }

  public int getPlayerActionsLeft() {
    return this.playerActionsLeft;
  }

  public void setPlayerActionsLeft(int playerActionsLeft) {
    this.playerActionsLeft = playerActionsLeft;
  }

  public void decrementPlayerActionsLeft() {
    this.playerActionsLeft--;
  }

  public int getTotalResearchStationLeft() {
    return this.researchStationLeft;
  }

  public void setTotalResearchStationLeft(int num) {
    this.researchStationLeft = num;
  }

  public void decrementResearchStationLeft() {
    this.researchStationLeft--;
  }

  public int getTotalPlayers() {
    return totalPlayers;
  }

  public void setTotalPlayers(int totalPlayers) {
    this.totalPlayers = totalPlayers;
  }

  public int getCubesLeftByColor(CubeColor color) {
    return cubes.get(color);
  }

  public void setCubes(Map<CubeColor, Integer> cubes) {
    this.cubes = cubes;
  }

  public void setDiseaseCubesLeft(CubeColor color, int num) {
    this.cubes.put(color, num);
  }

  public Map<CubeColor, Boolean> getCuredDiseases() {
    return curedDiseases;
  }

  public void setCuredDiseases(Map<CubeColor, Boolean> curedDiseases) {
    this.curedDiseases = curedDiseases;
  }

  public boolean isDiseaseCured(CubeColor diseaseColor) {
    return this.curedDiseases.get(diseaseColor);
  }

  public void cureDisease(CubeColor diseaseColor) {
    this.curedDiseases.put(diseaseColor, true);
  }

  public void setPlayerDiscardDeck(Deck playerDiscardDeck) {
    this.playerDiscardDeck = playerDiscardDeck;
  }

  public void setCityMap(Map<String, City> cityMap) {
    this.cityMap = cityMap;
  }

  public City getCity(String cityName) {
    return this.cityMap.get(cityName);
  }

  public void setPlayersToSkipNextInfectorPhase(
      Map<Player, Boolean> playersToSkipNextInfectorPhase) {
    this.playersToSkipNextInfectorPhase = playersToSkipNextInfectorPhase;
  }

  public boolean willPlayerSkipNextInfectorPhase(Player player) {
    return this.playersToSkipNextInfectorPhase.get(player);
  }

  public void updatePlayerSkipNextInfectorPhase(Player player, boolean skip) {
    this.playersToSkipNextInfectorPhase.put(player, skip);
  }

  public boolean hasPlayers(City city) {
    for (Player player : this.players) {
      if (player.getCurrentCity().equals(city)) {
        return true;
      }
    }
    return false;
  }

  public void changeTurn() {
    this.currentPlayerIndex = (this.currentPlayerIndex + 1) % this.totalPlayers;
    this.playerActionsLeft = 4;
  }

  public Player getNextPlayer() {
    return this.players.get((this.currentPlayerIndex + 1) % this.totalPlayers);
  }

  public int getInfectionRateIndex() {
    return this.infectionRateIndex;
  }

  public int getCurrentPlayerIndex() {
    return this.currentPlayerIndex;
  }

  public void setCurrentPlayerIndex(int index) {
    this.currentPlayerIndex = index;
  }

  public Map<String, City> getCityMap() {
    return this.cityMap;
  }

  public void setOutbreaks(int count) {
    this.outbreaks = count;
  }

  public boolean hasMedicAt(City city) {
    for (Player player : this.getPlayers()) {
      if (player.getRole().equals(Role.Medic)) {
        return player.getCurrentCity().equals(city);
      }
    }
    return false;
  }

  public boolean checkTurnOver() {
    return this.playerActionsLeft == 0;
  }
}