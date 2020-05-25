package com.infosys.internal.portal.common.repository;

import java.io.Serializable;
import java.util.List;

import com.infosys.internal.portal.common.model.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository(value = "userInfoRepository")
public interface UserInfoRepository extends JpaRepository<UserInfo, Serializable> {

	@Query(value = "select * from userinfo u where u.email = :email", nativeQuery = true)
	public UserInfo findByEmail(@Param("email") String email);

	public List<UserInfo> findAllByOrderById();

	public UserInfo findById(int id);

	@Transactional
	@Modifying
	@Query(value = "update userinfo set active=false where id=?", nativeQuery = true)
	public void blockUser(int id);

	@Transactional
	@Modifying
	@Query(value = "update userinfo set active=true where id=?", nativeQuery = true)
	public void unBlockUser(int id);
}
