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
	//ʹ��JSR 303������֤���,�������˵�������֤��ʵ������Hibernate��
	@NotEmpty(message="�û����벻��Ϊ��!")
	private String userCode;	//�û�����
	@NotEmpty(message="�û�������Ϊ��!")
	private String userName;	//�û�����
	@NotNull(message="�û����벻��Ϊ��!")
	@Length(min=6,max=10,message="�û�����ĳ���Ϊ6-10")
	private String userPassword;	//�û�����
	private int gender;		//�Ա�
	@Past(message="������һ����ȥ��ʱ��")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	@JSONField(format="yyyy-MM-dd")	//JSON������ڸ�ʽ����
	private Date birthday;	//��������
	private String phone;	//�绰
	private String address;		//��ַ
	private int userRole;	//��ɫ
	private int createdBy;	//������

	private Date creationDate;	//����ʱ��
	private int modifyBy;	//������
	private Date modifyDate;	//����ʱ��
	
	private int age;	//����
	
	private String userRoleName;	//�û���ɫ����
	
	private String idPicPath;	//֤����·��
	
	public String getIdPicPath() {
		return idPicPath;
	}

	public void setIdPicPath(String idPicPath) {
		this.idPicPath = idPicPath;
	}
	
	public String workPicPath;	//����֤��Ƭ
	
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
