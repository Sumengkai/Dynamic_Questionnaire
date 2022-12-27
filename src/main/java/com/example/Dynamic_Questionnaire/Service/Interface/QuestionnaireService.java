package com.example.Dynamic_Questionnaire.Service.Interface;

import com.example.Dynamic_Questionnaire.Vo.QuestionnaireReq;
import com.example.Dynamic_Questionnaire.Vo.QuestionnaireRes;

public interface QuestionnaireService {
	// �s�W�ݨ���D req (�ݨ��W�B�}�l�ɶ��B�����ɶ��B�y�z�B�D�ؼ��D�B�O�_����B�h����B�ﶵ) ok!
	public QuestionnaireRes creatQuestionnaireName(QuestionnaireReq req);

	// �ק�ݨ����D req (�ݨ�uuid�B�ݨ��W�B�}�l�ɶ��B�����ɶ��B�y�z�B�D�ؼ��D�B�O�_����B�h����B�ﶵ) ok!
	public QuestionnaireRes updateQuestionnaireName(QuestionnaireReq req);

	// �j���ݨ��C�� req (�ݨ��W�١B�}�l����B�������) ok!
	public QuestionnaireRes getQuestionnaireList(QuestionnaireReq req);

	// �ʸ˦^�h�A�H�K�Τ�s�� req(�ݨ�uuid) ok!
	public QuestionnaireRes getVoList(QuestionnaireReq req);

	// �j���ݨ����D�M�ﶵ req (�ݨ�uuid) ps.���L�X��i�ݨ���� ok! <�ȯd>
	public QuestionnaireRes getTopicAndQuestion(QuestionnaireReq req);

	// �R���ݨ� req (�ݨ�uuid) ok!
	public QuestionnaireRes deleteQuestionnaireName(QuestionnaireReq req);
	//�p��ʤ���B�H�� req(�ﶵ uuid)
	public QuestionnaireRes countData(QuestionnaireReq req);

}
