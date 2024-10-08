package com.example.blog.exception;

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
/*
* status -> 400
* */
public class InvalidRequest extends HodologException{
    private static final String MESSAGE = "잘못된 요청입니다.";
    public InvalidRequest() {super(MESSAGE);}

    public InvalidRequest(String fieldName, String message) {
        super(MESSAGE);
        addValidation(fieldName, message);
    }

    @Override
    public int getStatusCode() {
        return 400;
    }
}
