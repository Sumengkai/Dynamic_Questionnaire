package com.example.Dynamic_Questionnaire.Vo;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

public class QuestionnaireReq {
	private String questionnaireName; // �ݨ��W��

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate startDate; // �}�Ҥ��

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate endDate; // �������

	private String description; // �ݨ��y�z

	private String questionnaireNameUuid; // �ק�ݨ��W�٥Ψ쪺UUID

	private String topicTitle;// �D�ئW��

	private String necessaryOrNot;// ���n�D���n

	private String onlyOrMany;// ���Φh��

	private DynamicQuestionnaireVo dynamicQuestionnaireVo;

	private List<DynamicQuestionnaireVo> dynamicQuestionnaireVoList; // �s�W�ݨ��B���D�B�ﶵ

	private String searchQuestionnaireName;// �j�M�ݨ�

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate searchStartDate;// �j�M�}�l���

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate searchEndDate;// �j�M�������

	private String topicUuid;// �ק���D���D�Ψ쪺UUID

	private boolean necessaryOrNotBoolean; // �ק���D�|�Ψ쪺���L(����?)

	private boolean onlyOrManyBoolean;// �ק���D�|�Ψ쪺���L(���?)

	private String optionsUuid;// �ﶵUUID �ק�ﶵ�|�Ψ�

	private String questionName;// �ﶵ�W�� �s�W�B�B�ק�ﶵ�W�ٷ|�Ψ�

	private List<String> optionsUuidList;// �p��ΡA�˵ۿﶵ��UUID

	private String peopleId;// ����ID

	private String manOrGirl;// �k�Τk

	private String name;// �Τ�m�W

	private String email;// �H�c

	private String phone;// ���

	private int age;// �~��

	private String writeDateUuid;// ��g���uuid

	private List<String> questionnaireNameUuidList;// �R������ݨ�
//----------------------------------------------------------------------	

	public String getQuestionnaireName() {
		return questionnaireName;
	}

	public void setQuestionnaireName(String questionnaireName) {
		this.questionnaireName = questionnaireName;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getQuestionnaireNameUuid() {
		return questionnaireNameUuid;
	}

	public void setQuestionnaireNameUuid(String questionnaireNameUuid) {
		this.questionnaireNameUuid = questionnaireNameUuid;
	}
	// ---

	public String getTopicTitle() {
		return topicTitle;
	}

	public void setTopicTitle(String topicTitle) {
		this.topicTitle = topicTitle;
	}

	public String getNecessaryOrNot() {
		return necessaryOrNot;
	}

	public void setNecessaryOrNot(String necessaryOrNot) {
		this.necessaryOrNot = necessaryOrNot;
	}

	public String getOnlyOrMany() {
		return onlyOrMany;
	}

	public void setOnlyOrMany(String onlyOrMany) {
		this.onlyOrMany = onlyOrMany;
	}
	// --

	public DynamicQuestionnaireVo getDynamicQuestionnaireVo() {
		return dynamicQuestionnaireVo;
	}

	public void setDynamicQuestionnaireVo(DynamicQuestionnaireVo dynamicQuestionnaireVo) {
		this.dynamicQuestionnaireVo = dynamicQuestionnaireVo;
	}

	public List<DynamicQuestionnaireVo> getDynamicQuestionnaireVoList() {
		return dynamicQuestionnaireVoList;
	}

	public void setDynamicQuestionnaireVoList(List<DynamicQuestionnaireVo> dynamicQuestionnaireVoList) {
		this.dynamicQuestionnaireVoList = dynamicQuestionnaireVoList;
	}

	// ---
	public String getSearchQuestionnaireName() {
		return searchQuestionnaireName;
	}

	public void setSearchQuestionnaireName(String searchQuestionnaireName) {
		this.searchQuestionnaireName = searchQuestionnaireName;
	}

	public LocalDate getSearchStartDate() {
		return searchStartDate;
	}

	public void setSearchStartDate(LocalDate searchStartDate) {
		this.searchStartDate = searchStartDate;
	}

	public LocalDate getSearchEndDate() {
		return searchEndDate;
	}

	public void setSearchEndDate(LocalDate searchEndDate) {
		this.searchEndDate = searchEndDate;
	}

	// ---
	public String getTopicUuid() {
		return topicUuid;
	}

	public void setTopicUuid(String topicUuid) {
		this.topicUuid = topicUuid;
	}

	public boolean isNecessaryOrNotBoolean() {
		return necessaryOrNotBoolean;
	}

	public void setNecessaryOrNotBoolean(boolean necessaryOrNotBoolean) {
		this.necessaryOrNotBoolean = necessaryOrNotBoolean;
	}

	public boolean isOnlyOrManyBoolean() {
		return onlyOrManyBoolean;
	}

	public void setOnlyOrManyBoolean(boolean onlyOrManyBoolean) {
		this.onlyOrManyBoolean = onlyOrManyBoolean;
	}
	// ---

	public String getOptionsUuid() {
		return optionsUuid;
	}

	public void setOptionsUuid(String optionsUuid) {
		this.optionsUuid = optionsUuid;
	}

	public String getQuestionName() {
		return questionName;
	}

	public void setQuestionName(String questionName) {
		this.questionName = questionName;
	}

	public List<String> getOptionsUuidList() {
		return optionsUuidList;
	}

	public void setOptionsUuidList(List<String> optionsUuidList) {
		this.optionsUuidList = optionsUuidList;
	}

	public String getPeopleId() {
		return peopleId;
	}

	public void setPeopleId(String peopleId) {
		this.peopleId = peopleId;
	}

	public String getManOrGirl() {
		return manOrGirl;
	}

	public void setManOrGirl(String manOrGirl) {
		this.manOrGirl = manOrGirl;
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

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getWriteDateUuid() {
		return writeDateUuid;
	}

	public void setWriteDateUuid(String writeDateUuid) {
		this.writeDateUuid = writeDateUuid;
	}

	public List<String> getQuestionnaireNameUuidList() {
		return questionnaireNameUuidList;
	}

	public void setQuestionnaireNameUuidList(List<String> questionnaireNameUuidList) {
		this.questionnaireNameUuidList = questionnaireNameUuidList;
	}

}
