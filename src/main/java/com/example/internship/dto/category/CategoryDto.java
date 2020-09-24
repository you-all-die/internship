package com.example.internship.dto.category;

import com.example.internship.entity.Category;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * @author Самохвалов Юрий Алексеевич
 */
public enum CategoryDto {
    ;

    public enum Request {
        ;

        @Data
        public static class All implements Id, Name, ParentId {
            private Long id;
            private String name;
            private Long parentId;
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
        public static class AllWithSubcategories implements Id, Name, Subcategories {
            private Long id;
            private String name;
            private List<AllWithSubcategories> subcategories;
        }

        @Data
        @EqualsAndHashCode(callSuper = true)
        public static class AllWithParent extends All implements Parent {
            private Category parent;
        }
    }

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

    private interface Subcategories {
        List<Response.AllWithSubcategories> getSubcategories();
    }
}
