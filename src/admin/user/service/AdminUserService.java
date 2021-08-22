package admin.user.service;

import admin.user.dao.AdminUserDao;
import cn.itcast.commons.CommonUtils;
import pager.PageBean;
import user.domain.User;

import java.sql.SQLException;

public class AdminUserService {
    private AdminUserDao userDao =  new AdminUserDao();

    /**
     * 查找所有用户
     * @param pc
     * @return
     */
    public PageBean<User> findAllUser(int pc,String condition) {
        try {
            return userDao.findAllUser(pc,condition);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * 按用户名称模糊查找
     * @param pc,condition,username
     * @return
     */
    public PageBean<User> findByUsername(int pc, String condition, String loginname) {
        try {
            return userDao.findByUsername(pc,condition,loginname);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 添加新用户
     * @param user
     */
    public void addUser(User user) {
        /*
         * 1. 数据的补齐
         */
        user.setUid(CommonUtils.uuid());
        user.setActivationCode(CommonUtils.uuid() + CommonUtils.uuid());
        user.setBalance(0.00);
        try {
            userDao.addUser(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 封号与解封用户
     * @param uid
     * @param condition
     */
    public void updateState(String uid, String condition) {
        try {
            userDao.updateState(uid,condition);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 删除用户
     * @param uid
     */
    public void delete(String uid) {
        try {
            userDao.delete(uid);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 按uid查看用户
     * @param uid
     * @return
     */
    public User findByUid(String uid) {
        try {
            return userDao.findByUid(uid);
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }

    /**
     * 修改用户
     * @param user
     */
    public void edit(User user) {
        try {
            userDao.edit(user);
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }

    /**
     * 批量管理用户
     * @param userIds
     * @param condition
     */
    public void batchLock(String userIds, String condition) {
        int state = 1;
        if("lock".equals(condition)){
            state = 0;
        }
        try {
            userDao.batchLock(userIds,state);
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }

    /**
     * 批量删除用户
     * @param userIds
     */
    public void batchDelete(String userIds) {
        try {
            userDao.batchDelete(userIds);
        }catch (SQLException e){
            throw new RuntimeException();
        }
    }
}
