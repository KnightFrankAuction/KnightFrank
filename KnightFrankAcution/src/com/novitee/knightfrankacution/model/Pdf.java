package com.novitee.knightfrankacution.model;

import java.io.Serializable;

public class Pdf implements Serializable {
	
	int pdf_id;
	String pdf_link;
	String pdf_name;
	
	public Pdf() {
		super();
	}

	public int getPdf_id() {
		return pdf_id;
	}

	public void setPdf_id(int pdf_id) {
		this.pdf_id = pdf_id;
	}

	public String getPdf_link() {
		return pdf_link;
	}

	public void setPdf_link(String pdf_link) {
		this.pdf_link = pdf_link;
	}

	public String getPdf_name() {
		return pdf_name;
	}

	public void setPdf_name(String pdf_name) {
		this.pdf_name = pdf_name;
	}
	
	
}
