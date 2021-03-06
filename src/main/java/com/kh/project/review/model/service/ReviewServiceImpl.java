package com.kh.project.review.model.service;

import java.io.File;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kh.project.review.controller.ReviewController;
import com.kh.project.review.model.dao.ReviewMapper;
import com.kh.project.review.model.vo.Review;
import com.kh.project.review.model.vo.ReviewUpload;

import lombok.extern.slf4j.Slf4j;

@Transactional
@Slf4j
@Service("reviewService")
public class ReviewServiceImpl implements ReviewService {

	private final ReviewMapper reviewMapper;

	@Autowired
	public ReviewServiceImpl(ReviewMapper reviewMapper) {
		this.reviewMapper = reviewMapper;
	}
	
	@Override
	public List<Review> selectReviewList() {
		return reviewMapper.selectReviewList();
	}

	@Override
	public int insertReview(Review review) {
		
		/* 리뷰 테이블 삽입*/
		int reviewResult = reviewMapper.insertReview(review);
		int uploadResult = 0;
		/* 리뷰 업로드 테이블 삽입 */
		ReviewUpload thumbnail = review.getThumbnail();
		
		if (thumbnail != null) {
			
			uploadResult = reviewMapper.insertRvupload(thumbnail);
			
			// 실패 시 저장 된 사진 삭제
			if(uploadResult == 0) {
				File failedFile = new File(thumbnail.getFilePath() + thumbnail.getChangedName());
				failedFile.delete();
			}
		} else {
			uploadResult = 1;
		}
		
		int result = 0;
		if(reviewResult > 0 && uploadResult > 0) {
			result = 1;
		} else {
			result = 0;
		}
		return result;
	}

	@Override
	public int increaseCount(int rvno) {
		
		return reviewMapper.increaseCount(rvno);
	}

	@Override
	public Review selectReview(int rvno) {
		
		return reviewMapper.selectReview(rvno);
	}

	@Override
	public int deleteReview(int rvno) {
		
		return reviewMapper.deleteReview(rvno);
	}

	
	@Override
	public ReviewUpload deleteThumbnail(int rvno) {
		
		ReviewUpload deleteUpload = reviewMapper.selectThumbnail(rvno);
		
		int reviewResult = reviewMapper.deleteReview(rvno);
		
		int thumbResult = reviewMapper.deleteThumbnail(rvno);
		
		if(reviewResult > 0 && thumbResult > 0) {
			
		} else {
			deleteUpload = null;
		}
		
		return deleteUpload;
	}

	@Override
	public int updateReview(Review review) {
		
		/* Review 테이블 수정 */
		int reviewResult = reviewMapper.updateReview(review);
		
		int updatePhotoResult = 0;
		int insertPhotoResult = 0;
		int updateCount = 0;
		int insertCount = 0;
		
		/* ReviewUpload 테이블 수정 */
		ReviewUpload thumbnail = review.getThumbnail();
		if (thumbnail != null) {
			
			if(thumbnail.getDeletedName() != null) {
				/* 기존에 있던 파일을 덮어쓰기 - update*/
				updatePhotoResult += reviewMapper.updateThumbnail(thumbnail);
				updateCount++;
				log.info("update : {}", thumbnail);
				log.info("updatePhotoResult {} ", updatePhotoResult);
			} else {
				/* 새로 첨부 된 파일을 추가하기 - insert*/
				insertPhotoResult += reviewMapper.insertAddedThumbnail(review.getRvno(), thumbnail);
				insertCount++;
				log.info("insert : {}", thumbnail);
				log.info("insertPhotoResult {} ", insertPhotoResult);
			}
		}
		
		int result = 0;
		if(reviewResult > 0 && updatePhotoResult == updateCount && insertPhotoResult == insertCount) {
			result = 1;
		} else {
			result = 0;
		}
		
		return result ;
	}



	
	
}
