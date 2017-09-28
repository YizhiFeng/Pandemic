package pandemic_gui_control;

import pandemic_game.Constants.CubeColor;
import pandemic_game.Constants.Role;

public interface MapDisplay {

  void drawPlayerAt(Role role, String cityName);

  void drawCubesOn(int[] numCubes, CubeColor[] colors, String cityName);

  void drawResearchStations(String[] cityNames);

  void showSelectedCity(String cityName);

  void drawInfectionRate(int rateIndex);

  void drawOutbreaks(int numOfOutbreaksOccured);

  void setGuiListener(GuiListener listener);

  void drawCuredDiseases(String[] curedDiseases);

  void drawEradicatedDiseases(String[] eradicatedDiseases);

  void drawPlayerDeck();

  void drawPlayerDiscard();

  void drawInfectionDeck();

  void drawInfectionDiscard();
}
