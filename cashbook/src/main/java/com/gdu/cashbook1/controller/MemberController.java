package com.gdu.cashbook1.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.gdu.cashbook1.service.MemberService;
import com.gdu.cashbook1.vo.LoginMember;
import com.gdu.cashbook1.vo.Member;
import com.gdu.cashbook1.vo.MemberForm;

@Controller
public class MemberController {
	@Autowired
	private MemberService memberService;
	
	
	//비밀번호 찾기
	@PostMapping("/findMemberPw")
	public String findMemberPw(HttpSession session, Model model, Member member) {
		int row = memberService.getMemberPw(member);
		String msg = "아이디와 메일을 확인하세요";
		if(row == 1) {
			msg = "비밀번호를 입력한 메일로 전송하였습니다";
		}
		model.addAttribute("msg", msg);
		return "memberPwView";
	}
	@GetMapping("/findMemberPw")
	public String findMemberPw(HttpSession session) {
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		return "findMemberPw";
	}
	
	
	// 아이디 찾기
	@GetMapping("/findMemberId")
	public String findMemberId(HttpSession session) {
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		return "findMemberId";
	}
	@PostMapping("/findMemberId")
	public String findMemberId(HttpSession session, Model model, Member member) {
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		String memberIdPart = memberService.getMemberIdByMember(member);
		System.out.println(memberIdPart+"<--memberIdPart");
		model.addAttribute("memberIdPart", memberIdPart);
		return "memberIdView";
	}
	
	
	// 회원탈퇴
	@GetMapping("/removeMember")
	public String removeMember(HttpSession session) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		return "removeMember"; // input type="password" name="memberPw"
	}
	@PostMapping("/removeMember")
	public String removeMember(HttpSession session, @RequestParam("memberPw") String memberPw) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}

		LoginMember loginMember = (LoginMember)(session.getAttribute("loginMember"));
		loginMember.setMemberPw(memberPw);
		memberService.deleteMember(loginMember);
		session.invalidate();
		
		return "redirect:/";
	}
	
	// 회원정보
	@GetMapping("/memberInfo")
	public String memberInfo(HttpSession session, Model model) {
		if(session.getAttribute("loginMember") == null) {
			return "redirect:/";
		}
		
		Member member = memberService.getMemberOne((LoginMember)(session.getAttribute("loginMember")));
		System.out.println(member);
		model.addAttribute("member", member);
		return "memberInfo";
	}
	
	@PostMapping("/checkMemberId")
	public String checkMemberId(HttpSession session, Model model, @RequestParam("memberIdCheck") String memberIdCheck) {
		// 로그인 중일때
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		String confirmMemberId = memberService.checkMemberId(memberIdCheck);
		// select member_id from member where member_id=memberIdCheck
		System.out.println(confirmMemberId);
		if(confirmMemberId == null) {
			System.out.println("아이디를 사용할 수 있습니다");
			model.addAttribute("memberIdCheck", memberIdCheck);
		} else {
			System.out.println("아이디를 사용할 수 없습니다");
			model.addAttribute("msg", "사용중인 아이디입니다");
		}
		
		return "addMember";
	}
	
	// 회원가입
	@GetMapping("/addMember")
	public String addMember(HttpSession session) {
		// 로그인 중일때
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		return "addMember";
	}
	
	@PostMapping("/addMember")
	public String addMember(MemberForm memberForm, HttpSession session) { // Command 객체 , 도메인 객체
		// 로그인 중일때
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		
		System.out.println(memberForm+"<--memberForm");
		
		if(memberForm.getMemberPic() != null) {
			// 파일은 .png, .jpg, .gif 만 업로드 가능
			if(!memberForm.getMemberPic().getContentType().equals("image/png")
				&& !memberForm.getMemberPic().getContentType().equals("image/jpeg")
				&& !memberForm.getMemberPic().getContentType().equals("image/gif")) {
				return "redirect:/addMember";
			}
		}
		
		memberService.addMember(memberForm);
		// memberForm->member+폴더에 파일도 저장
		
		return "redirect:/index";
	}
	
	
	// 로그인
	@GetMapping("/login") // login Form
	public String login(HttpSession session) {
		// 로그인 중일때
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		return "login";
	}
	@PostMapping("/login") // login Action
	public String login(Model model, LoginMember loginMember, HttpSession session) { // HttpSession session = request.getSession();
		// 로그인 중일때
		if(session.getAttribute("loginMember") != null) {
			return "redirect:/";
		}
		System.out.println(loginMember);
		LoginMember returnLoginMember = memberService.login(loginMember);
		System.out.println("returnLoginMember:"+returnLoginMember);
		if(returnLoginMember == null) { // 로그인 실패시
			model.addAttribute("msg", "아이디와 비밀번호를 확인하세요");
			return "login";
		} else { // 로그인 성공시
			session.setAttribute("loginMember", returnLoginMember);
			return "redirect:/home";
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
	
	
	
}
