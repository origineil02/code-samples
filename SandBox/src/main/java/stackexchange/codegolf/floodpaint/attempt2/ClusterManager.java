package stackexchange.codegolf.floodpaint.attempt2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import stackexchange.codegolf.floodpaint.Node;

public class ClusterManager {
  private Map<Integer, List<NodeCluster>> clustersByValue;
  private Map<Node, NodeCluster> nodeToCluster;
  
  public ClusterManager(Set<Node> nodes){
    final Set<Node> clustered = new HashSet<Node>();
    nodeToCluster = new  HashMap<Node, NodeCluster>();
    for(Node n : nodes){
      
      if(!clustered.contains(n)){
      if(!clustersByValue.containsKey(n.getValue())){
        clustersByValue.put(n.getValue(), new ArrayList<NodeCluster>());
      }
      
        final NodeCluster cluster = new NodeCluster(n.getAllLikeValuedNeighbors());
        
        if(!cluster.isEmpty()){
          nodeToCluster.put(n, cluster);
          clustersByValue.get(n.getValue()).add(cluster);
          clustered.addAll(cluster.getNodes());
        }
      }
      
      
    }
  }
}
