import com.google.gson.GsonBuilder;

import java.util.ArrayList;

public class MainClass {

    public static ArrayList<Block> blockchain = new ArrayList<>();
    public static int difficulty = 5;

    public static void main(String[] args) {
        blockchain.add( new Block("Chancellor on brink of second bailout for banks", "0") );
        System.out.println("Mining block 1 . . .");
        blockchain.get(0).mineBlock( difficulty );

        blockchain.add( new Block("Second block", blockchain.get(blockchain.size() - 1).hash) );
        System.out.println("Mining block 2 . . .");
        blockchain.get(1).mineBlock( difficulty );

        blockchain.add( new Block( "Third block", blockchain.get(blockchain.size() - 1).hash) );
        System.out.println("Mining block 3 . . .");
        blockchain.get(2).mineBlock( difficulty );

        System.out.println("\nBlockchain is valid: " + isChainValid());

        String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson( blockchain );
        System.out.println("\nThe block chain: ");
        System.out.println(blockchainJson);
    }

    public static Boolean isChainValid() {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace( '\0', '0' );

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

            // check if hash is solved
            if(!currentBlock.hash.substring( 0, difficulty ).equals( hashTarget )) {
                System.out.println("This block hasn't been mined");
                return false;
            }
        }

        return true;
    }
}
