package com.example.internship.dto.category;

import com.example.internship.entity.Category;
import lombok.*;

/**
 * @author Самохвалов Юрий Алексеевич
 */
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

    private interface ParentId {
        Long getParentId();
        void setParentId(Long parentId);
    }

    private interface ParentName {
        String getParentName();
        void setParentName(String parentName);
    }

    private interface Priority {
        long getPriority();
        void setPriority(long priority);
    }

    /* Пока пустой, но при необходимости допишем */
    public enum Request {
        ;
    }

    public enum Response {
        ;

        @Data
        public static class Full implements Id, Name, ParentId, Priority {
            private long id;
            private String name;
            private Long parentId;
            private long priority;
        }

        @Data
        public static class FullWithParentName implements Id, Name, ParentId, ParentName, Priority {
            private long id;
            private String name;
            private Long parentId;
            private String parentName;
            private long priority;
        }

        @Data
        public static class IdAndName implements Id, Name {
            private long id;
            private String name;
        }
    }
}
