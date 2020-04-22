package com.gomezortiz.transactionsmicro.shared.domain.model;

public final class DoubleMother {
    public static Double random(int positions, int min, int max) {
        return MotherCreator.random().number().randomDouble(positions, min, max);
    }
}
