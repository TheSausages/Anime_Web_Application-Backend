package pwr.pracainz.entities.anime.query.parameters.media;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MediaTitleTest {
    @Test
    void MediaTitleBuilder_NoParams_ThrowException() {
        //given

        //when
        Exception exception = assertThrows(IllegalStateException.class, () -> MediaTitle.getMediaTitleBuilder()
                .buildMediaTitle());

        assertEquals(exception.getMessage(), "At least 1 language must be selected!");
    }

    @Test
    void MediaTitleBuilder_RomajiNotStylized_NoException() {
        //given

        //when
        MediaTitle mediaTitle = MediaTitle.getMediaTitleBuilder()
                .romajiLanguage()
                .buildMediaTitle();

        //then
        assertEquals(mediaTitle.toString(), "title {\nromaji\n}");
    }

    @Test
    void MediaTitleBuilder_ManyRomajiNotStylized_NoException() {
        //given

        //when
        MediaTitle mediaTitle = MediaTitle.getMediaTitleBuilder()
                .romajiLanguage()
                .romajiLanguage()
                .buildMediaTitle();

        //then
        assertEquals(mediaTitle.toString(), "title {\nromaji\n}");
    }

    @Test
    void MediaTitleBuilder_RomajiStylized_NoException() {
        //given

        //when
        MediaTitle mediaTitle = MediaTitle.getMediaTitleBuilder()
                .romajiLanguageStylized()
                .buildMediaTitle();

        //then
        assertEquals(mediaTitle.toString(), "title {\nromaji(stylized: true)\n}");
    }

    @Test
    void MediaTitleBuilder_ManyRomajiStylized_NoException() {
        //given

        //when
        MediaTitle mediaTitle = MediaTitle.getMediaTitleBuilder()
                .romajiLanguageStylized()
                .romajiLanguage()
                .buildMediaTitle();

        //then
        assertEquals(mediaTitle.toString(), "title {\nromaji(stylized: true)\n}");
    }

    @Test
    void MediaTitleBuilder_EnglishNotStylized_NoException() {
        //given

        //when
        MediaTitle mediaTitle = MediaTitle.getMediaTitleBuilder()
                .englishLanguage()
                .buildMediaTitle();

        //then
        assertEquals(mediaTitle.toString(), "title {\nenglish\n}");
    }

    @Test
    void MediaTitleBuilder_ManyEnglishNotStylized_NoException() {
        //given

        //when
        MediaTitle mediaTitle = MediaTitle.getMediaTitleBuilder()
                .englishLanguage()
                .englishLanguageStylized()
                .englishLanguage()
                .buildMediaTitle();

        //then
        assertEquals(mediaTitle.toString(), "title {\nenglish\n}");
    }

    @Test
    void MediaTitleBuilder_EnglishStylized_NoException() {
        //given

        //when
        MediaTitle mediaTitle = MediaTitle.getMediaTitleBuilder()
                .englishLanguageStylized()
                .buildMediaTitle();

        //then
        assertEquals(mediaTitle.toString(), "title {\nenglish(stylized: true)\n}");
    }

    @Test
    void MediaTitleBuilder_ManyEnglishStylized_NoException() {
        //given

        //when
        MediaTitle mediaTitle = MediaTitle.getMediaTitleBuilder()
                .englishLanguageStylized()
                .englishLanguageStylized()
                .englishLanguage()
                .buildMediaTitle();

        //then
        assertEquals(mediaTitle.toString(), "title {\nenglish(stylized: true)\n}");
    }

    @Test
    void MediaTitleBuilder_NativeNotStylized_NoException() {
        //given

        //when
        MediaTitle mediaTitle = MediaTitle.getMediaTitleBuilder()
                .nativeLanguage()
                .buildMediaTitle();

        //then
        assertEquals(mediaTitle.toString(), "title {\nnative\n}");
    }

    @Test
    void MediaTitleBuilder_ManyNativeNotStylized_NoException() {
        //given

        //when
        MediaTitle mediaTitle = MediaTitle.getMediaTitleBuilder()
                .nativeLanguage()
                .nativeLanguageStylized()
                .nativeLanguage()
                .buildMediaTitle();

        //then
        assertEquals(mediaTitle.toString(), "title {\nnative\n}");
    }

    @Test
    void MediaTitleBuilder_NativeStylized_NoException() {
        //given

        //when
        MediaTitle mediaTitle = MediaTitle.getMediaTitleBuilder()
                .nativeLanguageStylized()
                .buildMediaTitle();

        //then
        assertEquals(mediaTitle.toString(), "title {\nnative(stylized: true)\n}");
    }

    @Test
    void MediaTitleBuilder_ManyNativeStylized_NoException() {
        //given

        //when
        MediaTitle mediaTitle = MediaTitle.getMediaTitleBuilder()
                .nativeLanguageStylized()
                .nativeLanguage()
                .nativeLanguageStylized()
                .buildMediaTitle();

        //then
        assertEquals(mediaTitle.toString(), "title {\nnative(stylized: true)\n}");
    }

    @Test
    void MediaTitleBuilder_NativeStylizedAndEnglishNotStylized_NoException() {
        //given

        //when
        MediaTitle mediaTitle = MediaTitle.getMediaTitleBuilder()
                .nativeLanguageStylized()
                .englishLanguage()
                .buildMediaTitle();

        //then
        assertEquals(mediaTitle.toString(), "title {\nnative(stylized: true)\nenglish\n}");
    }

    @Test
    void MediaTitleBuilder_NativeStylizedAndEnglishNotStylizedAndRomajiStylized_NoException() {
        //given

        //when
        MediaTitle mediaTitle = MediaTitle.getMediaTitleBuilder()
                .romajiLanguage()
                .nativeLanguageStylized()
                .englishLanguage()
                .buildMediaTitle();

        //then
        assertEquals(mediaTitle.toString(), "title {\nromaji\nnative(stylized: true)\nenglish\n}");
    }

    @Test
    void MediaTitleBuilder_NativeStylizedAndEnglishNotStylizedAndManyRomajiStylized_NoException() {
        //given

        //when
        MediaTitle mediaTitle = MediaTitle.getMediaTitleBuilder()
                .romajiLanguage()
                .nativeLanguageStylized()
                .romajiLanguage()
                .englishLanguage()
                .romajiLanguage()
                .buildMediaTitle();

        //then
        assertEquals(mediaTitle.toString(), "title {\nromaji\nnative(stylized: true)\nenglish\n}");
    }
}