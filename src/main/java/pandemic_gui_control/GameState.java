package pandemic_gui_control;

import java.util.List;

import pandemic_game.Card;
import pandemic_game.City;
import pandemic_game.Constants.CubeColor;
import pandemic_game.Constants.Role;
import pandemic_game.Player;

public interface GameState {

  public Role getCurrentPlayerRole();

  public Card[] getCurrentPlayerCards();

  public List<City> getAdjacentCitiesOfCurrentPlayer();

  public boolean isCityName(String cityName);

  public void movePlayerByDriveOrFerry(String selectedCity);

  public void movePlayerByShuttle(String selectedCity);

  public void movePlayerByCharterFlight(String selectedCity);

  public void movePlayerByDirectFlight(String selectedCity);

  public int getNumberOfPlayers();

  public Player getPlayer(int index);

  public List<String> getCitiesWithResearchStations();

  public void buildResearchStation();

  public void buildResearchStation(String city);

  public int getInfectionRate();

  public int getOutbreakNumber();

  public List<City> getDiseasedCities();

  public void treatDisease(CubeColor color);

  public CubeColor[] cubeColorsOnCurrCity();

  public CubeColor[] cubeColorsOnCity(City city);

  public int getPlayerActionsLeft();

  public void changeTurn();

  public Player getCurrentPlayer();

  public Card drawFromPlayerDeck();

  public void epidemicCardDrawn();

  public void discard(Player player, Card card);

  public boolean isOver();

  public void passCard(Player player);

  public Card[] getResearcherCards();

  public void researcherPassCard(String string, Card card);

  public void dispatcherMovePlayers(String string, String selectedCity);

  public Card[] getPlayerEventCards(int chosenPlayer);

  public void airliftPlayer(Player cardOwner, String cityName, Player movePlayer, Card eventCard);

  public void oneQuietNightPlayer(Player cardOwner, Card card);

  public void governmentGrantCity(Player cardOwner, Card card, String cityName);

  public Card[] getInfectionDiscardCards();

  public void resilientPopulationCard(Player cardOwner, Card card, Card card2);

  public String[] getCuredDiseases();

  public String[] getEradicatedDiseases();

  public void cureDisease();

  public void cureDisease(Card[] cardsToCureDisease);

  public boolean hasEnoughCardsToCureDisease();

  public Card[] getTopSixInfectionCards();

  public void placeForecastCards(Player cardOwner, Card card, Card[] forecast);

  public void doInfectionPhase();

  public CubeColor findColorToCure();

  public int getNumCardsToCure();

  public void governmentGrantRemoveStation(Player cardOwner, Card card, String cityName,
      String stationRemove);
  
  public boolean checkTurnOver();
}
