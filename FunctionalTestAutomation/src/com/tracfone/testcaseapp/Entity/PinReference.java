package com.tracfone.testcaseapp.Entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * The persistent class for the PIN_REFERENCE database table.
 * 
 */
@Entity
@Table(name="PIN_REFERENCE")
@NamedQuery(name="PinReference.findAll", query="SELECT p FROM PinReference p")
public class PinReference implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="PIN_REFERENCE_PARTNUM_GENERATOR", sequenceName="PIN_REFERENCE_SEQ1")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="PIN_REFERENCE_PARTNUM_GENERATOR")
	private String partnum;

	private BigDecimal id;

	//bi-directional many-to-one association to EsnReference
	@ManyToOne
	@JoinColumn(name="ESNPARTNUM")
	private EsnReference esnReference;

	public PinReference() {
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