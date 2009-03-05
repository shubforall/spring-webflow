/*
 * Copyright 2004-2008 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.binding.convert;

import java.util.Set;

/**
 * A service interface for retrieving type conversion executors. The returned command objects are thread-safe and may be
 * safely cached for use by client code.
 * 
 * Type converters convert from one type to another.
 * 
 * @author Keith Donald
 */
public interface ConversionService {

	/**
	 * Execute a conversion of the source object provided to the specified <code>targetClass</code>
	 * @param source the source to convert from (may be null)
	 * @param targetClass the target class to convert to
	 * @return the converted object, an instance of the <code>targetClass</code>
	 * @throws ConversionException if an exception occurred during the conversion process
	 */
	public Object executeConversion(Object source, Class<?> targetClass) throws ConversionException;

	/**
	 * Execute a conversion using the custom converter with the provided id.
	 * @param converterId the id of the custom converter, which must be registered with this conversion service and
	 * capable of converting to the target class
	 * @param source the source to convert from (may be null)
	 * @param targetClass the target class to convert to
	 * @return the converted object, an instance of the <code>targetClass</code>
	 * @throws ConversionException if an exception occurred during the conversion process
	 */
	public Object executeConversion(String converterId, Object source, Class<?> targetClass);

	/**
	 * Return the default conversion executor capable of converting source objects of the specified
	 * <code>sourceClass</code> to instances of the <code>targetClass</code>.
	 * <p>
	 * The returned ConversionExecutor is thread-safe and may safely be cached for use in client code.
	 * @param sourceClass the source class to convert from (required)
	 * @param targetClass the target class to convert to (required)
	 * @return the executor that can execute instance type conversion, never null
	 * @throws ConversionExecutorNotFoundException when no suitable conversion executor could be found
	 */
	public <S, T> ConversionExecutor<S, T> getConversionExecutor(Class<S> sourceClass, Class<T> targetClass)
			throws ConversionExecutorNotFoundException;

	/**
	 * Return the custom conversion executor capable of converting source objects of the specified
	 * <code>sourceClass</code> to instances of the <code>targetClass</code>.
	 * <p>
	 * The returned ConversionExecutor is thread-safe and may safely be cached for use in client code.
	 * @param id the id of the custom conversion executor (required)
	 * @param sourceClass the source class to convert from (required)
	 * @param targetClass the target class to convert to (required)
	 * @return the executor that can execute instance type conversion, never null
	 * @throws ConversionExecutorNotFoundException when no suitable conversion executor could be found
	 */
	public <S, T> ConversionExecutor<S, T> getConversionExecutor(String id, Class<S> sourceClass, Class<T> targetClass)
			throws ConversionExecutorNotFoundException;

	/**
	 * Return all conversion executors capable of converting <i>from</i> the provided <code>sourceClass</code>. For
	 * example, <code>getConversionExecutor(String.class)</code> would return all converters that convert from String to
	 * some other Object. Mainly useful for adapting a set of converters to some other environment.
	 * @param sourceClass the source class converting from
	 * @return the conversion executors that can convert from that source class
	 */
	public <S> Set<ConversionExecutor<S, ?>> getConversionExecutors(Class<S> sourceClass);

	/**
	 * Lookup a class by its well-known alias. For example, <code>long</code> for <code>java.lang.Long</code>
	 * @param alias the class alias
	 * @return the class, or <code>null</code> if no alias exists
	 */
	public Class<?> getClassForAlias(String alias);

}