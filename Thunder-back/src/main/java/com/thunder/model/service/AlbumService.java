package com.thunder.model.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.thunder.model.dto.Album;

public interface AlbumService {

	boolean registImage(String userId, MultipartFile file, int scheduleId);

	List<Album> getAllAlbum(String userId);

}
