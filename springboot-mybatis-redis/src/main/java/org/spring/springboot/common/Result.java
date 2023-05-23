package org.spring.springboot.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data

@AllArgsConstructor
public class Result {
    private Boolean success;
    private String errorMsg;
    private Object data;
    private Long total;

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }


    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Result() {
        this.success = true;
    }

}

