
public class Main
{
  public static void main(final String[] args){
    final Thread t = new Thread(new SolverRunner(1));
    t.run();
  }
}
