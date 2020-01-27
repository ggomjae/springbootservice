package com.gomjae.book.springboot.web;


import com.gomjae.book.springboot.config.auth.LoginUser;
import com.gomjae.book.springboot.config.auth.dto.SessionUser;
import com.gomjae.book.springboot.service.PostsService;
import com.gomjae.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.mail.Session;

@RequiredArgsConstructor
@Controller
public class IndexController {

    private final PostsService postsService;

    @GetMapping("/")
    public String index(Model model, @LoginUser SessionUser user){

        model.addAttribute("posts",postsService.findAllDesc());

        if(user != null){
            model.addAttribute("userName",user.getName());
        }

        return "index";//
    }

    @GetMapping("/posts/get/{id}")
    public String postsGet(@PathVariable Long id, @LoginUser SessionUser user, Model model){

        PostsResponseDto dto = postsService.findById(id);
        boolean btnStatus = false;

        if(user.getName().equals(dto.getAuthor())){
            btnStatus = true;
        }

        model.addAttribute("post",dto);
        model.addAttribute("btnStatus",btnStatus);

        return "posts-get";
    }

    @GetMapping("/posts/save")
    public String postsSave(Model model , @LoginUser SessionUser user){

        if(user != null){
            model.addAttribute("userName", user.getName());
        }

        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model){

        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post",dto);

        return "posts-update";//
    }
}