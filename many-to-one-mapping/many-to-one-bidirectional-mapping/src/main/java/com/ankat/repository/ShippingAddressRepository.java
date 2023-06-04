package com.ankat.repository;

import com.ankat.entity.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository(value = "shippingAddressRepository")
public interface ShippingAddressRepository extends JpaRepository<ShippingAddress, Long> {
}
