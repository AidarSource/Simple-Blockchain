
public class TransactionInput {
    public String transactionOutputId; // Reference to TransactionOutputs -> transactionId
    public TransactionOutput UTXO; // Contains unspent transaction output

    public TransactionInput(String transactionOutputId) {
        this.transactionOutputId = transactionOutputId;
    }
}
