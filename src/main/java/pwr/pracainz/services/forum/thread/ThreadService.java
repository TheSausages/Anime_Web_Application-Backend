package pwr.pracainz.services.forum.thread;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pwr.pracainz.DTO.PageDTO;
import pwr.pracainz.DTO.forum.ForumQuery;
import pwr.pracainz.DTO.forum.Thread.CompleteThreadDTO;
import pwr.pracainz.DTO.forum.Thread.CreateThreadDTO;
import pwr.pracainz.DTO.forum.Thread.SimpleThreadDTO;
import pwr.pracainz.DTO.forum.Thread.UpdateThreadDTO;
import pwr.pracainz.entities.databaseerntities.forum.Tag;
import pwr.pracainz.entities.databaseerntities.forum.Thread;
import pwr.pracainz.entities.databaseerntities.user.User;
import pwr.pracainz.exceptions.exceptions.AuthenticationException;
import pwr.pracainz.exceptions.exceptions.ObjectNotFoundException;
import pwr.pracainz.repositories.forum.ThreadRepository;
import pwr.pracainz.services.DTOOperations.Conversion.DTOConversionInterface;
import pwr.pracainz.services.DTOOperations.Deconversion.DTODeconversionInterface;
import pwr.pracainz.services.forum.category.ForumCategoryServiceInterface;
import pwr.pracainz.services.forum.post.PostServiceInterface;
import pwr.pracainz.services.forum.tag.TagServiceInterface;
import pwr.pracainz.services.i18n.I18nServiceInterface;
import pwr.pracainz.services.user.UserServiceInterface;
import pwr.pracainz.utils.UserAuthorizationUtilities;

import java.util.List;
import java.util.stream.Collectors;

@Log4j2
@Service
public class ThreadService implements ThreadServiceInterface {
	private final ThreadRepository threadRepository;
	private final TagServiceInterface tagService;
	private final ForumCategoryServiceInterface forumCategoryService;
	private final PostServiceInterface postService;
	private final UserServiceInterface userService;
	private final DTOConversionInterface dtoConversion;
	private final DTODeconversionInterface dtoDeconversion;
	private final I18nServiceInterface i18nService;

	@Autowired
	ThreadService(ThreadRepository threadRepository,
	              TagServiceInterface tagService,
	              ForumCategoryServiceInterface forumCategoryService,
	              PostServiceInterface postService,
	              UserServiceInterface userService,
	              I18nServiceInterface i18nService,
	              DTOConversionInterface dtoConversion,
	              DTODeconversionInterface dtoDeconversion) {
		this.threadRepository = threadRepository;
		this.tagService = tagService;
		this.forumCategoryService = forumCategoryService;
		this.postService = postService;
		this.userService = userService;
		this.i18nService = i18nService;
		this.dtoConversion = dtoConversion;
		this.dtoDeconversion = dtoDeconversion;
	}

	@Override
	public PageDTO<SimpleThreadDTO> getNewestThreads(int pageNumber) {
		log.info("Get newest threads - page: {}", pageNumber);

		return dtoConversion.convertToDTO(
				threadRepository.findAll(PageRequest.of(pageNumber, 30, Sort.by("creation").descending())).map(dtoConversion::convertToSimpleDTO)
		);
	}

	@Override
	public PageDTO<SimpleThreadDTO> searchThreads(int pageNumber, ForumQuery forumQuery) {
		log.info("Get all threads that meet criteria: {},\n page: {}", forumQuery, pageNumber);

		List<Tag> tags = forumQuery.getTags().stream()
				.map(tagDto -> tagService.findTagByIdAndName(tagDto.getTagId(), tagDto.getTagName()))
				.collect(Collectors.toList());

		return dtoConversion.convertToDTO(
				threadRepository.findAllByForumQuery(
						forumQuery.getMinCreation(),
						forumQuery.getMaxCreation(),
						forumQuery.getMinModification(),
						forumQuery.getMaxModification(),
						forumQuery.getMinNrOfPosts(),
						forumQuery.getMaxNrOfPosts(),
						forumQuery.getTitle(),
						forumQuery.getCreatorUsername(),
						forumQuery.getCategory(),
						forumQuery.getStatus(),
						tags,
						PageRequest.of(pageNumber, 30, Sort.by("creation").descending())).map(dtoConversion::convertToSimpleDTO)
		);
	}

	@Override
	public CompleteThreadDTO getThreadById(int id) {
		Thread thread = getNonDTOThreadById(id);

		return dtoConversion.convertToDTO(
				thread, postService.findPostsByThread(0, thread.getThreadId())
		);
	}

	@Override
	public Thread getNonDTOThreadById(int id) {
		log.info("Get thread with id: {}", id);

		return threadRepository.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException(i18nService.getTranslation("forum.no-such-thread", id),
						String.format("No thread with id %s found", id)));
	}

	@Override
	public SimpleThreadDTO createThread(CreateThreadDTO newThread) {
		if (!UserAuthorizationUtilities.checkIfLoggedUser()) {
			throw new AuthenticationException(i18nService.getTranslation("authentication.not-logged-in"),
					"User tried to create Thread without being logged in");
		}

		log.info("Create thread with title '{}' by user {}", newThread.getTitle(), userService.getUsernameOfCurrentUser());

		Thread thread = dtoDeconversion.convertFromDTO(newThread);
		thread.setCategory(forumCategoryService.findCategoryByIdAndName(newThread.getCategory().getCategoryId(), newThread.getCategory().getCategoryName()));
		thread.setTags(newThread.getTags().stream().map(tagDto -> tagService.findTagByIdAndName(tagDto.getTagId(), tagDto.getTagName())).collect(Collectors.toList()));
		thread.setCreator(userService.getCurrentUserOrInsert());

		return dtoConversion.convertToSimpleDTO(threadRepository.save(thread));
	}

	@Override
	public CompleteThreadDTO updateThread(int threadId, UpdateThreadDTO thread) {
		if (!UserAuthorizationUtilities.checkIfLoggedUser()) {
			throw new AuthenticationException(i18nService.getTranslation("authentication.not-logged-in"),
					"User tried to update Thread without being logged in");
		}

		User currUser = userService.getCurrentUser();
		Thread oldThread = threadRepository.findById(threadId)
				.orElseThrow(() -> new ObjectNotFoundException(i18nService.getTranslation("forum.no-such-thread", threadId),
						String.format("No thread with id %s found", threadId)));

		if (!currUser.equals(oldThread.getCreator())) {
			throw new AuthenticationException(i18nService.getTranslation("forum.no-updating-others-thread"),
					String.format("User %s tried to update post of user %s (post id: %s)", currUser.getUsername(), oldThread.getCreator().getUsername(), oldThread.getCreator().getUserId()));
		}

		log.info("Update thread {}(id: {}), created by user {}", oldThread.getTitle(), oldThread.getThreadId(), currUser.getUsername());

		oldThread.setTitle(thread.getTitle());
		oldThread.setThreadText(thread.getText());
		oldThread.setStatus(thread.getStatus());
		oldThread.setCategory(forumCategoryService.findCategoryByIdAndName(thread.getCategory().getCategoryId(), thread.getCategory().getCategoryName()));
		oldThread.setTags(thread.getTags().stream().map(tagDto -> tagService.findTagByIdAndName(tagDto.getTagId(), tagDto.getTagName())).collect(Collectors.toList()));

		return dtoConversion.convertToDTO(threadRepository.save(oldThread),
				postService.findPostsByThread(0, thread.getThreadId()));
	}
}
