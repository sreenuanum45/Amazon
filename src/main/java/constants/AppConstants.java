package constants;

public class AppConstants {

    // Timeouts
    public static final int EXPLICIT_WAIT_TIMEOUT = 20;
    public static final int PAGE_LOAD_TIMEOUT = 30;
    public static final int IMPLICIT_WAIT_TIMEOUT = 10;

    // Messages
    public static final String ADDED_TO_CART_MESSAGE = "Added to Cart";
    public static final String PROCEED_TO_CHECKOUT = "Proceed to checkout";

    // File Paths
    public static final String CONFIG_FILE_PATH = "src/test/resources/config/config.properties";
    public static final String TEST_DATA_FILE_PATH = "src/test/resources/testdata/testdata.json";

    // Browser Types
    public static final String CHROME = "chrome";
    public static final String FIREFOX = "firefox";
    public static final String EDGE = "edge";

    private AppConstants() {
        // Private constructor to prevent instantiation
    }
}
