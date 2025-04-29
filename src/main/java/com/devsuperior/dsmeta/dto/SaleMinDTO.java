package com.devsuperior.dsmeta.dto;

import java.time.LocalDate;

import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.projection.SalerProjection;

public class SaleMinDTO {

	private Long id;
	private Double amount;
	private LocalDate date;
	private String sellerName;
	
	public SaleMinDTO(Long id, Double amount, LocalDate date) {
		this.id = id;
		this.amount = amount;
		this.date = date;
	}

	
	public SaleMinDTO(Sale entity) {
		id = entity.getId();
		amount = entity.getAmount();
		date = entity.getDate();
	}

	public SaleMinDTO(SalerProjection entity) {
		id = entity.getId();
		amount = entity.getQuantidade();
		date = LocalDate.parse(entity.getDia());
		sellerName = entity.getNome();
	}

	public Long getId() {
		return id;
	}

	public Double getAmount() {
		return amount;
	}

	public LocalDate getDate() {
		return date;
	}

	public String getSellerName() {
		return sellerName;
	}
}
