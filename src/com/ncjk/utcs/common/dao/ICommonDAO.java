package com.ncjk.utcs.common.dao;

import java.io.Serializable;
import java.util.List;

public interface ICommonDAO extends Serializable{
	/**
	 * 根据HQL语句查询单个信息对象
	 * 
	 * @param 查询HQL语句
	 * @return 返回单个信息对象
	 */
	public Object findByHql(String hql);

	/**
	 * 根据HQL语句和条件集合查询数据集
	 * 
	 * @param 查询HQL语句
	 * @param 查询条件集合
	 * @return 返回查询结果集合
	 */
	public List<Object[]> findByHql(String hql, List<Object> obj);

	/**
	 * 根据HQL语句查询分页数据集
	 * 
	 * @param 查询HQL语句
	 * @param 当前页码数
	 * @param 每页显示条数
	 * @return 返回查询结果集合
	 */
	public List findByHql(String hql, int page, int pageSize);

	/**
	 * 根据HQL语句及条件查询分页数据集
	 * 
	 * @param 查询HQL语句
	 * @param 查询条件集合
	 * @param 当前页码数
	 * @param 每页显示条数
	 * @return 返回查询结果集合
	 */
	public List<Object[]> findByHql(String hql, List<Object> obj, int page,
			int pageSize);

	/**
	 * 根据ID值查询信息对象
	 * 
	 * @param 类型
	 * @param 对象ID值
	 * @return 返回信息对象结果
	 */
	public Object findObjectById(Class<?> classz, Integer id);

	/**
	 * 根据HQL语句更新信息
	 * 
	 * @param 查询HQL语句
	 * @return 返回更新结果(True-成功、False-失败)
	 */
	public boolean updateByHql(String hql);

	/**
	 * 根据信息对象更新信息
	 * 
	 * @param 信息对象
	 * @return 返回更新结果(True-成功、False-失败)
	 */
	public boolean saveOrUpdate(Object obj);

	/**
	 * 根据HQL条件，更新其相应信息
	 * 
	 * @param 查询HQL语句
	 * @param 更新HQL语句
	 * @return 返回更新后的信息集合
	 */
	public List updateByHql(String queryhql, String updatehql);

	/**
	 * 根据HQL条件删除信息
	 * 
	 * @param 删除HQL语句
	 * @return 返回删除结果(True-成功、False-失败)
	 */
	public boolean deleteByHql(String hql);
	
	/**
	 * 根据SQL语句和条件集合查询数据集
	 * 
	 * @param 查询SQL语句
	 * @param 查询条件集合
	 * @return 返回查询结果集合
	 */
	public List findBySQL(String sql, int page, int pageSize);
	
	/**
	 * 根据HQL语句查询单个信息对象
	 * 
	 * @param 查询HQL语句
	 * @return 返回单个信息对象
	 */
	public Object findBySql(String sql);
}