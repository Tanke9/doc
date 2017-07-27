package com.platzi.profesoresplatzi.dao;

import java.util.List;

import com.platzi.profesoresplatzi.model.Course;

public interface CourseDao {

	void saveCourse(Course course);
	
	void deleteCourse(Long idCourse);
	
	void updateCourse(Course course);
	
	List<Course> findAllCourses();
	
	Course findCourseById(Long idCourse);
	
	Course findByName(String name);
	
	List<Course> findByIdTeacher (Long idTeacher);
	
}
