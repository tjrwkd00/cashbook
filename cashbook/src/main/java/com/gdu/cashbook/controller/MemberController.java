package com.gdu.cashbook.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook.service.MemberService;
import com.gdu.cashbook.vo.LoginMember;
import com.gdu.cashbook.vo.Member;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;
	@PostMapping("/checkmemberId")
	public String checkMemberId(HttpSession session, Model model, @RequestParam("memberIdCheck") String memberIdCheck) {
		//로그인 중일떄
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		
		String confirmMemberId = memberService.checkMemberId(memberIdCheck);
		System.out.println(confirmMemberId);
		if(confirmMemberId == null) {
			System.out.println("아이디를 사용할 수 있습니다");
			model.addAttribute("MemberIdCheck", memberIdCheck);
		} else {
			System.out.println("아이디를 사용할 수 없습니다.");
			model.addAttribute("msg", "사용중인 아이디입니다");
		}
		return "addMember";
	}
	
	// 로그인 폼
	@GetMapping("/login") 
	public String login(HttpSession session) {
		//로그인 중일때
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		//로그인 아닐때
		return "login";
	}
	// 로그인 액션
	@PostMapping("/login")
	public String login(Model model, LoginMember loginMember, HttpSession session) {
		//로그인 중일때
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		System.out.println(loginMember);
		
		LoginMember returnLoginMember = memberService.login(loginMember);
		System.out.println("returnLoginMember : "+returnLoginMember);
		if(returnLoginMember == null) { //  로그인 실패시
			model.addAttribute("msg", "아이디와 비밀번호를 확인하세요");
			return "login";
		} else { // 로그인 성공시
			session.setAttribute("loginMember", returnLoginMember);
			return "redirect:/";
		}
	}
	
	// 로그아웃
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		// 로그인 아닐때
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		session.invalidate();
		return "redirect:/";
	}
	
	
	
	// 회원가입 폼
	@GetMapping("/addMember") // 메소드에만 적용
	public String addMember(HttpSession session) {
		//로그인 중일때
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		return "addMember";
	}
	// 회언가입 액션
	@PostMapping("/addMember") // 파라미터 노출  x
	public String addMember(Member member, HttpSession session) { // Command객체 , 도메인객체
		//로그인 중일때
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		System.out.println(member.toString());
		memberService.addMember(member);
		return "redirect:/index";
	}
}
