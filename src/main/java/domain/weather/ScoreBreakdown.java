package domain.weather;

public class ScoreBreakdown {
    public int tempScore = 0;
    public int rainScore = 0;
    public int snowScore = 0;
    public int windScore = 0;

    public int total() {
        return tempScore + rainScore + snowScore + windScore;
    }
}
