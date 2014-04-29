package stackexchange.codegolf.floodpaint;

import java.util.Scanner;

public class FloodPaintBoardGenerater implements Runnable {

  public void run() {
     
    final Initializer init = new Initializer();
    final Scanner scanner = new Scanner(init.stuff);
    
    final Board b = new Board(init.size);
    
    int line =0;
    
    while(scanner.hasNextLine()){
      final String row = scanner.nextLine();
      System.out.println(row);
      for(int column=0; column < row.length(); ++column){
        final Coordinate c = new Coordinate(line, column);
        final int value = Integer.parseInt(  String.valueOf( row.charAt(column) ) );
        
        final Node n = new Node(c, value);
        b.addNode(n);
      }
      line++;
    }
    
    System.out.println(b);
    
    b.solve();
    
  }
  
  public class Initializer{
    final int size = 5;
    final String stuff = "12345\n65432\n12345\n65432\n12345";
  }
          
}
