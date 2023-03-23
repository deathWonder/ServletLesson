package org.example.repository;

import org.example.model.Post;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepository {

    AtomicLong generateIndex = new AtomicLong(1);
    ConcurrentHashMap<Long, Post> listPost = new ConcurrentHashMap<>();

    public List<Post> all() {
        return new ArrayList<>(listPost.values());
        }
    public Optional<Post> getById(long id) {
        return Optional.of(listPost.get(id));
    }
    public Post save(Post post) {
        if(post.getId()==0){
            post.setId(generateIndex.getAndIncrement());
        }
        listPost.put(post.getId(), post);
        return post;
    }
    public void removeById(long id) {
        listPost.remove(id);
    }
}
