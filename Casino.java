import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Casino {

    private List<String> matchData;
    private List<String> playerOperations;
    private List<Match> matches;
    private List<Player> players;

    private long casinoBalance;

    public Casino(String playerDataFileName, String matchDataFileName) {
        this.matchData = readLines(matchDataFileName);
        this.playerOperations = readLines(playerDataFileName);
        this.matches = new ArrayList<>();
        this.players = new ArrayList<>();
        casinoBalance = 0;
    }

    public void verifyMatches() throws IOException {

        for (String matchDataRow : matchData) {
            var matchParts = matchDataRow.split(",");
            var match = new Match(matchParts[0], Double.parseDouble(matchParts[1]), Double.parseDouble(matchParts[2]), matchParts[3]);
            matches.add(match);
        }

        for (String playerOperationRow : playerOperations) {
            var playerDataParts = playerOperationRow.split(",", 5);
            var playerId = playerDataParts[0];
            var playerOperation = playerDataParts[1];
            var matchId = playerDataParts[2];
            var amount = playerDataParts[3];
            var chosenSide = playerDataParts[4];

            if (!playerExists(playerId)) {
                var player = new Player(playerId);
                players.add(player);
            }

            var existingPlayer = getPlayer(playerId);
            var operation = new PlayerOperation(playerId, Operation.valueOf(playerOperation), matchId, Integer.parseInt(amount), chosenSide);
            switch (operation.getOperation()) {
                case BET: {
                    if (existingPlayer.getBalance() < operation.getAmount()) {
                        existingPlayer.addIllegalOperation(operation);
                        break;
                    }

                    var match = getMatch(matchId);

                    var matchResult = match.getMatchResult();

                    if (matchResult.equals("DRAW")) {
                        existingPlayer.addMatchPlayed();
                        break;
                    }

                    var didPlayerWin = matchResult.equals(operation.getSide());
                    if (didPlayerWin) {
                        var isSideA = operation.getSide().equals("A");
                        var returnRate = isSideA ? match.getReturnRateA() : match.getReturnRateB();
                        var winnings = Math.floor(operation.getAmount() * returnRate);
                        existingPlayer.addBalance((int) winnings);
                        casinoBalance -= (long) winnings;
                        existingPlayer.addWonMatch();
                        existingPlayer.addMatchPlayed();
                        existingPlayer.addWinning((int) winnings);
                    } else {
                        casinoBalance += operation.getAmount();
                        existingPlayer.removeBalance(operation.getAmount());
                        existingPlayer.addWinning(-operation.getAmount());
                        existingPlayer.addMatchPlayed();
                    }

                    break;
                }
                case DEPOSIT: {
                    existingPlayer.addBalance(operation.getAmount());
                    break;
                }
                case WITHDRAW: {
                    if (existingPlayer.getBalance() < operation.getAmount()) {
                        existingPlayer.addIllegalOperation(operation);
                        break;
                    }

                    existingPlayer.removeBalance(operation.getAmount());

                    break;
                }
            }

            updatePlayer(existingPlayer);

        }

        var illegalPlayers = players.stream().filter(player -> player.getIllegalOperations().size() > 0).sorted(Comparator.comparing(Player::getPlayerId)).toList();
        illegalPlayers.forEach(player -> casinoBalance += player.getTotalWinnings());
        var legitimatePlayers = players.stream().filter(player -> player.getIllegalOperations().size() == 0).sorted(Comparator.comparing(Player::getPlayerId)).toList();

        PrintWriter writer = new PrintWriter("src/results.txt", StandardCharsets.UTF_8);
        legitimatePlayers.forEach(player -> writer.println(player.toString()));
        writer.println();
        illegalPlayers.forEach(player -> writer.println(player.toString()));
        if(illegalPlayers.size() == 0){
            writer.println();
        }
        writer.println();
        writer.println(casinoBalance);
        writer.close();

    }

    private void updatePlayer(Player player) {
        players.set(indexOfPlayer(player.getPlayerId()), player);
    }

    private int indexOfPlayer(String id) {
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            if (player.getPlayerId().equals(id)) {
                return i;
            }
        }

        return -1;
    }

    private Player getPlayer(String id) {
        for (Player player : players) {
            if (player.getPlayerId().equals(id)) {
                return player;
            }
        }

        return null;
    }

    private Match getMatch(String id) {
        for (Match match : matches) {
            if (match.getMatchId().equals(id)) {
                return match;
            }
        }

        return null;
    }


    private boolean playerExists(String id) {
        for (Player player : players) {
            if (player.getPlayerId().equals(id)) {
                return true;
            }
        }

        return false;
    }

    private List<String> readLines(String fileName) {
        InputStream is = getClass().getResourceAsStream(fileName);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        return br.lines().toList();
    }
}

