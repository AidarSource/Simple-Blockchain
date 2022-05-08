import java.util.Date;

public class Block {
    public String hash;
    public String prevHash;
    private String data;
    private long timestamp;

    public Block( String prevHash, String data ) {
        this.prevHash = prevHash;
        this.data = data;
        this.timestamp = new Date().getTime();
        this.hash = calculateHash();
    }

    public String calculateHash() {
        String calculatedHash = StringUtil.applySha256(
                prevHash + Long.toString( timestamp ) + data
        );

        return calculatedHash;
    }
}
