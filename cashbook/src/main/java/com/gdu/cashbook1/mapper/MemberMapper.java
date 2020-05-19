package com.gdu.cashbook1.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook1.vo.LoginMember;
import com.gdu.cashbook1.vo.Member;
import com.gdu.cashbook1.vo.MemberForm;

@Mapper
public interface MemberMapper {
	
	public String selectMemberPic(String memberId);
	
	public String selectMemberIdByMember(Member member);
	
	public Member selectMemberOne(LoginMember loginMember);
	public int addMember(MemberForm memberForm);
	public String selectMemberId(String memberIdCheck);
	public LoginMember selectLoginMember(LoginMember loginMember);
	
	public int deleteMember(LoginMember loginMember);
	public int updateMemberPw(Member member);
	public int insertMember(Member member);
}
	