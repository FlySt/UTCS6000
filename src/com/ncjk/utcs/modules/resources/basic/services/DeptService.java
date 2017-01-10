package com.ncjk.utcs.modules.resources.basic.services;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.ncjk.utcs.common.dao.ICommonDAO;
//import com.ncjk.utcs.common.util.Pinyin;
import com.ncjk.utcs.modules.resources.basic.pojo.UtcsDept;
import com.ncjk.utcs.modules.resources.basic.pojo.UtcsUser;
//import com.ncjk.utcs.modules.basic.pojo.UtcsUser;
import com.ncjk.utcs.modules.resources.basic.services.interfaces.IDeptService;

@Scope("prototype")
@Component("deptService")
public class DeptService implements IDeptService {
	private ICommonDAO comDAO;

	/**
	 * 根据部门名称分页查询部门数据
	 * @param 部门名称
	 * @param 当前页码数
	 * @param 每页显示条数
	 * @return 返回符合要求的所有对象JSON
	 */
	public JSONObject findDepts(String deptName, int page, int pageSize) {
		String hql = " from UtcsDept t where t.showSign!=0  ";
		StringBuffer condition = new StringBuffer();
		if (deptName != null && !"".equals(deptName)) {
			deptName = deptName.toLowerCase();
			condition.append(" and (t.deptName like '%" + deptName + "%' or t.deptFullSpell like '%" + deptName
					+ "%' or t.deptHeadSpell like '" + deptName + "%') ");
		}
		JSONObject jsonObj = new JSONObject();
		JSONArray invdata = new JSONArray();
		List<UtcsDept> utcsDepts = findDeptsByHql(hql + condition.toString(), page, pageSize);
		int total = countDepts(condition.toString());
		if (utcsDepts != null && !utcsDepts.isEmpty()) {
			for (UtcsDept utcsDept : utcsDepts) {
				JSONObject js = new JSONObject();
				js.put("deptId", utcsDept.getDeptId()!=null?utcsDept.getDeptId():"");

				js.put("deptName", utcsDept.getDeptName()!=null?utcsDept.getDeptName():"");
				if (utcsDept.getDeptLevel() == 0) {
					js.put("deptLevel", "部局");
				}
				else if (utcsDept.getDeptLevel() == 1) {
					js.put("deptLevel", "总队");
				}
				else if (utcsDept.getDeptLevel() == 2) {
					js.put("deptLevel", "支队");
				}
				else if (utcsDept.getDeptLevel() == 3) {
					js.put("deptLevel", "大队");
				}
				else if (utcsDept.getDeptLevel() == 4) {
					js.put("deptLevel", "中队");
				}
				else {
					js.put("deptLevel", "其他");
				}
				if (utcsDept.getDeptType() == 0) {
					js.put("deptType", "部门");
				}
				else {
					js.put("deptType", "区域");
				}
				js.put("deptCode", utcsDept.getDeptCode()!=null?utcsDept.getDeptCode():"");
				js.put("regionCode", utcsDept.getRegionCode()!=null?utcsDept.getRegionCode():"");
				js.put("deptExplain", utcsDept.getDeptExplain()!=null?utcsDept.getDeptExplain():"");
				Integer parentDeptId = utcsDept.getParentDeptId();
				if (parentDeptId != null) {
					UtcsDept uDept = findDeptById(utcsDept.getParentDeptId());
					if (uDept != null) {
						js.put("parentDeptName", uDept.getDeptName());
					}
					else {
						js.put("parentDeptName", "—");
					}
				}
				else {
					js.put("parentDeptName", "—");
				}
			//	js.put("dealAddress", utcsDept.getDealAddress());
				js.put("dealAddress", utcsDept.getDealAddress()!=null?utcsDept.getDealAddress():"");
				invdata.add(js);
			}
		}
		int totalPage = 0;
		if (pageSize != 0) {
			int resultPage = total % pageSize;
			totalPage = (resultPage == 0) ? (total / pageSize) : (total / pageSize + 1);
		}

		jsonObj.put("data", invdata);
		jsonObj.put("page", page);
		jsonObj.put("total", total);
		jsonObj.put("totalPage", totalPage);
		jsonObj.put("objectId", 1);
		return jsonObj;
	}
	/**
	 * 根据部门名称分页查询部门数据
	 * @param 部门名称
	 * @param 当前页码数
	 * @param 每页显示条数
	 * @return 返回符合要求的所有对象JSON
	 */
	public JSONObject findDeptBydeptId(int deptId) {
		String hql = " from UtcsDept t where t.showSign!=0  ";
		StringBuffer condition = new StringBuffer();

		condition.append(" and t.deptId="+deptId);

		JSONObject js = new JSONObject();
		List<UtcsDept> utcsDepts = findDeptsByHql(hql + condition.toString(), 0, 0);
		if (utcsDepts != null && !utcsDepts.isEmpty()) {
				UtcsDept utcsDept = utcsDepts.get(0);
				js.put("deptId", utcsDept.getDeptId());

				js.put("deptName", utcsDept.getDeptName());
				if (utcsDept.getDeptLevel() == 0) {
					js.put("deptLevel", "部局");
				}
				else if (utcsDept.getDeptLevel() == 1) {
					js.put("deptLevel", "总队");
				}
				else if (utcsDept.getDeptLevel() == 2) {
					js.put("deptLevel", "支队");
				}
				else if (utcsDept.getDeptLevel() == 3) {
					js.put("deptLevel", "大队");
				}
				else if (utcsDept.getDeptLevel() == 4) {
					js.put("deptLevel", "中队");
				}
				else {
					js.put("deptLevel", "其他");
				}
				if (utcsDept.getDeptType() == 0) {
					js.put("deptType", "部门");
				}
				else {
					js.put("deptType", "区域");
				}
				js.put("deptCode", utcsDept.getDeptCode());
				js.put("regionCode", utcsDept.getRegionCode());
				js.put("deptExplain", utcsDept.getDeptExplain());
				Integer parentDeptId = utcsDept.getParentDeptId();
				if (parentDeptId != null) {
					UtcsDept uDept = findDeptById(utcsDept.getParentDeptId());
					if (uDept != null) {
						js.put("parentDeptName", uDept.getDeptName());
					}
					else {
						js.put("parentDeptName", "—");
					}
				}
				else {
					js.put("parentDeptName", "—");
				}
				js.put("dealAddress", utcsDept.getDealAddress());
				
		}
		return js;
		
	}
	/**
	 * 根据查询HQL分页查询部门数据
	 * @param 查询HQL语句
	 * @param 当前页码数
	 * @param 每页显示条数
	 * @return 返回部门对象集合
	 */
	public List<UtcsDept> findDeptsByHql(String hql, int page, int pageSize) {
		@SuppressWarnings("unchecked")
		List<UtcsDept> utcsDepts = (List<UtcsDept>) comDAO.findByHql(hql, page, pageSize);
		return utcsDepts;
	}

	/**
	 * 根据HQL返回部门数
	 * @param 查询HQL语句
	 * @return 返回部门条数
	 */
	public int countDepts(String hql) {
		int count = 0;
		StringBuffer sb = new StringBuffer(" select count(t.id) from  UtcsDept t where t.showSign!=0   ");
		sb.append(hql);
		Object objs = comDAO.findByHql(sb.toString());
		if (objs != null) {
			count = ((Long) objs).intValue();
		}
		return count;
	}

	/**
	 * 根据部门id删除部门
	 * @param 部门ID数组
	 * @return 返回值true为删除成功，false为删除失败
	 */
	public boolean delDpets(int[] ids) {
		boolean b = false;
		if (ids != null && ids.length > 0) {
			for (int id : ids) {
				UtcsDept dept = (UtcsDept) comDAO.findObjectById(UtcsDept.class, Integer.valueOf(id));
				b = comDAO.deleteByHql(" delete UtcsDept t where t.deptId = " + id);
				if (b) {
					b = comDAO.updateByHql(" update UtcsDept t set t.parentDeptId=" + dept.getParentDeptId()
							+ " where t.parentDeptId = " + id);
				}
			}
		}
		return b;
	}

	/**
	 * 根据部门ID查询部门对象
	 * @param 部门ID
	 * @return 返回部门对象
	 */
	public UtcsDept findDeptById(int deptId) {
		UtcsDept utcs = (UtcsDept) comDAO.findObjectById(UtcsDept.class, deptId);
		return utcs;
	}

	/**
	 * 保存部门
	 * @param 部门对象
	 * @return 返回值true为保存成功，false为保存失败
	 */
	public boolean saveOrUpdateDept(UtcsDept deptInfo) {
		UtcsDept dept = new UtcsDept();
		if (deptInfo.getDeptId() == null) {
			dept.setUpdateTime(new Date());
		}
		else {
			dept = findDeptById(deptInfo.getDeptId());
		}
		dept.setDeptCode(deptInfo.getDeptCode());
		dept.setDeptLevel(deptInfo.getDeptLevel());
		dept.setDeptName(deptInfo.getDeptName());
		dept.setDeptType(deptInfo.getDeptType());
		dept.setDeptExplain(deptInfo.getDeptExplain());
		dept.setRegionCode(deptInfo.getRegionCode());
		dept.setDealAddress(deptInfo.getDealAddress());
		dept.setUpdateTime(new Date());
		dept.setParentDeptId(deptInfo.getParentDeptId());
		dept.setShowSign(1);
		
		UtcsUser user = (UtcsUser) ServletActionContext.getRequest().getSession().getAttribute("user");
		dept.setUpdateAccount(user.getUserAccount());
				boolean b = comDAO.saveOrUpdate(dept);

		return b;
	}

	/**
	 * 根据HQL语句创建部门树
	 * @param 查询HQL语句
	 * @param 当前页码数
	 * @param 每页显示条数
	 * @return 返回JSON对象数组
	 */
	public JSONArray buildDeptsTree(String deptLevel,String type) {
		JSONArray invdata = new JSONArray();
		String hql = "from UtcsDept t where t.showSign='1' and t.deptLevel <="+deptLevel;
		JSONObject fjs = new JSONObject();
		if("-1".equals(type)){
			fjs.put("id", "dept_");
			fjs.put("pId", "dept_");
			fjs.put("name", "-");
			fjs.put("deptLevel", 1);
			invdata.add(fjs);
		}
		List<UtcsDept> utcsDepts = findDeptsByHql(hql, 0, 0);
		
		if (utcsDepts != null && !utcsDepts.isEmpty()) {
			for (UtcsDept utcsDept : utcsDepts) {
				Integer parentId = utcsDept.getParentDeptId();
				if (parentId == null) {
					parentId = -1;
				}
				JSONObject js = new JSONObject();
				js.put("id", "dept_" + utcsDept.getDeptId());
				js.put("pId", "dept_" + parentId);
				js.put("name", utcsDept.getDeptName());
				js.put("deptLevel", utcsDept.getDeptLevel());
				invdata.add(js);
			}
		}
		return invdata;
	}

	/**
	 * 查找部门ID下的所属部门ID
	 * @param 部门ID
	 * @return 返回ID数组
	 */
	public int[] findDeptByParentDeptId(int deptId) {
		int[] ids = new int[1];
		String sql = "select d.DEPT_ID from UTCS_DEPT d  start with ( d.DEPT_ID = " + deptId
				+ ")  connect by  prior d.DEPT_ID=d.PARENT_DEPT_ID ";
		@SuppressWarnings("unchecked")
		List<Object> objs = (List<Object>) comDAO.findBySQL(sql, 0, 0);
		if (objs != null && objs.size() > 0) {
			ids = new int[objs.size()];
			int i = 0;
			for (Object ob : objs) {
				ids[i] = ((BigDecimal) ob).intValue();
				i++;
			}
		}
		return ids;
	}

	/**
	 * 查找部门ID下的所属部门ID
	 * @param 部门ID
	 * @return 返回以逗号隔开的ID字符串
	 */
	public String returnDeptIdString(int id) {
		String result = "";
		int[] ids = findDeptByParentDeptId(id);
		if (ids != null && ids.length > 0) {
			for (long lid : ids) {
				result = result + ", " + lid;
			}
			result = result.substring(1);
		}
		return result;
	}

	/**
	 * 当部门ID有值时，判断用户名是否有重复
	 * @param 部门名deptName 部门ID
	 * @return 部门名称存在返回true 不存在返回false
	 */
	public boolean isExitDeptName(String deptName, int id) {
		boolean b = true;
		if (id == 0) {
			UtcsDept dept = (UtcsDept) comDAO.findByHql(" from UtcsDept t where t.deptName='" + deptName + "'");
			if (dept != null) {
				return false;
			}
		}
		if ("无上级部门".equals(deptName)) {
			return false;
		}
		return b;
	}

	/**
	 * 查询所有部门信息
	 */
	public List<UtcsDept> deptList(Integer deptId) {
		String hql = " from UtcsDept t where  t.showSign!=0";
		if (deptId != 0) {
			hql += " and t.deptId != " + deptId;
		}
		List<UtcsDept> utcsDepts = findDeptsByHql(hql, 0, 0);
		return utcsDepts;
	}

	public ICommonDAO getComDAO() {
		return comDAO;
	}

	@Resource(name = "commonDAO")
	public void setComDAO(ICommonDAO comDAO) {
		this.comDAO = comDAO;
	}
}