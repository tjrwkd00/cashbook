package com.gdu.cashbook1.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.gdu.cashbook1.service.CashService;
import com.gdu.cashbook1.vo.Cash;
import com.gdu.cashbook1.vo.LoginMember;

@Controller
public class CashController {
	@Autowired private CashService cashService;
	
	@GetMapping
	public String getCashListByDate(HttpSession session, Model model) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		// 로그인 아이디
		String loginMemberId = ((LoginMember)session.getAttribute("loginMember")).getMemberId();
		// 오늘 날짜를 구해서 원하는 문자열 맘대로 변경
		Date today = new Date(); // "yyyy-mm-dd"
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		String strToday = sdf.format(today); // 2020-05-19
		System.out.println(strToday+"<--strToday");
		
		Cash cash = new Cash(); // +id, +date("yyyy-mm-dd")
		cash.setMemberId(loginMemberId);
		cashService.getCashListByDate(cash);
		
		List<Cash> cashList = cashService.getCashListByDate(cash);
		model.addAttribute("cashList", cashList);
		model.addAttribute("today", today);
		
		for(Cash c : cashList) {
			System.out.println(c);
		}
		
		return "getCashListByDate";
	}
	
}
