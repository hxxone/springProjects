package com.green.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.green.service.BoardService;
import com.green.vo.BoardAttachVO;
import com.green.vo.BoardVO;
import com.green.vo.Criteria;
import com.green.vo.PageDto;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("/board/*")
@Slf4j
public class BoardController {
	@Setter(onMethod_=@Autowired)
	BoardService service;

	@GetMapping("/register")
	@PreAuthorize("isAuthenticated()")
	public void resgister() {
		System.out.println("1) 컨트롤러 등록 get " );
	}
	
	@PostMapping("/register")
	@PreAuthorize("isAuthenticated()")
	public String registerList(BoardVO board, RedirectAttributes rttr) {
		System.out.println("1) controller에서 포스트 방식 등록 "+board);
		if(board.getAttachList()!=null) board.getAttachList().forEach(i -> log.info("=============================="+ i));
		
		service.register(board); // 등록하고
		rttr.addFlashAttribute("result", board.getBno()); //뭘까
		System.out.println();
		return "redirect:list";
		
	}

	
	@GetMapping("/list")
	public void list(Model model, Criteria cri) {
		log.info("1) 컨트롤러에서 목록 조회");
//		model.addAttribute("pageMaker", new PageDto(cri, 123));
		int total = service.getTotal(cri);
		log.info("1) 컨트롤에서 갯수 구하기 " + total +"===========================================");
		model.addAttribute("list" , service.getList(cri));
		model.addAttribute("pageMaker", new PageDto(cri, total));
		
	}
	
	

	@GetMapping({"/get","/modify"})
	public void get(@RequestParam("bno") Long  bno, Model model, @ModelAttribute("cri") Criteria cri) {
		log.info("1) 컨트롤러에서의 데이터 조회// 수정 " +bno+" "+cri);
		model.addAttribute("board", service.get(bno));
	}
	@PreAuthorize("principal.username==#writer")
	@PostMapping("/modify")
	public String modify(BoardVO board, RedirectAttributes rttr, @ModelAttribute("cri") Criteria cri) {
		log.info("1) 컨트롤러에서 수정" + board+"  cri: " + cri);
		
		if(service.modify(board)) rttr.addFlashAttribute("result", "success");
		rttr.addAttribute("pageNum", cri.getPageNum());
		rttr.addAttribute("amount", cri.getAmount());
		rttr.addAttribute("keyword", cri.getKeyword());
		rttr.addAttribute("type", cri.getType());
		
		return "redirect:/board/list";
	}
	
	@PreAuthorize("principal.username==#writer")
	@PostMapping("/remove")
	public String remover(@RequestParam("bno") Long bno, RedirectAttributes rttr, @ModelAttribute("cri") Criteria cri) {
		log.info("1) 컨트롤러에서 삭제 : " + bno);
		
		List<BoardAttachVO> attachList = service.getAttachList(bno);
		if(service.remove(bno)) {
			deleteFile(attachList);
			rttr.addFlashAttribute("result", "success");
		}
//		rttr.addAttribute("pageNum", cri.getPageNum());
//		rttr.addAttribute("amount", cri.getAmount());
//		rttr.addAttribute("keyword", cri.getKeyword());
//		rttr.addAttribute("type", cri.getType());
		return  "redirect:/board/list";
	}
	
	@GetMapping(value="/getAttachList", produces =MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ResponseBody
	public ResponseEntity<List<BoardAttachVO>> getAttachList(Long bno){
		log.info("1) 컨트롤러에서 첨부파일 =========================================="+ bno);
		return new ResponseEntity<List<BoardAttachVO>>(service.getAttachList(bno), HttpStatus.OK);
	}//첨부파일 리스트를 얻어옴
	
	
	private void deleteFile(List<BoardAttachVO> attachList){
		if(attachList ==null || attachList.size()==0) return;
		log.info("delete File : " + attachList);
		
		
		
		attachList.forEach( attach -> {
			try {
				Path file = Paths.get("C:\\upload\\"+attach.getUploadPath()+"\\"+attach.getUuid()+"_"+attach.getFileName());
				Files.deleteIfExists(file);
				
				if(Files.probeContentType(file).startsWith("image")) {
					Path thumbNail = Paths.get("C:\\upload\\"+attach.getUploadPath()+"\\s_"+attach.getUuid()+"_"+attach.getFileName());
					Files.delete(thumbNail);
				}
			} catch (Exception e) {
				e.printStackTrace();
				
			}
		});

	}

}


