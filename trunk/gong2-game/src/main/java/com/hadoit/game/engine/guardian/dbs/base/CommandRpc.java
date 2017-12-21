package com.hadoit.game.engine.guardian.dbs.base;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 带有这个annotation的方法，都会被作为dbs command
 * 
 * @author bezy
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface CommandRpc {

	/**
	 * rpc的别名(opName = handlerName + "." + alias)
	 * 
	 * @return
	 */
	public String alias() default "";

	/**
	 * 整个rpc的别名(opName = fullAlias)
	 * 
	 * @return
	 */
	public String fullAlias() default "";

	/**
	 * 是否unpack参数，默认不unpack，则参数为Object[]
	 * 
	 * @return
	 */
	public boolean unpack() default false;

}
