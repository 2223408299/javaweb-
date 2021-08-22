package admin.purse.service;

import admin.purse.dao.PurseDao;
import admin.purse.domain.Purse;
import pager.PageBean;

import java.sql.SQLException;

public class PurseService {
    private PurseDao purseDao = new PurseDao();

    /**
     * 查询指定药品在订单中的个数
     * @param drugId
     * @return
     */
    public boolean findByUid(String drugId) {
        try {
            int cnt = purseDao.findByUid(drugId);
            if(cnt>0){
                return true;
            }
            return false;
        } catch(SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 批量删除已经处理的充值申请
     * @param purseIds
     */
    public void batchDelete(String purseIds) {
        try {
            purseDao.batchDelete(purseIds);
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }

    /**
     * 批量处理申请
     * @param purseIds
     */
    public void batchCheck(String purseIds) {
        Object[] purseIdArray = purseIds.split(",");
        for(int i = 0;i<purseIdArray.length;i++){
            try {
                purseDao.updateState((String) purseIdArray[i]);
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
//        try {
//            purseDao.batchCheck(purseIds);
//        }catch (SQLException e){
//            throw new RuntimeException();
//        }
    }

    /**
     * 按用户ID查询
     * @param pc
     * @return
     */
    public PageBean<Purse> findPurseByUid(int pc, String condition,String uid) {
        try {
            return purseDao.findPurseByUid(pc,condition,uid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 查找所有钱包充值管理
     * @param pc
     * @return
     */
    public PageBean<Purse> findAllUser(int pc, String condition) {
        try {
            return purseDao.findAllPurse(pc,condition);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 管理充值申请
     * @param purseId
     */
    public void updateState(String purseId) {
        try {
            purseDao.updateState(purseId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /**
     * 删除充值申请
     * @param purseId
     */
    public void delete(String purseId) {
        try {
            purseDao.delete(purseId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
