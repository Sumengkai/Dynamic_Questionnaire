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

	// 都沒有參數時用
	List<QuestionnaireName> findAllByOrderByStartDateDesc();

	// 只有搜尋問卷名稱用
	List<QuestionnaireName> findByQuestionnaireNameLikeOrderByStartDateDesc(String qsuestionnaireName);

	// 搜尋問卷名、開始日期、結束日期區間
	List<QuestionnaireName> findByQuestionnaireNameLikeAndStartDateBetweenOrderByStartDateDesc(
			String qsuestionnaireName, LocalDate startDate, LocalDate endDate);

	// 藉由時間找到所有問卷
	List<QuestionnaireName> findByStartDateBetweenOrderByStartDateDesc(LocalDate startDate, LocalDate endDate);

	// 刪除問卷
	public void deleteByQuestionnaireUuidIn(List<UUID> questionnaireUuid);

}
