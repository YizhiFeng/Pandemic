package pandemic_gui;

import static javax.swing.JOptionPane.QUESTION_MESSAGE;
import static javax.swing.JOptionPane.WARNING_MESSAGE;
import static javax.swing.JOptionPane.YES_NO_CANCEL_OPTION;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import pandemic_game.Constants.CubeColor;
import pandemic_game.Constants.Role;
import pandemic_gui_control.ButtonDisplay;
import pandemic_gui_control.CardDisplay;
import pandemic_gui_control.Gui;
import pandemic_gui_control.GuiListener;
import pandemic_gui_control.MapDisplay;

public class GuiImpl implements Gui {

  private static ResourceBundle messages = ResourceBundle.getBundle("MessageBundle");
  private JFrame frame;
  private MapDisplay map;
  private ButtonDisplay buttons;
  private CardDisplay cards;

  public GuiImpl(JPanel buttons, MapPanel map, JPanel cards) {
    frame = new JFrame();
    initialize(buttons, map, cards);
  }

  private void initialize(JPanel buttons, MapPanel map, JPanel cards) {
    String pandemic = messages.getString("pandemic");
    frame.setTitle(pandemic);
    GridBagLayout layout = new GridBagLayout();
    frame.getContentPane().setLayout(layout);

    setupMap(map);
    setupButtons(buttons);
    setupCards(cards);
    frame.pack();
  }

  private void setupMap(MapPanel mapPanel) {
    mapPanel.setPreferredSize(new Dimension(1100, 750));
    GridBagConstraints mapConstraints = makeConstraints(0, 0, 10, 25);
    frame.add(mapPanel, mapConstraints);
  }

  private void setupButtons(JPanel buttons) {
    buttons.setPreferredSize(new Dimension(200, 500));
    frame.add(buttons, makeConstraints(27, 0, 10, 3));
  }

  private void setupCards(JPanel cards) {
    cards.setPreferredSize(new Dimension(900, 190));
    frame.add(cards, makeConstraints(0, 11, 4, 20));
  }

  private GridBagConstraints makeConstraints(int gridXpos, int gridYpos, int height, int width) {
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.gridx = gridXpos;
    constraints.gridy = gridYpos;
    constraints.gridheight = height;
    constraints.gridwidth = width;
    Insets externalPadding = new Insets(10, 10, 10, 10);
    constraints.insets = externalPadding;
    constraints.fill = GridBagConstraints.HORIZONTAL;
    constraints.ipadx = 10;
    constraints.ipady = 10;
    constraints.weightx = 1.0;
    constraints.weighty = 1.0;
    return constraints;
  }

  @Override
  public void setMapControl(MapDisplay map) {
    this.map = map;
  }

  @Override
  public void setButtonControl(ButtonDisplay buttons) {
    this.buttons = buttons;
  }

  @Override
  public void setCardControl(CardDisplay cards) {
    this.cards = cards;
  }

  public void startShowing() {
    frame.setVisible(true);
  }

  public void displayError(Exception exception) {
    String title = messages.getString("error");
    JOptionPane.showMessageDialog(frame, exception.getMessage(), title, WARNING_MESSAGE);
  }

  public int displayOptions(String msg, String title, CubeColor[] colors) {
    int chosen = JOptionPane.showOptionDialog(frame, msg, title, YES_NO_CANCEL_OPTION,
        QUESTION_MESSAGE, null, colors, colors[0]);
    return chosen;
  }

  public void displayMessage(String message) {
    String title = messages.getString("uhOh");
    JOptionPane.showMessageDialog(frame, message, title, JOptionPane.INFORMATION_MESSAGE);
  }

  public void setGuiListener(GuiListener listener) {
    map.setGuiListener(listener);
    buttons.setButtonListener(listener);
  }

  public void drawPlayer(Role role, String cityName) {
    map.drawPlayerAt(role, cityName);
  }

  public void drawCubesOnCity(int[] cubeCounts, CubeColor[] colors, String city) {
    map.drawCubesOn(cubeCounts, colors, city);
  }

  public void drawResearchStations(String[] cityNames) {
    map.drawResearchStations(cityNames);
  }

  public void drawInfectionRateMarker(int rate) {
    map.drawInfectionRate(rate);
  }

  public void drawOutbreakMarker(int numOfOutbreaks) {
    map.drawOutbreaks(numOfOutbreaks);
  }

  public void drawSelectedCityCircle(String selectedCity) {
    map.showSelectedCity(selectedCity);
  }

  @Override
  public void drawCuredDiseases(String[] curedDiseases) {
    map.drawCuredDiseases(curedDiseases);
  }

  @Override
  public void drawEradicatedDiseases(String[] eradicatedDiseases) {
    map.drawEradicatedDiseases(eradicatedDiseases);
  }

  @Override
  public void drawPlayerCards(String[] cardNames) {
    cards.drawCards(cardNames);
  }

  @Override
  public void drawPlayerRole(Role role) {
    cards.drawRoleCard(role.toString());
  }

  @Override
  public void drawAllDecks() {
    map.drawPlayerDeck();
    map.drawPlayerDiscard();
    map.drawInfectionDeck();
    map.drawInfectionDiscard();
  }

  @Override
  public int displayCardOptions(String message, String title, String[] labels) {
    return JOptionPane.showOptionDialog(frame, message, title, JOptionPane.YES_OPTION,
        QUESTION_MESSAGE, null, labels, null);
  }

  @Override
  public int displayPlayerOptions(String message, String[] labels) {
    String title = messages.getString("passCard");
    return JOptionPane.showOptionDialog(frame, message, title, JOptionPane.YES_OPTION,
        QUESTION_MESSAGE, null, labels, labels[0]);
  }

  @Override
  public void close() {
    frame.dispose();
  }

  @Override
  public void drawAllPlayersAndCards(String[] playerRoles, String[][] playerCards) {
    cards.drawAllPlayersAndCards(playerRoles, playerCards);
  }

  @Override
  public int displayResearchStationOptions(String[] citiesWithStations) {
    String message = messages.getString("pickAStationToMove");
    String title = messages.getString("tooManyResearchStations");
    return JOptionPane.showOptionDialog(frame, message, title, JOptionPane.YES_OPTION,
        QUESTION_MESSAGE, null, citiesWithStations, citiesWithStations[0]);
  }
}