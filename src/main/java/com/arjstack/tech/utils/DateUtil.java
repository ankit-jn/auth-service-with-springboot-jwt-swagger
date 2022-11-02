package com.arjstack.tech.utils;

import java.sql.Timestamp;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class DateUtil {

	public static String getISOLocalDate(Timestamp timestamp) {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE;
		return formatter.format(timestamp.toLocalDateTime());
	}

}
