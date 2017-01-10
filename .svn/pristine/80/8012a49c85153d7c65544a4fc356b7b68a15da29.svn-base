package com.ncjk.utcs.modules.system.services;

import com.ncjk.utcs.common.dao.ICommonDAO;
import com.ncjk.utcs.modules.system.pojo.UtcsPluginParam;
import com.ncjk.utcs.modules.system.services.interfaces.IPluginParamService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/**
 * Created by swl on 2016/11/30.
 */
@Service("plugParamService")
public class PlugParamService implements IPluginParamService {
    @Resource
    ICommonDAO commonDAO;
    /**
     * 查找参数信息
     * @return
     */
    @Override
    public UtcsPluginParam findPluginParam() {
        String hql = "from UtcsPluginParam t where 1=1";
        UtcsPluginParam utcsPluginParam = (UtcsPluginParam) commonDAO.findByHql(hql);
        return utcsPluginParam;
    }

    /**
     * 获取参数信息
     * @return
     */
    @Override
    public JSONObject getPluginParam(String fileName) {
        JSONObject jsonObject = new JSONObject();
        UtcsPluginParam utcsPluginParam = findPluginParam();
        jsonObject.put("serverIp",utcsPluginParam.getServerIP());
        jsonObject.put("ftpUser",utcsPluginParam.getFtpUser());
        jsonObject.put("ftpPwd",utcsPluginParam.getFtpPwd());
        jsonObject.put("ftpPort",utcsPluginParam.getFtpPort());
        jsonObject.put("tcpPort",utcsPluginParam.getTcpPort());
        String ftpDir = utcsPluginParam.getFtpDir();
        File dirFile = new File(ftpDir);
        if(!dirFile.exists()){
            dirFile.mkdir();
        }
        String filePath = dirFile+System.getProperty("file.separator")+fileName;
        File file = new File(filePath);
        if (!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }

    /**
     * 获取Tcp的端口号
     * @return
     */
    @Override
    public Integer getTcpPort() {
        Integer port = findPluginParam().getTcpPort();
        return port;
    }
}
