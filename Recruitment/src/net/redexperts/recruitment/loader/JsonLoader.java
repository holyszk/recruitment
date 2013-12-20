package net.redexperts.recruitment.loader;

import java.io.IOException;
import java.io.InputStream;

import net.redexperts.recruitment.data.Place;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonLoader extends BasicLoader<Place> {

	@Override
	protected Place parseData(InputStream is) {
		ObjectMapper mapper = new ObjectMapper();
		Place place = null;
		
		try {
			place = mapper.readValue(is, Place.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return place;
	}
}
