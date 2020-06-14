package com.leverx.blog.controller;

import com.leverx.blog.dto.ArticleDTO;
import com.leverx.blog.dto.CommentDTO;
import com.leverx.blog.entity.TagsCloud;
import com.leverx.blog.security.JwtUser;
import com.leverx.blog.service.ArticleService;
import com.leverx.blog.service.CommentService;
import com.leverx.blog.service.TagService;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/articles")
public class ArticleController {
    private final ArticleService articleService;
    private final CommentService commentService;
    private final TagService tagService;

    public ArticleController(ArticleService articleService, CommentService commentService, TagService tagService) {
        this.articleService = articleService;
        this.commentService = commentService;
        this.tagService = tagService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<ArticleDTO> editArticle(
            @PathVariable Integer id,
            @RequestBody ArticleDTO article,
            @AuthenticationPrincipal JwtUser currentUser) {
        Optional<ArticleDTO> edited = articleService.editArticle(id, article, currentUser.getId());
        return edited.isPresent() ? ResponseEntity.ok(edited.get()) : ResponseEntity.noContent().build();
    }

    @PostMapping
    public ResponseEntity<ArticleDTO> save(@RequestBody ArticleDTO article) {
        return ResponseEntity.ok(articleService.save(article));
    }

    @PostMapping("/{article-id}/comments")
    public ResponseEntity<CommentDTO> save(
            @PathVariable("article-id") Integer articleId,
            @RequestBody CommentDTO comment
    ) {
        return ResponseEntity.ok(commentService.save(articleId, comment));
    }

    @GetMapping
    public ResponseEntity<List<ArticleDTO>> getArticles(
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) int skip,
            @RequestParam(required = false) int limit,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) Sort.Direction order
    ) {
        return ResponseEntity.ok(articleService.getPublicArticles(userId, skip, limit, sortBy, order));
    }

    @GetMapping("/my")
    public ResponseEntity<List<ArticleDTO>> getMyArticles(@AuthenticationPrincipal JwtUser currentUser) {
        return ResponseEntity.ok(articleService.getMyArticles(currentUser.getId()));
    }

    @GetMapping("/{article-id}/comments")
    public ResponseEntity<List<CommentDTO>> getComments(
            @PathVariable("article-id") Integer articleId,
            @RequestParam(required = false) Integer userId,
            @RequestParam(required = false) int skip,
            @RequestParam(required = false) int limit,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) Sort.Direction order
    ) {
        return ResponseEntity.ok(commentService.getComments(articleId, userId, skip, limit, sortBy, order));
    }

    @GetMapping("/{article-id}/comments/{comment-id}")
    public ResponseEntity<CommentDTO> getComment(
            @PathVariable("article-id") Integer articleId,
            @PathVariable("comment-id") Integer commentId
    ) {
        Optional<CommentDTO> comment = commentService.getComment(articleId, commentId);
        return comment.isPresent() ? ResponseEntity.ok(comment.get()) : ResponseEntity.noContent().build();
    }

    @GetMapping("/tags")
    public ResponseEntity<List<ArticleDTO>> getArticlesByTags(@RequestParam List<String> tags) {
        return ResponseEntity.ok(tagService.getArticlesByTags(tags));
    }

    @GetMapping("/tags-cloud")
    public ResponseEntity<List<TagsCloud>> getTagsCloud(@RequestParam List<String> tags) {
        return ResponseEntity.ok(tagService.getTagsCloud(tags));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        articleService.delete(id);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{article-id}/comments/{comment-id}")
    public ResponseEntity delete(
            @PathVariable("article-id") Integer articleId,
            @PathVariable("comment-id") Integer commentId,
            @AuthenticationPrincipal JwtUser currentUser
    ) {
        commentService.delete(articleId, commentId, currentUser.getId());
        return ResponseEntity.ok().build();
    }
}
