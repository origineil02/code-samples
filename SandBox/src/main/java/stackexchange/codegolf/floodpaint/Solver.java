package stackexchange.codegolf.floodpaint;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solver {

  private final Set<Node> remaining;
  private final Node center;
  private Set<Node> targets;
  private final Set<Node> paintable;
  private final Board b;
  private final List<Integer> input;

  public Solver(final Board b) {
    this.remaining = new HashSet<Node>(b.getBoardState().values());
    this.center = b.getCenterNode();
    this.paintable = new HashSet<Node>();
    this.b = b;
    this.input = new ArrayList<Integer>();
  }

  public String solve() {
    while (hasMoreElements()) {
      flood();
    }
    return input.toString();
  }

  public void flood() {

    final Data data = new Data();

    if (remaining.contains(center)) {
      targets = center.getNeighbors();
      remaining.remove(center);
      paintable.add(center);
    }

    consolidateCandidates(data, targets);

    input.add(data.getTarget());
    paintable.addAll(data.targets());
    remaining.removeAll(data.targets());
    data.setPainted(paintable);
    targets = data.potentialTargets();
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
//      return false;
    return !remaining.isEmpty();
  }
}
