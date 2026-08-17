import java.util.Scanner;
import constants.*;

public class Echo {
  private static void intro() {
    Constants.divider();
    System.out.println("Welcome to Echo!");
    System.out.println("Here I will echo whatever you tell me!");
    System.out.println("Enter /exit to go back!");
    Constants.divider();
  }

  /*
   * This would be the main loop for the echo function
   */
  public static void start(Scanner scanner) {
    Constants.divider("Echo");
    intro();
    while (true) {
      System.out.print("You: ");
      String userText = scanner.nextLine();

      // If I am exiting, I would like to break the loop
      if (userText.equals("/exit")) {
        System.out.println("Back to home!");
        break;
      }

      System.out.print("Rambo: ");
      System.out.println(userText);
    }

  }
}
