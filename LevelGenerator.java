import java.util.Random;
public class LevelGenerator implements ILevelGenerator {
    private final Random rand;

    public LevelGenerator() {
      rand = new Random();
    }
    
    @Override
    public int getRandomLevel(double alpha, int key) {
      return generateEll(alpha, key);
    }

    private int generateEll(double alpha_, int key) {
        int level = 1;
        if (alpha_ >= 0. && alpha_< 1) {
          while (rand.nextDouble() < alpha_) {
              level += 1;
          }
        }
        else {
          while (key != 0 && key % 2 == 0){
            key = key / 2;
            level += 1;
          }
        }

        return level;
    }
}
