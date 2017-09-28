package pandemic_gui;

import static java.awt.RenderingHints.KEY_INTERPOLATION;
import static java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

import pandemic_game.Constants.Role;
import pandemic_gui.MapMouseListener.MapCallbackListener;

public class MapPanel extends JPanel implements MapCallbackListener {

  public int width;
  public int height;

  private BufferedImage mapImage;
  private Map<Role, JLabel> roles;
  private Map<String, JLabel[]> cubes;
  private Map<String, JLabel> stations;
  private Map<String, JLabel> cures;
  private Map<String, JLabel> decks;
  private JLabel infectionMarker;
  private JLabel outbreakMarker;
  private JLabel cityCircle;
  private JLabel mapLabel;
  private MapListener listener;
  private boolean resize;

  public MapPanel(JLabel map) {
    mapLabel = map;
    mapLabel.addMouseListener(new MapMouseListener(this));
    add(mapLabel);
    width = 0;
    height = 0;
    roles = new HashMap<>();
    cubes = new HashMap<>();
    stations = new HashMap<>();
    cures = new HashMap<>();
    decks = new HashMap<>();
    cityCircle = new JLabel();
    infectionMarker = new JLabel();
    outbreakMarker = new JLabel();
    resize = false;

    Timer repaintTimer = new Timer(100, (event) -> repaint());
    repaintTimer.start();

    Timer resizeTimer = new Timer(500, (event) -> doResize());
    resizeTimer.start();
  }

  private void doResize() {
    if (resize) {
      listener.onMapResize();
      resize = false;
    }
  }

  public void setMapImage(BufferedImage image) {
    mapImage = image;
    mapLabel.setIcon(new ImageIcon(mapImage));
  }

  public void drawPlayer(Role role, JLabel label) {
    if (roles.containsKey(role)) {
      mapLabel.remove(roles.get(role));
    }

    roles.put(role, label);
    mapLabel.add(label);
  }

  public void drawCubes(String cityName, JLabel[] labels) {
    if (cubes.containsKey(cityName)) {
      for (JLabel label : cubes.get(cityName)) {
        mapLabel.remove(label);
      }
    }

    cubes.put(cityName, labels);
    for (JLabel label : labels) {
      mapLabel.add(label);
    }
  }

  public void drawDeck(String key, JLabel deck) {
    if (decks.containsKey(key)) {
      mapLabel.remove(decks.get(key));
    }

    decks.put(key, deck);
    mapLabel.add(deck);
  }

  public void removeResearchStations() {
    for (JLabel label : stations.values()) {
      mapLabel.remove(label);
    }
    stations.clear();
  }

  public void drawResearchStation(String cityName, JLabel label) {
    stations.put(cityName, label);
    mapLabel.add(label);
  }

  public void drawInfectionMarker(JLabel label) {
    mapLabel.remove(infectionMarker);

    infectionMarker = label;
    mapLabel.add(label);
  }

  public void drawOutbreakMarker(JLabel label) {
    mapLabel.remove(outbreakMarker);

    outbreakMarker = label;
    mapLabel.add(label);
  }

  public void drawCityCircle(JLabel label) {
    removeCityCircle();

    cityCircle = label;
    mapLabel.add(label);
  }

  public void removeCityCircle() {
    mapLabel.remove(cityCircle);
  }

  public void drawCure(String color, JLabel eradicatedLabel) {
    if (cures.containsKey(color)) {
      mapLabel.remove(cures.get(color));
    }

    cures.put(color, eradicatedLabel);
    mapLabel.add(eradicatedLabel);
  }

  @Override
  public void paintComponent(Graphics graphics) {
    super.paintComponents(graphics);
    if (sizeChanged()) {
      Image temp = resizeBufferedImage(mapImage, getWidth(), getHeight());
      mapLabel.setIcon(new ImageIcon(temp));
      width = temp.getWidth(null);
      height = temp.getHeight(null);
      resize = true;
    }
  }

  private BufferedImage resizeBufferedImage(BufferedImage image, int width, int height) {
    if (image.getHeight() == height && image.getWidth() == width) {
      return image;
    }
    BufferedImage resizedImg = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);
    Graphics2D g2 = resizedImg.createGraphics();
    g2.setRenderingHint(KEY_INTERPOLATION, VALUE_INTERPOLATION_BILINEAR);
    g2.drawImage(image, 0, 0, width, height, null);
    g2.dispose();
    return resizedImg;
  }

  private boolean sizeChanged() {
    return height != getHeight() || width != getWidth();
  }

  public void setMapListener(MapListener listener) {
    this.listener = listener;
  }

  public void onMapClicked(int xposition, int yposition) {
    listener.onMapSelected(xposition, yposition);
  }

  public interface MapListener {
    void onMapResize();

    void onMapSelected(int xposition, int yposition);
  }

}