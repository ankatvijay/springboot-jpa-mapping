package com.ankat.repository;

import com.ankat.entity.Customer;
import com.ankat.entity.ShippingAddress;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class ShippingAddressRepositoryTest {

    @Autowired
    private ShippingAddressRepository shippingAddressRepository;

    @BeforeEach
    void setUp() {
        shippingAddressRepository.deleteAll();
    }

    @Test
    void testGivenSavedPayload_WhenFindAll_ThenReturnSavedPayloads() {
        // Given
        Customer customer = new Customer();
        customer.setFirstname("Tom");
        customer.setLastname("Hendry");
        customer.setEmail("test@gmail.com");
        customer.setPhone("9876543210");

        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setProvince("Washington Province");
        shippingAddress.setCity("Washington");
        shippingAddress.setStreet("Washington Street");
        shippingAddress.setZip("115475");
        shippingAddress.setCountry("US");

        shippingAddress.setCustomer(customer);
        shippingAddressRepository.save(shippingAddress);

        // When
        List<ShippingAddress> shippingAddresses = shippingAddressRepository.findAll();

        // Then
        shippingAddresses.forEach(actualShippingAddress -> assertPayload(actualShippingAddress, shippingAddress));
    }

    @Test
    void testGivenPayload_WhenSave_ThenReturnSavedPayload() {
        // Given
        Customer customer = new Customer();
        customer.setFirstname("Tom");
        customer.setLastname("Hendry");
        customer.setEmail("test@gmail.com");
        customer.setPhone("9876543210");

        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setProvince("Washington Province");
        shippingAddress.setCity("Washington");
        shippingAddress.setStreet("Washington Street");
        shippingAddress.setZip("115475");
        shippingAddress.setCountry("US");

        shippingAddress.setCustomer(customer);

        // When
        ShippingAddress savedShippingAddress = shippingAddressRepository.save(shippingAddress);

        // Then
        assertPayload(savedShippingAddress, shippingAddress);
    }

    @Test
    void testGivenSavedPayload_WhenUpdatedSavePayload_ThenReturnUpdatedSavedPayloads() {
        // Given
        Customer customer = new Customer();
        customer.setFirstname("Tom");
        customer.setLastname("Hendry");
        customer.setEmail("test@gmail.com");
        customer.setPhone("9876543210");

        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setProvince("Washington Province");
        shippingAddress.setCity("Washington");
        shippingAddress.setStreet("Washington Street");
        shippingAddress.setZip("115475");
        shippingAddress.setCountry("US");

        shippingAddress.setCustomer(customer);
        ShippingAddress savedShippingAddress = shippingAddressRepository.save(shippingAddress);

        // When
        savedShippingAddress.setProvince("London Province");
        savedShippingAddress.setCity("England");
        savedShippingAddress.setStreet("London Street");
        savedShippingAddress.setZip("25475");
        savedShippingAddress.setCountry("UK");
        ShippingAddress updatedShippingAddress = shippingAddressRepository.save(savedShippingAddress);

        // Then
        assertPayload(updatedShippingAddress, savedShippingAddress);
    }

    @Test
    void testGivenSavedPayload_WhenDeleteAndExist_ThenReturnFalse() {
        // Given
        Customer customer = new Customer();
        customer.setFirstname("Tom");
        customer.setLastname("Hendry");
        customer.setEmail("test@gmail.com");
        customer.setPhone("9876543210");

        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setProvince("Washington Province");
        shippingAddress.setCity("Washington");
        shippingAddress.setStreet("Washington Street");
        shippingAddress.setZip("115475");
        shippingAddress.setCountry("US");

        shippingAddress.setCustomer(customer);
        ShippingAddress savedShippingAddress = shippingAddressRepository.save(shippingAddress);

        // When
        shippingAddressRepository.deleteById(savedShippingAddress.getId());
        boolean flag = shippingAddressRepository.existsById(shippingAddress.getId());

        // Then
        Assertions.assertThat(flag).isFalse();
    }

    private void assertPayload(ShippingAddress actualShippingAddress, ShippingAddress expectedShippingAddress) {
        Assertions.assertThat(actualShippingAddress).isNotNull();
        Assertions.assertThat(actualShippingAddress.getProvince()).isEqualTo(expectedShippingAddress.getProvince());
        Assertions.assertThat(actualShippingAddress.getCity()).isEqualTo(expectedShippingAddress.getCity());
        Assertions.assertThat(actualShippingAddress.getStreet()).isEqualTo(expectedShippingAddress.getStreet());
        Assertions.assertThat(actualShippingAddress.getZip()).isEqualTo(expectedShippingAddress.getZip());
        Assertions.assertThat(actualShippingAddress.getCountry()).isEqualTo(expectedShippingAddress.getCountry());
        Assertions.assertThat(actualShippingAddress.getCustomer().getFirstname()).isEqualTo(expectedShippingAddress.getCustomer().getFirstname());
        Assertions.assertThat(actualShippingAddress.getCustomer().getLastname()).isEqualTo(expectedShippingAddress.getCustomer().getLastname());
        Assertions.assertThat(actualShippingAddress.getCustomer().getEmail()).isEqualTo(expectedShippingAddress.getCustomer().getEmail());
        Assertions.assertThat(actualShippingAddress.getCustomer().getPhone()).isEqualTo(expectedShippingAddress.getCustomer().getPhone());
    }
}