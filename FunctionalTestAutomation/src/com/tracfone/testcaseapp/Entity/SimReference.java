package com.tracfone.testcaseapp.Entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the SIM_REFERENCE database table.
 * 
 */
@Entity
@Table(name="SIM_REFERENCE")
@NamedQuery(name="SimReference.findAll", query="SELECT s FROM SimReference s")
public class SimReference implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="SIM_REFERENCE_PARTNUM_GENERATOR", sequenceName="SIM_REFERENCE_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="SIM_REFERENCE_PARTNUM_GENERATOR")
	private String partnum;

	private BigDecimal id;

	//bi-directional many-to-one association to EsnReference
	@ManyToOne
	@JoinColumn(name="ESNPARTNUM")
	private EsnReference esnReference;

	public SimReference() {
	}

	public String getPartnum() {
		return this.partnum;
	}

	public void setPartnum(String partnum) {
		this.partnum = partnum;
	}

	public BigDecimal getId() {
		return this.id;
	}

	public void setId(BigDecimal id) {
		this.id = id;
	}

	public EsnReference getEsnReference() {
		return this.esnReference;
	}

	public void setEsnReference(EsnReference esnReference) {
		this.esnReference = esnReference;
	}

}