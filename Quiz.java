import java.util.Scanner;
import java.util.concurrent.*;

public class Quiz {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String[] questions = {
            "In which year did India gain independence?",
            "What is the boiling point of water?",
            "Which country is known as the Land of the Rising Sun?"
        };

        String[][] options = {
            {"1. 1994", "2. 1947", "3. 1942", "4. 1950"},
            {"1. 100°C or 216°F", "2. 100°C or 212°F", "3. 100°C or 215°F", "4. 100°C or 200°F"},
            {"1. India", "2. Hesse", "3. Japan", "4. Monaco"}
        };

        int[] answers = {2, 2, 3};

        int score = 0;

        ExecutorService executor = Executors.newSingleThreadExecutor();

        for (int i = 0; i < questions.length; i++) {

            System.out.println("\nQ" + (i + 1) + ": " + questions[i]);

            for (String option : options[i]) {
                System.out.println(option);
            }

            System.out.print("Enter your answer (1-4): ");

            Callable<Integer> input = () -> {
                return sc.nextInt();
            };

            Future<Integer> future = executor.submit(input);

            try {
                int answer = future.get(10, TimeUnit.SECONDS);

                if (answer == answers[i]) {
                    System.out.println("Correct!");
                    score++;
                } else {
                    System.out.println("Wrong!");
                }

            } catch (TimeoutException e) {
                System.out.println("Time's up!");
                future.cancel(true);

            } catch (Exception e) {
                System.out.println("Invalid input.");
                future.cancel(true);
            }
        }

        executor.shutdownNow();

        System.out.println("\nYour Score: " + score + "/" + questions.length);

        sc.close();
    }
}
