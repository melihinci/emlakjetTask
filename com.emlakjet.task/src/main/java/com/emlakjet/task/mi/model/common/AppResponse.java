package com.emlakjet.task.mi.model.common;

import java.io.Serializable;

public class AppResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean isSuccess;
    private String resultDescription;

    public AppResponse() {
    }

    public AppResponse(boolean isSuccess, String resultDescription) {
        this.isSuccess = isSuccess;
        this.resultDescription = resultDescription;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public String getResultDescription() {
        return resultDescription;
    }

    public void setResultDescription(String resultDescription) {
        this.resultDescription = resultDescription;
    }

}
