package com.ncjk.utcs.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import org.springframework.util.DefaultPropertiesPersister;

public class DataBaseDecrypt extends DefaultPropertiesPersister
{
	public void load(Properties props, InputStream is) throws IOException
	{
		Properties properties = new Properties();
		properties.load(is);
		String password = (String) properties.get("oracle.password");
		String user = (String) properties.get("oracle.username");
		DesUtil ds = null;
		try
		{
			ds = new DesUtil("database");
		}
		catch (Exception e1)
		{
			e1.printStackTrace();
		}

		if ((password != null))
		{
			// 这里通过解密算法，得到你的真实密码，然后写入到properties中
			try
			{
				String newPassword = ds.decrypt(password);
				properties.setProperty("oracle.password", newPassword);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}

		if ((user != null))
		{
			// 这里通过解密算法，得到你的真实密码，然后写入到properties中
			try
			{
				String newUser = ds.decrypt(user);
				properties.setProperty("oracle.username", newUser);
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		OutputStream outputStream = null;
		try
		{
			outputStream = new ByteArrayOutputStream();
			properties.store(outputStream, "");
			is = outStream2InputStream(outputStream);
			super.load(props, is);
		}
		catch (IOException e)
		{
			throw e;
		}
		finally
		{
			outputStream.close();
		}
	}

	private InputStream outStream2InputStream(OutputStream out)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bos = (ByteArrayOutputStream) out;
		ByteArrayInputStream swapStream = new ByteArrayInputStream(bos.toByteArray());
		return swapStream;
	}
}