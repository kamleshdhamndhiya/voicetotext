package com.test.speechtotext.model.newModel.Product;

import java.io.Serializable;

public class ProductWithQuantity implements Serializable {

    int quantity;

    Product product;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
