package stackexchange.codegolf.floodpaint;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class Solver implements Runnable {

  private Set<Node> remaining;
  private Set<Node> targets;
  private Set<Node> paintable;
  private List<Integer> input;
  private final String id;
  private boolean cancelled = false;
  
  private Solver(){
   id = UUID.randomUUID().toString();
  }

  @Override
  public String toString() {
    return "Solver{" + "remaining=" + remaining + "\ntargets=" + targets + "\npaintable=" + paintable + "\ninput=" + input + "\nid=" + id + '}';
  }
  
  public Solver(final Data d, final Integer selectedValue, List<Integer> input) {
    
    this.remaining = new HashSet<Node>(d.getRemaining());
    this.paintable = new HashSet<Node>(d.getPaintable());
    
    this.targets = d.potentialTargets(d.byTheNumbers().get(selectedValue), d.getPaintable());
    
    this.input = new ArrayList<Integer>(input);
    id = UUID.randomUUID().toString();
  }
  
  public Solver(final Board b) {
    
    final Set<Node> init = b.getCenterNode().getAllLikeValuedNeighbors();
    this.remaining = new HashSet<Node>(b.getBoardState().values());
    this.paintable = new HashSet<Node>(init);
    this.paintable.add(b.getCenterNode());
    this.remaining.remove(b.getCenterNode());
    this.remaining.removeAll(init);
    
    this.input = new ArrayList<Integer>();
    this.targets = new Data().potentialTargets(init, paintable);
    
    if(targets.isEmpty()){
      targets = b.getCenterNode().getNeighbors();
    }
     id = UUID.randomUUID().toString();
  }
  
  public static Solver clone(final Solver solver, Set<Node> targets){
    Solver clone = new Solver();
    clone.input = new ArrayList<Integer>(solver.input);
    clone.remaining = new HashSet<Node>(solver.remaining);
    clone.paintable = new HashSet<Node>(solver.paintable);
    clone.targets = new HashSet<Node>(targets);
    return clone;
  }
  
  public static Collection<Node> clone(Collection<Node> source){
    Collection<Node> c = new ArrayList<Node>();
    for(Node n : source){
      c.add(Node.clone(n));
    }
    return c;
  }

  public String getId(){
    return id;
  }
  
  public void run(){
     solve();
  }
  
  public List<Integer> solve() {
    while (hasMoreElements() && !cancelled) {
      flood();
    }
    
    if(!cancelled){
    
    SolutionRepository.getInstance().setThreshold(input.size());
    SolutionRepository.getInstance().addSolution(input);
    SolutionRepository.getInstance().debug();
    }
    
    //System.out.println(SolutionRepository.getInstance().bestAnswer());
    return input;
  }

  public void flood() {

    final Data data = new Data();

    
    consolidateCandidates(data, targets);

    input.add(data.getTarget());
     
    if(input.size() > SolutionRepository.getInstance().getThreshold()){
      //System.out.println("Exceeded threshold: " + input.toString());
      cancelled = true;
    }
    paintable.addAll(data.targets());
    remaining.removeAll(data.targets());
    
    if(!data.targets().isEmpty()){
    targets = data.potentialTargets(data.targets(), paintable);
    
    data.setPaintable(paintable);
    data.setRemaining(remaining);
    data.setInput(input);
    
    SolutionRepository.getInstance().addSnapshot(data, input);
    }
  }

  private void consolidateCandidates(Data data, Set<Node> neighbors) {

    for (Node n : neighbors) {
      if (null == n) {
        continue;
      }
      data.addNode(n);
      
      Set<Node> children =  data.candidates(n);
      if(!children.isEmpty()){
        consolidateCandidates(data, children);
      }
    }
  }

  public boolean hasMoreElements() {
    return !remaining.isEmpty();
  }
}
