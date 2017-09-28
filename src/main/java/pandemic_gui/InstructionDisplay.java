package pandemic_gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import pandemic_exception.InitializationException;

public class InstructionDisplay {

  private JFrame frame;
  private JLabel currentImage;
  private int currentPage;
  private ResourceBundle messages = ResourceBundle.getBundle("MessageBundle");
  private Map<String, ImageIcon> pages;

  public InstructionDisplay(JFrame frame) {
    this.frame = frame;
    this.currentPage = 1;
    this.pages = new HashMap<>();
    this.currentImage = new JLabel();
    frame.setLayout(new GridBagLayout());
    setupPages();
    updatePage();
    frame.add(currentImage, makeConstraints(0, 0, 5, 10));

    setupButtons();

    frame.setVisible(true);
    frame.pack();
    frame.setResizable(false);
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

  }

  private void setupPages() {
    for (int i = 1; i < 9; i++) {
      currentPage = i;
      String imageName = "instruction_pg" + currentPage + ".jpg";
      pages.put(imageName, new ImageIcon(loadImage(imageName)));
    }
    currentPage = 1;
  }

  private void updatePage() {
    currentImage.setIcon(pages.get("instruction_pg" + currentPage + ".jpg"));
  }

  private BufferedImage loadImage(String fileName) {
    try {
      fileName = "images/" + fileName;
      return ImageIO.read(getClass().getClassLoader().getResource(fileName));
    } catch (Exception e) {
      String message = messages.getString("unableToLoadMapImageException") + fileName;
      throw new InitializationException(message);
    }
  }

  private void setupButtons() {
    JButton prevButton = new JButton();
    prevButton.addActionListener((event) -> previousPage());
    prevButton.setText(messages.getString("prev"));
    frame.add(prevButton, makeConstraints(0, 6, 1, 3));

    JButton nextButton = new JButton();
    nextButton.addActionListener((event) -> nextPage());
    nextButton.setText(messages.getString("next"));
    frame.add(nextButton, makeConstraints(3, 6, 1, 3));
  }

  private void previousPage() {
    if (currentPage > 1) {
      currentPage--;
      updatePage();
    }
    frame.repaint();
  }

  private void nextPage() {
    if (currentPage < 8) {
      currentPage++;
      updatePage();
    }
    frame.repaint();
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
}