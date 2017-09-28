package pandemic_gui;

import static java.awt.RenderingHints.KEY_INTERPOLATION;
import static java.awt.RenderingHints.VALUE_INTERPOLATION_BILINEAR;
import static pandemic_game.Constants.Role.Dispatcher;
import static pandemic_game.Constants.Role.Medic;
import static pandemic_game.Constants.Role.OperationExpert;
import static pandemic_game.Constants.Role.Researcher;
import static pandemic_game.Constants.Role.Scientist;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

import pandemic_exception.InitializationException;
import pandemic_game.Constants.CubeColor;
import pandemic_game.Constants.Role;
import pandemic_gui.MapPanel.MapListener;
import pandemic_gui_control.GuiListener;
import pandemic_gui_control.LabelFactory;
import pandemic_gui_control.MapDisplay;

public class MapDisplayImpl implements MapDisplay, MapListener {

  private static final int PLAYER_WIDTH = 80;
  private static final int PLAYER_HEIGHT = 40;
  private static final int CUBE_WIDTH = 70;
  private static final int CUBE_HEIGHT = 60;
  private static final int OVAL_WIDTH = 25;
  private static final int OVAL_HEIGHT = 17;
  private static final int STATION_WIDTH = 40;
  private static final int STATION_HEIGHT = 40;
  private static Map<Integer, Point> cubePointLookupMap;
  private static Map<CubeColor, BufferedImage> cubeImages;
  private static ResourceBundle messages = ResourceBundle.getBundle("MessageBundle");

  private MapPanel map;
  private BufferedImage mapUnderlay;
  private BufferedImage station;
  private BufferedImage cityCircle;
  private BufferedImage infectionMarker;
  private BufferedImage outbreakMarker;
  private BufferedImage playerCardBack;
  private BufferedImage infectionCardBack;
  private Map<String, BufferedImage> eradicatedMarkers;
  private Map<String, BufferedImage> curedMarkers;
  private Map<Role, RoleInfo> roleMap;
  private Map<Role, JLabel> playerLabels;
  private LabelFactory factory;
  private GuiListener listener;

  public MapDisplayImpl(MapPanel map, LabelFactory factory) {
    this.factory = factory;
    this.map = map;
    this.map.setMapImage(loadImage("pandemic_board.jpg"));
    this.map.setMapListener(this);
    createRoleMap();
    createCubePointLookupMap();
    loadImages();
    playerLabels = new HashMap<>();
  }

  private void createRoleMap() {
    roleMap = new HashMap<>();
    roleMap.put(Medic, new RoleInfo(15, 20));
    roleMap.put(Dispatcher, new RoleInfo(0, 20));
    roleMap.put(Scientist, new RoleInfo(15, 0));
    roleMap.put(Researcher, new RoleInfo(23, 10));
    roleMap.put(OperationExpert, new RoleInfo(8, 30));
  }

  private void createCubePointLookupMap() {
    cubePointLookupMap = new HashMap<>();
    cubePointLookupMap.put(0, new Point(8, -15));
    cubePointLookupMap.put(1, new Point(22, -15));
    cubePointLookupMap.put(2, new Point(22, 2));
    cubePointLookupMap.put(3, new Point(22, 16));
    cubePointLookupMap.put(4, new Point(13, -31));
    cubePointLookupMap.put(5, new Point(31, -31));
    cubePointLookupMap.put(6, new Point(40, -15));
    cubePointLookupMap.put(7, new Point(40, 2));
    cubePointLookupMap.put(8, new Point(40, 16));
    cubePointLookupMap.put(9, new Point(22, -45));
  }

  private void loadImages() {
    cubeImages = new HashMap<>();
    eradicatedMarkers = new HashMap<>();
    curedMarkers = new HashMap<>();
    mapUnderlay = loadImage("pandemic_board_underlay.jpg");
    station = loadImage("research_station.png");
    cityCircle = loadImage("city_circle.png");
    infectionMarker = loadImage("infection_rate_marker.png");
    outbreakMarker = loadImage("outbreak_marker.png");
    playerCardBack = loadImage("player_card_back_vertical.jpg");
    infectionCardBack = loadImage("infection_card_back.png");
    cubeImages.put(CubeColor.YELLOW, loadImage("cube_YELLOW.png"));
    cubeImages.put(CubeColor.BLUE, loadImage("cube_BLUE.png"));
    cubeImages.put(CubeColor.BLACK, loadImage("cube_BLACK.png"));
    cubeImages.put(CubeColor.RED, loadImage("cube_RED.png"));
    roleMap.get(Medic).image = loadImage("player_image_orange.png");
    roleMap.get(Dispatcher).image = loadImage("player_image_pink.png");
    roleMap.get(Scientist).image = loadImage("player_image_white.png");
    roleMap.get(Researcher).image = loadImage("player_image_brown.png");
    roleMap.get(OperationExpert).image = loadImage("player_image_green.png");
    eradicatedMarkers.put("YELLOW", loadImage("eradicated_yellow.png"));
    eradicatedMarkers.put("RED", loadImage("eradicated_red.png"));
    eradicatedMarkers.put("BLUE", loadImage("eradicated_blue.png"));
    eradicatedMarkers.put("BLACK", loadImage("eradicated_black.png"));
    curedMarkers.put("YELLOW", loadImage("cure_yellow.png"));
    curedMarkers.put("RED", loadImage("cure_red.png"));
    curedMarkers.put("BLUE", loadImage("cure_blue.png"));
    curedMarkers.put("BLACK", loadImage("cure_black.png"));
  }

  private BufferedImage loadImage(String fileName) {
    try {
      fileName = "images/" + fileName;
      return ImageIO.read(ColorMap.class.getClassLoader().getResource(fileName));
    } catch (Exception e) {
      String message = messages.getString("unableToLoadMapImageException") + fileName;
      throw new InitializationException(message);
    }
  }

  public void drawPlayerAt(Role role, String cityName) {
    playerLabels.remove(role);
    mapUnderlay = resizeBufferedImage(mapUnderlay, map.getWidth(), map.getHeight());
    RoleInfo info = roleMap.get(role);
    Color toMatch = ColorMapper.get(cityName);
    Point pnt = findColorInUnderlay(toMatch);
    JLabel labl = factory.createLabel(info.image, pnt.xpos - info.offsetX, pnt.ypos - info.offsetY,
        PLAYER_WIDTH, PLAYER_HEIGHT, map.width, map.height);
    map.drawPlayer(role, labl);
    playerLabels.put(role, labl);
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

  public void drawCubesOn(int[] numCubes, CubeColor[] colors, String cityName) {
    Point city = findColorInUnderlay(ColorMapper.get(cityName));
    int positionLookup = 0;
    Point off = null;
    BufferedImage image = null;
    JLabel cubeLabel = null;
    List<JLabel> cubes = new ArrayList<>();

    for (int i = 0; i < numCubes.length; i++) {
      image = cubeImages.get(colors[i]);
      for (int j = 0; j < numCubes[i]; j++) {
        off = cubePointLookupMap.get(positionLookup);
        cubeLabel = factory.createLabel(image, city.xpos + off.xpos, city.ypos + off.ypos,
            CUBE_WIDTH, CUBE_HEIGHT, map.width, map.height);
        cubes.add(cubeLabel);
        positionLookup++;
      }
    }

    JLabel[] labels = new JLabel[cubes.size()];
    cubes.toArray(labels);
    map.drawCubes(cityName, labels);
  }

  public void drawResearchStations(String[] cityNames) {
    map.removeResearchStations();
    for (String cityName : cityNames) {
      Point pnt = findColorInUnderlay(ColorMapper.get(cityName));
      JLabel label = factory.createLabel(station, pnt.xpos - 5, pnt.ypos + 20, STATION_WIDTH,
          STATION_HEIGHT, map.width, map.height);
      map.drawResearchStation(cityName, label);
    }
  }

  public void showSelectedCity(String cityName) {
    if (cityName.isEmpty()) {
      map.removeCityCircle();
      return;
    }
    Color toMatch = ColorMapper.get(cityName);
    Point pnt = findColorInUnderlay(toMatch);
    JLabel circleLabel = factory.createLabel(cityCircle, pnt.xpos - 6, pnt.ypos - 17, OVAL_WIDTH,
        OVAL_HEIGHT, map.width, map.height);
    map.drawCityCircle(circleLabel);
  }

  public void drawInfectionRate(int rateIndex) {
    String key = "InfectionRate" + rateIndex;
    Color toMatch = ColorMapper.get(key);
    Point pnt = findColorInUnderlay(toMatch);
    JLabel infectionLabel = factory.createLabel(infectionMarker, pnt.xpos - 10, pnt.ypos - 10, 25,
        25, map.width, map.height);
    map.drawInfectionMarker(infectionLabel);
  }

  public void drawOutbreaks(int numOfOutbreaksOccured) {
    String key = "outbreak" + numOfOutbreaksOccured;
    Color toMatch = ColorMapper.get(key);
    Point pnt = findColorInUnderlay(toMatch);
    JLabel outbreakLabel = factory.createLabel(outbreakMarker, pnt.xpos - 24, pnt.ypos - 20, 15, 15,
        map.width, map.height);
    map.drawOutbreakMarker(outbreakLabel);
  }

  public void drawEradicatedDiseases(String[] eradicatedDiseases) {
    String key = "";
    Color toMatch = null;
    Point pnt = null;
    for (String color : eradicatedDiseases) {
      key = "Cure" + color;
      toMatch = ColorMapper.get(key);
      pnt = findColorInUnderlay(toMatch);
      JLabel eradicatedLabel = factory.createLabel(eradicatedMarkers.get(color), pnt.xpos + 3,
          pnt.ypos - 15, 40, 20, map.width, map.height);
      map.drawCure(color, eradicatedLabel);
    }
  }

  public void drawCuredDiseases(String[] curedDiseases) {
    String key = "";
    Color toMatch = null;
    Point pnt = null;
    for (String color : curedDiseases) {
      key = "Cure" + color;
      toMatch = ColorMapper.get(key);
      pnt = findColorInUnderlay(toMatch);
      JLabel eradicatedLabel = factory.createLabel(curedMarkers.get(color), pnt.xpos + 3,
          pnt.ypos - 15, 40, 20, map.width, map.height);
      map.drawCure(color, eradicatedLabel);
    }
  }

  @Override
  public void drawPlayerDeck() {
    String key = "PlayerDeck";
    drawDeck(key, playerCardBack, 9, 5);
  }

  @Override
  public void drawPlayerDiscard() {
    String key = "PlayerDiscard";
    drawDeck(key, playerCardBack, 9, 5);
  }

  @Override
  public void drawInfectionDeck() {
    String key = "InfectionDeck";
    drawDeck(key, infectionCardBack, 7, 7);
  }

  @Override
  public void drawInfectionDiscard() {
    String key = "InfectionDiscard";
    drawDeck(key, infectionCardBack, 7, 7);
  }

  private void drawDeck(String key, BufferedImage image, int widthScale, int heightScale) {
    Color toMatch = ColorMapper.get(key);
    Point pnt = findColorInUnderlay(toMatch);
    JLabel playerDeck = factory.createLabel(image, pnt.xpos, pnt.ypos, widthScale, heightScale,
        map.width, map.height);
    map.drawDeck(key, playerDeck);
  }

  private Point findColorInUnderlay(Color toMatch) {
    Color potentialMatch = null;
    for (int x = 0; x < mapUnderlay.getWidth(null); x++) {
      for (int y = 0; y < mapUnderlay.getHeight(null); y++) {
        potentialMatch = new Color(mapUnderlay.getRGB(x, y));
        if (ColorMapper.colorsEqual(toMatch, potentialMatch)) {
          return new Point(x, y);
        }
      }
    }
    return new Point(-100, -100);
  }

  @Override
  public void setGuiListener(GuiListener listener) {
    this.listener = listener;
  }

  private static class Point {
    int xpos;
    int ypos;

    Point(int xpos, int ypos) {
      this.xpos = xpos;
      this.ypos = ypos;
    }
  }

  private static class RoleInfo {
    RoleInfo(int xoff, int yoff) {
      offsetX = xoff;
      offsetY = yoff;
    }

    int offsetX;
    int offsetY;
    BufferedImage image;
  }

  @Override
  public void onMapResize() {
    listener.onResize();
  }

  @Override
  public void onMapSelected(int xposition, int yposition) {
    Color toMatch = new Color(mapUnderlay.getRGB(xposition, yposition));
    try {
      String thing = ColorMapper.get(toMatch);

      listener.onMapSelected(thing);
    } catch (NoSuchElementException except) {
      listener.onMapSelected("");
    }
  }

}
