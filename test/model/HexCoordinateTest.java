package model;


import org.junit.Assert;
import org.junit.Test;

/**
  * Tests for the HexCoordinate class.
 */

public class HexCoordinateTest {

  @Test
  public void testHexCoordinate() {
    HexCoordinate hexCoordinate = new HexCoordinate(7, 3);
    Assert.assertEquals(hexCoordinate.getQ(), 7);
    Assert.assertEquals(hexCoordinate.getR(), 3);
  }

  @Test
  public void testEquals() {
    HexCoordinate hexCoordinate = new HexCoordinate(5, 4);
    HexCoordinate hexCoordinate2 = new HexCoordinate(5, 4);
    Assert.assertEquals(hexCoordinate, hexCoordinate2);
  }

  @Test
  public void testNotEquals() {
    HexCoordinate hexCoordinate = new HexCoordinate(5, 8);
    HexCoordinate hexCoordinate2 = new HexCoordinate(2, 3);
    Assert.assertNotEquals(hexCoordinate, hexCoordinate2);
  }

  @Test
  public void testHashCode() {
    HexCoordinate hexCoordinate = new HexCoordinate(6, 2);
    HexCoordinate hexCoordinate2 = new HexCoordinate(6, 2);
    Assert.assertEquals(hexCoordinate.hashCode(), hexCoordinate2.hashCode());
  }

  @Test
  public void testNotHashCode() {
    HexCoordinate hexCoordinate = new HexCoordinate(3, 1);
    HexCoordinate hexCoordinate2 = new HexCoordinate(1, 3);
    Assert.assertNotEquals(hexCoordinate.hashCode(), hexCoordinate2.hashCode());
  }

  @Test
  public void testGetQ() {
    HexCoordinate hexCoordinate = new HexCoordinate(2, 2);
    Assert.assertEquals(hexCoordinate.getQ(), 2);
  }

}