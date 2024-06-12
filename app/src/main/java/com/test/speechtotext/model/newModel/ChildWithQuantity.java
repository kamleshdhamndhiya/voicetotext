package com.test.speechtotext.model.newModel;

import java.io.Serializable;

public class ChildWithQuantity implements Serializable {
    int quantity;

    Child child;

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }
}
