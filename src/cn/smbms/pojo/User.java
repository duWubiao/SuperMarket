package cn.smbms.pojo;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.format.annotation.DateTimeFormat;

import com.alibaba.fastjson.annotation.JSONField;


/**
 * @author Du
 *
 */
public class User {

	private int id;		//id
	//使用JSR 303数据验证框架,服务器端的数据验证（实现者是Hibernate）
	@NotEmpty(message="用户编码不能为空!")
	private String userCode;	//用户编码
	@NotEmpty(message="用户名不能为空!")
	private String userName;	//用户名称
	@NotNull(message="用户密码不能为空!")
	@Length(min=6,max=10,message="用户密码的长度为6-10")
	private String userPassword;	//用户密码
	private int gender;		//性别
	@Past(message="必须是一个过去的时间")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format="yyyy-MM-dd")	//JSON解决日期格式编码
	private Date birthday;	//出生日期
	private String phone;	//电话
	private String address;		//地址
	private int userRole;	//角色
	private int createdBy;	//创建者

	private Date creationDate;	//创建时间
	private int modifyBy;	//更新者
	private Date modifyDate;	//更新时间
	
	private int age;	//年龄
	
	private String userRoleName;	//用户角色名称
	
	private String idPicPath;	//证件照路径
	
	public String getIdPicPath() {
		return idPicPath;
	}

	public void setIdPicPath(String idPicPath) {
		this.idPicPath = idPicPath;
	}
	
	public String workPicPath;	//工作证照片
	
	public String getWorkPicPath() {
		return workPicPath;
	}

	public void setWorkPicPath(String workPicPath) {
		this.workPicPath = workPicPath;
	}

	public User(){}
	
	public User(Integer id,String userCode,String userName,String userPassword,Integer gender,Date birthday,String phone,
			String address,Integer userRole,Integer createdBy,Date creationDate,Integer modifyBy,Date modifyDate,String userRoleName){
		this.id = id;
		this.userCode = userCode;
		this.userName = userName;
		this.userPassword = userPassword;
		this.gender = gender;
		this.birthday = birthday;
		this.phone = phone;
		this.address = address;
		this.userRole = userRole;
		this.createdBy = createdBy;
		this.creationDate = creationDate;
		this.modifyBy = modifyBy;
		this.modifyDate = modifyDate;
		this.userRoleName = userRoleName;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPassword() {
		return userPassword;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}


	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getUserRole() {
		return userRole;
	}

	public void setUserRole(int userRole) {
		this.userRole = userRole;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	public int getModifyBy() {
		return modifyBy;
	}

	public void setModifyBy(int modifyBy) {
		this.modifyBy = modifyBy;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	/**
	 * @return
	 */
	public int getAge() {
		Date date = new Date();
		if(birthday != null){
			age = date.getYear()-birthday.getYear();
		}	
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}


	public String getUserRoleName() {
		return userRoleName;
	}

	public void setUserRoleName(String userRoleName) {
		this.userRoleName = userRoleName;
	}
	
}
