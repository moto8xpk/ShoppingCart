package com.shoppingcart.server.model;

import javax.persistence.*;

@Entity
@Table(name = "sc_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    //not done yet

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "cateid")
    private ProductCategory productCategory;

    @Column(name = "price")
    private long price;

    @Column(name = "description")
    private String desc;

    @Column(name = "imagelink")
    private String imagelink;

    public Product() {
    }

    public Product(String name, ProductCategory productCategory, long price, String desc, String imagelink) {
        this.name = name;
        this.productCategory = productCategory;
        this.price = price;
        this.desc = desc;
        this.imagelink = imagelink;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ProductCategory getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(ProductCategory productCategory) {
        this.productCategory = productCategory;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getImagelink() {
        return imagelink;
    }

    public void setImagelink(String imagelink) {
        this.imagelink = imagelink;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", productCategory=" + productCategory +
                ", price=" + price +
                ", desc='" + desc + '\'' +
                ", imagelink='" + imagelink + '\'' +
                '}';
    }
}
