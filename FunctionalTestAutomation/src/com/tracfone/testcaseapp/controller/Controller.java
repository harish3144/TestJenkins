package com.tracfone.testcaseapp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.xml.parsers.ParserConfigurationException;

import org.jdom2.JDOMException;
import org.xml.sax.SAXException;

import com.tracfone.testcaseapp.DAO.ServiceDAO;
import com.tracfone.testcaseapp.DAO.Impl.ServiceDAOImpl;
import com.tracfone.testcaseapp.Entity.Brand;
import com.tracfone.testcaseapp.Entity.Channel;
import com.tracfone.testcaseapp.Entity.EsnReference;
import com.tracfone.testcaseapp.Entity.Flow;
import com.tracfone.testcaseapp.Entity.PinReference;
import com.tracfone.testcaseapp.Entity.SimReference;
import com.tracfone.testcaseapp.Entity.Testcase;
import com.tracfone.testcaseapp.Entity.TestcaseExecution;
import com.tracfone.testcaseapp.Service.InvocationService;
import com.tracfone.testcaseapp.Service.Impl.InvocationServiceImpl;
import com.tracfone.testcaseapp.bean.ReferenceData;
import com.tracfone.testcaseapp.bean.TestCaseBean;
import com.tracfone.testcaseapp.bean.UIBean;
import com.tracfone.testcaseapp.util.DataTransformer;
import com.tracfone.testcaseapp.util.XMLParser;

public class Controller {
	public String login() {
		System.out.println("in login");
		String login = "Success";
		UIBean uiBean = populateBean();
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("uIBean", uiBean);
		return login;
	}

	public String addTestCase() {
		ServiceDAO dao = new ServiceDAOImpl();
		List<Flow> flows = dao.getFlowDetails();
		DataTransformer dataTransformer = new DataTransformer();
		TestCaseBean bean = (TestCaseBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("testCaseBean");
		if (bean == null) {
			bean = new TestCaseBean();
		}
		Map<String, ReferenceData> referenceMap = dataTransformer.fetchCompatibleData();
		bean.setReferenceMap(referenceMap);
		bean.setEsnPartNumList(getEsnPartNumList(referenceMap));
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem("", ""));
		bean.setSimPartNumList(items);
		bean.setPinPartNumList(items);
		bean.setFlows(populateFlows(flows));
		bean.setBrandList(populateBrand(dao.getBrandDetails()));
		bean.setChannelList(populateChannel(dao.getSourceSystemDetails()));
		dao.getSourceSystemDetails();
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("testCaseBean", bean);
		return "Success";
	}

	private List<SelectItem> getEsnPartNumList(Map<String, ReferenceData> referenceMap) {
		Set<String> esnRefNumSet = referenceMap.keySet();
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem("", ""));
		if (esnRefNumSet != null && esnRefNumSet.size() > 0) {
			Iterator<String> itr = esnRefNumSet.iterator();
			while (itr.hasNext()) {
				String esnPN = itr.next();
				items.add(new SelectItem(esnPN, esnPN));
			}
		}
		return items;
	}

	public void getDependency(ValueChangeEvent changeEvent) {
		System.out.println("value chage executed");
		String esnPartNum = (String) changeEvent.getNewValue();
		TestCaseBean bean = (TestCaseBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("testCaseBean");
		// String esnPartNum = bean.getEsnPartNum();
		if (esnPartNum != null && !esnPartNum.trim().equals("")) {
			ReferenceData data = bean.getReferenceMap().get(esnPartNum);
			if (data != null) {
				bean.setSimPartNumList(populatePartNum(data.getSimPartNum()));
				bean.setPinPartNumList(populatePartNum(data.getPinPartNum()));
			}
		}
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("testCaseBean", bean);
	}

	private List<SelectItem> populatePartNum(List<String> partNumlist) {
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem("", ""));
		if (partNumlist != null && partNumlist.size() > 0) {
			Iterator<String> itr = partNumlist.iterator();
			while (itr.hasNext()) {
				String partNum = itr.next();
				items.add(new SelectItem(partNum, partNum));
			}
		}
		return items;
	}

	private List<SelectItem> populateChannel(List<Channel> list) {
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem("", ""));
		if (list != null && list.size() > 0) {
			Iterator<Channel> itr = list.iterator();
			while (itr.hasNext()) {
				Channel channel = itr.next();
				items.add(new SelectItem(channel.getChannelname(), channel.getChannelname()));
			}
		}
		return items;
	}

	private List<SelectItem> populateBrand(List<Brand> list) {
		List<SelectItem> items = new ArrayList<SelectItem>();
		items.add(new SelectItem("", ""));
		if (list != null && list.size() > 0) {
			Iterator<Brand> itr = list.iterator();
			while (itr.hasNext()) {
				Brand brand = itr.next();
				items.add(new SelectItem(brand.getBrandname(), brand.getBrandname()));
			}
		}
		return items;
	}

	private List<SelectItem> populateFlows(List<Flow> flows) {
		List<SelectItem> items = new ArrayList<SelectItem>();
		if (flows != null && flows.size() > 0) {
			Iterator<Flow> itr = flows.iterator();
			while (itr.hasNext()) {
				Flow flow = itr.next();
				items.add(new SelectItem(flow.getId() + "", flow.getName()));
			}
		} else {
			items.add(new SelectItem("", ""));
		}
		return items;
	}

	public String executeTestSuite() {
		UIBean bean = (UIBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("uIBean");
		List<TestCaseBean> testCaseList = bean.getTestCaseList();
		try {
			if (testCaseList != null && testCaseList.size() > 0) {
				Iterator<TestCaseBean> itr = testCaseList.iterator();
				while (itr.hasNext()) {
					TestCaseBean testCaseBean = itr.next();
					invokeService(testCaseBean, 0);
					System.out.println(testCaseBean.getOutput());
				}
			}
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("uIBean", bean);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public String executeTestCase() {
		try {
			System.out.println("i am here");
			TestCaseBean bean = (TestCaseBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("testCaseBean");
			System.out.println(bean.getInput());
			invokeService(bean, 0);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("testCaseBean", bean);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public String generateAndExecuteTestCase() {
		try {
			System.out.println("i am here");
			TestCaseBean bean = (TestCaseBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
					.get("testCaseBean");
			System.out.println(bean.getInput());
			ServiceDAO dao = new ServiceDAOImpl();
			String esn = dao.generateESN(bean.getEsnPartNum());
			String pin = dao.generatePIN(bean.getPinPartNum());
			String sim = dao.generateSIM(bean.getSimPartNum());
			System.out.println("generated esn >>>> " + esn);
			System.out.println("generated sim >>>> " + sim);
			System.out.println("generated pin >>>> " + pin);
			XMLParser parser = new XMLParser();
			bean.setInput(parser.populateData(bean.getInput(), bean, esn, sim, pin));
			System.out.println(bean.getInput());
			invokeService(bean, 0);
			FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("testCaseBean", bean);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public String parseData() {
		ServiceDAO serviceDAO = new ServiceDAOImpl();
		System.out.println(serviceDAO.getEnvContext("ENV1", 1));
		System.out.println("BEFORE");
		System.out.println(serviceDAO.generateESN("SMALA621BGP5P"));
		List<EsnReference> compatibleData = serviceDAO.getCompatibleData();
		if (compatibleData != null && compatibleData.size() > 0) {
			Iterator<EsnReference> itr = compatibleData.iterator();
			while (itr.hasNext()) {
				EsnReference esnRef = itr.next();
				System.out.println(esnRef.getPartnum());
				System.out.println("pin size >>>>>" + esnRef.getPinReferences().size());
				System.out.println("sim size >>>>>" + esnRef.getSimReferences().size());
				if (esnRef.getPinReferences() != null && esnRef.getPinReferences().size() > 0) {
					Iterator<PinReference> pinItr = esnRef.getPinReferences().iterator();
					while (pinItr.hasNext()) {
						System.out.println(pinItr.next().getPartnum());
					}
				}
				if (esnRef.getSimReferences() != null && esnRef.getSimReferences().size() > 0) {
					Iterator<SimReference> simItr = esnRef.getSimReferences().iterator();
					while (simItr.hasNext()) {
						System.out.println(simItr.next().getPartnum());
					}
				}
			}
		}
		System.out.println();
		return "";
	}

	public String saveTestCase() {
		TestCaseBean bean = (TestCaseBean) FacesContext.getCurrentInstance().getExternalContext().getSessionMap()
				.get("testCaseBean");
		System.out.println("before persistence");
		Testcase testCase = new Testcase();
		testCase.setEndpoint(bean.getEndPoint());
		testCase.setRequest(bean.getInput());
		testCase.setName(bean.getName());
		Flow flow = new Flow();
		flow.setId(bean.getFlow());
		testCase.setFlow(flow);
		testCase.setResponse(bean.getOutput());
		ServiceDAO dao = new ServiceDAOImpl();
		dao.addTestCase(testCase);
		System.out.println("after persistence");
		UIBean uiBean = populateBean();
		FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("uIBean", uiBean);
		return "Success";

	}

	private void invokeService(TestCaseBean bean, int testCaseID)
			throws ParserConfigurationException, SAXException, IOException {
		XMLParser parser = new XMLParser();
		String parsedXml = parser.parseSOAPXml(bean.getInput());
		if (bean.getEnv().equals("DEVLISA")) {
			String input = bean.getInput();
			parsedXml = input.substring(0, input.indexOf("<soapenv:Body>") + 14) + parsedXml
					+ input.substring(input.indexOf("</soapenv:Body>"));
		}
		System.out.println("parsedXml>>>" + parsedXml);
		InvocationService invocationService = new InvocationServiceImpl();
		bean.setOutput(invocationService.invokeService(bean.getEnv(), 2, bean.getEndPoint(), parsedXml));
		ServiceDAO dao = new ServiceDAOImpl();
		if (testCaseID <= 0) {
			Testcase testcase = new Testcase();
			testcase.setEndpoint(bean.getEndPoint());
			testcase.setRequest(bean.getInput());
			testcase.setResponse(bean.getOutput());
			Flow flow = new Flow();
			flow.setId(bean.getFlow());
			testcase.setFlow(flow);
			testcase.setName(bean.getName());
			dao.addTestCase(testcase);
			testCaseID = (int) dao.getTestCase(bean.getName()).getId();
		}
		String executionStatus = parser.parseOutput(bean.getOutput());
		TestcaseExecution execution = populateTestCaseExecution(bean, testCaseID, executionStatus);
		dao.addTestCaseExecution(execution);
	}

	private TestcaseExecution populateTestCaseExecution(TestCaseBean bean, int testCaseID, String executionStatus) {
		TestcaseExecution execution = new TestcaseExecution();
		Testcase dependentTC = new Testcase();
		dependentTC.setId(testCaseID);
		execution.setTestcase(dependentTC);
		execution.setRequest(bean.getInput());
		execution.setResponse(bean.getOutput());
		execution.setDateOfExecution(new Date());
		execution.setExecutionstatus(executionStatus);
		return execution;
	}

	private UIBean populateBean() {
		UIBean bean = new UIBean();
		ServiceDAO dao = new ServiceDAOImpl();
		List<Testcase> result = dao.retrieveAllTestCases();
		List<TestCaseBean> testCaseList = new ArrayList<TestCaseBean>();
		if (result.size() > 0) {
			Iterator<Testcase> itr = result.iterator();
			while (itr.hasNext()) {
				Testcase testCaseEntity = itr.next();
				testCaseList.add(populateTestBean(testCaseEntity));
			}
		}
		bean.setTestCaseList(testCaseList);
		return bean;
	}

	private TestCaseBean populateTestBean(Testcase testCaseEntity) {
		TestCaseBean bean = new TestCaseBean();
		bean.setEndPoint(testCaseEntity.getEndpoint());
		bean.setScenarioID("" + testCaseEntity.getFlow().getId());
		bean.setInput(testCaseEntity.getRequest());
		bean.setOutput(testCaseEntity.getResponse());
		bean.setName(testCaseEntity.getName());
		bean.setId(testCaseEntity.getId());
		return bean;
	}

}
