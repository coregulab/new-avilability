package com.rest_api.service;

import com.rest_api.entity.Post;
import com.rest_api.exception.ResourceNotFoundException;
import com.rest_api.payload.PostDto;
import com.rest_api.repository.PostRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class  PostService {

    private PostRepository postRepo;


    public PostService(PostRepository postRepo) {
        this.postRepo = postRepo;
    }

    public PostDto createPost(PostDto postDto) {
        Post post = mapToEntity(postDto);
        Post postEntity = postRepo.save(post);
        PostDto dto = mapToDto(postEntity);
        return dto;
    }
    public Post mapToEntity(PostDto postDto){
        Post post = new Post();
        post.setId(postDto.getId());
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        return post;

    }
    public PostDto mapToDto( Post post) {
        PostDto dto = new PostDto();
        dto.setId(post.getId());
        dto.setTitle(post.getTitle());
        dto.setDescription(post.getDescription());
        dto.setContent(post.getContent());

        return dto;
    }

    public List<PostDto> getAllPosts() {
        List<Post> posts = postRepo.findAll();

        List<PostDto> dtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        return dtos;
    }


    public PostDto getPostById(long id) {
        Post post = postRepo.findById(id).get();


        PostDto postDto = mapToDto(post);
        return postDto;
    }


    public PostDto updatePost(PostDto postDto, long id) {
        Post post = postRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("post","id",id)
        );
        post.setTitle(postDto.getTitle());
        post.setDescription(postDto.getDescription());
        post.setContent(postDto.getContent());

        Post newPost = postRepo.save(post);
        PostDto dto = mapToDto(newPost);
        return dto;

    }


    public void deletePost(long id) {
        Post post = postRepo.findById(id).orElseThrow(
                ()-> new ResourceNotFoundException("post","id",id)
        );
        postRepo.deleteById(id);

    }

}