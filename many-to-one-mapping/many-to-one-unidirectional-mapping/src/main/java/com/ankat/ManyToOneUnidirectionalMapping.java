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
public class ManyToOneUnidirectionalMapping {

    private final CustomerService customerService;
    private final ShippingAddressService shippingAddressService;

    public static void main(String[] args) {
        SpringApplication.run(ManyToOneUnidirectionalMapping.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {
            // Payload Customer to ShippingAddress
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

            ShippingAddress shippingAddress = new ShippingAddress();
            shippingAddress.setProvince("Washington Province");
            shippingAddress.setCity("Washington");
            shippingAddress.setStreet("Washington Street");
            shippingAddress.setZip("115475");
            shippingAddress.setCountry("US");

            customer1.setShippingAddress(shippingAddress);
            customer2.setShippingAddress(shippingAddress);

            Set<Customer> customers = new HashSet<>();
            customers.add(customer1);
            customers.add(customer2);

            List<Customer> list = customerService.insertCustomers(customers);
            list.forEach(customer -> log.info("Customer Saved : " + customer));
        };
    }
}