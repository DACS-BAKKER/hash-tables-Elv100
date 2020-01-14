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
    public void printHash(){
        for(int i = 0; i<capacity; i++){
            if (keys[i] != null){
                StdOut.println("Key: "+ keys[i]+"  Hash: " +hashIt2(keys[i]) + "  Place: " +i +"  Val: " +vals[i]);
            }
        }
    }
}
