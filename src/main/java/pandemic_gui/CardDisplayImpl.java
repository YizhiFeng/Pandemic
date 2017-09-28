package pandemic_gui;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import pandemic_exception.ImageNotFoundException;
import pandemic_exception.InitializationException;
import pandemic_gui_control.CardDisplay;

public class CardDisplayImpl implements CardDisplay {

  private static final int NUM_CARDS_IN_HAND = 7;
  private static final String DEFAULT_CARD_IMAGE = "player_card_back_vertical.jpg";
  private static final ResourceBundle messages = ResourceBundle.getBundle("MessageBundle");
  private Map<String, BufferedImage> cardImages = new HashMap<>();
  private JPanel panel;
  private JLabel roleLabel;
  private JLabel[] cardLabels = new JLabel[NUM_CARDS_IN_HAND];

  public CardDisplayImpl(JPanel panel) {
    this.panel = panel;
    GridLayout layout = new GridLayout(1, NUM_CARDS_IN_HAND + 1);
    layout.setHgap(10);
    panel.setLayout(layout);
    initializeCards();
  }

  private void initializeCards() {
    ImageIcon backOfCardVertical = new ImageIcon(loadImage(DEFAULT_CARD_IMAGE));
    roleLabel = new JLabel(backOfCardVertical);
    roleLabel.setText(messages.getString("currentPlayer"));
    roleLabel.setHorizontalAlignment(SwingConstants.CENTER);
    roleLabel.setHorizontalTextPosition(SwingConstants.CENTER);
    roleLabel.setVerticalTextPosition(SwingConstants.TOP);
    roleLabel.setPreferredSize(new Dimension(100, 170));
    panel.add(roleLabel);

    for (int i = 0; i < NUM_CARDS_IN_HAND; i++) {
      JLabel card = new JLabel(backOfCardVertical);

      card.setPreferredSize(new Dimension(100, 170));
      cardLabels[i] = card;
      panel.add(card);
    }
  }

  private BufferedImage loadImage(String fileName) {
    try {
      String path = "images/" + fileName;
      return ImageIO.read(getClass().getClassLoader().getResource(path));
    } catch (Exception except) {
      throw new InitializationException(messages.getString("unableToLoadCardImageException"));
    }
  }

  private ImageIcon getCardIcon(String imageName) {
    if (cardImages.containsKey(imageName)) {
      return new ImageIcon(cardImages.get(imageName));
    }
    BufferedImage image = loadImage(imageName);
    cardImages.put(imageName, image);
    return new ImageIcon(image);
  }

  @Override
  public void drawCards(String[] cardNames) {
    for (int i = 0; i < NUM_CARDS_IN_HAND; i++) {
      if (i < cardNames.length) {
        try {
          cardLabels[i].setIcon(getCardIcon(cardNames[i] + ".png"));
        } catch (Exception except) {
          throw new ImageNotFoundException(messages.getString("cardImageNotFound"));
        }
        cardLabels[i].setVisible(true);
      } else {
        cardLabels[i].setVisible(false);
      }
    }
  }

  @Override
  public void drawRoleCard(String role) {
    try {
      roleLabel.setIcon(getCardIcon(role.toString() + ".png"));
    } catch (Exception except) {
      throw new ImageNotFoundException(messages.getString("roleImageNotFound") + role);
    }
  }

  @Override
  public void drawAllPlayersAndCards(String[] playerRoles, String[][] playerCards) {
    JFrame frame = new JFrame();
    frame.setLayout(new GridLayout(playerRoles.length, 8));

    for (int i = 0; i < playerRoles.length; i++) {
      JLabel roleLabel = new JLabel(getCardIcon(playerRoles[i] + ".png"));
      frame.add(roleLabel);

      for (int j = 0; j < 7; j++) {
        if (playerCards[i][j] != null) {
          JLabel cardLabel = new JLabel(getCardIcon(playerCards[i][j] + ".png"));
          frame.add(cardLabel);
        } else {
          JLabel blankLabel = new JLabel();
          frame.add(blankLabel);
        }
      }
    }
    frame.pack();
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    frame.setVisible(true);

  }

}
