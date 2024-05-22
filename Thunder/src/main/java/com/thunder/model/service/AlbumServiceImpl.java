package com.thunder.model.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.thunder.model.dao.AlbumDao;
import com.thunder.model.dto.Album;

@Service
public class AlbumServiceImpl implements AlbumService {

	@Value("${file.desktop-album-dir}")
	private String desktopDir;
	
	private final AlbumDao albumDao;
	
	private final ResourceLoader resourceLoader;
	
	@Autowired
	public AlbumServiceImpl(AlbumDao albumDao, ResourceLoader resourceLoader) {
		this.albumDao = albumDao;
		this.resourceLoader = resourceLoader;
	}

	@Override
	@Transactional
	public boolean registImage(String userId, MultipartFile file, int scheduleId) {
		// 앨범 객체 생성
		Album album = new Album();
		album.setUserId(userId);
		album.setScheduleId(scheduleId);
		
		// 업로드한 파일 있으면
		if (file != null && file.getSize() > 0) {
			try {
				// 파일 저장
				Path uploadPath = Paths.get(desktopDir);
	
		        // 디렉토리가 존재하지 않으면 생성
		        if (!Files.exists(uploadPath)) {
		            Files.createDirectories(uploadPath);
		        }
	
		        album.setOrgImage(file.getOriginalFilename());
		        album.setImage( UUID.randomUUID().toString() + "_" + file.getOriginalFilename());
		        Path filePath = uploadPath.resolve(album.getImage());
	
		        // 파일 저장
		        Files.copy(file.getInputStream(), filePath);
		        
			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
	
		} else {
			// 파일이 없으면 false
			return false;
		}
		
		return albumDao.insert(album) == 1 ? true : false;
	}

	@Override
	public List<Album> getAllAlbum(String userId) {
		return albumDao.selectAllAlbum(userId);
	}

}
