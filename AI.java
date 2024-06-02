import java.util.Random;

public class AI extends Player {
    private Random random = new Random();

    @Override
    public void play() {
        int diceRoll = random.nextInt(6) + 1; // Roll between 1 and 6
        addToScore(diceRoll);
    }
}
