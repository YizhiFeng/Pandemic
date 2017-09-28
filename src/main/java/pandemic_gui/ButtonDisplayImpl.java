package pandemic_gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import pandemic_gui_control.ButtonDisplay;
import pandemic_gui_control.GuiListener;

public class ButtonDisplayImpl implements ButtonDisplay {

  private JPanel panel;
  private static int NUM_BUTTONS = 14;
  private GuiListener listener;
  private static ResourceBundle messages = ResourceBundle.getBundle("MessageBundle");

  public ButtonDisplayImpl(JPanel panel) {
    this.panel = panel;
    GridLayout layout = new GridLayout(NUM_BUTTONS, 1, 5, 10);
    panel.setLayout(layout);
    createButtons();
  }

  private void createButtons() {
    createCarOrFerryButton();
    createDirectFlightButton();
    createCharterFlightButton();
    createShuttleFlightButton();
    createBuildResearchStationButton();
    createTreatDiseaseButton();
    createDiscoverCureButton();
    createTradeWithResearcherButton();
    createMoveOtherPlayersButton();
    createTradeCardButton();
    createPlayEventCardButton();
    createEndTurnButton();
    createViewPlayerCardsButton();
    createInstructionsButton();
  }

  private void createCarOrFerryButton() {
    String carAndFerry = messages.getString("car&FerryTravel");
    JButton carBtn = new JButton(carAndFerry);
    carBtn.addActionListener((event) -> listener.onCarAndFerryTravel());
    carBtn.setMinimumSize(new Dimension(200, 30));
    panel.add(carBtn);
  }

  private void createDirectFlightButton() {
    String directFlight = messages.getString("directFlight");
    JButton directFlightBtn = new JButton(directFlight);
    directFlightBtn.addActionListener((event) -> listener.onDirectFlight());
    panel.add(directFlightBtn);
  }

  private void createCharterFlightButton() {
    String charterFlight = messages.getString("charterFlight");
    JButton charterFlightBtn = new JButton(charterFlight);
    charterFlightBtn.addActionListener((event) -> listener.onCharterFlight());
    panel.add(charterFlightBtn);
  }

  private void createShuttleFlightButton() {
    String shuttleFlight = messages.getString("shuttle");
    JButton shuttleBtn = new JButton(shuttleFlight);
    shuttleBtn.addActionListener((event) -> listener.onShuttleTravel());
    panel.add(shuttleBtn);
  }

  private void createBuildResearchStationButton() {
    String buildResearchStation = messages.getString("buildResearchStation");
    JButton buildResearchStationBtn = new JButton(buildResearchStation);
    buildResearchStationBtn.addActionListener((event) -> listener.onBuildResearchStation());
    panel.add(buildResearchStationBtn);
  }

  private void createTreatDiseaseButton() {
    String treatDiesease = messages.getString("treatDisease");
    JButton treatDiseaseBtn = new JButton(treatDiesease);
    treatDiseaseBtn.addActionListener((event) -> listener.onTreatDisease());
    panel.add(treatDiseaseBtn);
  }

  private void createDiscoverCureButton() {
    String discoverCure = messages.getString("discoverCure");
    JButton discoverCureBtn = new JButton(discoverCure);
    discoverCureBtn.addActionListener((event) -> listener.onDiscoverCure());
    panel.add(discoverCureBtn);
  }

  private void createTradeWithResearcherButton() {
    String tradeWithResearcher = messages.getString("tradeWithResearcher");
    JButton researcherTradeBtn = new JButton(tradeWithResearcher);
    researcherTradeBtn.addActionListener((event) -> listener.onResearcherTrade());
    panel.add(researcherTradeBtn);
  }

  private void createMoveOtherPlayersButton() {
    String moveOtherPlayers = messages.getString("moveOtherPlayers");
    JButton moveOtherPlayersBtn = new JButton(moveOtherPlayers);
    moveOtherPlayersBtn.addActionListener((event) -> listener.onMoveOtherPlayers());
    panel.add(moveOtherPlayersBtn);
  }

  private void createTradeCardButton() {
    String tradeCard = messages.getString("tradeCard");
    JButton tradeCardBtn = new JButton(tradeCard);
    tradeCardBtn.addActionListener((event) -> listener.onTradeCard());
    panel.add(tradeCardBtn);
  }

  private void createPlayEventCardButton() {
    String playEventCard = messages.getString("playEventCard");
    JButton playEventCardBtn = new JButton(playEventCard);
    playEventCardBtn.addActionListener((event) -> listener.onPlayEventCard());
    panel.add(playEventCardBtn);
  }

  private void createEndTurnButton() {
    String endTurn = messages.getString("endTurn");
    JButton endTurnBtn = new JButton(endTurn);
    endTurnBtn.addActionListener((event) -> listener.onEndTurn());
    panel.add(endTurnBtn);
  }

  private void createViewPlayerCardsButton() {
    String playerCards = messages.getString("seeAllPlayersCards");
    JButton playerCardsBtn = new JButton(playerCards);
    playerCardsBtn.addActionListener((event) -> listener.onSeePlayersCards());
    panel.add(playerCardsBtn);
  }

  private void createInstructionsButton() {
    String instructions = messages.getString("instructionButton");
    JButton instructionsBtn = new JButton(instructions);
    instructionsBtn.addActionListener((event) -> new InstructionDisplay(new JFrame()));
    panel.add(instructionsBtn);
  }

  @Override
  public void setButtonListener(GuiListener listener) {
    this.listener = listener;
  }

}
