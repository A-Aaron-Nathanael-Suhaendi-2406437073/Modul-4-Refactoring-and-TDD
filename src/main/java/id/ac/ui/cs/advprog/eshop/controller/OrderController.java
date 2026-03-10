package id.ac.ui.cs.advprog.eshop.controller;

import id.ac.ui.cs.advprog.eshop.model.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/order")
public class OrderController {

    private List<Order> orderList = new ArrayList<>();

    @GetMapping("/create")
    public String createOrderPage() {
        return "createOrder";
    }

    @PostMapping("/create")
    public String createOrderPost() {
        return "redirect:/";
    }

    @GetMapping("/history")
    public String orderHistoryPage() {
        return "orderHistory";
    }

    @PostMapping("/history")
    public String orderListPost(@RequestParam("author") String author, Model model) {
        List<Order> filteredOrders = new ArrayList<>();

        model.addAttribute("author", author);
        model.addAttribute("orders", filteredOrders);
        return "orderList";
    }
}