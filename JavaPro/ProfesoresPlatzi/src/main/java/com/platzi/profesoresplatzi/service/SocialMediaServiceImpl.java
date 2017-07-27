package com.platzi.profesoresplatzi.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platzi.profesoresplatzi.dao.CourseDao;
import com.platzi.profesoresplatzi.dao.SocialMediaDao;
import com.platzi.profesoresplatzi.model.SocialMedia;
import com.platzi.profesoresplatzi.model.TeacherSocialMedia;

@Service("socialMediaService")
@Transactional
public class SocialMediaServiceImpl implements SocialMediaService {

	@Autowired //llamo al objeto mas generico y encuentra el que sea mas compatible con éste
	private SocialMediaDao _socialMediaDao;
	
	@Override
	public void saveSocialMedia(SocialMedia socialMedia) {
		_socialMediaDao.saveSocialMedia(socialMedia);
		
	}

	@Override
	public void deleteSocialMedia(Long idSocialMedia) {
		_socialMediaDao.deleteSocialMedia(idSocialMedia);
		
	}

	@Override
	public void updateSocialMedia(SocialMedia socialMedia) {
		_socialMediaDao.updateSocialMedia(socialMedia);
		
		
	}

	@Override
	public List<SocialMedia> findAllSocialMedias() {
		return _socialMediaDao.findAllSocialMedias();
	}

	@Override
	public SocialMedia findById(Long idSocialMedia) {
		return _socialMediaDao.findById(idSocialMedia);
	}

	@Override
	public SocialMedia findByName(String name) {
		return _socialMediaDao.findByName(name);
	}

	@Override
	public TeacherSocialMedia findSocialMediaByIdAndNickname(Long idSocialMedia, String nickname) {
		return _socialMediaDao.findSocialMediaByIdAndNickname(idSocialMedia, nickname);
	}

}
