package tests;

import com.microsoft.edge.seleniumtools.EdgeDriverService;
import com.microsoft.edge.seleniumtools.EdgeOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.IOException;

import static org.openqa.selenium.support.locators.RelativeLocator.withTagName;

public class Demo {

    protected WebDriver driver;

    @Before
    public void setup() {
//        Chrome
        System.setProperty("webdriver.chrome.driver","chromedriver ");
        driver = new ChromeDriver();

//        Edge
//        driver = new EdgeDriver();
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void openNewWindow() {
        driver.get("https://www.google.com/");
        // Opens a new window and switches to new window
        driver.switchTo().newWindow(WindowType.TAB);
        // Opens LambdaTest homepage in the newly opened window
        driver.navigate().to("https://www.lambdatest.com/");
    }

    @Test
    public void relativeLocators() {
        driver.get("https://www.phptravels.net/login");
        // Search for an object
        WebElement username = driver.findElement(By.xpath("//*[@name='username']"));
        username.sendKeys("thisisthe@email.com");

        WebElement belowInput = driver.findElement(withTagName("input").below(username));
        belowInput.sendKeys("andthisisthepassword");

        driver.findElement(By.xpath("//*[@name='password']"));
    }

    @Test
    public void takeScreenshot() throws IOException {
        driver.get("https://www.phptravels.net/login");
        // Search for an object
        WebElement username = driver.findElement(By.xpath("//*[@name='username']"));
        username.sendKeys("thisisthe@email.com");

        try {
        File source = ((TakesScreenshot)username).getScreenshotAs(OutputType.FILE);
        FileHandler.copy(source, new File("src/screenshots/element.png"));

        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

}
