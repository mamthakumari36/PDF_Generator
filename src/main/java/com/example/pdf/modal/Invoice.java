package com.example.pdf.modal;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Invoice 
{
	private String seller;
	private String sellerGstin;
	private String sellerAddress;
	private String buyer;
	private String buyerGstin;
	private String buyerAddress;
	private List<Item> items;
	
}
