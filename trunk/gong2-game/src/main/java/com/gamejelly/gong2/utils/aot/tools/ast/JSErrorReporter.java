package com.gamejelly.gong2.utils.aot.tools.ast;

import sun.org.mozilla.javascript.internal.ErrorReporter;
import sun.org.mozilla.javascript.internal.EvaluatorException;

/**
 * 
 * @author Ram Kulkarni
 *
 */
public class JSErrorReporter implements ErrorReporter
{

	@Override
	public void warning(String message, String sourceName, int line,
			String lineSource, int lineOffset) {
		System.out.println("Warning : " + message);
	}
	
	@Override
	public EvaluatorException runtimeError(String message, String sourceName,
			int line, String lineSource, int lineOffset) {
		return null;
	}
	
	@Override
	public void error(String message, String sourceName, int line,
			String lineSource, int lineOffset) {
		System.out.println("Error : " + message);
	}
	
}