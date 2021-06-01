package pwr.PracaInz.Entities.Anime.Query.QueryElements;

import net.minidev.json.JSONObject;
import pwr.PracaInz.Entities.Anime.Query.Parameters.ParameterString;

import java.util.Set;

public interface QueryElements {
    String getElementString();
    Set<ParameterString> getQueryParameters();
    JSONObject getVariables();
}
