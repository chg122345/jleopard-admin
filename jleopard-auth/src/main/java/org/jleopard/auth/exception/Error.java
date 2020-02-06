/**
 * @author (c) 2019. Chen_9g jleopard@126.com.
 * @date 2020/2/2  19:59
 * @version 1.0
 *
 * <p>
 * Find a way for success and not make excuses for failure.
 * </p>
 */
package org.jleopard.auth.exception;

import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Data
public class Error {

    private Integer status = 401;

    private Long timestamp;

    private String message;

    private Error() {
        this.timestamp = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
    }

    private Error(String message) {
        this.timestamp = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        this.message = message;
    }

    private Error(Integer status, String message) {
        this.timestamp = LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli();
        this.status = status;
        this.message = message;
    }

    public static Error error(String message){
        return new Error(message);
    }

    public static Error error(Integer status, String message){
        return new Error(status, message);
    }
}
