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

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class ManyToOneBidirectionalMapping {

    private final CustomerService customerService;
    private final ShippingAddressService shippingAddressService;

    public static void main(String[] args) {
        SpringApplication.run(ManyToOneBidirectionalMapping.class, args);
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

            Customer customer2 = new Customer();
            customer2.setFirstname("Jerry");
            customer2.setLastname("Barter");
            customer2.setEmail("test@gmail.com");
            customer2.setPhone("9876543210");

            ShippingAddress shippingAddress1 = new ShippingAddress();
            shippingAddress1.setProvince("Washington Province");
            shippingAddress1.setCity("Washington");
            shippingAddress1.setStreet("Washington Street");
            shippingAddress1.setZip("115475");
            shippingAddress1.setCountry("US");

            customer1.setShippingAddress(shippingAddress1);
            customer2.setShippingAddress(shippingAddress1);

            Set<Customer> customers1 = new HashSet<>();
            customers1.add(customer1);
            customers1.add(customer2);

            List<Customer> list = customerService.insertCustomers(customers1);
            list.forEach(customer -> log.info("Customer Saved : " + customer));

            // Payload 2 ShippingAddress to Customer
            ShippingAddress shippingAddress2 = new ShippingAddress();
            shippingAddress2.setProvince("Washington Province");
            shippingAddress2.setCity("Washington");
            shippingAddress2.setStreet("Washington Street");
            shippingAddress2.setZip("115475");
            shippingAddress2.setCountry("US");

            Customer customer3 = new Customer();
            customer3.setFirstname("Bruce");
            customer3.setLastname("Wayne");
            customer3.setEmail("test@gmail.com");
            customer3.setPhone("9876543210");
            customer3.setShippingAddress(shippingAddress2);

            Customer customer4 = new Customer();
            customer4.setFirstname("Tony");
            customer4.setLastname("Stark");
            customer4.setEmail("test@gmail.com");
            customer4.setPhone("9876543210");
            customer4.setShippingAddress(shippingAddress2);

            Set<Customer> customers = new HashSet<>();
            customers.add(customer3);
            customers.add(customer4);
            shippingAddress2.setCustomers(customers);

            ShippingAddress savedShippingAddress = shippingAddressService.insertShippingAddress(shippingAddress2);
            log.info("Shipping Address Saved : " + savedShippingAddress);
        };
    }
}