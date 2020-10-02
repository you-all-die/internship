package com.example.internship.refactoringdto;

/**
 * @author Modenov D.A
 */

public class View {

    /**
     * Устанавливается для основных полей, включая id.
     */
    public interface Public {
    }

    /**
     * Устанавливается для полей, которые можно редактировать.
     */
    public interface Update {
    }

    /**
     * Устанавливается для показа всех полей, за исключением Private полей.
     */
    public interface All extends Public {
    }

    /**
     * Устанавливается для секретных полей, которые не стоит редактировать и/или видеть.
     */
    public interface Private extends All {
    }

}
