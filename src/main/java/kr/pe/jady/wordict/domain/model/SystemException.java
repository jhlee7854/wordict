package kr.pe.jady.wordict.domain.model;

import javax.persistence.*;

@Entity(name = "system_exceptions")
public class SystemException {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String exceptionClassName;
    private String message;
    @Lob
    private String stackTrace;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getExceptionClassName() {
        return exceptionClassName;
    }

    public void setExceptionClassName(String exceptionClassName) {
        this.exceptionClassName = exceptionClassName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(String stackTrace) {
        this.stackTrace = stackTrace;
    }

    @Override
    public String toString() {
        return "SystemException{" +
                "id=" + id +
                ", exceptionClassName='" + exceptionClassName + '\'' +
                ", message='" + message + '\'' +
                ", stackTrace='" + stackTrace + '\'' +
                '}';
    }
}
