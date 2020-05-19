package com.gdu.cashbook1.service;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.gdu.cashbook1.mapper.MemberMapper;
import com.gdu.cashbook1.vo.LoginMember;
import com.gdu.cashbook1.vo.Member;
import com.gdu.cashbook1.vo.MemberForm;

@Service
@Transactional
public class MemberService {
	@Autowired
    private MemberMapper memberMapper;
	
    @Value("D:\\git-work\\cashbook\\cashbook\\src\\main\\resources\\static\\upload\\")
    private String path;
	
    public int deleteMember(LoginMember loginMember) {
    	
    	String memberPic = memberMapper.selectMemberId(loginMember.getMemberId());
    	return memberMapper.deleteMember(loginMember);
    }
	
    
    public int addMmeber (MemberForm memberForm) {
    	MultipartFile mf = memberForm.getMemberPic();
    	String orginName = mf.getOriginalFilename();
    	/*
    	if(mf.getContentType().equals("image/png") || mf.getContentType().equals("image/jpeg")) {
    		// 업로드
    	} else {
    		// 업로드 실패
    	}
    	*/
    	System.out.println(orginName+"<--originName");
    	int lastDot = orginName.lastIndexOf("."); // 좌석표.PNG
    	String extension = orginName.substring(lastDot);
    	String memberPic = memberForm.getMemberId()+extension;
    	
    	// 1. db 저장		
    	Member member = new Member();
    	member.setMemberId(memberForm.getMemberId());
    	member.setMemberPw(member.getMemberPw());
    	member.setMemberAddr(memberForm.getMemberAddr());
    	member.setMemberEmail(memberForm.getMemberEmail());
    	member.setMemberName(memberForm.getMemberName());
    	member.setMemberPhone(memberForm.getMemberPhone());
    	member.setMemberPic(memberPic);
    	System.out.println(member+"<-MemberService.addMember.member");
    	int row = memberMapper.insertMember(member);
    	
    	// 2. 파일 저장
    	String path = "D:\\git-work\\cashbook\\cashbook\\src\\main\\resources\\static\\upload";
    	File file = new File(path+"\\"+memberPic);
    	try {
    		mf.transferTo(file);
    	} catch(Exception e) {
    		e.printStackTrace();
    		throw new RuntimeException();
    		// Exception
    		// 1. 예외처리를 해야만 문법적으로 이상없는 예외
    		// 2. 예외처리를 코드에서 구현하지 않아도 아무 문제없는 예외 RuntimeException
    		
    	}
    	return row;
    }
    
    public int getMemberPw(Member member) {
    	UUID uuid = UUID.randomUUID();
    	
    	String memberPw = uuid.toString().substring(0, 8);
    	member.setMemberPw(memberPw);
    	int row = memberMapper.updateMemberPw(member);
    	String msg = "아이디와 메일을 확인하세요";
    	if(row== 1) {
    		// 메일로  수정 성공한 랜덤  비밀번호 전송
    		// 메일객체 new JavaMailSender();
    		msg ="비밀번호를 확인하세요";
    	}
    	return row;
    }
	
	
	public Member getMemberOne(LoginMember loginMember) {
    	return memberMapper.selectMemberOne(loginMember);
    }
	
	public String checkMemberId(String memberIdCheck) {
    	return memberMapper.selectMemberId(memberIdCheck); // null, memberId
    }
	
	public LoginMember login(LoginMember loginMember) {
    	return memberMapper.selectLoginMember(loginMember);
    }
	
	public int addMember(MemberForm memberForm) {
        return memberMapper.addMember(memberForm);
    }

	public String getMemberIdByMember(Member member) {
		return memberMapper.selectMemberIdByMember(member);
		
		
	}
}
