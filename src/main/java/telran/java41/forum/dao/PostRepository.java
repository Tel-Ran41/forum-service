package telran.java41.forum.dao;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.mongodb.repository.MongoRepository;

import telran.java41.forum.model.Post;

public interface PostRepository extends MongoRepository<Post, String> {
//	My code. Methods return PostDto		
	Stream<Post> findByAuthorIgnoreCase(String author);
	
	Stream<Post> findByTagsInIgnoreCase(List<String> tags);
//	Query objects in MongoDB in date format <YYYY-mm-dd> for the period from 2022-03-20 (including) to 2022-03-21 (excluding):
//	{$and:[{dateCreated:{'$gte': ISODate("2022-03-20")}}, {dateCreated:{'$lte': ISODate("2022-03-21")}}]}
	
//	My Method:	
//	@Query("{$and:[{dateCreated:{'$gte': ?0}}, {dateCreated:{'$lte': ?1}}]}")
//	Iterable<PostDto> findPostsByPeriod(LocalDateTime dateFrom, LocalDateTime dateTo);
	
//	 Edd's Method:
		Stream<Post> findByDateCreatedBetween(LocalDate from, LocalDate to);
}
