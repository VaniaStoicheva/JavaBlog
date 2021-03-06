package softuniBlog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import softuniBlog.bindingModel.ArticleBindingModel;
import softuniBlog.bindingModel.CommentBindingModel;
import softuniBlog.entity.Article;
import softuniBlog.entity.Comment;
import softuniBlog.entity.User;
import softuniBlog.repository.ArticleRepository;
import softuniBlog.repository.CommentRepository;
import softuniBlog.repository.UserRepository;

import java.util.Set;

@Controller
public class CommentController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @GetMapping("/comment/create/{id}")
    @PreAuthorize("isAuthenticated()")
    public String create(Model model,@PathVariable Integer id){

       Article article=this.articleRepository.findOne(id);

        model.addAttribute("article",article);
        model.addAttribute("view","comment/create");

        return "base-layout";
    }


    @PostMapping("/comment/create/{id}")
    @PreAuthorize("isAuthenticated()")
    public String createProcess(CommentBindingModel commentBindingModel,@PathVariable Integer id){

        UserDetails user=(UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        User userEntity=this.userRepository.findByEmail(user.getUsername());

        Article article=this.articleRepository.findOne(id);


        Comment commentEntity=new Comment(
                commentBindingModel.getContent(),
                userEntity,
                article

        );

        this.commentRepository.saveAndFlush(commentEntity);

        return "redirect:/";
    }
@GetMapping("/comment/{id}")
public String listComments(Model model,@PathVariable Integer id){

    if(!this.commentRepository.exists(id)){
        return "redirect:/";
    }

    Article article=this.articleRepository.findOne(id);
    Set<Comment> comments=this.articleRepository.findOne(id).getComments();

    model.addAttribute("article",article);
    model.addAttribute("comments",comments);
    model.addAttribute("view","comment/list-comment");

    return "base-layout";
}
}
