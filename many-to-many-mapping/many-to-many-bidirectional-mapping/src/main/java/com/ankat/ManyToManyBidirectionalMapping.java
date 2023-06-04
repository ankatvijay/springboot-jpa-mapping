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
public class ManyToManyBidirectionalMapping {

    private final CustomerService customerService;
    private final ShippingAddressService shippingAddressService;

    public static void main(String[] args) {
        SpringApplication.run(ManyToManyBidirectionalMapping.class, args);
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

            ShippingAddress shippingAddress2 = new ShippingAddress();
            shippingAddress2.setProvince("LA Province");
            shippingAddress2.setCity("LA");
            shippingAddress2.setStreet("LA Street");
            shippingAddress2.setZip("1154873");
            shippingAddress2.setCountry("US");

            Set<ShippingAddress> shippingAddresses1 = new HashSet<>();
            shippingAddresses1.add(shippingAddress1);
            shippingAddresses1.add(shippingAddress2);

            customer1.setShippingAddresses(shippingAddresses1);
            customer2.setShippingAddresses(shippingAddresses1);

            Set<Customer> customers1 = new HashSet<>();
            customers1.add(customer1);
            customers1.add(customer2);

            List<Customer> savedCustomers = customerService.insertCustomers(customers1);
            savedCustomers.forEach(customer -> log.info("Customer Saved : " + customer));

            // Payload 2 ShippingAddress to Customer
            Customer customer3 = new Customer();
            customer3.setFirstname("Bruce");
            customer3.setLastname("Wayne");
            customer3.setEmail("test@gmail.com");
            customer3.setPhone("9876543210");

            Customer customer4 = new Customer();
            customer4.setFirstname("Tony");
            customer4.setLastname("Stark");
            customer4.setEmail("test@gmail.com");
            customer4.setPhone("9876543210");

            ShippingAddress shippingAddress3 = new ShippingAddress();
            shippingAddress3.setProvince("Manhatan Province");
            shippingAddress3.setCity("Manhatan");
            shippingAddress3.setStreet("Manhatan Street");
            shippingAddress3.setZip("1154873");
            shippingAddress3.setCountry("US");

            ShippingAddress shippingAddress4 = new ShippingAddress();
            shippingAddress4.setProvince("Silicon Valley Province");
            shippingAddress4.setCity("Silicon Valley");
            shippingAddress4.setStreet("Silicon Valley Street");
            shippingAddress4.setZip("1154873");
            shippingAddress4.setCountry("US");

            Set<Customer> customers2 = new HashSet<>();
            customers2.add(customer3);
            customers2.add(customer4);

            shippingAddress3.setCustomers(customers2);
            shippingAddress4.setCustomers(customers2);

            Set<ShippingAddress> shippingAddresses2 = new HashSet<>();
            shippingAddresses2.add(shippingAddress3);
            shippingAddresses2.add(shippingAddress4);

            List<ShippingAddress> savedShippingAddresses = shippingAddressService.insertShippingAddresses(shippingAddresses2);
            savedShippingAddresses.forEach(shippingAddress -> log.info("Shipping Address Saved : " + shippingAddress));
        };
    }
}