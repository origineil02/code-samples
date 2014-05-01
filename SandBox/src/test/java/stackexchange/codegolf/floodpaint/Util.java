package stackexchange.codegolf.floodpaint;


import java.util.StringTokenizer;
import org.junit.Ignore;

@Ignore
public class Util {

    
public void solve(String str){     
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
  
   
}
