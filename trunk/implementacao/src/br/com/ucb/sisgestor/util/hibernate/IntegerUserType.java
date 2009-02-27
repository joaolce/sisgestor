/*
 * Projeto: SisGestor
 * Criação: 25/10/2008 por João Lúcio
 */
package br.com.ucb.sisgestor.util.hibernate;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Properties;
import org.hibernate.HibernateException;
import org.hibernate.MappingException;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

/**
 * UserType reponsável por tipar o atributo mapeado no Hibernate com o código de um Objeto(Integer) que
 * implemente a interface {@link CodigoDescricao}.
 * 
 * @param <E> enumeração
 * 
 * @author João Lúcio
 * @since 25/10/2008
 */
public class IntegerUserType<E extends Enum<E>> implements UserType, ParameterizedType {

	private static final int[]	SQL_TYPES	= new int[] {Types.SMALLINT};
	private Class<E>				clazz;
	private CodigoDescricao		codigoDescricao;

	/**
	 * {@inheritDoc}
	 */
	public Object assemble(Serializable cached, Object owner) throws HibernateException {
		return cached;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object deepCopy(Object value) throws HibernateException {
		return value;
	}

	/**
	 * {@inheritDoc}
	 */
	public Serializable disassemble(Object value) throws HibernateException {
		return (Serializable) value;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean equals(Object x, Object y) throws HibernateException {
		if (x == y) { //NOPMD by João Lúcio - implementação de infra. Precisa testar se os objetos são os mesmos
			return true;
		}
		if ((null == x) || (null == y)) {
			return false;
		}
		return x.equals(y);
	}

	/**
	 * {@inheritDoc}
	 */
	public int hashCode(Object x) throws HibernateException {
		return x.hashCode();
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isMutable() {
		return false;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException,
			SQLException {
		Integer codigo = rs.getInt(names[0]);
		CodigoDescricao object = null;
		object = this.getObjeto(codigo);
		return object;
	}

	/**
	 * {@inheritDoc}
	 */
	public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException,
			SQLException {
		if (null == value) {
			st.setNull(index, Types.INTEGER);
		} else {
			this.codigoDescricao = (CodigoDescricao) value;
			st.setInt(index, this.codigoDescricao.getId());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public Object replace(Object original, Object target, Object owner) throws HibernateException {
		return original;
	}

	/**
	 * {@inheritDoc}
	 */
	public Class<E> returnedClass() {
		return this.clazz;
	}

	/**
	 * {@inheritDoc}
	 */
	@SuppressWarnings("unchecked")
	public void setParameterValues(Properties params) {
		String className = params.getProperty("className");
		if (className == null) {
			throw new MappingException("className parameter not specified");
		}
		try {
			this.clazz = (Class<E>) Class.forName(className);
		} catch (java.lang.ClassNotFoundException e) {
			throw new MappingException("class " + className + " not found", e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public int[] sqlTypes() {
		return SQL_TYPES.clone();
	}

	/**
	 * Retorna o objeto.
	 * 
	 * @param codigo código do objeto
	 * @return enum
	 */
	private CodigoDescricao getObjeto(Integer codigo) {
		Class<? extends Enum<E>> enumClass = this.returnedClass();
		Enum<E>[] enums = enumClass.getEnumConstants();
		for (Enum<E> enum1 : enums) {
			if (((CodigoDescricao) enum1).getId() == codigo) {
				return (CodigoDescricao) enum1;
			}
		}
		return null;
	}
}
