package user.web.servlet;

import admin.purse.domain.Purse;
import cart.domain.CartItem;
import cart.service.CartItemService;
import cn.itcast.commons.CommonUtils;
import cn.itcast.servlet.BaseServlet;
import user.domain.User;
import user.domain.myAddress;
import user.service.UserService;
import user.service.exception.UserException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/UserServlet")
public class UserServlet extends BaseServlet {
	private UserService userService = new UserService();
	private CartItemService cartItemService = new CartItemService();

	/**
	 * 设置默认地址
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String upstate(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		User user = (User)req.getSession().getAttribute("sessionUser");
		String uid = user.getUid();
		String addressId = req.getParameter("addressId");
		userService.upstate(addressId,uid);//修改数据库的记录
		return myaddress(req,resp);
	}

	/**
	 * 删除收货地址
	 *
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String delete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String addressId = req.getParameter("addressId");
		userService.delete(addressId);//修改数据库的记录
		return myaddress(req,resp);
	}

	/**
	 * 修改收货地址
	 *
	 * @param req
	 * @param resp
	 * @return
	 */
	public String edit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException{

		myAddress myAddress = CommonUtils.toBean(req.getParameterMap(), myAddress.class);
		userService.edit(myAddress);
		return myaddress(req,resp);
	}

	/**
	 * 按addressId查收货地址
	 *
	 * @param req
	 * @param resp
	 * @return
	 */
	public String load(HttpServletRequest req, HttpServletResponse resp) {

		String addressId = req.getParameter("addressId");
		myAddress myAddress = userService.findByaddressId(addressId);
		req.setAttribute("myAddress", myAddress);
		return "f:/jsps/user/myaddressDesc.jsp";
	}

	/*
	添加新收货地址
	 */
	public String addmyaddress(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		User user = (User)req.getSession().getAttribute("sessionUser");
		String uid = user.getUid();
		Map map = req.getParameterMap();
		myAddress myAddress = CommonUtils.toBean(map, myAddress.class);
		String state2 = req.getParameter("state2");
		myAddress.setUid(uid);
		userService.addmyaddress(myAddress,state2);
		int state = Integer.parseInt(req.getParameter("state"));
		if(state==1){
			String cartItemIds = req.getParameter("cartItemIds");
			List<CartItem> cartItemList = cartItemService.loadCartItems(cartItemIds);
			double total = Double.parseDouble(req.getParameter("total"));
			List<myAddress> myAddressList = userService.myaddress(uid);
			req.setAttribute("myAddressList", myAddressList);
			req.setAttribute("cartItemList", cartItemList);
			req.setAttribute("total", total);
			req.setAttribute("cartItemIds", cartItemIds);
			return "f:/jsps/cart/showitem.jsp";
		}

		return myaddress(req,resp);
	}

	/*
	按用户查询收货地址
	 */
	public String myaddress(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 得到uid
		 */
		User user = (User)req.getSession().getAttribute("sessionUser");
		String uid = user.getUid();
		List<myAddress> myAddressList = userService.myaddress(uid);
		req.setAttribute("myAddressList", myAddressList);
		return "f:/jsps/user/myaddress.jsp";
	}

	/*
	查询用户个人信息
	 */
	public String myinformation(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 得到uid
		 */
		User user1 = (User)req.getSession().getAttribute("sessionUser");
		String uid = user1.getUid();
		User user = userService.mypurse(uid);
		req.setAttribute("user", user);
		return "f:/jsps/user/myinformation.jsp";
	}

	/*
	按用户查询账号余额
	 */
	public String mypurse(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 得到uid
		 */
		User user1 = (User)req.getSession().getAttribute("sessionUser");
		String uid = user1.getUid();
		User user = userService.mypurse(uid);
		req.setAttribute("user", user);
		return "f:/jsps/user/mypurse.jsp";
	}
	/*
	用户充值余额申请
	 */
	public String rechange(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		User user = (User)req.getSession().getAttribute("sessionUser");
		Purse purse = CommonUtils.toBean(req.getParameterMap(), Purse.class);
		String uid = user.getUid();
		userService.rechange(purse,uid);
		return mypurse(req,resp);
	}

	/**
	 * ajax用户名是否注册校验
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String ajaxValidateLoginname(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 获取用户名
		 */
		String loginname = req.getParameter("loginname");
		/*
		 * 2. 通过service得到校验结果
		 */
		boolean b = userService.ajaxValidateLoginname(loginname);
		/*
		 * 3. 发给客户端
		 */
		resp.getWriter().print(b);
		return null;
	}
	
	/**
	 * ajax Email是否注册校验
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String ajaxValidateEmail(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 获取Email
		 */
		String email = req.getParameter("email");
		/*
		 * 2. 通过service得到校验结果
		 */
		boolean b = userService.ajaxValidateEmail(email);
		/*
		 * 3. 发给客户端
		 */
		resp.getWriter().print(b);
		return null;
	}
	
	/**
	 * ajax验证码是否正确校验
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String ajaxValidateVerifyCode(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 获取输入框中的验证码
		 */
		String verifyCode = req.getParameter("verifyCode");
		/*
		 * 2. 获取图片上真实的校验码
		 */
		String vcode = (String) req.getSession().getAttribute("vCode");
		/*
		 * 3. 进行忽略大小写比较，得到结果
		 */
		boolean b = verifyCode.equalsIgnoreCase(vcode);
		/*
		 * 4. 发送给客户端
		 */
		resp.getWriter().print(b);
		return null;
	}

	/**
	 * 注册功能
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String regist(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 封装表单数据到User对象
		 */
		User formUser = CommonUtils.toBean(req.getParameterMap(), User.class);
		/*
		 * 2. 校验之, 如果校验失败，保存错误信息，返回到regist.jsp显示
		 */
		Map<String,String> errors = validateRegist(formUser, req.getSession());
		if(errors.size() > 0) {
			req.setAttribute("form", formUser);
			req.setAttribute("errors", errors);
			return "f:/jsps/user/regist.jsp";
		}
		/*
		 * 3. 使用service完成业务
		 */
		userService.regist(formUser);
		/*
		 * 4. 保存成功信息，转发到msg.jsp显示！
		 */
		req.setAttribute("code", "success");
		req.setAttribute("msg", "注册功能，请马上到邮箱激活！");
		return "f:/jsps/msg.jsp";
	}
	
	/*
	 * 注册校验
	 * 对表单的字段进行逐个校验，如果有错误，使用当前字段名称为key，错误信息为value，保存到map中
	 * 返回map
	 */
	private Map<String,String> validateRegist(User formUser, HttpSession session) {
		Map<String,String> errors = new HashMap<String,String>();
		/*
		 * 1. 校验登录名
		 */
		String loginname = formUser.getLoginname();
		if(loginname == null || loginname.trim().isEmpty()) {
			errors.put("loginname", "用户名不能为空！");
		} else if(loginname.length() < 3 || loginname.length() > 20) {
			errors.put("loginname", "用户名长度必须在3~20之间！");
		} else if(!userService.ajaxValidateLoginname(loginname)) {
			errors.put("loginname", "用户名已被注册！");
		}
		
		/*
		 * 2. 校验登录密码
		 */
		String loginpass = formUser.getLoginpass();
		if(loginpass == null || loginpass.trim().isEmpty()) {
			errors.put("loginpass", "密码不能为空！");
		} else if(loginpass.length() < 3 || loginpass.length() > 20) {
			errors.put("loginpass", "密码长度必须在3~20之间！");
		}
		
		/*
		 * 3. 确认密码校验
		 */
		String reloginpass = formUser.getReloginpass();
		if(reloginpass == null || reloginpass.trim().isEmpty()) {
			errors.put("reloginpass", "确认密码不能为空！");
		} else if(!reloginpass.equals(loginpass)) {
			errors.put("reloginpass", "两次输入不一致！");
		}
		
		/*
		 * 4. 校验email
		 */
		String email = formUser.getEmail();
		if(email == null || email.trim().isEmpty()) {
			errors.put("email", "Email不能为空！");
		} else if(!email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
			errors.put("email", "Email格式错误！");
		} else if(!userService.ajaxValidateEmail(email)) {
			errors.put("email", "Email已被注册！");
		}
		
		/*
		 * 5. 验证码校验
		 */
		String verifyCode = formUser.getVerifyCode();
		String vcode = (String) session.getAttribute("vCode");
		if(verifyCode == null || verifyCode.trim().isEmpty()) {
			errors.put("verifyCode", "验证码不能为空！");
		} else if(!verifyCode.equalsIgnoreCase(vcode)) {
			errors.put("verifyCode", "验证码错误！");
		}
		
		return errors;
	}
	
	/**
	 * 激活功能
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String activation(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 获取参数激活码
		 * 2. 用激活码调用service方法完成激活
		 *   > service方法有可能抛出异常, 把异常信息拿来，保存到request中，转发到msg.jsp显示
		 * 3. 保存成功信息到request，转发到msg.jsp显示。
		 */
		String code = req.getParameter("activationCode");
		try {
			userService.activatioin(code);
			req.setAttribute("code", "success");//通知msg.jsp显示对号
			req.setAttribute("msg", "恭喜，激活成功，请马上登录！");
		} catch (UserException e) {
			// 说明service抛出了异常
			req.setAttribute("msg", e.getMessage());
			req.setAttribute("code", "error");//通知msg.jsp显示X
		}
		return "f:/jsps/msg.jsp";
	}
	
	/**
	 * 修改密码　
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String updatePassword(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 封装表单数据到user中
		 * 2. 从session中获取uid
		 * 3. 使用uid和表单中的oldPass和newPass来调用service方法
		 *   > 如果出现异常，保存异常信息到request中，转发到pwd.jsp
		 * 4. 保存成功信息到rquest中
		 * 5. 转发到msg.jsp
		 */
		User formUser = CommonUtils.toBean(req.getParameterMap(), User.class);
		User user = (User)req.getSession().getAttribute("sessionUser");
		// 如果用户没有登录，返回到登录页面，显示错误信息
		if(user == null) {
			req.setAttribute("msg", "您还没有登录！");
			return "f:/jsps/user/login.jsp"; 
		}
		/*
		 * 2. 校验
		 */
		Map<String,String> errors = validatePwd(formUser, req.getSession());
		if(errors.size() > 0) {
			req.setAttribute("user", formUser);
			req.setAttribute("errors", errors);
			return "f:/jsps/user/pwd.jsp";
		}
		
		try {
			userService.updatePassword(user.getUid(), formUser.getNewpass(), 
					formUser.getLoginpass());
			req.setAttribute("msg", "修改密码成功");
			req.setAttribute("code", "success");
			return "f:/jsps/msg.jsp";
		} catch (UserException e) {
			req.setAttribute("msg", e.getMessage());//保存异常信息到request
			req.setAttribute("user", formUser);//为了回显
			return "f:/jsps/user/pwd.jsp";
		}
	}
	
	private Map<String, String> validatePwd(User formUser, HttpSession session) {
		Map<String,String> errors = new HashMap<String,String>();
		/*
		 * 1. 密码长度
		 */
		String loginpass = formUser.getLoginpass();
		String newpass = formUser.getNewpass();
		String reloginpass = formUser.getReloginpass();
		if(loginpass == null || loginpass.trim().isEmpty()) {
			errors.put("loginpass", "密码不能为空！");
		} else if(loginpass.length() < 3 || loginpass.length() > 20) {
			errors.put("loginpass", "密码长度必须在3~20之间！");
		}
		if(newpass == null || newpass.trim().isEmpty()) {
			errors.put("newpass", "密码不能为空！");
		} else if(newpass.length() < 3 || newpass.length() > 20) {
			errors.put("newpass", "密码长度必须在3~20之间！");
		}
		if(reloginpass == null || reloginpass.trim().isEmpty()) {
			errors.put("reloginpass", "密码不能为空！");
		} else if(reloginpass.length() < 3 || reloginpass.length() > 20) {
			errors.put("reloginpass", "密码长度必须在3~20之间！");
		}
		/*
		 * 2. 确认密码校验
		 */
		if(!reloginpass.equals(newpass)) {
			errors.put("reloginpass", "两次输入不一致！");
		}
		
		
		return errors;
	}
	
	/*
	 * 发邮箱验证码
	 */
	
	public String sendcontent(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException{
		
		String email = req.getParameter("email");
		String content = userService.sendcontent(email);
		if(content!=null){
			HttpSession session = req.getSession();
			session.setAttribute("content", content);
			resp.getWriter().print(true);
		}
		return null;
	}
	/*
	 * 找回密码
	 */
	public String findPassword(HttpServletRequest req, HttpServletResponse resp)
	throws ServletException, IOException{
		
		User formUser = CommonUtils.toBean(req.getParameterMap(), User.class);
		
		Map<String,String> errors = findPwd(formUser, req.getSession());
		if(errors.size() > 0) {
			req.setAttribute("user", formUser);
			req.setAttribute("errors", errors);
			return "f:/jsps/user/findpwd.jsp";
		}
		
		boolean flag = userService.findpwd(formUser.getLoginname(),formUser.getReloginpass());
		if(flag){
			req.getSession().invalidate();
			req.setAttribute("msg", "修改密码成功");
			req.setAttribute("code", "success");
			return "f:/jsps/msg.jsp";
		}else {
			req.setAttribute("user", formUser);
			req.setAttribute("errors", errors);
			return "f:/jsps/user/findpwd.jsp";
		}
	}

	private Map<String, String> findPwd(User formUser, HttpSession session) {
		Map<String,String> errors = new HashMap<String,String>();
		
		String loginname = formUser.getLoginname();
		
		if(loginname == null || loginname.trim().isEmpty()) {
			errors.put("loginname", "用户名不能为空！");
		} else if(loginname.length() < 3 || loginname.length() > 20) {
			errors.put("loginname", "用户名长度必须在3~20之间！");
		}
		
		
		/*
		 * 2. 校验登录密码
		 */
		String loginpass = formUser.getLoginpass();
		if(loginpass == null || loginpass.trim().isEmpty()) {
			errors.put("loginpass", "密码不能为空！");
		} else if(loginpass.length() < 3 || loginpass.length() > 20) {
			errors.put("loginpass", "密码长度必须在3~20之间！");
		}
		
		/*
		 * 3. 确认密码校验
		 */
		String reloginpass = formUser.getReloginpass();
		if(reloginpass == null || reloginpass.trim().isEmpty()) {
			errors.put("reloginpass", "确认密码不能为空！");
		} else if(!reloginpass.equals(loginpass)) {
			errors.put("reloginpass", "两次输入不一致！");
		}
		
		/*
		 * 4. 校验email
		 */
		String email = formUser.getEmail();
		if(email == null || email.trim().isEmpty()) {
			errors.put("email", "Email不能为空！");
		} else if(!email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
			errors.put("email", "Email格式错误！");
		} else if(userService.ajaxValidateEmail(email)) {
			errors.put("email", "Email不存在！");
		}
		
		/*
		 * 5. 验证码校验
		 */
		String verifyCode = formUser.getVerifyCode();
		String vcode = (String) session.getAttribute("content");
		if(verifyCode == null || verifyCode.trim().isEmpty()) {
			errors.put("verifyCode", "验证码不能为空！");
		} else if(!verifyCode.equalsIgnoreCase(vcode)) {
			errors.put("verifyCode", "验证码错误！");
		}
		return errors;
	}
	
	/**
	 * 退出功能
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String quit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
//		req.getSession().invalidate();
		req.getSession().removeAttribute("sessionUser");
		return "r:/jsps/user/login.jsp";
	}
	
	/**
	 * 登录功能
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String login(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		User formUser = CommonUtils.toBean(req.getParameterMap(), User.class);
		/*
		 * 2. 校验
		 */
		Map<String,String> errors = validateLogin(formUser, req.getSession());
		if(errors.size() > 0) {
			req.setAttribute("form", formUser);
			req.setAttribute("errors", errors);
			return "f:/jsps/user/login.jsp";
		}
		
		/*
		 * 3. 调用userService#login()方法
		 */
		User user = userService.login(formUser);
		/*
		 * 4. 开始判断
		 */
		if(user == null) {
			req.setAttribute("msg", "用户名或密码错误！");
			req.setAttribute("form", formUser);
			return "f:/jsps/user/login.jsp";
		} else {
			if(!user.isStatus()) {
				req.setAttribute("msg", "该账号还没有激活！");
				req.setAttribute("form", formUser);
				return "f:/jsps/user/login.jsp";				
			} else if(!user.isState()){
				req.setAttribute("msg", "该账号已被冻结！");
				req.setAttribute("form", formUser);
				return "f:/jsps/user/login.jsp";
			}
			else {
				// 保存用户到session
				req.getSession().setAttribute("sessionUser", user);
				// 获取用户名保存到cookie中
				String loginname = user.getLoginname();
				loginname = URLEncoder.encode(loginname, "utf-8");
				Cookie cookie = new Cookie("loginname", loginname);
				cookie.setMaxAge(60 * 60);//保存1个小时
				resp.addCookie(cookie);
				return "r:/index.jsp";//重定向到主页
			}
		}
	}
	
	/*
	 * 登录校验方法，内容等你自己来完成
	 */
	private Map<String,String> validateLogin(User formUser, HttpSession session) {
		Map<String,String> errors = new HashMap<String,String>();
		/*
		 * 1. 校验登录名
		 */
		String loginname = formUser.getLoginname();
		if(loginname == null || loginname.trim().isEmpty()) {
			errors.put("loginname", "用户名不能为空！");
		} else if(loginname.length() < 3 || loginname.length() > 20) {
			errors.put("loginname", "用户名长度必须在3~20之间！");
		}
		
		/*
		 * 2. 校验登录密码
		 */
		String loginpass = formUser.getLoginpass();
		if(loginpass == null || loginpass.trim().isEmpty()) {
			errors.put("loginpass", "密码不能为空！");
		} else if(loginpass.length() < 3 || loginpass.length() > 20) {
			errors.put("loginpass", "密码长度必须在3~20之间！");
		}
		
		/*
		 * 5. 验证码校验
		 */
		String verifyCode = formUser.getVerifyCode();
		String vcode = (String) session.getAttribute("vCode");
		if(verifyCode == null || verifyCode.trim().isEmpty()) {
			errors.put("verifyCode", "验证码不能为空！");
		} else if(!verifyCode.equalsIgnoreCase(vcode)) {
			errors.put("verifyCode", "验证码错误！");
		}
		return errors;
	}
}
