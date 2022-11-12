import com.google.gson.GsonBuilder;

import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;

public class MainClass {

    public static ArrayList<Block> blockchain = new ArrayList<>();
    public static int difficulty = 5;

    public static HashMap<String, TransactionOutput> UTXOs = new HashMap<>(); // list of all unspent transactions

    public static Wallet walletA;
    public static Wallet walletB;


    public static void main(String[] args) {
        // Setup Bouncy castle as a Security Provider
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        // Create the wallets
        walletA = new Wallet();
        walletB = new Wallet();

        // Test public and private keys
        System.out.println("Private and public keys:");
        System.out.println(StringUtil.getStringFromKey( walletA.privateKey ));
        System.out.println(StringUtil.getStringFromKey( walletB.publicKey ));

        // Create a test transaction from WalletA to WalletB
        Transaction transaction = new Transaction( walletA.publicKey, walletB.publicKey, 5, null );
        transaction.generateSignature( walletA.privateKey );

        // Verify the signature works and verify it from the public key
        System.out.println("Is signature verified");
        System.out.println(transaction.verifySignature());

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
