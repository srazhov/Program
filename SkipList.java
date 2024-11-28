import Interfaces.ILevelGenerator;
import Interfaces.IRandomListGenerator;
import Interfaces.ISkipList;

public class SkipList implements ISkipList {
    private LinkedListElement start;
    private int height;
    private final int width;
    private final double alpha;
    private ILevelGenerator levelGenerator;

    public SkipList(int size, IRandomListGenerator randomListGenerator, double alpha, int key, ILevelGenerator levelGenerator) {
        var elements = randomListGenerator.GetRandomizedSortedList(size);
        var levels = levelGenerator.GetRandomLevels(alpha, key, size);
        this.levelGenerator = levelGenerator;
        this.alpha = alpha;
        width = levels.length;

        start = new LinkedListElement(null, "-inf");

        generateFirstRow(elements);
        generateVertically(levels);
        connectVertically(levels);
        
        setTrueStart();
    }

    public LinkedListElement getStart() {
        return start;
    }

    public int getMaxLevel() {
        return height;
    }

    public int getWidth(){
        return width;
    }

    @Override
    public LinkedListElement skipSearch(int k) {
        var temp = start;
        while (temp.getBelow() != null) {
            temp = temp.getBelow();
            while (k >= temp.getRight().getKey()) {
                temp = temp.getRight();
            }
        }

        return temp;
    }

    @Override
    public LinkedListElement skipInsert(int k, String val) {
        var p = skipSearch(k);
        LinkedListElement q = null;
        
        var maxLvl = levelGenerator.GetRandomLevel(alpha, k);
        var iter = 0;
        var i = -1;
        do {
            i += 1;
            if (i >= height) {
                height += 1;
            }
        }
        while (iter > maxLvl);

        return null;
    }

    private void generateFirstRow(int[] elements) {   
        var temp = start;
        for (int i = 0; i < elements.length; i++) {
            temp.setRight(new LinkedListElement(elements[i], null));
            temp = temp.getRight();
        }

        temp.setRight(new LinkedListElement(null, "+inf"));
    }

    private void generateVertically(int[] levels) {
        var horizontalTemp = start.getRight();
        var max = levels[0];
        var i = 0;
        
        // Generate vertically the real values
        while (horizontalTemp.getKey() != null) {
            if (max < levels[i]) {
                max = levels[i];
            }
            
            var verticalTemp = horizontalTemp;
            for (int j = 0; j < levels[i]; j++) {
                verticalTemp.setAbove(new LinkedListElement(horizontalTemp.getKey(), horizontalTemp.getValue()));
                verticalTemp = verticalTemp.getAbove();
            }

            horizontalTemp = horizontalTemp.getRight();
            i++;
        }

        height = max + 2;
        // Generate vertically up the two sentinels
        var tempStart = start;
        for (int j = 0; j < height; j++) {
            tempStart.setAbove(new LinkedListElement(tempStart.getKey(), tempStart.getValue()));
            tempStart = tempStart.getAbove();

            horizontalTemp.setAbove(new LinkedListElement(horizontalTemp.getKey(), horizontalTemp.getValue()));
            horizontalTemp = horizontalTemp.getAbove();
        }
    }

    private void connectVertically(int[] levels) {
        var temp = start;
        var i = 0;
        while (temp.getRight() != null) {
            if (temp.getAbove() != null || temp.getRight().getKey() == null) {
                linkColumns(temp, temp.getRight(), levels, i);
            }

            temp = temp.getRight();
            i++;
        }
    }

    private void linkColumns(LinkedListElement curColumn, LinkedListElement interestedColumn, int[] levels, int startIndex) {
        var tempFirstRow = interestedColumn;
        var max = 0;
        // levels.length + 1 is used to determine the sentinel column
        for (int i = startIndex; i < levels.length + 1; i++) {
            if (i == levels.length || max < levels[i]){
                for(int j = 0; j < max; j++) {
                    interestedColumn = interestedColumn.getAbove();
                }
            }

            for (int j = max; (i == levels.length || j < levels[i]); j++) {
                interestedColumn = interestedColumn.getAbove();
                curColumn = curColumn.getAbove();
                if (curColumn == null) {
                    return;
                }
                
                curColumn.setRight(interestedColumn);
            }

            if (i < levels.length && max < levels[i]) {
                max = levels[i];
            }

            tempFirstRow = tempFirstRow.getRight();
            interestedColumn = tempFirstRow;
        }
    }

    private void setTrueStart() {
        while (start.getAbove() != null) {
            start = start.getAbove();
        }
    }
}