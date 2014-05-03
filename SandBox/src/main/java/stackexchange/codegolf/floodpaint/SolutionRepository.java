package stackexchange.codegolf.floodpaint;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class SolutionRepository {

  private Map<TurnKey, Data> map = new TreeMap<TurnKey, Data>(new Comparator<TurnKey>() {
    public int compare(TurnKey t, TurnKey t1) {
      int rc = t.getTurn().compareTo(t1.getTurn());
      return rc != 0 ? rc : t.getSelectedValue().compareTo(t1.getSelectedValue());
    }
  });
  private Map<Integer, List<List<Integer>>> solutions = new TreeMap<Integer, List<List<Integer>>>();
  
  private Set<TurnKey> investigated = new HashSet<TurnKey>();
  private static SolutionRepository instance;
  private Integer threshold = Integer.MAX_VALUE;
  private Integer puzzleNumber;

  private SolutionRepository() {
  }

  public Integer getPuzzleNumber() {
    return puzzleNumber;
  }

  public static SolutionRepository getInstance() {
    if (instance == null) {
      instance = new SolutionRepository();
    }
    return instance;
  }

  public Integer getThreshold() {
    return threshold;
  }

  public void setThreshold(Integer threshold) {
    this.threshold = threshold;
  }

  void runOtherScenarios() {

     

    final Set<TurnKey> keys = new LinkedHashSet<TurnKey>(map.keySet());
    for (TurnKey key : keys) {

//      System.out.println("Turn " + key.getTurn() + " (" + map.get(key).getTarget() + ")");
//      System.out.println();
//      System.out.println("Other moves: " + map.get(key).byTheNumbers().keySet());


      for (Map.Entry<Integer, Set<Node>> entry1 : map.get(key).byTheNumbers().entrySet()) {
        runAlternateMove(entry1.getKey(), map.get(key));
      }

//      System.out.println("--------------------------------------------------------");
    }
    
//    System.out.println(solutions);
  }

  void addSolution(final List<Integer> input) {
    
    if(!solutions.containsKey(input.size())){
      solutions.put(input.size(), new ArrayList<List<Integer>>());
    }
    solutions.get(input.size()).add(input);
  }

  void addSnapshot(final Data data, final List<Integer> input) {
    map.put(new TurnKey(input.size(), data.getTarget()), data);
  }

  private void runAlternateMove(Integer key, Data data) {

    if (!key.equals(data.getTarget())) {

      final TurnKey turnKey = new TurnKey(data.getInput().size(), key);
//      System.out.println(turnKey.getTurn());
      if (!investigated.contains(turnKey)) {
        investigated.add(turnKey);
        
//        System.out.println("What if " + key + "?");

        List<Integer> input = data.getInput();
        input.remove(input.size() - 1);
        input.add(key);
        new Solver(data, key, input).solve();
      }
    }
  }

   public List<Integer> bestAnswer() {
    return solutions.entrySet().iterator().next().getValue().get(0);
  }
   
  public void reset(){
     instance = new SolutionRepository();
   }
}
