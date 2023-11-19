import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String matchDataFilename = "match_data.txt";
        String playerDataFilename = "player_data.txt";

        new Casino(playerDataFilename, matchDataFilename).verifyMatches();
    }
}
