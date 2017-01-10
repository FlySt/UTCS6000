package com.ncjk.utcs.modules.resources.basic.services.interfaces;

import java.util.List;

import com.ncjk.utcs.modules.resources.basic.pojo.UtcsDept;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public interface IDeptService {

	/**
	 * 根据部门名称分页查询部门数据
	 * @param 部门名称
	 * @param 当前页码数
	 * @param 每页显示条数
	 * @return 返回符合要求的所有对象JSON
	 */
	public JSONObject findDepts(String deptName, int page, int pageSize);

	/**
	 * 根据部门id删除部门
	 * @param 部门ID
	 * @return 返回值true为删除成功，false为删除失败
	 */
	public boolean delDpets(int[] ids);

	/**
	 * 保存部门
	 * @param 部门对象
	 * @return 返回值true为保存成功，false为保存失败
	 */
	public boolean saveOrUpdateDept(UtcsDept deptInfo);

	/**
	 * 根据部门ID查询部门对象
	 * @param 部门ID
	 * @return 返回部门对象
	 */
	public UtcsDept findDeptById(int deptId);

	
	public JSONObject findDeptBydeptId(int deptId);
	/**
	 * 根据HQL语句创建部门树
	 * @param 查询HQL语句
	 * @param 当前页码数
	 * @param 每页显示条数
	 * @return 返回JSON对象数组
	 */
	public JSONArray buildDeptsTree(String deptLevel,String type);

	/**
	 * 查找部门ID下的所属部门ID
	 * @param 部门ID
	 * @return 返回以逗号隔开的ID字符串
	 */
	public String returnDeptIdString(int id);

	/**
	 * 当部门ID有值时，判断用户名是否有重复
	 * @param 部门名deptName 部门ID
	 * @return 部门名称存在返回true 不存在返回false
	 */
	public boolean isExitDeptName(String depName, int id);

	public List<UtcsDept> deptList(Integer deptId);
}
