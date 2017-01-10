package com.ncjk.utcs.common.dao;

import java.util.List;
import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.stereotype.Component;

import com.ncjk.utcs.common.util.PrintLog;

@Component("commonDAO")
public class CommonDAO implements ICommonDAO {
	private static final long serialVersionUID = 1L;
//	private static final String Throwable = null;
	private HibernateTemplate hibernateTemplate;
	//private static Log logger = LogFactory.getLog(CommonDAO.class);

	/**
	 * 根据HQL语句查询单个信息对象
	 * 
	 * @param 查询HQL语句
	 * @return 返回单个信息对象
	 */
	public Object findByHql(String hql) {
		try {
			Query query = getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createQuery(hql);
			List<Object> ob = query.list();
			if (ob != null && !ob.isEmpty()) {
				return ob.get(0);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			PrintLog.printErrorLog(getClass(), e);
//			Throwable th = e.getCause();
//			String cause = " ";
//			while(th !=null){
//				cause = th.toString();
//				th = e.getCause();
//			}
//			
//			cause = new Date()+ cause; 
//			logger.error(cause);
		}
		return null;
	}

	/**
	 * 根据HQL语句和条件集合查询数据集
	 * 
	 * @param 查询HQL语句
	 * @param 查询条件集合
	 * @return 返回查询结果集合
	 */
	public List<Object[]> findByHql(String hql, List<Object> obj) {
		return this.getHibernateTemplate().find(hql, obj.toArray());
	}

	/**
	 * 根据HQL语句查询分页数据集
	 * 
	 * @param 查询HQL语句
	 * @param 当前页码数
	 * @param 每页显示条数
	 * @return 返回查询结果集合
	 */
	public List findByHql(String hql, int page, int pageSize) {
		int rsStart = (page - 1) * pageSize;
		int rsEnd = pageSize;
		try {
			Query query = getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createQuery(hql);
			if (rsEnd != 0) {
				query.setFirstResult(rsStart);
				query.setMaxResults(rsEnd);
			}
			return query.list();
		} catch (Exception e) {
			e.printStackTrace();
			PrintLog.printErrorLog(getClass(), e);
//			Throwable th = e.getCause();
//			String cause = " ";
//			while(th !=null){
//				cause = th.toString();
//				th = e.getCause();
//			}
//			
//			cause = new Date()+ cause; 
//			logger.error(cause);
		}
		return null;
	}

	/**
	 * 根据HQL语句及条件查询分页数据集
	 * 
	 * @param 查询HQL语句
	 * @param 查询条件集合
	 * @param 当前页码数
	 * @param 每页显示条数
	 * @return 返回查询结果集合
	 */
	public List<Object[]> findByHql(String hql, List<Object> para, int page,
			int pageSize) {
		int i = 0;
		int rsStart = (page - 1) * pageSize;
		int rsEnd = pageSize;
		Query query = getHibernateTemplate().getSessionFactory()
				.getCurrentSession().createQuery(hql);
		for (Object o : para) {
			query.setParameter(i, o);
			i++;
		}
		query.setFirstResult(rsStart);
		query.setMaxResults(rsEnd);
		return query.list();
	}

	/**
	 * 根据ID值查询信息对象
	 * 
	 * @param 类型
	 * @param 对象ID值
	 * @return 返回信息对象结果
	 */
	public Object findObjectById(Class<?> classz, Integer id) {
		return this.getHibernateTemplate().get(classz, id);
	}

	/**
	 * 根据HQL语句更新信息
	 * 
	 * @param 查询HQL语句
	 * @return 返回更新结果(True-成功、False-失败)
	 */
	public boolean updateByHql(String hql) {
		boolean b = false;
		try {
			Session session = getHibernateTemplate().getSessionFactory()
					.getCurrentSession();
			Query query = session.createQuery(hql);
			query.executeUpdate();
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
			PrintLog.printErrorLog(getClass(), e);
//			Throwable th = e.getCause();
//			String cause = " ";
//			while(th !=null){
//				cause = th.toString();
//				th = e.getCause();
//			}
//			
//			cause = new Date()+ cause; 
//			logger.error(cause);
		}
		return b;
	}

	/**
	 * 根据信息对象更新信息
	 * 
	 * @param 信息对象
	 * @return 返回更新结果(True-成功、False-失败)
	 */
	public boolean saveOrUpdate(Object obj) {
		boolean b = false;
		try {
			getHibernateTemplate().saveOrUpdate(obj);
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
			PrintLog.printErrorLog(getClass(), e);
//			Throwable th = e.getCause();
//			String cause = " ";
//			while(th !=null){
//				cause = th.toString();
//				th = e.getCause();
//			}
//			
//			cause = new Date()+ cause; 
//			logger.error(cause);
		}
		return b;
	}

	/**
	 * 根据HQL条件，更新其相应信息
	 * 
	 * @param 查询HQL语句
	 * @param 更新HQL语句
	 * @return 返回更新后的信息集合
	 */
	public List updateByHql(String queryhql, String updatehql) {
		Query query = null;
		List list = null;
		try {
			Session session = getHibernateTemplate().getSessionFactory()
					.getCurrentSession();
			if (queryhql != null) {
				query = session.createQuery(queryhql);
				list = query.list();
			}
			query = session.createQuery(updatehql);
			query.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			PrintLog.printErrorLog(getClass(), e);
//			Throwable th = e.getCause();
//			String cause = " ";
//			while(th !=null){
//				cause = th.toString();
//				th = e.getCause();
//			}
//			
//			cause = new Date()+ cause; 
//			logger.error(cause);
		}
		return list;
	}

	/**
	 * 根据HQL条件删除信息
	 * 
	 * @param 删除HQL语句
	 * @return 返回删除结果(True-成功、False-失败)
	 */
	public boolean deleteByHql(String hql) {
		boolean b = false;
		try {
			Query query = getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createQuery(hql);
			query.executeUpdate();
			b = true;
		} catch (Exception e) {
			e.printStackTrace();
			PrintLog.printErrorLog(getClass(), e);
//			Throwable th = e.getCause();
//			String cause = " ";
//			while(th !=null){
//				cause = th.toString();
//				th = e.getCause();
//			}
//			
//			cause = new Date()+ cause; 
//			logger.error(cause);
		}
		return b;
	}
	
	/**
	 * 根据SQL语句和条件集合查询数据集
	 * 
	 * @param 查询SQL语句
	 * @param 查询条件集合
	 * @return 返回查询结果集合
	 */
	public List findBySQL(String sql, int page, int pageSize) {
		
		int rsStart = (page - 1) * pageSize;
		int rsEnd = pageSize;
		try {
			Query query = getHibernateTemplate().getSessionFactory()
					.getCurrentSession().createSQLQuery(sql);
			if (rsEnd != 0) {
				query.setFirstResult(rsStart);
				query.setMaxResults(rsEnd);
			}
			return query.list();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			PrintLog.printErrorLog(getClass(), e);
//			Throwable th = e.getCause();
//			String cause = " ";
//			while(th !=null){
//				cause = th.toString();
//				th = e.getCause();
//			}
//			
//			cause = new Date()+ cause; 
//			logger.error(cause);
		}
		return null;
	}
	
	/**
	 * 根据HQL语句查询单个信息对象
	 * 
	 * @param 查询HQL语句
	 * @return 返回单个信息对象
	 */
	public Object findBySql(String sql) {
		try {
			Query query = getHibernateTemplate().getSessionFactory()
			.getCurrentSession().createSQLQuery(sql);
			List<Object> ob = query.list();
			if (ob != null && !ob.isEmpty()) {
				return ob.get(0);
			}
			return null;
		} catch (Exception e) {
			e.printStackTrace();
			PrintLog.printErrorLog(getClass(), e);
//			Throwable th = e.getCause();
//			String cause = " ";
//			while(th !=null){
//				cause = th.toString();
//				th = e.getCause();
//			}
//			
//			cause = new Date()+ cause; 
//			logger.error(cause);
		}
		return null;
	}

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	@Resource
	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	
	
}

 