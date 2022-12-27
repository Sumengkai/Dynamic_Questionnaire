package com.example.Dynamic_Questionnaire.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.Dynamic_Questionnaire.Service.Interface.QuestionnaireService;
import com.example.Dynamic_Questionnaire.Vo.QuestionnaireReq;
import com.example.Dynamic_Questionnaire.Vo.QuestionnaireRes;

@RestController
public class QuestionnaireController {
	@Autowired
	private QuestionnaireService questionnaireNameService;

//---------------------------------------------------------------
	/*·s¼W°Ý¨÷---------------------------------------------------*/
	@PostMapping(value = "creatQuestionnaireName")
	public QuestionnaireRes creatQuestionnaireName(@RequestBody QuestionnaireReq req) {
		return questionnaireNameService.creatQuestionnaireName(req);
	}

	@PostMapping(value = "updateQuestionnaireName")
	public QuestionnaireRes updateQuestionnaireName(@RequestBody QuestionnaireReq req) {
		return questionnaireNameService.updateQuestionnaireName(req);
	}
}
