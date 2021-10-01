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
import pwr.pracainz.services.user.UserServiceInterface;
import pwr.pracainz.utils.UserAuthorizationUtilities;

import java.util.stream.Collectors;

import static pwr.pracainz.utils.UserAuthorizationUtilities.checkIfLoggedUser;

@Log4j2
@Service
public class ThreadService implements ThreadServiceInterface {
    private final ThreadRepository threadRepository;
    private final TagServiceInterface tagService;
    private final ForumCategoryServiceInterface forumCategoryService;
    private final PostServiceInterface postService;
    private final UserServiceInterface userService;
    private final DTOConversionInterface<SimpleThreadDTO> dtoConversion;
    private final DTODeconversionInterface dtoDeconversion;

    @Autowired
    ThreadService(ThreadRepository threadRepository,
                  TagServiceInterface tagService,
                  ForumCategoryServiceInterface forumCategoryService,
                  PostServiceInterface postService,
                  UserServiceInterface userService,
                  DTOConversionInterface<SimpleThreadDTO> dtoConversion,
                  DTODeconversionInterface dtoDeconversion) {
        this.threadRepository = threadRepository;
        this.tagService = tagService;
        this.forumCategoryService = forumCategoryService;
        this.postService = postService;
        this.userService = userService;
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
        log.info("Get all threads with meet criteria: {}, page: {}", forumQuery, pageNumber);

        return dtoConversion.convertToDTO(
                threadRepository.getAllByCategory(forumQuery.getCategory(), PageRequest.of(pageNumber, 30, Sort.by("creation").descending())).map(dtoConversion::convertToSimpleDTO)
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
                .orElseThrow(() -> new ObjectNotFoundException("Could not find thread with id: " + id));
    }

    @Override
    public SimpleThreadDTO createThread(CreateThreadDTO newThread) {
        if (!checkIfLoggedUser()) {
            throw new AuthenticationException("You are not logged in!");
        }

        log.info("Create thread with title '{}' by user with id {}", newThread.getTitle(), UserAuthorizationUtilities.getIdOfCurrentUser());

        Thread thread = dtoDeconversion.convertFromDTO(newThread);
        thread.setCategory(forumCategoryService.findCategoryByIdAndName(newThread.getCategory().getCategoryId(), newThread.getCategory().getCategoryName()));
        thread.setTags(newThread.getTags().stream().map(tagDto -> tagService.findTagByIdAndName(tagDto.getTagId(), tagDto.getTagName())).collect(Collectors.toList()));
        thread.setCreator(userService.getCurrentUserOrInsert());

        return dtoConversion.convertToSimpleDTO(threadRepository.save(thread));
    }

    @Override
    public SimpleThreadDTO updateThread(int threadId, UpdateThreadDTO thread) {
        if (!checkIfLoggedUser()) {
            throw new AuthenticationException("You are not logged in!");
        }

        User currUser = userService.getCurrentUser();
        Thread oldThread = threadRepository.findById(threadId)
                .orElseThrow(() -> new ObjectNotFoundException("No thread with id " + threadId + " found!"));

        if (!currUser.equals(oldThread.getCreator())) {
            throw new AuthenticationException("You can't update another persons thread!");
        }

        log.info("Update thread {}(id: {}), created by user {}", oldThread.getTitle(), oldThread.getThreadId(), currUser.getUsername());

        oldThread.setTitle(thread.getTitle());
        oldThread.setThreadText(thread.getText());
        oldThread.setStatus(thread.getStatus());
        oldThread.setCategory(forumCategoryService.findCategoryByIdAndName(thread.getCategory().getCategoryId(), thread.getCategory().getCategoryName()));
        oldThread.setTags(thread.getTags().stream().map(tagDto -> tagService.findTagByIdAndName(tagDto.getTagId(), tagDto.getTagName())).collect(Collectors.toList()));

        return dtoConversion.convertToSimpleDTO(threadRepository.save(oldThread));
    }
}
