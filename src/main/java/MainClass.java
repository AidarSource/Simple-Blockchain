import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class MainClass {

    public static ArrayList<Block> blockchain = new ArrayList<>();

    public static void main(String[] args) {
        Block genesisBlock = new Block("Chancellor on brink of second bailout for banks", "0");
        Block secondBlock = new Block("Second block", genesisBlock.hash);
        Block thirdBlock = new Block( "Third block", secondBlock.hash );

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson( blockchain );
        System.out.println(blockchainJson);
    }

    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;

        // looping through blockchain to check hashes
        for(int i = 1; i < blockchain.size(); i++) {
            currentBlock = blockchain.get(i);
            previousBlock = blockchain.get(i - 1);

            // compare registered hash and calculated hash
            if(!currentBlock.hash.equals( currentBlock.calculateHash() )) {
                System.out.println("Current hashes not equal");
                return false;
            }

            // compare previous hash and registered previous hash
            if(!previousBlock.hash.equals( currentBlock.prevHash )) {
                System.out.println("Previous hashes not equal");
                return false;
            }
        }

        return true;
    }
}
