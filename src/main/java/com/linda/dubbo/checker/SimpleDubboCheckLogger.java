package com.linda.dubbo.checker;

import java.util.List;

import org.apache.log4j.Logger;

public class SimpleDubboCheckLogger implements Service {

	private SimpleDubboChecker checker;

	private ZkDubboProviderService providerService;

	private Logger logger = Logger.getLogger("dubboChecker");

	private String zkConnectString;

	public String getZkConnectString() {
		return zkConnectString;
	}

	public void setZkConnectString(String zkConnectString) {
		this.zkConnectString = zkConnectString;
	}

	public void startService() {
		checker = new SimpleDubboChecker();
		providerService = new ZkDubboProviderService();
		providerService.setConnectString(zkConnectString);
		providerService.startService();
		checker.setProviderService(providerService);
		checker.startService();
	}

	public void checkAndLog(List<DubboBean> beans) {
		logger.info("startCheck services:" + JSONUtils.toJSON(beans));
		for (DubboBean bean : beans) {
			boolean exist = checker.exist(bean.getService(), bean.getVersion());
			logger.info(bean.getService() + " " + bean.getVersion() + "  ----> " + exist);
		}
	}

	public void checkHostAndPort(List<DubboBean> beans, String host, int port, long timeDiff) {
		logger.info("startCheck:" + host + ":" + port + " " + timeDiff + " services:" + JSONUtils.toJSON(beans));
		for (DubboBean bean : beans) {
			boolean exist = checker.exist(bean.getService(), bean.getVersion(), host, port, timeDiff);
			logger.info(bean.getService() + " " + bean.getVersion() + "  ----> " + exist);
		}
	}

	public void checkSeld(List<DubboBean> beans, long diff) {
		logger.info("startCheckSelf:" + diff + " services:" + JSONUtils.toJSON(beans));
		logger.info("selfIPs:" + JSONUtils.toJSON(SensorUtils.getLocalV4IPs()));
		for (DubboBean bean : beans) {
			boolean exist = checker.existSelf(bean.getService(), bean.getVersion(), diff);
			logger.info(bean.getService() + " " + bean.getVersion() + "  ----> " + exist);
		}
	}

	public void stopService() {
		checker.stopService();
		providerService.stopService();
	}
}
