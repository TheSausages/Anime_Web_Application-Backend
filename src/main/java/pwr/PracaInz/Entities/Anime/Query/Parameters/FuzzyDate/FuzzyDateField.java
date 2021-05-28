package pwr.PracaInz.Entities.Anime.Query.Parameters.FuzzyDate;

import lombok.Getter;
import pwr.PracaInz.Entities.Anime.Query.Parameters.FieldParameters;
import pwr.PracaInz.Entities.Anime.Query.Parameters.ParameterString;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
public class FuzzyDateField {
    private final String fuzzyDateString;

    private FuzzyDateField(String fuzzyDateString) {
        this.fuzzyDateString = fuzzyDateString;
    }

    public static FuzzyDateFieldBuilder getFuzzyDateFieldBuilder(FieldParameters parameter) {
        if (parameter != null && parameter.getType().equals("FuzzyDate")) {
            return new FuzzyDateFieldBuilder(parameter);
        }

        throw new IllegalArgumentException("The FieldParameters param should be of FuzzyDate Type!");
    }

    @Override
    public String toString() {
        return fuzzyDateString;
    }

    public final static class FuzzyDateFieldBuilder {
        private final Set<ParameterString> fuzzyDate = new LinkedHashSet<>();
        private final FieldParameters parameter;

        private FuzzyDateFieldBuilder(FieldParameters parameter) {
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

            return new FuzzyDateField(fuzzyDateBuilder.toString());
         }
    }
}
