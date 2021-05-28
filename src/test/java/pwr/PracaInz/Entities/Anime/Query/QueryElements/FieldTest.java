package pwr.PracaInz.Entities.Anime.Query.QueryElements;

import org.junit.jupiter.api.Test;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Charackters.*;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Connections.Charackters.Character;
import pwr.PracaInz.Entities.Anime.Query.Parameters.FieldParameters;
import pwr.PracaInz.Entities.Anime.Query.Parameters.FuzzyDate.FuzzyDateField;
import pwr.PracaInz.Entities.Anime.Query.Parameters.Media.*;
import pwr.PracaInz.Entities.Anime.Query.QueryElements.Media.Field;

import static org.junit.jupiter.api.Assertions.*;

class FieldTest {
    @Test
    void FieldBuilder_NoParams_ThrowException() {
        //given

        //when
        Exception exception = assertThrows(IllegalStateException.class, () -> Field.getFieldBuilder()
                .buildField());

        //then
        assertEquals(exception.getMessage(), "Field must have at least 1 Parameter");
    }

    @Test
    void FieldBuilder_FieldParameter_NoException() {
        //given
        FieldParameters parameter = FieldParameters.id;

        //when
        Field field = Field.getFieldBuilder()
                .parameter(parameter)
                .buildField();

        //then
        assertEquals(field.toString(), "{\nid\n}");
    }

    @Test
    void FieldBuilder_ManyDifferentFieldParameter_NoException() {
        //given
        FieldParameters parameter = FieldParameters.id;
        FieldParameters parameter1 = FieldParameters.averageScore;

        //when
        Field field = Field.getFieldBuilder()
                .parameter(parameter)
                .parameter(parameter1)
                .buildField();

        //then
        assertEquals(field.toString(), "{\nid\naverageScore\n}");
    }

    @Test
    void FieldBuilder_ManySameFieldParameters_NoException() {
        //given
        FieldParameters parameter = FieldParameters.id;
        FieldParameters parameter1 = FieldParameters.averageScore;
        FieldParameters parameter2 = FieldParameters.id;

        //when
        Field field = Field.getFieldBuilder()
                .parameter(parameter)
                .parameter(parameter1)
                .parameter(parameter2)
                .buildField();

        //then
        assertEquals(field.toString(), "{\nid\naverageScore\n}");
    }

    @Test
    void FieldBuilder_Titles_NoException() {
        //given
        MediaTitle title = MediaTitle.getMediaTitleBuilder()
                .englishLanguage()
                .romajiLanguageStylized()
                .buildMediaTitle();

        //when
        Field field = Field.getFieldBuilder()
                .title(title)
                .buildField();

        //then
        assertEquals(field.toString(), "{\ntitle {\nenglish\nromaji(stylized: true)\n}\n}");
    }

    @Test
    void FieldBuilder_ManyTitles_NoException() {
        //given
        MediaTitle title = MediaTitle.getMediaTitleBuilder()
                .englishLanguage()
                .romajiLanguageStylized()
                .buildMediaTitle();
        MediaTitle title2 = MediaTitle.getMediaTitleBuilder()
                .nativeLanguage()
                .buildMediaTitle();

        //when
        Field field = Field.getFieldBuilder()
                .title(title)
                .title(title2)
                .buildField();

        //then
        assertEquals(field.toString(), "{\ntitle {\nenglish\nromaji(stylized: true)\n}\n}");
    }

    @Test
    void FieldBuilder_Trailer_NoException() {
        //given

        //when
        Field field = Field.getFieldBuilder()
                .trailer()
                .buildField();

        //then
        assertEquals(field.toString(), "{\ntrailer {\nid\nsite\nthumbnail\n}\n}");
    }

    @Test
    void FieldBuilder_ManyTrailer_NoException() {
        //given

        //when
        Field field = Field.getFieldBuilder()
                .trailer()
                .trailer()
                .buildField();

        //then
        assertEquals(field.toString(), "{\ntrailer {\nid\nsite\nthumbnail\n}\n}");
    }

    @Test
    void FieldBuilder_Tags_NoException() {
        //given

        //when
        Field field = Field.getFieldBuilder()
                .tags()
                .buildField();

        //then
        assertEquals(field.toString(), "{\ntags {\nid\nname\ndescription\ncategory\nrank\nisGeneralSpoiler\nisMediaSpoiler\nisAdult\n}\n}");
    }

    @Test
    void FieldBuilder_ManyTags_NoException() {
        //given

        //when
        Field field = Field.getFieldBuilder()
                .tags()
                .tags()
                .buildField();

        //then
        assertEquals(field.toString(), "{\ntags {\nid\nname\ndescription\ncategory\nrank\nisGeneralSpoiler\nisMediaSpoiler\nisAdult\n}\n}");
    }

    @Test
    void FieldBuilder_NextAiringEpisode_NoException() {
        //given

        //when
        Field field = Field.getFieldBuilder()
                .nextAiringEpisode()
                .buildField();

        //then
        assertEquals(field.toString(), "{\nnextAiringEpisode {\nid\nairingAt\ntimeUntilAiring\nepisode\nmediaId\n}\n}");
    }

    @Test
    void FieldBuilder_ManyNextAiringEpisode_NoException() {
        //given

        //when
        Field field = Field.getFieldBuilder()
                .nextAiringEpisode()
                .nextAiringEpisode()
                .buildField();

        //then
        assertEquals(field.toString(), "{\nnextAiringEpisode {\nid\nairingAt\ntimeUntilAiring\nepisode\nmediaId\n}\n}");
    }

    @Test
    void FieldBuilder_StatusNoVersion_NoException() {
        //given

        //when
        Field field = Field.getFieldBuilder()
                .status()
                .buildField();

        //then
        assertEquals(field.toString(), "{\nstatus\n}");
    }

    @Test
    void FieldBuilder_ManyStatusNoVersion_NoException() {
        //given

        //when
        Field field = Field.getFieldBuilder()
                .status()
                .status()
                .buildField();

        //then
        assertEquals(field.toString(), "{\nstatus\n}");
    }

    @Test
    void FieldBuilder_StatusWithVersion_NoException() {
        //given
        int version = 2;

        //when
        Field field = Field.getFieldBuilder()
                .status(version)
                .buildField();

        //then
        assertEquals(field.toString(), "{\nstatus(version: 2)\n}");
    }

    @Test
    void FieldBuilder_ManyStatusWithVersion_NoException() {
        //given
        int version = 2;
        int version1 = 1;

        //when
        Field field = Field.getFieldBuilder()
                .status(version)
                .status(version1)
                .buildField();

        //then
        assertEquals(field.toString(), "{\nstatus(version: 2)\n}");
    }

    @Test
    void FieldBuilder_MixedStatus_NoException() {
        //given
        int version = 2;

        //when
        Field field = Field.getFieldBuilder()
                .status(version)
                .status()
                .buildField();

        //then
        assertEquals(field.toString(), "{\nstatus(version: 2)\n}");
    }

    @Test
    void FieldBuilder_DescriptionNotAsHtml_NoException() {
        //given

        //when
        Field field = Field.getFieldBuilder()
                .description()
                .buildField();

        //then
        assertEquals(field.toString(), "{\ndescription\n}");
    }

    @Test
    void FieldBuilder_ManyDescriptionNotAsHtml_NoException() {
        //given

        //when
        Field field = Field.getFieldBuilder()
                .description()
                .description()
                .buildField();

        //then
        assertEquals(field.toString(), "{\ndescription\n}");
    }

    @Test
    void FieldBuilder_DescriptionWithAsHtml_NoException() {
        //given

        //when
        Field field = Field.getFieldBuilder()
                .descriptionAsHtml()
                .buildField();

        //then
        assertEquals(field.toString(), "{\ndescription(asHtml: true)\n}");
    }

    @Test
    void FieldBuilder_ManyDescriptionWithAsHtml_NoException() {
        //given

        //when
        Field field = Field.getFieldBuilder()
                .descriptionAsHtml()
                .descriptionAsHtml()
                .buildField();

        //then
        assertEquals(field.toString(), "{\ndescription(asHtml: true)\n}");
    }

    @Test
    void FieldBuilder_MixedDescriptionWithAsHtml_NoException() {
        //given

        //when
        Field field = Field.getFieldBuilder()
                .descriptionAsHtml()
                .description()
                .buildField();

        //then
        assertEquals(field.toString(), "{\ndescription(asHtml: true)\n}");
    }

    @Test
    void FieldBuilder_SourceNoVersion_NoException() {
        //given

        //when
        Field field = Field.getFieldBuilder()
                .source()
                .buildField();

        //then
        assertEquals(field.toString(), "{\nsource\n}");
    }

    @Test
    void FieldBuilder_ManySourceNoVersion_NoException() {
        //given

        //when
        Field field = Field.getFieldBuilder()
                .source()
                .source()
                .buildField();

        //then
        assertEquals(field.toString(), "{\nsource\n}");
    }

    @Test
    void FieldBuilder_SourceWithVersion_NoException() {
        //given
        int version = 2;

        //when
        Field field = Field.getFieldBuilder()
                .source(version)
                .buildField();

        //then
        assertEquals(field.toString(), "{\nsource(version: 2)\n}");
    }

    @Test
    void FieldBuilder_ManySourceWithVersion_NoException() {
        //given
        int version = 2;
        int version1 = 1;

        //when
        Field field = Field.getFieldBuilder()
                .source(version)
                .source(version1)
                .buildField();

        //then
        assertEquals(field.toString(), "{\nsource(version: 2)\n}");
    }

    @Test
    void FieldBuilder_MixedSource_NoException() {
        //given
        int version = 2;

        //when
        Field field = Field.getFieldBuilder()
                .source(version)
                .source()
                .buildField();

        //then
        assertEquals(field.toString(), "{\nsource(version: 2)\n}");
    }

    @Test
    void FieldBuilder_ExternalLinks_NoException() {
        //given
        MediaExternalLinks externalLink = MediaExternalLinks.getMediaExternalLinkStringBuilder()
                .addId()
                .buildMediaExternalLinks();

        //when
        Field field = Field.getFieldBuilder()
                .externalLinks(externalLink)
                .buildField();

        //then
        assertEquals(field.toString(), "{\nexternalLinks {\nid\n}\n}");
    }

    @Test
    void FieldBuilder_ManyExternalLinks_NoException() {
        //given
        MediaExternalLinks externalLink = MediaExternalLinks.getMediaExternalLinkStringBuilder()
                .addId()
                .buildMediaExternalLinks();
        MediaExternalLinks externalLink1 = MediaExternalLinks.getMediaExternalLinkStringBuilder()
                .addUrl()
                .buildMediaExternalLinks();

        //when
        Field field = Field.getFieldBuilder()
                .externalLinks(externalLink)
                .externalLinks(externalLink1)
                .buildField();

        //then
        assertEquals(field.toString(), "{\nexternalLinks {\nid\n}\n}");
    }

    @Test
    void FieldBuilder_Ranking_NoException() {
        //given
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .rank()
                .buildMediaRank();

        //when
        Field field = Field.getFieldBuilder()
                .ranking(rank)
                .buildField();

        //then
        assertEquals(field.toString(), "{\nranking {\nrank\n}\n}");
    }

    @Test
    void FieldBuilder_ManyRanking_NoException() {
        //given
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .rank()
                .buildMediaRank();
        MediaRank rank1 = MediaRank.getMediaRankBuilder()
                .year()
                .buildMediaRank();

        //when
        Field field = Field.getFieldBuilder()
                .ranking(rank1)
                .ranking(rank)
                .buildField();

        //then
        assertEquals(field.toString(), "{\nranking {\nyear\n}\n}");
    }

    @Test
    void FieldBuilder_FuzzyDate_NoException() {
        //given
        FieldParameters parameter = FieldParameters.startDate;
        FuzzyDateField fuzzyField = FuzzyDateField.getFuzzyDateFieldBuilder(parameter)
                .year()
                .buildFuzzyDateField();

        //when
        Field field = Field.getFieldBuilder()
                .fuzzyDate(fuzzyField)
                .buildField();

        //then
        assertEquals(field.toString(), "{\nstartDate {\nyear\n}\n}");
    }

    @Test
    void FieldBuilder_ManySameFuzzyDate_NoException() {
        //given
        FieldParameters parameter = FieldParameters.startDate;
        FuzzyDateField fuzzyField = FuzzyDateField.getFuzzyDateFieldBuilder(parameter)
                .year()
                .buildFuzzyDateField();
        FieldParameters parameter1 = FieldParameters.startDate;
        FuzzyDateField fuzzyField1 = FuzzyDateField.getFuzzyDateFieldBuilder(parameter1)
                .allAndBuild();

        //when
        Field field = Field.getFieldBuilder()
                .fuzzyDate(fuzzyField)
                .fuzzyDate(fuzzyField1)
                .buildField();

        //then
        assertEquals(field.toString(), "{\nstartDate {\nyear\n}\n}");
    }

    @Test
    void FieldBuilder_ManyDifferentFuzzyDate_NoException() {
        //given
        FieldParameters parameter = FieldParameters.startDate;
        FuzzyDateField fuzzyField = FuzzyDateField.getFuzzyDateFieldBuilder(parameter)
                .year()
                .buildFuzzyDateField();
        FieldParameters parameter1 = FieldParameters.endDate;
        FuzzyDateField fuzzyField1 = FuzzyDateField.getFuzzyDateFieldBuilder(parameter1)
                .allAndBuild();

        //when
        Field field = Field.getFieldBuilder()
                .fuzzyDate(fuzzyField)
                .fuzzyDate(fuzzyField1)
                .buildField();

        //then
        assertEquals(field.toString(), "{\nstartDate {\nyear\n}\nendDate {\nyear\nmonth\nday\n}\n}");
    }

    @Test
    void FieldBuilder_StreamingEpisodes_NoException() {
        //given
        MediaStreamingEpisodes episode = MediaStreamingEpisodes.MediaStreamingEpisodesBuilder()
                .title()
                .buildMediaRank();

        //when
        Field field = Field.getFieldBuilder()
                .streamingEpisodes(episode)
                .buildField();

        //then
        assertEquals(field.toString(), "{\nstreamingEpisodes {\ntitle\n}\n}");
    }

    @Test
    void FieldBuilder_ManyStreamingEpisodes_NoException() {
        //given
        MediaStreamingEpisodes episode = MediaStreamingEpisodes.MediaStreamingEpisodesBuilder()
                .title()
                .buildMediaRank();
        MediaStreamingEpisodes episode1 = MediaStreamingEpisodes.MediaStreamingEpisodesBuilder()
                .site()
                .buildMediaRank();

        //when
        Field field = Field.getFieldBuilder()
                .streamingEpisodes(episode)
                .streamingEpisodes(episode1)
                .buildField();

        //then
        assertEquals(field.toString(), "{\nstreamingEpisodes {\ntitle\n}\n}");
    }

    @Test
    void FieldBuilder_Characters_NoException() {
        //given
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .id()
                .buildCharacterEdge();
        CharacterConnection characterConnection = CharacterConnection.getCharacterConnectionBuilder()
                .edges(edge)
                .buildCharacterConnection();

        //when
        Field field = Field.getFieldBuilder()
                .characters(characterConnection)
                .buildField();

        //then
        assertEquals(field.toString(), "{\ncharacters {\nedges {\nid\n}\n}\n}");
    }

    @Test
    void FieldBuilder_ManyCharacters_NoException() {
        //given
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .name()
                .buildCharacterEdge();
        Character character = Character.getCharacterBuilder()
                .id()
                .buildCharacter();
        CharacterConnection characterConnection = CharacterConnection.getCharacterConnectionBuilder()
                .nodes(character)
                .buildCharacterConnection();
        CharacterConnection characterConnection1 = CharacterConnection.getCharacterConnectionBuilder()
                .edges(edge)
                .buildCharacterConnection();

        //when
        Field field = Field.getFieldBuilder()
                .characters(characterConnection)
                .characters(characterConnection1)
                .buildField();

        //then
        assertEquals(field.toString(), "{\ncharacters {\nnodes {\nid\n}\n}\n}");
    }

    @Test
    void FieldBuilder_CharactersWithCharacterArguments_NoException() {
        //given
        CharacterArguments arguments = CharacterArguments.getCharacterArgumentsBuilder()
                .role(CharacterRole.MAIN)
                .buildCharacterMediaArguments();
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .id()
                .buildCharacterEdge();
        CharacterConnection characterConnection = CharacterConnection.getCharacterConnectionBuilder()
                .edges(edge)
                .buildCharacterConnection();

        //when
        Field field = Field.getFieldBuilder()
                .characters(arguments, characterConnection)
                .buildField();

        //then
        assertEquals(field.toString(), "{\ncharacters(role: MAIN) {\nedges {\nid\n}\n}\n}");
    }

    @Test
    void FieldBuilder_ManyCharactersWithCharacterArguments_NoException() {
        //given
        CharacterArguments arguments = CharacterArguments.getCharacterArgumentsBuilder()
                .mediaSort(new MediaSort[]{MediaSort.TYPE})
                .buildCharacterMediaArguments();
        CharacterArguments arguments1 = CharacterArguments.getCharacterArgumentsBuilder()
                .perPage(12)
                .buildCharacterMediaArguments();
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .name()
                .buildCharacterEdge();
        Character character = Character.getCharacterBuilder()
                .id()
                .buildCharacter();
        CharacterConnection characterConnection = CharacterConnection.getCharacterConnectionBuilder()
                .nodes(character)
                .buildCharacterConnection();
        CharacterConnection characterConnection1 = CharacterConnection.getCharacterConnectionBuilder()
                .edges(edge)
                .buildCharacterConnection();

        //when
        Field field = Field.getFieldBuilder()
                .characters(arguments, characterConnection)
                .characters(arguments1, characterConnection1)
                .buildField();

        //then
        assertEquals(field.toString(), "{\ncharacters(sort: [TYPE]) {\nnodes {\nid\n}\n}\n}");
    }

    @Test
    void FieldBuilder_IdAndTitle_NoException() {
        //given
        FieldParameters parameter = FieldParameters.id;
        MediaTitle title = MediaTitle.getMediaTitleBuilder()
                .englishLanguage()
                .romajiLanguageStylized()
                .buildMediaTitle();

        //when
        Field field = Field.getFieldBuilder()
                .parameter(parameter)
                .title(title)
                .buildField();

        //then
        assertEquals(field.toString(), "{\nid\ntitle {\nenglish\nromaji(stylized: true)\n}\n}");
    }

    @Test
    void FieldBuilder_IdAndDescriptionAndTagsAndStatus_NoException() {
        //given
        FieldParameters parameter = FieldParameters.id;

        //when
        Field field = Field.getFieldBuilder()
                .parameter(parameter)
                .description()
                .tags()
                .status(2)
                .buildField();

        //then
        assertEquals(field.toString(), "{\nid\ndescription\ntags {\nid\nname\ndescription\ncategory\nrank\nisGeneralSpoiler\nisMediaSpoiler\nisAdult\n}\nstatus(version: 2)\n}");
    }

    @Test
    void FieldBuilder_AllPossible_NoException() {
        //given
        FieldParameters parameter = FieldParameters.id;
        FieldParameters parameter1 = FieldParameters.averageScore;
        FieldParameters parameter2 = FieldParameters.isLicensed;
        MediaTitle title = MediaTitle.getMediaTitleBuilder()
                .englishLanguage()
                .romajiLanguageStylized()
                .buildMediaTitle();
        MediaExternalLinks link = MediaExternalLinks.getMediaExternalLinkStringBuilder()
                .addId()
                .addUrl()
                .buildMediaExternalLinks();
        MediaRank rank = MediaRank.getMediaRankBuilder()
                .rank()
                .year()
                .season()
                .buildMediaRank();
        FuzzyDateField startDateField = FuzzyDateField.getFuzzyDateFieldBuilder(FieldParameters.startDate)
                .allAndBuild();
        FuzzyDateField endDateField = FuzzyDateField.getFuzzyDateFieldBuilder(FieldParameters.endDate)
                .allAndBuild();
        MediaStreamingEpisodes episodes = MediaStreamingEpisodes.MediaStreamingEpisodesBuilder()
                .title()
                .thumbnail()
                .url()
                .buildMediaRank();

        CharacterArguments arguments = CharacterArguments.getCharacterArgumentsBuilder()
                .role(CharacterRole.MAIN)
                .buildCharacterMediaArguments();
        CharacterEdge edge = CharacterEdge.getCharacterEdgeBuilder()
                .id()
                .buildCharacterEdge();
        Character character = Character.getCharacterBuilder()
                .name()
                .age()
                .buildCharacter();
        CharacterConnection characterConnection = CharacterConnection.getCharacterConnectionBuilder()
                .edges(edge)
                .nodes(character)
                .buildCharacterConnection();

        //when
        Field field = Field.getFieldBuilder()
                .parameter(parameter)
                .parameter(parameter1)
                .title(title)
                .trailer()
                .tags()
                .nextAiringEpisode()
                .status(2)
                .description()
                .source()
                .externalLinks(link)
                .ranking(rank)
                .fuzzyDate(startDateField)
                .fuzzyDate(endDateField)
                .streamingEpisodes(episodes)
                .parameter(parameter2)
                .characters(arguments, characterConnection)
                .buildField();

        //then
        assertEquals(field.toString(), "{\nid\naverageScore\ntitle {\nenglish\nromaji(stylized: true)\n}\ntrailer {\nid\nsite\nthumbnail\n}\n" +
                "tags {\nid\nname\ndescription\ncategory\nrank\nisGeneralSpoiler\nisMediaSpoiler\nisAdult\n}\nnextAiringEpisode {\nid\nairingAt\ntimeUntilAiring\nepisode\nmediaId\n}\n" +
                "status(version: 2)\ndescription\nsource\nexternalLinks {\nid\nurl\n}\nranking {\nrank\nyear\nseason\n}\nstartDate {\nyear\nmonth\nday\n}\nendDate {\nyear\nmonth\nday\n}\n" +
                "streamingEpisodes {\ntitle\nthumbnail\nurl\n}\nisLicensed\ncharacters(role: MAIN) {\nedges {\nid\n}\nnodes {\nname\nage\n}\n}\n}");
    }
}