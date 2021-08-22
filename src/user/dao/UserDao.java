package user.dao;

import admin.purse.domain.Purse;
import cn.itcast.commons.CommonUtils;
import cn.itcast.jdbc.TxQueryRunner;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import user.domain.myAddress;
import user.domain.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class UserDao {
    private QueryRunner qr = new TxQueryRunner();

    /**
     * 按uid和password查询
     *
     * @param uid
     * @param password
     * @return
     * @throws SQLException
     */
    public boolean findByUidAndPassword(String uid, String password) throws SQLException {
        String sql = "select count(*) from t_user where uid=? and loginpass=?";
        Number number = (Number) qr.query(sql, new ScalarHandler(), uid, password);
        return number.intValue() > 0;
    }

    /**
     * 修改密码
     *
     * @param uid
     * @param password
     * @throws SQLException
     */
    public void updatePassword(String uid, String password) throws SQLException {
        String sql = "update t_user set loginpass=? where uid=?";
        qr.update(sql, password, uid);
    }

    public void findPassword(String loginname, String pwd) throws SQLException{
        String sql = "update t_user set loginpass=? where loginname=?";
        qr.update(sql, pwd, loginname);
    }

    /**
     * 按用户名和密码查询
     *
     * @param loginname
     * @param loginpass
     * @return
     * @throws SQLException
     */
    public User findByLoginnameAndLoginpass(String loginname, String loginpass) throws SQLException {
        String sql = "select * from t_user where loginname=? and loginpass=?";
        return qr.query(sql, new BeanHandler<User>(User.class), loginname, loginpass);
    }

    /**
     * 通过激活码查询用户
     *
     * @param code
     * @return
     * @throws SQLException
     */
    public User findByCode(String code) throws SQLException {
        String sql = "select * from t_user where activationCode=?";
        return qr.query(sql, new BeanHandler<User>(User.class), code);
    }

    /**
     * 修改用户状态
     *
     * @param uid
     * @param status
     * @throws SQLException
     */
    public void updateStatus(String uid, boolean status) throws SQLException {
        String sql = "update t_user set status=? where uid=?";
        qr.update(sql, status, uid);
    }

    /**
     * 校验用户名是否注册
     *
     * @param loginname
     * @return
     * @throws SQLException
     */
    public boolean ajaxValidateLoginname(String loginname) throws SQLException {
        String sql = "select count(1) from t_user where loginname=?";
        Number number = (Number) qr.query(sql, new ScalarHandler(), loginname);
        return number.intValue() == 0;
    }

    /**
     * 校验Email是否注册
     *
     * @param email
     * @return
     * @throws SQLException
     */
    public boolean ajaxValidateEmail(String email) throws SQLException {
        String sql = "select count(1) from t_user where email=?";
        Number number = (Number) qr.query(sql, new ScalarHandler(), email);
        return number.intValue() == 0;
    }

    /**
     * 添加用户
     *
     * @param user
     * @throws SQLException
     */
    public void add(User user) throws SQLException {
        String sql = "insert into t_user(uid,loginname,loginpass,email,balance,status,state,activationCode) values(?,?,?,?,?,?,?,?)";
        Object[] params = {user.getUid(), user.getLoginname(), user.getLoginpass(),
                user.getEmail(),user.getBalance(), user.isStatus(), user.isState(), user.getActivationCode()};
        qr.update(sql, params);
    }

    /**
     * 按用户查询账号余额
     * @param uid
     * @throws SQLException
     */
    public User mypurse(String uid) throws SQLException {
        String sql = "select * from t_user where uid = ?";
        Map<String, Object> map = qr.query(sql, new MapHandler(), uid);
        User mypurse = CommonUtils.toBean(map, User.class);
        return mypurse;
    }

    /**
     * 余额充值申请
     * @param purse
     * @throws SQLException
     */
    public void rechange(Purse purse) throws SQLException {
        String sql = "insert into t_purse(purseId,uid,rechange,state) values(?,?,?,?)";
        Object[] params = {purse.getPurseId(),purse.getUid(),purse.getRechange(),purse.isState()};
        qr.update(sql, params);
    }

    /**
     * 修改账号余额
     * @param uid
     * @param subtotal
     * @throws SQLException
     */
    public void payment(String uid,double subtotal) throws SQLException {
        String sql = "update t_user set balance=balance-? where uid = ?";
        qr.update(sql, subtotal,uid);
    }

    /**
     * 按用户查询收货地址
     * @param uid
     * @throws SQLException
     */
    public List<myAddress> myaddress(String uid) throws SQLException {
        String sql = "select * from t_address where uid = ? ORDER BY state DESC";
//        List<Map<String,Object>> mapList = qr.query(sql, new MapListHandler(), uid);
        List<myAddress> myAddressList = qr.query(sql, new BeanListHandler<myAddress>(myAddress.class),uid);

        return myAddressList;
    }

    /**
     * 添加新收货地址
     * @param myAddress
     */
    public void addmyaddress(myAddress myAddress) throws SQLException{
        String sql = "insert into t_address(addressId,address,phone,consignee,state,uid) values(?,?,?,?,?,?)";
        Object[] params = {myAddress.getAddressId(), myAddress.getAddress(), myAddress.getPhone(),
                myAddress.getConsignee(),myAddress.getState(), myAddress.getUid()};
        qr.update(sql, params);
    }

    /**
     * 按addressId查询
     *
     * @param addressId
     * @return
     * @throws SQLException
     */
    public myAddress findByaddressId(String addressId) throws SQLException {
        String sql = "SELECT * FROM t_address  WHERE addressId = ? ";
        Map<String, Object> map = qr.query(sql, new MapHandler(), addressId);
        myAddress myAddress = CommonUtils.toBean(map, myAddress.class);
//        Address address = (Address) qr.query(sql, new BeanListHandler<Address>(Address.class),addressId);
        return myAddress;
    }

    /**
     * 修改收货地址
     *
     * @param myAddress
     * @throws SQLException
     */
    public void edit(myAddress myAddress) throws SQLException {
        String sql = "update t_address set address=?,phone=?,consignee=? where addressId = ?";
        Object[] params = {myAddress.getAddress(), myAddress.getPhone(), myAddress.getConsignee(), myAddress.getAddressId()};
        qr.update(sql, params);
    }

    /**
     * 删除收货地址
     *
     * @param addressId
     * @throws SQLException
     */
    public void delete(String addressId) throws SQLException {
        String sql = "delete from t_address where addressId=?";
        qr.update(sql, addressId);
    }

    /**
     * 设置默认地址
     *
     * @param addressId
     * @throws SQLException
     */
    public void upstate(String addressId,String uid) throws SQLException {
        String sql="update t_address set state=0 where state = 1 and uid=?";
        qr.update(sql, uid);
        sql = "update t_address set state=1 where addressId = ?";
        qr.update(sql, addressId);
    }
}
