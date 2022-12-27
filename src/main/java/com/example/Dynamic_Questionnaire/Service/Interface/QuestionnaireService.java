package com.example.Dynamic_Questionnaire.Service.Interface;

import com.example.Dynamic_Questionnaire.Vo.QuestionnaireReq;
import com.example.Dynamic_Questionnaire.Vo.QuestionnaireRes;

public interface QuestionnaireService {
	// 新增問券標題 req (問卷名、開始時間、結束時間、描述、題目標題、是否必填、多選單選、選項) ok!
	public QuestionnaireRes creatQuestionnaireName(QuestionnaireReq req);

	// 修改問卷標題 req (問卷uuid、問卷名、開始時間、結束時間、描述、題目標題、是否必填、多選單選、選項) ok!
	public QuestionnaireRes updateQuestionnaireName(QuestionnaireReq req);

	// 搜索問卷列表 req (問卷名稱、開始日期、結束日期) ok!
	public QuestionnaireRes getQuestionnaireList(QuestionnaireReq req);

	// 封裝回去，以便用戶編輯 req(問卷uuid) ok!
	public QuestionnaireRes getVoList(QuestionnaireReq req);

	// 搜索問卷標題和選項 req (問卷uuid) ps.打印出整張問卷資料 ok! <暫留>
	public QuestionnaireRes getTopicAndQuestion(QuestionnaireReq req);

	// 刪除問卷 req (問卷uuid) ok!
	public QuestionnaireRes deleteQuestionnaireName(QuestionnaireReq req);
	//計算百分比、人數 req(選項 uuid)
	public QuestionnaireRes countData(QuestionnaireReq req);

}
