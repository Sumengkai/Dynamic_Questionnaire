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
			return new QuestionnaireRes("�ж�J�ݨ��W��");
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
	// ----�s�W�ݨ� ps.2022/12/23���\
	@Override
	public QuestionnaireRes creatQuestionnaireName(QuestionnaireReq req) {
		QuestionnaireName questionnaireName = new QuestionnaireName();
		QuestionnaireRes res = checkQuestionnaireName(req);
		if (res != null) {
			return res;
		}
		res = new QuestionnaireRes();
		// �}�ҩ�������
		boolean checkOpenOrClorse = false;
		// �������J��true
		boolean checkStartDate = req.getStartDate() != null;
		boolean checkEndDate = req.getEndDate() != null;
		if (!checkStartDate && checkEndDate) {
			return new QuestionnaireRes("�п�J�}�l���");
		}
		if (checkStartDate && checkEndDate) {
			LocalDate startDate = req.getStartDate();
			LocalDate endDate = req.getEndDate();
			boolean checkTimeIsBefore = checkTimeIsBefore(startDate, endDate);
			if (!checkTimeIsBefore) {
				return new QuestionnaireRes("�}�l�ɶ�����j�󵲧��ɶ�");
			}
		}

		// ���Ӥ�����S���ɡA���i�h
		if (!checkStartDate && !checkEndDate) {
			// �w�]�}�l�������
			LocalDate startDate = LocalDate.now();
			// �w�]��������O�}�l���+7
			LocalDate endDate = startDate.plus(7, ChronoUnit.DAYS);
			// ���y�z
			if (StringUtils.hasText(req.getDescription())) {
				questionnaireName = new QuestionnaireName(UUID.randomUUID(), req.getQuestionnaireName(), startDate,
						endDate, true, req.getDescription());
				questionnaireNameDao.save(questionnaireName);

			}
			// �S���y�z
			questionnaireName = new QuestionnaireName(UUID.randomUUID(), req.getQuestionnaireName(), startDate, endDate,
					true, "�L");
			questionnaireNameDao.save(questionnaireName);

		}

		// ���Ӥ������
		if (checkStartDate && checkEndDate) {
			LocalDate startDate = req.getStartDate();
			LocalDate endDate = req.getEndDate();
			if ((endDate.isAfter(LocalDate.now()) && startDate.isAfter(LocalDate.now()))
					|| startDate.equals(LocalDate.now()) || endDate.equals(LocalDate.now())) {
				checkOpenOrClorse = true;
			}
			// ���y�z
			if (StringUtils.hasText(req.getDescription())) {
				questionnaireName = new QuestionnaireName(UUID.randomUUID(), req.getQuestionnaireName(), startDate,
						endDate, checkOpenOrClorse, req.getDescription());
				questionnaireNameDao.save(questionnaireName);

			}
			questionnaireName = new QuestionnaireName(UUID.randomUUID(), req.getQuestionnaireName(), startDate, endDate,
					checkOpenOrClorse, "�L");
			questionnaireNameDao.save(questionnaireName);

		}
		// �W�����S�ױ��A�@�w�u���}�l���
		if (checkStartDate && !checkEndDate) {
			LocalDate startDate = req.getStartDate();
			LocalDate endDate = startDate.plus(7, ChronoUnit.DAYS);
			if ((endDate.isAfter(LocalDate.now()) && startDate.isAfter(LocalDate.now()))
					|| startDate.equals(LocalDate.now()) || endDate.equals(LocalDate.now())) {
				checkOpenOrClorse = true;
			}
			// ���y�z
			if (StringUtils.hasText(req.getDescription())) {
				questionnaireName = new QuestionnaireName(UUID.randomUUID(), req.getQuestionnaireName(), startDate,
						endDate, checkOpenOrClorse, req.getDescription());
				questionnaireNameDao.save(questionnaireName);

			}
			// �S���y�z
			questionnaireName = new QuestionnaireName(UUID.randomUUID(), req.getQuestionnaireName(), startDate, endDate,
					checkOpenOrClorse, "�L");
		}
		TopicTitle topicTitle = new TopicTitle();
		Options question = new Options();
		// �ʸ�
		List<DynamicQuestionnaireVo> dynamicQuestionnaireVoList = req.getDynamicQuestionnaireVoList();
		List<TopicTitle> topicTitleList = new ArrayList<>();
		List<Options> optionsList = new ArrayList<>();
		// �j��ʸ˪�vo
		for (var dynamicQuestionnaireVoItem : dynamicQuestionnaireVoList) {
			if (!StringUtils.hasText(dynamicQuestionnaireVoItem.getQuestionName())
					|| !StringUtils.hasText(dynamicQuestionnaireVoItem.getTopicTitleName())) {
				return new QuestionnaireRes("�п�J�ܤ֤@�ӿﶵ�ΰ��D");
			}
			boolean checkEssential = dynamicQuestionnaireVoItem.isEssential();// ��אּtrue
			boolean checkOnlyOrMany = dynamicQuestionnaireVoItem.isOnlyOrMany();// �ߤ@��true
			// �}�c
			// �D�ؼ��D
			topicTitle = new TopicTitle(UUID.randomUUID(), questionnaireName.getQuestionnaireUuid(),
					dynamicQuestionnaireVoItem.getTopicTitleName(), checkEssential, checkOnlyOrMany);
			topicTitleList.add(topicTitle);
//			topicTitleDao.save(topicTitle);
			// �����D�ؿﶵ
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
				// �p�G�e�ݶǶi�ӨS���r���A���Τ���
				question = new Options(UUID.randomUUID(), questionnaireName.getQuestionnaireUuid(),
						topicTitle.getTopicUuid(), dynamicQuestionnaireVoItem.getQuestionName());
				optionsList.add(question);
//				questionDao.save(question);
			}

		}
		topicTitleDao.saveAll(topicTitleList);
		optionsDao.saveAll(optionsList);
		return new QuestionnaireRes(questionnaireName, topicTitle, question, "�s�W���\");
	}

	/*
	 * =============================================================================
	 */
	// ----�ק�ݨ� ps.2022/12/26���\
	@Override
	public QuestionnaireRes updateQuestionnaireName(QuestionnaireReq req) {
		// ����J�ݨ��W��true
		boolean checkQuestionnaireName = StringUtils.hasText(req.getQuestionnaireName());
		// ����J�y�ztrue
		boolean checkDescription = StringUtils.hasText(req.getDescription());
		// ����J���true
		boolean checkStartDate = req.getStartDate() != null;
		// ����J���true
		boolean checkEndDate = req.getEndDate() != null;
		List<DynamicQuestionnaireVo> dynamicQuestionnaireVoList = req.getDynamicQuestionnaireVoList();
		if (!checkStartDate && checkEndDate) {
			return new QuestionnaireRes("�п�J�}�l���");
		}
		if (checkEndDate && checkStartDate) {
			LocalDate startDate = req.getStartDate();
			LocalDate endDate = req.getEndDate();
			boolean checkTimeIsBefore = checkTimeIsBefore(startDate, endDate);
			if (!checkTimeIsBefore) {
				return new QuestionnaireRes("�}�l�ɶ�����j�󵲧��ɶ�");
			}
		}
		UUID questionnaireNameUuid = UUID.fromString(req.getQuestionnaireNameUuid());
		// �P�_��Ʈw�O�_���P�@�ӥDkay
		Optional<QuestionnaireName> questionnaireNameOp = questionnaireNameDao.findById(questionnaireNameUuid);
		if (!questionnaireNameOp.isPresent()) {
			return new QuestionnaireRes("�䤣��A���ݨ�");
		}

		QuestionnaireName questionnaireName = questionnaireNameOp.get();

		if (checkStartDate && !checkEndDate) {
			LocalDate startDate = req.getStartDate();
			LocalDate endDate = questionnaireName.getEndDate();
			if (endDate.isBefore(startDate)) {
				return new QuestionnaireRes("�}�l�������j��A�쥻�ݨ����������");
			}
		}
		// �W�����S�ױ��A�N��@�w�|�ק�Y�ӪF��
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
				return new QuestionnaireRes("�п�J�ܤ֤@�ӿﶵ�ΰ��D");
			}
			boolean checkEssential = dynamicQuestionnaireVoItem.isEssential();// ��אּtrue
			boolean checkOnlyOrMany = dynamicQuestionnaireVoItem.isOnlyOrMany();// �ߤ@��true
			// �}�c
			// �D�ؼ��D
			topicTitle = new TopicTitle(UUID.randomUUID(), questionnaireName.getQuestionnaireUuid(),
					dynamicQuestionnaireVoItem.getTopicTitleName(), checkEssential, checkOnlyOrMany);
			topicTitleList.add(topicTitle);
//			topicTitleDao.save(topicTitle);
			// �����D�ؿﶵ
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
				// �p�G�e�ݶǶi�ӨS���r���A���Τ���
				question = new Options(UUID.randomUUID(), questionnaireName.getQuestionnaireUuid(),
						topicTitle.getTopicUuid(), dynamicQuestionnaireVoItem.getQuestionName());
				optionsList.add(question);
//				questionDao.save(question);
			}
		}
		topicTitleDao.saveAll(topicTitleList);
		optionsDao.saveAll(optionsList);
		return new QuestionnaireRes(questionnaireName, topicTitle, question, "�ק令�\");

	}

	/*
	 * =============================================================================
	 */
	// ----��ܰݨ��C����ݥΤ�n���n�ק� ps.2022/12/25���\
	// req (�ݨ��W�١B�}�l����B�������)

	@Override
	public QuestionnaireRes getQuestionnaireList(QuestionnaireReq req) {
		// ����J�ݨ��W��true
		boolean checkSearchQname = StringUtils.hasText(req.getSearchQuestionnaireName());
		// ����J�}�l���true
		boolean checkSearchStartDate = req.getSearchStartDate() != null;
		// ����J�������true
		boolean checkSearchEndDate = req.getSearchEndDate() != null;
		// �ݨ��W�ٸ�T
		List<QuestionnaireName> questionnaireNameInfo = new ArrayList<>();
		// 1.�T�ӳ��S��J
		if (!checkSearchQname && !checkSearchStartDate && !checkSearchEndDate) {
			questionnaireNameInfo = questionnaireNameDao.findAllByOrderByStartDateDesc();

		}
		// ����������B�S���}�l���(���b)
		if (checkSearchEndDate && !checkSearchStartDate) {
			return new QuestionnaireRes("�п�J�}�l���");
		}
		// 2.�T�̳�����J
		if (checkSearchQname && checkSearchStartDate && checkSearchEndDate) {
			LocalDate startDate = req.getSearchStartDate();
			LocalDate endDate = req.getSearchEndDate();
			if (endDate.isBefore(startDate)) {
				return new QuestionnaireRes("�}�l������i�j�󵲧��ɶ�");
			}
			questionnaireNameInfo = questionnaireNameDao
					.findByQuestionnaireNameLikeAndStartDateBetweenOrderByStartDateDesc(req.getQuestionnaireName(),
							startDate, endDate);
		}
		// 3.���ݨ��W�١B���}�l����B�S���������
		if (checkSearchQname && checkSearchStartDate && !checkSearchEndDate) {
			LocalDate startDate = req.getSearchStartDate();
			if (LocalDate.now().isBefore(startDate)) {
				return new QuestionnaireRes("�}�l������i�j�󤵤�");
			}
			questionnaireNameInfo = questionnaireNameDao
					.findByQuestionnaireNameLikeAndStartDateBetweenOrderByStartDateDesc(req.getQuestionnaireName(),
							startDate, LocalDate.now());
		}
		// 4.�u���ݨ��W��
		if (checkSearchQname && !checkSearchStartDate && !checkSearchEndDate) {
			questionnaireNameInfo = questionnaireNameDao
					.findByQuestionnaireNameLikeOrderByStartDateDesc(req.getQuestionnaireName());
		}
		// 5.�W���S�ױ��N��S���ݨ��W�١A�o�̬O���}�l����P�������
		if (!checkSearchQname && checkSearchStartDate && checkSearchEndDate) {
			LocalDate startDate = req.getSearchStartDate();
			LocalDate endDate = req.getSearchEndDate();
			if (endDate.isBefore(startDate)) {
				return new QuestionnaireRes("�}�l������i�j�󵲧����");
			}
			questionnaireNameInfo = questionnaireNameDao.findByStartDateBetweenOrderByStartDateDesc(startDate, endDate);
		}
		// 6.�W�����S�ױ��N��u����J�}�l���
		if (!checkSearchQname && checkSearchStartDate && !checkSearchEndDate) {
			LocalDate startDate = req.getSearchStartDate();
			if (LocalDate.now().isBefore(startDate)) {
				return new QuestionnaireRes("�}�l������i�j�󤵤�");
			}
			questionnaireNameInfo = questionnaireNameDao.findByStartDateBetweenOrderByStartDateDesc(startDate,
					LocalDate.now());
		}
		if (questionnaireNameInfo.isEmpty()) {
			return new QuestionnaireRes("�d�L���");
		}
		return new QuestionnaireRes(questionnaireNameInfo);
	}

	/*
	 * =============================================================================
	 */
	// ----�j�M���ݨ����D�I�i�h�ݨ�̭������D ps.2022/12/25���\ <�ȯd>
	// req (�ݨ�uuid)
	@Override
	public QuestionnaireRes getTopicAndQuestion(QuestionnaireReq req) {

		UUID questionnaireNameUuid = UUID.fromString(req.getQuestionnaireNameUuid());
		// �P�_��Ʈw�O�_���P�@�ӥDkay
		Optional<QuestionnaireName> questionnaireNameOp = questionnaireNameDao.findById(questionnaireNameUuid);
		if (!questionnaireNameOp.isPresent()) {
			return new QuestionnaireRes("�䤣��A���ݨ�");
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
	// �R������ݨ��Breq(�ݨ�uuid)
	@Override
	public QuestionnaireRes deleteQuestionnaireName(QuestionnaireReq req) {
		UUID questionnaireNameUuid = UUID.fromString(req.getQuestionnaireNameUuid());
		questionnaireNameDao.deleteById(questionnaireNameUuid);
		topicTitleDao.deleteByQuestionnaireUuid(questionnaireNameUuid);
		optionsDao.deleteByQuestionnaireUuid(questionnaireNameUuid);
		return new QuestionnaireRes("�R�����\");
	}

	/*
	 * =============================================================================
	 */
	// �i�J�ק�Ҧ��A���Τ�ݥL�쥻���ȡA�ʸ˦^vo
	@Override
	public QuestionnaireRes getVoList(QuestionnaireReq req) {
		UUID questionnaireNameUuid = UUID.fromString(req.getQuestionnaireNameUuid());
		// �P�_��Ʈw�O�_���P�@�ӥDkay
		Optional<QuestionnaireName> questionnaireNameOp = questionnaireNameDao.findById(questionnaireNameUuid);
		if (!questionnaireNameOp.isPresent()) {
			return new QuestionnaireRes("�䤣��A���ݨ�");
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
		return new QuestionnaireRes(dynamicQuestionnaireVoList, "���\");
	}

	@Override
	public QuestionnaireRes countData(QuestionnaireReq req) {
		List<String> optionsUuidList = req.getOptionsUuidList();
		UUID questionnaireNameUuid = UUID.fromString(req.getQuestionnaireNameUuid());
		if (optionsUuidList.isEmpty()) {
			return new QuestionnaireRes("��");
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
