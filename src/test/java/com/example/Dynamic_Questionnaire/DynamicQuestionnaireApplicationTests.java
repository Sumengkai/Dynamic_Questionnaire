package com.example.Dynamic_Questionnaire;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
	void getQuestionnaireFeedback() {
		QuestionnaireReq req = new QuestionnaireReq();
		req.setWriteDateUuid("a6d99525-6acb-42a2-acf9-d9aa63fab5fc");
		QuestionnaireRes res = questionnaireNameService.getQuestionnaireFeedback(req);
		List<TopicTitle> topicTitleInfo = res.getTopicTitleInfo();
		List<Options> optionsInfo = res.getOptionsInfoList();
		List<String> chooseOptionsForPeople = res.getChooseListForPeople();
		Map<String, Boolean> map = new HashMap<>();
		System.out.println(res.getQuestionnaireName().getQuestionnaireName() + " �ݨ��W��");
		System.out.println(res.getPeople().getName() + " �Τ�W�l");
		System.out.println(res.getWriteDate().getWriteDateTime() + " ��g�ɶ�");
		for (var tL : topicTitleInfo) {
			System.out.println(tL.getTopicName() + " =>�D��");
			for (var oL : optionsInfo) {
				if (tL.getTopicUuid().equals(oL.getTopicUuid())) {
					String options = oL.getQuestionName() + " �ﶵ";
//					System.out.println(options);
					for (var cL : chooseOptionsForPeople) {
						if (oL.getQuestionName().equals(cL) && tL.getTopicUuid().equals(oL.getTopicUuid())) {
//							System.out.println(cL + " �Τ�ĤĪ��ﶵ");
							String op = options+"�Ĥ�";
//							options += "�Ĥ�";
							System.out.println(op);
						}
					}
					System.out.println(options);
					options = "";
				}
			}
		}

//		for (var tL : topicTitleInfo) {
//			System.out.println(tL.getTopicName() + " =>�D��");
//			for (var cL : chooseOptionsForPeople) {
//				System.out.println(cL + " �Τ�Ĥ�");
//				for (var oL : optionsInfo) {
//					if (tL.getTopicUuid().equals(oL.getTopicUuid())) {
//						System.out.println(oL.getQuestionName() + " �ﶵ");
//					}
//				}
//			}
//		}

	}

	@Test
	void creatWriteDateAndCount() {
		QuestionnaireReq req = new QuestionnaireReq();
		req.setQuestionnaireNameUuid("173b6085-7196-4c59-b23f-b4630ceeafaa");
		req.setPeopleId("24c5effe-616a-4064-bda6-1fbf62a17b35");
		List<String> optionsList = new ArrayList<>();
		optionsList.add("e6d88bf7-3783-473a-a6df-b706a0e6f132"); 
//		optionsList.add("5ae36e4e-dc7e-41d6-bd07-5bff4949f708");

		req.setOptionsUuidList(optionsList);
		QuestionnaireRes res = questionnaireNameService.creatWriteDateAndCount(req);
		System.out.println(res.getWriteDate().getName());
	}

	@Test
	public void creatPeople() {
		QuestionnaireReq req = new QuestionnaireReq();
		req.setName("Ĭ����");
		req.setManOrGirl("�k");
		req.setEmail("a0973038822@gmail.com");
		req.setPhone("0973038822");
		req.setAge(23);
		QuestionnaireRes res = questionnaireNameService.creatPeople(req);
		System.out.println(res.getMessage());
	}

	@Test
	public void deleteQnameTest() {
		List<String> dList= new ArrayList<>();
		dList.add("�ݨ�uuid");
		QuestionnaireReq req = new QuestionnaireReq();
		req.setQuestionnaireNameUuidList(dList);

		
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
		LocalDate s = LocalDate.now();
		req.setStartDate(s);
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
		QuestionnaireRes res = questionnaireNameService.getTopicAndOptions(req);
		List<TopicTitle> TL = res.getTopicTitleInfo();
		List<Options> QL = res.getOptionsInfoList();
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