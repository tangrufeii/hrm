package cn.itsource.constants;

/**
 * @description:
 * @auth: tangrufei
 * @date: 2022-08-26 14:16
 */
public enum ErrorCode {

    SYSTEM_ERROR("1001", "系统内部错误"),
    ERROR_1002("1002", "身份证号重复，请更改");

    //错误码
    private String code;

    //错误信息
    private String message;

    ErrorCode(String code, String message){
        this.code = code;
        this.message = message;
    }

    public String getCode(){
        return code;
    }

    public String getMessage(){
        return message;
    }
}