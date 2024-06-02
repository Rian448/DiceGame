import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DiceGame {
    String FILE_PATH = "C:\\Code\\WinRecord.txt";

    public static void main(String[] args) {
        DiceGame diceGame = new DiceGame();
        diceGame.start();
    }

    private void start() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to the Dice Game! Do you want to play? (yes/no)");
            String input = scanner.nextLine();

            if (input.equalsIgnoreCase("yes")) {
                playGame();
                break; // Exit the loop if the user wants to play
            } else if (input.equalsIgnoreCase("no")) {
                System.out.println("Okay, maybe next time. Goodbye!");
                break; // Exit the loop if the user doesn't want to play
            } else {
                System.out.println("Invalid input. Please enter 'yes' or 'no'.");
            }
        }

        scanner.close();
    }

    private void playGame() {
        User user = new User();
        AI ai = new AI();
        Scanner scanner = new Scanner(System.in);

        int userWins = 0;
        int aiWins = 0;

        try {
            // Read existing win counts from the file
            try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
                String line = reader.readLine();
                if (line != null) {
                    String[] wins = line.split(" ");
                    userWins = Integer.parseInt(wins[0]);
                    aiWins = Integer.parseInt(wins[1]);
                }
            } catch (IOException e) {
                System.out.println("Could not read win counts. Starting fresh.");
            }

            while (true) {
                System.out.println("Your current score: " + user.getScore());

                System.out.println("Do you want to roll (yes/no)");
                String input = scanner.nextLine();

                if (input.equalsIgnoreCase("no")) {
                    break;
                } else if (input.equalsIgnoreCase("yes")) {
                    System.out.println("---------------");
                    user.play();
                    ai.play();    
                    continue;
                } else {
                    System.out.println("---------------");
                    System.out.println("Enter only 'yes' or 'no'");
                }
            }

            System.out.println("Game ended.");
            System.out.println("Final scores:");
            System.out.println("Your score: " + user.getScore());
            System.out.println("AI's score: " + ai.getScore());

            String result = determineWinner(user.getScore(), ai.getScore());
            if (result.equals("playerW")) {
                System.out.println("You win!");
                userWins++;
            } else if (result.equals("aiW")) {
                System.out.println("AI wins!");
                aiWins++;
            } else {
                System.out.println("It's a tie!");
            }

            // Save updated win counts to the file
            try (FileWriter writer = new FileWriter(FILE_PATH)) {
                writer.write(userWins + " " + aiWins);
            } catch (IOException e) {
                System.out.println("Could not save win counts.");
            }

            // Display win counts
            System.out.println("Total wins:");
            System.out.println("You: " + userWins);
            System.out.println("AI: " + aiWins);
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private String determineWinner(int userScore, int aiScore) {
        if (userScore > 21 && aiScore > 21) {
            return "tie"; // Tie
        } else {
            if (userScore <= 21 && aiScore <= 21) {
                int userDifference = 21 - userScore;
                int aiDifference = 21 - aiScore;

                if (userDifference < aiDifference) {
                    return "playerW"; // User wins
                } else if (aiDifference < userDifference) {
                    return "aiW"; // AI wins
                } else {
                    return "tie"; // Tie
                }
            } else if (userScore > 21) {
                return "aiW"; // AI wins
            } else {
                return "playerW"; // User wins
            }
        }
    }
}
