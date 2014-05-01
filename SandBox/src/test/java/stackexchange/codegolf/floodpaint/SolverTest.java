package stackexchange.codegolf.floodpaint;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import junit.framework.TestCase;

public class SolverTest extends TestCase {

  public SolverTest(String testName) {
    super(testName);
    
  }

  public void testSomeMethod() {

    final StringBuilder sb = new StringBuilder();
    final Board b = new Board(5);
    for (int i = 0; i < 5; i++) {
      for (int j = 0; j < 5; j++) {
        final Coordinate c = new Coordinate(i, j);
        b.addNode(new Node(c, j));
        sb.append(new Node(c, j).toString());
      }
      sb.append("\n");
    }

    System.out.println(sb.toString());
    assertEquals(b.toString(), sb.toString());

    
    //assertEquals("[1, 0, 3, 4]", new Solver(b).solve().toString());
  }
  
  public void testPuzzle53(){
    final String str = "1333663615124616645\n" +
"3364142155624313113\n" +
"4262154154141321461\n" +
"2462514344222222226\n" +
"5451251123654256444\n" +
"3225266121453515526\n" +
"5563335164334353141\n" +
"6645141636652214464\n" +
"6634143263233614551\n" +
"4535122131364223453\n" +
"6526434326462661456\n" +
"6525143344236145156\n" +
"2442266655263612142\n" +
"6425121544622533411\n" +
"1365526556466253124\n" +
"5233145321541264161\n" +
"2214216115465554212\n" +
"3516235532344511553\n" +
"5346451324115365251";
    
     
     final StringTokenizer rows = new StringTokenizer(str,"\n");
     
     final Board b = new Board(19);
     int rowIndex = 0;
     while(rows.hasMoreElements()){
       final String row = rows.nextToken();
       
       int columnIndex = 0;
       for(Character c : row.toCharArray()){
         final Coordinate coord = new Coordinate(rowIndex, columnIndex++);
         //System.out.println(coord);
         b.addNode(new Node(coord, Integer.parseInt(c.toString())));
       }
       rowIndex++;
     }
     System.out.println(b.asValues());
 
     new Solver(b).solve();
  }
  
  public void testCaseWriter() throws FileNotFoundException, IOException{
    
     File f = new File("testCases.java");
     OutputStream out = new FileOutputStream(f);
     OutputStreamWriter writer = new OutputStreamWriter(out);
    
    final Scanner scanner = new Scanner(new File("floodtest"));
    
     StringBuilder sb = new StringBuilder();
    int puzzle = 1;
    
    
     while(scanner.hasNextLine()){
      
      final String line = scanner.nextLine()  ;
      //System.out.println(line);
      if(line.trim().isEmpty()){
         
        writer.append("\npublic void testPuzzle" + puzzle  + "(){ \nfinal String str="+ sb.toString() +"; Util.solve(str);}");
        writer.append("");
        sb = new StringBuilder();
        
        if(puzzle % 100 ==0){
            writer.flush();
     writer.close();
     out.close();
          f = new File("testCases" +puzzle+".java");
       out = new FileOutputStream(f);
       writer = new OutputStreamWriter(out);
        }
        
        puzzle++;
      }
      else{
        
      sb.append("\"" + line + "\\n\"+");
      }
    }
     System.out.println(puzzle);
     
   
  }
}
