package com.arapeak.adkya.model.getanswer;

import com.arapeak.adkya.model.getQuestion.Question;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Answer {
    @SerializedName("isCorrect")
    @Expose
    private Integer isCorrect;
    @SerializedName("questionNumber")
    @Expose
    private Integer questionNumber;
    @SerializedName("correctAnswers")
    @Expose
    private Integer correctAnswers;
    @SerializedName("correctOptions")
    @Expose
    private List<Integer> correctOptions = null;
    @SerializedName("score")
    @Expose
    private Integer score;
    @SerializedName("retype_answer")
    @Expose
    private String retypeAnswer;
    @SerializedName("userType")
    @Expose
    private String userType;
    @SerializedName("report")
    @Expose
    private Integer report;
    @SerializedName("isMax")
    @Expose
    private Integer isMax;
    @SerializedName("isFinished")
    @Expose
    private Integer isFinished;
    @SerializedName("next_question")
    @Expose
    private Question nextQuestion;

    public Integer getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Integer isCorrect) {
        this.isCorrect = isCorrect;
    }

    public Integer getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(Integer questionNumber) {
        this.questionNumber = questionNumber;
    }

    public Integer getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(Integer correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public List<Integer> getCorrectOptions() {
        return correctOptions;
    }

    public void setCorrectOptions(List<Integer> correctOptions) {
        this.correctOptions = correctOptions;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getRetypeAnswer() {
        return retypeAnswer;
    }

    public void setRetypeAnswer(String retypeAnswer) {
        this.retypeAnswer = retypeAnswer;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public Integer getReport() {
        return report;
    }

    public void setReport(Integer report) {
        this.report = report;
    }

    public Integer getIsMax() {
        return isMax;
    }

    public void setIsMax(Integer isMax) {
        this.isMax = isMax;
    }

    public Integer getIsFinished() {
        return isFinished;
    }

    public void setIsFinished(Integer isFinished) {
        this.isFinished = isFinished;
    }

    public Question getNextQuestion() {
        return nextQuestion;
    }

    public void setNextQuestion(Question nextQuestion) {
        this.nextQuestion = nextQuestion;
    }
}
