package com.platzi.profesoresplatzi.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.platzi.profesoresplatzi.dao.CourseDao;
import com.platzi.profesoresplatzi.model.Course;

@Service("courseService")
@Transactional
public class CourseServiceImpl implements CourseService{

	@Autowired //llamo al objeto mas generico y encuentra el que sea mas compatible con éste
	private CourseDao _courseDao;
	
	@Override
	public void saveCourse(Course course) {
		_courseDao.saveCourse(course);
		
	}

	@Override
	public void deleteCourse(Long idCourse) {
		_courseDao.deleteCourse(idCourse);
		
	}

	@Override
	public void updateCourse(Course course) {
		_courseDao.updateCourse(course);
		
	}

	@Override
	public List<Course> findAllCourses() {
		
		return _courseDao.findAllCourses();
	}

	@Override
	public Course findCourseById(Long idCourse) {
		return _courseDao.findCourseById(idCourse);
	}

	@Override
	public Course findByName(String name) {
		return _courseDao.findByName(name);
	}

	@Override
	public List<Course> findByIdTeacher(Long idTeacher) {
		return _courseDao.findByIdTeacher(idTeacher);
	}

}
