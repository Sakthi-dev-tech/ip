package rambo;

import rambo.ui.Ui;

public class Echo {
  private static void intro(Ui ui) {
    ui.showDivider();
    ui.showLine("Welcome to Echo!");
    ui.showLine("Here I will echo whatever you tell me!");
    ui.showLine("Enter /exit to go back!");
    ui.showDivider();
  }

  /*
   * This would be the main loop for the echo function
   */
  public static void start(Ui ui) {
    ui.showDivider("Echo");
    intro(ui);
    while (true) {
      ui.showPrompt("You: ");
      String userText = ui.readLine();

      // If I am exiting, I would like to break the loop
      if (userText.equals("/exit")) {
        ui.showLine("Back to home!");
        break;
      }

      ui.showPrompt("Rambo: ");
      ui.showLine(userText);
    }

  }
}
