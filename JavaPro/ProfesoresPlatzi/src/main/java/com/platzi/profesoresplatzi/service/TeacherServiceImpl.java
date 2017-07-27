package com.platzi.profesoresplatzi.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platzi.profesoresplatzi.dao.TeacherDao;
import com.platzi.profesoresplatzi.model.Teacher;

@Service("teacherService")
@Transactional
public class TeacherServiceImpl implements TeacherService {

	@Autowired
	private TeacherDao _teacherDao;
	
	@Override
	public void saveTeacher(Teacher teacher) {
		_teacherDao.saveTeacher(teacher);
		
	}

	@Override
	public void deleteTeacher(Long idTeacher) {
		_teacherDao.deleteTeacher(idTeacher);
	}

	@Override
	public void updateTeacher(Teacher teacher) {
		_teacherDao.updateTeacher(teacher);
		
	}

	@Override
	public List<Teacher> findAllTeachers() {
		return _teacherDao.findAllTeachers();
	
	}

	@Override
	public Teacher findTeacherById(Long idTeacher) {
		return _teacherDao.findTeacherById(idTeacher);
	}

	@Override
	public Teacher findByName(String name) {
		return _teacherDao.findByName(name);
	}

}
