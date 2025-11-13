/******************************************************************
 * @file :                     Proj3.java
 * @description:               This class analyzes the performance of multiple sorting algorithms on a dataset of soccer players. It measures their
 *                             runtime on three types of input lists: already-sorted, randomly-shuffled, and reversely-sorted. For Bubble Sort and
 *                             Transposition Sort, it also counts the number of comparisons performed. The program reads a CSV dataset, sorts the data
 *                             according to the selected algorithm, writes the sorted output to sorted.txt, and appends runtime and comparison results
 *                             to analysis.txt.
 * @author:                    Elham Fayzi
 * @date:                      Nov 13, 2025
 *******************************************************************/

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class Proj3 {
    // Sorting Method declarations
    // Merge Sort
    public static <T extends Comparable<T>> void mergeSort(ArrayList<T> a, int left, int right) {
        if (left >= right) return;

        int mid = left + (right - left) / 2;
        mergeSort(a, left, mid);
        mergeSort(a, mid + 1, right);

        merge(a, left, mid, right);
    }

    // Helper method for mergeSort
    public static <T extends Comparable<T>> void merge(ArrayList<T> a, int left, int mid, int right) {
        ArrayList<T> temp = new ArrayList<T>();

        int i = left;
        int j = mid + 1;
        while (i <= mid && j <= right) {
            if (a.get(i).compareTo(a.get(j)) <= 0) {            //NOTE : (<=) makes the algorithm stable by keeping the relative positions of equal elements
                temp.add(a.get(i));
                i++;
            }
            else {
                temp.add(a.get(j));
                j++;
            }
        }

        // Add remaining elements from left sublist
        while (i <= mid) {
            temp.add(a.get(i));
            i++;
        }

        // Add remaining elements from right sublist
        while (j <= right) {
            temp.add(a.get(j));
            j++;
        }

        // Copy sorted elements back to original array
        for (int idx = 0; idx < temp.size(); idx++) {
            a.set(idx + left, temp.get(idx));
        }
    }

    // Quick Sort
    public static <T extends Comparable<T>> void quickSort(ArrayList<T> a, int left, int right) {
        if (left >= right) return;

        // Median-of-three pivot selection
        int center = (left + right) / 2;
        if (a.get(center).compareTo(a.get(left)) < 0) { swap(a, left, center); }
        if (a.get(right).compareTo(a.get(left)) < 0) { swap(a, left, right); }
        if (a.get(right).compareTo(a.get(center)) < 0) { swap(a, center, right); }
        swap(a, center, right);          // Pivot placed at right

        int pivot = partition(a, left, right);

        quickSort(a, left, pivot - 1);
        quickSort(a, pivot + 1, right);
    }

    // Helper method for quickSort (partitions list around pivot)
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
                swap(a, i, j);              // We can also use Post-increments in the function call to make this block concise
                i++;
                j++;
            }
        }
        swap(a, i, pivot);  // Place pivot in correct position
        return i;          // Return pivot index which is at index i because after swapping a[i] and a[pivot], i would be pointing to the pivot value in the list
    }

    // Swap Helper
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

    // Helper method for heapSort (heapify subtree rooted at 'left')
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
            for (int i = 0; i < size - 1; i++) {           // We could also optimize with decreasing limit each pass (looping till size – 1 – pass instead) since the largest values would start to add up at the end of the list after each iteration.
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
            numSwapsIfParallel++;                       //Count 1 phase of simultaneous comparisons of even-indexed elements as per instructions

            //Bubble sort on odd-indexed elements
            for (int i = 1; i < size - 1; i += 2) {
                if (a.get(i).compareTo(a.get(i + 1)) > 0) {
                    swap(a, i, i + 1);
                    isSorted = false;
                }
            }
            numSwapsIfParallel++;                       //Count 1 phase of simultaneous comparisons of odd-indexed elements as per instructions
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

        // Read the dataset
        FileInputStream fis = new FileInputStream(dataSet);
        Scanner reader = new Scanner(fis);

        // store header to later re-write in the sorted file
        String firstLine = reader.nextLine();

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

        // =============================
        // Initialize runtime/comparison counters
        // =============================
        long startTime;
        long endTime;

        double runtimeSorted = -1;
        double runtimeShuffled = -1;
        double runtimeReversed = -1;

        boolean bubbleOrTransposition = false;
        int comparisonsSorted = -1;
        int comparisonsShuffled = -1;
        int comparisonsReversed = -1;

        // =============================
        // Run selected sorting algorithm on sorted, shuffled, and reversed lists
        // =============================
        switch (sortingAlgo.toLowerCase()) {
            case "bubblesort" -> {
                bubbleOrTransposition = true;
                //============================
                // Measure running times for sorting an already-sorted list
                Collections.sort(playersList);

                startTime = System.nanoTime();
                comparisonsSorted = bubbleSort(playersList, playersList.size());
                endTime = System.nanoTime();
                runtimeSorted = (endTime - startTime) / 1_000_000.0;

                //============================
                // Measure running times for sorting a randomly-shuffled list
                Collections.shuffle(playersList);

                startTime = System.nanoTime();
                comparisonsShuffled = bubbleSort(playersList, playersList.size());
                endTime = System.nanoTime();
                runtimeShuffled = (endTime - startTime) / 1_000_000.0;

                //============================
                // Measure running times for sorting a reversely-sorted list
                Collections.sort(playersList, Collections.reverseOrder());

                startTime = System.nanoTime();
                comparisonsReversed = bubbleSort(playersList, playersList.size());
                endTime = System.nanoTime();
                runtimeReversed = (endTime - startTime) / 1_000_000.0;
            }

            case "transpositionsort" -> {
                bubbleOrTransposition = true;
                //============================
                // Measure number of comparisons for sorting an already-sorted list using transposition sort
                Collections.sort(playersList);

                startTime = System.nanoTime();
                comparisonsSorted = transpositionSort(playersList, playersList.size());
                endTime = System.nanoTime();
                runtimeSorted = (endTime - startTime) / 1_000_000.0;

                //============================
                // Measure number of comparisons for sorting a randomly-shuffled list using transposition sort
                Collections.shuffle(playersList);

                startTime = System.nanoTime();
                comparisonsShuffled = transpositionSort(playersList, playersList.size());
                endTime = System.nanoTime();
                runtimeShuffled = (endTime - startTime) / 1_000_000.0;

                //============================
                // Measure number of comparisons for sorting a reversely-sorted list using transposition sort
                Collections.sort(playersList, Collections.reverseOrder());
                startTime = System.nanoTime();
                comparisonsReversed = transpositionSort(playersList, playersList.size());
                endTime = System.nanoTime();
                runtimeReversed = (endTime - startTime) / 1_000_000.0;
            }

            case "mergesort" -> {
                // Measure running times for sorting an already-sorted list
                Collections.sort(playersList);

                startTime = System.nanoTime();
                mergeSort(playersList, 0, playersList.size() - 1);
                endTime = System.nanoTime();
                runtimeSorted = (endTime - startTime) / 1_000_000.0;

                // Measure running times for sorting a randomly-shuffled list
                Collections.shuffle(playersList);

                startTime = System.nanoTime();
                mergeSort(playersList, 0, playersList.size() - 1);
                endTime = System.nanoTime();
                runtimeShuffled = (endTime - startTime) / 1_000_000.0;

                //============================
                // Measure running times for sorting a reversely-sorted list
                Collections.sort(playersList, Collections.reverseOrder());

                startTime = System.nanoTime();
                mergeSort(playersList, 0, playersList.size() - 1);
                endTime = System.nanoTime();
                runtimeReversed = (endTime - startTime) / 1_000_000.0;
            }

            case "heapsort" -> {
                //============================
                // Measure running times for sorting an already-sorted list
                Collections.sort(playersList);

                startTime = System.nanoTime();
                heapSort(playersList, 0, playersList.size() - 1);
                endTime = System.nanoTime();
                runtimeSorted = (endTime - startTime) / 1_000_000.0;

                //============================
                // Measure running times for sorting a randomly-shuffled list
                Collections.shuffle(playersList);

                startTime = System.nanoTime();
                heapSort(playersList, 0, playersList.size() - 1);
                endTime = System.nanoTime();
                runtimeShuffled = (endTime - startTime) / 1_000_000.0;

                //============================
                // Measure running times for sorting a reversely-sorted list
                Collections.sort(playersList, Collections.reverseOrder());

                startTime = System.nanoTime();
                heapSort(playersList, 0, playersList.size() - 1);
                endTime = System.nanoTime();
                runtimeReversed = (endTime - startTime) / 1_000_000.0;
            }

            case "quicksort" -> {
                //============================
                // Measure running times for sorting an already-sorted list
                Collections.sort(playersList);

                startTime = System.nanoTime();
                quickSort(playersList, 0, playersList.size() - 1);
                endTime = System.nanoTime();
                runtimeSorted = (endTime - startTime) / 1_000_000.0;

                //============================
                // Measure running times for sorting a randomly-shuffled list
                Collections.shuffle(playersList);

                startTime = System.nanoTime();
                quickSort(playersList, 0, playersList.size() - 1);
                endTime = System.nanoTime();
                runtimeShuffled = (endTime - startTime) / 1_000_000.0;

                //============================
                // Measure running times for sorting a reversely-sorted list
                Collections.sort(playersList, Collections.reverseOrder());

                startTime = System.nanoTime();
                quickSort(playersList, 0, playersList.size() - 1);
                endTime = System.nanoTime();
                runtimeReversed = (endTime - startTime) / 1_000_000.0;
            }

            default -> {
                System.err.println("Unrecognized sorting algorithm: " + sortingAlgo);
                System.exit(1);
            }
        }

        // Write sorted data to file
        FileOutputStream fos = new FileOutputStream("sorted.txt");
        PrintWriter writer = new PrintWriter(fos);
        writer.println(firstLine);

        for (Player p : playersList) {
            writer.println(p.toString());
            writer.flush();
        }

        writer.close();

        // Append runtime analysis to analysis.txt
        File file = new File("analysis.txt");
        boolean fileExists = file.exists();


        fos = new FileOutputStream(file, true);
        writer = new PrintWriter(fos);

        if (!fileExists) {
            writer.println("SortingAlgorithm" + "," + "NumLines" + "," + "RuntimeSorted" + "," + "RuntimeShuffled" + "," + "RuntimeReversed" + "," + "ComparisonsSorted" + "," + "ComparisonsShuffled" + "," + "ComparisonsReversed");
        }
        writer.print(sortingAlgo + "," + numLines + "," + runtimeSorted + "," + runtimeShuffled + "," + runtimeReversed + ",");
        if (bubbleOrTransposition) {
            writer.print(comparisonsSorted + "," + comparisonsShuffled + "," + comparisonsReversed);
        }
        writer.println();
        writer.flush();
        writer.close();

        // Print results to console
        System.out.println("============================================");
        System.out.println("Runtimes in milliseconds for " + sortingAlgo + " (" + numLines + " lines)");
        System.out.println(" - Already-sorted List    : " + runtimeSorted);
        System.out.println(" - Randomly-shuffled List : " + runtimeShuffled);
        System.out.println(" - Randomly-reversed List : " + runtimeReversed);
        System.out.println("============================================");
        if (bubbleOrTransposition) {
            System.out.println("Number of comparisons for " + sortingAlgo + " (" + numLines + " lines)");
            System.out.println(" - Already-sorted List    : " + comparisonsSorted);
            System.out.println(" - Randomly-shuffled List : " + comparisonsShuffled);
            System.out.println(" - Randomly-reversed List : " + comparisonsReversed);
            System.out.println("============================================");
        }
    }
}
