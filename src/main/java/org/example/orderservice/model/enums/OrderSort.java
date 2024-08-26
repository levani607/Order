package org.example.orderservice.model.enums;

import lombok.Getter;

@Getter
public enum OrderSort {

    ID("id"),
    PRODUCT("product");

    private final String value;

    OrderSort(String value) {
        this.value = value;
    }
}
