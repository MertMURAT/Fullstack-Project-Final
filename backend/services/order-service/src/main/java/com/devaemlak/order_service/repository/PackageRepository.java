package com.devaemlak.order_service.repository;

import com.devaemlak.order_service.model.AdPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PackageRepository extends JpaRepository<AdPackage, Long> {

    @Query(value = "SELECT * FROM ad_package ORDER BY id ASC LIMIT 1", nativeQuery = true)
    Optional<AdPackage> findTopByOrderByIdAsc();
}
