package com.arapeak.adkya.model.register;

import com.arapeak.adkya.model.AccountType;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {


    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("provider_type_id")
    @Expose
    private Integer providerTypeId;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("total_time")
    @Expose
    private String totalTime;
    @SerializedName("general_level")
    @Expose
    private String generalLevel;
    @SerializedName("general_level_text")
    @Expose
    private String generalLevelText;
    @SerializedName("last_week")
    @Expose
    private Integer lastWeek;
    @SerializedName("is_paid")
    @Expose
    private Boolean isPaid;
    @SerializedName("is_trial")
    @Expose
    private Boolean isTrial;
    @SerializedName("end_date")
    @Expose
    private String endDate;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("account_type")
    @Expose
    private AccountType accountType;

    public User() {
    }

    public User(Integer id, String name, String email, Integer providerTypeId, String updatedAt, String createdAt, String totalTime, String generalLevel, String generalLevelText, Integer lastWeek, Boolean isPaid, Boolean isTrial, String endDate, Address address, AccountType accountType) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.providerTypeId = providerTypeId;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
        this.totalTime = totalTime;
        this.generalLevel = generalLevel;
        this.generalLevelText = generalLevelText;
        this.lastWeek = lastWeek;
        this.isPaid = isPaid;
        this.isTrial = isTrial;
        this.endDate = endDate;
        this.address = address;
        this.accountType = accountType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getProviderTypeId() {
        return providerTypeId;
    }

    public void setProviderTypeId(Integer providerTypeId) {
        this.providerTypeId = providerTypeId;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(String totalTime) {
        this.totalTime = totalTime;
    }

    public String getGeneralLevel() {
        return generalLevel;
    }

    public void setGeneralLevel(String generalLevel) {
        this.generalLevel = generalLevel;
    }

    public String getGeneralLevelText() {
        return generalLevelText;
    }

    public void setGeneralLevelText(String generalLevelText) {
        this.generalLevelText = generalLevelText;
    }

    public Integer getLastWeek() {
        return lastWeek;
    }

    public void setLastWeek(Integer lastWeek) {
        this.lastWeek = lastWeek;
    }

    public Boolean getIsPaid() {
        return isPaid;
    }

    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }

    public Boolean getIsTrial() {
        return isTrial;
    }

    public void setIsTrial(Boolean isTrial) {
        this.isTrial = isTrial;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

}
