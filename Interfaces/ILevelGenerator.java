package Interfaces;

public interface ILevelGenerator {
    int[] GetRandomLevels(double alpha, int key, int size);
    int GetRandomLevel(double alpha, int key);
}
