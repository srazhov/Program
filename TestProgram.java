import Interfaces.Implementations.LevelGenerator;
import Interfaces.Implementations.RandomListGenerator;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

//Class my entry
class MyEntry {
    private final Integer key;
    private final String value;
    public MyEntry(Integer key, String value) {
        this.key = key;
        this.value = value;
    }
    
    public Integer getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return key + " " + value;
    }
}

//Class SkipListPQ
class SkipListPQ {
    private final double alpha;
    private final Random rand;

    public SkipListPQ(double alpha) {
        this.alpha = alpha;
        this.rand = new Random();
    }

    public int size() {
	// TO BE COMPLETED  
        return 0;     
    }

    public MyEntry min() {
	// TO BE COMPLETED 
        return new MyEntry(0,"");
    }

    public int insert(int key, String value) {
	// TO BE COMPLETED 
        return 0;
    }

    private int generateEll(double alpha_ , int key) {
        int level = 0;
        if (alpha_ >= 0. && alpha_< 1) {
          while (rand.nextDouble() < alpha_) {
              level += 1;
          }
        }
        else {
          while (key != 0 && key % 2 == 0){
            key = key / 2;
            level += 1;
          }
        }
        return level;
    }

    public MyEntry removeMin() {
	// TO BE COMPLETED 
        return new MyEntry(0,"");
    }

    public void print() {
	// TO BE COMPLETED 
    }
}

//TestProgram

public class TestProgram {
    public static void main(String[] args) {
        ////// MIRAS SRAZHOV TEST
        
        var sl = new SkipList(10, new RandomListGenerator(), 0.5, 0, new LevelGenerator());
        
        var results = new Integer[sl.getMaxLevel()][sl.getWidth() + 2];
        var temp = sl.getStart();
        while (temp.getBelow() != null) {
            temp = temp.getBelow();
        }

        var horizontalTemp = temp;
        for (Integer[] result : results) {
            var j = -1;
            while (horizontalTemp != null) {
                if (horizontalTemp.getBelow() == null){   
                    j++;
                }
                else {
                    for (int i = 0; i < results[0].length; i++) {
                        if (results[0][i] == (horizontalTemp.getKey() == null ? 9999 : horizontalTemp.getKey())) {
                            j = i;
                            break;
                        }
                    }
                }

                result[j] = horizontalTemp.getKey() == null ? 9999 : horizontalTemp.getKey();
                horizontalTemp = horizontalTemp.getRight();
            }

            result[sl.getWidth() + 1] = 9999;
            temp = temp.getAbove();
            horizontalTemp = temp;
        }

        System.out.println();
        for (Integer[] result : results) {
            System.out.println();
            for (Integer i : result) {
                var res = i == null ? " - " : (" " + i.toString() + " ");
                System.out.print(" " + res + " ");
            }
        }
        
        if (true) {
            return;
        }

        ////// MIRAS SRAZHOV TEST
        
        if (args.length != 1) {
            System.out.println("Usage: java TestProgram <file_path>");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(args[0]))) {
            String[] firstLine = br.readLine().split(" ");
            int N = Integer.parseInt(firstLine[0]);
            double alpha = Double.parseDouble(firstLine[1]);
            System.out.println(N + " " + alpha);

            SkipListPQ skipList = new SkipListPQ(alpha);

            for (int i = 0; i < N; i++) {
                String[] line = br.readLine().split(" ");
                int operation = Integer.parseInt(line[0]);

                switch (operation) {
                    case 0:
			// TO BE COMPLETED 
                        break;
                    case 1:
			// TO BE COMPLETED 
                        break;
                    case 2:
			// TO BE COMPLETED 
                        break;
                    case 3:
			// TO BE COMPLETED 
                        break;
                    default:
                        System.out.println("Invalid operation code");
                        return;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
