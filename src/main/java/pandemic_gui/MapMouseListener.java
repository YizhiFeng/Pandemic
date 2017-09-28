package pandemic_gui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MapMouseListener implements MouseListener {

  private MapCallbackListener listener;

  public MapMouseListener(MapCallbackListener listener) {
    this.listener = listener;
  }

  @Override
  public void mouseClicked(MouseEvent event) {
    listener.onMapClicked(event.getX(), event.getY());
  }

  @Override
  public void mouseEntered(MouseEvent event) {

  }

  @Override
  public void mouseExited(MouseEvent event) {

  }

  @Override
  public void mousePressed(MouseEvent event) {

  }

  @Override
  public void mouseReleased(MouseEvent event) {

  }

  public interface MapCallbackListener {
    void onMapClicked(int xposition, int yposition);
  }

}
