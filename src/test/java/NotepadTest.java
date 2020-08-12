import io.appium.java_client.windows.WindowsDriver;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;

import java.net.URL;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit;

public class NotepadTest {
    private static WindowsDriver notePadSession = null;

    public static String getDate() {
        LocalDate date = LocalDate.now();
        return date.toString();
    }

    /*
    "platformName" and "deviceName" properties are not necessary
    if you are performing tests using WinAppDriver.
    Those are only needed if you use Appium to clarify which platform
    we are using for tests.
     */
    @BeforeClass
    public static void setUp() {
        try {
            DesiredCapabilities capabilities = new DesiredCapabilities();
            capabilities.setCapability("app", "C:\\Windows\\system32\\notepad.exe");
            capabilities.setCapability("platformName", "Windows");
            capabilities.setCapability("deviceName", "WindowsPC");
            notePadSession = new WindowsDriver(new URL("http://127.0.0.1:4723/"), capabilities);
            notePadSession.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @AfterMethod
    public void cleanApp() {
        notePadSession.quit();
        setUp();
    }

    @AfterSuite
    public void tearDown() {
        notePadSession.quit();
    }

    @Test
    public void checkAboutWindow() {
        notePadSession.findElementByName("Help").click();
        notePadSession.findElementByName("About Notepad").click();
        notePadSession.findElementByName("OK").click();
    }

    @Test
    public void sendTestText(){
        notePadSession.findElementByClassName("Edit").sendKeys(getDate());
        notePadSession.findElementByClassName("Edit").clear();
    }

    @Test
    public void pressTimeAndDateButton() {
        notePadSession.findElementByName("Edit").click();
        notePadSession.findElementByAccessibilityId("26").click();

        //checking that the text is not empty in notepad
        Assert.assertNotNull(notePadSession.findElementByClassName("Edit"));

        notePadSession.findElementByClassName("Edit").clear();
    }

}
