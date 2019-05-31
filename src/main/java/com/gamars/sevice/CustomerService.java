package com.gamars.sevice;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.gamars.vo.CustomerVO;

public interface CustomerService extends CrudRepository<CustomerVO, Long> {
	List<CustomerVO> findByAge(int age);
}
