package pwr.pracainz.entities.anime.query.queryElements.Page;

import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;
import pwr.pracainz.entities.anime.query.parameters.connections.PageInfo;
import pwr.pracainz.entities.anime.query.queryElements.Media.Field;
import pwr.pracainz.entities.anime.query.queryElements.Media.Media;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PageTest {

    @Test
    void PageBuilder_NoParams_throwException() {
        //given
        int page = 1;
        int perPage = 50;

        //when
        Exception exception = assertThrows(IllegalStateException.class, () -> Page.getPageBuilder(page, perPage).buildPage());

        //then
        assertEquals(exception.getMessage(), "Page must posses at least 1 parameter!");
    }

    @Test
    void PageBuilder_PageInfo_throwException() {
        //given
        int page = 1;
        int perPage = 50;
        PageInfo pageInfo = PageInfo.getPageInfoBuilder()
                .total()
                .buildPageInfo();

        //when
        Page pageObject = Page.getPageBuilder(page, perPage)
                .pageInfo(pageInfo)
                .buildPage();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("page", page);
        forTesting.put("perPage", perPage);

        assertEquals(pageObject.getElementString(), "Page(page: $page, perPage: $perPage) {\npageInfo {\ntotal\n}\n}");
        assertEquals(pageObject.getQueryParameters().toString(), "[$page: Int, , $perPage: Int, ]");
        assertEquals(pageObject.getVariables(), forTesting);
    }

    @Test
    void PageBuilder_Media_throwException() {
        //given
        int id = 123654;
        int page = 1;
        int perPage = 50;
        Field field = Field.getFieldBuilder()
                .description()
                .buildField();
        Media media = Media.getMediaBuilder(field)
                .id(id)
                .buildMedia();

        //when
        Page pageObject = Page.getPageBuilder(page, perPage)
                .media(media)
                .buildPage();

        //then
        JSONObject forTesting = new JSONObject();
        forTesting.put("page", page);
        forTesting.put("perPage", perPage);
        forTesting.put("id", id);

        assertEquals(pageObject.getElementString(), "Page(page: $page, perPage: $perPage) {\nmedia(id: $id) {\ndescription\n}\n}");
        assertEquals(pageObject.getQueryParameters().toString(), "[$page: Int, , $perPage: Int, , $id: Int, ]");
        assertEquals(pageObject.getVariables(), forTesting);
    }
}