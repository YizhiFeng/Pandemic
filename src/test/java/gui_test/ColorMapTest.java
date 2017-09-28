package gui_test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.util.NoSuchElementException;

import org.easymock.EasyMock;
import org.junit.Test;

import pandemic_gui.ColorMap;
import pandemic_gui.ColorMapper;

public class ColorMapTest {

  @Test
  public void testGetBothDirections() {
    ColorMap map = new ColorMap();
    Color color = EasyMock.niceMock(Color.class);

    EasyMock.replay(color);
    map.add("Atlanta", color);

    assertEquals(color, map.get("Atlanta"));
    assertEquals("Atlanta", map.get(color));
    EasyMock.verify(color);
  }

  @Test
  public void testTwoEntries() {
    Color color = EasyMock.mock(Color.class);
    Color color2 = EasyMock.mock(Color.class);

    EasyMock.expect(color.getBlue()).andReturn(10);
    EasyMock.expect(color.getBlue()).andReturn(10);
    EasyMock.expect(color.getBlue()).andReturn(10);
    EasyMock.expect(color.getBlue()).andReturn(10);
    EasyMock.expect(color.getRed()).andReturn(10);
    EasyMock.expect(color.getRed()).andReturn(10);
    EasyMock.expect(color.getGreen()).andReturn(10);
    EasyMock.expect(color.getGreen()).andReturn(10);

    EasyMock.expect(color2.getBlue()).andReturn(20);
    EasyMock.expect(color2.getBlue()).andReturn(20);
    EasyMock.expect(color2.getBlue()).andReturn(20);
    EasyMock.expect(color2.getBlue()).andReturn(20);
    EasyMock.expect(color2.getRed()).andReturn(20);
    EasyMock.expect(color2.getRed()).andReturn(20);
    EasyMock.expect(color2.getGreen()).andReturn(20);
    EasyMock.expect(color2.getGreen()).andReturn(20);

    EasyMock.replay(color, color2);
    ColorMap map = new ColorMap();

    map.add("Atlanta", color);
    map.add("Chicago", color2);

    assertEquals(color, map.get("Atlanta"));
    assertEquals("Atlanta", map.get(color));
    assertEquals(color2, map.get("Chicago"));
    assertEquals("Chicago", map.get(color2));
    EasyMock.verify(color, color2);
  }

  @Test(expected = NoSuchElementException.class)
  public void testColorNotFound() {
    ColorMap map = new ColorMap();
    Color color = EasyMock.niceMock(Color.class);
    EasyMock.replay(color);

    map.get(color);

    EasyMock.verify(color);
  }

  @Test
  public void testColorsMatchingMaximumAllowance() {
    Color color = EasyMock.mock(Color.class);
    Color color2 = EasyMock.mock(Color.class);

    EasyMock.expect(color.getBlue()).andReturn(2);
    EasyMock.expect(color.getRed()).andReturn(2);
    EasyMock.expect(color.getGreen()).andReturn(2);

    EasyMock.expect(color2.getBlue()).andReturn(0);
    EasyMock.expect(color2.getRed()).andReturn(0);
    EasyMock.expect(color2.getGreen()).andReturn(0);

    EasyMock.replay(color, color2);

    assertTrue(ColorMapper.colorsEqual(color, color2));
    EasyMock.verify(color, color2);
  }

  @Test
  public void testColorsBlueOneOverMaximumAllowance() {
    Color color = EasyMock.mock(Color.class);
    Color color2 = EasyMock.mock(Color.class);

    EasyMock.expect(color.getBlue()).andReturn(3);
    EasyMock.expect(color2.getBlue()).andReturn(0);

    EasyMock.replay(color, color2);

    assertFalse(ColorMapper.colorsEqual(color, color2));
    EasyMock.verify(color, color2);
  }

  @Test
  public void testColorsRedOneOverMaximumAllowance() {
    Color color = EasyMock.mock(Color.class);
    Color color2 = EasyMock.mock(Color.class);

    EasyMock.expect(color.getBlue()).andReturn(2);
    EasyMock.expect(color.getRed()).andReturn(3);

    EasyMock.expect(color2.getBlue()).andReturn(0);
    EasyMock.expect(color2.getRed()).andReturn(0);

    EasyMock.replay(color, color2);

    assertFalse(ColorMapper.colorsEqual(color, color2));
    EasyMock.verify(color, color2);
  }

  @Test
  public void testColorsGreenOneOverMaximumAllowance() {
    Color color = EasyMock.mock(Color.class);
    Color color2 = EasyMock.mock(Color.class);

    EasyMock.expect(color.getBlue()).andReturn(2);
    EasyMock.expect(color.getRed()).andReturn(2);
    EasyMock.expect(color.getGreen()).andReturn(3);

    EasyMock.expect(color2.getBlue()).andReturn(0);
    EasyMock.expect(color2.getRed()).andReturn(0);
    EasyMock.expect(color2.getGreen()).andReturn(0);

    EasyMock.replay(color, color2);

    assertFalse(ColorMapper.colorsEqual(color, color2));
    EasyMock.verify(color, color2);
  }

}
