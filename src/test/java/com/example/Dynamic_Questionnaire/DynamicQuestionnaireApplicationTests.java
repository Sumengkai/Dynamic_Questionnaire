package com.example.Dynamic_Questionnaire;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.Dynamic_Questionnaire.Entity.Options;
import com.example.Dynamic_Questionnaire.Entity.QuestionnaireName;
import com.example.Dynamic_Questionnaire.Entity.TopicTitle;
import com.example.Dynamic_Questionnaire.Repository.OptionsDao;
import com.example.Dynamic_Questionnaire.Repository.QuestionnaireNameDao;
import com.example.Dynamic_Questionnaire.Repository.TopicTitleDao;
import com.example.Dynamic_Questionnaire.Service.Interface.QuestionnaireService;
import com.example.Dynamic_Questionnaire.Vo.DynamicQuestionnaireVo;
import com.example.Dynamic_Questionnaire.Vo.QuestionnaireReq;
import com.example.Dynamic_Questionnaire.Vo.QuestionnaireRes;

@SpringBootTest
class DynamicQuestionnaireApplicationTests {
	@Autowired
	QuestionnaireService questionnaireNameService;
	@Autowired
	TopicTitleDao topicTitleDao;
	@Autowired
	OptionsDao questionDao;

	@Test
	void contextLoads() {
	}

	@Test
	public void deleteQnameTest() {
		QuestionnaireReq req = new QuestionnaireReq();
		req.setQuestionnaireNameUuid("d2216015-4d4f-49ef-bea1-3ef92017e1c9");
		QuestionnaireRes res = questionnaireNameService.deleteQuestionnaireName(req);
		System.out.println(res.getMessage());
	}

	@Test
	public void searchVoTest() {
		QuestionnaireReq req = new QuestionnaireReq();
		req.setQuestionnaireNameUuid("d7951f25-d9d4-44fd-9043-59f2cf0d7bda");
		QuestionnaireRes res = questionnaireNameService.getVoList(req);
		List<DynamicQuestionnaireVo> voList = res.getDynamicQuestionnaireVoList();
		for (var voItem : voList) {
			System.out.println("�D�� : " + voItem.getTopicTitleName() + " ����� : " + voItem.isEssential() + " ���� : "
					+ voItem.isOnlyOrMany() + " �ﶵ : " + voItem.getQuestionName());
		}
	}

	@Test
	public void updateTest() {
		QuestionnaireReq req = new QuestionnaireReq();
		DynamicQuestionnaireVo reqVo = new DynamicQuestionnaireVo();
		DynamicQuestionnaireVo reqVo2 = new DynamicQuestionnaireVo();
		DynamicQuestionnaireVo reqVo3 = new DynamicQuestionnaireVo();
		DynamicQuestionnaireVo reqVo4 = new DynamicQuestionnaireVo();
		reqVo.setTopicTitleName("�Ĥ@���ݨ����Ĥ@���D��=�h����");
		reqVo.setQuestionName("1.����;2.�x�_;3.�Ÿq;4.�x�n;5.�a����");
		reqVo.setEssential(true);
		reqVo.setOnlyOrMany(true);
		reqVo2.setTopicTitleName("�Ĥ@���ݨ����ĤG���D��=�ܤ���");
		reqVo2.setQuestionName("1.����;2.����;3.����");
		reqVo3.setTopicTitleName("�Ĥ@���ݨ����ĤT���D��=�Ԥ���");
		reqVo3.setQuestionName("1.�j�K");
		List<DynamicQuestionnaireVo> reqVoList = new ArrayList<>();
		reqVoList.add(reqVo);
		reqVoList.add(reqVo2);
		reqVoList.add(reqVo3);
		req.setQuestionnaireNameUuid("d0cc74d7-f08a-4f38-98e5-d05fa3c2c7b0");
		req.setDynamicQuestionnaireVoList(reqVoList);
		QuestionnaireRes res = questionnaireNameService.updateQuestionnaireName(req);
		System.out.println(res.getMessage());
	}

	@Test
	public void creatTest() {
		QuestionnaireReq req = new QuestionnaireReq();
		req.setQuestionnaireName("�Ĥ@���ݨ�");
		DynamicQuestionnaireVo reqVo = new DynamicQuestionnaireVo();
		DynamicQuestionnaireVo reqVo2 = new DynamicQuestionnaireVo();
		DynamicQuestionnaireVo reqVo3 = new DynamicQuestionnaireVo();
		reqVo.setTopicTitleName("�Ĥ@���ݨ����Ĥ@���D��=�Y����");
		reqVo.setQuestionName("1.�~��   ;   2.�J�| ;  3.�T���v");
		reqVo2.setTopicTitleName("�Ĥ@���ݨ����ĤG���D��=�ܤ���");
		reqVo2.setQuestionName("1.����;2.����;3.����");
		reqVo3.setTopicTitleName("�Ĥ@���ݨ����ĤT���D��=�Ԥ���");
		reqVo3.setQuestionName("1.�j�K");
		List<DynamicQuestionnaireVo> reqVoList = new ArrayList<>();
		reqVoList.add(reqVo);
		reqVoList.add(reqVo2);
		reqVoList.add(reqVo3);
		req.setDynamicQuestionnaireVoList(reqVoList);
		QuestionnaireRes res = questionnaireNameService.creatQuestionnaireName(req);
		for (var item : reqVoList) {
			System.out.println(
					res.getQuestionnaireName() + " " + item.getTopicTitleName() + "���D���e:" + item.getQuestionName());
		}
//

	}

	@Test
	public void sTest() { // �Ȯɯd��
		String uuidStr = "d0cc74d7-f08a-4f38-98e5-d05fa3c2c7b0";
		QuestionnaireReq req = new QuestionnaireReq();
		req.setQuestionnaireNameUuid(uuidStr);
		QuestionnaireRes res = questionnaireNameService.getTopicAndQuestion(req);
		List<TopicTitle> TL = res.getTopicTitleInfo();
		List<Options> QL = res.getQuestionInfo();
		System.out.println(res.getQuestionnaireName().getQuestionnaireName());
		for (var T : TL) {
			System.out.println(T.getTopicName());
			for (var Q : QL) {
				if (T.getTopicUuid().equals(Q.getTopicUuid())) {
					System.out.println(Q.getQuestionName());
				}
			}
		}

	}
}