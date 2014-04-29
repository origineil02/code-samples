 
package stackexchange.codegolf.floodpaint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

 
public class Board {
  private Map<Coordinate, Node> boardState;
  private int tileCount;
 
  public Board(int tileCount){
    this.tileCount = tileCount;
    this.boardState = new HashMap<Coordinate, Node>();
  }
  
  public void addNode(final Node n){
    boardState.put(n.getCoordinate(), n);
    for(Direction d : Direction.values())
    {
      assignNeighbor(n, d);
    }
  }
  
  private void assignNeighbor(Node n, Direction d) {
   
    Node x = getNode(d.getCoordinate(n));
    n.assignNeighbor(x, d);
    
    if(null != x)
    {
      x.assignNeighbor(n, Direction.inverse(d));
    }
  }
  
  public Node getNode(Coordinate n){
    return boardState.get(n);
  }
  
  public int solve(){
    final Set<Node> remaining = new HashSet<Node>(boardState.values());
    
    final int ptr = tileCount/2;
    final Coordinate center = new Coordinate(ptr, ptr);
    
    final Solver solver = new Solver(remaining, boardState.get(center));
    
    while(solver.hasMoreElements()){
      solver.flood();
      System.out.println(this);
    }
    return -1;
  }
  
   
  public String toString(){
    final StringBuilder sb = new StringBuilder();
    
    for(int x = 0; x < tileCount; ++x){
      for(int y = 0;  y < tileCount; ++y){
        final Coordinate key = new Coordinate(x, y);
        sb.append( 
        boardState.get(key).toString()
                );
        
        if( (y+1) % tileCount == 0){
          sb.append("\n");
        }
      }
    }
    
    return sb.toString();
  }
}
