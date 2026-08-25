package constants;

/**
 * Constants used throughout the Rambo application.
 * Provides ANSI color codes and divider strings for UI formatting.
 */
public class Constants {
  /**
   * A simple divider to clearly define different sections.
   */
  public static void divider() {
    System.out.println("\n");
    System.out.println("===========================================================");
    System.out.println("\n");
  }

  public static void divider(String appName) {
    System.out.println("\n");
    System.out.println(String.format("============================ %s ==============================", appName));
    System.out.println("\n");
  }

  /**
   * ANSI green color code for terminal text highlighting.
   */
  public static final String ANSI_GREEN = "\u001B[32m";
  /**
   * ANSI red color code for terminal text highlighting.
   */
  public static final String ANSI_RED = "\033[91m";
  /**
   * ANSI reset color code to restore default terminal text color.
   */
  public static final String ANSI_RESET = "\u001B[0m";
}
