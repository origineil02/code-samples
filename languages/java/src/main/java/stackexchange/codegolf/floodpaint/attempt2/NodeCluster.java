package stackexchange.codegolf.floodpaint.attempt2;

import java.util.Set;
import stackexchange.codegolf.floodpaint.Node;

public class NodeCluster {
  
  private Set<Node> nodes;
  public NodeCluster(Set<Node> nodes){
    this.nodes = nodes;
  }
  
  public boolean isEmpty(){
    return nodes.isEmpty();
  }
  
  public Set<Node> getNodes(){
    return nodes;
  }
}
