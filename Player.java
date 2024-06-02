public abstract class Player {
    protected int score;

    public Player() {
        this.score = 0;
    }

    public int getScore() {
        return score;
    }

    public void addToScore(int points) {
        this.score += points;
    }

    public abstract void play();
}
