package com.fernando.oliveira.traveler.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PageRequestModel {

	
	private static final String PAGE= "page";
	private static final String SIZE= "size";
	private static final String SORT= "sort";
	
	
	private int page = 0;
	private int size = 5;
	private String sort = "";
	
	public PageRequestModel(Map<String, String> params) {
		if(params.containsKey(PAGE))
			page = Integer.parseInt(params.get(PAGE));
		if(params.containsKey(SIZE))
			size = Integer.parseInt(params.get(SIZE));
		if(params.containsKey(SORT))
			sort = params.get(SORT);
	}
	
	public PageRequest toSpringPageRequest() {
	
		List<Order> orders = new ArrayList<Order>();
		
		String [] properties = sort.split(",");
		
		for(String prop : properties) {
			if(prop.trim().length() > 0) {
				String column = prop.trim();
				
				if(column.startsWith("-")) {
					column = column.replace("-","").trim();
					orders.add(Order.desc(column));
				}else {
					orders.add(Order.asc(column));
				}
				
			}
		}
		
		
		return PageRequest.of(page, size, Sort.by(orders));
	}
	
	
}
