package com.imss.sivimss.catvelatorios.util;

public class ConvertirGenerico {
	private ConvertirGenerico(){}

	public static <T> T convertInstanceOfObject(Object o) {
	    try {
	       return (T) o;
	    } catch (ClassCastException e) {
	        return null;
	    }
	}
}
