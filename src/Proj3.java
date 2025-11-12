import java.io.IOException;
import java.util.ArrayList;

public class Proj3 {
    // Sorting Method declarations
    // Merge Sort
    public static <T extends Comparable> void mergeSort(ArrayList<T> a, int left, int right) {
        // Finish Me
    }

    public static <T extends Comparable> void merge(ArrayList<T> a, int left, int mid, int right) {
        // Finish Me
    }

    // Quick Sort
    public static <T extends Comparable> void quickSort(ArrayList<T> a, int left, int right) {
        // Finish Me
    }

    public static <T extends Comparable> int partition (ArrayList<T> a, int left, int right) {
        // Finish Me
    }

    static <T> void swap(ArrayList<T> a, int i, int j) {
        T temp = a.get(i);
        a.set(i, a.get(j));
        a.set(j, temp);
    }

    // Heap Sort
    public static <T extends Comparable> void heapSort(ArrayList<T> a, int left, int right) {
        // Finish Me
    }

    public static <T extends Comparable> void heapify (ArrayList<T> a, int left, int right) {
        // Finish Me
    }

    // Bubble Sort
    public static <T extends Comparable> int bubbleSort(ArrayList<T> a, int size) {
        //REVIEW THIS !!!!

        int numOfSwaps = 0;

        boolean isSorted = false;
        while (!isSorted) {
            isSorted = true;
            for (int i = 0; i < size - 1; i++) {           //FIXME!!! We don't need to do complete passes each time, since the largest values would start to add to the end of the list anyways. We can keep track of the number of passes and loop till size - 1 - pass
                if (a.get(i).compareTo(a.get(i + 1)) > 0) {
                    swap(a, i, i + 1);
                    numOfSwaps++;

                    isSorted = false;
                }
            }
        }

        return numOfSwaps;
    }

    // Odd-Even Transposition Sort
    public static <T extends Comparable> int transpositionSort(ArrayList<T> a, int size) {
        // Finish Me
    }

    public static void main(String [] args)  throws IOException {
        if (args.length != 3) {
            System.out.println("Usage: java Proj3 [dataset name] [sorting algorithm] [number of lines]");
        }

        String dataSet = args[0];
        String sortingAlgo = args[1];
        int numOfLines = Integer.parseInt(args[2]);


    }
}
