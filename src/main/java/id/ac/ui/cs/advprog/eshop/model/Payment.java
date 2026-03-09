package id.ac.ui.cs.advprog.eshop.model;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

import id.ac.ui.cs.advprog.eshop.enums.PaymentStatus;

@Builder
@Getter
public class Payment {
    String id;
    String method;
    String status;
    Map<String, String> paymentData;

    public Payment(String id, String method, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.paymentData = paymentData;

        boolean isValid = false;
        if ("VOUCHER".equals(method)) {
            isValid = validateVoucher();
        } else if ("CASH_ON_DELIVERY".equals(method)) {
            isValid = validateCOD();
        }

        this.status = isValid ? PaymentStatus.SUCCESS.getValue() : PaymentStatus.REJECTED.getValue();
    }

    public Payment(String id, String method, String status, Map<String, String> paymentData) {
        this.id = id;
        this.method = method;
        this.status = status;
        this.paymentData = paymentData;
    }

    private boolean validateVoucher() {
        String voucherCode = paymentData.get("voucherCode");
        if (voucherCode == null || voucherCode.length() != 16 || !voucherCode.startsWith("ESHOP")) {
            return false;
        }

        int numCount = 0;
        for (char c : voucherCode.toCharArray()) {
            if (Character.isDigit(c)) numCount++;
        }
        return numCount == 8;
    }

    private boolean validateCOD() {
        String address = paymentData.get("address");
        String deliveryFee = paymentData.get("deliveryFee");

        return address != null && !address.isEmpty() && deliveryFee != null && !deliveryFee.isEmpty();
    }
}