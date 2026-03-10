package id.ac.ui.cs.advprog.eshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/payment")
public class PaymentController {

    @GetMapping("/detail")
    public String paymentDetailForm() {
        return "paymentDetailForm";
    }

    @GetMapping("/detail/{paymentId}")
    public String paymentDetail(@PathVariable String paymentId, Model model) {
        model.addAttribute("paymentId", paymentId);
        return "paymentDetail";
    }

    @GetMapping("/admin/list")
    public String paymentAdminList() {
        return "paymentList";
    }

    @GetMapping("/admin/detail/{paymentId}")
    public String paymentAdminDetail(@PathVariable String paymentId, Model model) {
        model.addAttribute("paymentId", paymentId);
        return "paymentAdminDetail";
    }

    @PostMapping("/admin/set-status/{paymentId}")
    public String paymentAdminSetStatus(@PathVariable String paymentId) {
        return "redirect:/payment/admin/list";
    }
}