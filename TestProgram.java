import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

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
        this.skipList = new SkipList(alpha, new LevelGenerator());
    }

    public int size() {
	    return skipList.getWidth() - 2;
    }

    public MyEntry min() {
        var minVal = getMinKey();
        if (minVal == null) {
            return null;
        }

        var minValPointer = skipList.skipSearch(minVal);
        var result = "";
        while (minValPointer.getKey() != null && minValPointer.getKey().intValue() == minVal) {
            result = minValPointer.getValue() + " " + result;
            minValPointer = minValPointer.getLeft();
        }

        return new MyEntry(minVal, result);
    }

    public int insert(int key, String value) {
        return skipList.skipInsert(key, value);
    }

    public MyEntry removeMin() {
        var min = getMinKey();
        if (min == null) {
            return null;
        }

        var removed = skipList.skipRemove(min);
        return new MyEntry(removed.getKey(), removed.getValue());
    }

    public void print() {
        var horizontalTemp = skipList.getStart();
        while (horizontalTemp.getBelow() != null) {
            horizontalTemp = horizontalTemp.getBelow();
        }

        horizontalTemp = horizontalTemp.getRight();
        var result = new String[skipList.getWidth() - 2];
        for (int i = 0; i < skipList.getWidth() - 2; i++) {
            var verticalTemp = horizontalTemp;
            var height = 1;
            while (verticalTemp.getAbove() != null) {
                height++;
                verticalTemp = verticalTemp.getAbove();
            }

            result[i] = String.format("%d %s %d", verticalTemp.getKey(), verticalTemp.getValue(), height);
            horizontalTemp = horizontalTemp.getRight();
        }

        System.out.println(String.join(", ", result));
    }

    private Integer getMinKey() {
        var minValPointer = skipList.getStart();
        while (minValPointer.getBelow() != null) {
            minValPointer = minValPointer.getBelow();
        }

        return minValPointer.getRight().getKey();
    }
}

//TestProgram
public class TestProgram {
    public static void main(String[] args) {
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

            var insertsCount = 0;
            long sumOfNodesTraversed = 0;
            for (int i = 0; i < N; i++) {
                String[] line = br.readLine().split(" ");
                int operation = Integer.parseInt(line[0]);

                switch (operation) {
                    case 0 -> {
                        var min = skipList.min();
                        System.out.println(min);
                    }
                    case 1 -> {
                        var min = skipList.removeMin();
                        System.out.println(min.toString());
                    }
                    case 2 -> {
                        var k = Integer.parseInt(line[1]);
                        var v = line[2];
                        
                        // Throws an exception in case of overflow 
                        var nodesTraversed = skipList.insert(k, v);
                        sumOfNodesTraversed = Math.addExact(sumOfNodesTraversed, nodesTraversed);
                        insertsCount++;
                    }
                    case 3 -> skipList.print();
                    default -> {
                        System.out.println("Invalid operation code");
                        return;
                    }
                }
            }

            var average = Double.toString((double)(sumOfNodesTraversed/insertsCount));
            System.out.println(String.format("%,.2f %d %d %s", alpha, skipList.size(), insertsCount, average));
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
