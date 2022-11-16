package com.teamride.messenger.server.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public final class RestResponse {
	private int errno;
	private String errmsg;
	private Object data;

	/**
	 * 정상 리턴인 경우
	 * 
	 * @param data
	 */
	public RestResponse(Object data) {
		super();
		this.errno = 0;
		this.data = data;
	}

	/**
	 * 실패 리턴인 경우
	 * 
	 * @param errno
	 * @param errmsg
	 * @param data
	 */
	public RestResponse(int errno, String errmsg, Object data) {
		super();
		this.errno = errno;
		this.errmsg = errmsg;
		this.data = data;
	}

}
