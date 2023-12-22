package view;

import model.HexCoordinate;

/**
 * Utility class for hexagonal grid operations.
 */

public class HexUtils {

  /**
   * Converts an axial hex coordinate to an even-row offset hex coordinate.
   *
   * @param hex The axial hex coordinate to convert.
   * @return The corresponding even-row offset hex coordinate.
   */

  public static OffsetCoord axialToEvenR(HexCoordinate hex) {
    int row = hex.getR();
    return new OffsetCoord(row);
  }

  /**
   * Represents an offset coordinate in a hexagonal grid.
   */

  public static class OffsetCoord {
    private final int row;

    /**
     * Constructs an offset coordinate with the specified row.
     *
     * @param row The row of the offset coordinate.
     */

    public OffsetCoord(int row) {
      this.row = row;
    }


    /**
     * Gets the row of the offset coordinate.
     *
     * @return The row of the offset coordinate.
     */

    public int getRow() {
      return row;
    }
  }

  /**
   * Represents a hexagonal coordinate with axial (q, r) values.
   */

  public static class HexCoordinate {
    private final int q;
    private final int r;

    /**
     * Constructs a hexagonal coordinate with the specified axial values.
     *
     * @param q The axial q value.
     * @param r The axial r value.
     */

    public HexCoordinate(int q, int r) {
      this.q = q;
      this.r = r;
    }

    /**
     * Gets the q value of the hexagonal coordinate.
     *
     * @return The q value.
     */

    public int getQ() {
      return q;
    }

    /**
     * Gets the r value of the hexagonal coordinate.
     *
     * @return The r value.
     */
    public int getR() {
      return r;
    }
  }



}

