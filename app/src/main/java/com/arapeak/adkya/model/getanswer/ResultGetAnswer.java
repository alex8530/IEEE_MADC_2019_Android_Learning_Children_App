package com.arapeak.adkya.model.getanswer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ResultGetAnswer{

        @SerializedName("status")
        @Expose
        private Boolean status;
        @SerializedName("data")
        @Expose
        private Answer data;

        public Boolean getStatus() {
            return status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }

        public Answer getData() {
            return data;
        }

        public void setData(Answer data) {
            this.data = data;
        }

}
