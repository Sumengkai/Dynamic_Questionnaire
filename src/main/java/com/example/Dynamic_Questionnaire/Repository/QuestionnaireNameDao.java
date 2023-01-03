package com.example.Dynamic_Questionnaire.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.Dynamic_Questionnaire.Entity.QuestionnaireName;

@Transactional
@Repository
public interface QuestionnaireNameDao extends JpaRepository<QuestionnaireName, UUID> {
	List<QuestionnaireName> findByQuestionnaireName(String qsuestionnaireName);

	// ���S���ѼƮɥ�
	List<QuestionnaireName> findAllByOrderByStartDateDesc();

	// �u���j�M�ݨ��W�٥�
	List<QuestionnaireName> findByQuestionnaireNameLikeOrderByStartDateDesc(String qsuestionnaireName);

	// �j�M�ݨ��W�B�}�l����B��������϶�
	List<QuestionnaireName> findByQuestionnaireNameLikeAndStartDateBetweenOrderByStartDateDesc(
			String qsuestionnaireName, LocalDate startDate, LocalDate endDate);

	// �ǥѮɶ����Ҧ��ݨ�
	List<QuestionnaireName> findByStartDateBetweenOrderByStartDateDesc(LocalDate startDate, LocalDate endDate);

	// �R���ݨ�
	public void deleteByQuestionnaireUuidIn(List<UUID> questionnaireUuid);

}
