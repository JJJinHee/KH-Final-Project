package com.kh.project.member.model.vo;

import java.sql.Timestamp;

import lombok.Data;

@Data
public class LoginLog {
	
	private int no;
	private int memberNo;
	private Timestamp dateTime;
	private String ip;
	

}