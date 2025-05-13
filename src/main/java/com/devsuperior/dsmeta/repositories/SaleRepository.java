package com.devsuperior.dsmeta.repositories;

import com.devsuperior.dsmeta.projection.SalerProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.devsuperior.dsmeta.entities.Sale;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface SaleRepository extends JpaRepository<Sale, Long> {

    @Query(nativeQuery = true,value = """
            SELECT sa.id as id,sa.date as dia ,sa.amount as quantidade, se.name as nome
             FROM TB_SALES AS sa
            INNER JOIN TB_SELLER AS se ON sa.SELLER_ID = se.ID
            WHERE sa.date BETWEEN :minDate AND :maxDate
            and upper(se.name) like upper(concat('%',:name,'%'))
            order by sa.id
            """)
    Page<SalerProjection> findSaleByDateAndSeller1(LocalDate  minDate,LocalDate maxDate,String name, Pageable pageable);


    @Query(
            value = "SELECT obj FROM Sale obj WHERE obj.date BETWEEN :minDate AND :maxDate AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%',:name,'%')) ",
            countQuery = "SELECT COUNT(obj) FROM Sale obj WHERE obj.date BETWEEN :minDate AND :maxDate AND UPPER(obj.seller.name) LIKE UPPER(CONCAT('%',:name,'%')) "
    )
    Page<Sale> findSaleByDateAndSeller(LocalDate  minDate,LocalDate maxDate,String name, Pageable pageable);

}
