package com.kh.project.admin.csManage.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.project.admin.common.model.vo.Search;
import com.kh.project.admin.csManage.model.dao.CSManageMapper;
import com.kh.project.admin.csManage.model.vo.Question;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CSManageServiceImpl implements CSManageService {

	private CSManageMapper csManageMapper;
	
	@Autowired
	public CSManageServiceImpl(CSManageMapper csManageMapper) {
		this.csManageMapper = csManageMapper;
	}
	
	@Override
	public int getListCount(int sort, Search search) {
		// 전체 글 목록 갯수 조회 or 정렬기준 갯수 조회
		int result = 0;
		if(sort != 0) {
			result = csManageMapper.getListCount(sort);
		} else {
			result = csManageMapper.getSearchListCount(search);
		}
		return result;
	}
	
	@Override
	public int getAnswerStatusCount(int sort) {
		// 답변여부별 갯수 조회		
		return csManageMapper.getAnswerStatusCount(sort);
	}
	
	@Override
	public List<Question> selectQuestionList(int startRow, int endRow, int sort, Search search) {
		
		search.setStartRow(startRow);
		search.setEndRow(endRow);
		search.setSort(sort);
		
		List<Question> questionList = csManageMapper.selectQuestionList(search);
				
		return questionList;
	}

	@Override
	public Question selectQuestionByNo(int no) {
		
		Question question = csManageMapper.selectQuestionByNo(no);
		
		return question;
	}

	@Transactional
	@Override
	public int deleteQuestion(int qno, int ano) {
				
		int answerResult = 1;
		if(ano != 0) {
			answerResult = csManageMapper.deleteAnswer(ano);
		}
		
		int result = csManageMapper.deleteQuestion(qno);
		
		return result > 0 && answerResult > 0 ? 1 : 0;
	}



}
