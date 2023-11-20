package com.ssafy.enjoytrip.map.model;

import lombok.Data;

@Data
public class AttractionDto {
	private int attraction_id;
	private int type;
	private String title;
	private String addr1;
	private String addr2;
	private String zipcode;
	private String tel;
	private String first_image;
	private String first_image2;
	private int readcount;
	private int sido_code;
	private int gugun_code;
	private double latitude;
	private double longitude;
	private String mlevel;
}
