package pwr.pracainz.entities.anime.query.queryElements.Media;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import pwr.pracainz.entities.anime.query.parameters.FieldParameters;
import pwr.pracainz.entities.anime.query.parameters.enums.Genre;
import pwr.pracainz.entities.anime.query.parameters.enums.Tags;
import pwr.pracainz.entities.anime.query.parameters.fuzzyDate.FuzzyDateField;
import pwr.pracainz.entities.anime.query.parameters.fuzzyDate.FuzzyDateFieldParameter;
import pwr.pracainz.entities.anime.query.parameters.fuzzyDate.FuzzyDateValue;
import pwr.pracainz.entities.anime.query.parameters.media.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MediaTest {

    @Test
    void MediaBuilder_Id_NoException() {
        //given
        int id = 256545;
        FuzzyDateField dateField = FuzzyDateField.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.startDate)
                .allAndBuild();
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .fuzzyDate(dateField)
                .buildField();

        //when
        Media media = Media.getMediaBuilder(field)
                .id(id)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("id", id);

        assertEquals(media.getElementString(), "Media(id: $id) {\nid\nstartDate {\nyear\nmonth\nday\n}\n}");
        assertEquals(media.getQueryParameters().toString(), "[$id: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_ManyId_NoException() {
        //given
        int id = 256545;
        int id1 = 123456;
        FuzzyDateField dateField = FuzzyDateField.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.startDate)
                .allAndBuild();
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .fuzzyDate(dateField)
                .buildField();

        //when
        Media media = Media.getMediaBuilder(field)
                .id(id)
                .id(id1)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("id", id);

        assertEquals(media.getElementString(), "Media(id: $id) {\nid\nstartDate {\nyear\nmonth\nday\n}\n}");
        assertEquals(media.getQueryParameters().toString(), "[$id: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_IdMal_NoException() {
        //given
        int idMal = 24562;
        FuzzyDateField dateField = FuzzyDateField.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.startDate)
                .allAndBuild();
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .fuzzyDate(dateField)
                .buildField();

        //when
        Media media = Media.getMediaBuilder(field)
                .idMal(idMal)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("idMal", idMal);

        assertEquals(media.getElementString(), "Media(idMal: $idMal) {\nid\nstartDate {\nyear\nmonth\nday\n}\n}");
        assertEquals(media.getQueryParameters().toString(), "[$idMal: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_ManyIdMal_NoException() {
        //given
        int idMal = 24562;
        int idMal1 = 78965;
        int idMal2 = 125424562;
        FuzzyDateField dateField = FuzzyDateField.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.startDate)
                .allAndBuild();
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .fuzzyDate(dateField)
                .buildField();

        //when
        Media media = Media.getMediaBuilder(field)
                .idMal(idMal)
                .idMal(idMal1)
                .idMal(idMal2)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("idMal", idMal);

        assertEquals(media.getElementString(), "Media(idMal: $idMal) {\nid\nstartDate {\nyear\nmonth\nday\n}\n}");
        assertEquals(media.getQueryParameters().toString(), "[$idMal: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_StartDate_NoException() {
        //given
        FuzzyDateField dateField = FuzzyDateField.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.startDate)
                .allAndBuild();
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .fuzzyDate(dateField)
                .buildField();
        FuzzyDateValue fuzzyDateValue = FuzzyDateValue.getFuzzyDateValueBuilder()
                .nowAndBuild();

        //when
        Media media = Media.getMediaBuilder(field)
                .startDate(fuzzyDateValue)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("startDate", fuzzyDateValue.getFuzzyDateNumber());

        assertEquals(media.getElementString(), "Media(startDate: $startDate) {\nid\nstartDate {\nyear\nmonth\nday\n}\n}");
        assertEquals(media.getQueryParameters().toString(), "[$startDate: FuzzyDateInt, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_ManyStartDate_NoException() {
        //given
        FuzzyDateField dateField = FuzzyDateField.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.startDate)
                .allAndBuild();
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .fuzzyDate(dateField)
                .buildField();
        FuzzyDateValue fuzzyDateValue = FuzzyDateValue.getFuzzyDateValueBuilder()
                .nowAndBuild();
        FuzzyDateValue fuzzyDateValue1 = FuzzyDateValue.getFuzzyDateValueBuilder()
                .currentYear()
                .nowAndBuild();

        //when
        Media media = Media.getMediaBuilder(field)
                .startDate(fuzzyDateValue)
                .startDate(fuzzyDateValue1)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("startDate", fuzzyDateValue.getFuzzyDateNumber());

        assertEquals(media.getElementString(), "Media(startDate: $startDate) {\nid\nstartDate {\nyear\nmonth\nday\n}\n}");
        assertEquals(media.getQueryParameters().toString(), "[$startDate: FuzzyDateInt, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_EndDate_NoException() {
        //given
        FuzzyDateField dateField = FuzzyDateField.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.startDate)
                .allAndBuild();
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .fuzzyDate(dateField)
                .buildField();
        FuzzyDateValue fuzzyDateValue = FuzzyDateValue.getFuzzyDateValueBuilder()
                .nowAndBuild();

        //when
        Media media = Media.getMediaBuilder(field)
                .endDate(fuzzyDateValue)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("endDate", fuzzyDateValue.getFuzzyDateNumber());

        assertEquals(media.getElementString(), "Media(endDate: $endDate) {\nid\nstartDate {\nyear\nmonth\nday\n}\n}");
        assertEquals(media.getQueryParameters().toString(), "[$endDate: FuzzyDateInt, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_ManyEndDate_NoException() {
        //given
        FuzzyDateField dateField = FuzzyDateField.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.startDate)
                .allAndBuild();
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .fuzzyDate(dateField)
                .buildField();
        FuzzyDateValue fuzzyDateValue = FuzzyDateValue.getFuzzyDateValueBuilder()
                .nowAndBuild();
        FuzzyDateValue fuzzyDateValue1 = FuzzyDateValue.getFuzzyDateValueBuilder()
                .currentYear()
                .buildFuzzyDateValue();

        //when
        Media media = Media.getMediaBuilder(field)
                .endDate(fuzzyDateValue)
                .endDate(fuzzyDateValue1)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("endDate", fuzzyDateValue.getFuzzyDateNumber());

        assertEquals(media.getElementString(), "Media(endDate: $endDate) {\nid\nstartDate {\nyear\nmonth\nday\n}\n}");
        assertEquals(media.getQueryParameters().toString(), "[$endDate: FuzzyDateInt, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }


    @Test
    void MediaBuilder_Season_NoException() {
        //given
        FuzzyDateField dateField = FuzzyDateField.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.startDate)
                .allAndBuild();
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .fuzzyDate(dateField)
                .buildField();
        MediaSeason season = MediaSeason.SPRING;

        //when
        Media media = Media.getMediaBuilder(field)
                .season(season)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("season", season);

        assertEquals(media.getElementString(), "Media(season: $season) {\nid\nstartDate {\nyear\nmonth\nday\n}\n}");
        assertEquals(media.getQueryParameters().toString(), "[$season: MediaSeason, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_ManySeason_NoException() {
        //given
        FuzzyDateField dateField = FuzzyDateField.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.startDate)
                .allAndBuild();
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .fuzzyDate(dateField)
                .buildField();
        MediaSeason season = MediaSeason.SPRING;
        MediaSeason season1 = MediaSeason.WINTER;

        //when
        Media media = Media.getMediaBuilder(field)
                .season(season)
                .season(season1)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("season", season);

        assertEquals(media.getElementString(), "Media(season: $season) {\nid\nstartDate {\nyear\nmonth\nday\n}\n}");
        assertEquals(media.getQueryParameters().toString(), "[$season: MediaSeason, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_SeasonYear_NoException() {
        //given
        FuzzyDateField dateField = FuzzyDateField.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.startDate)
                .allAndBuild();
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .fuzzyDate(dateField)
                .buildField();
        int seasonYear = 2020;

        //when
        Media media = Media.getMediaBuilder(field)
                .seasonYear(seasonYear)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("seasonYear", seasonYear);

        assertEquals(media.getElementString(), "Media(seasonYear: $seasonYear) {\nid\nstartDate {\nyear\nmonth\nday\n}\n}");
        assertEquals(media.getQueryParameters().toString(), "[$seasonYear: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_ManySeasonYear_NoException() {
        //given
        FuzzyDateField dateField = FuzzyDateField.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.startDate)
                .allAndBuild();
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .fuzzyDate(dateField)
                .buildField();
        int seasonYear1 = 2020;
        int seasonYear = 2021;

        //when
        Media media = Media.getMediaBuilder(field)
                .seasonYear(seasonYear)
                .seasonYear(seasonYear1)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("seasonYear", seasonYear);

        assertEquals(media.getElementString(), "Media(seasonYear: $seasonYear) {\nid\nstartDate {\nyear\nmonth\nday\n}\n}");
        assertEquals(media.getQueryParameters().toString(), "[$seasonYear: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_Type_NoException() {
        //given
        FuzzyDateField dateField = FuzzyDateField.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.startDate)
                .allAndBuild();
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .fuzzyDate(dateField)
                .buildField();
        MediaType type = MediaType.ANIME;

        //when
        Media media = Media.getMediaBuilder(field)
                .type(type)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("type", type);

        assertEquals(media.getElementString(), "Media(type: $type) {\nid\nstartDate {\nyear\nmonth\nday\n}\n}");
        assertEquals(media.getQueryParameters().toString(), "[$type: MediaType, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_ManyType_NoException() {
        //given
        FuzzyDateField dateField = FuzzyDateField.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.startDate)
                .allAndBuild();
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .fuzzyDate(dateField)
                .buildField();
        MediaType type = MediaType.MANGA;
        MediaType type1 = MediaType.ANIME;

        //when
        Media media = Media.getMediaBuilder(field)
                .type(type)
                .type(type1)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("type", type);

        assertEquals(media.getElementString(), "Media(type: $type) {\nid\nstartDate {\nyear\nmonth\nday\n}\n}");
        assertEquals(media.getQueryParameters().toString(), "[$type: MediaType, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_Format_NoException() {
        //given
        FuzzyDateField dateField = FuzzyDateField.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.startDate)
                .allAndBuild();
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .fuzzyDate(dateField)
                .buildField();
        MediaFormat mediaFormat = MediaFormat.MANGA;

        //when
        Media media = Media.getMediaBuilder(field)
                .format(mediaFormat)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("format", mediaFormat);

        assertEquals(media.getElementString(), "Media(format: $format) {\nid\nstartDate {\nyear\nmonth\nday\n}\n}");
        assertEquals(media.getQueryParameters().toString(), "[$format: MediaFormat, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_ManyFormat_NoException() {
        //given
        FuzzyDateField dateField = FuzzyDateField.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.startDate)
                .allAndBuild();
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .fuzzyDate(dateField)
                .buildField();
        MediaFormat mediaFormat1 = MediaFormat.MANGA;
        MediaFormat mediaFormat = MediaFormat.MUSIC;

        //when
        Media media = Media.getMediaBuilder(field)
                .format(mediaFormat)
                .format(mediaFormat1)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("format", mediaFormat);

        assertEquals(media.getElementString(), "Media(format: $format) {\nid\nstartDate {\nyear\nmonth\nday\n}\n}");
        assertEquals(media.getQueryParameters().toString(), "[$format: MediaFormat, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_Status_NoException() {
        //given
        FuzzyDateField dateField = FuzzyDateField.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.startDate)
                .allAndBuild();
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .fuzzyDate(dateField)
                .buildField();
        MediaStatus status = MediaStatus.FINISHED;

        //when
        Media media = Media.getMediaBuilder(field)
                .status(status)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("status", status);

        assertEquals(media.getElementString(), "Media(status: $status) {\nid\nstartDate {\nyear\nmonth\nday\n}\n}");
        assertEquals(media.getQueryParameters().toString(), "[$status: MediaStatus, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_ManyStatus_NoException() {
        //given
        FuzzyDateField dateField = FuzzyDateField.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.startDate)
                .allAndBuild();
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .fuzzyDate(dateField)
                .buildField();
        MediaStatus status1 = MediaStatus.FINISHED;
        MediaStatus status = MediaStatus.HIATUS;

        //when
        Media media = Media.getMediaBuilder(field)
                .status(status)
                .status(status1)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("status", status);

        assertEquals(media.getElementString(), "Media(status: $status) {\nid\nstartDate {\nyear\nmonth\nday\n}\n}");
        assertEquals(media.getQueryParameters().toString(), "[$status: MediaStatus, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }



    @Test
    void MediaBuilder_ManyEpisodes_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int episodes1 = 12;
        int episodes = 24;

        //when
        Media media = Media.getMediaBuilder(field)
                .episodes(episodes)
                .episodes(episodes1)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("episodes", episodes);

        assertEquals(media.getElementString(), "Media(episodes: $episodes) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$episodes: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_Duration_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int duration = 25;

        //when
        Media media = Media.getMediaBuilder(field)
                .duration(duration)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("duration", duration);

        assertEquals(media.getElementString(), "Media(duration: $duration) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$duration: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_ManyDuration_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int duration1 = 25;
        int duration = 52;

        //when
        Media media = Media.getMediaBuilder(field)
                .duration(duration)
                .duration(duration1)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("duration", duration);

        assertEquals(media.getElementString(), "Media(duration: $duration) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$duration: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_Chapters_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int chapters = 25;

        //when
        Media media = Media.getMediaBuilder(field)
                .chapters(chapters)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("chapters", chapters);

        assertEquals(media.getElementString(), "Media(chapters: $chapters) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$chapters: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_ManyChapters_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int chapters1 = 25;
        int chapters = 52;

        //when
        Media media = Media.getMediaBuilder(field)
                .chapters(chapters)
                .chapters(chapters1)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("chapters", chapters);

        assertEquals(media.getElementString(), "Media(chapters: $chapters) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$chapters: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_Volumes_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int volumes = 25;

        //when
        Media media = Media.getMediaBuilder(field)
                .volumes(volumes)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("volumes", volumes);

        assertEquals(media.getElementString(), "Media(volumes: $volumes) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$volumes: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_ManyVolumes_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int volumes1 = 25;
        int volumes = 52;

        //when
        Media media = Media.getMediaBuilder(field)
                .volumes(volumes)
                .volumes(volumes1)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("volumes", volumes);

        assertEquals(media.getElementString(), "Media(volumes: $volumes) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$volumes: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_IsAdult_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        boolean isAdult = true;

        //when
        Media media = Media.getMediaBuilder(field)
                .isAdult(isAdult)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("isAdult", isAdult);

        assertEquals(media.getElementString(), "Media(isAdult: $isAdult) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$isAdult: Boolean, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_ManyIsAdult_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        boolean isAdult = false;
        boolean isAdult1 = true;

        //when
        Media media = Media.getMediaBuilder(field)
                .isAdult(isAdult)
                .isAdult(isAdult1)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("isAdult", isAdult);

        assertEquals(media.getElementString(), "Media(isAdult: $isAdult) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$isAdult: Boolean, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_Genre_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        Genre genre = Genre.MECHA;

        //when
        Media media = Media.getMediaBuilder(field)
                .genre(genre)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("genre", genre);

        assertEquals(media.getElementString(), "Media(genre: $genre) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$genre: String, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_ManyGenre_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        Genre genre = Genre.ACTION;
        Genre genre1 = Genre.MECHA;

        //when
        Media media = Media.getMediaBuilder(field)
                .genre(genre)
                .genre(genre1)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("genre", genre);

        assertEquals(media.getElementString(), "Media(genre: $genre) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$genre: String, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_Tag_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        Tags tag = Tags.Aliens;

        //when
        Media media = Media.getMediaBuilder(field)
                .tag(tag)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("tag", tag);

        assertEquals(media.getElementString(), "Media(tag: $tag) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$tag: String, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_ManyTag_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        Tags tag = Tags.Achromatic;
        Tags tag1 = Tags.Aliens;

        //when
        Media media = Media.getMediaBuilder(field)
                .tag(tag)
                .tag(tag1)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("tag", tag);

        assertEquals(media.getElementString(), "Media(tag: $tag) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$tag: String, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_OnList_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        boolean onList = false;

        //when
        Media media = Media.getMediaBuilder(field)
                .onList(onList)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("onList", onList);

        assertEquals(media.getElementString(), "Media(onList: $onList) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$onList: Boolean, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_ManyOnList_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        boolean onList = true;
        boolean onList1 = false;

        //when
        Media media = Media.getMediaBuilder(field)
                .onList(onList)
                .onList(onList1)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("onList", onList);

        assertEquals(media.getElementString(), "Media(onList: $onList) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$onList: Boolean, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_LicensedBy_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        String StudioA = "StudioA";

        //when
        Media media = Media.getMediaBuilder(field)
                .licensedBy(StudioA)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("licensedBy", StudioA);

        assertEquals(media.getElementString(), "Media(licensedBy: $licensedBy) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$licensedBy: String, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_ManyLicensedBy_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        String StudioA = "StudioA";
        String StudioB = "StudioB";

        //when
        Media media = Media.getMediaBuilder(field)
                .licensedBy(StudioB)
                .licensedBy(StudioA)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("licensedBy", StudioB);

        assertEquals(media.getElementString(), "Media(licensedBy: $licensedBy) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$licensedBy: String, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_AverageScore_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int averageScore = 6;

        //when
        Media media = Media.getMediaBuilder(field)
                .averageScore(averageScore)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("averageScore", averageScore);

        assertEquals(media.getElementString(), "Media(averageScore: $averageScore) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$averageScore: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_ManyAverageScore_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int averageScore = 8;
        int averageScore1 = 6;

        //when
        Media media = Media.getMediaBuilder(field)
                .averageScore(averageScore)
                .averageScore(averageScore1)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("averageScore", averageScore);

        assertEquals(media.getElementString(), "Media(averageScore: $averageScore) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$averageScore: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_Source_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        MediaSource source = MediaSource.ANIME;

        //when
        Media media = Media.getMediaBuilder(field)
                .source(source)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("source", source);

        assertEquals(media.getElementString(), "Media(source: $source) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$source: MediaSource, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_ManySource_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        MediaSource source = MediaSource.DOUJINSHI;
        MediaSource source1 = MediaSource.ANIME;

        //when
        Media media = Media.getMediaBuilder(field)
                .source(source)
                .source(source1)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("source", source);

        assertEquals(media.getElementString(), "Media(source: $source) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$source: MediaSource, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_CountryOfOrigin_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        String countryOfOrigin = "PL";

        //when
        Media media = Media.getMediaBuilder(field)
                .countryOfOrigin(countryOfOrigin)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("countryOfOrigin", countryOfOrigin);

        assertEquals(media.getElementString(), "Media(countryOfOrigin: $countryOfOrigin) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$countryOfOrigin: CountryCode, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_ManyCountryOfOrigin_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        String countryOfOrigin = "DE";
        String countryOfOrigin1 = "PL";

        //when
        Media media = Media.getMediaBuilder(field)
                .countryOfOrigin(countryOfOrigin)
                .countryOfOrigin(countryOfOrigin1)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("countryOfOrigin", countryOfOrigin);

        assertEquals(media.getElementString(), "Media(countryOfOrigin: $countryOfOrigin) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$countryOfOrigin: CountryCode, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_Popularity_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int popularity = 6;

        //when
        Media media = Media.getMediaBuilder(field)
                .popularity(popularity)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("popularity", popularity);

        assertEquals(media.getElementString(), "Media(popularity: $popularity) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$popularity: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_ManyPopularity_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int popularity = 9;
        int popularity1 = 6;

        //when
        Media media = Media.getMediaBuilder(field)
                .popularity(popularity)
                .popularity(popularity1)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("popularity", popularity);

        assertEquals(media.getElementString(), "Media(popularity: $popularity) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$popularity: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_Search_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        String search = "Fate";

        //when
        Media media = Media.getMediaBuilder(field)
                .search(search)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("search", search);

        assertEquals(media.getElementString(), "Media(search: $search) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$search: String, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_ManySearch_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        String search = "Demon Slayer";
        String search1 = "Fate";

        //when
        Media media = Media.getMediaBuilder(field)
                .search(search)
                .search(search1)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("search", search);

        assertEquals(media.getElementString(), "Media(search: $search) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$search: String, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_IdNot_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int idNot = 1233654;

        //when
        Media media = Media.getMediaBuilder(field)
                .idNot(idNot)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("id_not", idNot);

        assertEquals(media.getElementString(), "Media(id_not: $id_not) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$id_not: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_ManyIdNot_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int idNot = 1233654;
        int idNot1 = 78654;

        //when
        Media media = Media.getMediaBuilder(field)
                .idNot(idNot)
                .idNot(idNot1)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("id_not", idNot);

        assertEquals(media.getElementString(), "Media(id_not: $id_not) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$id_not: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_IdIn_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int[] idIn = new int[]{123654, 987456};

        //when
        Media media = Media.getMediaBuilder(field)
                .idIn(idIn)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("id_in", idIn);

        assertEquals(media.getElementString(), "Media(id_in: $id_in) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$id_in: [Int], ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_ManyIdIn_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int[] idIn = new int[]{897456, 285963};
        int[] idIn1 = new int[]{123654, 987456};

        //when
        Media media = Media.getMediaBuilder(field)
                .idIn(idIn)
                .idIn(idIn1)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("id_in", idIn);

        assertEquals(media.getElementString(), "Media(id_in: $id_in) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$id_in: [Int], ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_IdNotIn_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int[] idNotIn = new int[]{123654, 987456};

        //when
        Media media = Media.getMediaBuilder(field)
                .idNotIn(idNotIn)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("id_not_in", idNotIn);

        assertEquals(media.getElementString(), "Media(id_not_in: $id_not_in) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$id_not_in: [Int], ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_ManyIdNotIn_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int[] idNotIn = new int[]{123654, 987456};
        int[] idNotIn1 = new int[]{124541245, 1298745456};

        //when
        Media media = Media.getMediaBuilder(field)
                .idNotIn(idNotIn)
                .idNotIn(idNotIn1)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("id_not_in", idNotIn);

        assertEquals(media.getElementString(), "Media(id_not_in: $id_not_in) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$id_not_in: [Int], ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_IdMalNot_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int idMalNot = 1233654;

        //when
        Media media = Media.getMediaBuilder(field)
                .idMalNot(idMalNot)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("idMal_not", idMalNot);

        assertEquals(media.getElementString(), "Media(idMal_not: $idMal_not) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$idMal_not: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_ManyIdMalNot_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int idMalNot = 1233654;
        int idMalNot1 = 78654;

        //when
        Media media = Media.getMediaBuilder(field)
                .idMalNot(idMalNot)
                .idMalNot(idMalNot1)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("idMal_not", idMalNot);

        assertEquals(media.getElementString(), "Media(idMal_not: $idMal_not) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$idMal_not: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_IdMalIn_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int[] idMalIn = new int[]{123654, 987456};

        //when
        Media media = Media.getMediaBuilder(field)
                .idMalIn(idMalIn)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("idMal_in", idMalIn);

        assertEquals(media.getElementString(), "Media(idMal_in: $idMal_in) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$idMal_in: [Int], ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_ManyIdMalIn_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int[] idMalIn = new int[]{897456, 285963};
        int[] idMalIn1 = new int[]{123654, 987456};

        //when
        Media media = Media.getMediaBuilder(field)
                .idMalIn(idMalIn)
                .idMalIn(idMalIn1)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("idMal_in", idMalIn);

        assertEquals(media.getElementString(), "Media(idMal_in: $idMal_in) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$idMal_in: [Int], ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_IdMalNotIn_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int[] idMalNotIn = new int[]{123654, 987456};

        //when
        Media media = Media.getMediaBuilder(field)
                .idMalNotIn(idMalNotIn)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("idMal_not_in", idMalNotIn);

        assertEquals(media.getElementString(), "Media(idMal_not_in: $idMal_not_in) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$idMal_not_in: [Int], ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_ManyIdMalNotIn_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int[] idMalNotIn = new int[]{123654, 987456};
        int[] idMalNotIn1 = new int[]{124541245, 1298745456};

        //when
        Media media = Media.getMediaBuilder(field)
                .idMalNotIn(idMalNotIn)
                .idMalNotIn(idMalNotIn1)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("idMal_not_in", idMalNotIn);

        assertEquals(media.getElementString(), "Media(idMal_not_in: $idMal_not_in) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$idMal_not_in: [Int], ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_StartDateGreater_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        FuzzyDateValue dateValue = FuzzyDateValue.getFuzzyDateValueBuilder().nowAndBuild();

        //when
        Media media = Media.getMediaBuilder(field)
                .startDateGreater(dateValue)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("startDate_greater", dateValue.getFuzzyDateNumber());

        assertEquals(media.getElementString(), "Media(startDate_greater: $startDate_greater) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$startDate_greater: FuzzyDateInt, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_StartDateLesser_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        FuzzyDateValue dateValue = FuzzyDateValue.getFuzzyDateValueBuilder().nowAndBuild();

        //when
        Media media = Media.getMediaBuilder(field)
                .startDateLesser(dateValue)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("startDate_lesser", dateValue.getFuzzyDateNumber());

        assertEquals(media.getElementString(), "Media(startDate_lesser: $startDate_lesser) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$startDate_lesser: FuzzyDateInt, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_EndDateGreater_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        FuzzyDateValue dateValue = FuzzyDateValue.getFuzzyDateValueBuilder().nowAndBuild();

        //when
        Media media = Media.getMediaBuilder(field)
                .endDateGreater(dateValue)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("endDate_greater", dateValue.getFuzzyDateNumber());

        assertEquals(media.getElementString(), "Media(endDate_greater: $endDate_greater) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$endDate_greater: FuzzyDateInt, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_EndDateLesser_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        FuzzyDateValue dateValue = FuzzyDateValue.getFuzzyDateValueBuilder().nowAndBuild();

        //when
        Media media = Media.getMediaBuilder(field)
                .endDateLesser(dateValue)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("endDate_lesser", dateValue.getFuzzyDateNumber());

        assertEquals(media.getElementString(), "Media(endDate_lesser: $endDate_lesser) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$endDate_lesser: FuzzyDateInt, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_FormatNot_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        MediaFormat formats = MediaFormat.TV;

        //when
        Media media = Media.getMediaBuilder(field)
                .formatNot(formats)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("format_not", formats);

        assertEquals(media.getElementString(), "Media(format_not: $format_not) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$format_not: MediaFormat, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_FormatIn_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        MediaFormat[] formats = new MediaFormat[]{MediaFormat.MANGA, MediaFormat.MOVIE};

        //when
        Media media = Media.getMediaBuilder(field)
                .formatIn(formats)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("format_in", formats);

        assertEquals(media.getElementString(), "Media(format_in: $format_in) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$format_in: [MediaFormat], ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_FormatNotIn_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        MediaFormat[] formats = new MediaFormat[]{MediaFormat.MANGA, MediaFormat.MOVIE};

        //when
        Media media = Media.getMediaBuilder(field)
                .formatNotIn(formats)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("format_not_in", formats);

        assertEquals(media.getElementString(), "Media(format_not_in: $format_not_in) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$format_not_in: [MediaFormat], ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_StatusNot_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        MediaStatus status = MediaStatus.HIATUS;

        //when
        Media media = Media.getMediaBuilder(field)
                .statusNot(status)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("status_not", status);

        assertEquals(media.getElementString(), "Media(status_not: $status_not) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$status_not: MediaStatus, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_StatusIn_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        MediaStatus[] statuses = new MediaStatus[]{MediaStatus.FINISHED, MediaStatus.CANCELLED};
        //when
        Media media = Media.getMediaBuilder(field)
                .statusIn(statuses)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("status_in", statuses);

        assertEquals(media.getElementString(), "Media(status_in: $status_in) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$status_in: [MediaStatus], ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_StatusNotIn_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        MediaStatus[] statuses = new MediaStatus[]{MediaStatus.FINISHED, MediaStatus.CANCELLED};

        //when
        Media media = Media.getMediaBuilder(field)
                .statusNotIn(statuses)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("status_not_in", statuses);

        assertEquals(media.getElementString(), "Media(status_not_in: $status_not_in) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$status_not_in: [MediaStatus], ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_EpisodesGreater_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int episodes = 24;

        //when
        Media media = Media.getMediaBuilder(field)
                .episodesGreater(episodes)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("episodes_greater", episodes);

        assertEquals(media.getElementString(), "Media(episodes_greater: $episodes_greater) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$episodes_greater: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_EpisodesLesser_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int episodes = 24;

        //when
        Media media = Media.getMediaBuilder(field)
                .episodesLesser(episodes)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("episodes_lesser", episodes);

        assertEquals(media.getElementString(), "Media(episodes_lesser: $episodes_lesser) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$episodes_lesser: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_DurationGreater_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int duration = 15;

        //when
        Media media = Media.getMediaBuilder(field)
                .durationGreater(duration)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("duration_greater", duration);

        assertEquals(media.getElementString(), "Media(duration_greater: $duration_greater) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$duration_greater: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_DurationLesser_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int duration = 15;

        //when
        Media media = Media.getMediaBuilder(field)
                .durationLesser(duration)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("duration_lesser", duration);

        assertEquals(media.getElementString(), "Media(duration_lesser: $duration_lesser) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$duration_lesser: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_ChaptersGreater_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int chapters = 30;

        //when
        Media media = Media.getMediaBuilder(field)
                .chaptersGreater(chapters)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("chapters_greater", chapters);

        assertEquals(media.getElementString(), "Media(chapters_greater: $chapters_greater) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$chapters_greater: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_ChaptersLesser_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int chapters = 30;

        //when
        Media media = Media.getMediaBuilder(field)
                .chaptersLesser(chapters)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("chapters_lesser", chapters);

        assertEquals(media.getElementString(), "Media(chapters_lesser: $chapters_lesser) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$chapters_lesser: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_VolumesGreater_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int volumes = 5;

        //when
        Media media = Media.getMediaBuilder(field)
                .volumesGreater(volumes)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("volumes_greater", volumes);

        assertEquals(media.getElementString(), "Media(volumes_greater: $volumes_greater) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$volumes_greater: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_VolumesLesser_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int volumes = 5;

        //when
        Media media = Media.getMediaBuilder(field)
                .volumesLesser(volumes)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("volumes_lesser", volumes);

        assertEquals(media.getElementString(), "Media(volumes_lesser: $volumes_lesser) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$volumes_lesser: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_GenreIn_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        Genre[] genres = new Genre[]{Genre.ACTION, Genre.MECHA};

        //when
        Media media = Media.getMediaBuilder(field)
                .genreIn(genres)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("genre_in", genres);

        assertEquals(media.getElementString(), "Media(genre_in: $genre_in) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$genre_in: [String], ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_GenreNotIn_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        Genre[] genres = new Genre[]{Genre.ACTION, Genre.MECHA};

        //when
        Media media = Media.getMediaBuilder(field)
                .genreNotIn(genres)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("genre_not_in", genres);

        assertEquals(media.getElementString(), "Media(genre_not_in: $genre_not_in) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$genre_not_in: [String], ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_TagIn_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        Tags[] tags = new Tags[]{Tags.Go, Tags.Age_Gap};

        //when
        Media media = Media.getMediaBuilder(field)
                .tagIn(tags)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("tag_in", tags);

        assertEquals(media.getElementString(), "Media(tag_in: $tag_in) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$tag_in: [String], ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_TagNotIn_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        Tags[] tags = new Tags[]{Tags.Go, Tags.Age_Gap};

        //when
        Media media = Media.getMediaBuilder(field)
                .tagNotIn(tags)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("tag_not_in", tags);

        assertEquals(media.getElementString(), "Media(tag_not_in: $tag_not_in) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$tag_not_in: [String], ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_LicensedIn_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        String[] licensedBy = new String[]{"StudioA", "StudioB"};

        //when
        Media media = Media.getMediaBuilder(field)
                .licensedByIn(licensedBy)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("licensedBy_in", licensedBy);

        assertEquals(media.getElementString(), "Media(licensedBy_in: $licensedBy_in) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$licensedBy_in: [String], ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_AverageScoreNot_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int averageScore = 5;

        //when
        Media media = Media.getMediaBuilder(field)
                .averageScoreNot(averageScore)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("averageScore_not", averageScore);

        assertEquals(media.getElementString(), "Media(averageScore_not: $averageScore_not) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$averageScore_not: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_AverageScoreGreater_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int averageScore = 5;

        //when
        Media media = Media.getMediaBuilder(field)
                .averageScoreGreater(averageScore)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("averageScore_greater", averageScore);

        assertEquals(media.getElementString(), "Media(averageScore_greater: $averageScore_greater) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$averageScore_greater: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_AverageScoreLesser_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int averageScore = 5;

        //when
        Media media = Media.getMediaBuilder(field)
                .averageScoreLesser(averageScore)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("averageScore_lesser", averageScore);

        assertEquals(media.getElementString(), "Media(averageScore_lesser: $averageScore_lesser) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$averageScore_lesser: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_PopularityNot_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int popularity = 7;

        //when
        Media media = Media.getMediaBuilder(field)
                .popularityNot(popularity)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("popularity_not", popularity);

        assertEquals(media.getElementString(), "Media(popularity_not: $popularity_not) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$popularity_not: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_PopularityGreater_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int popularity = 7;

        //when
        Media media = Media.getMediaBuilder(field)
                .popularityGreater(popularity)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("popularity_greater", popularity);

        assertEquals(media.getElementString(), "Media(popularity_greater: $popularity_greater) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$popularity_greater: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_PopularityLesser_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        int popularity = 7;

        //when
        Media media = Media.getMediaBuilder(field)
                .popularityLesser(popularity)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("popularity_lesser", popularity);

        assertEquals(media.getElementString(), "Media(popularity_lesser: $popularity_lesser) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$popularity_lesser: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_SourceIn_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        MediaSource[] sources = new MediaSource[]{MediaSource.MANGA, MediaSource.ANIME};

        //when
        Media media = Media.getMediaBuilder(field)
                .sourceIn(sources)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("source_in", sources);

        assertEquals(media.getElementString(), "Media(source_in: $source_in) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$source_in: [MediaSource], ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_Sort_NoException() {
        //given
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .buildField();
        MediaSort[] sorts = new MediaSort[]{MediaSort.ID, MediaSort.SCORE};

        //when
        Media media = Media.getMediaBuilder(field)
                .sort(sorts)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("sort", sorts);

        assertEquals(media.getElementString(), "Media(sort: $sort) {\nid\n}");
        assertEquals(media.getQueryParameters().toString(), "[$sort: [MediaSort], ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }

    @Test
    void MediaBuilder_IdAndIdMal_NoException() {
        //given
        FuzzyDateField dateField = FuzzyDateField.getFuzzyDateFieldBuilder(FuzzyDateFieldParameter.startDate)
                .allAndBuild();
        Field field = Field.getFieldBuilder()
                .parameter(FieldParameters.id)
                .fuzzyDate(dateField)
                .buildField();
        int id = 159753;
        int idMal = 758624;

        //when
        Media media = Media.getMediaBuilder(field)
                .id(id)
                .idMal(idMal)
                .buildMedia();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("id", id);
        forTesting.put("idMal", idMal);

        assertEquals(media.getElementString(), "Media(id: $id, idMal: $idMal) {\nid\nstartDate {\nyear\nmonth\nday\n}\n}");
        assertEquals(media.getQueryParameters().toString(), "[$id: Int, , $idMal: Int, ]");
        assertEquals(media.getVariables().toString(), forTesting.toString());
    }
}