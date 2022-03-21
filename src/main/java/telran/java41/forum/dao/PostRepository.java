package telran.java41.forum.dao;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import telran.java41.forum.dto.PostDto;
import telran.java41.forum.model.Post;

public interface PostRepository extends MongoRepository<Post, String> {
	Iterable<PostDto> findByAuthorIgnoreCase(String author);
	
	Iterable<PostDto> findByTagsInIgnoreCase(List<String> tags);
	
	@Query("{$and:[{dateCreated:{'$gte': ?0}}, {dateCreated:{'$lte': ?1}}]}")
	Iterable<PostDto> findPostsByPeriod(LocalDateTime dateFrom, LocalDateTime dateTo);
}
