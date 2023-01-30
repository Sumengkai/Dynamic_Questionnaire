package com.example.Dynamic_Questionnaire.Vo;

import java.util.ArrayList;
import java.util.List;

import com.example.Dynamic_Questionnaire.Entity.Options;
import com.example.Dynamic_Questionnaire.Entity.People;
import com.example.Dynamic_Questionnaire.Entity.PeopleChoose;
import com.example.Dynamic_Questionnaire.Entity.QuestionnaireName;
import com.example.Dynamic_Questionnaire.Entity.TopicTitle;
import com.example.Dynamic_Questionnaire.Entity.WriteDate;

public class QuestionnaireRes {
	private QuestionnaireName questionnaireName; // �ݨ����D
	private TopicTitle topicTitle;// �D�ؼ��D
	private Options question;// �D��
	private String message; // �T��
	private List<QuestionnaireName> questionnaireNameList;// ���ѷj�M��
	private List<TopicTitle> topicTitleInfoList;// ���ѷj�M��
	private List<Options> optionsInfoList;// ���ѷj�M��
	private List<DynamicQuestionnaireVo> dynamicQuestionnaireVoList;// Vo�ʸ�
	private WriteDate writeDate;// ��g���
	private People people;// �Τ��T
	private PeopleChoose peopleChoose;// �Τ���
	private List<WriteDate> writeListInfo;// ��g�����T
	private List<String> chooseListForPeople;

	public QuestionnaireRes() {

	}

	public QuestionnaireRes(People people, WriteDate writeDate, QuestionnaireName questionnaireName,
			List<TopicTitle> topicTitleInfo, List<Options> optionsInfo, List<String> chooseListForPeople) {
		this.people = people;
		this.writeDate = writeDate;
		this.questionnaireName = questionnaireName;
		this.topicTitleInfoList = topicTitleInfo;
		this.optionsInfoList = optionsInfo;
		this.chooseListForPeople = chooseListForPeople;
	}

	public QuestionnaireRes(WriteDate writeDate, String message) {
		this.writeDate = writeDate;
		this.message = message;

	}

	public QuestionnaireRes(People people, String message) {
		this.people = people;
		this.message = message;

	}
	public QuestionnaireRes(List<DynamicQuestionnaireVo> dynamicQuestionnaireVoList,  QuestionnaireName questionnaireName) {
		this.dynamicQuestionnaireVoList = dynamicQuestionnaireVoList;
		this.questionnaireName = questionnaireName;
	}

	public QuestionnaireRes(List<DynamicQuestionnaireVo> dynamicQuestionnaireVoList, String message) {
		this.dynamicQuestionnaireVoList = dynamicQuestionnaireVoList;
		this.message = message;
	}

	public QuestionnaireRes(QuestionnaireName questionnaireName, List<TopicTitle> topicTitleInfo,
			List<Options> questionInfo) {
		this.questionnaireName = questionnaireName;
		this.topicTitleInfoList = topicTitleInfo;
		this.optionsInfoList = questionInfo;
	}

	public QuestionnaireRes(List<TopicTitle> topicTitleInfo, List<Options> questionInfo) {
		this.topicTitleInfoList = topicTitleInfo;
		this.optionsInfoList = questionInfo;
	}

	public QuestionnaireRes(List<QuestionnaireName> questionnaireNameList, List<TopicTitle> topicTitleInfo,
			List<Options> questionInfo) {
		this.questionnaireNameList = questionnaireNameList;
		this.topicTitleInfoList = topicTitleInfo;
		this.optionsInfoList = questionInfo;
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

	public List<TopicTitle> getTopicTitleInfoList() {
		return topicTitleInfoList;
	}

	public void setTopicTitleInfoList(List<TopicTitle> topicTitleInfo) {
		this.topicTitleInfoList = topicTitleInfo;
	}

	public List<Options> getOptionsInfoList() {
		return optionsInfoList;
	}

	public void setOptionsInfoList(List<Options> questionInfo) {
		this.optionsInfoList = questionInfo;
	}
	// ---

	public List<DynamicQuestionnaireVo> getDynamicQuestionnaireVoList() {
		return dynamicQuestionnaireVoList;
	}

	public void setDynamicQuestionnaireVoList(List<DynamicQuestionnaireVo> dynamicQuestionnaireVoList) {
		this.dynamicQuestionnaireVoList = dynamicQuestionnaireVoList;
	}

	public WriteDate getWriteDate() {
		return writeDate;
	}

	public void setWriteDate(WriteDate writeDate) {
		this.writeDate = writeDate;
	}

	public People getPeople() {
		return people;
	}

	public void setPeople(People people) {
		this.people = people;
	}

	public PeopleChoose getPeopleChoose() {
		return peopleChoose;
	}

	public void setPeopleChoose(PeopleChoose peopleChoose) {
		this.peopleChoose = peopleChoose;
	}

	public List<WriteDate> getWriteListInfo() {
		return writeListInfo;
	}

	public void setWriteListInfo(List<WriteDate> writeListInfo) {
		this.writeListInfo = writeListInfo;
	}

	public List<String> getChooseListForPeople() {
		return chooseListForPeople;
	}

	public void setChooseListForPeople(List<String> chooseListForPeople) {
		this.chooseListForPeople = chooseListForPeople;
	}

}
