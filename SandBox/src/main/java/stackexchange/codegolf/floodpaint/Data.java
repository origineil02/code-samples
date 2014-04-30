package stackexchange.codegolf.floodpaint;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Data {

  final Map<Integer, Set<Node>> map = new HashMap<Integer, Set<Node>>();
  private int largest = 1;
   
  public boolean containsAll(Integer target, Set<Node> children){
    return map.containsKey(target) && map.get(target).containsAll(children);
  }
  
  public Set<Node> candidates(Node n){
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

  public Integer getTarget() {
    return largest;
  }

  public Set<Node> targets() {
    return  map.get(largest);
  }

  public Set<Node> potentialTargets(Set<Node> painted) {
    final Set<Node> leaves = new HashSet<Node>();

    final Set<Node> theRest = new HashSet();
    for (Set<Node> group : map.values()) {
      theRest.addAll(group);
    }
    theRest.removeAll(targets());
    leaves.addAll(theRest);
    
    traverse(targets(), leaves, painted);
    
    return leaves;
  }

  private void traverse(Set<Node> nodes, Set<Node> leaves, Set<Node> painted) {
    for (Node n : nodes) {

      Set<Node> children = n.getLikeValuedNeighbors();
      children.removeAll(painted);
      
      if (!children.isEmpty()) {
          traverse(children, leaves, painted);
      } else {
        Set<Node> set = n.getNeighbors();
        set.removeAll(painted);
        leaves.addAll(set);
      }
    }
  }
}
