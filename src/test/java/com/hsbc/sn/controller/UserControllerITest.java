package com.hsbc.sn.controller;

import com.hsbc.sn.controller.entity.Message;
import com.hsbc.sn.controller.entity.PostId;
import com.hsbc.sn.controller.entity.RawPost;
import com.hsbc.sn.repository.PostRepository;
import com.hsbc.sn.repository.model.Post;
import com.hsbc.sn.repository.model.User;
import com.hsbc.sn.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControllerITest {

    private static final String BASE_URL = "/api/users";

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void shouldReceiveUserPosts() {
        // GIVEN
        User user = new User();
        user.setId(1);
        Post post1 = new Post();
        post1.setMessage("Message 1");
        Post post2 = new Post();
        post2.setMessage("Message 2");
        user.getPosts().add(post1);
        user.getPosts().add(post2);
        userRepository.saveAndFlush(user);

        // WHEN
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<RawPost[]> response = restTemplate.exchange(BASE_URL + "/1/posts", HttpMethod.GET, entity, RawPost[].class);
        RawPost[] posts = response.getBody();

        // THEN
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, posts.length);
        assertEquals(post1.getMessage(), posts[0].getMessage());
        assertEquals(post2.getMessage(), posts[1].getMessage());
    }

    @Test
    public void shouldVerifyUserPresenceWhenGet() {
        // WHEN
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity entity = new HttpEntity(headers);
        ResponseEntity<Post[]> response = restTemplate.exchange(BASE_URL + "/1/posts", HttpMethod.GET, entity, Post[].class);

        // THEN
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void shouldCreateUserWhenAddPost() {
        // GIVEN
        final long userId = 1;
        Message message = new Message("Message");

        // WHEN
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Message> entity = new HttpEntity<>(message, headers);
        ResponseEntity<PostId> response = restTemplate.exchange(BASE_URL + "/" + userId + "/posts", HttpMethod.POST, entity, PostId.class);

        // THEN
        User newUser = userRepository.findOne(userId);
        Post newPost = postRepository.findOne(response.getBody().getPostId());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(userId, newUser.getId());
        assertEquals(message.getMessage(), newPost.getMessage());
    }

    @Test
    public void shouldValidatePostMessageLength() {
        // GIVEN
        final long userId = 1;
        Message message = new Message(new String(new char[141]).replace('\0', 'a'));

        // WHEN
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<Message> entity = new HttpEntity<>(message, headers);
        ResponseEntity<PostId> response = restTemplate.exchange(BASE_URL + "/" + userId + "/posts", HttpMethod.POST, entity, PostId.class);

        // THEN
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

}
