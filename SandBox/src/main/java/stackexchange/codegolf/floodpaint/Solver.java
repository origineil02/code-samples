package stackexchange.codegolf.floodpaint;

import java.util.HashSet;
import java.util.Set;

public class Solver {

  private final Set<Node> remaining;
  private final Node center;
  private Set<Node> targets;
  private final Set<Node> paintable;

  public Solver(Set<Node> remaining, final Node n) {
    this.remaining = remaining;
    this.center = n;
    this.paintable = new HashSet<Node>();
  }

  public void flood() {

//       final Map<Integer, List<Node>> map = new HashMap<Integer, List<Node>>();
    final Data data = new Data();

    if (remaining.contains(center)) {
      targets = center.getNodes();
      remaining.remove(center);
      paintable.add(center);
    }

    consolidateCandidates(data, targets);


    System.out.println(data.getTarget());

    for (Node n : paintable) {
      n.paint(data.getTarget());
    }

    paintable.addAll(data.targets());
    remaining.removeAll(data.targets());

    targets = data.potentialTargets();
  }

  private void consolidateCandidates(Data data, Set<Node> neighbors) {

    for (Node n : neighbors) {
      if (null == n) {
        continue;
      }
      //data.addNode(n);
      consolidateCandidates(data, n.getLikeValuedNodes());
    }
  }

  public boolean hasMoreElements() {
//      return false;
    return !remaining.isEmpty();
  }
}
