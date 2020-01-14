import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.activation.ActivationGroup_Stub;

/*
 * HashTable Runner
 * Jan 13, 2020
 * Elven Shum
 */

public class HashTableRunner {
    private static final String FILE = "src/1000WordDictionary.txt";
    private static final int numOfData = 1000;


    private static void loadText(HashTable currHash) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE));

            String line;
            while ((line = bufferedReader.readLine()) != null) {
                currHash.put(line, "0");
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        int currCapacity = 5 * numOfData;
        HashTable currHash = new HashTable(currCapacity);
        loadText(currHash);
        currHash.printHash();


        /*for(int i = 1; i < 10; i++){
            int currCapacity = i * numOfData;
            HashTable currHash = new HashTable(currCapacity);
            loadText(currHash);
            currHash.printHash();
        } */
    }

}
