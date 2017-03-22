package com.tracfone.testcaseapp.DAO;

import java.util.List;

import com.tracfone.testcaseapp.Entity.Brand;
import com.tracfone.testcaseapp.Entity.Channel;
import com.tracfone.testcaseapp.Entity.EnvDetail;
import com.tracfone.testcaseapp.Entity.EsnReference;
import com.tracfone.testcaseapp.Entity.Flow;
import com.tracfone.testcaseapp.Entity.Testcase;
import com.tracfone.testcaseapp.Entity.TestcaseExecution;

public interface ServiceDAO {

	public void addTestCase(Testcase testcase);

	public Testcase getTestCase(String testCaseName);

	public void addTestCaseExecution(TestcaseExecution execution);

	public List<Testcase> retrieveAllTestCases();

	public List<Testcase> retrieveScenarioTestCases(int flowID);

	public String getEnvContext(String envName, int serviceID);

	public List<EsnReference> getCompatibleData();

	public List<EnvDetail> getEnvDetails();

	public List<Flow> getFlowDetails();

	public List<Brand> getBrandDetails();

	public List<Channel> getSourceSystemDetails();

	public String generateESN(String partNum);

	public String generateSIM(String partNum);

	public String generatePIN(String partNum);
}
