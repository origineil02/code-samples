package stackexchange.codegolf.floodpaint;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Data {
  final Map<Node, Context> map2 =  new HashMap<Node, Context>();
    final Map<Integer, Set<Node>> map = new HashMap<Integer, Set<Node>>();
    private int largest = 1; 
    
    public void addNode(Node n, Node parent){
     
     if(!map.containsKey(n.getValue())){
       map.put(n.getValue(), new HashSet<Node>());
     }             
          
     map.get(n.getValue()).add(n);
     
     if(null == map.get(largest) || map.get(largest).size() < map.get(n.getValue()).size()){
       largest = n.getValue();
     }
     
     map2.put(n, new Context(parent, n));
    }
    
    public Integer getTarget(){
      return largest;
    }
    
    public Set<Node> targets(){
      return map.get(largest);
    }
    
    public Set<Node> potentialTargets(){
      final Set<Node> leaves = new HashSet<Node>();
      traverse(targets(), leaves);
      
      return leaves;
    }
    
    private void traverse(Set<Node> nodes, Set<Node> leaves){
      for(Node n :nodes){
         
         if(map2.containsKey(n)){
           Context context = map2.get(n);
           
           
           Set<Node> children = context.applicableChildren();
         
           if(children.isEmpty()){
             leaves.addAll(context.getChildren());
           }
           else
           {
             traverse(children, leaves);
           }
         }
      }
    }
}
