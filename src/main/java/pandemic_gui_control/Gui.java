package pandemic_gui_control;

import pandemic_game.Constants.CubeColor;
import pandemic_game.Constants.Role;

public interface Gui {

  void startShowing();

  void setMapControl(MapDisplay map);

  void setButtonControl(ButtonDisplay button);

  int displayOptions(String msg, String title, CubeColor[] colors);

  void displayMessage(String message);

  void displayError(Exception except);

  void setGuiListener(GuiListener listener);

  void drawPlayer(Role role, String cityName);

  void drawCubesOnCity(int[] cubeCounts, CubeColor[] colors, String cityName);

  void drawResearchStations(String[] cityNames);

  void drawInfectionRateMarker(int rateIndex);

  void drawOutbreakMarker(int numOfOutbreaks);

  void drawSelectedCityCircle(String selectedCity);

  void setCardControl(CardDisplay cardControl);

  void drawPlayerCards(String[] cardNames);

  void drawPlayerRole(Role role);

  int displayCardOptions(String message, String title, String[] labels);

  void close();

  void drawAllPlayersAndCards(String[] playerRoles, String[][] playerCards);

  int displayPlayerOptions(String string, String[] createRoleLabels);

  void drawCuredDiseases(String[] curedDiseases);

  void drawEradicatedDiseases(String[] eradicatedDiseases);

  void drawAllDecks();

  int displayResearchStationOptions(String[] citiesWithStations);

}
