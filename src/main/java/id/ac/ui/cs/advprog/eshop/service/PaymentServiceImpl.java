package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Payment addPayment(Order order, String method, Map<String, String> paymentData) {
        Payment payment = new Payment(order.getId(), method, paymentData);
        return paymentRepository.save(payment);
    }

    @Override
    public Payment setStatus(Payment payment, String status) {
        if (status.equals("SUCCESS") || status.equals("REJECTED")) {
            Payment newPayment = new Payment(payment.getId(), payment.getMethod(), status, payment.getPaymentData());
            paymentRepository.save(newPayment);

            Order order = orderRepository.findById(newPayment.getId());
            if (order != null) {
                if (status.equals("SUCCESS")) {
                    order.setStatus("SUCCESS");
                } else if (status.equals("REJECTED")) {
                    order.setStatus("FAILED");
                }
                orderRepository.save(order);
            }

            return newPayment;
        } else {
            throw new IllegalArgumentException("Invalid payment status");
        }
    }

    @Override
    public Payment getPayment(String paymentId) {
        return paymentRepository.findById(paymentId);
    }

    @Override
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
}