package Interfaces.Implementations;

import Interfaces.ILevelGenerator;
import java.util.Random;
public class LevelGenerator implements ILevelGenerator {
    private Random rand;

    @Override
    public int[] GetRandomLevels(double alpha, int key, int size) {
        rand = new Random();
        var result = new int[size];
        
        for (int i = 0; i < size; i++) {
            result[i] = generateEll(alpha, key);
        }

        return result;
    }

    @Override
    public int GetRandomLevel(double alpha, int key) {
      return generateEll(alpha, key);
    }

    private int generateEll(double alpha_, int key) {
        int level = 0;
        if (alpha_ >= 0. && alpha_< 1) {
          while (rand.nextDouble() < alpha_) {
              level += 1;
          }
        }
        else{
          while (key != 0 && key % 2 == 0){
            key = key / 2;
            level += 1;
          }
        }
        return level;
    }
}
