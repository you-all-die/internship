package com.example.internship.dto.dadata;

import lombok.Data;

public enum DadataAddressDto {
    ;

    public enum Request {
        ;

        @Data
        public static class QueryOnly implements Query {
            private String query;
        }
    }

    public enum Response {
        ;

        @Data
        public static class ValueOnly implements Value {
            private String value;
        }

        @Data
        public static class WithUnrestrictedValue implements Value, UnrestrictedValue {
            private String value;
            private String unrestrictedValue;
        }
    }

    private interface Query {
        String getQuery();
        void setQuery(String query);
    }

    private interface Count {
        Integer getCount();
        void setCount(Integer count);
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
