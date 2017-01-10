package com.ncjk.utcs.common.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import org.apache.struts2.ServletActionContext;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

public class FileUtil {
	private static Log log = LogFactory.getLog(FileUtil.class);

	public static byte[] toByteArray(File photo) throws IOException {
		FileInputStream fis = new FileInputStream(photo);
		BufferedInputStream bis = new BufferedInputStream(fis);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		int c = bis.read();
		while (c != -1) {
			baos.write(c);
			c = bis.read();
		}
		fis.close();
		bis.close();
		byte[] rtn = baos.toByteArray();
		baos.close();
		return rtn;
	}

	public static byte[] buildThumbnail(File srcFile) {
		byte[] rtn = null;
		try {
			Image src = ImageIO.read(srcFile); // 构造Image对象
			int oldWidth = src.getWidth(null); // 得到源图宽
			int oldHeight = src.getHeight(null);// 得到源图高

			log.debug("old width is " + oldWidth);
			log.debug("old height is " + oldHeight);

			float divWidth = 200f; // 限制宽度为200
			int newWidth = 200; // 缩略图宽,
			int newHeight = 0; // 缩略图高
			float tmp;
			if (oldWidth > newWidth) {
				tmp = oldWidth / divWidth;
				newWidth = Math.round(oldWidth / tmp);// 计算缩略图高
				newHeight = Math.round(oldHeight / tmp);// 计算缩略图高
				log.debug("tmp scale is  " + tmp);
			} else {
				newWidth = oldWidth;
				newHeight = oldHeight;
			}

			int imageHeight = 300;
			int imageWidth = 300;

			log.debug("new width is " + newWidth);
			log.debug("new height is " + newHeight);

			BufferedImage bufferedImage = new BufferedImage(imageWidth,
					imageHeight, BufferedImage.TYPE_INT_RGB);

			Graphics2D graphics2D = (Graphics2D) bufferedImage.createGraphics();
			graphics2D.setBackground(Color.WHITE);
			graphics2D.clearRect(0, 0, imageWidth, imageHeight);
			bufferedImage.getGraphics().drawImage(src,
					//(imageWidth - oldWidth) / 2, (imageHeight - newHeight) / 2,
					0,0,
					newWidth, newHeight, null); // 绘制缩小后的图

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			BufferedOutputStream bos = new BufferedOutputStream(baos);
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(bos);
			encoder.encode(bufferedImage); // 近JPEG编码
			rtn = baos.toByteArray();
			bos.close();
			baos.close();
		} catch (Exception e) {
			log.debug(e);
		} finally {

		}
		return rtn;
	}


	/**
	 * 创建压缩文件
	 * @param path  文件夹路径
	 * @param resourceFile  源文件名  不存在则创建
	 * @param targetFile  压缩文件名
	 * @throws Exception
	 */
	public static void crompressFile(String path,String resourceFile,String targetFile) throws Exception{
		File directory = new File(path);
		if(!directory.exists()){
			System.out.println("创建文件夹");
			directory.mkdir();
		}
		//定义要压缩的文件  也就是说在D盘里有个 demo.txt 的文件搜索(必须要有,否者会有异常,实际应用中可判断);
		File file = new File(directory+"\\"+resourceFile);
		System.out.println("file:"+file);
		if(!file.exists()){
			file.createNewFile();
		}
		//定义压缩文件的名称
		File zipFile = new File(directory+"\\"+targetFile);

		//定义输入文件流
		InputStream input = new FileInputStream(file);

		//定义压缩输出流
		ZipOutputStream zipOut = null;

		//实例化压缩输出流,并制定压缩文件的输出路径  就是D盘下,名字叫 demo.zip
		zipOut = new ZipOutputStream(new FileOutputStream(zipFile));

		zipOut.putNextEntry(new ZipEntry(file.getName()));

		//设置注释
	//	zipOut.setComment("www.demo.com");
		int temp = 0;
		while((temp = input.read()) != -1) {
		zipOut.write(temp);
		}
		input.close();
		zipOut.close();
	}

	public static void fileDownload(String targetPath){
	//	String realPath = ServletActionContext.getServletContext().getRealPath("");
	//	System.out.println("realPath:"+realPath);
		HttpServletRequest request = ServletActionContext.getRequest();
		String urlPath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+"/zip/"+"123.why";
		String filePath = "E:\\zip";
		File dirFile = new File(filePath);
		if(!dirFile.exists()){
			//文件路径不存在时，自动创建目录
			dirFile.mkdir();
		}
		try {
			URL url = new URL(urlPath);
			//从服务器上获取图片并保 存
			URLConnection connection = url.openConnection();
			InputStream in = connection.getInputStream();
			FileOutputStream os = new FileOutputStream(filePath+"\\123.why");

			byte[] buffer = new byte[4 * 1024];

			int read;

			while ((read = in.read(buffer)) > 0) {

				os.write(buffer, 0, read);

			}

			os.close();

			in.close();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		//	System.out.println("basePath:"+basePath);


	}
}
