
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;
import stackexchange.codegolf.floodpaint.Board;
import stackexchange.codegolf.floodpaint.Coordinate;
import stackexchange.codegolf.floodpaint.Node;
import stackexchange.codegolf.floodpaint.SolutionRepository;
import stackexchange.codegolf.floodpaint.Solver;

//BUILD SUCCESSFUL (total time: 220 minutes 15 seconds)
//Total steps: 2403189
public class SolverRunner implements Runnable{
  private Integer currentPuzzle;

  public SolverRunner(Integer number) {
    this.currentPuzzle = number;
  }
  
  
  public void run() {
      final ResourceBundle bundle = ResourceBundle.getBundle("stackexchange.codegolf.floodpaint.PuzzlesBundle");
     
      try{
        int total = 0;
      final FileWriter writer = new FileWriter(new File("SomeSolutions"+ currentPuzzle+".txt"));
      for (int i = currentPuzzle; i <= 100000; i++) {
        currentPuzzle = i;
      final String str = bundle.getString("Puzzle"+i);
      process(str);
      final List<Integer> solution = SolutionRepository.getInstance().bestAnswer();
        writer.write("Puzzle"+i+ "=" +  solution);
         //final String numbersOnly = solution.replace("[", "").replace("]", "").replaceAll(",", "").replaceAll(" ", "").trim();
         total+=solution.size();
        writer.write("\n");
        writer.flush();
        SolutionRepository.getInstance().reset();
      
    }
        System.out.println(total);
      }catch(Exception ex){ex.printStackTrace();
        final Thread t = new Thread(new SolverRunner(currentPuzzle));
        t.start();
      }
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
