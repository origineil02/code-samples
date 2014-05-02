package stackexchange.codegolf.floodpaint;

import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Stack;
import junit.framework.TestCase;

public class SolutionsVerification extends TestCase {

  public SolutionsVerification(String testName) {
    super(testName);
  }
  
  public void test(){
    final ResourceBundle puzzles = ResourceBundle.getBundle("stackexchange.codegolf.floodpaint.PuzzlesBundle");
    final ResourceBundle solutions = ResourceBundle.getBundle("stackexchange.codegolf.floodpaint.SolutionsBundle");
     
      for (int i = 1; i <= 5316; i++) {
        final String puzzle = puzzles.getString("Puzzle"+i);
        final String solution = solutions.getString("Puzzle"+i);
        char[] board = boardGen(puzzle.replaceAll("\"", ""));
        
        final String numbersOnly = solution.replace("[", "").replace("]", "").replaceAll(",", "").replaceAll(" ", "").trim();
        System.out.println(numbersOnly);
        process(numbersOnly, board);
        verify(board, i);
    }
  }
  
  private char[] boardGen(String str) {
    char[] board = new char[361];

    final Scanner b = new Scanner(str);
    while (b.hasNextLine()) {
      for (int l = 0; l < 19; l++) {
        String lineb = b.nextLine();

        System.arraycopy(lineb.toCharArray(), 0, board, l * 19, 19);
      }
    }
    return board;
  }

  private void process(String steps, char[] board) {
    Stack<Integer> nodes = new Stack<Integer>();
    for (char c : steps.toCharArray()) {
      char targetColor = board[180];
      char replacementColor = c;

      nodes.push(180);

      while (!nodes.empty()) {
        int n = nodes.pop();
        if (n < 0 || n > 360) {
          continue;
        }
        if (board[n] == targetColor) {
          board[n] = replacementColor;
          if (n % 19 > 0) {
            nodes.push(n - 1);
          }
          if (n % 19 < 18) {
            nodes.push(n + 1);
          }
          if (n / 19 > 0) {
            nodes.push(n - 19);
          }
          if (n / 19 < 18) {
            nodes.push(n + 19);
          }
        }
      }
    }
  }

  public void verify(char[] b, int number) {
    char center = b[180];
    for (char c : b) {
      //System.out.println(c);
      if (c != center) {
        fail("Failed Puzzle " + number);
      }
    }
  }
}
