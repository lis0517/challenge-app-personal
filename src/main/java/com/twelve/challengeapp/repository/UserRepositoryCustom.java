package com.twelve.challengeapp.repository;

import com.twelve.challengeapp.dto.UserRequestDto;
import com.twelve.challengeapp.dto.UserResponseDto;

public interface UserRepositoryCustom {
	UserResponseDto getUserProfile(Long userId);
}
