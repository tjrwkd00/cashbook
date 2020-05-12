package com.gdu.cashbook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.Member;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	@GetMapping("/addMember") // 메소드에만 적용
	public String addMember() {
		return "addMember";
	}
	
	@PostMapping("/addMember") // 파라미터 노출  x
	public String addMember(Member member) { // Command객체 , 도메인객체
		memberService.insertMember(member);
		System.out.println(member.toString());
		
		return "redirect:/index";
	}
}
