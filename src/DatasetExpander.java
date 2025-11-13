/******************************************************************
 * @file :                     DatasetExpander.java
 * @description:               This is a self-written class that takes an existing CSV dataset and randomly generates additional entries
 *                             to increase its size. The program reads the original dataset, then creates the desired number of new rows by
 *                             randomly selecting values from existing rows for each column. The expanded dataset is saved to a new file
 *                             prefixed with _EXPANDED_.
 * @author:                    Elham Fayzi
 * @date:                      Nov 13, 2025
 *
 * @NOTE:                      I wrote this class to randomly expand my dataset from roughly 570 entries to approximately 15,000 entries for
 *                             better graphing and runtime analysis. Both the original dataset and the expanded version are available within the same directory.
 *******************************************************************/

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class DatasetExpander {
    public static void main(String[] args) throws IOException {
        if (args.length != 2) {
            System.out.println("Usage: DatasetExpander <dataset> <desired number of entries>");
            System.exit(1);
        }
        String fileName = args[0];
        int desiredNumOfEntries = Integer.parseInt(args[1]);

        FileInputStream fis = new FileInputStream(fileName);
        Scanner reader = new Scanner(fis);
        reader.nextLine();

        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();
        while (reader.hasNextLine()) {
            String[] line = reader.nextLine().split(",");
            ArrayList<String> row = new ArrayList<>(Arrays.asList(line));
            data.add(row);
        }

        reader.close();
        fis.close();

        // Randomly expand the dataset by the desired number of entries
        FileOutputStream fos = new FileOutputStream("_EXPANDED_" + fileName, true);
        PrintWriter writer = new PrintWriter(fos);
        writer.println();

        Random rand = new Random();
        for (int i = 0; i < desiredNumOfEntries; i++) {
            for (int j = 0; j < data.getFirst().size(); j++) {
                int row = rand.nextInt(data.size());
                writer.print(data.get(row).get(j) + ",");
            }
            writer.println();
            writer.flush();
        }
        writer.close();

    }
}
