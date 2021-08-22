package drug.service;

import drug.dao.DrugDao;
import drug.domain.Drug;
import pager.PageBean;

import java.sql.SQLException;
import java.util.List;

public class DrugService {
	private DrugDao drugDao = new DrugDao();

	/**
	 * 按销量查询热门商品
	 * @return
	 */
	public List<Drug> findHotDrug2() {
		try {
			return drugDao.findHotDrug2();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 按销量查询热门商品
	 * @return
	 */
	public List<Drug> findHotDrug() {
		try {
			return drugDao.findHotDrug();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 批量上下架功能
	 * @param drugIds
	 */
	public void batchOpdrug(String drugIds,int state) {
		try {
			drugDao.batchOpdrug(drugIds,state);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 批量删除功能
	 * @param drugIds
	 */
	public void batchDelete(String drugIds) {
		try {
			drugDao.batchDelete(drugIds);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 加载药品
	 * @param drugid
	 * @return
	 */
	public Drug load(String drugid) {
		try {
			return drugDao.findByDrugid(drugid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 按分类查
	 * @param cid
	 * @param pc
	 * @return
	 */
	public PageBean<Drug> findByCategory(String cid, int pc,String condition3) {
		try {
			return drugDao.findByCategory(cid, pc,condition3);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * 按药名模糊查询
	 * @param drugName
	 * @param pc
	 * @return
	 */
	public PageBean<Drug> findBydrugName(String drugName, int pc,String condition3) {
		try {
			return drugDao.findBydrugName(drugName, pc,condition3);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 降序
	 * @param condition
	 * @param pc
	 * @return
	 */
	public PageBean<Drug> findBydrugDesc(String condition, String condition2,int pc, String condition3) {
		try {
			return drugDao.findBydrugDesc(condition,condition2, pc,condition3);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 升序
	 * @param condition
	 * @param pc
	 * @return
	 */
	public PageBean<Drug> findByAsc(String condition, String condition2,int pc, String condition3) {
		try {
			return drugDao.findByAsc(condition,condition2, pc,condition3);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 多条件组合查询
	 * @param criteria
	 * @param pc
	 * @return
	 */
	public PageBean<Drug> findByCombination(Drug criteria, int pc,String condition, String condition3) {
		try {
			return drugDao.findByCombination(criteria, pc,condition,condition3);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 查找所有药品
	 * @param pc
	 * @return
	 */
	public PageBean<Drug> findAllDrug(int pc,String condition3) {
		try {
			return drugDao.findAllDrug(pc,condition3);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 删除药品
	 * @param drugId
	 */
	public void delete(String drugId) {
		try {
			drugDao.delete(drugId);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 上下架药品
	 */
	public void updateState(String drugId ,String condition3) {
		try {
			drugDao.updateState(drugId,condition3);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 修改药品
	 * @param drug
	 */
	public void edit(Drug drug) {
		try {
			drugDao.edit(drug);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 返回当前分类下药品个数
	 * @param cid
	 * @return
	 */
	public int findBookCountByCategory(String cid) {
		try {
			return drugDao.findDrugCountByCategory(cid);
		} catch(SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 添加药品
	 * @param drug
	 */
	public void add(Drug drug) {
		try {
			drugDao.add(drug);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * 更换药品图片
	 * @param drug
	 */
	public void upPicture(Drug drug) {
		try {
			drugDao.upPicture(drug);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
