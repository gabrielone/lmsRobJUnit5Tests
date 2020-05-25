package com.infosys.internal.portal.claims.service;

import java.util.List;
import java.util.stream.Collectors;

import com.infosys.internal.portal.common.dto.UserInfoDto;
import com.infosys.internal.portal.common.model.UserInfo;
import com.infosys.internal.portal.common.repository.UserInfoRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service(value = "claimsUserService")
public class ClaimsUserIServiceImpl implements ClaimsUserService {

	@Autowired
	private UserInfoRepository userInfoRepository;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public UserInfoDto getUserInfo() {

		return this.findUserByEmail(SecurityContextHolder.getContext().getAuthentication().getName());

	}

	public UserInfoDto findUserByEmail(String email) {
		return this.convertToDto(userInfoRepository.findByEmail(email));
	}

	public void saveUser(UserInfoDto userInfo) {
		userInfo.setPassword(bCryptPasswordEncoder.encode(userInfo.getPassword()));
		userInfo.setActive(true);// TO DO: uncomment this
		userInfoRepository.save(this.convertDtoToEntity(userInfo));

	}

	public List<UserInfoDto> getUsers() {

		return userInfoRepository.findAllByOrderById().stream().map(user -> this.convertToDto(user))
				.collect(Collectors.toList());
	}

	public UserInfoDto getUserById(int id) {

		return this.convertToDto(userInfoRepository.findById(id));
	}

	public void deleteUser(int id) {
		userInfoRepository.deleteById(id);
	}

	public void blockUser(int id) {

		userInfoRepository.blockUser(id);

	}

	public void unBlockUser(int id) {

		userInfoRepository.unBlockUser(id);
	}

	@Override
	public UserInfoDto convertToDto(UserInfo details) {

		return new ModelMapper().map(details, UserInfoDto.class);
	}

	@Override
	public UserInfo convertDtoToEntity(UserInfoDto details) {

		return new ModelMapper().map(details, UserInfo.class);
	}

}
