import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Player {

    private String playerId;
    private List<PlayerOperation> illegalOperations;
    private long balance;
    private int matchesWon;
    private int matchesTotal;

    private List<Integer> winnings;


    public Player(String playerId) {
        this.playerId = playerId;
        this.illegalOperations = new ArrayList<>();
        this.balance = 0;
        this.matchesWon = 0;
        this.matchesTotal = 0;
        this.winnings = new ArrayList<>();
    }

    public String getPlayerId() {
        return playerId;
    }

    public List<PlayerOperation> getIllegalOperations() {
        return illegalOperations;
    }

    public void addIllegalOperation(PlayerOperation illegalOperation) {
        this.illegalOperations.add(illegalOperation);
    }

    public long getBalance() {
        return this.balance;
    }

    public void addBalance(int balance) {
        this.balance += balance;
    }

    public void addWinning(int winning) {
        this.winnings.add(winning);
    }
    public long getTotalWinnings() {
        var wonAmount = 0;
        for (Integer winnings : this.winnings) {
            wonAmount += winnings;
        }
        return wonAmount;
    }

    public void removeBalance(int balance) {
        this.balance -= balance;
    }

    public void addWonMatch() {
        this.matchesWon += 1;
    }

    public void addMatchPlayed() {
        this.matchesTotal += 1;
    }

    @Override
    public String toString() {
        boolean isCheatingPlayer = illegalOperations.size() > 0;
        if (isCheatingPlayer) {
            return playerId + " " + illegalOperations.get(0).toString();
        } else {
            double rate = (double) matchesWon / (double) matchesTotal;
            var winRate = new BigDecimal(rate).setScale(2, BigDecimal.ROUND_HALF_EVEN);
            return playerId + " " + balance + " " + winRate;
        }
    }
}

