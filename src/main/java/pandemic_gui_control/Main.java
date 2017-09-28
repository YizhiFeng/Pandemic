
package pandemic_gui_control;

import static javax.swing.JOptionPane.ERROR_MESSAGE;

import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import pandemic_game.Game;
import pandemic_game.GameLogic;
import pandemic_gui.ButtonDisplayImpl;
import pandemic_gui.CardDisplayImpl;
import pandemic_gui.GuiImpl;
import pandemic_gui.MapDisplayImpl;
import pandemic_gui.MapPanel;
import pandemic_gui.StartMenuImpl;
import pandemic_initialization.LocaleLookup;
import pandemic_initialization.MainCityInitializer;
import pandemic_initialization.MainDeckInitializer;

public class Main {

  public static void main(String[] args) {

    try {
      StartMenu menu = new StartMenuImpl(new JFrame(), new LocaleLookup());
      while (true) {
        if (menu.readyToStartGame()) {
          break;
        }
        menu.refreshView();
      }
      int players = menu.getNumPlayers();
      int epicCards = menu.getNumOfEpidemicCards();
      menu.close();

      GameLogic pandemicGame = new GameLogic(new Game(players, epicCards));
      pandemicGame.initialize(new MainDeckInitializer(), new MainCityInitializer());
      GameState game = new GameAdapter(pandemicGame);

      JLabel mapLabel = new JLabel();
      MapPanel map = new MapPanel(mapLabel);
      JPanel buttons = new JPanel();
      JPanel cards = new JPanel();

      Gui gui = new GuiImpl(buttons, map, cards);
      gui.setButtonControl(new ButtonDisplayImpl(buttons));
      gui.setMapControl(new MapDisplayImpl(map, new LabelFactory()));
      gui.setCardControl(new CardDisplayImpl(cards));
      gui.startShowing();

      GameControl control = new GameControl(game, gui);

      control.start();

    } catch (Exception exception) {
      String message = ResourceBundle.getBundle("MessageBundle").getString("error");
      JOptionPane.showMessageDialog(new JFrame(), exception.getMessage(), message, ERROR_MESSAGE);
      System.exit(0);
    }
  }

}
