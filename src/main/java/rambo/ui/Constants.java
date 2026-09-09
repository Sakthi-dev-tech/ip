package rambo.ui;

/**
 * Constants used throughout the Rambo application.
 * Provides ANSI color codes and divider strings for UI formatting.
 */
public final class Constants {
    /** ANSI green color code for terminal text highlighting. */
    public static final String ANSI_GREEN = "\u001B[32m";
    /** ANSI red color code for terminal text highlighting. */
    public static final String ANSI_RED = "\033[91m";
    /** ANSI reset color code to restore default terminal text color. */
    public static final String ANSI_RESET = "\u001B[0m";

    private static final String DIVIDER = "===========================================================";

    private Constants() {
    }

    /**
     * Prints a divider that visually separates sections.
     */
    public static void divider() {
        System.out.println("\n");
        System.out.println(DIVIDER);
        System.out.println("\n");
    }

    /**
     * Prints a divider labelled with the supplied application name.
     *
     * @param appName name displayed in the divider
     */
    public static void divider(String appName) {
        System.out.println("\n");
        System.out.println(String.format("============================ %s ==============================", appName));
        System.out.println("\n");
    }
}
