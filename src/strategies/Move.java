package strategies;

/**
 * A Reversi strategy that prioritizes moves based on capturing.
 */
public class Move implements Comparable<Move> {
  private final int q;
  private final int r;

  /**
   * Constructor.
   * @param q q coordinate.
   * @param r r coordinate.
   */
  public Move(int q, int r) {
    this.q = q;
    this.r = r;
  }

  public int getQ() {
    return q;
  }

  public int getR() {
    return r;
  }

  /**
   * Compares this move with another move based on coordinates.
   * @param other The move to compare with.
   * @return A negative integer, zero, or a positive integer as this move is less than, equal to,
   *         or greater than the specified move.
   */
  @Override
  public int compareTo(Move other) {
    if (this.q != other.q) {
      return Integer.compare(this.q, other.q);
    }
    return Integer.compare(this.r, other.r);
  }

  /**
   * Returns a string representation of the move in the format "(q, r)".
   * @return The string representation of the move.
   */
  @Override
  public String toString() {
    return String.format("(%d, %d)", q, r);
  }

  /**
   * Checks if this move is equal to another object.
   * @param other The object to compare with.
   * @return True if the objects are equal, false otherwise.

   */
  @Override
  public boolean equals(Object other) {
    if (other instanceof Move) {
      Move that = (Move) other;
      return this.q == that.q && this.r == that.r;
    }
    return false;
  }

  /**
   * Returns the hash code of the move based on its coordinates.
   * @return The hash code of the move.
   */

  @Override
  public int hashCode() {
    return q * 31 + r;
  }
}
