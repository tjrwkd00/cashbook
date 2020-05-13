package com.gdu.cashbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	// 로그인 폼
	@GetMapping("/login") 
	public String login() {
		return "login";
	}
	// 로그인 액션
	@PostMapping("/login")
	public String login(LoginMember loginMember, HttpSession session) {
		System.out.println(loginMember);
		
		LoginMember returnLoginMember = memberService.login(loginMember);
		if(returnLoginMember == null) { //  로그인 실패시
			return "redirect:/login";
		} else { // 로그인 성공시
			session.setAttribute("loginMember", returnLoginMember);
			return "redirect:/";
		}
	}
	
	// 로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session1) {
		session1.invalidate();
		return "redirect:/";
	}
		
	
	
	// 회원가입 폼
	@GetMapping("/addMember") // 메소드에만 적용
	public String addMember() {
		return "addMember";
	}
	// 회언가입 액션
	@PostMapping("/addMember") // 파라미터 노출  x
	public String addMember(Member member) { // Command객체 , 도메인객체
		memberService.addMember(member);
		System.out.println(member.toString());
		
		return "redirect:/index";
	}
}
