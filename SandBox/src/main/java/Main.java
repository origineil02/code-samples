
import com.lsa036.datageneration.DataGenerator;

public class Main
{
  public static void main(final String[] args){
    final Thread t = new Thread(new DataGenerator());
    t.run();
  }
}
