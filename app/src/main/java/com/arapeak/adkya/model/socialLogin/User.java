package com.arapeak.adkya.model.socialLogin;

import com.arapeak.adkya.model.AccountType;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("gender_id")
    @Expose
    private Integer genderId;
    @SerializedName("provider_id")
    @Expose
    private String providerId;


    @SerializedName("provider_type_id")
    @Expose
    private Integer providerTypeId;

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @SerializedName("address")
    @Expose
    private Address address;

    @SerializedName("account_type_id")
    @Expose
    private Integer accountTypeId;
    @SerializedName("class_id")
    @Expose
    private Integer classId;
    @SerializedName("username")
    @Expose
    private String username;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("mobile")
    @Expose
    private Integer mobile;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("remember_token")
    @Expose
    private String rememberToken;
    @SerializedName("birth_date")
    @Expose
    private String birthDate;
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
    private String endAt;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("deleted_at")
    @Expose
    private String deletedAt;
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
    @SerializedName("account_type")
    @Expose
    private AccountType accountType;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGenderId() {
        return genderId;
    }

    public void setGenderId(Integer genderId) {
        this.genderId = genderId;
    }

    public String getProviderId() {
        return providerId;
    }

    public void setProviderId(String providerId) {
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

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
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

    public Integer getMobile() {
        return mobile;
    }

    public void setMobile(Integer mobile) {
        this.mobile = mobile;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRememberToken() {
        return rememberToken;
    }

    public void setRememberToken(String rememberToken) {
        this.rememberToken = rememberToken;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
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

    public String getEndAt() {
        return endAt;
    }

    public void setEndAt(String endAt) {
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

    public String getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(String deletedAt) {
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

    public Boolean getPaid() {
        return isPaid;
    }

    public void setPaid(Boolean paid) {
        isPaid = paid;
    }

    public Boolean getTrial() {
        return isTrial;
    }

    public void setTrial(Boolean trial) {
        isTrial = trial;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
}
