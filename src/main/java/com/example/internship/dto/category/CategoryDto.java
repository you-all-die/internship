package com.example.internship.dto.category;

import com.example.internship.entity.Category;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

/**
 * @author Самохвалов Юрий Алексеевич
 */
public enum CategoryDto {
    ;

    private interface Id {
        Long getId();
        void setId(Long id);
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

    private interface ParentName {
        String getParentName();
        void setParentName(String parentName);
    }

    private interface Subcategories {
        List<Category> getSubcategories();
    }

    private interface Ancestors {
        List<Response.IdOnly> getAncestors();
    }

    private interface Descendants {
        List<Response.IdOnly> getDescendants();
    }

    public enum Request {
        ;

        @Data
        public static class All implements Id, Name, ParentId, ParentName {
            private Long id;
            private String name;
            private Long parentId;
            private String parentName;
        }
    }

    public enum Response {
        ;

        @Data
        public static class All implements Id, Name {
            private Long id;
            private String name;
        }

        @Data
        @EqualsAndHashCode(callSuper = true)
        public static class AllWithParentId extends All implements ParentId {
            private Long parentId;
        }

        @Data
        @EqualsAndHashCode(callSuper = true)
        public static class AllWithParentIdParentName extends All implements ParentId, ParentName {
            private Long parentId;
            private String parentName;
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
        public static class AllWithParentSubcategoriesAncestors extends AllWithParentSubcategories implements Ancestors {
            @Getter private List<IdOnly> ancestors;
        }

        @Data
        public static class IdOnly implements Id {
            private Long id;
        }
    }
}
