package pwr.pracainz.entities.anime.query.queryElements;

import net.minidev.json.JSONObject;
import pwr.pracainz.entities.anime.query.parameters.ParameterString;

import java.util.Set;

public interface QueryElement {
	String getElementString();

	Set<ParameterString> getQueryParameters();

	JSONObject getVariables();
}
