package com.example.Dynamic_Questionnaire.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Dynamic_Questionnaire.Service.Interface.QuestionnaireService;
import com.example.Dynamic_Questionnaire.Vo.QuestionnaireReq;
import com.example.Dynamic_Questionnaire.Vo.QuestionnaireRes;

@CrossOrigin
@RestController
public class QuestionnaireController {
	@Autowired
	private QuestionnaireService questionnaireNameService;

//---------------------------------------------------------------
	/* �s�W�ݨ�--------------------------------------------------- */
	@PostMapping(value = "/api/creatQuestionnaireName")
	public QuestionnaireRes creatQuestionnaireName(@RequestBody QuestionnaireReq req) {
		return questionnaireNameService.creatQuestionnaireName(req);
	}

	/* �ק�ݨ�--------------------------------------------------- */
	@PostMapping(value = "updateQuestionnaireName")
	public QuestionnaireRes updateQuestionnaireName(@RequestBody QuestionnaireReq req) {
		return questionnaireNameService.updateQuestionnaireName(req);
	}

	/* �j���ݨ��C��----------------------------------------------- */
	@PostMapping(value = "getQuestionnaireList")
	public QuestionnaireRes getQuestionnaireList(@RequestBody QuestionnaireReq req) {
		return questionnaireNameService.getQuestionnaireList(req);
	}

	/* �ʸ˦^�h�A�H�K�Τ�s��-------------------------------------- */
	@PostMapping(value = "getVoList")
	public QuestionnaireRes getVoList(@RequestBody QuestionnaireReq req) {
		return questionnaireNameService.getVoList(req);
	}

	/* �R���ݨ�--------------------------------------------------- */
	@PostMapping(value = "deleteQuestionnaireName")
	public QuestionnaireRes deleteQuestionnaireName(@RequestBody QuestionnaireReq req) {
		return questionnaireNameService.deleteQuestionnaireName(req);
	}

	/* �s�W��g����B�p��ﶵ�`�ơB�ʤ���-------------------------- */
	@PostMapping(value = "creatWriteDateAndCount")
	public QuestionnaireRes creatWriteDateAndCount(@RequestBody QuestionnaireReq req) {
		return questionnaireNameService.creatWriteDateAndCount(req);
	}

	/* �s�W�H����T----------------------------------------------- */
	@PostMapping(value = "creatPeople")
	public QuestionnaireRes creatPeople(@RequestBody QuestionnaireReq req) {
		return questionnaireNameService.creatPeople(req);
	}

	/* �έp------------------------------------------------------- */
	@PostMapping(value = "getTopicAndOptions")
	public QuestionnaireRes getTopicAndOptions(@RequestBody QuestionnaireReq req) {
		return questionnaireNameService.getTopicAndOptions(req);
	}

	/* ���L�X�Τ��ݨ����----------------------------------------- */
	@PostMapping(value = "getPeopleAndWriteDateInfo")
	public QuestionnaireRes getPeopleAndWriteDateInfo(@RequestBody QuestionnaireReq req) {
		return questionnaireNameService.getPeopleAndWriteDateInfo(req);
	}

	/* �ݨ��^�X---------------------------------------------------- */
	@PostMapping(value = "getQuestionnaireFeedback")
	public QuestionnaireRes getQuestionnaireFeedback(@RequestBody QuestionnaireReq req) {
		return questionnaireNameService.getQuestionnaireFeedback(req);
	}
	
	
}
