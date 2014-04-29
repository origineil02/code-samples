package stackexchange.codegolf.floodpaint;

import java.util.HashSet;
import java.util.Set;

public class Context {

  final Node from;
  final Node to;

  public Context(Node from, Node to) {
    this.from = from;
    this.to = to;
  }

  Set<Node> applicableChildren() {
    final Set<Node> set = new HashSet<Node>();

    Direction d = Direction.evaluateFromDirection(from, to);


    return to.getLikeValuedNeighbors(d);
  }

  Set<Node> getChildren() {
    final Set<Node> set = new HashSet<Node>();

    Direction d = Direction.evaluateFromDirection(from, to);


    return to.getNeighbors(d);
  }
}
