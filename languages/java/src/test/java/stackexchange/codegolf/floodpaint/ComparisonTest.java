 
package stackexchange.codegolf.floodpaint;
  
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import junit.framework.TestCase;

public class ComparisonTest extends TestCase{

    private static final ForkJoinPool FORK_JOIN_POOL = new ForkJoinPool();

    public void testFiles(){
       final ResourceBundle winner = ResourceBundle.getBundle("stackexchange.codegolf.floodpaint.SolutionsWinner");
    final ResourceBundle mine = ResourceBundle.getBundle("stackexchange.codegolf.floodpaint.SolutionsBundle");
     
    for(int i =1; i < 2115; ++i){
      final String key = "Puzzle"+i;
      final String w = winner.getString(key);
      final String m = mine.getString(key);
      
      if(!w.equals(m)){
        System.out.println(i);
        System.out.println(w + "\n"+ m);
        System.out.println("----");
        //fail();
      }
    }
  }
    
    public static void blah() throws IOException, InterruptedException, ExecutionException {
        try (BufferedReader reader = new BufferedReader(new FileReader("floodtest"))) {
          
        final FileWriter writer = new FileWriter(new File("winner.txt"));
        
            int sum = 0;
            State initState = readNextInitState(reader);
            int i =1;
            while (initState != null) {
                List<Integer> solution = generateSolution(initState);
                //System.out.println("solution);
                writer.write("Puzzle"+i+++"="+solution+"\n");
                sum += solution.size();
                initState = readNextInitState(reader);
            }
            writer.flush();
            System.out.println(sum);
            writer.close();
        }
    }

    private static State readNextInitState(BufferedReader reader) throws IOException {
        int[] initGrid = new int[State.DIM * State.DIM];
        String line = reader.readLine();
        while ((line != null) && line.isEmpty()) {
            line = reader.readLine();
        }
        if (line == null) {
            return null;
        }
        for (int rowNo = 0; rowNo < State.DIM; ++rowNo) {
            for (int colNo = 0; colNo < State.DIM; ++colNo) {
                initGrid[(State.DIM * rowNo) + colNo] = line.charAt(colNo) - '0';
            }
            line = reader.readLine();
        }
        return new State(initGrid);
    }

    private static List<Integer> generateSolution(State initState) throws InterruptedException, ExecutionException {
        List<Integer> solution = new LinkedList<>();
        StateFactory stateFactory = new StateFactory();
        State state = initState;
        while (!state.isSolved()) {
            int num = findGoodNum(state, stateFactory);
            solution.add(num);
            state = state.getNextState(num, stateFactory);
        }
        return solution;
    }

    private static int findGoodNum(State state, StateFactory stateFactory) throws InterruptedException, ExecutionException {
        SolverTask task = new SolverTask(state, stateFactory);
        FORK_JOIN_POOL.invoke(task);
        return task.get();
    }

}

class SolverTask extends RecursiveTask<Integer> {

    private static final int DEPTH = 5;

    private final State state;
    private final StateFactory stateFactory;

    SolverTask(State state, StateFactory stateFactory) {
        this.state = state;
        this.stateFactory = stateFactory;
    }

    @Override
    protected Integer compute() {
        try {
            Map<Integer,AnalyzerTask> tasks = new HashMap<>();
            for (int num = 1; num <= 6; ++num) {
                if (num != state.getCenterNum()) {
                    State nextState = state.getNextState(num, stateFactory);
                    AnalyzerTask task = new AnalyzerTask(nextState, DEPTH - 1, stateFactory);
                    tasks.put(num, task);
                }
            }
            invokeAll(tasks.values());
            int bestValue = Integer.MAX_VALUE;
            int bestNum = -1;
            for (Map.Entry<Integer,AnalyzerTask> taskEntry : tasks.entrySet()) {
                int value = taskEntry.getValue().get();
                if (value < bestValue) {
                    bestValue = value;
                    bestNum = taskEntry.getKey();
                }
            }
            return bestNum;
        } catch (InterruptedException | ExecutionException ex) {
            throw new RuntimeException(ex);
        }
    }

}

class AnalyzerTask extends RecursiveTask<Integer> {

    private static final int DEPTH_THRESHOLD = 3;

    private final State state;
    private final int depth;
    private final StateFactory stateFactory;

    AnalyzerTask(State state, int depth, StateFactory stateFactory) {
        this.state = state;
        this.depth = depth;
        this.stateFactory = stateFactory;
    }

    @Override
    protected Integer compute() {
        return (depth < DEPTH_THRESHOLD) ? analyze() : split();
    }

    private int analyze() {
        return analyze(state, depth);
    }

    private int analyze(State state, int depth) {
        if (state.isSolved()) {
            return -depth;
        }
        if (depth == 0) {
            return state.getNumIslands();
        }
        int bestValue = Integer.MAX_VALUE;
        for (int num = 1; num <= 6; ++num) {
            if (num != state.getCenterNum()) {
                State nextState = state.getNextState(num, stateFactory);
                int nextValue = analyze(nextState, depth - 1);
                bestValue = Math.min(bestValue, nextValue);
            }
        }
        return bestValue;
    }

    private int split() {
        try {
            if (state.isSolved()) {
                return -depth;
            }
            Collection<AnalyzerTask> tasks = new ArrayList<>(5);
            for (int num = 1; num <= 6; ++num) {
                State nextState = state.getNextState(num, stateFactory);
                AnalyzerTask task = new AnalyzerTask(nextState, depth - 1, stateFactory);
                tasks.add(task);
            }
            invokeAll(tasks);
            int bestValue = Integer.MAX_VALUE;
            for (AnalyzerTask task : tasks) {
                int nextValue = task.get();
                bestValue = Math.min(bestValue, nextValue);
            }
            return bestValue;
        } catch (InterruptedException | ExecutionException ex) {
            throw new RuntimeException(ex);
        }
    }

}

class StateFactory {

    private static final int INIT_CAPACITY = 40000;
    private static final float LOAD_FACTOR = 0.9f;

    private final ReadWriteLock cacheLock = new ReentrantReadWriteLock();
    private final Map<List<Integer>,State> cache = new HashMap<>(INIT_CAPACITY, LOAD_FACTOR);

    State get(int[] grid) {
        List<Integer> stateKey = new IntList(grid);
        State state;
        cacheLock.readLock().lock();
        try {
            state = cache.get(stateKey);
        } finally {
            cacheLock.readLock().unlock();
        }
        if (state == null) {
            cacheLock.writeLock().lock();
            try {
                state = cache.get(stateKey);
                if (state == null) {
                    state = new State(grid);
                    cache.put(stateKey, state);
                }
            } finally {
                cacheLock.writeLock().unlock();
            }
        }
        return state;
    }

}

class State {

    static final int DIM = 19;
    private static final int CENTER_INDEX = ((DIM * DIM) - 1) / 2;

    private final int[] grid;
    private int numIslands;

    State(int[] grid) {
        this.grid = grid;
        numIslands = calcNumIslands(grid);
    }

    private static int calcNumIslands(int[] grid) {
        int numIslands = 0;
        BitSet uncounted = new BitSet(DIM * DIM);
        uncounted.set(0, DIM * DIM);
        int index = -1;
        while (!uncounted.isEmpty()) {
            index = uncounted.nextSetBit(index + 1);
            BitSet island = new BitSet(DIM * DIM);
            generateIsland(grid, index, grid[index], island);
            ++numIslands;
            uncounted.andNot(island);
        }
        return numIslands;
    }

    private static void generateIsland(int[] grid, int index, int num, BitSet island) {
        if ((grid[index] == num) && !island.get(index)) {
            island.set(index);
            if ((index % DIM) > 0) {
                generateIsland(grid, index - 1, num, island);
            }
            if ((index % DIM) < (DIM - 1)) {
                generateIsland(grid, index + 1, num, island);
            }
            if ((index / DIM) > 0) {
                generateIsland(grid, index - DIM, num, island);
            }
            if ((index / DIM) < (DIM - 1)) {
                generateIsland(grid, index + DIM, num, island);
            }
        }
    }

    int getCenterNum() {
        return grid[CENTER_INDEX];
    }

    boolean isSolved() {
        return numIslands == 1;
    }

    int getNumIslands() {
        return numIslands;
    }

    State getNextState(int num, StateFactory stateFactory) {
        int[] nextGrid = grid.clone();
        if (num != getCenterNum()) {
            flood(nextGrid, CENTER_INDEX, getCenterNum(), num);
        }
        State nextState = stateFactory.get(nextGrid);
        return nextState;
    }

    private static void flood(int[] grid, int index, int fromNum, int toNum) {
        if (grid[index] == fromNum) {
            grid[index] = toNum;
            if ((index % 19) > 0) {
                flood(grid, index - 1, fromNum, toNum);
            }
            if ((index % 19) < (DIM - 1)) {
                flood(grid, index + 1, fromNum, toNum);
            }
            if ((index / 19) > 0) {
                flood(grid, index - DIM, fromNum, toNum);
            }
            if ((index / 19) < (DIM - 1)) {
                flood(grid, index + DIM, fromNum, toNum);
            }
        }
    }

}

class IntList extends AbstractList<Integer> implements List<Integer> {

    private final int[] arr;
    private int hashCode = -1;

    IntList(int[] arr) {
        this.arr = arr;
    }

    @Override
    public int size() {
        return arr.length;
    }

    @Override
    public Integer get(int index) {
        return arr[index];
    }

    @Override
    public Integer set(int index, Integer value) {
        int oldValue = arr[index];
        arr[index] = value;
        return oldValue;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof IntList) {
            IntList arg = (IntList) obj;
            return Arrays.equals(arr, arg.arr);
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        if (hashCode == -1) {
            hashCode = 1;
            for (int elem : arr) {
                hashCode = 31 * hashCode + elem;
            }
        }
        return hashCode;
    }

}



