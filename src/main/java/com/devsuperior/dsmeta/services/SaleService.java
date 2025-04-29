package com.devsuperior.dsmeta.services;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.devsuperior.dsmeta.dto.SellerMinDTO;
import com.devsuperior.dsmeta.entities.Seller;
import com.devsuperior.dsmeta.projection.SellerProjection;
import com.devsuperior.dsmeta.repositories.SellerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;

	@Autowired
	private SellerRepository sellerRepository;

	@Transactional(readOnly = true)
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	@Transactional(readOnly = true)
	public List<SellerMinDTO> getSummary(String minDate, String maxDate){
		LocalDate newMinDate = null;
		LocalDate newMaxDate = null;
		//stringToLocalDate(minDate,maxDate,newMinDate,newMaxDate);

		if(maxDate !=null  && minDate!=null) {
			if (maxDate.isEmpty() || maxDate.isBlank())
				newMaxDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
			else
				newMaxDate = LocalDate.parse(maxDate);

			if (minDate.isEmpty() || minDate.isBlank())
				newMinDate = newMaxDate.minusYears(1L);
			else
				newMinDate = LocalDate.parse(minDate);
		}else{
			newMaxDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
			newMinDate = newMaxDate.minusYears(1L);
		}

		return sellerRepository.getSellerWithDealsByTime(newMinDate,newMaxDate)
				.stream()
				.map(SellerMinDTO::new)
				.toList();
	}

	@Transactional(readOnly = true)
	public Page<SaleMinDTO> getReport(String minDate, String maxDate,String sellerName,Pageable pageable){
		LocalDate newMinDate = null;
		LocalDate newMaxDate = null;
		if(maxDate !=null  && minDate!=null) {
			if (maxDate.isEmpty() || maxDate.isBlank())
				newMaxDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
			else
				newMaxDate = LocalDate.parse(maxDate);

			if (minDate.isEmpty() || minDate.isBlank())
				newMinDate = newMaxDate.minusYears(1L);
			else
				newMinDate = LocalDate.parse(minDate);
		}else{
			newMaxDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
			newMinDate = newMaxDate.minusYears(1L);
		}

		return repository.findSaleByDateAndSeller(newMinDate,newMaxDate,sellerName,pageable)
				.map(SaleMinDTO::new);
	}

/*	private void stringToLocalDate(String oldMinDate, String oldMaxDate,LocalDate newMinDate,LocalDate newMaxDate){
		if(oldMaxDate !=null  && oldMinDate!=null) {
			if (oldMaxDate.isEmpty() || oldMaxDate.isBlank())
				newMaxDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
			else
				newMaxDate = LocalDate.parse(oldMaxDate);

			if (oldMinDate.isEmpty() || oldMinDate.isBlank())
				newMinDate = newMaxDate.minusYears(1L);
			else
				newMinDate = LocalDate.parse(oldMinDate);
		}else{
			newMaxDate = LocalDate.ofInstant(Instant.now(), ZoneId.systemDefault());
			newMinDate = newMaxDate.minusYears(1L);
		}
	}*/
}
