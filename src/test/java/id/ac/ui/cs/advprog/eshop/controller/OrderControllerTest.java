package id.ac.ui.cs.advprog.eshop.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = OrderController.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateOrderPage() throws Exception {
        mockMvc.perform(get("/order/create"))
                .andExpect(status().isOk())
                .andExpect(view().name("createOrder"));
    }

    @Test
    void testCreateOrderPost() throws Exception {
        mockMvc.perform(post("/order/create"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    void testOrderHistoryPage() throws Exception {
        mockMvc.perform(get("/order/history"))
                .andExpect(status().isOk())
                .andExpect(view().name("orderHistory"));
    }

    @Test
    void testOrderListPost() throws Exception {
        mockMvc.perform(post("/order/history").param("author", "AaronNath11"))
                .andExpect(status().isOk())
                .andExpect(view().name("orderList"))
                .andExpect(model().attributeExists("author"))
                .andExpect(model().attribute("author", "AaronNath11"))
                .andExpect(model().attributeExists("orders"));
    }

    @Test
    void testPayOrderPage() throws Exception {
        mockMvc.perform(get("/order/pay/ORD-123"))
                .andExpect(status().isOk())
                .andExpect(view().name("paymentOrder"))
                .andExpect(model().attributeExists("orderId"))
                .andExpect(model().attribute("orderId", "ORD-123"));
    }

    @Test
    void testPayOrderPost() throws Exception {
        mockMvc.perform(post("/order/pay/ORD-123"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/payment/detail/PAY-ORD-123"));
    }
}