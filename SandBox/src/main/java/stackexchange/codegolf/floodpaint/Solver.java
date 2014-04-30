package stackexchange.codegolf.floodpaint;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solver {

  private final Set<Node> remaining;
  private Set<Node> targets;
  private final Set<Node> paintable;
  private final List<Integer> input;

  public Solver(final Board b) {
    this.remaining = new HashSet<Node>(b.getBoardState().values());
    this.paintable = new HashSet<Node>();
    this.paintable.add(b.getCenterNode());
    this.remaining.remove(b.getCenterNode());
    this.input = new ArrayList<Integer>();
    this.targets = b.getCenterNode().getNeighbors();
  }

  public List<Integer> solve() {
    while (hasMoreElements()) {
      flood();
    }
    return input;
  }

  public void flood() {

    final Data data = new Data();

    consolidateCandidates(data, targets);

    input.add(data.getTarget());
    paintable.addAll(data.targets());
    remaining.removeAll(data.targets());
    targets = data.potentialTargets(paintable);
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
