package com.platzi.profesoresplatzi.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.platzi.profesoresplatzi.service.SocialMediaService;
import com.platzi.profesoresplatzi.service.TeacherService;
import com.platzi.profesoresplatzi.util.CustomErrorType;

import com.platzi.profesoresplatzi.model.Course;
import com.platzi.profesoresplatzi.model.SocialMedia;
import com.platzi.profesoresplatzi.model.Teacher;
import com.platzi.profesoresplatzi.model.TeacherSocialMedia;

@Controller
@RequestMapping("/v1")
public class TeacherController {

	public static final String TEACHER_UPLOADED_FOLDER = "images/teachers/";
	
	@Autowired
	TeacherService _teacherService;
	@Autowired
	SocialMediaService _socialMediaService;
	
	@RequestMapping(value="/teachers", method=RequestMethod.GET, headers  ="Accept=application/json")
	public ResponseEntity<List<Teacher>> getTeachers(@RequestParam (name="name", required=false) String name){
		
		List<Teacher> teachers = new ArrayList<>();
		
		if(name == null) {
			teachers=_teacherService.findAllTeachers();
			if (teachers == null || teachers.isEmpty()) {
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<List<Teacher>>(teachers,HttpStatus.OK);
		}else {
			Teacher teacher = _teacherService.findByName(name);
			if (teacher == null)
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			
			teachers.add(teacher);
			return new ResponseEntity<List<Teacher>>(teachers,HttpStatus.OK);
		}
		
	}
	
		//GET
		@RequestMapping(value="/teachers/{id}", method=RequestMethod.GET, headers  ="Accept=application/json")
		public ResponseEntity<Teacher> getById(@PathVariable ("id") Long idTeacher){
			
			if (idTeacher == null || idTeacher <=0) 
				return new ResponseEntity(new CustomErrorType("idTeacher is required"), HttpStatus.CONFLICT);
			
			Teacher teacher= _teacherService.findTeacherById(idTeacher);
			if (teacher == null)
				return new ResponseEntity(HttpStatus.NO_CONTENT);
			
			return new ResponseEntity<Teacher>(teacher, HttpStatus.OK);
		}
		
		//DELETE
		@RequestMapping(value="/teachers/{id}", method=RequestMethod.DELETE, headers  ="Accept=application/json")
		public ResponseEntity<?> deleteTeacher(@PathVariable("id") Long idTeacher){
			if(idTeacher == null || idTeacher<=0) {
				return new ResponseEntity(new CustomErrorType("idTeacher is required"), HttpStatus.CONFLICT);
			}
			
			Teacher teacher= _teacherService.findTeacherById(idTeacher);
			if (teacher == null) {
				return new ResponseEntity(HttpStatus.NO_CONTENT);	
			}
			_teacherService.deleteTeacher(idTeacher);
			
			return new ResponseEntity<Teacher>(HttpStatus.OK);
		}
		
		//create teacher image
		@RequestMapping(value="/teachers/image", method=RequestMethod.POST, headers=("content-type=multipart/form-data"))
		public ResponseEntity<byte[]> uploadTeacherImage(@RequestParam("id_teacher") Long idTeacher,
				@RequestParam("file") MultipartFile multipartFile, UriComponentsBuilder componentsBuilder){
			
			if (idTeacher == null) {
				return new ResponseEntity(new CustomErrorType("Please set id_teacher"), HttpStatus.NO_CONTENT);
			}
			
			if (multipartFile.isEmpty()) {
				return new ResponseEntity(new CustomErrorType("Please select a file to upload"), HttpStatus.NO_CONTENT);
			}
			
			Teacher teacher = _teacherService.findTeacherById(idTeacher);
			
			if (teacher == null) {
				return new ResponseEntity(new CustomErrorType("Teacher with id_teacher: " + idTeacher + " not Found"), HttpStatus.NOT_FOUND);
			}
			
			//si ya existe una ruta con el avatar se elimina para poner la nueva
			if (!teacher.getAvatar().isEmpty() || teacher.getAvatar()!=null) {
				String fileName = teacher.getAvatar();
				Path path = Paths.get(fileName);
				File f= path.toFile();
				if(f.exists()) {
					f.delete();
				}
			}
			
			try {
				//un estandar para escribir nombres de imagenes. Imagenes unicas a partir de la fecha.
				Date date = new Date();
				SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
				String dataName = dateFormat.format(date);
				
				String fileName = String.valueOf(idTeacher) 
						+ "-pictureTeacher-" +dataName+ "." 
						+ multipartFile.getContentType().split("/")[1]; // IMAGE/JPEG
				
				teacher.setAvatar(TEACHER_UPLOADED_FOLDER+fileName);
				
				byte[] bytes = multipartFile.getBytes();
				Path path = Paths.get(TEACHER_UPLOADED_FOLDER + fileName);
				Files.write(path, bytes);
				
				_teacherService.updateTeacher(teacher);
				
				return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(bytes);
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity(new CustomErrorType("Error during upload: " + multipartFile.getOriginalFilename()), HttpStatus.CONFLICT); 
			}
		}
		
		//GET IMAGE
		@RequestMapping(value = "/teachers/{id_teacher}/images",  method=RequestMethod.GET)
		public ResponseEntity<byte[]> getTeacherImage(@PathVariable("id_teacher") Long idTeacher){
			if (idTeacher == null) {
				return new ResponseEntity(new CustomErrorType("Please set id_teacher"), HttpStatus.NO_CONTENT);
			}
			
			Teacher teacher = _teacherService.findTeacherById(idTeacher);
			if (teacher == null) {
				return new ResponseEntity(new CustomErrorType("Teacher with id_teacher: " + idTeacher + " not Found"), HttpStatus.NOT_FOUND);
			}
			
			try {
				String filename = teacher.getAvatar();
				Path path = Paths.get(filename);
				File f= path.toFile();
				if(!f.exists()) {
					return new ResponseEntity(new CustomErrorType("Image not found"), HttpStatus.NOT_FOUND);
				}
				
				byte[] image = Files.readAllBytes(path);
				
				return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(image);
				
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity(new CustomErrorType("Error to show image "), HttpStatus.CONFLICT);
			}
			
			
		}
		
		//DELETE IMAGE
		@RequestMapping(value = "/teachers/{id_teacher}/images",  method=RequestMethod.DELETE, headers  ="Accept=application/json")
		public ResponseEntity<?> deleteTeacherImage(@PathVariable ("id_teacher") Long idTeacher){
			if (idTeacher == null) {
				return new ResponseEntity(new CustomErrorType("Please set id_teacher"), HttpStatus.NO_CONTENT);
			}
			
			Teacher teacher = _teacherService.findTeacherById(idTeacher);
			if (teacher == null) {
				return new ResponseEntity(new CustomErrorType("Teacher with id_teacher: " + idTeacher + " not Found"), HttpStatus.NOT_FOUND);
			}
			
			if (teacher.getAvatar() == null || teacher.getAvatar().isEmpty()) {
				return new ResponseEntity(new CustomErrorType("Teacher without image assigned"), HttpStatus.NOT_FOUND);
			}
			
			String filename = teacher.getAvatar();
			
			try {
				Path path = Paths.get(filename);
				File file = path.toFile();
				if(file.exists()) {
					file.delete();
				}
				teacher.setAvatar("");
				_teacherService.updateTeacher(teacher);
				
				return new ResponseEntity<Teacher>(HttpStatus.NO_CONTENT);
				
			} catch (Exception e) {
				e.printStackTrace();
				return new ResponseEntity(new CustomErrorType("Error to delete image "), HttpStatus.CONFLICT);
			}
			
		}
		
		@RequestMapping (value="/teachers/socialMedias", method=RequestMethod.PATCH, headers  ="Accept=application/json")
		public ResponseEntity<?> assignTeacherSocialMedia(@RequestBody Teacher teacher, UriComponentsBuilder componentsBuilder){
			if (teacher == null || teacher.getIdTeacher()==null) {
				return new ResponseEntity(new CustomErrorType("We need almos id_teacher, idsocialmedia and nickname"), HttpStatus.NO_CONTENT);
			}
			
			Teacher teacherSaved = _teacherService.findTeacherById(teacher.getIdTeacher());
			if (teacherSaved == null) {
				return new ResponseEntity(new CustomErrorType("The id_teacher: " + teacher.getIdTeacher() + " not found"), HttpStatus.NO_CONTENT);
			}
			
			if(teacher.getTeacherSocialMedias().size() == 0) {
				return new ResponseEntity(new CustomErrorType("We need almos id_teacher, idsocialmedia and nickname"), HttpStatus.NO_CONTENT);
			}
			else {
				Iterator<TeacherSocialMedia> i = teacher.getTeacherSocialMedias().iterator();
				while (i.hasNext()) {
					TeacherSocialMedia teacherSocialMedia = i.next();
					if(teacherSocialMedia.getSociaMedia().getIdSocialMedia()==null || teacherSocialMedia.getNickname() == null) {
						return new ResponseEntity(new CustomErrorType("We need almos id_teacher, idsocialmedia and nickname"), HttpStatus.NO_CONTENT);
					}
					else {
						TeacherSocialMedia tsmAux = _socialMediaService.findSocialMediaByIdAndNickname(
								teacherSocialMedia.getSociaMedia().getIdSocialMedia(), 
								teacherSocialMedia.getNickname());
						
						if (tsmAux != null) {
							return new ResponseEntity(new CustomErrorType("The socialmedia " + teacherSocialMedia.getSociaMedia().getIdSocialMedia() + 
									" already exists"), HttpStatus.NO_CONTENT);
						} 
						
						SocialMedia socialMedia = _socialMediaService.findById(teacherSocialMedia.getSociaMedia().getIdSocialMedia());
						if (socialMedia==null) {
							return new ResponseEntity(new CustomErrorType("We idsocial media not found "), HttpStatus.NO_CONTENT);
						}
						
						teacherSocialMedia.setSociaMedia(socialMedia);
						teacherSocialMedia.setTeacher(teacherSaved);
						
						if (tsmAux == null) {
							teacherSaved.getTeacherSocialMedias().add(teacherSocialMedia);
						}else {
							LinkedList<TeacherSocialMedia> teacherSocialMedias = new LinkedList<>();
							teacherSocialMedias.addAll(teacherSaved.getTeacherSocialMedias());
							
							for(int j=0; j< teacherSocialMedias.size(); j++) {
								TeacherSocialMedia teacherSocialMedia2 = teacherSocialMedias.get(j);
								if (teacherSocialMedia.getTeacher().getIdTeacher() == teacherSocialMedia2.getTeacher().getIdTeacher()
										&& teacherSocialMedia.getSociaMedia().getIdSocialMedia() == teacherSocialMedia2.getSociaMedia().getIdSocialMedia() ) {
									teacherSocialMedia2.setNickname(teacherSocialMedia.getNickname());
									teacherSocialMedias.set(j, teacherSocialMedia2);
								}else {
									teacherSocialMedias.set(j, teacherSocialMedia2);
								}
							}
							
							teacherSaved.getTeacherSocialMedias().clear();
							teacherSaved.getTeacherSocialMedias().addAll(teacherSocialMedias);
						}
					}
				}
			}
			
			_teacherService.updateTeacher(teacherSaved);
			return new ResponseEntity<Teacher>(teacherSaved,HttpStatus.OK);
		}
		
}
