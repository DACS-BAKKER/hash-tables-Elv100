import edu.princeton.cs.algs4.StdOut;

/*
 * HashTable class
 * Jan 13, 2020
 * Elven Shum
 */

public class HashTable {
    private int capacity;      // size of table array
    private String[] keys;     // array of Keys: size of cap
    private String[] vals;     // array of vals: size of cap


    public HashTable(int capacity) {
        this.capacity = capacity;
        keys = new String[capacity];
        vals = new String[capacity];
    }

    //Input key, returns value
    public String get(String key) {
        if (key == null) return null;

        /*runs through array until EITHER
         *  -finds empty, then returns null
         *  -finds key, then returns value
         */
        for (int i = hashIt2(key); keys[i] != null; i = (i + 1) % capacity) {
            if (keys[i].equals(key)) {
                return vals[i];
            }
        }
        return null;
    }

    //put in Key and Value
    public void put(String key, String val) {
        if (key == null) return;
        int i;
        //runs through array until finds empty index
        for (i = hashIt2(key); keys[i] != null; i = (i + 1) % capacity) {
            //StdOut.println(i);
            //if the key already exists, set the value equal, end function
            if (keys[i].equalsIgnoreCase(key)) {
                vals[i] = val;
                return;
            }
        }
        /*if doesnt exist already,
         *then set key, value paring
         */
        keys[i] = key;
        vals[i] = val;
    }

    //the Hash Function
    private int hashIt(String key) {
        int hashed = 0;
        int spread = 4;
        int hashMod = capacity; //
        for (int i = 0; i < key.length(); i++) {
            hashed += (int) key.toLowerCase().charAt(i);
        }
        return (hashed * spread % hashMod);
    }

    private int hashIt2(String key) {
        int hashed = 0;
        int spread = 4;
        int hashMod = capacity; //
        for (int i = 0; i < key.length(); i++) {
            int charVal = (int) key.toLowerCase().charAt(i);
            hashed += charVal*charVal;
        }
        return (hashed * spread % hashMod);
    }

    //prints the contents of Entire HashTable
    public void consolePrintHash(){
        for(int i = 0; i<capacity; i++){
            if (keys[i] != null){
                StdOut.println("Key: "+ keys[i]+"  Hash: " +hashIt2(keys[i]) + "  Place: " +i +"  Val: " +vals[i]);
            }
        }
    }

    public void calculateEfficiencyVerbose(){
        int numOfUniqKeys = 0, lastKeyPos = 0;
        long distBetwnKeysRunning = 0, collisionsRunning = 0;
        double aveDistBetwnKeys, aveCollis;
        for(int i = 0; i<capacity; i++){
            if (keys[i] != null){
                numOfUniqKeys++;

                distBetwnKeysRunning += i - lastKeyPos;
                lastKeyPos = i;

                collisionsRunning += i - hashIt2(keys[i]);

            }
        }
        //average distance between keys
        //average num of collisions
        //Chi Squared Optimization
        aveDistBetwnKeys = (double) distBetwnKeysRunning/numOfUniqKeys;
        aveCollis = (double) collisionsRunning/numOfUniqKeys;

        StdOut.println("Capacity (size of HashTable): "+ capacity);
        StdOut.println("Num of Unique Keys: " + numOfUniqKeys);
        StdOut.println("Average Distance Between Keys: "+ aveDistBetwnKeys);
        StdOut.println("Total Num of Collisions: "+ collisionsRunning);
        StdOut.println("Average Num of Collisions: "+ aveCollis);
        StdOut.println("Optimal Distribution Metric (\"Goodness\") Measurement: "+ calculateGoodness());
        StdOut.println("    Note: from 0.95-1.05 on the Goodness Measurement indicates keys are essentially optimally/uniformly distributed");
    }

    //calculates Chi-Squared Optimalness,
    public double calculateGoodness(){
        double numerator = 0;
        double denominator = 0;
        double numOfKeys = 0;
        int m = capacity;
        double numItemsInVal = 0;
        for (int i = 0; i<capacity; i++){
            if (vals[i] != null) {
                numOfKeys++;
                numItemsInVal = 1;
                numerator += numItemsInVal * (numItemsInVal+1)/2;
            }
        }
        double part1 = (numOfKeys/(2 * m));
        double part2 =(numOfKeys+(2*m)-1);
        denominator = part1*part2;
        double goodness = numerator/denominator;
        return goodness;
    }

    //internal Caluclation for Graphs
    public double calculateAveCollisionsInternal(){
        int numOfUniqKeys = 0, lastKeyPos = 0;
        long distBetwnKeysRunning = 0, collisionsRunning = 0;
        double aveDistBetwnKeys, aveCollis;
        for(int i = 0; i<capacity; i++){
            if (keys[i] != null){
                numOfUniqKeys++;


                distBetwnKeysRunning += i - lastKeyPos;
                lastKeyPos = i;

                int temp = (i - hashIt2(keys[i])) % (capacity - 1);
                collisionsRunning += temp;
            }
        }
        // aveDistBetwnKeys = (double) distBetwnKeysRunning/numOfUniqKeys;
        aveCollis = (double) collisionsRunning/numOfUniqKeys;
        StdOut.print(aveCollis+ " ");
        return aveCollis;
    }

    //internal calculation for Goodness for Graphs
    public double calculateGoodnessInternal(){
        double numerator = 0;
        double denominator = 0;
        double numOfKeys = 0;
        int m = capacity;
        double numItemsInVal = 0;
        for (int i = 0; i<capacity; i++){
            if (vals[i] != null) {
                numOfKeys++;
                numItemsInVal = 1;
                numerator += numItemsInVal * (numItemsInVal+1)/2;
            }
        }
        double part1 = (numOfKeys/(2 * m));
        double part2 =(numOfKeys+(2*m)-1);
        denominator = part1*part2;
        double goodness = numerator/denominator;
        StdOut.print(goodness+ " ");
        return goodness;
    }
}
