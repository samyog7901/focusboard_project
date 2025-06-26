package org.focusboard.service.payment;

public class PayPalPaymentService implements PaymentService {
    @Override
    public void processPayment(double amount) {
        System.out.println("PayPal PAYMENT SERVICE");
        System.out.println("Amount: " + amount);
    }
}
