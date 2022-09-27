import java.lang.reflect.Array;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;

public class Transaction {
    public String transactionId; // this is hash of the transaction
    public PublicKey sender; // senders address/public key
    public PublicKey recipient; // recipients address/public key
    public float value; // value of coins to transfer
    public byte[] signature; // this is to prevent anybody else from spending funds in our wallet

    public ArrayList<TransactionInput> inputs = new ArrayList<>();
    public ArrayList<TransactionOutput> outputs = new ArrayList<>();

    private static int sequence = 0; // a rough count of how many transactions have been generated

    public Transaction(PublicKey from, PublicKey to, float value, ArrayList<TransactionInput> inputs) {
        this.sender = from;
        this.recipient = to;
        this.value = value;
        this.inputs = inputs;
    }

    // This calculates the transaction hash (which will be used as its ID)
    private String calculateHash() {
        sequence++; // increase  the sequence to avoid 2 identical transactions having the same hash
        return StringUtil.applySha256(
                StringUtil.getStringFromKey(sender) +
                        StringUtil.getStringFromKey(recipient) +
                        Float.toString( value ) + sequence
                );
    }

    // Signs all the data we don't wish to be tampered with
    public void generateSignature( PrivateKey privateKey) {
        String data = StringUtil.getStringFromKey( sender ) + StringUtil.getStringFromKey( recipient ) + value;
        signature = StringUtil.applyECDSASig( privateKey, data );
    }

    // Verifies the data we signed hasn't been tampered with
    public boolean verifySignature() {
        String data = StringUtil.getStringFromKey( sender ) + StringUtil.getStringFromKey( recipient ) + value;
        return StringUtil.verifyECDSASig( sender, data, signature );
    }
}
