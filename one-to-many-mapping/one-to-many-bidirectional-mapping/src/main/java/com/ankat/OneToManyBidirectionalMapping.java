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
public class OneToManyBidirectionalMapping {

    private final CustomerService customerService;
    private final ShippingAddressService shippingAddressService;

    public static void main(String[] args) {
        SpringApplication.run(OneToManyBidirectionalMapping.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {
            // Payload 1 Customer to ShippingAddress
            Customer customer = new Customer();
            customer.setFirstname("Tom");
            customer.setLastname("Hendry");
            customer.setEmail("test@gmail.com");
            customer.setPhone("9876543210");

            ShippingAddress shippingAddress1 = new ShippingAddress();
            shippingAddress1.setProvince("Washington Province");
            shippingAddress1.setCity("Washington");
            shippingAddress1.setStreet("Washington Street");
            shippingAddress1.setZip("115475");
            shippingAddress1.setCountry("US");
            shippingAddress1.setCustomer(customer);

            ShippingAddress shippingAddress2 = new ShippingAddress();
            shippingAddress2.setProvince("LA Province");
            shippingAddress2.setCity("LA");
            shippingAddress2.setStreet("LA Street");
            shippingAddress2.setZip("1154873");
            shippingAddress2.setCountry("US");
            shippingAddress2.setCustomer(customer);

            Set<ShippingAddress> shippingAddresses1 = new HashSet<>();
            shippingAddresses1.add(shippingAddress1);
            shippingAddresses1.add(shippingAddress2);

            customer.setShippingAddress(shippingAddresses1);

            Customer savedCustomer = customerService.insertCustomer(customer);
            log.info("Customer Saved : " + savedCustomer);

            // Payload 2 ShippingAddress to Customer
            Customer customer2 = new Customer();
            customer2.setFirstname("Jerry");
            customer2.setLastname("Barter");
            customer2.setEmail("test@gmail.com");
            customer2.setPhone("9876543210");

            ShippingAddress shippingAddress3 = new ShippingAddress();
            shippingAddress3.setProvince("Manhatan Province");
            shippingAddress3.setCity("Manhatan");
            shippingAddress3.setStreet("Manhatan Street");
            shippingAddress3.setZip("1154873");
            shippingAddress3.setCountry("US");
            shippingAddress3.setCustomer(customer2);

            ShippingAddress shippingAddress4 = new ShippingAddress();
            shippingAddress4.setProvince("Silicon Valley Province");
            shippingAddress4.setCity("Silicon Valley");
            shippingAddress4.setStreet("Silicon Valley Street");
            shippingAddress4.setZip("1154873");
            shippingAddress4.setCountry("US");
            shippingAddress4.setCustomer(customer2);

            Set<ShippingAddress> shippingAddresses2 = new HashSet<>();
            shippingAddresses2.add(shippingAddress3);
            shippingAddresses2.add(shippingAddress4);

            customer2.setShippingAddress(shippingAddresses2);

            List<ShippingAddress> savedShippingAddresses = shippingAddressService.insertShippingAddresses(shippingAddresses2);
            savedShippingAddresses.forEach(shippingAddress -> log.info("Shipping Address Saved : " + shippingAddress));
        };
    }
}