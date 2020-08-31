package com.example.internship.dto.category;

import lombok.Data;

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
        long getParentId();
        void setParentId(long parentId);
    }

    private interface Priority {
        long getPriority();
        void setPriority(long priority);
    }

    @Data
    public static class Full implements Id, Name, ParentId, Priority {
        private long id;
        private String name;
        private long parentId;
        private long priority;
    }

    @Data
    public static class IdAndName implements Id, Name {
        private long id;
        private String name;
    }
}
