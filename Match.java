public class Match {
    private String matchId;
    private double returnRateA;
    private double returnRateB;
    private String matchResult;

    @Override
    public String toString() {
        return "Match{" +
                "matchId='" + matchId + '\'' +
                ", returnRateA=" + returnRateA +
                ", returnRateB=" + returnRateB +
                ", matchResult='" + matchResult + '\'' +
                '}';
    }

    public Match(String matchId, double returnRateA, double returnRateB, String matchResult) {
        this.matchId = matchId;
        this.returnRateA = returnRateA;
        this.returnRateB = returnRateB;
        this.matchResult = matchResult;
    }

    public String getMatchId() {
        return matchId;
    }

    public double getReturnRateA() {
        return returnRateA;
    }

    public double getReturnRateB() {
        return returnRateB;
    }

    public String getMatchResult() {
        return matchResult;
    }
}