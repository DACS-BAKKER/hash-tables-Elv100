import edu.princeton.cs.algs4.StdOut;

/*
 * Concordance HashTable
 * Jan 13, 2020
 * Elven Shum
 */

public class ConcHashTable {
    private int capacity;      // size of table array
    private String[] keys;     // array of Keys: size of cap
    private LinkedList[] vals;     // array of vals: size of cap


    public ConcHashTable(int capacity) {
        this.capacity = capacity;
        keys = new String[capacity];
        vals = new LinkedList[capacity];
    }


    //Input key, returns first node of LinkedList val
    public LinkedList get(String key) {
        if (key == null) return null;

        /*runs through array until EITHER
         *  -finds empty, then returns null
         *  -finds key, then returns value
         */
        for (int i = hashIt(key); keys[i] != null; i = (i + 1) % capacity) {
            if (keys[i].equals(key)) {
                return vals[i];
            }
        }
        return null;
    }

    public String getStringVals(String key){
        if (key == null) return null;

        /*runs through array until EITHER
         *  -finds empty, then returns null
         *  -finds key, then returns value
         */
        for (int i = hashIt(key); keys[i] != null; i = (i + 1) % capacity) {
            if (keys[i].equals(key)) {
                return vals[i].outputStringList();
            }
        }
        return "Does not exist in Sonnets";
    }


    //put in Key and Value
    public void put(String key, Occurrence val) {
        if (key == null) return;
        int i;
        //runs through array until finds empty index
        for (i = hashIt(key); keys[i] != null; i = (i + 1) % capacity) {
            //if the key exists, then
            //set the value equal, end function
            if (keys[i].equalsIgnoreCase(key)) {
                vals[i].addItem(val);
                return;
            }
        }
        /*if doesnt exist already,
         *then set key, value paring
         */
        keys[i] = key;
        vals[i] = new LinkedList<>();
        vals[i].addItem(val);
    }

    //the Hash Function
    private int hashIt(String key) {
        int hashed = 0;
        int hashMod = capacity; //
        for (int i = 0; i < key.length(); i++) {
            int charVal = (int) key.toLowerCase().charAt(i);
            hashed += charVal*charVal;
        }
        return (hashed % hashMod);
    }


    public void parseLine(String line, int sonnetNum, int lineNum){
        //defines currentPosition
        Occurrence currPos = new Occurrence(sonnetNum, lineNum);

        //while the line has separate words--defined by spaces
        while(line.contains(" ")){
            int endOfFirstWord = line.indexOf(" ");
            //prepares raw word, then adds
            String currWord = prepWord(line.substring(0, endOfFirstWord));
            put(currWord, currPos);
            line = line.substring(endOfFirstWord+1);

        }
        //prepares word and adds
        String lastWord = prepWord(line);
        put(lastWord, currPos);
    }

    //prepares rawWord for adding
    //by removing ending punctuation: "; , . : ! ?"
    private String prepWord(String rawWord){
        int rawLength = rawWord.length();
        String finalChar = rawWord.substring(rawLength - 1);
        if (    finalChar.equals(";") ||
                finalChar.equals(",") ||
                finalChar.equals(".") ||
                finalChar.equals(":") ||
                finalChar.equals("!") ||
                finalChar.equals("?")){
            rawWord = rawWord.substring(0,rawLength-1);
        }
        //returns no longer rawWord
        return rawWord;
    }


    //prints entire HashTable in console
    public void consolePrintHash(){
        for(int i = 0; i<capacity; i++){
            if (keys[i] != null){
                StdOut.print("Key: "+ keys[i]+"  Hash: " +hashIt(keys[i]) + "  Place: " +i +"  Val: ");
                vals[i].consolePrintList();
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

                collisionsRunning += i - hashIt(keys[i]);
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
    }

    //calculates Chi-Squared Optimalness,
    //doesn't work yet...
    public double calculateGoodness(){
        double numerator = 0;
        double denominator = 0;
        double numOfUniqKeys = 0;
        int m = capacity;
        double numItemsInVal = 0;
        for (int i = 0; i<capacity; i++){
            if (vals[i] != null) {
                numOfUniqKeys++;
                numItemsInVal = vals[i].lengthList();
                numerator += numItemsInVal * (numItemsInVal+1)/2;
            }
        }
        double part1 = (numOfUniqKeys/(2 * m));
        double part2 =(numOfUniqKeys+(2*m)-1);
        denominator = part1*part2;
        return numerator/denominator;
    }

    public void calculateAveCollisions(){
        int numOfUniqKeys = 0, lastKeyPos = 0;
        long distBetwnKeysRunning = 0, collisionsRunning = 0;
        double aveDistBetwnKeys, aveCollis;
        for(int i = 0; i<capacity; i++){
            if (keys[i] != null){
                numOfUniqKeys++;


                distBetwnKeysRunning += i - lastKeyPos;
                lastKeyPos = i;

                int temp = (i - hashIt(keys[i])) % (capacity - 1);
                collisionsRunning += temp;
            }
        }
       // aveDistBetwnKeys = (double) distBetwnKeysRunning/numOfUniqKeys;
        aveCollis = (double) collisionsRunning/numOfUniqKeys;
        StdOut.print(aveCollis+ " ");
    }




    //Occurrence Object
    //essentially a Pair, but specifically for sonnets
    static class Occurrence {
        private int sonnetNum;
        private int lineNum;

        public Occurrence(int sonnetNum, int lineNum){
            this.sonnetNum = sonnetNum;
            this.lineNum = lineNum;
        }

        @Override
        public String toString() {
            return String.valueOf("Sonnet: " + sonnetNum + ", Line: " + lineNum);
        }
    }

    //standard LinkedList class
    class LinkedList<Item> {
        Node<Item> origin;

        public LinkedList() {
            origin = null;
        }
        public LinkedList(Node first) {
            origin = first;
        }

        public void addItem(Item data) {
            if (origin == null) {
                origin = new Node<Item>(data);
            } else {
                Node<Item> temp = origin;
                while (temp.next != null) {
                    temp = temp.next;
                }
                temp.next = new Node<Item>(data);
            }
        }

        public int getItemPosition(Item data) {
            if (origin == null) {
                return -1;
            }
            Node<Item> currentNode = origin;
            int counter = 0;
            while (!(currentNode.data == data)) {
                if (currentNode.next == null) {
                    return -1;
                }
                currentNode = currentNode.next;
                counter++;
            }
            return counter;


        }

        public void insert(Item data, int place) {
            Node<Item> currentNode = origin;
            //if start, then place node immediately at beginning
            if (place == 0) {
                origin = new Node<Item>(data);
                origin.next = currentNode;
            } else {
                int counter = 0;
                while (currentNode != null) {
                    if (counter == place - 1) {
                        Node<Item> temp = currentNode.next;
                        temp.next = new Node<Item>(data);
                        temp.next.next = temp;

                    } else {
                        currentNode = currentNode.next;
                    }
                    counter++;
                }

            }

        }

        public void remove(int place) {
            Node<Item> currentNode = origin;
            int counter = 0;
            while (counter != place - 1) {
                if (currentNode.next == null) {
                    return;
                }
                currentNode = currentNode.next;
                counter++;
            }
            currentNode.next = currentNode.next.next;
        }

        public void consolePrintList() {
            Node<Item> currentNode = origin;
            while (currentNode.next != null) {
                StdOut.print(currentNode.data + " ");
                currentNode = currentNode.next;
            }
            StdOut.print(currentNode.data + " \n");
        }

        public String outputStringList() {
            Node<Item> currentNode = origin;
            String outputStr = "";
            while (currentNode.next != null) {
                outputStr = outputStr + currentNode.data + "\n";
                currentNode = currentNode.next;
            }
            outputStr = outputStr + currentNode.data +"\n";
            return outputStr;
        }

        public int lengthList() {
            Node<Item> currentNode = origin;
            int count = 0;
;            while (currentNode.next != null) {
                count++;
                currentNode = currentNode.next;
            }
            count++;
            return count;
        }
    }

    //standard Node class
    class Node <Item>{
        public Item data;
        public Node next;

        public Node(Item data) {
            this.data = data;
        }

        public Node() {}

        @Override
        public String toString() {
            return String.valueOf(data);
        }
    }
}
