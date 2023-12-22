package model;

import java.util.Objects;

/**
 * Represents a hexagonal coordinate with q and r components.
 *
 * <p>The hexagonal coordinate system used here follows the axial coordinate system commonly used
 * in hexagon-based grids. The q (column) and r (row) components determine the position of a cell
 * in a hexagonal grid.</p>
 */
public class HexCoordinate {

  /**
   * The q component of the hexagonal coordinate.
   */
  private final int q;

  /**
   * The r component of the hexagonal coordinate.
   */
  private final int r;

  /**
   * Constructs a new HexCoordinate with the specified q and r components.
   *
   * @param q The q component.
   * @param r The r component.
   */
  public HexCoordinate(int q, int r) {
    this.q = q;
    this.r = r;
  }

  /**
   * Gets the q component of the hexagonal coordinate.
   *
   * @return The q component.
   */
  public int getQ() {
    return q;
  }

  /**
   * Gets the r component of the hexagonal coordinate.
   *
   * @return The r component.
   */
  public int getR() {
    return r;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    HexCoordinate that = (HexCoordinate) obj;
    return q == that.q && r == that.r;
  }

  @Override
  public int hashCode() {
    return Objects.hash(q, r);
  }

  @Override
  public String toString() {
    return "(" + q + ", " + r + ")";
  }


}