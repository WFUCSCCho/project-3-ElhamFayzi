import java.io.IOException;
import java.util.ArrayList;

public class Proj3 {
    // Sorting Method declarations
    // Merge Sort
    public static <T extends Comparable> void mergeSort(ArrayList<T> a, int left, int right) {
        if (left >= right) return;                      //FIXME!!! Check for all of the edge cases, e.g. a is empty or right > a.size()

        int mid = left + (right - left) / 2;
        mergeSort(a, left, mid);
        mergeSort(a, mid + 1, right);

        merge(a, left, mid, right);
    }

    public static <T extends Comparable> void merge(ArrayList<T> a, int left, int mid, int right) {
        ArrayList<T> temp = new ArrayList<T>();

        int i = left;
        int j = mid + 1;
        while (i <= mid && j <= right) {
            if (a.get(i).compareTo(a.get(j)) <= 0) {            //NOTE : <= makes the algorithm stable by keeping the relative positions of equal elements
                temp.add(a.get(i));
                i++;
            }
            else {
                temp.add(a.get(j));
                j++;
            }
        }

        while (i <= mid) {
            temp.add(a.get(i));                 //FIXME!!!! Can use post-increment inside a.get(i) instead of having it on a separate line
            i++;
        }

        while (j <= right) {
            temp.add(a.get(j));                //FIXME!!!! Can use post-increment inside a.get(i) instead of having it on a separate line
            j++;
        }

        for (int idx = 0; idx < temp.size(); idx++) {
            a.set(idx + left, temp.get(idx));
        }
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
        // Build MinHeap
        for (int i = (right / 2) - 1; i >= left; i++) {
            heapify(a, i, right);
        }

        while (left <= right) {
            swap(a, left, right);
            right--;
            heapify(a, left, right);
        }

    }

    public static <T extends Comparable> void heapify (ArrayList<T> a, int left, int right) {
        int min = left;
        int leftChild = 2 * min + 1;                               // leftChild is in index 2i on arrays that start from index 1, but since our array begins from 0th index, leftChild is at 2i + 1
        int rightChild = 2 * min + 2;

        if ((leftChild <= right) && (a.get(leftChild).compareTo(a.get(min)) < 0)) {
            min = leftChild;
        }
        if ((rightChild <= right) && (a.get(rightChild).compareTo(a.get(min)) < 0)) {
            min = rightChild;
        }
        if (min != left) {
            swap(a, left, min);
            heapify(a, min, right);
        }
    }

    // Bubble Sort
    public static <T extends Comparable> int bubbleSort(ArrayList<T> a, int size) {
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
        int numSwapsIfParallel = 0;

        boolean isSorted = false;
        while (!isSorted) {
            isSorted = true;
            //Bubble sort on even-indexed elements
            for (int i = 0; i < size - 1; i += 2) {
                if (a.get(i).compareTo(a.get(i + 1)) > 0) {
                    swap(a, i, i + 1);
                    isSorted = false;
                }
            }
            numSwapsIfParallel++;                       //Count 1 phase of simultaneous comparisons of even-indexed elements
            //Bubble sort on odd-indexed elements
            for (int i = 1; i < size - 1; i += 2) {
                if (a.get(i).compareTo(a.get(i + 1)) > 0) {
                    swap(a, i, i + 1);
                    isSorted = false;
                }
            }
            numSwapsIfParallel++;                       //Count 1 phase of simultaneous comparisons of odd-indexed elements
        }

        return numSwapsIfParallel;
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
