package com.arjstack.tech.enums;

public enum StatusEnum {

	ACTIVE("A"), INACTIVE("I"), PUBLIC("P"), RESTRICTED("R");

	private String status;

	private StatusEnum(String status) {
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public static boolean containsValue(String status) {
		for (StatusEnum statusEnum : StatusEnum.values()) {
			if (statusEnum.name().equals(status)) {
				return true;
			}
		}
		return false;
	}
}
