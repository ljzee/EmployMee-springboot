package com.employmee.employmee.comparator;

import java.util.Comparator;

import com.employmee.employmee.entity.Experience;

public class SortExperienceByEndDateDesc implements Comparator<Experience> {

	@Override
	public int compare(Experience o1, Experience o2) {
		return o2.getEndDate().compareTo(o1.getEndDate());
	}

}
