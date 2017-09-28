package pandemic_gui_control;

import static java.awt.Image.SCALE_SMOOTH;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class LabelFactory {

  public JLabel createLabel(BufferedImage image, int xpos, int ypos, int widthScale,
      int heightScale, int width, int height) {
    Image temp = image.getScaledInstance(width / widthScale, height / heightScale, SCALE_SMOOTH);
    JLabel label = new JLabel(new ImageIcon(temp));
    label.setBounds(xpos, ypos, temp.getWidth(null), temp.getHeight(null));
    return label;
  }
}
