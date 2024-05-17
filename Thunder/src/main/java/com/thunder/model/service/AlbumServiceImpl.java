package com.thunder.model.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.thunder.model.dao.AlbumDao;
import com.thunder.model.dto.Album;

@Service
public class AlbumServiceImpl implements AlbumService {

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
				// 원본 파일명 저장
				album.setOrgImage(file.getOriginalFilename());
				// 중복 방지 파일명 저장
				album.setImage(UUID.randomUUID().toString() + "_" + file.getOriginalFilename());
				
				Resource resource = resourceLoader.getResource("classpath:/static/img");
				file.transferTo(new File(resource.getFile(), album.getImage()));
			} catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
		}
		
		// 업로드한 파일 없으면 이미지 등록 실패
		else {
			return false;
		}
		
		return albumDao.insert(album) == 1 ? true : false;
	}

	@Override
	public List<Album> getAllAlbum(String userId) {
		return albumDao.selectAllAlbum(userId);
	}

}
