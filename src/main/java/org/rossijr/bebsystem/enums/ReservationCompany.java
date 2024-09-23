package org.rossijr.bebsystem.enums;

public enum ReservationCompany {
    AIRBNB("Airbnb", "ABB"),
    BOOKING("Booking", "BKG"),
    EXPEDIA("Expedia", "EXP"),
    TRIVAGO("Trivago", "TRV"),
    HOTELS("Hotels", "HTL"),
    HOTELURBANO("Hotel Urbano", "HUR"),
    DECOLAR("Decolar", "DCL"),
    CLOUDBEDS("CloudBeds", "CBD"),
    INTERNAL("Internal", "INT"),
    OTHER("Other", "OTH");

    private final String name;
    private final String abbreviation;

    public String getName() {
        return name;
    }

    public String getAbbreviation() {
        return abbreviation;
    }

    private ReservationCompany(String name, String abbreviation) {
        this.name = name;
        this.abbreviation = abbreviation;
    }

}
