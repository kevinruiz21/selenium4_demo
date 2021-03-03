package tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v86.emulation.Emulation;
import org.openqa.selenium.devtools.v86.log.Log;
import org.openqa.selenium.devtools.v86.network.Network;
import org.openqa.selenium.devtools.v86.security.Security;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.openqa.selenium.support.locators.RelativeLocator.withTagName;

public class Demo {

    protected ChromeDriver driver;

//        Edge
//    protected EdgeDriver driver;

    @Before
    public void setup() {
//        Chrome
        System.setProperty("webdriver.chrome.driver", "chromedriver ");
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

        System.out.println("breakpoint");
    }

    @Test
    public void relativeLocators() {
        driver.get("https://www.phptravels.net/login");
        // Search for an object
        WebElement username = driver.findElement(By.xpath("//*[@name='username']"));
        username.sendKeys("thisisthe@email.com");

        WebElement belowInput = driver.findElement(withTagName("input").below(username));
        belowInput.sendKeys("andthisisthepassword");

        System.out.println("breakpoint");
    }

    @Test
    public void takeScreenshot() throws IOException {
        driver.get("https://www.phptravels.net/login");
        // Search for an object
        WebElement username = driver.findElement(By.xpath("//*[@name='username']"));
        username.sendKeys("thisisthe@email.com");

        try {
            File source = ((TakesScreenshot) username).getScreenshotAs(OutputType.FILE);
            FileHandler.copy(source, new File("src/screenshots/element.png"));

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void webToMobileView() {

        DevTools devTools = driver.getDevTools();
        devTools.createSession();
        Map deviceMetrics = new HashMap() {{
            put("width", 600);
            put("height", 1000);
            put("mobile", true);
            put("deviceScaleFactor", 50);
        }};
        driver.executeCdpCommand("Emulation.setDeviceMetricsOverride", deviceMetrics);
        driver.get("https://www.google.com");

        System.out.println("breakpoint");
    }

    @Test
    public void mockGeoLocation() {

        DevTools devTools = driver.getDevTools();
        devTools.createSession();
        devTools.send(Emulation.setGeolocationOverride(
                Optional.of(35.8235),
                Optional.of(-78.8256),
                Optional.of(100)));
        driver.get("https://mycurrentlocation.net/");

        System.out.println("breakpoint");
    }

    @Test
    public void consoleLogs() {

        DevTools devTools = driver.getDevTools();
        devTools.createSession();
        devTools.send(Log.enable());
        devTools.addListener(Log.entryAdded(),
                logEntry -> {
                    System.out.println("log: " + logEntry.getText());
                    System.out.println("level: " + logEntry.getLevel());
                });
        driver.get("https://testersplayground.herokuapp.com/console-5d63b2b2-3822-4a01-8197-acd8aa7e1343.php");
    }

    @Test
    public void loadInsecureWebSite() {

        DevTools devTools = driver.getDevTools();
        devTools.createSession();
        devTools.send(Security.enable());
        devTools.send(Security.setIgnoreCertificateErrors(true));
        driver.get("https://expired.badssl.com/");

        System.out.println("breakpoint");
    }


    @Test
    public void captureNetwork() {

        DevTools devTools = driver.getDevTools();
        devTools.createSession();
        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        devTools.addListener(Network.requestWillBeSent(),
                entry -> {
                    System.out.println("Request URI : " + entry.getRequest().getUrl()+"\n"
                            + " With method : "+entry.getRequest().getMethod() + "\n");
                    entry.getRequest().getMethod();
                });
        driver.get("https://www.google.com");
        devTools.send(Network.disable());

        System.out.println("breakpoint");
    }


}
