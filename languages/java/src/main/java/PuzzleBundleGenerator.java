
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;


public class PuzzleBundleGenerator implements Runnable {

  public void run() {

    try {

      File f = new File("PuzzlesBundle.properties");
      final FileWriter writer = new FileWriter(f);


      final Scanner scanner = new Scanner(new File("floodtest"));

      StringBuilder sb = new StringBuilder();
      int puzzle = 1;

      int lineNumber = 0;
      while (scanner.hasNextLine()) {

        final String line = scanner.nextLine();
        //System.out.println(line);
        if (line.trim().isEmpty()) {
          lineNumber = 0;
          //System.out.println(sb.toString());
          writer.append("\nPuzzle" + puzzle + " = \"" + sb.toString() + "\"");
          writer.append("");
          sb = new StringBuilder();
          puzzle++;
        }
        else {

          sb.append(line).append(lineNumber++ < 18 ? "\\n" : "");
        }
      }

      writer.flush();
      writer.close();
      System.out.println(puzzle);
    }
    catch (Exception ex) {
    }

  }
}
