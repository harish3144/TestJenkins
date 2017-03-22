package com.tracfone.testcaseapp.Entity;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the FLOW database table.
 * 
 */
@Entity
@NamedQuery(name="Flow.findAll", query="SELECT f FROM Flow f")
public class Flow implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="FLOW_ID_GENERATOR", sequenceName="FLOW_SEQ1")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="FLOW_ID_GENERATOR")
	private long id;

	private String name;

	//bi-directional many-to-one association to Testcase
	@OneToMany(mappedBy="flow")
	private List<Testcase> testcases;

	//bi-directional many-to-one association to Testsuite
	@OneToMany(mappedBy="flow")
	private List<Testsuite> testsuites;

	public Flow() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Testcase> getTestcases() {
		return this.testcases;
	}

	public void setTestcases(List<Testcase> testcases) {
		this.testcases = testcases;
	}

	public Testcase addTestcas(Testcase testcas) {
		getTestcases().add(testcas);
		testcas.setFlow(this);

		return testcas;
	}

	public Testcase removeTestcas(Testcase testcas) {
		getTestcases().remove(testcas);
		testcas.setFlow(null);

		return testcas;
	}

	public List<Testsuite> getTestsuites() {
		return this.testsuites;
	}

	public void setTestsuites(List<Testsuite> testsuites) {
		this.testsuites = testsuites;
	}

	public Testsuite addTestsuite(Testsuite testsuite) {
		getTestsuites().add(testsuite);
		testsuite.setFlow(this);

		return testsuite;
	}

	public Testsuite removeTestsuite(Testsuite testsuite) {
		getTestsuites().remove(testsuite);
		testsuite.setFlow(null);

		return testsuite;
	}

}