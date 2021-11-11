package pwr.pracainz.services.DTOOperations.Deconversion;

import pwr.pracainz.DTO.animeInfo.AnimeUserInfoDTO;
import pwr.pracainz.DTO.forum.Post.CreatePostDTO;
import pwr.pracainz.DTO.forum.PostUserStatusDTO;
import pwr.pracainz.DTO.forum.Thread.CreateThreadDTO;
import pwr.pracainz.DTO.forum.ThreadUserStatusDTO;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfo;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfoId;
import pwr.pracainz.entities.databaseerntities.forum.Thread;
import pwr.pracainz.entities.databaseerntities.forum.*;

/**
 * Interface for a DTO Deconversion Service. Each implementation must use this interface.
 */
public interface DTODeconversionInterface {
	/**
	 * Deconvert a {@link AnimeUserInfoDTO} to {@link AnimeUserInfo} with the Id added.
	 * @param animeUserInfoDTO DTO to be deconverted
	 * @param animeUserInfoId Id to be added to the new Info object
	 * @return A new Info object with the Id added.
	 */
	AnimeUserInfo convertFromDTO(AnimeUserInfoDTO animeUserInfoDTO, AnimeUserInfoId animeUserInfoId);

	/**
	 * Deconvert a {@link PostUserStatusDTO} to {@link PostUserStatus} with the Id added.
	 * @param postUserStatusDTO DTO to be deconverted
	 * @param postUserStatusId Id to be added to the new Status object
	 * @return A new Status object with the Id added
	 */
	PostUserStatus convertFromDTO(PostUserStatusDTO postUserStatusDTO, PostUserStatusId postUserStatusId);

	/**
	 * Deconvert a {@link ThreadUserStatusDTO} to {@link ThreadUserStatus} with the Id added.
	 * @param threadUserStatusDTO DTO to be deconverted
	 * @param threadUserStatusId Id to be added to the new Status object
	 * @return A new Status object with the Id added
	 */
	ThreadUserStatus convertFromDTO(ThreadUserStatusDTO threadUserStatusDTO, ThreadUserStatusId threadUserStatusId);

	/**
	 * Deconvert a {@link CreatePostDTO} to a new {@link Post} object.
	 * @param postDTO DTO to be deconverted
	 * @return A new Post object with the Id added
	 */
	Post convertFromDTO(CreatePostDTO postDTO);

	/**
	 * Deconvert a {@link CreateThreadDTO} to a new {@link Thread} object.
	 * @param threadDTO DTO to be deconverted
	 * @return A new Post object with the Id added
	 */
	Thread convertFromDTO(CreateThreadDTO threadDTO);
}
