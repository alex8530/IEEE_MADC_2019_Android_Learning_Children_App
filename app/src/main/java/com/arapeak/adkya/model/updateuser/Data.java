package com.arapeak.adkya.model.updateuser;

import com.arapeak.adkya.model.AccountType;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("gender_id")
    @Expose
    private String genderId;
    @SerializedName("provider_id")
    @Expose
    private Object providerId;
    @SerializedName("provider_type_id")
    @Expose
    private Integer providerTypeId;
    @SerializedName("account_type_id")
    @Expose
    private Integer accountTypeId;
    @SerializedName("class_id")
    @Expose
    private String classId;
    @SerializedName("username")
    @Expose
    private Object username;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private Object mobile;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("remember_token")
    @Expose
    private Object rememberToken;
    @SerializedName("birth_date")
    @Expose
    private Object birthDate;
    @SerializedName("is_updated")
    @Expose
    private Integer isUpdated;
    @SerializedName("is_completed")
    @Expose
    private Integer isCompleted;
    @SerializedName("is_generated")
    @Expose
    private Integer isGenerated;
    @SerializedName("generated_group_id")
    @Expose
    private Integer generatedGroupId;
    @SerializedName("end_at")
    @Expose
    private Object endAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private Object deletedAt;
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
    private Object endDate;
    @SerializedName("address")
    @Expose
    private Address address;
    @SerializedName("account_type")
    @Expose
    private AccountType accountType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGenderId() {
        return genderId;
    }

    public void setGenderId(String genderId) {
        this.genderId = genderId;
    }

    public Object getProviderId() {
        return providerId;
    }

    public void setProviderId(Object providerId) {
        this.providerId = providerId;
    }

    public Integer getProviderTypeId() {
        return providerTypeId;
    }

    public void setProviderTypeId(Integer providerTypeId) {
        this.providerTypeId = providerTypeId;
    }

    public Integer getAccountTypeId() {
        return accountTypeId;
    }

    public void setAccountTypeId(Integer accountTypeId) {
        this.accountTypeId = accountTypeId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public Object getUsername() {
        return username;
    }

    public void setUsername(Object username) {
        this.username = username;
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

    public Object getMobile() {
        return mobile;
    }

    public void setMobile(Object mobile) {
        this.mobile = mobile;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Object getRememberToken() {
        return rememberToken;
    }

    public void setRememberToken(Object rememberToken) {
        this.rememberToken = rememberToken;
    }

    public Object getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Object birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getIsUpdated() {
        return isUpdated;
    }

    public void setIsUpdated(Integer isUpdated) {
        this.isUpdated = isUpdated;
    }

    public Integer getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Integer isCompleted) {
        this.isCompleted = isCompleted;
    }

    public Integer getIsGenerated() {
        return isGenerated;
    }

    public void setIsGenerated(Integer isGenerated) {
        this.isGenerated = isGenerated;
    }

    public Integer getGeneratedGroupId() {
        return generatedGroupId;
    }

    public void setGeneratedGroupId(Integer generatedGroupId) {
        this.generatedGroupId = generatedGroupId;
    }

    public Object getEndAt() {
        return endAt;
    }

    public void setEndAt(Object endAt) {
        this.endAt = endAt;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Object getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        this.deletedAt = deletedAt;
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

    public Object getEndDate() {
        return endDate;
    }

    public void setEndDate(Object endDate) {
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
