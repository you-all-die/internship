package com.example.internship.dto.product;

import lombok.AllArgsConstructor;
import lombok.Value;

public enum ProductPageDto {
    ;

    private interface Number {
        int getNumber();
    }

    private interface Active {
        boolean isActive();
    }

    private interface Anchor {
        boolean isAnchor();
    }

    public enum Response {
        ;

        @Value
        @AllArgsConstructor
        public static class All implements Number, Active, Anchor {
            int number;
            boolean active;
            boolean anchor;
        }
    }
}
