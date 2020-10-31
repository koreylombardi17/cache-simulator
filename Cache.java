import java.util.*;

public class Cache {

	// 64 Byte block size
    static final int BLOCK_SIZE = 64;
    private int cacheSize;
    private int associativity;
    private int numSets;

    // ArrayList used to store the DoublyLinkedList's and HashSet's
    private ArrayList<LinkedList<Block>> blocksList;
    private ArrayList<HashSet<Long>> tagsList;

    public Cache(int cacheSize, int associativity) {
        this.cacheSize = cacheSize;
        this.associativity = associativity;
        this.numSets = calculateNumSets();
        this.blocksList = new ArrayList<>();
        this.tagsList = new ArrayList<>();

        // Initializes the ArrayList used to store the DoublyLinkedList's and HashSet's
        initializeDoublys();
        initializeHashSets();
    }

    // Initializes the DoublyLinkedList's used for the cache lines
    public void initializeDoublys() {
        for (int i = 0; i < numSets; i++) {
            blocksList.add(new LinkedList<>());
        }
    }

    // Initializes the HashSets used to store the tagsList
    public void initializeHashSets() {
        for (int i = 0; i < numSets; i++) {
            tagsList.add(new HashSet<>());
        }
    }

    // Calculates the number of sets in cache
    public int calculateNumSets() {
        return cacheSize / (associativity * BLOCK_SIZE);
    }

    public int getAssociativity() {
        return associativity;
    }

    public int getNumSets() {
        return numSets;
    }

    public ArrayList<LinkedList<Block>> getBlocksList() {
        return blocksList;
    }

    public HashSet<Long> getTags(int setNumber) {
        return tagsList.get(setNumber);
    }
}
