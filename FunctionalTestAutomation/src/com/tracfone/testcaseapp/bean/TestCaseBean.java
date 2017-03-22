package com.tracfone.testcaseapp.bean;

import java.util.List;
import java.util.Map;

import javax.faces.model.SelectItem;

public class TestCaseBean {

	private String endPoint;
	private String env;
	private String serviceType;
	private String input;
	private String output;
	private String name;
	private boolean selected;
	private List<String> envList;
	private long id;
	private String scenarioID;
	private List<SelectItem> flows;
	private List<SelectItem> esnPartNumList;
	private List<SelectItem> simPartNumList;
	private List<SelectItem> pinPartNumList;
	private List<SelectItem> brandList;
	private List<SelectItem> channelList;
	private String esnPartNum;
	private String simPartNum;
	private String pinPartNum;
	private String brand;
	private String channel;
	private String domain;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public List<SelectItem> getBrandList() {
		return brandList;
	}

	public void setBrandList(List<SelectItem> brandList) {
		this.brandList = brandList;
	}

	public List<SelectItem> getChannelList() {
		return channelList;
	}

	public void setChannelList(List<SelectItem> channelList) {
		this.channelList = channelList;
	}

	public String getEsnPartNum() {
		return esnPartNum;
	}

	public void setEsnPartNum(String esnPartNum) {
		this.esnPartNum = esnPartNum;
	}

	public String getSimPartNum() {
		return simPartNum;
	}

	public void setSimPartNum(String simPartNum) {
		this.simPartNum = simPartNum;
	}

	public String getPinPartNum() {
		return pinPartNum;
	}

	public void setPinPartNum(String pinPartNum) {
		this.pinPartNum = pinPartNum;
	}

	private Map<String, ReferenceData> referenceMap;

	public Map<String, ReferenceData> getReferenceMap() {
		return referenceMap;
	}

	public void setReferenceMap(Map<String, ReferenceData> referenceMap) {
		this.referenceMap = referenceMap;
	}

	public List<SelectItem> getEsnPartNumList() {
		return esnPartNumList;
	}

	public void setEsnPartNumList(List<SelectItem> esnPartNumList) {
		this.esnPartNumList = esnPartNumList;
	}

	public List<SelectItem> getSimPartNumList() {
		return simPartNumList;
	}

	public void setSimPartNumList(List<SelectItem> simPartNumList) {
		this.simPartNumList = simPartNumList;
	}

	public List<SelectItem> getPinPartNumList() {
		return pinPartNumList;
	}

	public void setPinPartNumList(List<SelectItem> pinPartNumList) {
		this.pinPartNumList = pinPartNumList;
	}

	private int flow;

	public int getFlow() {
		return flow;
	}

	public void setFlow(int flow) {
		this.flow = flow;
	}

	public List<SelectItem> getFlows() {
		return flows;
	}

	public void setFlows(List<SelectItem> flows) {
		this.flows = flows;
	}

	public String getScenarioID() {
		return scenarioID;
	}

	public void setScenarioID(String scenarioID) {
		this.scenarioID = scenarioID;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public List<String> getEnvList() {
		return envList;
	}

	public void setEnvList(List<String> envList) {
		this.envList = envList;
	}

	public String getEndPoint() {
		return endPoint;
	}

	public void setEndPoint(String endPoint) {
		this.endPoint = endPoint;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getInput() {
		return input;
	}

	public void setInput(String input) {
		this.input = input;
	}

	public String getOutput() {
		return output;
	}

	public void setOutput(String output) {
		this.output = output;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

}
