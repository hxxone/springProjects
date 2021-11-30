package com.green.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.green.vo.AttachFileDTO;

import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnailator;

@Controller
@Slf4j

public class UploadController {
	@GetMapping("/uploadForm")
	public void uploadForm() {
		log.info("================ 파일 업로드 폼 태그 이용" );
	}
	@PostMapping("/uploadFormAction")
	public void upladFormPost(MultipartFile[] uploadFile, Model model) {
		String uploadFolder ="c:\\upload";
		for(MultipartFile i : uploadFile) {
			log.info("================= 컨트롤러에서 파일 업로드 포스트 폼 태그 전송");
			log.info(" 파일 이름 " + i.getContentType() + " || 1" + i.getName()+ " || 2   " + i.getOriginalFilename() + " || 3    "  + i.getClass());
			log.info(" 업로드 파일 사이즈  : " + i.getSize());
			
			File saveFile = new File(uploadFolder, i.getOriginalFilename());
			
			try {
				i.transferTo(saveFile);
			}catch(Exception e) {
				log.error(e.getMessage());
			}
		}// /for
	}// 폼태그로 이미지
	
	@GetMapping("/uploadAjax")
	public void uploadAjax() {
		
		log.info("파일 업로드 ajax 컨트롤러 " );
	}
	private String getFolder() {// 날짜를 이용 해 폴더 구조의 문자열을 반환하는 함수
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		String str = sdf.format(date);
		return str.replace("-", File.separator); // 문자열 중 - 을 파일의 구분자로 교체한다
	}
	
	private boolean checkImageType(File file) {
	
		try {
			String contentType = Files.probeContentType(file.toPath());
			return contentType.startsWith("image");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping(value = "/uploadAjaxAction", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<AttachFileDTO>> upladAjaxPost(MultipartFile[] uploadFile) {
		List<AttachFileDTO> list = new ArrayList<>();
		String uploadFolder ="c:\\upload";
		//폴더 생성
		String uploadFolderPath = getFolder();
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		log.info(" 파일 업로드 경로 : " + uploadPath);
		if(!uploadPath.exists()) {
			uploadPath.mkdirs(); // 파일 경로가 존재 하지 않으면 폴더를 생성함	
		}
		
		
		for(MultipartFile i : uploadFile) {
			log.info("================= 컨트롤러에서 파일 업로드 AJAX post 전송");
			AttachFileDTO attachDTO = new AttachFileDTO();
			String uploadFileName = i.getOriginalFilename();
			log.info(" 파일 이름 " + i.getContentType() + " || 1" + i.getName()+ " || 2   " + uploadFileName + " || 3    "  + i.getClass());
			log.info(" 업로드 파일 사이즈  : " + i.getSize());
		
			attachDTO.setFileName(uploadFileName);
			UUID uuid = UUID.randomUUID(); // 고유한 키를 생성해 주는 자바의 util
			uploadFileName =uuid.toString()+"_"+uploadFileName; 						
			log.info(" uuid 이후 파일 이름 " + uploadFileName);

			try {
				File saveFile = new File(uploadPath, uploadFileName); // 파일 객체 생성
				i.transferTo(saveFile);
				attachDTO.setUuid(uuid.toString());
				attachDTO.setUploadPath(uploadFolderPath);
				if(checkImageType(saveFile)) {
					attachDTO.setImage(true);
					FileOutputStream thumbnail = new FileOutputStream( // 파일 객체를 아웃스트림으로 변환 
							new File(uploadPath, "s_" + uploadFileName));//( 1) 파일의 경로, 2) 파일 이름)
					Thumbnailator.createThumbnail(i.getInputStream(), thumbnail, 100,100);
					thumbnail.close();
					
				}
				list.add(attachDTO);
			}catch(Exception e) {
				log.error(e.getMessage());
			}
		}
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}
	
	@GetMapping("/display")
	@ResponseBody
	public ResponseEntity<byte[]> getFile(String fileName){ // 문자열로 파일의 경로가 포함된 fileName을 받아 
		log.info("컨트롤러의 display 에서 file Name : " + fileName );
		File file = new File("c:\\upload\\" + fileName);
		log.info("file : " + file);
		ResponseEntity<byte[]> result = null; //
		try {
			HttpHeaders header = new HttpHeaders(); // prob~ 무슨 파일인지 확인하기 좋음?
			header.add("Content-type", Files.probeContentType(file.toPath())); //헤더에 추가
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK); // body, header , status
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result; //byte[] 전송, => 브라우저에서 보내주는 mime 타입이 파일의 종류에 따라 달라짐
		
	}
	@GetMapping(value = "/download" ,produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
	@ResponseBody 
	public ResponseEntity<Resource> downloadFile(String fileName){
		log.info("컨트롤러 파일 다운로드 file : " +  fileName);
		Resource resource = new FileSystemResource("c:\\upload\\" + fileName);
		if(resource.exists()==false) return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
		String resourceName = resource.getFilename();
		log.info("resource: " +resource);

		HttpHeaders headers = new HttpHeaders();
		String downloadName = null;
		try {
			log.info("크롬 브라우저");
			downloadName = new String(resourceName.getBytes("UTF8"),"ISO-8859-1");
			headers.add("Content-Disposition" , "attachment; fileName=" + downloadName);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ResponseEntity<Resource>(resource,headers , HttpStatus.OK);
	}
	
	@PreAuthorize("isAuthenticated()")
	@PostMapping("/deleteFile")
	@ResponseBody
	public ResponseEntity<String> deleteFile(String fileName, String type){
		log.info("delete File : " + fileName);
		
		File file;
		try {
			file = new File("c:\\upload\\" + URLDecoder.decode(fileName, "UTF-8"));
			file.delete();//thumbnail 삭제
			if(type.equals("image")) {
				String largeFileName = file.getAbsolutePath().replace("s_", "");
				log.info("원래 파일 이름" + largeFileName);
				// _s는 섬네일파일에 붙었으므로 이를 없애면 원래 파일명을 구할 수 있음
				file = new File(largeFileName);
				file.delete();// 원래 파일 삭제
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
			
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	}
	
}
