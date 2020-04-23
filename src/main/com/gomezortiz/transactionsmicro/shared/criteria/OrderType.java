package com.gomezortiz.transactionsmicro.shared.criteria;

import lombok.RequiredArgsConstructor;

public enum OrderType {
    ASC("asc"),
    DESC("desc"),
    NONE("none");

    private final String type;

    OrderType(String type) {
        this.type = type;
    }

    public String value() {
        return type;
    }
}