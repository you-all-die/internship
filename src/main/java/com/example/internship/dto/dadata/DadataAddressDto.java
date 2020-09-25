package com.example.internship.dto.dadata;

import lombok.Data;
import lombok.experimental.UtilityClass;

@UtilityClass
public class DadataAddressDto {

    @Data
    public static class QueryOnly implements Query {
        private String query;
    }

    @Data
    public static class ValueOnly implements Value {
        private String value;
    }

    @Data
    public static class WithUnrestrictedValue implements Value, UnrestrictedValue {
        private String value;
        private String unrestrictedValue;
    }

    private interface Query {
        String getQuery();
        void setQuery(String query);
    }

    private interface Value {
        String getValue();
        void setValue(String value);
    }

    private interface UnrestrictedValue {
        String getUnrestrictedValue();
        void setUnrestrictedValue(String unrestrictedValue);
    }
}
