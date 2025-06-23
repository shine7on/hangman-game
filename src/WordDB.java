import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.io.FileReader;
import java.util.Random;

public class WordDB {
    // contain key -> category, value -> words
    private HashMap<String, String[]> wordList;

    // pick the random categories index
    private ArrayList<String> categories;

    public WordDB() {
        try {
            wordList = new HashMap<>();
            categories = new ArrayList<>();

            //get a file path
            String filePath = getClass().getClassLoader().getResource(CommonConstants.DATA_PATH).getPath();
            if (filePath.contains("%20")) filePath = filePath.replaceAll("%20", " ");
            BufferedReader reader = new BufferedReader(new FileReader(filePath));

            // iterate through each line in the data.txt
            String line;
            while((line = reader.readLine()) != null) {
                // split the data by ","
                String[] parts = line.split(",");

                String category = parts[0];
                categories.add(category);

                String values[] = Arrays.copyOfRange(parts, 1, parts.length);
                wordList.put(category, values);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    public String[] loadChallenge() {
        Random rand = new Random();

        // generate number randomly to choose the category
        String category = categories.get(rand.nextInt(categories.size()));

        // generate number randomly to choose the value from category
        String[] categoryValues = wordList.get(category);
        String word = categoryValues[rand.nextInt(categoryValues.length)];

        // [0] -> category and [1] -> word
        return new String[]{category.toUpperCase(), word.toUpperCase()};
    }
}
