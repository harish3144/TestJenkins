package com.tracfone.testcaseapp.Entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the TESTCASE database table.
 * 
 */
@Entity
@NamedQuery(name="Testcase.findAll", query="SELECT t FROM Testcase t")
public class Testcase implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="TESTCASE_ID_GENERATOR", sequenceName="TESTCASE_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="TESTCASE_ID_GENERATOR")
	private long id;

	private String description;

	private String endpoint;

	private String name;

	@Lob
	private String request;

	@Lob
	private String response;

	//bi-directional many-to-one association to Flow
	@ManyToOne
	@JoinColumn(name="FLOWID")
	private Flow flow;

	//bi-directional many-to-one association to TestcaseExecution
	@OneToMany(mappedBy="testcase")
	private List<TestcaseExecution> testcaseExecutions;

	//bi-directional many-to-one association to Testsuite
	@OneToMany(mappedBy="testcase")
	private List<Testsuite> testsuites;

	public Testcase() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEndpoint() {
		return this.endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
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

	public Flow getFlow() {
		return this.flow;
	}

	public void setFlow(Flow flow) {
		this.flow = flow;
	}

	public List<TestcaseExecution> getTestcaseExecutions() {
		return this.testcaseExecutions;
	}

	public void setTestcaseExecutions(List<TestcaseExecution> testcaseExecutions) {
		this.testcaseExecutions = testcaseExecutions;
	}

	public TestcaseExecution addTestcaseExecution(TestcaseExecution testcaseExecution) {
		getTestcaseExecutions().add(testcaseExecution);
		testcaseExecution.setTestcase(this);

		return testcaseExecution;
	}

	public TestcaseExecution removeTestcaseExecution(TestcaseExecution testcaseExecution) {
		getTestcaseExecutions().remove(testcaseExecution);
		testcaseExecution.setTestcase(null);

		return testcaseExecution;
	}

	public List<Testsuite> getTestsuites() {
		return this.testsuites;
	}

	public void setTestsuites(List<Testsuite> testsuites) {
		this.testsuites = testsuites;
	}

	public Testsuite addTestsuite(Testsuite testsuite) {
		getTestsuites().add(testsuite);
		testsuite.setTestcase(this);

		return testsuite;
	}

	public Testsuite removeTestsuite(Testsuite testsuite) {
		getTestsuites().remove(testsuite);
		testsuite.setTestcase(null);

		return testsuite;
	}

}