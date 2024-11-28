public class SkipList implements ISkipList {
    private LinkedListElement start;
    private int height;
    private int width;

    private final double alpha;
    private final int key;
    private final ILevelGenerator levelGenerator;

    public SkipList(double alpha, int key, ILevelGenerator levelGenerator) {
        this.alpha = alpha;
        this.key = key;
        this.levelGenerator = levelGenerator;

        height = 1;
        width = 2;
        start = new LinkedListElement(Integer.MIN_VALUE, "-inf");
        start.setRight(new LinkedListElement(Integer.MAX_VALUE, "+inf"));
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
        var temp = start;
        while (temp.getBelow() != null) {
            temp = temp.getBelow();

            while (temp.getRight() != null && k >= temp.getRight().getKey()) {
                temp = temp.getRight();
            }
        }

        return temp;
    }

    @Override
    public LinkedListElement skipInsert(int k, String val) {
        var p = skipSearch(k);
        LinkedListElement q = null;
        
        var maxLvl = levelGenerator.GetRandomLevel(alpha, key);
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

        return q;
    }

    @Override
    public LinkedListElement skipRemove(int k) {
        var p = skipSearch(k);
        if (p.getKey() != k || k == Integer.MIN_VALUE || k == Integer.MAX_VALUE) {
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
        if (start.getBelow().getRight().getRight() == null) {
            start.setAbove(null);
            start.getRight().setAbove(null);
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
}