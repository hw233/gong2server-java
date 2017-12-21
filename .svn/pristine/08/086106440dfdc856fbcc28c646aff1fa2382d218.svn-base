package com.gamejelly.gong2.utils.aot.tools.ast;

import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import sun.org.mozilla.javascript.internal.Token;
import sun.org.mozilla.javascript.internal.ast.AstNode;
import sun.org.mozilla.javascript.internal.ast.AstRoot;

/**
 * 
 * @author Ram Kulkarni
 *
 */
public class JSSymbolVisitor implements IJSSymbolVisitor
{
	private Set<String> allSymbol;
	
	public JSSymbolVisitor(Set<String> allSymbol){
		this.allSymbol = allSymbol;
	}

	@Override
	public boolean visit(JSSymbol sym) {
		AstNode astNode = sym.getNode();
		if (astNode instanceof AstRoot)
			return true;
		
//		int tabs = getNumTabs(sym);
//		
//		for (int i = 0; i < tabs; i++)
//			System.out.print("\t");
//	
//		if (astNode.getType() == Token.FUNCTION)
//			System.out.print("Function : ");
		if(StringUtils.isNotBlank(sym.getName()))
			allSymbol.add(sym.getName());
		
		return true;
	}
	
	private int getNumTabs(JSSymbol sym)
	{
		int tabs = 0;
		JSSymbol currSym = sym;
		while ((currSym = currSym.getParent()) != null)
			tabs++;
		return tabs;
	}
}