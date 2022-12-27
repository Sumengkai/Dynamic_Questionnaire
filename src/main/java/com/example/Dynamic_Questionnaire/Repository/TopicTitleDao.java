package com.example.Dynamic_Questionnaire.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Dynamic_Questionnaire.Entity.QuestionnaireName;
import com.example.Dynamic_Questionnaire.Entity.TopicTitle;

@Transactional
@Repository
public interface TopicTitleDao extends JpaRepository<TopicTitle, UUID> {
	public List<TopicTitle> findByQuestionnaireUuid(UUID questionnaireUUID);// �ק�Ψ�

	// �R������ݨ�
	public void deleteByQuestionnaireUuid(UUID questionnaireUuid);

	public Optional<TopicTitle> findByTopicName(String name);
}
