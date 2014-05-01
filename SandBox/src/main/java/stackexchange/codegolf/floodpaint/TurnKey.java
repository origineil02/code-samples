 
package stackexchange.codegolf.floodpaint;

 
public class TurnKey {
  private Integer selectedValue;
  private Integer turn;

  public TurnKey( Integer turn, Integer selectedValue) {
    this.selectedValue = selectedValue;
    this.turn = turn;
  }
  
  public Integer getSelectedValue() {
    return selectedValue;
  }

  public Integer getTurn() {
    return turn;
  }

   

  @Override
  public int hashCode() {
    int hash = 7;
    hash = 41 * hash + (this.selectedValue != null ? this.selectedValue.hashCode() : 0);
    hash = 41 * hash + (this.turn != null ? this.turn.hashCode() : 0);
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
    final TurnKey other = (TurnKey) obj;
    if (this.selectedValue != other.selectedValue && (this.selectedValue == null || !this.selectedValue.equals(other.selectedValue))) {
      return false;
    }
    if (this.turn != other.turn && (this.turn == null || !this.turn.equals(other.turn))) {
      return false;
    }
    return true;
  }
}
