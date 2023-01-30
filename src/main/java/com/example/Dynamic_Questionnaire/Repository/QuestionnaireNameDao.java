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

	// ���S���ѼƮɥ�
	List<QuestionnaireName> findAllByOrderByCreatTimeDesc();

	// �u���j�M�ݨ��W�٥�
	List<QuestionnaireName> findByQuestionnaireNameContainingOrderByStartDateDesc(String qsuestionnaireName);

	// �u���j�M�ݨ��W�٥�
	List<QuestionnaireName> findByQuestionnaireNameContainingOrderByCreatTimeDesc(String qsuestionnaireName);

	// �j�M�ݨ��W�B�}�l����B��������϶�
	List<QuestionnaireName> findByQuestionnaireNameContainingAndStartDateBetweenOrderByStartDateDesc(
			String qsuestionnaireName, LocalDate startDate, LocalDate endDate);

	// �j�M�ݨ��W�B�}�l����B��������϶�
	List<QuestionnaireName> findByQuestionnaireNameContainingAndStartDateGreaterThanEqualAndEndDateLessThanEqualOrderByCreatTimeDesc(
			String qsuestionnaireName, LocalDate startDate, LocalDate endDate);

	// ���ݨ��W�١B���}�l����B�S���������
	List<QuestionnaireName> findByQuestionnaireNameContainingAndStartDateGreaterThanEqualOrderByCreatTimeDesc(
			String qsuestionnaireName, LocalDate startDate);

	// �j�M�ݨ��W�B�}�l����B��������϶�
	List<QuestionnaireName> findByQuestionnaireNameContainingAndEndDateLessThanEqualOrderByCreatTimeDesc(
			String qsuestionnaireName, LocalDate endDate);

	// �ǥѮɶ����Ҧ��ݨ�
	List<QuestionnaireName> findByStartDateBetweenOrderByStartDateDesc(LocalDate startDate, LocalDate endDate);

	// �ǥѮɶ����Ҧ��ݨ�
	List<QuestionnaireName> findByStartDateGreaterThanEqualAndEndDateLessThanEqualOrderByCreatTimeDesc(
			LocalDate startDate, LocalDate endDate);

	// �ǥѮɶ����Ҧ��ݨ�
	List<QuestionnaireName> findByEndDateLessThanEqualOrderByCreatTimeDesc(LocalDate endDate);

	// �ǥѮɶ����Ҧ��ݨ�
	List<QuestionnaireName> findByStartDateGreaterThanEqualOrderByCreatTimeDesc(LocalDate endDate);

	// �R���ݨ�
	public void deleteByQuestionnaireUuidIn(List<UUID> questionnaireUuid);

}
