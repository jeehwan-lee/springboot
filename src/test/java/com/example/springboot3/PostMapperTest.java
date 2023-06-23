package com.example.springboot3;

import com.example.springboot3.post.PostMapper;
import com.example.springboot3.post.PostRequest;
import com.example.springboot3.post.PostResponse;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class PostMapperTest {

    @Autowired
    PostMapper postMapper;

    @Test
    void save() {
        PostRequest params = new PostRequest();
        params.setTitle("1번 게시글 제목");
        params.setContent("1번 게시글 내용");
        params.setWriter("테스터");
        params.setNoticeYn(false);
        postMapper.save(params);

        List<PostResponse> posts = postMapper.findAll();
        System.out.println("전체 게시글 개수는 : " + posts.size());
    }

    @Test
    void findById() {
        PostResponse post = postMapper.findById(1L);

        try{
            String postJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(post);
            System.out.println(postJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void update() {
        PostRequest params = new PostRequest();
        params.setId(1L);
        params.setTitle("1번 게시글 수정되었습니다.");
        params.setContent("1번 게시글 내용 수정합니다.");
        params.setWriter("이지환");
        params.setNoticeYn(true);
        postMapper.update(params);

        PostResponse post = postMapper.findById(1L);

        try{
            String postJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(post);
            System.out.println(postJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void delete() {
        System.out.println("삭제 전 : " + postMapper.findAll().size());
        postMapper.deleteById(1L);
        System.out.println("삭제 후 : " + postMapper.findAll().size());
    }
}
