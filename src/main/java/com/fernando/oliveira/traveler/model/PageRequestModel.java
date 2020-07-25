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

	private int page = 0;
	private int size = 5;
	private String sort = "";
	
	public PageRequestModel(Map<String, String> params) {
		if(params.containsKey("page"))
			page = Integer.parseInt(params.get("page"));
		if(params.containsKey("size"))
			size = Integer.parseInt(params.get("size"));
		if(params.containsKey("sort"))
			sort = params.get("sort");
	}
	
	public PageRequest toSpringPageRequest() {
	
		List<Order> orders = new ArrayList<Order>();
		
		String [] properties = sort.split(",");
		
		for(String prop : properties) {
			if(prop.trim().length() > 0) {
				String column = prop.trim();
				
				if(column.startsWith("-")) {
					column = column.replace("-","");
					orders.add(Order.desc(column));
				}else {
					orders.add(Order.asc(column));
				}
				
			}
		}
		
		
		return PageRequest.of(page, size, Sort.by(orders));
	}
	
	
}
