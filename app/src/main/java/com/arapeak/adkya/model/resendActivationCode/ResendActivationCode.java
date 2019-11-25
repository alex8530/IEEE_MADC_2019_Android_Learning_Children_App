package com.arapeak.adkya.model.resendActivationCode;

public class ResendActivationCode {
    private boolean status;

    public ResendActivationCode(boolean status) {
        this.status = status;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
