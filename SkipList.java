public class SkipList implements ISkipList {
    private LinkedListElement start;
    private int height;
    private int width;

    private final double alpha;
    private final ILevelGenerator levelGenerator;

    public SkipList(double alpha, ILevelGenerator levelGenerator) {
        this.alpha = alpha;
        this.levelGenerator = levelGenerator;

        height = 1;
        width = 2;
        start = new LinkedListElement(null, "-inf");
        start.setRight(new LinkedListElement(null, "+inf"));
    }

    @Override
    public LinkedListElement getStart() {
        return start;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth(){
        return width;
    }

    @Override
    public LinkedListElement skipSearch(int k) {
        return skipSearchWithInfo(k).pointer;
    }

    private MyValues skipSearchWithInfo(int k) {
        var temp = start;
        var nodesTraversed = 1;
        while (temp.getBelow() != null) {
            temp = temp.getBelow();
            nodesTraversed++;

            while (temp.getRight().getKey() != null && k >= temp.getRight().getKey()) {
                nodesTraversed++;
                temp = temp.getRight();
            }
        }

        return new MyValues(temp, nodesTraversed);
    }

    @Override
    public int skipInsert(int k, String val) {
        var skipSearchInfo = skipSearchWithInfo(k);
        var p = skipSearchInfo.pointer;
        LinkedListElement q = null;
        
        var maxLvl = levelGenerator.getRandomLevel(alpha, k);
        var i = 0;
        do {
            i += 1;
            if (i >= height) {
                height++;
                var t = start.getRight();
                
                // grow two sentinels
                start = insertAfterAbove(null, start, start.getKey(), start.getValue());
                insertAfterAbove(start, t, t.getKey(), t.getValue());
            }

            q = insertAfterAbove(p, q, k, val);
            while (p.getAbove() == null) {
                p = p.getLeft();
            }

            p = p.getAbove();
        }
        while (i < maxLvl);
        width++;

        return skipSearchInfo.nodesTraversed;
    }

    @Override
    public LinkedListElement skipRemove(int k) {
        var p = skipSearch(k);
        if (p.getKey() != k) {
            return null;
        }

        var temp = p;
        while (temp != null) {
            var left = temp.getLeft();
            var right = temp.getRight();
            left.setRight(right);
            temp = temp.getAbove();
        }

        // cut the tower if the level below of start is empty
        while (start.getBelow().getRight().getKey() == null) {
            start.getBelow().getRight().setAbove(null);
            start.getBelow().setAbove(null);
            start = start.getBelow();
            height--;
        }

        width--;
        return p;
    }

    private LinkedListElement insertAfterAbove(LinkedListElement afterPos, LinkedListElement abovePos, Integer key, String val){
        var r = new LinkedListElement(key, val);
        if (afterPos != null) {
            var temp = afterPos.getRight();
            afterPos.setRight(r);
            if (temp != null) {
                r.setRight(temp);
            }
        }

        if (abovePos != null) {
            abovePos.setAbove(r);
        }
        return r;
    }

    private class MyValues {
        public LinkedListElement pointer;
        public int nodesTraversed;
    
        public MyValues(LinkedListElement pointer, int nodesTraversed) {
            this.nodesTraversed = nodesTraversed;
            this.pointer = pointer;
        }
    }
}