package com.thunder.model.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.thunder.model.dao.UserDao;
import com.thunder.model.dto.User;

@Service
public class UserServiceImpl implements UserService {

	@Value("${file.desktop-user-dir}")
    private String desktopDir;
	
	private final UserDao userDao;
	
	@Autowired
	public UserServiceImpl(UserDao userDao) {
		this.userDao = userDao;
	}
	
	// 회원가입
	@Transactional
	@Override
	public int registUser(User user, MultipartFile file){
		
		// 업로드한 파일 있으면
		if (file != null && file.getSize() > 0) {
			try {
				// 파일 저장
				Path uploadPath = Paths.get(desktopDir);

		        // 디렉토리가 존재하지 않으면 생성
		        if (!Files.exists(uploadPath)) {
		            Files.createDirectories(uploadPath);
		        }

		        user.setOrgImage(file.getOriginalFilename());
		        user.setImage( UUID.randomUUID().toString() + "_" + file.getOriginalFilename());
		        Path filePath = uploadPath.resolve(user.getImage());

		        // 파일 저장
		        Files.copy(file.getInputStream(), filePath);
				
			} catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
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
	
	// 유저 아이디로 유저 정보 조회
	@Override
	public User getUserById(String userId) {
		User user = userDao.selectUserById(userId);

		// id로 유저 조회가 되지 않는다면 null값 반환
		if (user == null) return null;
		
		user.setPassword(null);
		return user;
	}

}
