package com.example.demo.exception;

public class CustomException extends RuntimeException{
	private String errCode;
	private String errMsg;
	
	public CustomException(String errCode, String errMsg) {
		super();
		this.errCode = errCode;
		this.errMsg = errMsg;
	}
	
	public String getErrCode() {
		return errCode;
	}

	public String getErrMsg() {
		return errMsg;
	}

	

}
