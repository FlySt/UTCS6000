package com.ncjk.utcs.common.servlet;

import java.util.List;

import org.codehaus.jettison.json.JSONObject;

public interface ISignalDeviceServlet {
	public JSONObject saveOrUpdateSignal(String content, List<byte[]> imageList);

	public String delSignalDevice(Integer signalId, String content);

	public byte[] getImage(Integer id);

	public String saveSignalOperateLog(String content);
}