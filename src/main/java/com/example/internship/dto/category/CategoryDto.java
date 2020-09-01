package com.example.internship.dto.category;

import com.example.internship.entity.Category;
import lombok.*;

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

    private interface ParentId {
        Long getParentId();
        void setParentId(Long parentId);
    }

    private interface ParentName {
        String getParentName();
        void setParentName(String parentName);
    }

    private interface Priority {
        Long getPriority();
        void setPriority(Long priority);
    }

    /* Пока пустой, но при необходимости допишем */
    public enum Request {
        ;

        @Data
        public static class Full implements Id, Name, ParentId, Priority {
            private Long id;
            private String name;
            private Long parentId;
            private Long priority;
        }
    }

    public enum Response {
        ;

        @Data
        public static class Full implements Id, Name, ParentId, ParentName, Priority {
            private Long id;
            private String name;
            private Long parentId;
            private String parentName;
            private Long priority;
        }

        @Data
        public static class FullNoParentName implements Id, Name, ParentId, Priority {
            private Long id;
            private String name;
            private Long parentId;
            private Long priority;
        }

        @Data
        public static class IdAndName implements Id, Name {
            private Long id;
            private String name;
        }
    }
}
