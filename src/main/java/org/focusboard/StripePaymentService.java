package org.focusboard;

public class StripePaymentService implements PaymentService {
    @Override
    public void processPayment(double amount) {
        System.out.println("STRIPE PAYMENT SERVICE");
        System.out.println("Amount: " + amount);
    }
}
