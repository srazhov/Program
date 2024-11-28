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
    private final ISkipList skipList;

    public SkipListPQ(double alpha) {
        this.skipList = new SkipList(alpha, 0, new LevelGenerator());
    }

    public int size() {
	    return skipList.getWidth();
    }

    public MyEntry min() {
	    var minValPointer = skipList.getStart().getBelow();
        if (minValPointer == null) {
            return null;
        }

        var minVal = minValPointer.getRight().getKey();
        minValPointer = skipList.skipSearch(minVal);
        var result = "";
        while (minValPointer.getKey() == minVal) {
            result = minValPointer.getValue() + " " + result;
            minValPointer = minValPointer.getLeft();
        }

        return new MyEntry(minVal,result);
    }

    public int insert(int key, String value) {
        skipList.skipInsert(key, value);
        return 0;
    }

    public MyEntry removeMin() {
        var min = min();
        if (min == null) {
            return null;
        }

        var removed = skipList.skipRemove(min.getKey());
        return new MyEntry(removed.getKey(),removed.getValue());
    }

    public void print() {
        var horizontalTemp = skipList.getStart();
        while (horizontalTemp.getAbove() != null) {
            horizontalTemp = horizontalTemp.getAbove();
        }

        var result = new String[skipList.getWidth()];
        var verticalTemp = horizontalTemp;
        for (int i = 0; i < skipList.getWidth(); i++) {
            var height = 0;
            while (verticalTemp.getAbove() != null) {
                height++;
                verticalTemp = verticalTemp.getAbove();
            }

            result[i] = String.format("%d %s %d", verticalTemp.getKey(), verticalTemp.getValue(), height);
        }

        System.out.println(String.join(",", result));
    }
}

//TestProgram

public class TestProgram {
    public static void main(String[] args) {
        ////// MIRAS SRAZHOV TEST
        
        // ShowInfoForPQ();

        //ShowInfoForSkipList();

        if (true) {
            //return;
        }

        ////// MIRAS SRAZHOV TEST
        args = new String[] { "alphaEfficiencyTest/alphaEfficiencyTest_10K_2.txt" };
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
                        var min = skipList.min();
                        //System.out.println("minVal: " + min);
                        break;
                    case 1:
                        min = skipList.removeMin();
                        //System.out.println("removed minVal: " + min.getKey().toString());
                        break;
                    case 2:
                        var k = Integer.parseInt(line[1]);
                        var v = line[2];
                        skipList.insert(k, v);
                        break;
                    case 3:
                        skipList.print();
                        break;
                    default:
                        System.out.println("Invalid operation code");
                        return;
                }
            }

            System.out.println("Program is finished");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static void ShowInfoForPQ() {
        var pq = new SkipListPQ(0.5);
        
        var elements = GetRandomizedList(10);
        for (var elem : elements) {
            pq.insert(elem, String.valueOf(elem));
        }
        
        pq.insert(pq.min().getKey(), "anotherVal");
    }

    public static void ShowInfoForSkipList(ISkipList sl) { 
        var results = new String[sl.getHeight()][sl.getWidth()];
        var temp = sl.getStart();
        while (temp.getBelow() != null) {
            temp = temp.getBelow();
        }

        var horizontalTemp = temp;
        for (String[] result : results) {
            var j = -1;
            while (horizontalTemp != null) {
                if (horizontalTemp.getBelow() == null){   
                    j++;
                }
                else {
                    for (int i = 0; i < results[0].length; i++) {
                        var intVal = Integer.parseInt(results[0][i].split("/")[0]); 
                        if (intVal == horizontalTemp.getKey()) {
                            j = i;
                            break;
                        }
                    }
                }

                result[j] = horizontalTemp.getKey() + "/" + String.valueOf(horizontalTemp.getValue());
                horizontalTemp = horizontalTemp.getRight();
            }

            temp = temp.getAbove();
            horizontalTemp = temp;
        }

        System.out.println();
        for (String[] result : results) {
            System.out.println();
            for (String i : result) {
                var res = i == null ? " —— " : (" " + i + " ");
                System.out.print(" " + res + " ");
            }
        }
    }

    private static int[] GetRandomizedList(int size) {
        var rand = new Random();
        var result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = rand.nextInt(50);
        }

        return result;
    }
}
