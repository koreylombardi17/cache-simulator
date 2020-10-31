import java.math.BigInteger;
import java.util.List;

public class Simulation {

	// Static variables used in CacheController
    static int replacementPolicy;
    static int writeBackPolicy;
    static long endTime = 0;

    // Inject CacheController
    private CacheController cacheController;

    public Simulation(int cacheSize, int associativity, int theReplacementPolicy, int theWriteBackPolicy) {
        this.cacheController = new CacheController(new Cache(cacheSize, associativity));
        replacementPolicy = theReplacementPolicy;
        writeBackPolicy = theWriteBackPolicy;
    }

    // Calls read function inside CacheController
    public void read(int setNumber, long tag) {
        this.cacheController.read(setNumber, tag);
    }

    // Calls write function inside CacheController
    public void write(int setNumber, long tag) {
        this.cacheController.write(setNumber, tag);
    }

    // Used to execute the read and write operations stored in ArrayList
    public void execute(List<String> commands) {
        for (String command : commands) {
            executeSingleCommand(command);
        }
        printResults();
    }

    // Function used to execute a single command
    public void executeSingleCommand(String command) {
    	// First character of each command will be 'W' or 'R'							
        char instruction = command.charAt(0);	  // Below, chars 0-3 get removed from command 
        										  //   0123
        // Masking the command to get the addresss eg.(R 0x12345678)
        String addressStr = command.substring(4);
        
        // BigInteger is used to handle large address that long cannot handle
        BigInteger address = new BigInteger(addressStr, 16);

        // remove offset
        BigInteger remainingBits = address.divide(BigInteger.valueOf(Cache.BLOCK_SIZE));

        // calculate set number
        BigInteger setBig = remainingBits.mod(BigInteger.valueOf(cacheController.getNumSets()));
        int set = setBig.intValue();

        // calculate tag
        BigInteger tagBig = remainingBits.divide(BigInteger.valueOf(cacheController.getNumSets()));
        long tag = tagBig.longValue();

        if (instruction == 'R') {
            read(set, tag);
        }
        else if (instruction == 'W') {
            write(set, tag);
        }
    }
    
    // Print data using float data type
    public void printResults() {
        System.out.println("Miss rate =\t\t" + String.format("%.6f", calculateMissRatio(CacheController.misses, CacheController.hits)));
        System.out.println("Write to memory =\t" + String.format("%.6f", (float)CacheController.writes));
        System.out.println("Reads from memory =\t" + String.format("%.6f", (float)CacheController.reads));
        endTime = System.currentTimeMillis();
    }

    // Formula to calculate miss ratio
    public float calculateMissRatio(int misses, int hits) {
        float total = misses + hits;
        float ratio = misses / total;
        return ratio;
    }
    
    public long getEndTime() {
        return endTime;
    }

}
