package com.tracfone.testcaseapp.Entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the TESTCASE_EXECUTION database table.
 * 
 */
@Entity
@Table(name="TESTCASE_EXECUTION")
@NamedQuery(name="TestcaseExecution.findAll", query="SELECT t FROM TestcaseExecution t")
public class TestcaseExecution implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TESTCASE_EXECUTION_ID_GENERATOR", sequenceName="TESTCASE_EXECUTION_SEQ1")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TESTCASE_EXECUTION_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.DATE)
	@Column(name="DATE_OF_EXECUTION")
	private Date dateOfExecution;

	private String executionstatus;

	@Lob
	private String request;

	@Lob
	private String response;

	//bi-directional many-to-one association to Testcase
	@ManyToOne
	@JoinColumn(name="TESTCASEID")
	private Testcase testcase;

	public TestcaseExecution() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDateOfExecution() {
		return this.dateOfExecution;
	}

	public void setDateOfExecution(Date dateOfExecution) {
		this.dateOfExecution = dateOfExecution;
	}

	public String getExecutionstatus() {
		return this.executionstatus;
	}

	public void setExecutionstatus(String executionstatus) {
		this.executionstatus = executionstatus;
	}

	public String getRequest() {
		return this.request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return this.response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public Testcase getTestcase() {
		return this.testcase;
	}

	public void setTestcase(Testcase testcase) {
		this.testcase = testcase;
	}

}