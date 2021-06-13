package net.kamradtfamily.blockchain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Collections;
import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Block {
    static ObjectMapper mapper = new ObjectMapper();
    public static Block genesis = Block.builder()
            .index(0)
            .previousHash("0")
            .timestamp(Instant.now().getEpochSecond())
            .nonce(0)
            .transactions(Collections.emptyList())
            .build()
            .withHash();
    private long index;
    private String previousHash;
    private long timestamp;
    private long nonce;
    private String hash;
    private List<Transaction> transactions;

    public Block withHash() {
        hash = toHash();
        return this;
    }
    public String toHash() {
        try {
            return CryptoUtil.hash(this.index + this.previousHash + this.timestamp + mapper.writeValueAsString(this.transactions) + this.nonce);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Json decoding error", e);
        }
    }

    public long calculateDifficulty() {
        return -1l;
    }
}
