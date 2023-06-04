package com.ankat.service;

import com.ankat.entity.Customer;
import com.ankat.entity.ShippingAddress;
import com.ankat.exception.MissMatchRecordException;
import com.ankat.exception.RecordNotFoundException;
import com.ankat.repository.ShippingAddressRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class ShippingAddressServiceTest {

    @Mock
    private ShippingAddressRepository shippingAddressRepository;

    @InjectMocks
    private ShippingAddressService shippingAddressService;


    @BeforeEach
    void setUp() {
        // When
        Mockito.doNothing().when(shippingAddressRepository).deleteAll();
        shippingAddressRepository.deleteAll();

        // Then
        Mockito.verify(shippingAddressRepository).deleteAll();
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
        Mockito.when(shippingAddressRepository.save(shippingAddress)).thenReturn(shippingAddress);
        shippingAddressService.insertShippingAddress(shippingAddress);

        // When
        List<ShippingAddress> mockReturnShippingAddresses = new ArrayList<>();
        mockReturnShippingAddresses.add(shippingAddress);
        Mockito.when(shippingAddressRepository.findAll()).thenReturn(mockReturnShippingAddresses);
        List<ShippingAddress> shippingAddresses = shippingAddressService.selectAllShippingAddresses();

        // Then
        shippingAddresses.forEach(actualShippingAddress -> assertPayload(actualShippingAddress, shippingAddress));
        Mockito.verify(shippingAddressRepository).save(shippingAddress);
        Mockito.verify(shippingAddressRepository).findAll();
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
        Mockito.when(shippingAddressRepository.save(shippingAddress)).thenReturn(shippingAddress);
        ShippingAddress savedShippingAddress = shippingAddressService.insertShippingAddress(shippingAddress);

        // Then
        assertPayload(savedShippingAddress, shippingAddress);
        Mockito.verify(shippingAddressRepository).save(shippingAddress);
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
        shippingAddress.setId(25L);
        shippingAddress.setProvince("Washington Province");
        shippingAddress.setCity("Washington");
        shippingAddress.setStreet("Washington Street");
        shippingAddress.setZip("115475");
        shippingAddress.setCountry("US");

        shippingAddress.setCustomer(customer);
        Mockito.when(shippingAddressRepository.save(shippingAddress)).thenReturn(shippingAddress);
        ShippingAddress savedShippingAddress = shippingAddressService.insertShippingAddress(shippingAddress);

        // When
        savedShippingAddress.setProvince("London Province");
        savedShippingAddress.setCity("England");
        savedShippingAddress.setStreet("London Street");
        savedShippingAddress.setZip("25475");
        savedShippingAddress.setCountry("UK");
        Mockito.when(shippingAddressRepository.existsById(savedShippingAddress.getId())).thenReturn(true);
        Mockito.when(shippingAddressRepository.save(savedShippingAddress)).thenReturn(savedShippingAddress);
        ShippingAddress updatedShippingAddress = shippingAddressService.updateShippingAddress(savedShippingAddress.getId(), savedShippingAddress);

        // Then
        assertPayload(updatedShippingAddress, savedShippingAddress);
        Mockito.verify(shippingAddressRepository, Mockito.atLeastOnce()).save(shippingAddress);
        Mockito.verify(shippingAddressRepository).existsById(savedShippingAddress.getId());
        Mockito.verify(shippingAddressRepository, Mockito.atLeastOnce()).save(savedShippingAddress);
    }

    @Test
    void testGivenSavedPayload_WhenUpdatedSavePayloadAndRandomId_ThenReturnError(){
        // Given
        Long id = new Random().nextLong(1,1000);
        Customer customer = new Customer();
        customer.setFirstname("Tom");
        customer.setLastname("Hendry");
        customer.setEmail("test@gmail.com");
        customer.setPhone("9876543210");

        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setId(25L);
        shippingAddress.setProvince("Washington Province");
        shippingAddress.setCity("Washington");
        shippingAddress.setStreet("Washington Street");
        shippingAddress.setZip("115475");
        shippingAddress.setCountry("US");

        shippingAddress.setCustomer(customer);
        Mockito.when(shippingAddressRepository.save(shippingAddress)).thenReturn(shippingAddress);
        ShippingAddress savedShippingAddress = shippingAddressService.insertShippingAddress(shippingAddress);

        // When
        savedShippingAddress.setProvince("London Province");
        savedShippingAddress.setCity("England");
        savedShippingAddress.setStreet("London Street");
        savedShippingAddress.setZip("25475");
        savedShippingAddress.setCountry("UK");
        Assertions.assertThatThrownBy(() -> shippingAddressService.updateShippingAddress(id, savedShippingAddress))
                .isInstanceOf(MissMatchRecordException.class)
                .hasMessage("Update record id: " + id + " not equal to payload record id: " + savedShippingAddress.getId());

        // Then
        Mockito.verify(shippingAddressRepository).save(shippingAddress);
    }

    @Test
    void testGivenNull_WhenUpdatedAndRandomId_ThenReturnError(){
        // Given
        Long id = new Random().nextLong(1,1000);

        // When
        Assertions.assertThatThrownBy(() -> shippingAddressService.updateShippingAddress(id, null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Payload record id is null");

        // Then
    }

    @Test
    void testGivenSavedPayload_WhenUpdatedNewPayloadAndRandomId_ThenReturnError(){
        // Given
        Long id = new Random().nextLong(1,1000);
        Customer customer = new Customer();
        customer.setFirstname("Tom");
        customer.setLastname("Hendry");
        customer.setEmail("test@gmail.com");
        customer.setPhone("9876543210");

        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setId(id);
        shippingAddress.setProvince("Washington Province");
        shippingAddress.setCity("Washington");
        shippingAddress.setStreet("Washington Street");
        shippingAddress.setZip("115475");
        shippingAddress.setCountry("US");

        shippingAddress.setCustomer(customer);

        // When
        Assertions.assertThatThrownBy(() -> shippingAddressService.updateShippingAddress(id, shippingAddress))
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessage("No record found with id " + id);

        // Then
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
        shippingAddress.setId(25L);
        shippingAddress.setProvince("Washington Province");
        shippingAddress.setCity("Washington");
        shippingAddress.setStreet("Washington Street");
        shippingAddress.setZip("115475");
        shippingAddress.setCountry("US");

        shippingAddress.setCustomer(customer);
        Mockito.when(shippingAddressRepository.save(shippingAddress)).thenReturn(shippingAddress);
        ShippingAddress savedShippingAddress = shippingAddressService.insertShippingAddress(shippingAddress);

        // When
        Mockito.when(shippingAddressRepository.findById(savedShippingAddress.getId())).thenReturn(Optional.of(savedShippingAddress));
        Mockito.doNothing().when(shippingAddressRepository).delete(savedShippingAddress);
        shippingAddressService.deleteByIdShippingAddress(savedShippingAddress.getId());

        Mockito.when(shippingAddressRepository.existsById(savedShippingAddress.getId())).thenReturn(false);
        boolean flag = shippingAddressService.existByIdShippingAddress(shippingAddress.getId());

        // Then
        Assertions.assertThat(flag).isFalse();
        Mockito.verify(shippingAddressRepository).save(shippingAddress);
        Mockito.verify(shippingAddressRepository).findById(savedShippingAddress.getId());
        Mockito.verify(shippingAddressRepository).delete(savedShippingAddress);
        Mockito.verify(shippingAddressRepository).existsById(savedShippingAddress.getId());
    }

    @Test
    void testGivenMultiplePayload_WhenSaveAll_ThenReturnListPayload() {
        // Given
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
        shippingAddress1.setCustomer(customer1);

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
        shippingAddress2.setCustomer(customer2);

        Set<ShippingAddress> shippingAddresses = new HashSet<>();
        shippingAddresses.add(shippingAddress1);
        shippingAddresses.add(shippingAddress2);

        // When
        Mockito.when(shippingAddressRepository.saveAll(shippingAddresses)).thenReturn(shippingAddresses.stream().toList());
        List<ShippingAddress> savedShippingAddresses = shippingAddressService.insertShippingAddresses(shippingAddresses);

        // Then
        Iterator<ShippingAddress> actualShippingAddressesIterator = savedShippingAddresses.stream().iterator();
        Iterator<ShippingAddress> expectedShippingAddressesIterator = shippingAddresses.stream().iterator();
        while (actualShippingAddressesIterator.hasNext() && expectedShippingAddressesIterator.hasNext()) {
            assertPayload(actualShippingAddressesIterator.next(), expectedShippingAddressesIterator.next());
        }
    }

    @Test
    void testGivenRandomId_WhenDeleteById_ThenReturnFalse(){
        // Given
        Long id = new Random().nextLong(1,1000);

        // When
        Mockito.when(shippingAddressRepository.findById(id)).thenThrow(new RecordNotFoundException("No record found with id " + id));
        Assertions.assertThatThrownBy(() -> shippingAddressService.deleteByIdShippingAddress(id))
                .isInstanceOf(RecordNotFoundException.class)
                .hasMessage("No record found with id " + id);

        // Then
        Mockito.verify(shippingAddressRepository).findById(id);
    }

    @Test
    void testGivenNon_WhenDeleteAll_ThenDoNothing(){
        // Given

        // When
        Mockito.doNothing().when(shippingAddressRepository).deleteAll();
        shippingAddressService.deleteAllShippingAddresses();

        // Then
        Mockito.verify(shippingAddressRepository,Mockito.atLeastOnce()).deleteAll();
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