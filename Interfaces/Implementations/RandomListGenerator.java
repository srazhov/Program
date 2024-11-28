package Interfaces.Implementations;

import Interfaces.IRandomListGenerator;
import java.util.Random;

public class RandomListGenerator implements IRandomListGenerator {

    @Override
    public int[] GetRandomizedSortedList(int size) {
        var rand = new Random();
        var result = new int[size];
        for (int i = 0; i < size; i++) {
            result[i] = rand.nextInt(50);
        }

        BubbleSort(result);

        return result;
    }
    
    private void BubbleSort(int[] arr){
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[i]) {
                    var temp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }
}
