package com.ankat;

import com.ankat.entity.Customer;
import com.ankat.entity.ShippingAddress;
import com.ankat.service.CustomerService;
import com.ankat.service.ShippingAddressService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class OneToOneBidirectionalMapping {

    private final CustomerService customerService;
    private final ShippingAddressService shippingAddressService;

    public static void main(String[] args) {
        SpringApplication.run(OneToOneBidirectionalMapping.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {
            // Payload 1 Customer to ShippingAddress
            Customer customer1 = new Customer();
            customer1.setFirstname("Tom");
            customer1.setLastname("Hendry");
            customer1.setEmail("test@gmail.com");
            customer1.setPhone("9876543210");

            ShippingAddress shippingAddress1 = new ShippingAddress();
            shippingAddress1.setProvince("Washington Province");
            shippingAddress1.setCity("Washington");
            shippingAddress1.setStreet("Washington Street");
            shippingAddress1.setZip("115475");
            shippingAddress1.setCountry("US");

            customer1.setShippingAddress(shippingAddress1);
            shippingAddress1.setCustomer(customer1);

            Customer savedCustomer = customerService.insertCustomer(customer1);
            log.info("Customer Saved : " + savedCustomer);

            // Payload 2 ShippingAddress to Customer
            Customer customer2 = new Customer();
            customer2.setFirstname("Jerry");
            customer2.setLastname("Barter");
            customer2.setEmail("test@gmail.com");
            customer2.setPhone("9876543210");

            ShippingAddress shippingAddress2 = new ShippingAddress();
            shippingAddress2.setProvince("LA Province");
            shippingAddress2.setCity("LA");
            shippingAddress2.setStreet("LA Street");
            shippingAddress2.setZip("1154873");
            shippingAddress2.setCountry("US");

            customer2.setShippingAddress(shippingAddress2);
            shippingAddress2.setCustomer(customer2);

            ShippingAddress savedShippingAddress = shippingAddressService.insertShippingAddress(shippingAddress2);
            log.info("Shipping Address Saved : " + savedShippingAddress);
        };
    }
}