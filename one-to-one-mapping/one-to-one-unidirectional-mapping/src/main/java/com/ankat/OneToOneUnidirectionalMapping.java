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
public class OneToOneUnidirectionalMapping {

    private final CustomerService customerService;
    private final ShippingAddressService shippingAddressService;
    public static void main(String[] args) {
        SpringApplication.run(OneToOneUnidirectionalMapping.class, args);
    }

    @Bean
    public ApplicationRunner init() {
        return args -> {

            Customer customer = new Customer();
            customer.setFirstname("Vijay");
            customer.setLastname("Ankat");
            customer.setEmail("test@gmail.com");
            customer.setPhone("9876543210");

            ShippingAddress shippingAddress = new ShippingAddress();
            shippingAddress.setStreet("Renolds Colony");
            shippingAddress.setCity("Mumbai");
            shippingAddress.setProvince("Wadala");
            shippingAddress.setZip("400037");
            shippingAddress.setCountry("India");

            shippingAddress.setCustomer(customer);

            ShippingAddress savedShippingAddress = shippingAddressService.insertShippingAddress(shippingAddress);
            log.info(String.valueOf(savedShippingAddress));
        };
    }
}
