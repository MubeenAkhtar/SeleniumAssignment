package Test;


import java.util.concurrent.TimeUnit;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

public class TestClass {
    public static void main(String[] args) throws InterruptedException {
        WebDriverManager.chromedriver().browserVersion("96").setup();
        WebDriver driver = new ChromeDriver();
        driver.get("http://sanbox.undostres.com.mx/"); // Opening the URL
        //now Selecting operator and fill Recharge Details
        driver.findElement(By.name("operator")).click();  //Providing the input in the Operador
        driver.findElement(By.cssSelector("[click='1']")).click();
        driver.findElement(By.name("mobile")).sendKeys("8465433546"); //Providing the number
        driver.findElement(By.name("amount")).click();
        driver.findElement(By.xpath("//li[@data-id='1']")).click();
        driver.findElement(By.cssSelector("[levenstein='1']")).click();
        //verify user is reached on payment Screen or not
        String str = driver.findElement(By.cssSelector("div.summary-message-top")).getText();
        if (str.equalsIgnoreCase("Resumen de la compra")) {
            System.out.println("Successfully Login");
        } else {
            System.out.println("Try Again");
        }
        // Selecting Payment Method
        driver.manage().timeouts().implicitlyWait(1000, TimeUnit.SECONDS);
        driver.findElement(By.id("new-card-toggle")).click();                 // Clicking on the Tarjeta option
        Thread.sleep(8000);
        WebElement element = driver.findElement(By.id("radio-n"));
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
        driver.findElement(By.id("cardnumberunique")).sendKeys("4111111111111111");  // Providing card number
        driver.findElement(By.cssSelector("[data-qa='mes-input']")).sendKeys("11"); //Providing the month
        driver.findElement(By.cssSelector("[data-qa='expyear-input']")).sendKeys("25"); // Providing the date
        driver.findElement(By.cssSelector("[data-qa='cvv-input']")).sendKeys("111"); // Providing the cvv
        driver.findElement(By.name("txtEmail")).sendKeys("test@test.com"); //Providing email
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        driver.findElement(By.id("paylimit")).click(); // Clicking on the Pagar con Tarjeta button
        //enter emailid to complete recharge
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.findElement(By.id("usrname")).sendKeys("automationUDT1@gmail.com"); // Providing email
        driver.findElement(By.id("psw")).sendKeys("automationUDT123"); //Providing password

        // Click on Recaptcha

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.switchTo().frame(0);
        WebElement elem = driver.findElement(By.cssSelector("#recaptcha-anchor"));
        Actions actn = new Actions(driver);
        actn.moveToElement(elem).build().perform();
        actn.click().perform();
        Thread.sleep(4000);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.switchTo().defaultContent();
        WebElement eleme = driver.findElement(By.id("loginBtn"));
        Actions acti = new Actions(driver);
        acti.moveToElement(eleme).build().perform();
        acti.click().perform();
        //Verify recharge is successfull or not
        String st = driver.findElement(By.xpath("//div[contains(text(),'Para pagar por favor Regístrate o Ingresa a tu cuenta')]")).getText();
        if (st.equalsIgnoreCase("Para pagar por favor Regístrate o Ingresa a tu cuenta")) {
            System.out.println("Recharge not successful due to user blocked");
        } else {
            System.out.println("Recharge successful");
        }
    }
}
