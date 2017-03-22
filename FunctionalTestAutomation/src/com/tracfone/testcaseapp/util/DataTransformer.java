package com.tracfone.testcaseapp.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.tracfone.testcaseapp.DAO.ServiceDAO;
import com.tracfone.testcaseapp.DAO.Impl.ServiceDAOImpl;
import com.tracfone.testcaseapp.Entity.Brand;
import com.tracfone.testcaseapp.Entity.Channel;
import com.tracfone.testcaseapp.Entity.EnvDetail;
import com.tracfone.testcaseapp.Entity.EsnReference;
import com.tracfone.testcaseapp.Entity.Flow;
import com.tracfone.testcaseapp.Entity.PinReference;
import com.tracfone.testcaseapp.Entity.SimReference;
import com.tracfone.testcaseapp.bean.ReferenceData;
import com.tracfone.testcaseapp.bean.StaticDataBean;
import com.tracfone.testcaseapp.bean.UIBean;

public class DataTransformer {

	public Map<String, ReferenceData> fetchCompatibleData() {
		ServiceDAO dao = new ServiceDAOImpl();
		Map<String, ReferenceData> map = null;
		List<EsnReference> compatibleData = dao.getCompatibleData();
		if (compatibleData != null && compatibleData.size() > 0) {
			Iterator<EsnReference> itr = compatibleData.iterator();
			map = new HashMap<String, ReferenceData>();
			ReferenceData referenceData = new ReferenceData();
			List<String> pinRef = null;
			List<String> simRef = null;
			while (itr.hasNext()) {
				pinRef = null;
				simRef = null;
				EsnReference esnRef = itr.next();
				if (esnRef.getPinReferences() != null && esnRef.getPinReferences().size() > 0) {
					pinRef = new ArrayList<String>();
					Iterator<PinReference> pinItr = esnRef.getPinReferences().iterator();
					while (pinItr.hasNext()) {
						pinRef.add(pinItr.next().getPartnum());
					}
				}
				referenceData.setPinPartNum(pinRef);
				if (esnRef.getSimReferences() != null && esnRef.getSimReferences().size() > 0) {
					simRef = new ArrayList<String>();
					Iterator<SimReference> simItr = esnRef.getSimReferences().iterator();
					while (simItr.hasNext()) {
						simRef.add(simItr.next().getPartnum());
					}
				}
				referenceData.setSimPartNum(simRef);
				map.put(esnRef.getPartnum(), referenceData);
			}
		}
		return map;
	}

	public UIBean fetchReferenceData(UIBean uiBean) {
		ServiceDAO serviceDAO = new ServiceDAOImpl();
		List<Flow> flows = serviceDAO.getFlowDetails();
		if (flows != null && flows.size() > 0) {
			List<StaticDataBean> flowList = new ArrayList<StaticDataBean>();
			Iterator<Flow> itr = flows.iterator();
			while (itr.hasNext()) {
				Flow flow = itr.next();
				StaticDataBean bean = new StaticDataBean();
				bean.setKey("" + flow.getId());
				bean.setValue(flow.getName());
				flowList.add(bean);
			}
			uiBean.setFlowList(flowList);
		}

		List<EnvDetail> envDetails = serviceDAO.getEnvDetails();
		if (envDetails != null && envDetails.size() > 0) {
			List<StaticDataBean> envDetailsList = new ArrayList<StaticDataBean>();
			Iterator<EnvDetail> itr = envDetails.iterator();
			while (itr.hasNext()) {
				EnvDetail env = itr.next();
				StaticDataBean bean = new StaticDataBean();
				bean.setKey(env.getId().getEnvname());
				bean.setValue(env.getId().getEnvname());
				envDetailsList.add(bean);
			}
			uiBean.setEnvList(envDetailsList);
		}

		List<Brand> brands = serviceDAO.getBrandDetails();
		if (brands != null && brands.size() > 0) {
			List<StaticDataBean> brandList = new ArrayList<StaticDataBean>();
			Iterator<Brand> itr = brands.iterator();
			while (itr.hasNext()) {
				Brand brand = itr.next();
				StaticDataBean bean = new StaticDataBean();
				bean.setKey("" + brand.getBrandid());
				bean.setValue(brand.getBrandname());
				brandList.add(bean);
			}
			uiBean.setBrandList(brandList);
		}


		List<Channel> channels = serviceDAO.getSourceSystemDetails();
		if (channels != null && channels.size() > 0) {
			List<StaticDataBean> channelList = new ArrayList<StaticDataBean>();
			Iterator<Channel> itr = channels.iterator();
			while (itr.hasNext()) {
				Channel channel = itr.next();
				StaticDataBean bean = new StaticDataBean();
				bean.setKey("" + channel.getChannelid());
				bean.setValue(channel.getChannelname());
				channelList.add(bean);
			}
			uiBean.setChannelList(channelList);
		}
		uiBean.setCompatibilityData(fetchCompatibleData());
		return uiBean;
	}

}
