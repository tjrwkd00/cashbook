package com.gdu.cashbook1.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.gdu.cashbook1.mapper.CashMapper;
import com.gdu.cashbook1.vo.Cash;

@Service
@Transactional
public class CashService {
	@Autowired private CashMapper cashMapper;
	public List<Cash> getCashListByDate(Cash cash) {
		return cashMapper.selectCashListByToday(cash);
	}
}
