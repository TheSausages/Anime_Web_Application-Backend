package pwr.pracainz.entities.anime.query.parameters.fuzzyDate;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class FuzzyDateValueTest {
	@Test
	void FuzzyDateValueBuilder_NoYear_ThrowException() {
		//given

		//when
		Exception exception = assertThrows(IllegalArgumentException.class, () -> FuzzyDateValue.getFuzzyDateValueBuilder()
				.buildFuzzyDateValue());

		//then
		assertEquals(exception.getMessage(), "Year must be declared!");
	}

	@Test
	void FuzzyDateValueBuilder_MinusMonth_ThrowException() {
		//given
		int month = -5;
		int year = 2000;

		//when
		Exception exception = assertThrows(IllegalArgumentException.class, () -> FuzzyDateValue.getFuzzyDateValueBuilder()
				.monthByNumber(month)
				.year(year)
				.buildFuzzyDateValue());

		//then
		assertEquals(exception.getMessage(), "The Month number is incorrect!");
	}

	@Test
	void FuzzyDateValueBuilder_Over12Month_ThrowException() {
		//given
		int month = 28;
		int year = 2000;

		//when
		Exception exception = assertThrows(IllegalArgumentException.class, () -> FuzzyDateValue.getFuzzyDateValueBuilder()
				.monthByNumber(month)
				.year(year)
				.buildFuzzyDateValue());

		//then
		assertEquals(exception.getMessage(), "The Month number is incorrect!");
	}

	@Test
	void FuzzyDateValueBuilder_MinusDay_ThrowException() {
		//given
		int day = -25;
		int year = 2000;

		//when
		Exception exception = assertThrows(IllegalArgumentException.class, () -> FuzzyDateValue.getFuzzyDateValueBuilder()
				.day(day)
				.year(year)
				.buildFuzzyDateValue());

		//then
		assertEquals(exception.getMessage(), "The Day Number should not be minus!");
	}

	@Test
	void FuzzyDateValueBuilder_TooManyDays_ThrowException() {
		//given
		int day = 900;
		int year = 2000;

		//when
		Exception exception = assertThrows(IllegalArgumentException.class, () -> FuzzyDateValue.getFuzzyDateValueBuilder()
				.day(day)
				.year(year)
				.buildFuzzyDateValue());

		//then
		assertEquals(exception.getMessage(), "Day number exceeds month day number!");
	}

	@Test
	void FuzzyDateValueBuilder_TooManyDaysForNonLeapYear_ThrowException() {
		//given
		int day = 29;
		Month month = Month.FEBRUARY;
		int year = 2001;

		//when
		Exception exception = assertThrows(IllegalArgumentException.class, () -> FuzzyDateValue.getFuzzyDateValueBuilder()
				.day(day)
				.monthByMonth(month)
				.year(year)
				.buildFuzzyDateValue());

		//then
		assertEquals(exception.getMessage(), "Day number exceeds month day number!");
	}

	@Test
	void FuzzyDateValueBuilder_OnlyYear_NoException() {
		//given
		int year = 1999;

		//when
		FuzzyDateValue fuzzyDateValue = FuzzyDateValue.getFuzzyDateValueBuilder()
				.year(year)
				.buildFuzzyDateValue();

		//then
		assertEquals(fuzzyDateValue.getFuzzyDateNumber(), 19990000);
	}

	@Test
	void FuzzyDateValueBuilder_YearAndMonth_NoException() {
		//given
		int year = 1999;
		int month = 9;

		//when
		FuzzyDateValue fuzzyDateValue = FuzzyDateValue.getFuzzyDateValueBuilder()
				.year(year)
				.monthByNumber(month)
				.buildFuzzyDateValue();

		//then
		assertEquals(fuzzyDateValue.getFuzzyDateNumber(), 19990900);
	}

	@Test
	void FuzzyDateValueBuilder_YearAndMonthAndDays3Methods_NoException() {
		//given
		int year = 1999;
		int month = 9;
		int day = 12;

		//when
		FuzzyDateValue fuzzyDateValue = FuzzyDateValue.getFuzzyDateValueBuilder()
				.year(year)
				.monthByNumber(month)
				.day(day)
				.buildFuzzyDateValue();

		//then
		assertEquals(fuzzyDateValue.getFuzzyDateNumber(), 19990912);
	}

	@Test
	void FuzzyDateValueBuilder_Now_NoException() {
		//given

		//when
		FuzzyDateValue fuzzyDateValue = FuzzyDateValue.getFuzzyDateValueBuilder()
				.nowAndBuild();

		//then
		assertEquals(fuzzyDateValue.getFuzzyDateNumber(), Integer.parseInt(LocalDateTime.now().toLocalDate().toString().replaceAll("-", "")));
	}

	@Test
	void FuzzyDateValueBuilder_CorrectDaysForNonLeapYear_NoException() {
		//given
		int day = 28;
		Month month = Month.FEBRUARY;
		int year = 2001;

		//when
		FuzzyDateValue fuzzyDateValue = FuzzyDateValue.getFuzzyDateValueBuilder()
				.day(day)
				.monthByMonth(month)
				.year(year)
				.buildFuzzyDateValue();

		//then
		assertEquals(fuzzyDateValue.getFuzzyDateNumber(), 20010228);
	}

	@Test
	void FuzzyDateValueBuilder_CorrectDaysForLeapYear_NoException() {
		//given
		int day = 29;
		Month month = Month.FEBRUARY;
		int year = 2000;

		//when
		FuzzyDateValue fuzzyDateValue = FuzzyDateValue.getFuzzyDateValueBuilder()
				.day(day)
				.monthByMonth(month)
				.year(year)
				.buildFuzzyDateValue();

		//then
		assertEquals(fuzzyDateValue.getFuzzyDateNumber(), 20000229);
	}
}