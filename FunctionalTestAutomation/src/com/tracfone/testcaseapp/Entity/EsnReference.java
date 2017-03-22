package com.tracfone.testcaseapp.Entity;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;


/**
 * The persistent class for the ESN_REFERENCE database table.
 * 
 */
@Entity
@Table(name="ESN_REFERENCE")
@NamedQuery(name="EsnReference.findAll", query="SELECT e FROM EsnReference e")
public class EsnReference implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ESN_REFERENCE_PARTNUM_GENERATOR", sequenceName="ESN_REFERENCE_SEQ1")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ESN_REFERENCE_PARTNUM_GENERATOR")
	private String partnum;

	private BigDecimal id;

	//bi-directional many-to-one association to PinReference
	@OneToMany(mappedBy="esnReference")
	private List<PinReference> pinReferences;

	//bi-directional many-to-one association to SimReference
	@OneToMany(mappedBy="esnReference")
	private List<SimReference> simReferences;

	public EsnReference() {
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

	public List<PinReference> getPinReferences() {
		return this.pinReferences;
	}

	public void setPinReferences(List<PinReference> pinReferences) {
		this.pinReferences = pinReferences;
	}

	public PinReference addPinReference(PinReference pinReference) {
		getPinReferences().add(pinReference);
		pinReference.setEsnReference(this);

		return pinReference;
	}

	public PinReference removePinReference(PinReference pinReference) {
		getPinReferences().remove(pinReference);
		pinReference.setEsnReference(null);

		return pinReference;
	}

	public List<SimReference> getSimReferences() {
		return this.simReferences;
	}

	public void setSimReferences(List<SimReference> simReferences) {
		this.simReferences = simReferences;
	}

	public SimReference addSimReference(SimReference simReference) {
		getSimReferences().add(simReference);
		simReference.setEsnReference(this);

		return simReference;
	}

	public SimReference removeSimReference(SimReference simReference) {
		getSimReferences().remove(simReference);
		simReference.setEsnReference(null);

		return simReference;
	}

}