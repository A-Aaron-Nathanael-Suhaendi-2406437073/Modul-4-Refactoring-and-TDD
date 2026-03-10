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
class PaymentFunctionalTest {

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
    void paymentDetailFormPage_isAccessible(ChromeDriver driver)  {
        driver.get(baseUrl + "/payment/detail");
        assertEquals("Payment Detail Form", driver.getTitle());
    }

    @Test
    void paymentDetailPageById_isAccessible(ChromeDriver driver)  {
        driver.get(baseUrl + "/payment/detail/dummy-payment-123");
        assertEquals("Payment Detail", driver.getTitle());
    }

    @Test
    void paymentAdminListPage_isAccessible(ChromeDriver driver)  {
        driver.get(baseUrl + "/payment/admin/list");
        assertEquals("All Payments", driver.getTitle());
    }

    @Test
    void paymentAdminDetailPage_isAccessible(ChromeDriver driver)  {
        driver.get(baseUrl + "/payment/admin/detail/dummy-payment-123");
        assertEquals("Review Payment", driver.getTitle());
    }

    @Test
    void paymentAdminSetStatus_updatesAndRedirects(ChromeDriver driver)  {
        driver.get(baseUrl + "/payment/admin/detail/dummy-payment-123");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        assertEquals("All Payments", driver.getTitle());
    }
}