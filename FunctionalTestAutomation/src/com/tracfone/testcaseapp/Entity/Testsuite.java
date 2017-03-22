package com.tracfone.testcaseapp.Entity;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the TESTSUITE database table.
 * 
 */
@Entity
@NamedQuery(name="Testsuite.findAll", query="SELECT t FROM Testsuite t")
public class Testsuite implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TESTSUITE_ID_GENERATOR", sequenceName="TESTSUITE_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TESTSUITE_ID_GENERATOR")
	private long id;

	private String testsuitename;

	//bi-directional many-to-one association to Flow
	@ManyToOne
	@JoinColumn(name="FLOWID")
	private Flow flow;

	//bi-directional many-to-one association to Testcase
	@ManyToOne
	@JoinColumn(name="TESTCASEID")
	private Testcase testcase;

	public Testsuite() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTestsuitename() {
		return this.testsuitename;
	}

	public void setTestsuitename(String testsuitename) {
		this.testsuitename = testsuitename;
	}

	public Flow getFlow() {
		return this.flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	public Testcase getTestcase() {
		return this.testcase;
	}

	public void setTestcase(Testcase testcase) {
		this.testcase = testcase;
	}

}