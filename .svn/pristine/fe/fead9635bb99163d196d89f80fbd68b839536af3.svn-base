package com.ncjk.utcs.common.netty.client;

import org.apache.struts2.ServletActionContext;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2016/10/31.
 */
public class UpLoad {

    public static void fileUpLoad() throws Exception {
        File file = new File("E:\\zip\\demo.zip");
        String urlPath = "http://192.168.200.244:7080/zip/";
        URL url = new URL(urlPath);
        //从服务器上获取图片并保 存
        URLConnection conn = url.openConnection();
        String boundary = "---------------------------" + System.currentTimeMillis();
        String boundaryInContent = "--" + boundary;
        String rn = "\r\n";
        conn.addRequestProperty("Connection", "keep-alive");
        conn.addRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
        conn.setDoInput(true);
        conn.setDoOutput(true);
        conn.setConnectTimeout(1000);
        conn.setUseCaches(false);
        conn.connect();
        OutputStream out = conn.getOutputStream();
        StringBuilder sb = new StringBuilder();
        sb.append(boundaryInContent).append(rn);
        sb.append("Content-Disposition: form-data; name=domain").append(rn).append(rn);
        sb.append("chengdu.myechinese.com").append(rn);
        sb.append(boundaryInContent).append(rn);
        sb.append("Content-Disposition: form-data; name=file; filename=" + file.getName()).append(rn);
        sb.append("Content-Type: application/octet-stream" ).append(rn).append(rn);
        out.write(sb.toString().getBytes());
        out.write(getBytes(file));
        sb.delete(0, sb.length());
        sb.append(rn).append(boundaryInContent).append("--").append(rn).append(rn);
        out.write(sb.toString().getBytes());
        out.flush();
        out.close();
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String res = reader.readLine();
        reader.close();
        System.out.println("Uploader: " + res);
    }

    //把文件转换成字节数组
    private static byte[] getBytes(File f) throws Exception {
        FileInputStream in = new FileInputStream(f);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] b = new byte[1024];
        int n;
        while ((n = in.read(b)) != -1) {
            out.write(b, 0, n);
        }
        in.close();
        return out.toByteArray();
    }

    public static void main(String[] args) throws Exception {
        UpLoad.fileUpLoad();
    }
}
