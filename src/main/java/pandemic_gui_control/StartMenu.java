package pandemic_gui_control;

public interface StartMenu {

  void close();

  boolean readyToStartGame();

  void refreshView();

  int getNumPlayers();

  int getNumOfEpidemicCards();
}
