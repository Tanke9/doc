package com.platzi.profesoresplatzi.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
@Table(name="teacher_social_media")
public class TeacherSocialMedia implements Serializable{

	@Id
	@Column(name="id_teacher_social_media")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long idTeacherSocialMedia;
	@Column(name="nickname")
	private String nickname;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_teacher")
	@JsonIgnore
	private Teacher teacher;
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_social_media")
	private SocialMedia sociaMedia;
	
	
	public TeacherSocialMedia(Long idTeacherSocialMedia, Teacher teacher, SocialMedia sociaMedia, String nickname) {
		super();
		this.idTeacherSocialMedia = idTeacherSocialMedia;
		this.teacher = teacher;
		this.sociaMedia = sociaMedia;
		this.nickname = nickname;
	}
	public TeacherSocialMedia() {
		super();
		// TODO Auto-generated constructor stub
	}
	public Long getIdTeacherSocialMedia() {
		return idTeacherSocialMedia;
	}
	public void setIdTeacherSocialMedia(Long idTeacherSocialMedia) {
		this.idTeacherSocialMedia = idTeacherSocialMedia;
	}
	public Teacher getTeacher() {
		return teacher;
	}
	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}
	public SocialMedia getSociaMedia() {
		return sociaMedia;
	}
	public void setSociaMedia(SocialMedia sociaMedia) {
		this.sociaMedia = sociaMedia;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	
	
}
