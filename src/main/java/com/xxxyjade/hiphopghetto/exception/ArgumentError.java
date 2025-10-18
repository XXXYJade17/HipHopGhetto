package com.xxxyjade.hiphopghetto.exception;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ArgumentError {

    /**
     * 参数名
     */
	private String argumentName;

    /**
     * 错误信息
     */
	private String message;

}
