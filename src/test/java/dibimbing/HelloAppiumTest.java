package dibimbing;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.Arrays;

public class HelloAppiumTest {
  private AndroidDriver driver;

  @BeforeClass
  public void initAppium() {
    System.out.println("Starting Appium server...");
    UiAutomator2Options options = new UiAutomator2Options()
            .setDeviceName("emulator-5554")
            .setApp(System.getProperty("user.dir") + "/apk/demo.apk")
            .setAppActivity("com.saucelabs.mydemoapp.android.view.activities.SplashActivity")
            .setAdbExecTimeout(Duration.ofSeconds(10))
            .setNewCommandTimeout(Duration.ofSeconds(10))
            .setAppWaitDuration(Duration.ofSeconds(10));

    try {
      URL appiumServerUrl = new URL("http://127.0.0.1:4723");
      driver = new AndroidDriver(appiumServerUrl, options);
      driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
      System.out.println("Appium server started successfully!");
    } catch (MalformedURLException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void test() {
    System.out.println("Creating session...");
    assert driver.getSessionId() != null;

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

    WebElement product = wait.until(
            ExpectedConditions.elementToBeClickable(
                    AppiumBy.androidUIAutomator("new UiSelector().resourceId(\"com.saucelabs.mydemoapp.android:id/productIV\").instance(0)")
            )
    );

    product.click();

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
    System.out.println("Session ID: " + driver.getSessionId() + "successfully created!");
  }

  @Test
  public void testClickByCoordinate() {
    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
    Sequence tap = new Sequence(finger, 1);

    tap.addAction(finger.createPointerMove(Duration.ofMillis(0), PointerInput.Origin.viewport(), 300, 843));
    tap.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
    tap.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));


    driver.perform(Arrays.asList(tap));

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  public void testSwipeUsingPointerInput() {
    swipeUp(5);

    try {
      Thread.sleep(5000);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  private void swipeUp(int numberOfSwipes) {
    for (int i = 0; i < numberOfSwipes; i++) {
      System.out.println("Swiping up... --> " + (i + 1));
      swipeUp();
    }
  }

  private void swipeUp() {
    Dimension size = driver.manage().window().getSize();

    // Starting point swiping
    int startX = size.getWidth() / 2; // -> 160
    int startY = size.getHeight() / 2; // -> 320

    // Target point swiping
    int endY = (int) (size.getHeight() * 0.2);

    PointerInput finger = new PointerInput(PointerInput.Kind.TOUCH, "finger");
    Sequence swipe = new Sequence(finger, 1);

    swipe.addAction(finger.createPointerMove(Duration.ofMillis(0),
        PointerInput.Origin.viewport(), startX, startY));
    swipe.addAction(finger.createPointerDown(PointerInput.MouseButton.LEFT.asArg()));
    swipe.addAction(finger.createPointerMove(Duration.ofMillis(800),
        PointerInput.Origin.viewport(), startX, endY));
    swipe.addAction(finger.createPointerUp(PointerInput.MouseButton.LEFT.asArg()));
    driver.perform(Arrays.asList(swipe));
  }


  @AfterClass
  public void tearDownAppium() {
    if (driver != null) {
      driver.quit();
      System.out.println("Appium server stopped successfully!");
    }
  }
}
