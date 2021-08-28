package pwr.pracainz.services.anime;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoDTO;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoIdDTO;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfo;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfoId;
import pwr.pracainz.entities.databaseerntities.user.User;
import pwr.pracainz.exceptions.exceptions.AuthenticationException;
import pwr.pracainz.repositories.animeInfo.AnimeUserInfoRepository;
import pwr.pracainz.repositories.animeInfo.ReviewRepository;
import pwr.pracainz.services.DTOConvension.DTOConversionInterface;
import pwr.pracainz.services.DTOConvension.DTODeconversionInterface;
import pwr.pracainz.services.user.UserServiceInterface;

import java.util.Objects;

@Log4j2
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
        AnimeUserInfoId animeUserInfoId = new AnimeUserInfoId(userService.getCurrentUserOrInsert(), animeId);

        return animeUserInfoRepository
                .findById(animeUserInfoId)
                .map(dtoConversion::convertAnimeUserInfoToDTO)
                .orElse(dtoConversion.convertAnimeUserInfoToDTO(AnimeUserInfo.getEmptyAnimeUserInfo(animeUserInfoId)));
    }

    @Override
    public AnimeUserInfoDTO updateCurrentUserAnimeInfo(AnimeUserInfoDTO animeUserInfoDTO) {
        User currUser = userService.getCurrentUserOrInsert();
        AnimeUserInfoIdDTO requestUserId = animeUserInfoDTO.getId();

        if (Objects.nonNull(requestUserId) && !currUser.getUserId().equals(requestUserId.getUser().getUserId())) {
            throw new AuthenticationException("Updating your anime information was not successful - please try again");
        }

        log.info("Update Anime data for User: {}", currUser.getUserId());

        AnimeUserInfoId animeUserInfoId = new AnimeUserInfoId(currUser, animeUserInfoDTO.getId().getAnimeId());

        AnimeUserInfo updatedAnimeUserInfo = animeUserInfoRepository.findById(animeUserInfoId)
                .map(animeUserInfo -> animeUserInfo.copyDataFromDTO(animeUserInfoDTO))
                .orElse(dtoDeconversion.convertFromDTO(animeUserInfoDTO, animeUserInfoId));

        return dtoConversion.convertAnimeUserInfoToDTO(animeUserInfoRepository.save(updatedAnimeUserInfo));
    }
}
