package pandemic_gui_control;

public interface CardDisplay {

  void drawRoleCard(String string);

  void drawCards(String[] cardNames);

  void drawAllPlayersAndCards(String[] playerRoles, String[][] playerCards);

}
