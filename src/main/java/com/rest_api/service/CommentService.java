package com.rest_api.service;

import com.rest_api.entity.Comment;
import com.rest_api.entity.Post;
import com.rest_api.payload.CommentDto;
import com.rest_api.repository.CommentRepository;
import com.rest_api.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
    public class  CommentService {

        private CommentRepository commentRepo;
        private PostRepository postRepo;



        public CommentService(CommentRepository commentRepo, PostRepository postRepo) {
            this.commentRepo = commentRepo;
            this.postRepo = postRepo;
        }


        public CommentDto createComment(long postId, CommentDto commentDto) {
            Post post = postRepo.findById(postId).get();

            Comment comment = mapToComment(commentDto);

            comment.setPost(post);

            Comment newComment = commentRepo.save(comment);
            CommentDto dto = mapToDto(newComment);
            return dto;
        }

        public Comment mapToComment(CommentDto commentDto) {
            Comment comment = new Comment();
            comment.setId(commentDto.getId());
            comment.setBody(commentDto.getBody());
            comment.setEmail(commentDto.getEmail());
            comment.setName(commentDto.getName());

            return comment;
        }

        public CommentDto mapToDto(Comment comment) {
            CommentDto dto = new CommentDto();
            dto.setId(comment.getId());
            dto.setBody(comment.getBody());
            dto.setEmail(comment.getEmail());
            dto.setName(comment.getName());

            return dto;
        }

    public List<CommentDto> getCommentByPostId(long postId) {
        List<Comment> comments = commentRepo.findByPostId(postId);
        return comments.stream().map(comment->mapToDto(comment)).collect(Collectors.toList());

    }


    public CommentDto updateComment(long postId, long id, CommentDto commentDto) {
        Post post = postRepo.findById(postId).get();
        Comment comment = commentRepo.findById(id).get();

        comment.setName(commentDto.getName());
        comment.setEmail(commentDto.getEmail());
        comment.setBody(commentDto.getBody());

        Comment updatedComment = commentRepo.save(comment);
        return mapToDto(updatedComment);
    }


    public void deleteComment(long postId, long commentId) {
        Post post = postRepo.findById(postId).get();
        Comment comment = commentRepo.findById(commentId).get();
        commentRepo.deleteById(commentId);
    }

}


