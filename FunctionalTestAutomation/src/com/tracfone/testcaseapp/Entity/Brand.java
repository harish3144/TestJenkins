package com.tracfone.testcaseapp.Entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the BRAND database table.
 * 
 */
@Entity
@NamedQuery(name="Brand.findAll", query="SELECT b FROM Brand b")
public class Brand implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="BRAND_BRANDID_GENERATOR", sequenceName="BRAND_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="BRAND_BRANDID_GENERATOR")
	private long brandid;

	private String brandname;

	public Brand() {
	}

	public long getBrandid() {
		return this.brandid;
	}

	public void setBrandid(long brandid) {
		this.brandid = brandid;
	}

	public String getBrandname() {
		return this.brandname;
	}

	public void setBrandname(String brandname) {
		this.brandname = brandname;
	}

}