package pwr.pracainz.entities.anime.tagFile;

import lombok.Getter;

import java.util.HashSet;
import java.util.Set;

@Getter
public class TagList {
	private final Set<Tag> tagSet;

	public TagList() {
		tagSet = new HashSet<>();
	}

	public void addTag(Tag tag) {
		tagSet.add(tag);
	}

	public boolean addTagSet(Set<Tag> set) {
		return tagSet.addAll(set);
	}

	@Override
	public String toString() {
		return "TagList{" +
				"tagList=" + tagSet +
				'}';
	}
}
