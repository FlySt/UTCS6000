package com.ncjk.utcs.common.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class TransDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static WebApplicationContext context;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		List<byte[]> imageList = new ArrayList<byte[]>();
		ServletInputStream sis = request.getInputStream();

		// long l1 = System.currentTimeMillis();
		// FileOutputStream fos = new FileOutputStream("d:/pic/"+l1+".jpg");
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int len = sis.read(buf, 0, 1024);
		while (len != -1) {
			// fos.write(buf,0,len);
			bos.write(buf, 0, len);
			len = sis.read(buf, 0, 1024);
		}
		bos.close();
		// fos.close();
		sis.close();
		byte[] image = bos.toByteArray();
		imageList.add(image);
		String method = request.getParameter("method");
		String result = "0";
		ISignalDeviceServlet signalDeviceServlet = (ISignalDeviceServlet) findBean("signalDeviceServlet");
		if ("signalImage".equals(method)) {
			Integer signalId = Integer.valueOf(request.getParameter("id"));
			byte[] byteImage = signalDeviceServlet.getImage(signalId);
			if (byteImage != null) {
				response.setContentType("text/html;charset=utf-8");
				response.getOutputStream().write(byteImage);
			}
		} else if ("addSignal".equals(method)) {
			String content = request.getParameter("content");
			JSONObject json = signalDeviceServlet.saveOrUpdateSignal(content, imageList);

			response.setContentType("text/html;charset=utf-8");
			response.getOutputStream().write(json.toString().getBytes("utf-8"));
		} else if ("editSignal".equals(method)) {
			String content = request.getParameter("content");
			JSONObject json = signalDeviceServlet.saveOrUpdateSignal(content, imageList);
			try {
				result = (String) json.get("result");
			} catch (JSONException e) {
				e.printStackTrace();
			}
			response.setContentType("text/html;charset=utf-8");
			response.getOutputStream().write(result.getBytes("utf-8"));
		} else if ("delSignal".equals(method)) {
			String id = request.getParameter("id");
			String content = request.getParameter("content");
			result = signalDeviceServlet.delSignalDevice(Integer.valueOf(id), content);
			response.setContentType("text/html;charset=utf-8");
			response.getOutputStream().write(result.getBytes("utf-8"));
		} else if ("controlCar".equals(method)) {
			// String message = request.getParameter("message");
			result = "已接收";
			result = URLEncoder.encode(result, "UTF-8");
			response.setContentType("text/html;charset=utf-8");
			response.getOutputStream().write(result.getBytes("utf-8"));
		} else if ("signalLog".equals(method)) {
			String content = request.getParameter("content");
			result = signalDeviceServlet.saveSignalOperateLog(content);
			response.setContentType("text/html;charset=utf-8");
			response.getOutputStream().write(result.getBytes("utf-8"));
		}

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		System.out.println("调用doPost方法");
		doPost(request, response);
	}

	public Object findBean(String beanName) {
		if (context == null) {
			context = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
		}
		return context.getBean(beanName);
	}
}