package Interfaces;

public interface ISkipList {
    LinkedListElement skipSearch(int k);

    LinkedListElement skipInsert(int k, String val);
}
