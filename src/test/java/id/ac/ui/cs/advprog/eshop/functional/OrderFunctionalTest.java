package id.ac.ui.cs.advprog.eshop.functional;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@ExtendWith(SeleniumJupiter.class)
class OrderFunctionalTest {

    @LocalServerPort
    private int serverPort;

    @Value("${app.baseUrl:http://localhost}")
    private String testBaseUrl;

    private String baseUrl;

    @BeforeEach
    void setupTest() {
        baseUrl = String.format("%s:%d", testBaseUrl, serverPort);
    }

    @Test
    void createOrderPage_isAccessible(ChromeDriver driver) {
        driver.get(baseUrl + "/order/create");
        assertEquals("Create Order", driver.getTitle());
    }

    @Test
    void orderHistoryPage_isAccessible(ChromeDriver driver)  {
        driver.get(baseUrl + "/order/history");
        assertEquals("Order History Search", driver.getTitle());
    }

    @Test
    void orderHistoryPost_redirectsToOrderList(ChromeDriver driver) {
        driver.get(baseUrl + "/order/history");
        driver.findElement(By.id("author")).sendKeys("AaronNath11");
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        assertEquals("Order List", driver.getTitle());
        assertEquals("Order History for AaronNath11", driver.findElement(By.tagName("h2")).getText());
    }

    @Test
    void orderPayPage_isAccessible(ChromeDriver driver)  {
        driver.get(baseUrl + "/order/pay/dummy-order-123");
        assertEquals("Pay Order", driver.getTitle());
    }

    @Test
    void orderPayPost_submitsPayment(ChromeDriver driver)  {
        driver.get(baseUrl + "/order/pay/dummy-order-123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        assertEquals("Payment Detail", driver.getTitle());
    }
}