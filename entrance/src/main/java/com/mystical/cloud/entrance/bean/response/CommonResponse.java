package com.mystical.cloud.entrance.bean.response;



public class CommonResponse<T> extends BasePojo {
    private String code;
    private String msg;
    private T data;

    public CommonResponse() {
    }

    public CommonResponse(ICommonEnum commonEnum) {
        this.code = commonEnum.getCode();
        this.msg = commonEnum.getMsg();
    }

    public CommonResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CommonResponse(ICommonEnum commonEnum, T data) {
        this(commonEnum);
        this.data = data;
    }

    public String getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

    public T getData() {
        return this.data;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof CommonResponse)) {
            return false;
        } else {
            CommonResponse<?> other = (CommonResponse)o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                label47: {
                    Object this$code = this.getCode();
                    Object other$code = other.getCode();
                    if (this$code == null) {
                        if (other$code == null) {
                            break label47;
                        }
                    } else if (this$code.equals(other$code)) {
                        break label47;
                    }

                    return false;
                }

                Object this$msg = this.getMsg();
                Object other$msg = other.getMsg();
                if (this$msg == null) {
                    if (other$msg != null) {
                        return false;
                    }
                } else if (!this$msg.equals(other$msg)) {
                    return false;
                }

                Object this$data = this.getData();
                Object other$data = other.getData();
                if (this$data == null) {
                    if (other$data != null) {
                        return false;
                    }
                } else if (!this$data.equals(other$data)) {
                    return false;
                }

                return true;
            }
        }
    }

    protected boolean canEqual(Object other) {
        return other instanceof CommonResponse;
    }

    public int hashCode() {
        boolean PRIME = true;
        int result = 1;
        Object $code = this.getCode();
         result = result * 59 + ($code == null ? 43 : $code.hashCode());
        Object $msg = this.getMsg();
        result = result * 59 + ($msg == null ? 43 : $msg.hashCode());
        Object $data = this.getData();
        result = result * 59 + ($data == null ? 43 : $data.hashCode());
        return result;
    }

    public String toString() {
        return "CommonResponse(code=" + this.getCode() + ", msg=" + this.getMsg() + ", data=" + this.getData() + ")";
    }
}

