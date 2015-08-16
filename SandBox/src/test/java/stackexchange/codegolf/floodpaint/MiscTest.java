 
package stackexchange.codegolf.floodpaint;

import java.util.ResourceBundle;
import junit.framework.TestCase;


public class MiscTest extends TestCase {
 
  public void test(){
    init(1);
    
    /*
    1
  [4, 3, 2, 1, 5, 4, 6, 3, 2, 5, 1, 6, 4, 3, 6, 5, 1, 2, 3, 4, 5, 6, 4]
[2, 5, 3, 5, 4, 6, 3, 2, 5, 1, 6, 4, 3, 2, 5, 1, 6, 4, 3, 5, 2, 1, 6, 4, 3]
    */
  }
  public void init(int number){ 
final String str= ResourceBundle.getBundle("stackexchange.codegolf.floodpaint.PuzzlesBundle").getString("Puzzle"+number).replaceAll("\"", "") ; Util.solve(str);}

}
