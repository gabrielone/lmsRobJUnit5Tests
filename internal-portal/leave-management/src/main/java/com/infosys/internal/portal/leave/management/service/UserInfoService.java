package com.infosys.internal.portal.leave.management.service;

import com.infosys.internal.portal.common.dto.UserInfoDto;
import com.infosys.internal.portal.common.model.UserInfo;

import java.util.List;


public interface UserInfoService {

	UserInfoDto getUserInfo();

	UserInfoDto findUserByEmail(String email);

	void saveUser(UserInfoDto userInfo);

	List<UserInfoDto> getUsers();

	UserInfoDto getUserById(int id);

	void deleteUser(int id);

	void blockUser(int id);

	void unBlockUser(int id);

	UserInfoDto convertToDto(UserInfo details);

	UserInfo convertDtoToEntity(UserInfoDto details);
}
