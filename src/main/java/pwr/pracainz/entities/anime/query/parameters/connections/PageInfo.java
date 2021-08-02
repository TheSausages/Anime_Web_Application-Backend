package pwr.pracainz.entities.anime.query.parameters.connections;

import lombok.Getter;
import pwr.pracainz.entities.anime.query.parameters.ParameterString;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class PageInfo {
    private final String pageInfoString;

    private PageInfo(String pageInfoString) {
        this.pageInfoString = pageInfoString;
    }

    public static PageInfoBuilder getPageInfoBuilder() {
        return new PageInfoBuilder();
    }

    public String getPageInfoStringWithoutFieldName() {
        return this.pageInfoString.substring(9);
    }

    @Override
    public String toString() {
        return pageInfoString;
    }

    public static final class PageInfoBuilder {
        private final Set<ParameterString> pageInfo = new LinkedHashSet<>();

        public PageInfoBuilder total() {
            pageInfo.add(new ParameterString("total\n"));
            return this;
        }

        public PageInfoBuilder perPage() {
            pageInfo.add(new ParameterString("perPage\n"));
            return this;
        }

        public PageInfoBuilder currentPage() {
            pageInfo.add(new ParameterString("currentPage\n"));
            return this;
        }

        public PageInfoBuilder lastPage() {
            pageInfo.add(new ParameterString("lastPage\n"));
            return this;
        }

        public PageInfoBuilder hasNextPage() {
            pageInfo.add(new ParameterString("hasNextPage\n"));
            return this;
        }

        public PageInfo buildPageInfo() {
            if (pageInfo.isEmpty()) { throw new IllegalStateException("Page Info should posses at least 1 parameter!"); }

            StringBuilder pageInfoBuilder = new StringBuilder("pageInfo {\n");

            pageInfo.forEach(pageInfoBuilder::append);

            pageInfoBuilder.append("}");

            return new PageInfo(pageInfoBuilder.toString());
        }
    }
}
