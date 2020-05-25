package com.infosys.internal.portal.leave.management.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {

	public static String fromJsonToString(final Object obj) {
	    try {
	        return new ObjectMapper().writeValueAsString(obj);
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
}
