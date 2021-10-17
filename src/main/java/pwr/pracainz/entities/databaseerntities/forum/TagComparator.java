package pwr.pracainz.entities.databaseerntities.forum;

import java.util.Comparator;
import java.util.Objects;

public class TagComparator implements Comparator<Tag> {
	@Override
	public int compare(Tag o1, Tag o2) {
		if (Objects.isNull(o1.getTagImportance())) {
			return -1;
		}

		if (Objects.isNull(o2.getTagImportance())) {
			return 1;
		}

		return Integer.compare(o2.getTagImportance().getComparableImportance(), o1.getTagImportance().getComparableImportance());
	}
}
