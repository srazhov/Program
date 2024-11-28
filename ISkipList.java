public interface ISkipList {
    int getHeight();
    
    int getWidth();

    LinkedListElement getStart();

    LinkedListElement skipSearch(int k);

    LinkedListElement skipInsert(int k, String val);

    LinkedListElement skipRemove(int k);
}
