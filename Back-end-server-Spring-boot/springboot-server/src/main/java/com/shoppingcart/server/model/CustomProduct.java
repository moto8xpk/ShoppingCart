package com.shoppingcart.server.model;
import javax.persistence.*;

@Entity
@Table(name = "sc_product")
public class CustomProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "cateid")
    private long cateid;

    @Column(name = "price")
    private long price;

    @Column(name = "description")
    private String description;

    @Column(name = "imagelink")
    private String imagelink;

    public CustomProduct() {
    }

    public CustomProduct(String name, long cateid, long price, String desc, String imagelink) {
        this.name = name;
        this.cateid = cateid;
        this.price = price;
        this.description = desc;
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


    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getDesc() {
        return description;
    }

    public void setDesc(String desc) {
        this.description = desc;
    }

    public String getImagelink() {
        return imagelink;
    }

    public void setImagelink(String imagelink) {
        this.imagelink = imagelink;
    }

    public long getCateid() {
        return cateid;
    }

    public void setCateid(long cateid) {
        this.cateid = cateid;
    }

    @Override
    public String toString() {
        return "CustomProduct{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cateid=" + cateid +
                ", price=" + price +
                ", desc='" + description + '\'' +
                ", imagelink='" + imagelink + '\'' +
                '}';
    }
}
