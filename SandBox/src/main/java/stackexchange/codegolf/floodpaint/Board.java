package stackexchange.codegolf.floodpaint;

import java.util.HashMap;
import java.util.Map;

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
  
  public Node getCenterNode(){
    int ptr = tileCount / 2;
    return getNode(new Coordinate(ptr, ptr));
  }

  public Map<Coordinate, Node> getBoardState() {
    return boardState;
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
