package dibimbing.pages;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.pagefactory.AndroidFindBy;
import org.openqa.selenium.WebElement;

public class LoginPage extends BasePage {
  public LoginPage(AndroidDriver driver) {
    super(driver);
  }

  @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/nameET")
  private WebElement fieldUsername;

  @AndroidFindBy(id = "com.saucelabs.mydemoapp.android:id/passwordET")
  private WebElement fieldPassword;

  @AndroidFindBy(accessibility = "Tap to login with given credentials")
  private WebElement buttonLogin;

  // bod@example.com
  public void inputUsername(String username) {
    fieldUsername.sendKeys(username);
  }

  // 10203040
  public void inputPassword(String password) {
    fieldPassword.sendKeys(password);
  }

  public void clickLoginButton() {
    buttonLogin.click();
  }

  public void login(String username, String password) {
    inputUsername(username);
    inputPassword(password);
    clickLoginButton();
  }
}
