package pandemic_gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.plaf.basic.BasicArrowButton;

import pandemic_exception.InitializationException;
import pandemic_gui_control.StartMenu;
import pandemic_initialization.LocaleLookup;

public class StartMenuImpl implements StartMenu {

  private static ResourceBundle messages = ResourceBundle.getBundle("MessageBundle");
  
  private JFrame frame;
  private boolean startGame;
  private int numOfPlayers;
  private Locale selectedLocale;
  private LocaleLookup locales;
  private JButton startButton;
  private JButton instructions;
  private JComboBox<String> languageComboBox;
  private JLabel languageSelectLabel;
  private JLabel difficultySelectLabel;
  private JComboBox<String> difficultyComboBox;
  private JLabel playersLabel;
  private JLabel numOfPlayersLabel;
  private int numOfEpidemicCards;

  public StartMenuImpl(JFrame frame, LocaleLookup locales) {
    this.locales = locales;
    this.selectedLocale = locales.getDefault();
    this.frame = frame;
    this.startGame = false;
    frame.setLayout(new GridBagLayout());

    initialize();

    frame.setVisible(true);
    frame.pack();
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  private void initialize() {
    setupImage();
    setupInstructions();
    setupLanguageRow();
    setupPlayerCountRow();
    setupDifficultyRow(getDifficulties());
    setupStartGameRow();
    updateText();
    updateNumOfPlayers(4);
  }

  private void setupImage() {
    JLabel label = new JLabel(new ImageIcon(loadImage("pandemic_start_screen.jpg")));
    frame.add(label, makeConstraints(0, 0, 5, 10));
  }

  private BufferedImage loadImage(String fileName) {
    try {
      fileName = "images/" + fileName;
      return ImageIO.read(getClass().getClassLoader().getResource(fileName));
    } catch (Exception e) {
      ResourceBundle messages = ResourceBundle.getBundle("MessageBundle");
      String message = messages.getString("unableToLoadMapImageException") + fileName;
      throw new InitializationException(message);
    }
  }

  private void setupInstructions() {
    instructions = new JButton();
    instructions.addActionListener((event) -> new InstructionDisplay(new JFrame()));
    frame.add(instructions, makeConstraints(0, 6, 1, 10));
  }

  private void setupLanguageRow() {
    languageSelectLabel = new JLabel();
    frame.add(languageSelectLabel, makeConstraints(0, 7, 1, 3));

    languageComboBox = new JComboBox<>(locales.getLocales());
    languageComboBox.addActionListener((event) -> setSelectedLanguage());
    languageComboBox.setSelectedIndex(0);
    frame.add(languageComboBox, makeConstraints(3, 7, 1, 5));
  }

  private void setSelectedLanguage() {
    selectedLocale = locales.getLocale(languageComboBox.getSelectedItem());
    Locale.setDefault(selectedLocale);
  }

  private void setupPlayerCountRow() {
    playersLabel = new JLabel();
    frame.add(playersLabel, makeConstraints(0, 8, 2, 5));

    numOfPlayersLabel = new JLabel();
    frame.add(numOfPlayersLabel, makeConstraints(6, 8, 2, 1));

    JButton upButton = new BasicArrowButton(BasicArrowButton.NORTH);
    upButton.addActionListener((event) -> updateNumOfPlayers(numOfPlayers + 1));
    frame.add(upButton, makeConstraints(8, 8, 1, 2));

    JButton downButton = new BasicArrowButton(BasicArrowButton.SOUTH);
    downButton.addActionListener((event) -> updateNumOfPlayers(numOfPlayers - 1));
    frame.add(downButton, makeConstraints(8, 9, 1, 2));
  }

  private void updateNumOfPlayers(int numOfPlayers) {
    if (1 < numOfPlayers && numOfPlayers < 5) {
      this.numOfPlayers = numOfPlayers;
    }
    numOfPlayersLabel.setText(this.numOfPlayers + "");
  }

  private String[] getDifficulties() {
    String[] difficulties = new String[3];
    difficulties[0] = messages.getString("introductory");
    difficulties[1] = messages.getString("standard");
    difficulties[2] = messages.getString("heroic");
    return difficulties;
  }

  private void setupDifficultyRow(String[] difficulties) {
    difficultySelectLabel = new JLabel();
    frame.add(difficultySelectLabel, makeConstraints(0, 10, 1, 5));

    difficultyComboBox = new JComboBox<>(difficulties);
    difficultyComboBox.addActionListener((event) -> setSelectedDifficulty());
    difficultyComboBox.setSelectedIndex(0);
    frame.add(difficultyComboBox, makeConstraints(6, 10, 1, 4));
  }

  private void setSelectedDifficulty() {
    numOfEpidemicCards = difficultyComboBox.getSelectedIndex() + 4;
  }

  private void setupStartGameRow() {
    startButton = new JButton();
    startButton.addActionListener((event) -> start());
    frame.add(startButton, makeConstraints(0, 11, 1, 10));
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

  private void start() {
    startGame = true;
  }

  private void updateText() {
    updateDifficulties();
    instructions.setText(messages.getString("instructionButton"));
    startButton.setText(messages.getString("startButton"));
    languageSelectLabel.setText(messages.getString("selectLanguage"));
    playersLabel.setText(messages.getString("numPlayers"));
    difficultySelectLabel.setText(messages.getString("selectDifficulty"));
  }

  private void updateDifficulties() {
    frame.remove(difficultyComboBox);
    frame.remove(difficultySelectLabel);
    setupDifficultyRow(getDifficulties());
  }

  public void close() {
    frame.setVisible(false);
    frame.dispose();
  }

  public boolean readyToStartGame() {
    return startGame;
  }

  public void refreshView() {
    Locale newLoc = Locale.getDefault();
    if (newLoc.equals(selectedLocale)) {
      return;
    }
    updateText();
    frame.repaint();
  }

  public int getNumPlayers() {
    return numOfPlayers;
  }

  public int getNumOfEpidemicCards() {
    return numOfEpidemicCards;
  }

}
