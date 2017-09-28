package pandemic_gui_control;

public interface GuiListener {
  void onResize();

  void onMapSelected(String itemClicked);

  void onCarAndFerryTravel();

  void onTreatDisease();

  void onBuildResearchStation();

  void onShuttleTravel();

  void onCharterFlight();

  void onDirectFlight();

  void onEndTurn();

  void onDiscoverCure();

  void onTradeCard();

  void onSeePlayersCards();

  void onResearcherTrade();

  void onMoveOtherPlayers();

  void onPlayEventCard();

}
