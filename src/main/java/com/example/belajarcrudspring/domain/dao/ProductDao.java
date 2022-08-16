package com.example.belajarcrudspring.domain.dao;

import javax.persistence.*;

@Entity
@Table(name = "Products")
public class ProductDao {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_description")
    private String productDescription;
    public ProductDao() {
    }
    public ProductDao(String product_name, String product_description)
    {
        this.productName = product_name;
        this.productDescription = product_description;
    }

    public long getId(){
        return id;
    }

    public String getProductName() {
        return productName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public void setProductName(String product_name) {
        this.productName = product_name;
    }

    public void setProductDescription(String product_description) {
        this.productDescription = product_description;
    }

    public String toString() {
        return "Tutorial [id=" + id + ", product_name=" + productName + ", desc=" + productDescription +"]";
    }

}
