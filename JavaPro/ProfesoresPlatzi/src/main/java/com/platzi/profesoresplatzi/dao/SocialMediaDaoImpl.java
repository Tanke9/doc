package com.platzi.profesoresplatzi.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.stereotype.Repository;

import com.platzi.profesoresplatzi.model.SocialMedia;
import com.platzi.profesoresplatzi.model.TeacherSocialMedia;

@Repository
@Transactional 
public class SocialMediaDaoImpl extends AbstractSession implements SocialMediaDao{

	@Override
	public void saveSocialMedia(SocialMedia socialMedia) {
		getSession().persist(socialMedia);
		
	}

	@Override
	public void deleteSocialMedia(Long idSocialMedia) {
		SocialMedia socialMedia = findById(idSocialMedia);
		if (socialMedia!=null) {
			getSession().delete(socialMedia);
		}
		
	}

	@Override
	public void updateSocialMedia(SocialMedia socialMedia) {
		getSession().update(socialMedia);
		
	}

	@Override
	public List<SocialMedia> findAllSocialMedias() {
		return getSession().createQuery("from SocialMedia").list();
	}

	@Override
	public SocialMedia findById(Long idSocialMedia) {
		return getSession().get(SocialMedia.class,idSocialMedia);
	}

	@Override
	public SocialMedia findByName(String name) {
		return (SocialMedia) getSession().createQuery(
				"from SocialMedia where name = :name")
				.setParameter("name", name).uniqueResult();
	}

	@Override
	public TeacherSocialMedia findSocialMediaByIdAndNickname(Long idSocialMedia, String nickname) {
		List<Object[]> objects =  getSession().createQuery(
				"from TeacherSocialMedia tsm join tsm.socialMedia sm "
				+ "where sm.idSocialMedia = :idSocialMedia and tsm.nickname = :nickname ")
				.setParameter("idSocialMedia", idSocialMedia)
				.setParameter("nickname", nickname).list();
		
		if (objects.size()>0) {
			for (Object[] objects2 : objects) {
				for (Object object : objects2) {
					if (object instanceof TeacherSocialMedia) {
						return (TeacherSocialMedia) object;
					}
				}
			}
		}
		
		return null;
	}

	@Override
	public TeacherSocialMedia findSocialMediaByIdTeacherAndIdSocialMedia(Long idTeacher, Long idSocialMedia) {
		List<Object[]> objects =  getSession().createQuery(
				"from TeacherSocialMedia tsm join tsm.socialMedia sm join tsm.teacher t "
				+ "where sm.idSocialMedia = :idSocialMedia and t.idTeacher= :idTeacher ")
				.setParameter("idSocialMedia", idSocialMedia)
				.setParameter("idTeacher", idTeacher).list();
		
		if (objects.size()>0) {
			for (Object[] objects2 : objects) {
				for (Object object : objects2) {
					if (object instanceof TeacherSocialMedia) {
						return (TeacherSocialMedia) object;
					}
				}
			}
		}
		return null;
	}

}
