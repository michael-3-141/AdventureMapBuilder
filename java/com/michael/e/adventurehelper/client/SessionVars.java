package com.michael.e.adventurehelper.client;

public final class SessionVars {

	@SuppressWarnings("rawtypes")
	private static Class openedLedger;

	/**
	 * Deactivate constructor
	 */
	private SessionVars() {
	}

	@SuppressWarnings("rawtypes")
	public static void setOpenedLedger(Class ledgerClass) {
		openedLedger = ledgerClass;
	}

	@SuppressWarnings("rawtypes")
	public static Class getOpenedLedger() {
		return openedLedger;
	}
}