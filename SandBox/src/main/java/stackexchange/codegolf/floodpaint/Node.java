 package stackexchange.codegolf.floodpaint;

import java.util.HashSet;
import java.util.Set;

 
public class Node {
  private Node north;
  private Node south;
  private Node east;
  private Node west;
  
  private Coordinate c;
  private int value;
  
  public Node(final Coordinate c, int v){
    this.c = c;
    this.value = v;
  }
  
  public Coordinate getCoordinate(){
    return c;
  }
  public Integer getValue(){
    return this.value;
  }
  
  public void paint(Integer v){
    this.value = v;
  }
  
  public String toString(){
    return String.format("{ %d,%d : %d }", c.row, c.column, value);
  }

  void assignNeighbor(Node x, Direction direction) {
    switch(direction){
      case N: this.north = x;break;
      case E: this.south = x;break;
      case W: this.west = x;break;
      case S: this.east = x;break;
    }
  }
  public Node getNeighbor(Direction direction) {
    switch(direction){
      case N: return this.north;
      case E: return this.south;
      case W: return this.west;
      case S: return this.east;
    }
    return null;
  }
  
  public String neighbors(){
    final StringBuilder sb = new StringBuilder();
    
    sb.append(this).append(" | ");
    
    for(Direction d : Direction.values()){
      sb.append(" { "+ d +" : " + getNeighbor(d) + " } ");
    }
    return sb.toString();
  }
  
  public Set<Node> getNodes(){
    final Set<Node> nodes = new HashSet<Node>();
    
    for(Direction d : Direction.values()){
      nodes.add(getNeighbor(d));
    }
    return nodes;
  }
  
  public Set<Node> getLikeValuedNeighbors(Direction d){
    return neighbors(true, d);
  }
  
  public Set<Node> getNeighbors(Direction d){
    return neighbors(false, d);
  }
  
  public Set<Node> getNeighbors(){
    return neighbors(false, null);
  }
  
  private Set<Node> neighbors(boolean likeValued, Direction excluding){
     final Set<Node> nodes = new HashSet<Node>();
    
    for(Direction d : Direction.values()){
      
      if(null != excluding && d.equals(excluding)){
        continue;
      }
      
      final Node neighbor = getNeighbor(d);
      if(null != neighbor && (!likeValued || likeValued && null!=neighbor && neighbor.value == this.value))
      { 
        nodes.add(neighbor);
      }
    }
    return nodes;
  }
  public Set<Node> getLikeValuedNodes(){
    return neighbors(true, null);
  }
}
