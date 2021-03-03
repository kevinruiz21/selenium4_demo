package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.openqa.selenium.support.locators.RelativeLocator.withTagName;

public class Demo {

    protected WebDriver driver;

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver","chromedriver ") ;
        driver = new ChromeDriver();
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

        WebElement belowInput = driver.findElement(withTagName("input").above(username));
        belowInput.sendKeys("andthisisthepassword");

        driver.findElement(By.xpath("//*[@name='password']"));
    }

}
