public class PlayerOperation {
    private String playerId;
    private Operation operation;
    private String matchId;
    private int amount;
    private String side;

    public PlayerOperation(String playerId, Operation operation, String matchId, int amount, String side) {
        this.playerId = playerId;
        this.operation = operation;
        this.matchId = matchId;
        this.amount = amount;
        this.side = side;
    }

    public Operation getOperation() {
        return operation;
    }

    public int getAmount() {
        return amount;
    }

    public String getSide() {
        return side;
    }

    @Override
    public String toString() {
        var matchDisplay = matchId.length() == 0 ? "null" : matchId;
        var sideDisplay = side.length() == 0 ? "null" : side;
        return operation.name() + " " + matchDisplay + " " + getAmount() + " " + sideDisplay;
    }
}
