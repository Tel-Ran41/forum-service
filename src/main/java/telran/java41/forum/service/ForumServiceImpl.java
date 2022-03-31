package telran.java41.forum.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import telran.java41.forum.dao.PostRepository;
import telran.java41.forum.dto.DatePeriodDto;
import telran.java41.forum.dto.NewCommentDto;
import telran.java41.forum.dto.NewPostDto;
import telran.java41.forum.dto.PostDto;
import telran.java41.forum.dto.exceptions.PostNotFoundException;
import telran.java41.forum.model.Comment;
import telran.java41.forum.model.Post;

@Service
public class ForumServiceImpl implements ForumService {
	
	PostRepository postRepository;
	ModelMapper modelMapper;

	@Autowired
	public ForumServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
		this.postRepository = postRepository;
		this.modelMapper = modelMapper;
	}

	@Override
	public PostDto addNewPost(NewPostDto newPost, String author) {
		Post post = modelMapper.map(newPost, Post.class);
		post.setAuthor(author);
		post = postRepository.save(post);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto getPost(String id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto removePost(String id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		postRepository.delete(post);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public PostDto updatePost(NewPostDto postUpdateDto, String id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		String title = postUpdateDto.getTitle();
		if (title != null) {
			post.setTitle(title);
		}
		String content = postUpdateDto.getContent();
		if (content != null) {
			post.setContent(content);
		}
//		My code. Tag update:
		post.getTags().clear();
		post.getTags().addAll(postUpdateDto.getTags());
//		Code from Edd. Adding tags:
//		Set<String> tags = postUpdateDto.getTags();
//		if(tags != null) {
//			tags.forEach(post::addTag);
//		}
		post = postRepository.save(post);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public void addLike(String id) {
		Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		post.addLike();
		postRepository.save(post);
	}

	@Override
	public PostDto addComment(String id, String author, NewCommentDto newCommentDto) {
		Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException(id));
		post.addComment(new Comment(author, newCommentDto.getMessage()));
		postRepository.save(post);
		return modelMapper.map(post, PostDto.class);
	}

	@Override
	public Iterable<PostDto> findPostsByAuthor(String author) {
//	My code. Method findByAuthorIgnoreCase(author) return PostDto:		
//		return postRepository.findByAuthorIgnoreCase(author);
		
//	Code from Edd. Return Post:		
		return postRepository.findByAuthorIgnoreCase(author)
				.map(p -> modelMapper.map(p, PostDto.class))
				.collect(Collectors.toList());
	}

	@Override
	public Iterable<PostDto> findPostsByTags(List<String> tags) {
//	My code. Method findByTagsInIgnoreCase(tags) return PostDto:		
//		return postRepository.findByTagsInIgnoreCase(tags);

//		Code from Edd. Return Post:
		return postRepository.findByTagsInIgnoreCase(tags)
				.map(p -> modelMapper.map(p, PostDto.class))
				.collect(Collectors.toList());
	}

	@Override
		
		public Iterable<PostDto> findPostsByDates(DatePeriodDto datePeriodDto) {
// My code. Method findPostsByPeriod(LocalDateTime dateFrom, LocalDateTime dateTo) return PostDto:	
//		LocalDateTime dateFrom = LocalDateTime.of(datePeriodDto.getDateFrom(), LocalTime.MIN);
//		LocalDateTime dateTo = LocalDateTime.of(datePeriodDto.getDateTo(), LocalTime.MAX);
//		return postRepository.findPostsByPeriod(dateFrom, dateTo);
//	}
	
// Edd's code:
		return postRepository.findByDateCreatedBetween(datePeriodDto.getDateFrom(), datePeriodDto.getDateTo())
				.map(p -> modelMapper.map(p, PostDto.class))
				.collect(Collectors.toList());	
	}
	
}
