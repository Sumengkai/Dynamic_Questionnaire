package com.example.Dynamic_Questionnaire.Service.Impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.Dynamic_Questionnaire.Entity.Options;
import com.example.Dynamic_Questionnaire.Entity.People;
import com.example.Dynamic_Questionnaire.Entity.PeopleChoose;
import com.example.Dynamic_Questionnaire.Entity.QuestionnaireName;
import com.example.Dynamic_Questionnaire.Entity.TopicTitle;
import com.example.Dynamic_Questionnaire.Entity.WriteDate;
import com.example.Dynamic_Questionnaire.Repository.OptionsDao;
import com.example.Dynamic_Questionnaire.Repository.PeopleChooseDao;
import com.example.Dynamic_Questionnaire.Repository.PeopleDao;
import com.example.Dynamic_Questionnaire.Repository.QuestionnaireNameDao;
import com.example.Dynamic_Questionnaire.Repository.TopicTitleDao;
import com.example.Dynamic_Questionnaire.Repository.WriteDateDao;
import com.example.Dynamic_Questionnaire.Service.Interface.QuestionnaireService;
import com.example.Dynamic_Questionnaire.Vo.DynamicQuestionnaireVo;
import com.example.Dynamic_Questionnaire.Vo.QuestionnaireReq;
import com.example.Dynamic_Questionnaire.Vo.QuestionnaireRes;

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
	PeopleChooseDao chooseCountDao;
	@Autowired
	PeopleDao peopleDao;
	@Autowired
	WriteDateDao writeDateDao;
	@Autowired
	PeopleChooseDao peopleChooseDao;

	// ------------------------------------------
	private QuestionnaireRes checkQuestionnaireName(QuestionnaireReq req) {
		if (!StringUtils.hasText(req.getQuestionnaireName())) {
			return new QuestionnaireRes("�ж�J�ݨ��W��");
		}
		return null;

	}

	private boolean checkTimeIsBefore(LocalDate startDate, LocalDate endDate) {
		if (endDate.isBefore(startDate)) {
			return false;
		}
		return true;
	}

	private boolean checkOpenOrClorse(LocalDate startDate, LocalDate endDate) {
		if ((startDate.isBefore(LocalDate.now()) && endDate.isAfter(LocalDate.now()))
				|| startDate.equals(LocalDate.now()) || endDate.equals(LocalDate.now())) {
			return true;
		}
		return false;

	}

	private void autoUpdateOpenOrClosure() {

		// ��X�Ҧ��ݨ�
		List<QuestionnaireName> questionnaireNameInfo = questionnaireNameDao.findAll();

		// �w�Ʊ�<QuestionnaireName>��List
		List<QuestionnaireName> finalQuestionnaireNameInfo = new ArrayList<>();

		// �j��C�Ӱݨ��A�åB�T�w�C�Ӱݨ��O�}�ҩ�����
		for (var item : questionnaireNameInfo) {
			item.setStateOpenOrClosure(checkOpenOrClorse(item.getStartDate(), item.getEndDate()));
			finalQuestionnaireNameInfo.add(item);
		}
		questionnaireNameDao.saveAll(finalQuestionnaireNameInfo);
	}

	// =============================================================================
	// ----�s�W�ݨ� ps.2022/12/23���\
	@Override
	public QuestionnaireRes creatQuestionnaireName(QuestionnaireReq req) {

		// ��new�X�ݨ��W�٪����O
		QuestionnaireName questionnaireName = new QuestionnaireName();

		// �p����k:�T�{�ݨ��W�٬O�_����J
		QuestionnaireRes res = checkQuestionnaireName(req);
		if (res != null) {
			return res;
		}

		res = new QuestionnaireRes();

		// �������J��true
		boolean checkStartDate = req.getStartDate() != null;
		boolean checkEndDate = req.getEndDate() != null;

		// �s�W�ɥu������������b
		if (!checkStartDate && checkEndDate) {
			return new QuestionnaireRes("�п�J�}�l���");
		}

		// ��Ӥ��������J (���b)
		if (checkStartDate && checkEndDate) {
			LocalDate startDate = req.getStartDate();
			LocalDate endDate = req.getEndDate();

			// �p����k:�T�{�}�l�ɶ��O�_�j�󵲧��ɶ�
			boolean checkTimeIsBefore = checkTimeIsBefore(startDate, endDate);
			if (!checkTimeIsBefore) {
				return new QuestionnaireRes("�}�l�ɶ�����j�󵲧��ɶ�");
			}
		}

		// ���Ӥ�����S���ɡA���i�h ps.�]���}�l�ɶ��w�]���ѡB�����ɶ��w�]�}�l+7�A�ҥH�}�ҩ������@�w��true
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

			} else {
				// �S���y�z
				questionnaireName = new QuestionnaireName(UUID.randomUUID(), req.getQuestionnaireName(), startDate,
						endDate, true, "�L");
				questionnaireNameDao.save(questionnaireName);
			}
		}

		// ���Ӥ������
		if (checkStartDate && checkEndDate) {
			LocalDate startDate = req.getStartDate();
			LocalDate endDate = req.getEndDate();

			// �p����k:�T�{�O�_�}��
			boolean checkOpenOrClorse = checkOpenOrClorse(startDate, endDate);

			// ���y�z
			if (StringUtils.hasText(req.getDescription())) {
				questionnaireName = new QuestionnaireName(UUID.randomUUID(), req.getQuestionnaireName(), startDate,
						endDate, checkOpenOrClorse, req.getDescription());
				questionnaireNameDao.save(questionnaireName);

			} else {
				questionnaireName = new QuestionnaireName(UUID.randomUUID(), req.getQuestionnaireName(), startDate,
						endDate, checkOpenOrClorse, "�L");
				questionnaireNameDao.save(questionnaireName);
			}
		}

		// �W�����S�ױ��A�@�w�u���}�l���
		if (checkStartDate && !checkEndDate) {
			LocalDate startDate = req.getStartDate();
			LocalDate endDate = startDate.plus(7, ChronoUnit.DAYS);

			// �p����k:�T�{�O�_�}��
			boolean checkOpenOrClorse = checkOpenOrClorse(startDate, endDate);

			// ���y�z
			if (StringUtils.hasText(req.getDescription())) {
				questionnaireName = new QuestionnaireName(UUID.randomUUID(), req.getQuestionnaireName(), startDate,
						endDate, checkOpenOrClorse, req.getDescription());
				questionnaireNameDao.save(questionnaireName);

			} else {
				// �S���y�z
				questionnaireName = new QuestionnaireName(UUID.randomUUID(), req.getQuestionnaireName(), startDate,
						endDate, checkOpenOrClorse, "�L");
				questionnaireNameDao.save(questionnaireName);
			}
		}

		// ��new�X���D���O
		TopicTitle topicTitle = new TopicTitle();

		// ��new�X�ﶵ���O
		Options question = new Options();
		if (req.getDynamicQuestionnaireVoList() == null) {
			return new QuestionnaireRes(questionnaireName, "�s�W�ݨ����\");
		}

		// ���~���ШD���ʸ˶ivoList
		List<DynamicQuestionnaireVo> dynamicQuestionnaireVoList = req.getDynamicQuestionnaireVoList();
		// �w�Ʊ�< ���D���O >��List
		List<TopicTitle> topicTitleList = new ArrayList<>();
		// �w�Ʊ�< �ﶵ���O >��List
		List<Options> optionsList = new ArrayList<>();

		// �j��ʸ˪�vo ps.�}�c
		for (var voItem : dynamicQuestionnaireVoList) {
			if (!StringUtils.hasText(voItem.getQuestionName()) || !StringUtils.hasText(voItem.getTopicTitleName())) {
				return new QuestionnaireRes("�п�J�ܤ֤@�ӿﶵ�ΰ��D");
			}
			boolean checkEssential = voItem.isEssential();// ��אּfalse
			boolean checkOnlyOrMany = voItem.isOnlyOrMany();// �ߤ@��false

			// �s�W�D�ؼ��D
			topicTitle = new TopicTitle(UUID.randomUUID(), questionnaireName.getQuestionnaireUuid(),
					voItem.getTopicTitleName(), checkEssential, checkOnlyOrMany);

			// ���N�������_��
			topicTitleList.add(topicTitle);

			// �����D��"�ﶵ" ps.�Ƕi�Ӫ��F�観�i��u���@�ӿﶵ�A���N�S��";"�A�ҥH���~�n����
			if (voItem.getQuestionName().contains(";")) {

				// ���Φ��}�C ex:�ﶵ : �ﶵA;�ﶵB
				String[] optionsNameArray = voItem.getQuestionName().split(";");

				// �j����Χ��᪺�}�C
				for (var item : optionsNameArray) {
					// �h���ť�
					String optionsName = item.trim();
					question = new Options(UUID.randomUUID(), questionnaireName.getQuestionnaireUuid(),
							topicTitle.getTopicUuid(), optionsName);

					// ���N�������_��
					optionsList.add(question);

				}

			} else {
				// �p�G�e�ݶǶi�ӨS��";"�A���Τ��ΡA�����x�s�}�c�᪺"�ﶵ�W��"
				question = new Options(UUID.randomUUID(), questionnaireName.getQuestionnaireUuid(),
						topicTitle.getTopicUuid(), voItem.getQuestionName());

				// ���N�������_��
				optionsList.add(question);
			}

		}
		// �N�����᪺List�����x�s��Ʈw
		if (!dynamicQuestionnaireVoList.isEmpty()) {
			topicTitleDao.saveAll(topicTitleList);
			optionsDao.saveAll(optionsList);
		}
		return new QuestionnaireRes(questionnaireName, "�s�W�ݨ��B�D�ءB�ﶵ���\");
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

		// ���~���ШD���ʸ˶ivoList
		List<DynamicQuestionnaireVo> dynamicQuestionnaireVoList = req.getDynamicQuestionnaireVoList();

		// ���b
		if (!checkStartDate && checkEndDate) {
			return new QuestionnaireRes("�п�J�}�l���");
		}

		// ���b
		if (checkEndDate && checkStartDate) {
			LocalDate startDate = req.getStartDate();
			LocalDate endDate = req.getEndDate();
			boolean checkTimeIsBefore = checkTimeIsBefore(startDate, endDate);
			if (!checkTimeIsBefore) {
				return new QuestionnaireRes("�}�l�ɶ�����j�󵲧��ɶ�");
			}
		}

		// �ШD��ݨ��W�٪�uuid
		UUID questionnaireNameUuid = UUID.fromString(req.getQuestionnaireNameUuid());

		// �P�_��Ʈw�O�_���P�@�ӥDkay
		Optional<QuestionnaireName> questionnaireNameOp = questionnaireNameDao.findById(questionnaireNameUuid);
		if (!questionnaireNameOp.isPresent()) {
			return new QuestionnaireRes("�䤣��A���ݨ��A�����b�T���Τ���");
		}

		// ����
		QuestionnaireName questionnaireName = questionnaireNameOp.get();

		// �]���n�P�_�o�ӸӰݨ����S���H�g�L�A�u�n���N����ק�F
		List<WriteDate> peopleChoose = writeDateDao.findByQuestionnaireUuid(questionnaireName.getQuestionnaireUuid());

		if (!peopleChoose.isEmpty()) {
			return new QuestionnaireRes("���H��L�ݨ��T����ק�");
		}

		// ���}�l����B�S���������
		if (checkStartDate && !checkEndDate) {
			LocalDate startDate = req.getStartDate();
			LocalDate endDate = questionnaireName.getEndDate();
			if (endDate.isBefore(startDate)) {
				return new QuestionnaireRes("�}�l�������j��A�쥻�ݨ����������");
			}
		}

		// �W�����S�ױ��A�N��@�w�|�ק�Y�ӪF��

		// ���ק�ݨ��W��
		if (checkQuestionnaireName) {
			questionnaireName.setQuestionnaireName(req.getQuestionnaireName());
		}

		// ���ק�}�l���
		if (checkStartDate) {
			questionnaireName.setStartDate(req.getStartDate());
		}
		// ���קﵲ�����
		if (checkEndDate) {
			questionnaireName.setEndDate(req.getEndDate());
		}

		// ���ק�y�z
		if (checkDescription) {
			questionnaireName.setDescription(req.getDescription());
		}
		// �P�_�}�ҩ�����
		questionnaireName.setStateOpenOrClosure(
				checkOpenOrClorse(questionnaireName.getStartDate(), questionnaireName.getEndDate()));
		// �s�i��Ʈw
		questionnaireNameDao.save(questionnaireName);

		// �w�Ʊ�<TopicTitle>���O
		List<TopicTitle> topicTitleList = new ArrayList<>();
		// �w�Ʊ�<Options>���O
		List<Options> optionsList = new ArrayList<>();

		// ���޿�J������D��ﶵ�A���R���Ҧ��쥻���D�B�ﶵ
		topicTitleDao.deleteByQuestionnaireUuid(questionnaireNameUuid);
		optionsDao.deleteByQuestionnaireUuid(questionnaireNameUuid);

		// ��new�XOptions�����O
		Options question = new Options();
		// ��new�XTopicTitle�����O
		TopicTitle topicTitle = new TopicTitle();

		// �j��s���ʽcvo�A�åB�}�c
		for (var voItem : dynamicQuestionnaireVoList) {
			if (!StringUtils.hasText(voItem.getQuestionName()) || !StringUtils.hasText(voItem.getTopicTitleName())) {
				return new QuestionnaireRes("�п�J�ܤ֤@�ӿﶵ�ΰ��D");
			}

			// ��אּflase
			boolean checkEssential = voItem.isEssential();
			// �ߤ@��false
			boolean checkOnlyOrMany = voItem.isOnlyOrMany();

			// �}�c
			// �D�ؼ��D
			topicTitle = new TopicTitle(UUID.randomUUID(), questionnaireName.getQuestionnaireUuid(),
					voItem.getTopicTitleName(), checkEssential, checkOnlyOrMany);

			// ���������_��
			topicTitleList.add(topicTitle);

			// �����D��"�ﶵ" ps.�Ƕi�Ӫ��F�観�i��u���@�ӿﶵ�A���N�S��";"�A�ҥH���~�n����
			if (voItem.getQuestionName().contains(";")) {

				// ���Φ��}�C ex:�ﶵ : �ﶵA;�ﶵB
				String[] optionsNameArray = voItem.getQuestionName().split(";");

				// �j��}�C
				for (var item : optionsNameArray) {
					// �h���e��ť�
					String optionsName = item.trim();
					question = new Options(UUID.randomUUID(), questionnaireName.getQuestionnaireUuid(),
							topicTitle.getTopicUuid(), optionsName);

					// ���������_��
					optionsList.add(question);
				}

			} else {
				// �p�G�e�ݶǶi�ӨS��";"�A���Τ��ΡA�����x�s�}�c�᪺"�ﶵ�W��"
				question = new Options(UUID.randomUUID(), questionnaireName.getQuestionnaireUuid(),
						topicTitle.getTopicUuid(), voItem.getQuestionName());

				// ���������_��
				optionsList.add(question);
			}
		}
		// �N�����᪺List�����x�s��Ʈw
		topicTitleDao.saveAll(topicTitleList);
		optionsDao.saveAll(optionsList);
		return new QuestionnaireRes(questionnaireName, "�ק令�\");

	}

	/*
	 * =============================================================================
	 */
	// ----��ܰݨ��C����ݥΤ�n���n�ק� ps.2022/12/25���\
	// req (�ݨ��W�١B�}�l����B�������)

	@Override
	public QuestionnaireRes getQuestionnaireList(QuestionnaireReq req) {

		// �۰ʧ�s���A ps.�}�ҩ�����
		autoUpdateOpenOrClosure();

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
			// �^�ǥ������
			questionnaireNameInfo = questionnaireNameDao.findAllByOrderByStartDateDesc();

		}

		// 2.�T�̳�����J
		if (checkSearchQname && checkSearchStartDate && checkSearchEndDate) {

			// �o�������i�H���� ps.��K�\Ū
			LocalDate startDate = req.getSearchStartDate();
			LocalDate endDate = req.getSearchEndDate();
			// ���b
			if (endDate.isBefore(startDate)) {
				return new QuestionnaireRes("�}�l������i�j�󵲧��ɶ�");
			}

			// �^�Ǽҽk�j�M�B�ɶ��϶��᪺���
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

			// �^�Ǽҽk�j�M�B�ɶ��϶��᪺���
			questionnaireNameInfo = questionnaireNameDao
					.findByQuestionnaireNameLikeAndStartDateBetweenOrderByStartDateDesc(req.getQuestionnaireName(),
							startDate, LocalDate.now());
		}
		// 4.�u���ݨ��W��
		if (checkSearchQname && !checkSearchStartDate && !checkSearchEndDate) {
			// �^�Ǽҽk�j�M�����
			questionnaireNameInfo = questionnaireNameDao
					.findByQuestionnaireNameLikeOrderByStartDateDesc(req.getQuestionnaireName());
		}
		// 5.���ݨ��W�١B����������B�S���}�l���
		if (checkSearchQname && checkSearchEndDate && !checkSearchStartDate) {

			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			LocalDate startDate = LocalDate.parse("0001-01-01", format);

			LocalDate endDate = req.getSearchStartDate();

			// �^�Ǽҽk�j�M�B�ɶ��϶��᪺���
			questionnaireNameInfo = questionnaireNameDao
					.findByQuestionnaireNameLikeAndStartDateBetweenOrderByStartDateDesc(req.getQuestionnaireName(),
							startDate, endDate);
		}
		// 6.�S���ݨ��W�١B�S���}�l����B���������
		if (!checkSearchQname && checkSearchEndDate && !checkSearchStartDate) {

			DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

			LocalDate startDate = LocalDate.parse("0001-01-01", format);

			LocalDate endDate = req.getSearchStartDate();

			// �^�Ǽҽk�j�M�B�ɶ��϶��᪺���
			questionnaireNameInfo = questionnaireNameDao.findByStartDateBetweenOrderByStartDateDesc(startDate, endDate);
		}

		// 7.�S���ݨ��W�١B���}�l����B���������
		if (!checkSearchQname && checkSearchStartDate && checkSearchEndDate) {

			LocalDate startDate = req.getSearchStartDate();

			LocalDate endDate = req.getSearchEndDate();

			if (endDate.isBefore(startDate)) {
				return new QuestionnaireRes("�}�l������i�j�󵲧����");
			}

			// �^�Ǯɶ��϶����
			questionnaireNameInfo = questionnaireNameDao.findByStartDateBetweenOrderByStartDateDesc(startDate, endDate);
		}
		// 8.�S���ݨ��W�١B�S����������B���}�l���
		if (!checkSearchQname && checkSearchStartDate && !checkSearchEndDate) {
			LocalDate startDate = req.getSearchStartDate();

			if (LocalDate.now().isBefore(startDate)) {
				return new QuestionnaireRes("�}�l������i�j�󤵤�");
			}

			// �^�Ǯɶ��϶����
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
	// �ȯd
	// ----�j�M���ݨ����D�I�i�h�ݨ�̭������D ps.2022/12/25���\ <�ȯd>
	// req (�ݨ�uuid)
	@Override
	public QuestionnaireRes getTopicAndOptions(QuestionnaireReq req) {

		UUID questionnaireNameUuid = UUID.fromString(req.getQuestionnaireNameUuid());
		// �P�_��Ʈw�O�_���P�@�ӥDkay
		Optional<QuestionnaireName> questionnaireNameOp = questionnaireNameDao.findById(questionnaireNameUuid);
		if (!questionnaireNameOp.isPresent()) {
			return new QuestionnaireRes("�䤣��A���ݨ�");
		}
		QuestionnaireName questionnaireName = questionnaireNameOp.get();
		List<TopicTitle> topicTitleInfo = topicTitleDao.findByQuestionnaireUuid(questionnaireNameUuid);
		List<Options> questionInfo = optionsDao.findByQuestionnaireUuid(questionnaireNameUuid);
		return new QuestionnaireRes(questionnaireName, topicTitleInfo, questionInfo);
	}

	/*
	 * =============================================================================
	 */
	// �R������ݨ��Breq(�ݨ�uuid)
//	@Transactional
	@Override
	public QuestionnaireRes deleteQuestionnaireName(QuestionnaireReq req) {
		// �e�ݱ��i�ӬO�r��
		List<String> questionnaireNameUuidListString = req.getQuestionnaireNameUuidList();
		if (questionnaireNameUuidListString.isEmpty()) {
			return new QuestionnaireRes("�R������");
		}
		// �w�Ʊ��Ҧ��ݨ��W�٪�uuid
		List<UUID> questionnaireNameUuidList = new ArrayList<>();

		for (var item : questionnaireNameUuidListString) {
			UUID questionnaireNameUuid = UUID.fromString(item);
			questionnaireNameUuidList.add(questionnaireNameUuid);
		}
		// �R����Ʈw�P�@�i�ݨ����U���Ҧ����
		questionnaireNameDao.deleteByQuestionnaireUuidIn(questionnaireNameUuidList);
		topicTitleDao.deleteByQuestionnaireUuidIn(questionnaireNameUuidList);
		optionsDao.deleteByQuestionnaireUuidIn(questionnaireNameUuidList);
		writeDateDao.deleteByQuestionnaireUuidIn(questionnaireNameUuidList);
		peopleChooseDao.deleteByQuestionnaireUuidIn(questionnaireNameUuidList);
		return new QuestionnaireRes("�R�����\");
	}

	/*
	 * =============================================================================
	 */
	// �i�J�ק�Ҧ��A���Τ�ݥL�쥻���ȡA�ʸ˦^vo
	@Override
	public QuestionnaireRes getVoList(QuestionnaireReq req) {
		// �ШD�ݨ���uuid
		UUID questionnaireNameUuid = UUID.fromString(req.getQuestionnaireNameUuid());

		// �P�_��Ʈw�O�_���P�@�ӥDkay (�o�̥i�H����)
		Optional<QuestionnaireName> questionnaireNameOp = questionnaireNameDao.findById(questionnaireNameUuid);
		if (!questionnaireNameOp.isPresent()) {
			return new QuestionnaireRes("�䤣��A���ݨ��A�o�Ө��b�T�����i�H����");
		}

		// �w�ƫʸ˦^�h
		List<DynamicQuestionnaireVo> dynamicQuestionnaireVoList = new ArrayList<>();

		// ��X�P�i�ݨ����U���Ҧ����D
		List<TopicTitle> topicList = topicTitleDao.findByQuestionnaireUuid(questionnaireNameUuid);

		// �j����D
		for (var topicItem : topicList) {

			// ���w�]�@�ӪŦr��
			String optionsName = "";
			// ��X�P�@�Ӱ��D���U���ﶵ
			List<Options> optionsList = optionsDao.findByTopicUuid(topicItem.getTopicUuid());
			for (var optionsItem : optionsList) {
				// �N�Ҧ��ﶵ�걵�_�ӡA�H";"���Ϲj
				optionsName += optionsItem.getQuestionName() + ";";
			}
			// �]�����^�h�b�����ɷ|�h�@��";"�A�ҥH�n�A�h�@�Ӧr��h����ex:�ﶵA;�ﶵB;
			String finalQname = optionsName.substring(0, optionsName.length() - 1);
			DynamicQuestionnaireVo dynamicQuestionnaireVo = new DynamicQuestionnaireVo(topicItem.getTopicName(),
					finalQname, topicItem.isEssential(), topicItem.isOnlyOrMany());
			// �ʸ˦^�h
			dynamicQuestionnaireVoList.add(dynamicQuestionnaireVo);
		}
		return new QuestionnaireRes(dynamicQuestionnaireVoList, "");
	}

	/*
	 * =============================================================================
	 */
	// �s�W�H����T
	@Override
	public QuestionnaireRes creatPeople(QuestionnaireReq req) {

		// ���b
		if (!StringUtils.hasText(req.getName()) || !StringUtils.hasText(req.getManOrGirl())
				|| !StringUtils.hasText(req.getEmail()) || !StringUtils.hasText(req.getPhone()) || req.getAge() < 1) {
			return new QuestionnaireRes("�п�J���T�Ѽ�");
		}

		// �P�_���S���ŦX��J�k�Τk
		List<String> manOrGirlList = new ArrayList<>(List.of("�k", "�k"));
		if (!manOrGirlList.stream().anyMatch(item -> item.equalsIgnoreCase(req.getManOrGirl()))) {
			return new QuestionnaireRes("�п�J���T�ʧO");
		}

		if (!req.getEmail().matches("^[A-Za-z0-9]+@gmail.com")) {
			return new QuestionnaireRes("�H�c�榡���~");
		}
		if (!req.getPhone().matches("[0-9]{4}[0-9]{6}")) {
			return new QuestionnaireRes("����榡���~ ex:097x03x82x");
		}

		People people = new People(UUID.randomUUID(), req.getName(), req.getManOrGirl(), req.getEmail(), req.getPhone(),
				req.getAge());

		peopleDao.save(people);

		return new QuestionnaireRes(people, "���\");

	}
	/*
	 * =============================================================================
	 */

	@Override
	public QuestionnaireRes creatWriteDateAndCount(QuestionnaireReq req) {

		// �Τᰵ�ݨ��ɡA�|�d�U�L���H�������� PS.�o�̥�UUID�N��
		UUID peopleid = UUID.fromString(req.getPeopleId());

		// �ШD�ݨ��W�٪�UUID
		UUID questionnaireNameUuid = UUID.fromString(req.getQuestionnaireNameUuid());

		// �ݨ��W�٨���
		QuestionnaireName questionnaireNameInfo = questionnaireNameDao.findById(questionnaireNameUuid).get();

		// �Τ����
		People peopleInfo = peopleDao.findById(peopleid).get();

		// �s�W��g���
		WriteDate writeDate = new WriteDate(UUID.randomUUID(), peopleInfo.getPeopleUuid(),
				questionnaireNameInfo.getQuestionnaireUuid(), peopleInfo.getName(), LocalDateTime.now());

		// �x�s��g�ɶ�
		writeDateDao.save(writeDate);

		if (req.getOptionsUuidList() == null) {
			return new QuestionnaireRes("�õL����@���A���±z����g");
		}

		// �Τ�񧹰ݨ��|�o��ܦh���ﶵ��UUID
		List<String> optionsList = req.getOptionsUuidList();

		// �w�Ʊ�<PeopleChoose>���O
		List<PeopleChoose> peopleChooseList = new ArrayList<>();

		// �w�Ʊ�<Options>���O
		List<Options> optionsInfoList = new ArrayList<>();

		// �j��Τ�諸�ﶵ ps.�̭��񪺬O�r�ꫬ�A��uuid
		for (String optionsItem : optionsList) {

			// �N�C�ӿﶵ���r���নuuid
			UUID optionsUuid = UUID.fromString(optionsItem);

			// �ﶵ����
			Options optionsInfo = optionsDao.findById(optionsUuid).get();

			// �Τ��ܷs�W
			PeopleChoose peopleChoose = new PeopleChoose(UUID.randomUUID(), optionsInfo.getQuestionnaireUuid(),
					optionsInfo.getTopicUuid(), optionsInfo.getOptionsUuid(), peopleInfo.getPeopleUuid(),
					optionsInfo.getQuestionName());

			// �Τ��ܷs�W�A���s�iList
			peopleChooseList.add(peopleChoose);

			// �N�ӿﶵ�諸�H��+1
			optionsInfo.setTotalPeople(optionsInfo.getTotalPeople() + 1);
			if (peopleInfo.getManOrGirl().contains("�k")) {
				optionsInfo.setMan(optionsInfo.getMan() + 1);
			} else {
				optionsInfo.setGirl(optionsInfo.getGirl() + 1);
			}
			// �ﶵ���s�iList
			optionsInfoList.add(optionsInfo);
		}

		// ---�p��H��
		// �x�s�Τ���
		peopleChooseDao.saveAll(peopleChooseList);
		// �x�s�ﶵ(�k�Τk�B�`�H��)
		optionsDao.saveAll(optionsInfoList);

		// ---�p��ʤ���
		double topicTitleTotal = 0;

		// ½�X�Ҧ��Τ᪺�ﶵ
		peopleChooseList = peopleChooseDao.findAll();

		// �Τ᪺�Ҧ����
		for (var chooseItem : peopleChooseList) {

			// �p��Ӱ��D���`�H�� ps.�C�]���Ӱ��D���U���ﶵ���k�s
			topicTitleTotal = 0;

			// ��X�Ӱ��D���U���Ҧ��ﶵ
			optionsInfoList = optionsDao.findByTopicUuid(chooseItem.getTopicUuid());

			// �p��Ӱ��D���U���X�ӿﶵ ps.�p�ƾ�
			int optionsTotalPeople = 0;
			// �j��Ӱ��D���U���ﶵ
			for (var optionsItem : optionsInfoList) {

				// �p��Ӱ��D���`�H��
				topicTitleTotal += optionsItem.getTotalPeople();
				// ��]���W�h�j��ɡA�N��Ӱ��D���U���Ҧ��ﶵ�H�Ƥw�[�`����
				optionsTotalPeople++;
				// �]�����D���U���ﶵ���h�֭ӡA�N�n�p��X���A��p�⧹�ɤ~�|�i��
				if (optionsTotalPeople == optionsInfoList.size()) {

					// �]�Ӱ��D���U���C�ӿﶵ�A�íp��ʤ����x�s�i�h
					for (var optionsItem2 : optionsInfoList) {

						// �p��ʤ��� (�Ӱ��D�`�H��/�ӿﶵ���`�H��)*100
						double optionsPercentage = (optionsItem2.getTotalPeople() / topicTitleTotal) * 100;
						optionsItem2.setPercentage(Math.floor(optionsPercentage * 10.0) / 10.0);

						optionsDao.save(optionsItem2);
					}
				}

			}
		}
		return new QuestionnaireRes(writeDate, "���\");
	}

	/*
	 * =============================================================================
	 */

	@Override
	public QuestionnaireRes getPeopleAndWriteDateInfo() {
		QuestionnaireRes res = new QuestionnaireRes();
		List<WriteDate> writeDateInfo = writeDateDao.findByOrderByWriteDateTimeDesc();
		res.setWriteListInfo(writeDateInfo);
		return res;
	}

	/*
	 * =============================================================================
	 */
	@Override
	public QuestionnaireRes getQuestionnaireFeedback(QuestionnaireReq req) {

		UUID writeDateUuid = UUID.fromString(req.getWriteDateUuid());
//		UUID peopleid = UUID.fromString(writeDateUuid.);
		WriteDate writeDate = writeDateDao.findById(writeDateUuid).get();
		People people = peopleDao.findById(writeDate.getPeopleId()).get();
		QuestionnaireName questionnaireNameInfo = questionnaireNameDao.findById(writeDate.getQuestionnaireUuid()).get();

		List<TopicTitle> topicTitleInfo = topicTitleDao
				.findByQuestionnaireUuid(questionnaireNameInfo.getQuestionnaireUuid());
		List<Options> optionsInfo = optionsDao.findByQuestionnaireUuid(questionnaireNameInfo.getQuestionnaireUuid());
		List<PeopleChoose> chooseInfo = peopleChooseDao.findByPeopleId(people.getPeopleUuid());
		List<String> chooseOptionsForPeople = new ArrayList<>();
		for (var optionsItem : optionsInfo) {
			for (var chooseItem : chooseInfo) {
				if (optionsItem.getOptionsUuid().equals(chooseItem.getOptionsUuid())) {
					chooseOptionsForPeople.add(optionsItem.getQuestionName());// �Τ᪺���
				}
			}
		}

		// �H����T�B��g����B�ݨ��W�١B���D�W�١B�ﶵ�B�Τ�諸�ﶵ
		return new QuestionnaireRes(people, writeDate, questionnaireNameInfo, topicTitleInfo, optionsInfo,
				chooseOptionsForPeople);
	}

}
