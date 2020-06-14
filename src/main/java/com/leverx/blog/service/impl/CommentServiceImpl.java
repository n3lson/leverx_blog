package com.leverx.blog.service.impl;

import com.leverx.blog.converter.impl.CommentConverter;
import com.leverx.blog.dto.CommentDTO;
import com.leverx.blog.entity.Article;
import com.leverx.blog.entity.Comment;
import com.leverx.blog.repo.ArticleRepository;
import com.leverx.blog.repo.CommentRepository;
import com.leverx.blog.service.CommentService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepo;
    private final ArticleRepository articleRepo;
    private final CommentConverter converter;

    public CommentServiceImpl(CommentRepository commentRepo, ArticleRepository articleRepo, CommentConverter converter) {
        this.commentRepo = commentRepo;
        this.articleRepo = articleRepo;
        this.converter = converter;
    }

    @Override
    public CommentDTO save(Integer articleId, CommentDTO comment) {
        comment.setArticleId(articleId);
        return converter.toDTO(commentRepo.save(converter.toEntity(comment)));
    }

    @Override
    public List<CommentDTO> getComments(
            Integer articleId,
            Integer userId,
            int skip,
            int limit,
            String sortBy,
            Sort.Direction order
    ) {
        if (order == Sort.Direction.DESC) {
            return converter.entitiesToDTO(commentRepo.getCommentsDESC(articleId, userId, skip, limit, sortBy));
        } else {
            return converter.entitiesToDTO(commentRepo.getCommentsASC(articleId, userId, skip, limit, sortBy));
        }
    }

    @Override
    public Optional<CommentDTO> getComment(Integer articleId, Integer commentId) {
        Optional<Comment> comment = commentRepo.getCommentByArticleIdAndId(articleId, commentId);
        return comment.map(converter::toDTO);
    }

    @Override
    public void delete(Integer articleId, Integer commentId, Integer userId) {
        Optional<Article> article = articleRepo.findById(articleId);
        Optional<Comment> comment = commentRepo.findById(articleId);
        if (article.isPresent() && comment.isPresent()) {
            if (article.get().getUser().getId().equals(userId) ||
                comment.get().getUser().getId().equals(userId)) {
                commentRepo.deleteById(commentId);
            }
        }
    }
}
