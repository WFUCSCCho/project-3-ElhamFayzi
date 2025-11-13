import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Proj3 {
    // Sorting Method declarations
    // Merge Sort
    public static <T extends Comparable<T>> void mergeSort(ArrayList<T> a, int left, int right) {
        if (left >= right) return;                      //FIXME!!! Check for all of the edge cases, e.g. a is empty or right > a.size()

        int mid = left + (right - left) / 2;
        mergeSort(a, left, mid);
        mergeSort(a, mid + 1, right);

        merge(a, left, mid, right);
    }

    public static <T extends Comparable<T>> void merge(ArrayList<T> a, int left, int mid, int right) {
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
    public static <T extends Comparable<T>> void quickSort(ArrayList<T> a, int left, int right) {
        if (left >= right) return;

        //Find pivot
        int center = (left + right) / 2;
        if (a.get(center).compareTo(a.get(left)) < 0) { swap(a, left, center); }
        if (a.get(right).compareTo(a.get(left)) < 0) { swap(a, left, right); }
        if (a.get(right).compareTo(a.get(center)) < 0) { swap(a, center, right); }
        swap(a, center, right);             // FIXME!!! Confirm whether putting pivot at position right and right - 1 act the same way

        int pivot = partition(a, left, right);

        quickSort(a, left, pivot - 1);
        quickSort(a, pivot + 1, right);
    }


    public static <T extends Comparable<T>> int partition (ArrayList<T> a, int left, int right) {
        int pivot = right;
        int i = left;
        int j = right - 1;

        while (i < j) {
            while ((i < j) && (a.get(i).compareTo(a.get(pivot)) <= 0)) {
                i++;
            }
            while ((i < j) && a.get(j).compareTo(a.get(pivot)) >= 0) {
                j--;
            }

            if (i < j) {
                swap(a, i, j);              // We can use Post-increments in the function call to make it concise
                i++;
                j++;
            }
        }
        swap(a, i, pivot);
        return i;          // Because after swapping a[i] and a[pivot], i would be pointing to the pivot value and pivot would be pointing to i value
    }

    static <T> void swap(ArrayList<T> a, int i, int j) {
        T temp = a.get(i);
        a.set(i, a.get(j));
        a.set(j, temp);
    }

    // Heap Sort
    public static <T extends Comparable<T>> void heapSort(ArrayList<T> a, int left, int right) {
        // Build MinHeap
        for (int i = (right / 2) - 1; i >= left; i--) {
            heapify(a, i, right);
        }

        while (left <= right) {
            swap(a, left, right);
            right--;
            heapify(a, left, right);
        }

    }

    public static <T extends Comparable<T>> void heapify (ArrayList<T> a, int left, int right) {
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
    public static <T extends Comparable<T>> int bubbleSort(ArrayList<T> a, int size) {
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
    public static <T extends Comparable<T>> int transpositionSort(ArrayList<T> a, int size) {
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
            System.err.println("Usage: java Proj3 [dataset name] [sorting algorithm] [number of lines]");
            System.exit(1);
        }

        String dataSet = args[0];
        String sortingAlgo = args[1];
        int numLines = Integer.parseInt(args[2]);

        FileInputStream fis = new FileInputStream(dataSet);
        Scanner reader = new Scanner(fis);

        // ignore first line
        reader.nextLine();

        ArrayList<Player> playersList = new ArrayList<Player>();

        // Read each line and create Player objects
        for (int i = 0; i < numLines; i++) {
            if (!reader.hasNextLine()) {
                System.err.println("The entered numLines is greater than the number of lines in file (" + i + " lines exist in the input file");
                System.exit(1);
            }
            String[] line = reader.nextLine().toLowerCase().split(",");
            try {
                playersList.add(new Player(line));
            } catch (Exception e) {
                System.out.println("Line Number " + (i + 1) + " has an invalid format.");
            }
        }
        reader.close();


        long startTime;
        long endTime;

        int transpositionComparisons;
        int bubbleComparisons;

        //============================
        // Measure running times for sorting an already-sorted list
        Collections.sort(playersList);

        startTime = System.nanoTime();
        bubbleSort(playersList, playersList.size());
        endTime = System.nanoTime();
        double bubbleSort_SortedListTime = (endTime - startTime) / 1_000_000.0;

        startTime = System.nanoTime();
        mergeSort(playersList, 0, playersList.size() - 1);
        endTime = System.nanoTime();
        double mergeSort_SortedListTime = (endTime - startTime) / 1_000_000.0;

        startTime = System.nanoTime();
        heapSort(playersList, 0, playersList.size() - 1);
        endTime = System.nanoTime();
        double heapSort_SortedListTime = (endTime - startTime) / 1_000_000.0;

        //============================
        // Measure running times for sorting a randomly-shuffled list
        Collections.shuffle(playersList);

        startTime = System.nanoTime();
        bubbleSort(playersList, playersList.size());
        endTime = System.nanoTime();
        double bubbleSort_shuffledListTime = (endTime - startTime) / 1_000_000.0;

        startTime = System.nanoTime();
        mergeSort(playersList, 0, playersList.size() - 1);
        endTime = System.nanoTime();
        double mergeSort_shuffledListTime = (endTime - startTime) / 1_000_000.0;

        startTime = System.nanoTime();
        heapSort(playersList, 0, playersList.size() - 1);
        endTime = System.nanoTime();
        double heapSort_shuffledListTime = (endTime - startTime) / 1_000_000.0;

        //============================
        // Measure running times for sorting a reversely-sorted list
        Collections.sort(playersList, Collections.reverseOrder());

        startTime = System.nanoTime();
        bubbleSort(playersList, playersList.size());
        endTime = System.nanoTime();
        double bubbleSort_reversedListTime = (endTime - startTime) / 1_000_000.0;

        startTime = System.nanoTime();
        mergeSort(playersList, 0, playersList.size() - 1);
        endTime = System.nanoTime();
        double mergeSort_reversedListTime = (endTime - startTime) / 1_000_000.0;

        startTime = System.nanoTime();
        heapSort(playersList, 0, playersList.size() - 1);
        endTime = System.nanoTime();
        double heapSort_reversedListTime = (endTime - startTime) / 1_000_000.0;

        //============================
        // Measure number of comparisons for sorting an already-sorted list using bubble sort and transposition sort
        Collections.sort(playersList);
        transpositionComparisons = transpositionSort(playersList, playersList.size());
        bubbleComparisons = bubbleSort(playersList, playersList.size());

        //============================
        // Measure number of comparisons for sorting a randomly-shuffled list using bubble sort and transposition sort
        Collections.shuffle(playersList);
        transpositionComparisons = transpositionSort(playersList, playersList.size());
        bubbleComparisons = bubbleSort(playersList, playersList.size());

        //============================
        // Measure number of comparisons for sorting a reversely-sorted list using bubble sort and transposition sort
        Collections.sort(playersList, Collections.reverseOrder());
        transpositionComparisons = transpositionSort(playersList, playersList.size());
        bubbleComparisons = bubbleSort(playersList, playersList.size());
    }
}
