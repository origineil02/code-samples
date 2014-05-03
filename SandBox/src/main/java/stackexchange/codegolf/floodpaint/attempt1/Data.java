package stackexchange.codegolf.floodpaint.attempt1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import stackexchange.codegolf.floodpaint.Node;

public class Data {

  final Map<Integer, Set<Node>> map = new HashMap<Integer, Set<Node>>();
  private int largest = 1;
  private Set<Node> remaining;
  private Set<Node> paintable;
  private List<Integer> input;

  public List<Integer> getInput() {
    return input;
  }

  public void setInput(List<Integer> input) {
    this.input = new ArrayList<Integer>(input);
  }
  
  public Set<Node> getRemaining() {
    return remaining;
  }

  public void setRemaining(Set<Node> remaining) {
    this.remaining = new HashSet<Node>(remaining);
  }

  public Set<Node> getPaintable() {
    return paintable;
  }

  public void setPaintable(Set<Node> paintable) {
    this.paintable = new HashSet<Node>(paintable);
  }

  public Set<Node> candidates(Node n) {
    final Set<Node> children = n.getLikeValuedNeighbors();
    children.removeAll(map.get(n.getValue()));
    return children;
  }

  public void addNode(Node n) {

    if (!map.containsKey(n.getValue())) {
      map.put(n.getValue(), new HashSet<Node>());
    }

    map.get(n.getValue()).add(n);

    if (null == map.get(largest) || map.get(largest).size() < map.get(n.getValue()).size()) {
      largest = n.getValue();
    }
  }

  public Map<Integer, Set<Node>> byTheNumbers() {
    return map;
  }

  public Integer getTarget() {
    return largest;
  }

  public Set<Node> targets() {
    return null == map.get(largest) ? Collections.EMPTY_SET : map.get(largest);
  }

  public Set<Node> potentialTargets(Set<Node> targets, Set<Node> painted) {
    final Set<Node> leaves = new HashSet<Node>();

    final Set<Node> theRest = new HashSet();
    for (Set<Node> group : map.values()) {
      theRest.addAll(group);
    }
    theRest.removeAll(targets);
    leaves.addAll(theRest);

    traverse(targets, leaves, painted, new HashSet<Node>());

    return leaves;
  }

  private void traverse(Set<Node> nodes, Set<Node> leaves, Set<Node> painted, Set<Node> alreadyChecked) {
    for (Node n : nodes) {

      Set<Node> children = n.getLikeValuedNeighbors();
      children.removeAll(painted);

      if (!children.isEmpty()) {
        if (!alreadyChecked.contains(n)) {
          alreadyChecked.add(n);
          traverse(children, leaves, painted, alreadyChecked);
        }
      }
      else {
        Set<Node> set = n.getNeighbors();
        set.removeAll(painted);
        leaves.addAll(set);
      }
    }
  }

//  @Override
//  public int hashCode() {
//    int hash = 3;
//    hash = 41 * hash + (this.remaining != null ? this.remaining.hashCode() : 0);
//    return hash;
//  }
//
//  @Override
//  public boolean equals(Object obj) {
//    if (obj == null) {
//      return false;
//    }
//    if (getClass() != obj.getClass()) {
//      return false;
//    }
//    final Data other = (Data) obj;
//    if (this.remaining != other.remaining && (this.remaining == null || !this.remaining.equals(other.remaining))) {
//      return false;
//    }
//    return true;
//  }


}
