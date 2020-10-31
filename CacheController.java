import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

public class CacheController {

	// Constants used for write-back policy and replacement policy
    private final int LRU = 0;
    private final int WRITE_BACK = 1;

    // Static variables used to keep track of data 
    static int hits = 0;
    static int misses = 0;
    static int writes = 0;
    static int reads = 0;

    // Inject Cache
    private Cache cache;
 
    // Used to store all of the Doubly LinkedList's
    private ArrayList<LinkedList<Block>> setsList;
    
    // Doubly LinkedList used since insertion and deletion at beginning or end of list has a Big-O(1)
    private LinkedList<Block> set;
 
    // HashSet used since search has a Big-O(1)
    private HashSet<Long> tags;

    public CacheController(Cache theCache) {
        cache = theCache;
        setsList = cache.getBlocksList();
    }

    // Function to read from cache
    public void read(int setNumber, long tag) {
        set = setsList.get(setNumber);
        tags = cache.getTags(setNumber);

        // Cache miss, add to cache and increment misses and reads
        if (!tags.contains(tag)) {
            misses++;
            reads++;
            Block block = new Block(tag);
            add(block, setNumber);
        }
        // Cache hit, increment hits
        else {
            hits++;
            
            //If LRU replacement policy move to head of LinkedList
            if (Simulation.replacementPolicy == LRU) {
                moveToHead(setNumber, tag);
            }
        }
    }

    // Function to write from cache
    public void write(int setNumber, long tag) {
        set = setsList.get(setNumber);
        tags = cache.getTags(setNumber);
        Block block;

        // Cache miss, add to cache and increment misses and reads
        if (!tags.contains(tag)) {
            block = new Block(tag);

            // If write-back, mark bit as dirty, else increment writes
            if (Simulation.writeBackPolicy == WRITE_BACK) {
                block.setDirtyBit(true);
            }
             else {
                writes++;
            }

            add(block, setNumber);
            misses++;
            reads++;
        }
        // Cache hit, increment hits
        else {
            block = getBlock(set, tag);
            hits++;

            // If write-back, mark bit as dirty, else increment writes
            if (Simulation.writeBackPolicy == WRITE_BACK) {
                block.setDirtyBit(true);
            }
            else {
                writes++;
            }

            // If LRU replacement policy, move to head of LinkedList
            if (Simulation.replacementPolicy == LRU) {
                moveToHead(setNumber, tag);
            }
        }
    }

    // Method used to add a clock to cache
    public void add(Block block, int setNumber) {
        set = setsList.get(setNumber);
        tags = cache.getTags(setNumber);

        // If set is full, take care of eviction
        // Add block to LinkedList, add tag to HashSet for both cases
        if (!isSetFull(setNumber)) {
            set.addFirst(block);
            tags.add(block.getTag());
        } else {
            evict(set, tags);
            set.addFirst(block);
            tags.add(block.getTag());
        }
    }

    // Function used for evicting cache block, evicts last block in the LinkedList
    public void evict(LinkedList<Block> blocks, HashSet<Long> tags) {
        Block block = blocks.removeLast();
        long tag = block.getTag();
        tags.remove(tag);

        // Check for dirty bit, increment writes if dirty
        if (block.isDirtyBit()) {
            writes++;
        }
    }

    // Function used to check if cache set is full
    // Used to determine whether or not to evict a cache block before making insertion 
    public boolean isSetFull(int setNumber) {
        if (setsList.get(setNumber).size() == cache.getAssociativity()) {
            return true;
        }
        return false;
    }

    // Big-O(n)
    // Function used to get a block
    public Block getBlock(LinkedList<Block> set, long tag) {
        for (Block block : set) {
            if (block.getTag() == tag) {
                return block;
            }
        }
        return null;
    }

    // Function used to implement LRU replacement policy
    // Takes a block and moves it to the beginning of LinkedList
    public void moveToHead(int setNumber, long tag) {
        set = setsList.get(setNumber);
        
        // Case when block is already first element in the LinkedList
        if (set.getFirst().getTag() != tag) {
            for (Block block : set) {
                if (block.getTag() == tag) {
                    set.remove(block);
                    set.addFirst(block);
                    return;
                }
            }
        }
    }

    // Function returns the number of cache sets
    public int getNumSets() {
        return cache.getNumSets();
    }
}
