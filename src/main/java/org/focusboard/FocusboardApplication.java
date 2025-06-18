package org.focusboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class FocusboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(FocusboardApplication.class, args);
//		var orderService = new OrderService(new PayPalPaymentService());
//		orderService.placeOrder();
	}
//
//	@Bean
//	public BCryptPasswordEncoder passwordEncoder() {
//		return new BCryptPasswordEncoder();
//	}

}
