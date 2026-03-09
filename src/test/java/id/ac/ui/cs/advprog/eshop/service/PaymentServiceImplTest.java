package id.ac.ui.cs.advprog.eshop.service;

import id.ac.ui.cs.advprog.eshop.enums.OrderStatus;
import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;
import id.ac.ui.cs.advprog.eshop.model.Order;
import id.ac.ui.cs.advprog.eshop.model.Payment;
import id.ac.ui.cs.advprog.eshop.model.Product;
import id.ac.ui.cs.advprog.eshop.repository.OrderRepository;
import id.ac.ui.cs.advprog.eshop.repository.PaymentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    @Mock
    OrderRepository orderRepository;

    List<Payment> payments;
    Order order;

    @BeforeEach
    void setUp() {
        payments = new ArrayList<>();

        Map<String, String> paymentData1 = new HashMap<>();
        paymentData1.put("voucherCode", "ESHOP1234ABC5678");
        Payment payment1 = new Payment("1", "VOUCHER", paymentData1);
        payments.add(payment1);

        Map<String, String> paymentData2 = new HashMap<>();
        paymentData2.put("address", "Jalan Margonda");
        paymentData2.put("deliveryFee", "10000");
        Payment payment2 = new Payment("2", "CASH_ON_DELIVERY", paymentData2);
        payments.add(payment2);

        List<Product> products = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductId("eb558e9f-1c39-460e-8860-71af6af63bd6");
        product1.setProductName("Sampo Cap Bambang");
        product1.setProductQuantity(2);
        products.add(product1);

        order = new Order(payments.get(0).getId(), products, 1708560000L, "Safira Sudrajat");
    }

    @Test
    void testAddPayment() {
        Map<String, String> paymentData = new HashMap<>();
        paymentData.put("voucherCode", "ESHOP1234ABC5678");
        Payment expectedPayment = new Payment(order.getId(), "VOUCHER", paymentData);
        doReturn(expectedPayment).when(paymentRepository).save(any(Payment.class));

        Payment result = paymentService.addPayment(order, "VOUCHER", paymentData);

        verify(paymentRepository, times(1)).save(any(Payment.class));
        assertEquals(expectedPayment.getId(), result.getId());
        assertEquals(expectedPayment.getMethod(), result.getMethod());
        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
    }

    @Test
    void testSetStatusSuccess() {
        Payment payment = payments.get(0);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        doReturn(order).when(orderRepository).findById(payment.getId());

        Payment result = paymentService.setStatus(payment, PaymentStatus.SUCCESS.getValue());

        assertEquals(PaymentStatus.SUCCESS.getValue(), result.getStatus());
        assertEquals(OrderStatus.SUCCESS.getValue(), order.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testSetStatusRejected() {
        Payment payment = payments.get(0);
        doReturn(payment).when(paymentRepository).save(any(Payment.class));
        doReturn(order).when(orderRepository).findById(payment.getId());

        Payment result = paymentService.setStatus(payment, PaymentStatus.REJECTED.getValue());

        assertEquals(PaymentStatus.REJECTED.getValue(), result.getStatus());
        assertEquals("FAILED", order.getStatus());
        verify(paymentRepository, times(1)).save(any(Payment.class));
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void testSetStatusInvalid() {
        Payment payment = payments.get(0);
        assertThrows(IllegalArgumentException.class, () -> {
            paymentService.setStatus(payment, "MEOW");
        });
    }

    @Test
    void testGetPaymentIfFound() {
        Payment expectedPayment = payments.get(0);
        doReturn(expectedPayment).when(paymentRepository).findById(expectedPayment.getId());

        Payment result = paymentService.getPayment(expectedPayment.getId());

        assertEquals(expectedPayment.getId(), result.getId());
    }

    @Test
    void testGetPaymentIfNotFound() {
        doReturn(null).when(paymentRepository).findById("invalid-id");

        Payment result = paymentService.getPayment("invalid-id");

        assertNull(result);
    }

    @Test
    void testGetAllPayments() {
        doReturn(payments).when(paymentRepository).findAll();

        List<Payment> result = paymentService.getAllPayments();

        assertEquals(2, result.size());
    }
}