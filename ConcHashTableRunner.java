import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*
 * Concordance HashTable Runner
 * Jan 13, 2020
 * Elven Shum
 */

public class ConcHashTableRunner {
    private static final String FILE = "src/FormattedSonnets.txt";
    private static final int numOfData = 3256;
    //private static final ConcHashTable.Occurrence test = new ConcHashTable.Occurrence(2,2);


    public static void loadText(ConcHashTable currHash) {
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(FILE));
            String line;
            int sonnetNum = 1, lineNum = 0;
            while ((line = bufferedReader.readLine()) != null) {
                //if new sonnet
                if(line.equals("(NEW)")){
                    //inc sonnetNum, reset line Num
                    sonnetNum++;
                    lineNum = 0;
                } else {
                    //inc lineNum, and add word
                    lineNum++;
                    currHash.parseLine(line, sonnetNum,lineNum);
                }
            }

            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        StdOut.println("Welcome to Elven's Concordance HashTable Runner");
        StdOut.println("This HashTable tests with Shakespere's Sonnets as data, which has " +numOfData+" unique keys");
        StdOut.println("We test HashTable sizes from: n*"+numOfData+" to m*"+ numOfData+ " at increasing incremements of i");
        StdOut.println("ie, we test n*"+numOfData+", (n+i)*"+numOfData+", (n+2i)*"+numOfData+"...m*"+numOfData);
        StdOut.println("Please enter your desired n");
        double bot = StdIn.readDouble();
        StdOut.println("Please enter your desired m");
        double top = StdIn.readDouble();
        StdOut.println("Please enter your desired i");
        double inc = StdIn.readDouble();
        if (bot > top){
            StdOut.println("Your desired n < m; please restart and try again.");
        }
        if (bot ==1){
            StdOut.println("Unfortunately, n=1 is currently unrunnable. Defaulting to n = 1.5");
            bot = 1.5;
        }
        StdOut.println("Which metrics would you desire to see?");
        StdOut.println("1 : PrintEntireHashTable");
        StdOut.println("2 : Calculate Efficiency");
        StdOut.println("3 : Both");
        StdOut.println("Please input the number corresponding to your chosen metrics.");
        int metricSelec = StdIn.readInt();
        if (!(metricSelec > 0 && metricSelec < 4)) {
            StdOut.println("You have failed to enter a valid metric number, defaulting to Calculate Efficiency");
            metricSelec = 2;
        }

        switch (metricSelec) {
            case 1:
                for(double i = bot; i < top; i += inc){
                    int currCapacity = (int) (i * numOfData);
                    ConcHashTable currHash = new ConcHashTable(currCapacity);
                    StdOut.println("\n");
                    StdOut.println("Current HashTable Size: "+i);
                    loadText(currHash);
                    currHash.consolePrintHash();
                }
                break;
            case 2:
                for(double i = bot; i < top; i += inc){
                    int currCapacity = (int) (i * numOfData);
                    ConcHashTable currHash = new ConcHashTable(currCapacity);
                    StdOut.println("\n");
                    StdOut.println("Current HashTable Size: "+i);
                    loadText(currHash);
                    currHash.calculateEfficiencyVerbose();
                    StdOut.println(currHash.calculateGoodness());
                }
                break;
            case 3:
                for(double i = bot; i < top; i += inc){
                    int currCapacity = (int) (i * numOfData);
                    ConcHashTable currHash = new ConcHashTable(currCapacity);
                    loadText(currHash);
                    StdOut.println("\n");
                    StdOut.println("Current HashTable Multiplier: "+i);
                    currHash.consolePrintHash();
                    currHash.calculateEfficiencyVerbose();
                }
                break;
        }




    }

}
