package com.arapeak.adkya.model.getStatistics;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Statistic {
    public String getGeneralStudentRate() {
        return generalStudentRate;
    }

    public void setGeneralStudentRate(String generalStudentRate) {
        this.generalStudentRate = generalStudentRate;
    }

    public String getGeneralStudentRateText() {
        return generalStudentRateText;
    }

    public void setGeneralStudentRateText(String generalStudentRateText) {
        this.generalStudentRateText = generalStudentRateText;
    }

    public String getSittingInteractingRate() {
        return sittingInteractingRate;
    }

    public void setSittingInteractingRate(String sittingInteractingRate) {
        this.sittingInteractingRate = sittingInteractingRate;
    }

    public Double getDailySectionsResolved() {
        return dailySectionsResolved;
    }

    public void setDailySectionsResolved(Double dailySectionsResolved) {
        this.dailySectionsResolved = dailySectionsResolved;
    }

    public Double getDailyExercisesResolved() {
        return dailyExercisesResolved;
    }

    public void setDailyExercisesResolved(Double dailyExercisesResolved) {
        this.dailyExercisesResolved = dailyExercisesResolved;
    }

    public Double getDailyCorrectAnswersRate() {
        return dailyCorrectAnswersRate;
    }

    public void setDailyCorrectAnswersRate(Double dailyCorrectAnswersRate) {
        this.dailyCorrectAnswersRate = dailyCorrectAnswersRate;
    }

    public Double getWeeklySectionsResolved() {
        return weeklySectionsResolved;
    }

    public void setWeeklySectionsResolved(Double weeklySectionsResolved) {
        this.weeklySectionsResolved = weeklySectionsResolved;
    }

    public Double getWeekExercises() {
        return weekExercises;
    }

    public void setWeekExercises(Double weekExercises) {
        this.weekExercises = weekExercises;
    }

    public Double getWeeklyCorrectAnswersRate() {
        return weeklyCorrectAnswersRate;
    }

    public void setWeeklyCorrectAnswersRate(Double weeklyCorrectAnswersRate) {
        this.weeklyCorrectAnswersRate = weeklyCorrectAnswersRate;
    }

    public Double getMonthlySectionsResolved() {
        return monthlySectionsResolved;
    }

    public void setMonthlySectionsResolved(Double monthlySectionsResolved) {
        this.monthlySectionsResolved = monthlySectionsResolved;
    }

    public Double getMonthlyExercisesResolved() {
        return monthlyExercisesResolved;
    }

    public void setMonthlyExercisesResolved(Double monthlyExercisesResolved) {
        this.monthlyExercisesResolved = monthlyExercisesResolved;
    }

    public Double getMonthlyCorrectAnswersRate() {
        return monthlyCorrectAnswersRate;
    }

    public void setMonthlyCorrectAnswersRate(Double monthlyCorrectAnswersRate) {
        this.monthlyCorrectAnswersRate = monthlyCorrectAnswersRate;
    }

    public Double getTotalSection() {
        return totalSection;
    }

    public void setTotalSection(Double totalSection) {
        this.totalSection = totalSection;
    }

    public Double getTotalExercises() {
        return totalExercises;
    }

    public void setTotalExercises(Double totalExercises) {
        this.totalExercises = totalExercises;
    }

    public Double getTotalAverage() {
        return totalAverage;
    }

    public void setTotalAverage(Double totalAverage) {
        this.totalAverage = totalAverage;
    }

    @SerializedName("general_student_rate")
    @Expose
    private String generalStudentRate;
    @SerializedName("general_student_rate_text")
    @Expose
    private String generalStudentRateText;
    @SerializedName("sitting_interacting_rate")
    @Expose
    private String sittingInteractingRate;
    @SerializedName("daily_sections_resolved")
    @Expose
    private Double dailySectionsResolved;
    @SerializedName("daily_exercises_resolved")
    @Expose
    private Double dailyExercisesResolved;
    @SerializedName("daily_correct_answers_rate")
    @Expose
    private Double dailyCorrectAnswersRate;
    @SerializedName("weekly_sections_resolved")
    @Expose
    private Double weeklySectionsResolved;
    @SerializedName("week_exercises")
    @Expose
    private Double weekExercises;
    @SerializedName("weekly_correct_answers_rate")
    @Expose
    private Double weeklyCorrectAnswersRate;
    @SerializedName("monthly_sections_resolved")
    @Expose
    private Double monthlySectionsResolved;
    @SerializedName("monthly_exercises_resolved")
    @Expose
    private Double monthlyExercisesResolved;
    @SerializedName("monthly_correct_answers_rate")
    @Expose
    private Double monthlyCorrectAnswersRate;
    @SerializedName("total_section")
    @Expose
    private Double totalSection;
    @SerializedName("total_exercises")
    @Expose
    private Double totalExercises;
    @SerializedName("total_average")
    @Expose
    private Double totalAverage;



}
