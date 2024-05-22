package com.thunder.model.dao;

import java.util.List;

import com.thunder.model.dto.Album;

public interface AlbumDao {

	int insert(Album album);

	List<Album> selectAllAlbum(String userId);

}