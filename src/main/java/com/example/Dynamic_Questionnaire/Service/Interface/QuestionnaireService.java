package com.example.Dynamic_Questionnaire.Service.Interface;

import com.example.Dynamic_Questionnaire.Vo.QuestionnaireReq;
import com.example.Dynamic_Questionnaire.Vo.QuestionnaireRes;

public interface QuestionnaireService {
	// �s�W�ݨ���D req (�ݨ��W�B�}�l�ɶ��B�����ɶ��B�y�z�B#Vo(�D�ؼ��D�B�O�_����B�h����B�ﶵ)) ok!
	public QuestionnaireRes creatQuestionnaireName(QuestionnaireReq req);

	// �ק�ݨ����D req (�ݨ�uuid�B�ݨ��W�B�}�l�ɶ��B�����ɶ��B�y�z�B�D�ؼ��D�B�O�_����B�h����B�ﶵ) ok!
	public QuestionnaireRes updateQuestionnaireName(QuestionnaireReq req);

	// �j���ݨ��C�� req (�ݨ��W�١B�}�l����B�������) ok!
	public QuestionnaireRes getQuestionnaireList(QuestionnaireReq req);

	// �ʸ˦^�h�A�H�K�Τ�s�� req(�ݨ�uuid) ok!
	public QuestionnaireRes getVoList(QuestionnaireReq req);

	// �j���ݨ����D�M�ﶵ req (�ݨ�uuid) ps.���L�X��i�ݨ��B���D�B�ﶵ ok! <�ȯd>
	public QuestionnaireRes getTopicAndOptions(QuestionnaireReq req);

	// �R���ݨ� req (�ݨ�uuid) ok!
	public QuestionnaireRes deleteQuestionnaireName(QuestionnaireReq req);

	// �s�W�Τ� req(�m�W�B�k�k�B�~�֡B�H�c�B���) ok!
	public QuestionnaireRes creatPeople(QuestionnaireReq req);

	// �s�W��g����B�p��ﶵ�`�ơB�ʤ��� req(�Τ�id�B�ݨ�uuid)(List<string> �ﶵuuid) ok!
	public QuestionnaireRes creatWriteDateAndCount(QuestionnaireReq req);

	// �۰ʧ�s�}�ҩ����� req(�L)
//	public void autoUpdateOpenOrClosure();

	// �d�ݶ�g����A�᭱�d�ݰݨ��^�X�|�Ψ� req(�L)
	public QuestionnaireRes getPeopleAndWriteDateInfo();

	// �d�ݰݨ����X req(��g���uuid)
	public QuestionnaireRes getQuestionnaireFeedback(QuestionnaireReq req);

}
