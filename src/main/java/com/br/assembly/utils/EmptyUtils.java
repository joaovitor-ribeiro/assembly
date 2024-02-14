package com.br.assembly.utils;

public class EmptyUtils {
	
	public static boolean isEmpty(Integer value) {
		if (value == null || value <= 0) {
			return true;
		}
		
		return false;
	}

	public static boolean isEmpty(Boolean value) {
		if (value == null) {
			return true;
		}
		
		return false;
	}

}
