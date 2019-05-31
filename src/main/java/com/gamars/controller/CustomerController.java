package com.gamars.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gamars.sevice.CustomerService;
import com.gamars.vo.CustomerVO;


@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CustomerController {

	@Autowired
	CustomerService service;

	@GetMapping("/customers")
	public List<CustomerVO> getAllCustomers() {
		System.out.println("Get all Customers...");

		List<CustomerVO> customers = new ArrayList<>();
		service.findAll().forEach(customers::add);

		return customers;
	}

	@PostMapping("/customer")
	public CustomerVO postCustomer(@RequestBody CustomerVO customer) {

		CustomerVO _customer = service.save(new CustomerVO(customer.getName(), customer.getAge()));
		return _customer;
	}

	@DeleteMapping("/customer/{id}")
	public ResponseEntity<String> deleteCustomer(@PathVariable("id") long id) {
		System.out.println("Delete Customer with ID = " + id + "...");

		service.deleteById(id);

		return new ResponseEntity<>("Customer has been deleted!", HttpStatus.OK);
	}

	@GetMapping("customers/age/{age}")
	public List<CustomerVO> findByAge(@PathVariable int age) {

		List<CustomerVO> customers = service.findByAge(age);
		return customers;
	}

	@PutMapping("/customer/{id}")
	public ResponseEntity<CustomerVO> updateCustomer(@PathVariable("id") long id, 
			@RequestBody CustomerVO customer) {
		System.out.println("Update Customer with ID = " + id + "...");

		Optional<CustomerVO> customerData = service.findById(id);

		if (customerData.isPresent()) {
			CustomerVO _customer = customerData.get();
			_customer.setName(customer.getName());
			_customer.setAge(customer.getAge());
			_customer.setActive(customer.isActive());
			return new ResponseEntity<>(service.save(_customer), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
