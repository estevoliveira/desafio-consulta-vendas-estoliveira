package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.entities.Seller;
import com.devsuperior.dsmeta.projection.SellerProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SellerRepository extends JpaRepository<Seller, Long> {

    @Query(nativeQuery = true,value = "SELECT SUM(sa.amount) as soma, se.name as nome " +
            "FROM TB_SALES AS sa " +
            "INNER JOIN TB_SELLER AS se ON sa.SELLER_ID = se.ID " +
            "WHERE sa.date BETWEEN :minDate AND :maxDate " +
            "GROUP BY se.id " +
            "order by se.name")
    List<SellerProjection> getSellerWithDealsByTime(LocalDate minDate, LocalDate maxDate);

}
