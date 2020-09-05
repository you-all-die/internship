package com.example.internship.dto.category;

import com.example.internship.entity.Category;
import lombok.Data;
import lombok.EqualsAndHashCode;

public enum CategoryDto {
    ;

    private interface Id {
        long getId();
        void setId(long id);
    }

    private interface Name {
        String getName();
        void setName(String name);
    }

    private interface Parent {
        Category getParent();
        void setParent(Category parent);
    }

    private interface ParentId {
        Long getParentId();
        void setParentId(Long parentId);
    }

    public enum Request {
        ;

        @Data
        public static class All implements Id, Name, ParentId {
            private long id;
            private String name;
            private Long parentId;
        }
    }

    public enum Response {
        ;

        @Data
        public static class All implements Id, Name {
            private long id;
            private String name;
        }

        @Data
        @EqualsAndHashCode(callSuper = true)
        public static class AllWithParentId extends All implements ParentId {
            private Long parentId;
        }

        @Data
        @EqualsAndHashCode(callSuper = true)
        public static class AllWithParent extends All implements Parent {
            private Category parent;
        }
    }
}
