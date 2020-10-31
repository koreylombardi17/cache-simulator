import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {

	static long startTime = 0;
	
    public static void main(String[] args) {

    	startTime = System.currentTimeMillis();
    	
    	// Get arguments from command line
        int cacheSize = Integer.parseInt(args[0]);
        int associativity = Integer.parseInt(args[1]);
        int replacementPolicy = Integer.parseInt(args[2]);
        int writeBackPolicy = Integer.parseInt(args[3]);

        // ArrayList used to store the read and write operations
        List<String> commands = new ArrayList<>();

        // BufferedReader used to read the input file
        BufferedReader br = null;
        try {
            // Get command from the user at command line
            br = new BufferedReader(new FileReader(args[4]));
            String text = br.readLine();

            // Loop until BufferedReader reaches null, populate list with read and write commands 
            while (text != null) {
                commands.add(text);
                text = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            // Close down the BufferedReader
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ioe) {
                // Error message
                System.out.println("BufferedReader failed to close");
            }
        }
        // Create simulation object 
        // Simulations sets up the cache then begins executing read and write commands 
        Simulation simulation = new Simulation(cacheSize, associativity, replacementPolicy, writeBackPolicy);
        simulation.execute(commands);
        
        // Used to get the execution time of program
        System.out.println("Execution time in ms =\t" + (simulation.getEndTime() - startTime));
    }
}
