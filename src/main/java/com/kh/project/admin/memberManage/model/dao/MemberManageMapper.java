package com.kh.project.admin.memberManage.model.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.kh.project.admin.common.model.vo.Search;
import com.kh.project.admin.memberManage.model.vo.MemberInfo;
import com.kh.project.admin.reservationManage.model.vo.Dog;
import com.kh.project.admin.reservationManage.model.vo.ReservationManage;

@Mapper
public interface MemberManageMapper {

	List<MemberInfo> selectMemberList(Search search);

	Dog selectDogInfo(int no);

	MemberInfo selectMemberByNo(int no);

	List<ReservationManage> selectReservationList(int no);

	List<ReservationManage> selectReservationInputList(int no);

	Dog selectDogInputInfo(int rno);

	ReservationManage getReservationInfo(int rno);

	int getListCount(Search search);

	int deleteMemberInfo(int memberNo);

	int updateReason(Map<String, Object> map);


}
