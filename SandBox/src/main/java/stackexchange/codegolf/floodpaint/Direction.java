package stackexchange.codegolf.floodpaint;
 
public enum Direction {
    
    N,
    S,
    E,
    W;
    
    public Coordinate getCoordinate(final Node n){
      final Coordinate c = n.getCoordinate();
      switch(this){
        case N: 
          return new Coordinate(c.row-1, c.column);
        case S: 
          return new Coordinate(c.row+1, c.column);
        case E: 
          return new Coordinate(c.row, c.column-1);
        case W: 
          return new Coordinate(c.row, c.column+1);
          
      }
      return null;
    }
    
     public static Direction inverse(final Direction d){
      switch(d){
        case N: 
          return Direction.S;
        case S: 
          return Direction.N;
        case E: 
          return Direction.W;
        case W: 
          return Direction.E;
          
      }
      return null;
    }
     
}
