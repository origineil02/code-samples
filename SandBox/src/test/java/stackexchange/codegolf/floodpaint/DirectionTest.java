package stackexchange.codegolf.floodpaint;

import junit.framework.TestCase;

public class DirectionTest extends TestCase {

  public void test_N_S() {
    
    final Coordinate c1 = new Coordinate(2, 2);
    final Coordinate c2 = new Coordinate(1, 2);
    final Node n1 = new Node(c1, 1);
    final Node n2 = new Node(c2, 1);
    
    assertEquals(Direction.S, Direction.evaluateFromDirection(n1, n2));
    assertEquals(Direction.N, Direction.evaluateFromDirection(n2, n1));
  }
  
  public void test_E_W() {
    
    final Coordinate c1 = new Coordinate(2, 2);
    final Coordinate c2 = new Coordinate(2, 1);
    final Node n1 = new Node(c1, 1);
    final Node n2 = new Node(c2, 1);
    
    assertEquals(Direction.W, Direction.evaluateFromDirection(n1, n2));
    assertEquals(Direction.E, Direction.evaluateFromDirection(n2, n1));
  }
}
