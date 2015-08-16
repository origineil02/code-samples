 
package stackexchange.codegolf.floodpaint;

 
public class Coordinate {
  public final int row;
  public final int column;
  
  public Coordinate(int row, int column){
    this.row = row;
    this.column = column;
  }

  @Override
  public String toString() {
    return "Coordinate{" +  row + ", " + column + '}';
  }

  
  @Override
  public int hashCode() {
    int hash = 3;
    hash = 43 * hash + this.row;
    hash = 43 * hash + this.column;
    return hash;
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final Coordinate other = (Coordinate) obj;
    if (this.row != other.row) {
      return false;
    }
    if (this.column != other.column) {
      return false;
    }
    return true;
  }
  
  
}
