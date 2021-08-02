package pwr.pracainz.entities.anime.query.parameters.fuzzyDate;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FuzzyDateFieldTest {
    @Test
    void FuzzyDateQueryBuilder_NullArgument_ThrowException() {
        //given
        FuzzyDateFieldParameter parameter = null;

        //when
        Exception exception = assertThrows(IllegalArgumentException.class, () -> FuzzyDateField.getFuzzyDateFieldBuilder(parameter)
                .buildFuzzyDateField());

        //then
        assertEquals(exception.getMessage(), "The FieldParameters param should be of FuzzyDate Type!");
    }

    @Test
    void FuzzyDateQueryBuilder_WithCorrectArgument_ThrowException() {
        //given
        FuzzyDateFieldParameter parameter = FuzzyDateFieldParameter.startDate;

        //when
        Exception exception = assertThrows(IllegalStateException.class, () -> FuzzyDateField.getFuzzyDateFieldBuilder(parameter)
                .buildFuzzyDateField());

        //then
        assertEquals(exception.getMessage(), "Fuzzy Date should posses at least 1 parameter!");
    }

    @Test
    void FuzzyDateQueryBuilder_WithCorrectArgumentYear_NoException() {
        //given
        FuzzyDateFieldParameter parameter = FuzzyDateFieldParameter.startDate;

        //when
        FuzzyDateField dateField = FuzzyDateField.getFuzzyDateFieldBuilder(parameter)
                .year()
                .buildFuzzyDateField();

        //then
        assertEquals(dateField.toString(), "startDate {\nyear\n}");
    }

    @Test
    void FuzzyDateQueryBuilder_WithCorrectArgumentMonth_NoException() {
        //given
        FuzzyDateFieldParameter parameter = FuzzyDateFieldParameter.startDate;

        //when
        FuzzyDateField dateField = FuzzyDateField.getFuzzyDateFieldBuilder(parameter)
                .month()
                .buildFuzzyDateField();

        //then
        assertEquals(dateField.toString(), "startDate {\nmonth\n}");
    }

    @Test
    void FuzzyDateQueryBuilder_WithCorrectArgumentDay_NoException() {
        //given
        FuzzyDateFieldParameter parameter = FuzzyDateFieldParameter.startDate;

        //when
        FuzzyDateField dateField = FuzzyDateField.getFuzzyDateFieldBuilder(parameter)
                .day()
                .buildFuzzyDateField();

        //then
        assertEquals(dateField.toString(), "startDate {\nday\n}");
    }

    @Test
    void FuzzyDateQueryBuilder_WithCorrectArgumentYearAndMonth_NoException() {
        //given
        FuzzyDateFieldParameter parameter = FuzzyDateFieldParameter.startDate;

        //when
        FuzzyDateField dateField = FuzzyDateField.getFuzzyDateFieldBuilder(parameter)
                .year()
                .month()
                .buildFuzzyDateField();

        //then
        assertEquals(dateField.toString(), "startDate {\nyear\nmonth\n}");
    }

    @Test
    void FuzzyDateQueryBuilder_WithCorrectArgumentDayAndMonth_NoException() {
        //given
        FuzzyDateFieldParameter parameter = FuzzyDateFieldParameter.startDate;

        //when
        FuzzyDateField dateField = FuzzyDateField.getFuzzyDateFieldBuilder(parameter)
                .day()
                .month()
                .buildFuzzyDateField();

        //then
        assertEquals(dateField.toString(), "startDate {\nday\nmonth\n}");
    }

    @Test
    void FuzzyDateQueryBuilder_WithCorrectArgumentAll_NoException() {
        //given
        FuzzyDateFieldParameter parameter = FuzzyDateFieldParameter.startDate;

        //when
        FuzzyDateField dateField = FuzzyDateField.getFuzzyDateFieldBuilder(parameter)
                .allAndBuild();

        //then
        assertEquals(dateField.toString(), "startDate {\nyear\nmonth\nday\n}");
    }
}