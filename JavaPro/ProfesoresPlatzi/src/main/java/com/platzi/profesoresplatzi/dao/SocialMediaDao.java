package com.platzi.profesoresplatzi.dao;

import java.util.List;

import com.platzi.profesoresplatzi.model.SocialMedia;
import com.platzi.profesoresplatzi.model.TeacherSocialMedia;

public interface SocialMediaDao {

	void saveSocialMedia(SocialMedia socialMedia);
	
	void deleteSocialMedia(Long idSocialMedia);
	
	void updateSocialMedia(SocialMedia socialMedia);
	
	List<SocialMedia> findAllSocialMedias();
	
	SocialMedia findById(Long idSocialMedia);
	
	SocialMedia findByName (String name);
	
	TeacherSocialMedia findSocialMediaByIdAndNickname (Long idSocialMedia, String nickname);
	
	TeacherSocialMedia findSocialMediaByIdTeacherAndIdSocialMedia (Long idTeacher, Long idSocialMedia);
	
}
