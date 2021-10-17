package pwr.pracainz.entities.anime.query.parameters.fuzzyDate;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.Year;

public class FuzzyDateValue {
	private final String fuzzyDate;

	public int getFuzzyDateNumber() {
		return Integer.parseInt(fuzzyDate);
	}

	private FuzzyDateValue(String fuzzyDate) {
		this.fuzzyDate = fuzzyDate;
	}

	@Override
	public String toString() {
		return fuzzyDate;
	}

	public static FuzzyDateValueBuilder getFuzzyDateValueBuilder() {
		return new FuzzyDateValueBuilder();
	}

	public static final class FuzzyDateValueBuilder {
		private int year = 0;
		private int month = 0;
		private int day = 0;

		public FuzzyDateValueBuilder year(int year) {
			this.year = year;
			return this;
		}

		public FuzzyDateValueBuilder currentYear() {
			this.year = LocalDateTime.now().getYear();
			return this;
		}

		public FuzzyDateValueBuilder monthByNumber(int month) {
			this.month = month;
			return this;
		}

		public FuzzyDateValueBuilder monthByMonth(Month month) {
			this.month = month.getValue();
			return this;
		}

		public FuzzyDateValueBuilder currentMonth() {
			this.month = LocalDateTime.now().getMonthValue();
			return this;
		}

		public FuzzyDateValueBuilder day(int day) {
			this.day = day;
			return this;
		}

		public FuzzyDateValueBuilder currentDay() {
			this.day = LocalDateTime.now().getDayOfMonth();
			return this;
		}

		public FuzzyDateValue nowAndBuild() {
			this.year = LocalDateTime.now().getYear();
			this.month = LocalDateTime.now().getMonthValue();
			this.day = LocalDateTime.now().getDayOfMonth();

			return buildFuzzyDateValue();
		}

		public FuzzyDateValue buildFuzzyDateValue() {
			if (year < 1000) {
				throw new IllegalArgumentException("Year must be declared!");
			}

			if (month < 0 || month > 12) {
				throw new IllegalArgumentException("The Month number is incorrect!");
			}

			if (day < 0) {
				throw new IllegalArgumentException("The Day Number should not be minus!");
			}

			if (day > Month.of(month == 0 ? 1 : month).length(Year.of(year).isLeap())) {
				throw new IllegalArgumentException("Day number exceeds month day number!");
			}

			return new FuzzyDateValue(year +
					String.format("%02d", month) +
					String.format("%02d", day));
		}
	}
}
