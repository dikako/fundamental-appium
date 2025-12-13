package dibimbing;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class HelloAppiumTest {
  private AndroidDriver driver;

  @BeforeClass
  public void initAppium() {
    System.out.println("Starting Appium server...");
    UiAutomator2Options options = new UiAutomator2Options()
            .setDeviceName("emulator-5554")
            .setApp(System.getProperty("user.dir") + "/apk/demo.apk");

    try {
      URL appiumServerUrl = new URL("http://127.0.0.1:4723");
      driver = new AndroidDriver(appiumServerUrl, options);
      System.out.println("Appium server started successfully!");
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void test() {
    System.out.println("Creating session...");
    assert driver.getSessionId() != null;
    System.out.println("Session ID: " + driver.getSessionId() + "successfully created!");
  }

  @AfterClass
  public void tearDownAppium() {
    if (driver != null) {
      driver.quit();
      System.out.println("Appium server stopped successfully!");
    }
  }
}
