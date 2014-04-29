package stackexchange.codegolf.floodpaint;

import java.util.Scanner;

public class FloodPaintBoardGenerater implements Runnable {

  public void run() {

    final Initializer init = new Initializer();
    final Scanner scanner = new Scanner(init.stuff.replaceAll(" ", ""));

    final Board b = new Board(init.size);

    int line = 0;

    while (scanner.hasNextLine()) {
      final String row = scanner.nextLine();
      System.out.println(row);
      for (int column = 0; column < row.length(); ++column) {
        final Coordinate c = new Coordinate(line, column);
        final int value = Integer.parseInt(String.valueOf(row.charAt(column)));

        final Node n = new Node(c, value);
        b.addNode(n);
      }
      line++;
    }

    System.out.println(b);

    final Solver solver = new Solver(b);
    System.out.println(solver.solve());
  }

  public class Initializer {

    final int size = 19;
//    final String stuff = "12345\n65432\n12345\n65432\n12345";
//    final String stuff = x7();
            
            final String stuff
            = "4 5 1 1 2 2 1 6 2 6 3 4 2 3 2 3 1 6 3\n"
            + "4 2 6 3 4 4 5 6 4 4 5 3 3 3 3 5 4 3 4\n"
            + "2 3 5 2 2 5 5 1 2 6 2 6 6 2 1 6 6 1 2\n"
            + "4 6 5 5 5 5 4 1 6 6 3 2 6 4 2 6 3 6 6\n"
            + "1 6 4 4 4 4 6 4 2 5 5 3 2 2 4 1 5 2 5\n"
            + "1 6 2 1 5 1 6 4 4 1 5 1 3 4 5 2 3 4 1\n"
            + "3 3 5 3 2 2 2 4 2 1 6 6 6 6 1 4 5 2 5\n"
            + "1 6 1 3 2 4 1 3 3 4 6 5 1 5 5 3 4 3 3\n"
            + "4 4 1 5 5 1 4 6 3 3 4 5 5 6 1 6 2 6 4\n"
            + "1 4 2 5 6 5 5 3 2 5 5 5 3 6 1 4 4 6 6\n"
            + "4 6 6 2 6 6 2 4 2 6 1 5 6 2 3 3 4 3 6\n"
            + "6 1 3 6 3 5 5 3 6 1 3 4 4 5 1 2 6 4 3\n"
            + "2 6 1 3 2 4 2 6 1 1 5 2 6 6 6 6 3 3 3\n"
            + "3 4 5 4 6 6 3 3 4 1 1 6 4 5 1 3 4 1 2\n"
            + "4 2 6 4 1 5 3 6 4 3 4 5 4 2 1 1 4 1 1\n"
            + "4 2 4 1 5 2 2 3 6 6 6 5 2 5 4 5 4 5 1\n"
            + "5 6 2 3 4 6 5 4 1 3 2 3 2 1 3 6 2 2 4\n"
            + "6 5 4 1 3 2 2 1 1 1 6 1 2 6 2 5 6 4 5\n"
            + "5 1 1 4 2 6 2 5 6 1 3 3 4 1 6 1 2 1 2";
  }

  public String x7() {
    return    "4 5 1 1 2 2 1 \n"
            + "4 2 6 3 4 4 5 \n"
            + "2 3 5 2 2 5 5 \n"
            + "4 6 5 5 5 5 4 \n"
            + "1 6 4 4 4 4 6 \n"
            + "1 6 2 1 5 1 6 \n"
            + "3 3 5 3 2 2 2 \n";
  }
}
