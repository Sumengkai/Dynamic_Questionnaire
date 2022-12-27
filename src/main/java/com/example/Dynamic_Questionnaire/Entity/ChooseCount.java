package com.example.Dynamic_Questionnaire.Entity;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;

@Entity
@Table(name = "choose_count")
public class ChooseCount {
	@Id
	@Column(name = "count_uuid")
	@Type(type = "uuid-char")
	private UUID countUuid;

	@Column(name = "questionnaire_uuid")
	@Type(type = "uuid-char")
	private UUID questionnaireUuid;

	@Column(name = "topic_uuid")
	@Type(type = "uuid-char")
	private UUID topicUuid;

	@Column(name = "options_uuid")
	@Type(type = "uuid-char")
	private UUID optionsUuid;

	@Column(name = "man")
	private int man;

	@Column(name = "girl")
	private int girl;

	@Column(name = "total_people")
	private int totalPeople;

	public ChooseCount() {
	}

	public ChooseCount(UUID countUuid, UUID questionnaireUuid, UUID topicUuid, UUID optionsUuid) {
		this.countUuid = countUuid;
		this.questionnaireUuid = questionnaireUuid;
		this.topicUuid = topicUuid;
		this.optionsUuid = optionsUuid;

	}
	public ChooseCount(UUID countUuid, UUID questionnaireUuid,  UUID optionsUuid) {
		this.countUuid = countUuid;
		this.questionnaireUuid = questionnaireUuid;
	
		this.optionsUuid = optionsUuid;

	}

	public UUID getCountUuid() {
		return countUuid;
	}

	public void setCountUuid(UUID countUuid) {
		this.countUuid = countUuid;
	}

	public UUID getQuestionnaireUuid() {
		return questionnaireUuid;
	}

	public void setQuestionnaireUuid(UUID questionnaireUuid) {
		this.questionnaireUuid = questionnaireUuid;
	}

	public UUID getTopicUuid() {
		return topicUuid;
	}

	public void setTopicUuid(UUID topicUuid) {
		this.topicUuid = topicUuid;
	}

	public UUID getOptionsUuid() {
		return optionsUuid;
	}

	public void setOptionsUuid(UUID optionsUuid) {
		this.optionsUuid = optionsUuid;
	}

	public int getMan() {
		return man;
	}

	public void setMan(int man) {
		this.man = man;
	}

	public int getGirl() {
		return girl;
	}

	public void setGirl(int girl) {
		this.girl = girl;
	}

	public int getTotalPeople() {
		return totalPeople;
	}

	public void setTotalPeople(int totalPeople) {
		this.totalPeople = totalPeople;
	}

}
