package com.gomezortiz.transactionsmicro.shared.domain;

import java.util.UUID;

public final class IdMother {
    public static String random() {
        return UUID.randomUUID().toString();
    }
}
