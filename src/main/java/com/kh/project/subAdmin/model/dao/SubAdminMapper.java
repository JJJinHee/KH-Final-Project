package com.kh.project.subAdmin.model.dao;

import org.apache.ibatis.annotations.Mapper;

import com.kh.project.subAdmin.model.vo.Holiday;
import com.kh.project.subAdmin.model.vo.Veterinarian;

@Mapper
public interface SubAdminMapper {

	int registVeterinarian(Veterinarian newVeterinarian);
	
	int registHoliday(Holiday holiday);
}