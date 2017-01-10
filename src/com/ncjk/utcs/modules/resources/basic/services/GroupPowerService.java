package com.ncjk.utcs.modules.resources.basic.services;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ncjk.utcs.common.dao.ICommonDAO;
import com.ncjk.utcs.modules.resources.basic.services.interfaces.IGroupPowerService;


/** 
* @ClassName: GroupPowerService 
* @Description: 用户组权限业务实现类
* @author 石望来
* @date 2016年8月19日 上午10:57:14 
* 
* tags 
*/
@Scope("prototype")
@Component("groupPowerService")
public class GroupPowerService implements IGroupPowerService{
	private ICommonDAO comDAO;

	/**  
	* @Description: 删除组权限信息
	* @param ids   
	* @return boolean   
	* @author shiwanglai
	* @date 2016年8月19日 上午11:02:03  
	*/
	public boolean delGroupPowers(int[] ids) {
		boolean b = false;
		if (ids != null && ids.length > 0) {
			String idstr = "";
			for (int id : ids) {
				idstr = idstr + "," + id;
			}
			idstr = idstr.substring(1);
			b = comDAO.deleteByHql(" delete UtcsGroupPower t where t.groupPowerId in (" + idstr + ")");
		}
		return b;
	}

	public ICommonDAO getComDAO() {
		return comDAO;
	}

	@Resource(name = "commonDAO")
	public void setComDAO(ICommonDAO comDAO) {
		this.comDAO = comDAO;
	}
}
