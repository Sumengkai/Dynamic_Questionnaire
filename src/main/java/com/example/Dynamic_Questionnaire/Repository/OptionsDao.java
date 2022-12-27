package com.example.Dynamic_Questionnaire.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Dynamic_Questionnaire.Entity.Options;
import com.example.Dynamic_Questionnaire.Entity.QuestionnaireName;
import com.example.Dynamic_Questionnaire.Entity.TopicTitle;

@Transactional
@Repository
public interface OptionsDao extends JpaRepository<Options, UUID> {


	public List<Options> findByTopicUuid(UUID topicUuid);

	public List<Options> findByQuestionnaireUuid(UUID questionnaireUuid);

	// �R������ݨ�
	public void deleteByQuestionnaireUuid(UUID questionnaireUuid);

	// �R���D�ؼ��D
	public void deleteByTopicUuid(UUID topicUuid);

	public Optional<Options> findByQuestionName(String name);
}
