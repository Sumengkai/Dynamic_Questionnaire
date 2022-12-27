package com.example.Dynamic_Questionnaire.Service.Impl;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.Dynamic_Questionnaire.Entity.ChooseCount;
import com.example.Dynamic_Questionnaire.Entity.Options;
import com.example.Dynamic_Questionnaire.Entity.QuestionnaireName;
import com.example.Dynamic_Questionnaire.Entity.TopicTitle;
import com.example.Dynamic_Questionnaire.Repository.ChooseCountDao;
import com.example.Dynamic_Questionnaire.Repository.OptionsDao;
import com.example.Dynamic_Questionnaire.Repository.QuestionnaireNameDao;
import com.example.Dynamic_Questionnaire.Repository.TopicTitleDao;
import com.example.Dynamic_Questionnaire.Service.Interface.QuestionnaireService;
import com.example.Dynamic_Questionnaire.Vo.DynamicQuestionnaireVo;
import com.example.Dynamic_Questionnaire.Vo.QuestionnaireReq;
import com.example.Dynamic_Questionnaire.Vo.QuestionnaireRes;
import com.mysql.cj.x.protobuf.MysqlxDatatypes.Array;

//@Transactional
@Service
public class QuestionnaireImpl implements QuestionnaireService {
	@Autowired
	QuestionnaireNameDao questionnaireNameDao;
	@Autowired
	TopicTitleDao topicTitleDao;
	@Autowired
	OptionsDao optionsDao;
	@Autowired
	ChooseCountDao chooseCountDao;

	// ------------------------------------------

	// ------------------------------------------
	private QuestionnaireRes checkQuestionnaireName(QuestionnaireReq req) {
		if (!StringUtils.hasText(req.getQuestionnaireName())) {
			return new QuestionnaireRes("請填入問卷名稱");
		}
		return null;

	}

	private boolean checkTimeIsBefore(LocalDate start, LocalDate end) {
		if (end.isBefore(start)) {
			return false;
		}
		return true;
	}

	// =============================================================================
	// ----新增問卷 ps.2022/12/23成功
	@Override
	public QuestionnaireRes creatQuestionnaireName(QuestionnaireReq req) {
		QuestionnaireName questionnaireName = new QuestionnaireName();
		QuestionnaireRes res = checkQuestionnaireName(req);
		if (res != null) {
			return res;
		}
		res = new QuestionnaireRes();
		// 開啟或關閉中
		boolean checkOpenOrClorse = false;
		// 日期有輸入為true
		boolean checkStartDate = req.getStartDate() != null;
		boolean checkEndDate = req.getEndDate() != null;
		if (!checkStartDate && checkEndDate) {
			return new QuestionnaireRes("請輸入開始日期");
		}
		if (checkStartDate && checkEndDate) {
			LocalDate startDate = req.getStartDate();
			LocalDate endDate = req.getEndDate();
			boolean checkTimeIsBefore = checkTimeIsBefore(startDate, endDate);
			if (!checkTimeIsBefore) {
				return new QuestionnaireRes("開始時間不能大於結束時間");
			}
		}

		// 當兩個日期都沒有時，掉進去
		if (!checkStartDate && !checkEndDate) {
			// 預設開始日期今天
			LocalDate startDate = LocalDate.now();
			// 預設結束日期是開始日期+7
			LocalDate endDate = startDate.plus(7, ChronoUnit.DAYS);
			// 有描述
			if (StringUtils.hasText(req.getDescription())) {
				questionnaireName = new QuestionnaireName(UUID.randomUUID(), req.getQuestionnaireName(), startDate,
						endDate, true, req.getDescription());
				questionnaireNameDao.save(questionnaireName);

			}
			// 沒有描述
			questionnaireName = new QuestionnaireName(UUID.randomUUID(), req.getQuestionnaireName(), startDate, endDate,
					true, "無");
			questionnaireNameDao.save(questionnaireName);

		}

		// 當兩個日期都有
		if (checkStartDate && checkEndDate) {
			LocalDate startDate = req.getStartDate();
			LocalDate endDate = req.getEndDate();
			if ((endDate.isAfter(LocalDate.now()) && startDate.isAfter(LocalDate.now()))
					|| startDate.equals(LocalDate.now()) || endDate.equals(LocalDate.now())) {
				checkOpenOrClorse = true;
			}
			// 有描述
			if (StringUtils.hasText(req.getDescription())) {
				questionnaireName = new QuestionnaireName(UUID.randomUUID(), req.getQuestionnaireName(), startDate,
						endDate, checkOpenOrClorse, req.getDescription());
				questionnaireNameDao.save(questionnaireName);

			}
			questionnaireName = new QuestionnaireName(UUID.randomUUID(), req.getQuestionnaireName(), startDate, endDate,
					checkOpenOrClorse, "無");
			questionnaireNameDao.save(questionnaireName);

		}
		// 上面都沒擋掉，一定只有開始日期
		if (checkStartDate && !checkEndDate) {
			LocalDate startDate = req.getStartDate();
			LocalDate endDate = startDate.plus(7, ChronoUnit.DAYS);
			if ((endDate.isAfter(LocalDate.now()) && startDate.isAfter(LocalDate.now()))
					|| startDate.equals(LocalDate.now()) || endDate.equals(LocalDate.now())) {
				checkOpenOrClorse = true;
			}
			// 有描述
			if (StringUtils.hasText(req.getDescription())) {
				questionnaireName = new QuestionnaireName(UUID.randomUUID(), req.getQuestionnaireName(), startDate,
						endDate, checkOpenOrClorse, req.getDescription());
				questionnaireNameDao.save(questionnaireName);

			}
			// 沒有描述
			questionnaireName = new QuestionnaireName(UUID.randomUUID(), req.getQuestionnaireName(), startDate, endDate,
					checkOpenOrClorse, "無");
		}
		TopicTitle topicTitle = new TopicTitle();
		Options question = new Options();
		// 封裝
		List<DynamicQuestionnaireVo> dynamicQuestionnaireVoList = req.getDynamicQuestionnaireVoList();
		List<TopicTitle> topicTitleList = new ArrayList<>();
		List<Options> optionsList = new ArrayList<>();
		// 迴圈封裝的vo
		for (var dynamicQuestionnaireVoItem : dynamicQuestionnaireVoList) {
			if (!StringUtils.hasText(dynamicQuestionnaireVoItem.getQuestionName())
					|| !StringUtils.hasText(dynamicQuestionnaireVoItem.getTopicTitleName())) {
				return new QuestionnaireRes("請輸入至少一個選項或問題");
			}
			boolean checkEssential = dynamicQuestionnaireVoItem.isEssential();// 單選為true
			boolean checkOnlyOrMany = dynamicQuestionnaireVoItem.isOnlyOrMany();// 唯一為true
			// 開箱
			// 題目標題
			topicTitle = new TopicTitle(UUID.randomUUID(), questionnaireName.getQuestionnaireUuid(),
					dynamicQuestionnaireVoItem.getTopicTitleName(), checkEssential, checkOnlyOrMany);
			topicTitleList.add(topicTitle);
//			topicTitleDao.save(topicTitle);
			// 切割題目選項
			if (dynamicQuestionnaireVoItem.getQuestionName().contains(";")) {
				String[] questionNameArray = dynamicQuestionnaireVoItem.getQuestionName().split(";");
				for (var questionNameArrayItem : questionNameArray) {
					String stringTrimForQuesName = questionNameArrayItem.trim();
					question = new Options(UUID.randomUUID(), questionnaireName.getQuestionnaireUuid(),
							topicTitle.getTopicUuid(), stringTrimForQuesName);
					optionsList.add(question);
//					questionDao.save(question);
				}

			} else {
				// 如果前端傳進來沒有逗號，不用切割
				question = new Options(UUID.randomUUID(), questionnaireName.getQuestionnaireUuid(),
						topicTitle.getTopicUuid(), dynamicQuestionnaireVoItem.getQuestionName());
				optionsList.add(question);
//				questionDao.save(question);
			}

		}
		topicTitleDao.saveAll(topicTitleList);
		optionsDao.saveAll(optionsList);
		return new QuestionnaireRes(questionnaireName, topicTitle, question, "新增成功");
	}

	/*
	 * =============================================================================
	 */
	// ----修改問卷 ps.2022/12/26成功
	@Override
	public QuestionnaireRes updateQuestionnaireName(QuestionnaireReq req) {
		// 有輸入問卷名稱true
		boolean checkQuestionnaireName = StringUtils.hasText(req.getQuestionnaireName());
		// 有輸入描述true
		boolean checkDescription = StringUtils.hasText(req.getDescription());
		// 有輸入日期true
		boolean checkStartDate = req.getStartDate() != null;
		// 有輸入日期true
		boolean checkEndDate = req.getEndDate() != null;
		List<DynamicQuestionnaireVo> dynamicQuestionnaireVoList = req.getDynamicQuestionnaireVoList();
		if (!checkStartDate && checkEndDate) {
			return new QuestionnaireRes("請輸入開始日期");
		}
		if (checkEndDate && checkStartDate) {
			LocalDate startDate = req.getStartDate();
			LocalDate endDate = req.getEndDate();
			boolean checkTimeIsBefore = checkTimeIsBefore(startDate, endDate);
			if (!checkTimeIsBefore) {
				return new QuestionnaireRes("開始時間不能大於結束時間");
			}
		}
		UUID questionnaireNameUuid = UUID.fromString(req.getQuestionnaireNameUuid());
		// 判斷資料庫是否有同一個主kay
		Optional<QuestionnaireName> questionnaireNameOp = questionnaireNameDao.findById(questionnaireNameUuid);
		if (!questionnaireNameOp.isPresent()) {
			return new QuestionnaireRes("找不到你的問卷");
		}

		QuestionnaireName questionnaireName = questionnaireNameOp.get();

		if (checkStartDate && !checkEndDate) {
			LocalDate startDate = req.getStartDate();
			LocalDate endDate = questionnaireName.getEndDate();
			if (endDate.isBefore(startDate)) {
				return new QuestionnaireRes("開始日期不能大於你原本問卷關閉的日期");
			}
		}
		// 上面都沒擋掉，代表一定會修改某個東西
		if (checkQuestionnaireName) {
			questionnaireName.setQuestionnaireName(req.getQuestionnaireName());
		}
		if (checkStartDate) {
			questionnaireName.setStartDate(req.getStartDate());
		}
		if (checkEndDate) {
			questionnaireName.setEndDate(req.getEndDate());
		}
		if (checkDescription) {
			questionnaireName.setDescription(req.getDescription());
		}
		if (questionnaireName.getEndDate().isAfter(LocalDate.now())
				&& questionnaireName.getStartDate().isAfter(LocalDate.now())
				|| questionnaireName.getStartDate().equals(LocalDate.now())
				|| questionnaireName.getEndDate().equals(LocalDate.now())) {
			questionnaireName.setStateOpenOrClosure(true);
		} else {
			questionnaireName.setStateOpenOrClosure(false);
		}
		List<TopicTitle> topicTitleList = new ArrayList<>();
		List<Options> optionsList = new ArrayList<>();
		questionnaireNameDao.save(questionnaireName);
		topicTitleDao.deleteByQuestionnaireUuid(questionnaireNameUuid);
		optionsDao.deleteByQuestionnaireUuid(questionnaireNameUuid);
		Options question = new Options();
		TopicTitle topicTitle = new TopicTitle();
		for (var dynamicQuestionnaireVoItem : dynamicQuestionnaireVoList) {
			if (!StringUtils.hasText(dynamicQuestionnaireVoItem.getQuestionName())
					|| !StringUtils.hasText(dynamicQuestionnaireVoItem.getTopicTitleName())) {
				return new QuestionnaireRes("請輸入至少一個選項或問題");
			}
			boolean checkEssential = dynamicQuestionnaireVoItem.isEssential();// 單選為true
			boolean checkOnlyOrMany = dynamicQuestionnaireVoItem.isOnlyOrMany();// 唯一為true
			// 開箱
			// 題目標題
			topicTitle = new TopicTitle(UUID.randomUUID(), questionnaireName.getQuestionnaireUuid(),
					dynamicQuestionnaireVoItem.getTopicTitleName(), checkEssential, checkOnlyOrMany);
			topicTitleList.add(topicTitle);
//			topicTitleDao.save(topicTitle);
			// 切割題目選項
			if (dynamicQuestionnaireVoItem.getQuestionName().contains(";")) {
				String[] questionNameArray = dynamicQuestionnaireVoItem.getQuestionName().split(";");
				for (var questionNameArrayItem : questionNameArray) {
					String stringTrimForQuesName = questionNameArrayItem.trim();
					question = new Options(UUID.randomUUID(), questionnaireName.getQuestionnaireUuid(),
							topicTitle.getTopicUuid(), stringTrimForQuesName);
					optionsList.add(question);
//					questionDao.save(question);
				}

			} else {
				// 如果前端傳進來沒有逗號，不用切割
				question = new Options(UUID.randomUUID(), questionnaireName.getQuestionnaireUuid(),
						topicTitle.getTopicUuid(), dynamicQuestionnaireVoItem.getQuestionName());
				optionsList.add(question);
//				questionDao.save(question);
			}
		}
		topicTitleDao.saveAll(topicTitleList);
		optionsDao.saveAll(optionsList);
		return new QuestionnaireRes(questionnaireName, topicTitle, question, "修改成功");

	}

	/*
	 * =============================================================================
	 */
	// ----顯示問卷列表之後看用戶要不要修改 ps.2022/12/25成功
	// req (問卷名稱、開始日期、結束日期)

	@Override
	public QuestionnaireRes getQuestionnaireList(QuestionnaireReq req) {
		// 有輸入問卷名稱true
		boolean checkSearchQname = StringUtils.hasText(req.getSearchQuestionnaireName());
		// 有輸入開始日期true
		boolean checkSearchStartDate = req.getSearchStartDate() != null;
		// 有輸入結束日期true
		boolean checkSearchEndDate = req.getSearchEndDate() != null;
		// 問卷名稱資訊
		List<QuestionnaireName> questionnaireNameInfo = new ArrayList<>();
		// 1.三個都沒輸入
		if (!checkSearchQname && !checkSearchStartDate && !checkSearchEndDate) {
			questionnaireNameInfo = questionnaireNameDao.findAllByOrderByStartDateDesc();

		}
		// 有結束日期、沒有開始日期(防呆)
		if (checkSearchEndDate && !checkSearchStartDate) {
			return new QuestionnaireRes("請輸入開始日期");
		}
		// 2.三者都有輸入
		if (checkSearchQname && checkSearchStartDate && checkSearchEndDate) {
			LocalDate startDate = req.getSearchStartDate();
			LocalDate endDate = req.getSearchEndDate();
			if (endDate.isBefore(startDate)) {
				return new QuestionnaireRes("開始日期不可大於結束時間");
			}
			questionnaireNameInfo = questionnaireNameDao
					.findByQuestionnaireNameLikeAndStartDateBetweenOrderByStartDateDesc(req.getQuestionnaireName(),
							startDate, endDate);
		}
		// 3.有問卷名稱、有開始日期、沒有結束日期
		if (checkSearchQname && checkSearchStartDate && !checkSearchEndDate) {
			LocalDate startDate = req.getSearchStartDate();
			if (LocalDate.now().isBefore(startDate)) {
				return new QuestionnaireRes("開始日期不可大於今天");
			}
			questionnaireNameInfo = questionnaireNameDao
					.findByQuestionnaireNameLikeAndStartDateBetweenOrderByStartDateDesc(req.getQuestionnaireName(),
							startDate, LocalDate.now());
		}
		// 4.只有問卷名稱
		if (checkSearchQname && !checkSearchStartDate && !checkSearchEndDate) {
			questionnaireNameInfo = questionnaireNameDao
					.findByQuestionnaireNameLikeOrderByStartDateDesc(req.getQuestionnaireName());
		}
		// 5.上面沒擋掉代表沒有問卷名稱，這裡是有開始日期與結束日期
		if (!checkSearchQname && checkSearchStartDate && checkSearchEndDate) {
			LocalDate startDate = req.getSearchStartDate();
			LocalDate endDate = req.getSearchEndDate();
			if (endDate.isBefore(startDate)) {
				return new QuestionnaireRes("開始日期不可大於結束日期");
			}
			questionnaireNameInfo = questionnaireNameDao.findByStartDateBetweenOrderByStartDateDesc(startDate, endDate);
		}
		// 6.上面都沒擋掉代表只有輸入開始日期
		if (!checkSearchQname && checkSearchStartDate && !checkSearchEndDate) {
			LocalDate startDate = req.getSearchStartDate();
			if (LocalDate.now().isBefore(startDate)) {
				return new QuestionnaireRes("開始日期不可大於今天");
			}
			questionnaireNameInfo = questionnaireNameDao.findByStartDateBetweenOrderByStartDateDesc(startDate,
					LocalDate.now());
		}
		if (questionnaireNameInfo.isEmpty()) {
			return new QuestionnaireRes("查無資料");
		}
		return new QuestionnaireRes(questionnaireNameInfo);
	}

	/*
	 * =============================================================================
	 */
	// ----搜尋完問卷標題點進去看到裡面的問題 ps.2022/12/25成功 <暫留>
	// req (問卷uuid)
	@Override
	public QuestionnaireRes getTopicAndQuestion(QuestionnaireReq req) {

		UUID questionnaireNameUuid = UUID.fromString(req.getQuestionnaireNameUuid());
		// 判斷資料庫是否有同一個主kay
		Optional<QuestionnaireName> questionnaireNameOp = questionnaireNameDao.findById(questionnaireNameUuid);
		if (!questionnaireNameOp.isPresent()) {
			return new QuestionnaireRes("找不到你的問卷");
		}
		QuestionnaireName questionnaireName = questionnaireNameOp.get();
		List<TopicTitle> topicTitleInfo = topicTitleDao.findByQuestionnaireUuid(questionnaireNameUuid);
		List<Options> questionInfo = optionsDao.findByQuestionnaireUuid(questionnaireNameUuid);
		List<Options> questionInfoList = new ArrayList<>();
		for (var topicTitleItem : topicTitleInfo) {
			for (var questionItem : questionInfo) {
				if (topicTitleItem.getTopicUuid().equals(questionItem.getTopicUuid())) {
					questionInfoList.add(questionItem);
				}
			}
		}

		return new QuestionnaireRes(questionnaireName, topicTitleInfo, questionInfoList);
	}

	/*
	 * =============================================================================
	 */
	// 刪除整份問卷、req(問卷uuid)
	@Override
	public QuestionnaireRes deleteQuestionnaireName(QuestionnaireReq req) {
		UUID questionnaireNameUuid = UUID.fromString(req.getQuestionnaireNameUuid());
		questionnaireNameDao.deleteById(questionnaireNameUuid);
		topicTitleDao.deleteByQuestionnaireUuid(questionnaireNameUuid);
		optionsDao.deleteByQuestionnaireUuid(questionnaireNameUuid);
		return new QuestionnaireRes("刪除成功");
	}

	/*
	 * =============================================================================
	 */
	// 進入修改模式，給用戶看他原本的值，封裝回vo
	@Override
	public QuestionnaireRes getVoList(QuestionnaireReq req) {
		UUID questionnaireNameUuid = UUID.fromString(req.getQuestionnaireNameUuid());
		// 判斷資料庫是否有同一個主kay
		Optional<QuestionnaireName> questionnaireNameOp = questionnaireNameDao.findById(questionnaireNameUuid);
		if (!questionnaireNameOp.isPresent()) {
			return new QuestionnaireRes("找不到你的問卷");
		}
		List<DynamicQuestionnaireVo> dynamicQuestionnaireVoList = new ArrayList<>();
		List<TopicTitle> topicList = topicTitleDao.findByQuestionnaireUuid(questionnaireNameUuid);
		for (var topicItem : topicList) {
			String qName = "";
			List<Options> questionList = optionsDao.findByTopicUuid(topicItem.getTopicUuid());
			for (var qItem : questionList) {
				qName += qItem.getQuestionName() + ";";
			}
			String finalQname = qName.substring(0, qName.length() - 1);
			DynamicQuestionnaireVo dynamicQuestionnaireVo = new DynamicQuestionnaireVo(topicItem.getTopicName(),
					finalQname, topicItem.isEssential(), topicItem.isOnlyOrMany());
			dynamicQuestionnaireVoList.add(dynamicQuestionnaireVo);
		}
		return new QuestionnaireRes(dynamicQuestionnaireVoList, "成功");
	}

	@Override
	public QuestionnaireRes countData(QuestionnaireReq req) {
		List<String> optionsUuidList = req.getOptionsUuidList();
		UUID questionnaireNameUuid = UUID.fromString(req.getQuestionnaireNameUuid());
		if (optionsUuidList.isEmpty()) {
			return new QuestionnaireRes("空");
		}
		int manPeople = 0;
		int girlPeople = 0;
		int totalPeople = 0;
		for (var optionsItem : optionsUuidList) {
			UUID optionsUuid = UUID.fromString(optionsItem);
			Options countPeople = optionsDao.findById(optionsUuid).get();
			
		}
		return null;
	}
}
