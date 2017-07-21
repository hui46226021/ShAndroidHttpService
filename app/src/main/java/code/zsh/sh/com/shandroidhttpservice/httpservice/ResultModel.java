package code.zsh.sh.com.shandroidhttpservice.httpservice;

/**
 * Created by zhush on 2017/6/16.
 * Email 405086805@qq.com
 */

public class ResultModel {
    boolean success;
    String errorMessage;
    Object data;

    public ResultModel(boolean success) {
        this.success = success;
        this.errorMessage=success+"";
    }
    public ResultModel() {
        this.success = true;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
