//package com.infosys.internal.portal.common.model;
//
//import java.util.List;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.ManyToMany;
//import javax.persistence.Table;
//
//import org.hibernate.validator.constraints.NotEmpty;


//@Entity
//@Table(name="roles_de_sters")
//public class Role
//{
//	@Id @GeneratedValue(strategy=GenerationType.AUTO)
//	private Integer id;
//	@Column(nullable=false, unique=true)
//	@NotEmpty
//	private String name;
//		
//	@ManyToMany(mappedBy="roles_new")
//	private List<UserInfo> userInfo;
//
//	public Integer getId()
//	{
//		return id;
//	}
//
//	public void setId(Integer id)
//	{
//		this.id = id;
//	}
//
//	public String getName()
//	{
//		return name;
//	}
//
//	public void setName(String name)
//	{
//		this.name = name;
//	}
//
//	public List<UserInfo> getUserInfo() {
//		return userInfo;
//	}
//
//	public void setUserInfo(List<UserInfo> userInfo) {
//		this.userInfo = userInfo;
//	}
//
//	
//	
//}
