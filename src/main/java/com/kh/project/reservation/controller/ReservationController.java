package com.kh.project.reservation.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.kh.project.reservation.model.service.ReservationService;
import com.kh.project.reservation.model.vo.VeterinarianAndTreatmentType;
import com.kh.project.subAdmin.model.vo.Veterinarian;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/reservation")
public class ReservationController {
	
	private ReservationService reservationService;
	
	@Autowired
	public ReservationController(ReservationService reservationService) {
		this.reservationService = reservationService;
	}

	@GetMapping("notice")
	public String ReservaionNotice() {
		return "reservation/notice";
	}
	
	@GetMapping("treatmentType")
	public String ReservationTreatmentType() {
		return "reservation/treatmentType";
	}
	
	@GetMapping("reservation_typeChoice")
	public String Reservation_typeChoice() {
		return "reservation/reservation_typeChoice";
	}
	
	@PostMapping("reservation_timeChoice")
	public ModelAndView Reservation_timeChoice(@RequestParam("tno") int tno, ModelAndView mv) {
		
		/* 진료 유형을 선택하면 진료 시간 선택으로 화면은 넘어가고
		 * 선택한 유형에 맞게 진료 유형의 이름과 담당의의 이름이 표시되어야 하기때문에
		 * 진료 과목과 담당의의 이름을 알아오기 위한 select 구현 */
		List<VeterinarianAndTreatmentType> VList = reservationService.findVname(tno);
		
		mv.addObject("VList", VList);
		mv.setViewName("reservation/reservation_timeChoice");
		
		return mv;
	}
}
