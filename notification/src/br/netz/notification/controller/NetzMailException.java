package br.netz.notification.controller;

public class NetzMailException extends Exception {

	private static final long serialVersionUID = 6721033058420779365L;

	public NetzMailException() {
	}

	public NetzMailException(String arg0) {
		super(arg0);
	}

	public NetzMailException(Throwable arg0) {
		super(arg0);
	}

	public NetzMailException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
