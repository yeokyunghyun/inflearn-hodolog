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
* status -> 404
* */
public class PostNotFound extends HodologException{

    private static final String MESSAGE = "존재하지 않는 ID입니다.";
    public PostNotFound() {
        super(MESSAGE);
    }

    public PostNotFound(Throwable cause) {
        super(MESSAGE, cause);
    }

    @Override
    public int getStatusCode() {
        return 404;
    }
}
