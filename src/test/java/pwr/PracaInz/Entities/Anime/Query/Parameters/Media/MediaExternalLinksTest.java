package pwr.PracaInz.Entities.Anime.Query.Parameters.Media;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MediaExternalLinksTest {
    @Test
    void MediaExternalLinkStringBuilder_NoParameter_ThrowException() {
        //given

        //when
        Exception exception = assertThrows(IllegalStateException.class, () -> {
            MediaExternalLinks.getMediaExternalLinkStringBuilder()
                    .buildMediaExternalLinks();
        });

        //then
        assertEquals(exception.getMessage(), "External Links should posses at least 1 parameter!");
    }

    @Test
    void MediaExternalLinkStringBuilder_Id_ThrowException() {
        //given

        //when
        MediaExternalLinks link = MediaExternalLinks.getMediaExternalLinkStringBuilder()
                .addId()
                .buildMediaExternalLinks();

        //then
        assertEquals(link.getMediaExternalLinkString(), "externalLinks {\nid\n}");
    }

    @Test
    void MediaExternalLinkStringBuilder_Url_ThrowException() {
        //given

        //when
        MediaExternalLinks link = MediaExternalLinks.getMediaExternalLinkStringBuilder()
                .addUrl()
                .buildMediaExternalLinks();

        //then
        assertEquals(link.getMediaExternalLinkString(), "externalLinks {\nurl\n}");
    }

    @Test
    void MediaExternalLinkStringBuilder_Site_ThrowException() {
        //given

        //when
        MediaExternalLinks link = MediaExternalLinks.getMediaExternalLinkStringBuilder()
                .addSite()
                .buildMediaExternalLinks();

        //then
        assertEquals(link.getMediaExternalLinkString(), "externalLinks {\nsite\n}");
    }

    @Test
    void MediaExternalLinkStringBuilder_UrlAndSite_ThrowException() {
        //given

        //when
        MediaExternalLinks link = MediaExternalLinks.getMediaExternalLinkStringBuilder()
                .addUrl()
                .addSite()
                .buildMediaExternalLinks();

        //then
        assertEquals(link.getMediaExternalLinkString(), "externalLinks {\nurl\nsite\n}");
    }

    @Test
    void MediaExternalLinkStringBuilder_IdAndSite_ThrowException() {
        //given

        //when
        MediaExternalLinks link = MediaExternalLinks.getMediaExternalLinkStringBuilder()
                .addId()
                .addSite()
                .buildMediaExternalLinks();

        //then
        assertEquals(link.getMediaExternalLinkString(), "externalLinks {\nid\nsite\n}");
    }

    @Test
    void MediaExternalLinkStringBuilder_IdAndUrl_ThrowException() {
        //given

        //when
        MediaExternalLinks link = MediaExternalLinks.getMediaExternalLinkStringBuilder()
                .addId()
                .addUrl()
                .buildMediaExternalLinks();

        //then
        assertEquals(link.getMediaExternalLinkString(), "externalLinks {\nid\nurl\n}");
    }

    @Test
    void MediaExternalLinkStringBuilder_IdAndSiteAndUrl_ThrowException() {
        //given

        //when
        MediaExternalLinks link = MediaExternalLinks.getMediaExternalLinkStringBuilder()
                .addId()
                .addUrl()
                .addSite()
                .buildMediaExternalLinks();

        //then
        assertEquals(link.getMediaExternalLinkString(), "externalLinks {\nid\nurl\nsite\n}");
    }
}