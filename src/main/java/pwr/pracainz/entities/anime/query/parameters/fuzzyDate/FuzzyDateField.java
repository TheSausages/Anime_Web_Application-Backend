package pwr.pracainz.entities.anime.query.parameters.fuzzyDate;

import lombok.Getter;
import pwr.pracainz.entities.anime.query.parameters.ParameterString;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class FuzzyDateField {
    private final String fuzzyDateString;
    private final FuzzyDateFieldParameter parameter;

    private FuzzyDateField(String fuzzyDateString, FuzzyDateFieldParameter parameter) {
        this.fuzzyDateString = fuzzyDateString;
        this.parameter = parameter;
    }

    public static FuzzyDateFieldBuilder getFuzzyDateFieldBuilder(FuzzyDateFieldParameter parameter) {
        if (parameter != null) {
            return new FuzzyDateFieldBuilder(parameter);
        }

        throw new IllegalArgumentException("The FieldParameters param should be of FuzzyDate Type!");
    }

    @Override
    public String toString() {
        return fuzzyDateString;
    }

    public String getFuzzyDateStringWithoutFieldName() {
        return this.getFuzzyDateString().substring(parameter.toString().length());
    }

    public final static class FuzzyDateFieldBuilder {
        private final Set<ParameterString> fuzzyDate = new LinkedHashSet<>();
        private final FuzzyDateFieldParameter parameter;

        private FuzzyDateFieldBuilder(FuzzyDateFieldParameter parameter) {
            this.parameter = parameter;
        }

         public FuzzyDateFieldBuilder year() {
            fuzzyDate.add(new ParameterString("year\n"));
            return this;
         }

         public FuzzyDateFieldBuilder month() {
            fuzzyDate.add(new ParameterString("month\n"));
            return this;
         }

         public FuzzyDateFieldBuilder day() {
            fuzzyDate.add(new ParameterString("day\n"));
            return this;
         }

         public FuzzyDateField allAndBuild() {
            fuzzyDate.add(new ParameterString("year\nmonth\nday\n"));
            return buildFuzzyDateField();
         }

         public FuzzyDateField buildFuzzyDateField() {
            if (fuzzyDate.isEmpty()) { throw new IllegalStateException("Fuzzy Date should posses at least 1 parameter!"); }

            StringBuilder fuzzyDateBuilder = new StringBuilder(parameter + " {\n");
            fuzzyDate.forEach(fuzzyDateBuilder::append);
            fuzzyDateBuilder.append("}");

            return new FuzzyDateField(fuzzyDateBuilder.toString(), parameter);
         }
    }
}
