package com.thunder.model.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.thunder.model.dao.UserDao;
import com.thunder.model.dto.User;

@Service
public class UserServiceImpl implements UserService {

	private final UserDao userDao;
	private final ResourceLoader resourceLoader;
	private static final String DEFALT_USER_IMAGE = "default_user.jpg";
	
	@Autowired
	public UserServiceImpl(UserDao userDao, ResourceLoader resourceLoader) {
		this.userDao = userDao;
		this.resourceLoader = resourceLoader;
	}
	

	// resource 경로를 가져오기 위해 ResourceLoader를 주입 받는다.
	@Autowired
	ResourceLoader resLoader;

	// 회원가입
	@Override
	public int registUser(User user, MultipartFile file){
		// 업로드한 파일 있으면
		if (file != null && file.getSize() > 0) {
			try {
				// 원본 파일명 저장
				user.setOrgImage(file.getOriginalFilename());
				// 중복 방지 파일명 저장
				user.setImage( UUID.randomUUID().toString() + "_" + file.getOriginalFilename());
				
				Resource resource = resourceLoader.getResource("classpath:/static/img");
				file.transferTo(new File(resource.getFile(), user.getImage()));
				
			} catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
		}
		
		// 업로드한 파일 없으면 기본 이미지 저장
		else {
			user.setImage(DEFALT_USER_IMAGE);
		}
		return userDao.insertUser(user);
	}
	
	// 로그인
	@Override
	public User login(String id, String password) {
		Map<String, String> user = new HashMap<>();
		user.put("id", id);
		user.put("password", password);
		return userDao.login(user);
	}


}
