package pwr.PracaInz.Entities.Anime.Query.Parameters.Media;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MediaTitleTest {
    @Test
    void MediaTitleBuilder_NoParams_ThrowException() {
        //given

        //when
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            MediaTitle.getMediaTitleBuilder()
                    .buildMediaTitle();
        });

        assertEquals(exception.getMessage(), "At least 1 language must be selected!");
    }

    @Test
    void MediaTitleBuilder_RomajiNotStylized_NoException() {
        //given
        boolean stylized = false;

        //when
        MediaTitle mediaTitle = MediaTitle.getMediaTitleBuilder()
                .romajiLanguage(stylized)
                .buildMediaTitle();

        //then
        assertEquals(mediaTitle.toString(), "title {\nromaji\n}");
    }

    @Test
    void MediaTitleBuilder_RomajiStylized_NoException() {
        //given
        boolean stylized = true;

        //when
        MediaTitle mediaTitle = MediaTitle.getMediaTitleBuilder()
                .romajiLanguage(stylized)
                .buildMediaTitle();

        //then
        assertEquals(mediaTitle.toString(), "title {\nromaji(stylized: true)\n}");
    }

    @Test
    void MediaTitleBuilder_EnglishNotStylized_NoException() {
        //given
        boolean stylized = false;

        //when
        MediaTitle mediaTitle = MediaTitle.getMediaTitleBuilder()
                .englishLanguage(stylized)
                .buildMediaTitle();

        //then
        assertEquals(mediaTitle.toString(), "title {\nenglish\n}");
    }

    @Test
    void MediaTitleBuilder_EnglishStylized_NoException() {
        //given
        boolean stylized = true;

        //when
        MediaTitle mediaTitle = MediaTitle.getMediaTitleBuilder()
                .englishLanguage(stylized)
                .buildMediaTitle();

        //then
        assertEquals(mediaTitle.toString(), "title {\nenglish(stylized: true)\n}");
    }

    @Test
    void MediaTitleBuilder_NativeNotStylized_NoException() {
        //given
        boolean stylized = false;

        //when
        MediaTitle mediaTitle = MediaTitle.getMediaTitleBuilder()
                .nativeLanguage(stylized)
                .buildMediaTitle();

        //then
        assertEquals(mediaTitle.toString(), "title {\nnative\n}");
    }

    @Test
    void MediaTitleBuilder_NativeStylized_NoException() {
        //given
        boolean stylized = true;

        //when
        MediaTitle mediaTitle = MediaTitle.getMediaTitleBuilder()
                .nativeLanguage(stylized)
                .buildMediaTitle();

        //then
        assertEquals(mediaTitle.toString(), "title {\nnative(stylized: true)\n}");
    }

    @Test
    void MediaTitleBuilder_NativeStylizedAndEnglishNotStylized_NoException() {
        //given
        boolean stylizedNative = true;
        boolean stylizedEnglish = false;

        //when
        MediaTitle mediaTitle = MediaTitle.getMediaTitleBuilder()
                .nativeLanguage(stylizedNative)
                .englishLanguage(stylizedEnglish)
                .buildMediaTitle();

        //then
        assertEquals(mediaTitle.toString(), "title {\nnative(stylized: true)\nenglish\n}");
    }

    @Test
    void MediaTitleBuilder_NativeStylizedAndEnglishNotStylizedAndRomajiStylized_NoException() {
        //given
        boolean stylizedNative = true;
        boolean stylizedEnglish = false;
        boolean stylizedRomaji = false;

        //when
        MediaTitle mediaTitle = MediaTitle.getMediaTitleBuilder()
                .romajiLanguage(stylizedRomaji)
                .nativeLanguage(stylizedNative)
                .englishLanguage(stylizedEnglish)
                .buildMediaTitle();

        //then
        assertEquals(mediaTitle.toString(), "title {\nromaji\nnative(stylized: true)\nenglish\n}");
    }
}