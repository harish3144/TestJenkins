package com.tracfone.testcaseapp.bean;

import java.util.List;
import java.util.Map;

public class UIBean {
	private List<TestCaseBean> testCaseList;

	private int listSize;

	private List<StaticDataBean> serviceList;
	private List<StaticDataBean> channelList;
	private List<StaticDataBean> brandList;
	private List<StaticDataBean> envList;
	private List<StaticDataBean> flowList;

	public Map<String, ReferenceData> getCompatibilityData() {
		return compatibilityData;
	}

	public void setCompatibilityData(Map<String, ReferenceData> compatibilityData) {
		this.compatibilityData = compatibilityData;
	}

	private Map<String, ReferenceData> compatibilityData;

	public List<StaticDataBean> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<StaticDataBean> serviceList) {
		this.serviceList = serviceList;
	}

	public List<StaticDataBean> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<StaticDataBean> channelList) {
		this.channelList = channelList;
	}

	public List<StaticDataBean> getBrandList() {
		return brandList;
	}

	public void setBrandList(List<StaticDataBean> brandList) {
		this.brandList = brandList;
	}

	public List<StaticDataBean> getEnvList() {
		return envList;
	}

	public void setEnvList(List<StaticDataBean> envList) {
		this.envList = envList;
	}

	public List<StaticDataBean> getFlowList() {
		return flowList;
	}

	public void setFlowList(List<StaticDataBean> flowList) {
		this.flowList = flowList;
	}

	public void setListSize(int listSize) {
		this.listSize = listSize;
	}

	public int getListSize() {
		if (testCaseList != null) {
			return testCaseList.size();
		} else {
			return 0;
		}
	}

	public List<TestCaseBean> getTestCaseList() {
		return testCaseList;
	}

	public void setTestCaseList(List<TestCaseBean> testCaseList) {
		this.testCaseList = testCaseList;
	}

}
