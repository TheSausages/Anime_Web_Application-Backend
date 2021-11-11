package pwr.pracainz.services.forum.thread;

import pwr.pracainz.DTO.PageDTO;
import pwr.pracainz.DTO.forum.ForumQuery;
import pwr.pracainz.DTO.forum.Thread.CompleteThreadDTO;
import pwr.pracainz.DTO.forum.Thread.CreateThreadDTO;
import pwr.pracainz.DTO.forum.Thread.SimpleThreadDTO;
import pwr.pracainz.DTO.forum.Thread.UpdateThreadDTO;
import pwr.pracainz.DTO.forum.ThreadUserStatusDTO;
import pwr.pracainz.entities.databaseerntities.forum.Thread;

/**
 * Interface for a Thread Service. Each implementation must use this interface.
 */
public interface ThreadServiceInterface {
	/**
	 * Get a Page from the list of the newest threads.
	 * @param pageNumber Which page from the list should be returned
	 * @return Page of threads
	 */
	PageDTO<SimpleThreadDTO> getNewestThreads(int pageNumber);

	/**
	 * Get a page from the threads that meet the requirements from the {@link ForumQuery} query.
	 * @param pageNumber Which page of threads should be returned
	 * @param forumQuery Query used for requirements
	 * @return Page of threads that met the requirements
	 */
	PageDTO<SimpleThreadDTO> searchThreads(int pageNumber, ForumQuery forumQuery);

	/**
	 * Variant of {@link #getNonDTOThreadById(int)} that converts the thead to {@link CompleteThreadDTO}
	 * @param id Id used to search the thread
	 * @return Detailed thread information
	 */
	CompleteThreadDTO getThreadById(int id);

	/**
	 * Get threads by its Id as a non-DTO {@link Thread} object.
	 * @param id Id used to search the thread
	 * @return Detailed thread information
	 */
	Thread getNonDTOThreadById(int id);

	/**
	 * Create a new thread.
	 * @param thread Data of the thread to be created
	 * @return Basic information about the newly created thread
	 */
	SimpleThreadDTO createThread(CreateThreadDTO thread);

	/**
	 * Update a given thread.
	 * @param threadId Id of the thread that should be updated
	 * @param thread Data which should be used to update the thread
	 * @return Detailed information on the updated Thread
	 */
	CompleteThreadDTO updateThread(int threadId, UpdateThreadDTO thread);

	/**
	 * Update thread user status for a given thread for a user.
	 * @param threadId Id of the thread for which the status should be updated
	 * @param status Status to be used for the update
	 * @return The updated status
	 */
	ThreadUserStatusDTO updateThreadUserStatus(int threadId, ThreadUserStatusDTO status);
}
