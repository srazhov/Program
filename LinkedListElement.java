public class LinkedListElement {
    private final MyEntry entry;
    private LinkedListElement left;
    private LinkedListElement right;
    private LinkedListElement above;
    private LinkedListElement below;


    public LinkedListElement(Integer key, String value) {
        entry = new MyEntry(key, value);
    }

    public Integer getKey() {
        return entry.getKey();
    }

    public String getValue() {
        return entry.getValue();
    }

    public LinkedListElement getLeft() {
        return left;
    }

    public LinkedListElement getRight() {
        return right;
    }

    public void setRight(LinkedListElement right) {
        this.right = right;
        right.left = this;
    }

    public LinkedListElement getAbove() {
        return above;
    }

    public void setAbove(LinkedListElement above) {
        this.above = above;
        above.below = this;
    }

    public LinkedListElement getBelow() {
        return below;
    }
}
