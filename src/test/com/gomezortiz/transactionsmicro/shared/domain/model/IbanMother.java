package com.gomezortiz.transactionsmicro.shared.domain.model;

public final class IbanMother {
    public static String random(String countryCode) {
        return null != countryCode
                ? MotherCreator.random().finance().iban(countryCode)
                : MotherCreator.random().finance().iban();
    }
}
