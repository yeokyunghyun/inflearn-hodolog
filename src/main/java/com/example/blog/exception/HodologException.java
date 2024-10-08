package com.example.blog.exception;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

/**
 * packageName    : com.example.blog.exception
 * fileName       :
 * author         : njy
 * date           : 2024-10-07
 * description    : <<여기 설명>>
 * <pre>
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2024-10-07           njy            최초 생성
 * </pre>
 */
@Getter
public abstract class HodologException extends RuntimeException{

    private final Map<String, String> validation = new HashMap<>();

    public HodologException(String message) {
        super(message);
    }

    public HodologException(String message, Throwable cause) {
        super(message, cause);
    }

    public abstract int getStatusCode();

    public void addValidation(String fieldName, String message) {
        validation.put(fieldName, message);
    }
}
