package stackexchange.codegolf.floodpaint;

import junit.framework.TestCase;

public class SolverTest extends TestCase {
  
  public SolverTest(String testName) {
    super(testName);
  }
  
  public void testSomeMethod() {
    
    final StringBuilder sb = new StringBuilder();
    final Board b = new Board(5);
    for(int i = 0; i < 5; i++){
      for(int j = 0; j < 5; j++){
        final Coordinate c = new Coordinate(i, j);
        b.addNode(new Node(c, j));
        sb.append(new Node(c, j).toString());
      }
      sb.append("\n");
    }
    
    assertEquals(b.toString(), sb.toString());
    
    assertEquals("[2, 1, 0, 3, 4]", new Solver(b).solve().toString());
  }
}
