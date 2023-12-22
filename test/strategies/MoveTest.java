package strategies;


import org.junit.Assert;
import org.junit.Test;

/**
 * Tests for the Move class.
 */
public class MoveTest {

  @Test
  public void testMoveInequality() {
    Move move1 = new Move(2, 3);
    Move move2 = new Move(4, 5);
    Assert.assertNotEquals(move1, move2);
  }

}