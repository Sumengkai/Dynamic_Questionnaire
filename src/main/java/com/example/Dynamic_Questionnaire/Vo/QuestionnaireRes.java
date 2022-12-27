package com.example.Dynamic_Questionnaire.Vo;

import java.util.ArrayList;
import java.util.List;

import com.example.Dynamic_Questionnaire.Entity.Options;
import com.example.Dynamic_Questionnaire.Entity.QuestionnaireName;
import com.example.Dynamic_Questionnaire.Entity.TopicTitle;

public class QuestionnaireRes {
	private QuestionnaireName questionnaireName; // 問卷標題
	private TopicTitle topicTitle;// 題目標題
	private Options question;// 題目
	private String message; // 訊息
	private List<QuestionnaireName> questionnaireNameList;// 提供搜尋用
	private List<TopicTitle> topicTitleInfo;// 提供搜尋用
	private List<Options> questionInfo;// 提供搜尋用
	private List<DynamicQuestionnaireVo> dynamicQuestionnaireVoList;

	public QuestionnaireRes() {

	}

	public QuestionnaireRes(List<DynamicQuestionnaireVo> dynamicQuestionnaireVoList,String message) {
		this.dynamicQuestionnaireVoList = dynamicQuestionnaireVoList;
		this.message=message;
	}

	public QuestionnaireRes(QuestionnaireName questionnaireName, List<TopicTitle> topicTitleInfo,
			List<Options> questionInfo) {
		this.questionnaireName = questionnaireName;
		this.topicTitleInfo = topicTitleInfo;
		this.questionInfo = questionInfo;
	}

	public QuestionnaireRes(List<TopicTitle> topicTitleInfo, List<Options> questionInfo) {
		this.topicTitleInfo = topicTitleInfo;
		this.questionInfo = questionInfo;
	}

	public QuestionnaireRes(List<QuestionnaireName> questionnaireNameList, List<TopicTitle> topicTitleInfo,
			List<Options> questionInfo) {
		this.questionnaireNameList = questionnaireNameList;
		this.topicTitleInfo = topicTitleInfo;
		this.questionInfo = questionInfo;
	}

	public QuestionnaireRes(List<QuestionnaireName> questionnaireNameList) {
		this.questionnaireNameList = questionnaireNameList;
	}

	public QuestionnaireRes(QuestionnaireName questionnaireName, TopicTitle topicTitle, Options question,
			String message) {
		this.questionnaireName = questionnaireName;
		this.topicTitle = topicTitle;
		this.question = question;
		this.message = message;
	}

	public QuestionnaireRes(Options question, String message) {
		this.question = question;
		this.message = message;
	}

	public QuestionnaireRes(TopicTitle topicTitle, String message) {
		this.topicTitle = topicTitle;
		this.message = message;
	}

	public QuestionnaireRes(QuestionnaireName questionnaireName, String message) {
		this.questionnaireName = questionnaireName;
		this.message = message;
	}

	public QuestionnaireRes(String message) {
		this.message = message;
	}

	public QuestionnaireName getQuestionnaireName() {
		return questionnaireName;
	}

	public void setQuestionnaireName(QuestionnaireName questionnaireName) {
		this.questionnaireName = questionnaireName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public TopicTitle getTopicTitle() {
		return topicTitle;
	}

	public void setTopicTitle(TopicTitle topicTitle) {
		this.topicTitle = topicTitle;
	}

	public Options getQuestion() {
		return question;
	}

	public void setQuestion(Options question) {
		this.question = question;
	}

	// ---
	public List<QuestionnaireName> getQuestionnaireNameList() {
		return questionnaireNameList;
	}

	public void setQuestionnaireNameList(List<QuestionnaireName> questionnaireNameList) {
		this.questionnaireNameList = questionnaireNameList;
	}

	public List<TopicTitle> getTopicTitleInfo() {
		return topicTitleInfo;
	}

	public void setTopicTitleInfo(List<TopicTitle> topicTitleInfo) {
		this.topicTitleInfo = topicTitleInfo;
	}

	public List<Options> getQuestionInfo() {
		return questionInfo;
	}

	public void setQuestionInfo(List<Options> questionInfo) {
		this.questionInfo = questionInfo;
	}
	// ---

	public List<DynamicQuestionnaireVo> getDynamicQuestionnaireVoList() {
		return dynamicQuestionnaireVoList;
	}

	public void setDynamicQuestionnaireVoList(List<DynamicQuestionnaireVo> dynamicQuestionnaireVoList) {
		this.dynamicQuestionnaireVoList = dynamicQuestionnaireVoList;
	}
	

}
