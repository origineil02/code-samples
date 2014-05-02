
import java.io.File;
import java.io.FileWriter;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import stackexchange.codegolf.floodpaint.Board;
import stackexchange.codegolf.floodpaint.Coordinate;
import stackexchange.codegolf.floodpaint.Node;
import stackexchange.codegolf.floodpaint.SolutionRepository;
import stackexchange.codegolf.floodpaint.Solver;

public class SolverRunner implements Runnable{

  public void run() {
      final ResourceBundle bundle = ResourceBundle.getBundle("stackexchange.codegolf.floodpaint.PuzzlesBundle");
     
      try{
      final FileWriter writer = new FileWriter(new File("SomeSolutions.txt"));
      for (int i = 1840; i <= 10000; i++) {
      final String str = bundle.getString("Puzzle"+i);
      process(str);
        writer.write("Puzzle"+i+ "=" + SolutionRepository.getInstance().bestAnswer());
        writer.write("\n");
        writer.flush();
        SolutionRepository.getInstance().reset();
      
    }
      }catch(Exception ex){}
  }
 
  private void process(String str){
    
    str = str.replace("\"", "");
    //System.out.println(str);
    final StringTokenizer rows = new StringTokenizer(str, "\n");

    final Board b = new Board(19);
    int rowIndex = 0;
    while (rows.hasMoreElements()) {
      final String row = rows.nextToken();

      int columnIndex = 0;
      for (Character c : row.toCharArray()) {
        final Coordinate coord = new Coordinate(rowIndex, columnIndex++);
        //System.out.println(coord);
        b.addNode(new Node(coord, Integer.parseInt(c.toString())));
      }
      rowIndex++;
    }
    //System.out.println(b.asValues());

    new Solver(b).solve();
  }
}
