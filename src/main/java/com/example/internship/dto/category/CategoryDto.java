package com.example.internship.dto.category;

import com.example.internship.entity.Category;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

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

    private interface Subcategories {
        List<Category> getSubcategories();
        void setSubcategories(List<Category> subcategories);
    }

    private interface Descendants {
        List<Response.IdOnly> getDescendants();
        void setDescendants(List<Response.IdOnly> descendants);
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

        @Data
        @EqualsAndHashCode(callSuper = true)
        public static class AllWithParentSubcategories extends AllWithParent implements Subcategories {
            private List<Category> subcategories;
        }

        @Data
        @EqualsAndHashCode(callSuper = true)
        public static class AllWithParentSubcategoriesDescendants extends AllWithParentSubcategories implements Descendants {
            private List<IdOnly> descendants;
        }

        @Data
        public static class IdOnly implements Id {
            private long id;
        }
    }
}
