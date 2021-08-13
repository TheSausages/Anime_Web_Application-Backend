package pwr.pracainz.services.anime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pwr.pracainz.DTO.animeInfo.AnimeUserInfoDTO;
import pwr.pracainz.entities.databaseerntities.animeInfo.AnimeUserInfoId;
import pwr.pracainz.repositories.animeInfo.AnimeUserInfoRepository;
import pwr.pracainz.repositories.animeInfo.GradeRepository;
import pwr.pracainz.repositories.animeInfo.ReviewRepository;
import pwr.pracainz.services.DTOConvension.DTOConversionInterface;
import pwr.pracainz.services.user.UserServiceInterface;

@Service
public class AnimeUserService implements AnimeUserServiceInterface {
    private final DTOConversionInterface dtoConversion;
    private final UserServiceInterface userService;
    private final AnimeUserInfoRepository animeUserInfoRepository;
    private final GradeRepository gradeRepository;
    private final ReviewRepository reviewRepository;

    @Autowired
    AnimeUserService(DTOConversionInterface dtoConversion, UserServiceInterface userService, AnimeUserInfoRepository animeUserInfoRepository, GradeRepository gradeRepository, ReviewRepository reviewRepository) {
        this.dtoConversion = dtoConversion;
        this.userService = userService;
        this.animeUserInfoRepository = animeUserInfoRepository;
        this.gradeRepository = gradeRepository;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public AnimeUserInfoDTO getCurrentUserAnimeInfo(int animeId) {
        AnimeUserInfoId animeUserInfoId = new AnimeUserInfoId(userService.getCurrentUser(), animeId);

        return animeUserInfoRepository
                .findById(animeUserInfoId)
                .map(dtoConversion::convertAnimeUserInfoToDTO)
                .orElse(null);
    }
}
