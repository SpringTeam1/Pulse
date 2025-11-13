package com.test.pulse.controller.boardnotice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/boardnotice")
public class BoardNoticeController {
	
	@GetMapping("/list.do")
    public String boardNoticeList() {
        
		return "boardnotice.list";
    }
	
	@GetMapping("/add.do")
    public String boardNoticeAdd() {
        
		return "boardnotice.add";
    }

}
