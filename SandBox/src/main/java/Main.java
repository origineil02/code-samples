
import com.lsa036.datageneration.DataGenerator;
import stackexchange.codegolf.floodpaint.FloodPaintBoardGenerater;

public class Main
{
  public static void main(final String[] args){
    final Thread t = new Thread(new FloodPaintBoardGenerater());
    t.run();
  }
}
