package com.gdu.cashbook1.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.gdu.cashbook1.vo.Cash;

@Mapper
public interface CashMapper {
	public List<Cash> selectCashListByToday(Cash cash);
}
