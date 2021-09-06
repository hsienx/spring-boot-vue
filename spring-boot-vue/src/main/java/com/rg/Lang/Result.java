package com.rg.Lang;

public class Result {
	//相應碼
    private int code;
    //資訊
    private String message;
    //返回資料
    private Object data;

    public static Result success(Object data) {
    	return success(200,"操作成功",data);
    }
	private static Result success(int code, String message, Object data) {
		Result result =new Result();
		result.setCode(code);
		result.setMessage(message);
		result.setData(data);
		return result;
	}
	
	public static  Result fail(String message) {
		return fail(400,message,null);
	}
	
	private static  Result fail(int code, String message,Object data) {
		Result result =new Result();
		result.setCode(code);
		result.setMessage(message);
		result.setData(data);
		return result;
	}
	
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public Result(int code, String message, Object data) {
		super();
		this.code = code;
		this.message = message;
		this.data = data;
	}
	public Result() {
		super();
		// TODO Auto-generated constructor stub
	}

    
	
}
