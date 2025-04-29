package com.devsuperior.dsmeta.dto;

import com.devsuperior.dsmeta.projection.SellerProjection;

public class SellerMinDTO {

    private String sellerName;
    private Double total;

    public SellerMinDTO() {
    }

    public SellerMinDTO(String sellerName, Double total) {
        this.sellerName = sellerName;
        this.total = total;
    }

    public SellerMinDTO(SellerProjection projection) {
        this.sellerName = projection.getNome();
        this.total = projection.getSoma();
    }

    public String getSellerName() {
        return sellerName;
    }

    public Double getTotal() {
        return total;
    }
}
