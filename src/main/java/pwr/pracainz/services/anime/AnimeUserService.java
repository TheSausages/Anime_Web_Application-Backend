package pwr.pracainz.services.anime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoDTO;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfo;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfoId;
import pwr.pracainz.entities.databaseerntities.user.User;
import pwr.pracainz.exceptions.exceptions.AuthenticationException;
import pwr.pracainz.repositories.animeInfo.AnimeUserInfoRepository;
import pwr.pracainz.repositories.animeInfo.ReviewRepository;
import pwr.pracainz.services.DTOConvension.DTOConversionInterface;
import pwr.pracainz.services.DTOConvension.DTODeconversionInterface;
import pwr.pracainz.services.user.UserServiceInterface;

@Service
public class AnimeUserService implements AnimeUserServiceInterface {
    private final DTOConversionInterface dtoConversion;
    private final DTODeconversionInterface dtoDeconversion;
    private final UserServiceInterface userService;
    private final AnimeUserInfoRepository animeUserInfoRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    AnimeUserService(DTOConversionInterface dtoConversion, DTODeconversionInterface dtoDeconversion, UserServiceInterface userService, AnimeUserInfoRepository animeUserInfoRepository, ReviewRepository reviewRepository) {
        this.dtoConversion = dtoConversion;
        this.dtoDeconversion = dtoDeconversion;
        this.userService = userService;
        this.animeUserInfoRepository = animeUserInfoRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public AnimeUserInfoDTO getCurrentUserAnimeInfo(int animeId) {
        AnimeUserInfoId animeUserInfoId = new AnimeUserInfoId(userService.getCurrentUser(), animeId);

        return animeUserInfoRepository
                .findById(animeUserInfoId)
                .map(dtoConversion::convertAnimeUserInfoToDTO)
                .orElse(dtoConversion.convertAnimeUserInfoToDTO(AnimeUserInfo.getEmptyAnimeUserInfo(animeUserInfoId)));
    }

    @Override
    public AnimeUserInfoDTO updateCurrentUserAnimeInfo(AnimeUserInfoDTO animeUserInfoDTO) {
        User currUser = userService.getCurrentUser();

        if (!animeUserInfoDTO.getId().getUser().getUserId().equals(currUser.getUserId())) {
            throw new AuthenticationException("User Anime Information was not successful - please try again");
        }

        AnimeUserInfoId animeUserInfoId = new AnimeUserInfoId(currUser, animeUserInfoDTO.getId().getAnimeId());

        AnimeUserInfo updatedAnimeUserInfo = animeUserInfoRepository.findById(animeUserInfoId)
                .map(animeUserInfo -> animeUserInfo.copyDataFromDTO(animeUserInfoDTO))
                .orElse(dtoDeconversion.convertFromDTO(animeUserInfoDTO, animeUserInfoId));

        return dtoConversion.convertAnimeUserInfoToDTO(animeUserInfoRepository.save(updatedAnimeUserInfo));
    }
}
